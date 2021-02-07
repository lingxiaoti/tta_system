package com.sie.watsons.base.withdrawal.services;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtEntity_HI;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandCountCRecord;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierBrandEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemHeaderEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemHeaderEntity_HI_RO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.dao.readonly.TtaSupplierItemHeaderDAO_HI_RO;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import redis.clients.jedis.JedisCluster;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequestMapping("/ttaSupplierItemHeaderService")  
public class TtaSupplierItemHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemHeaderService.class);
	@Autowired
	private ITtaSupplierItemHeader iTtaSupplierItemHeader;
	//用于控制并发查询的任务数
	private static ExecutorService concurrentSubmitPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	//阻塞队列
	private LinkedBlockingQueue<JSONObject> queue = new LinkedBlockingQueue<JSONObject>(1000);
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private ITtaBrandCountCRecord ttaBrandCountCRecordServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.iTtaSupplierItemHeader;
	}

	public TtaSupplierItemHeaderService(){
		LOGGER.info("*****************Proposal拆分与合并类初始化******************************");
		for (int i=0;i<8;i++){
			Runnable runnable=new Runnable() {
				@Override
				public void run() {
					while (true){
						JSONObject submitParams = null;
						try {
							LOGGER.info("{}开始处理，当前任务数:{}",Thread.currentThread().getName(),queue.size());
							//获取参数
							submitParams = queue.take();
							Integer userId = submitParams.getInteger("userId") != null ? submitParams.getInteger("userId") : -1;
							if(!SaafToolUtils.isNullOrEmpty(submitParams.getString("type"))){
								String type = submitParams.getString("type");
								switch (type) {
									case "1":
										//提交
										//执行方法
										iTtaSupplierItemHeader.callCommon(submitParams,userId);
										break;
									case "2":
										//生成明细
										iTtaSupplierItemHeader.saveTtaSupplierItemSplitConditionDetail(submitParams,userId);
										break;
								}
							}
						} catch (Exception e){
							jedisCluster.setex(submitParams.getString("createKey"), 3600, "{}");
							LOGGER.error(e.getMessage(),e);
							try {
                                ttaBrandCountCRecordServer.updateProposalSplitRecordStatus(submitParams,e);
							} catch (Exception e1) {
								e1.printStackTrace();
								LOGGER.error("Proposal拆分与合并指定供应商操作记录失败:{}",e1.getMessage());
							}
						}
					}
				}
			};
			concurrentSubmitPool.submit(runnable);
		}
	}

	/**
     * 查询proposal拆分与合并头
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("findTtaSupplierItemHeaderInfoList")
    public String findTtaSupplierItemHeaderEntityHIPage(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSupplierItemHeaderEntity_HI_RO> page = iTtaSupplierItemHeader.findTtaSupplierItemHeaderEntityHIPage(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, null).toJSONString();
		} 
    }

	/**
	 * 新增头或者修改头信息
	 * @param params JSON参数，对象属性的JSON格式
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject instance = iTtaSupplierItemHeader.saveTtaSupplierItemHeaderInfo(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
	
	/**
     * 查询供应商
     * @param params
     * @return
     * @throws Exception 
     */
    @PostMapping("findTtaSupplierEntity_HI_RO")
    public String findTtaSupplierEntity_HI_RO(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSupplierEntity_HI_RO> page = iTtaSupplierItemHeader.findTtaSupplierEntity_HI_RO(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }

	/**
	 * 提交单据
	 * @param params
	 * @return
	 */
	@PostMapping("ttaSupplierItemSubmitBill")
	public String ttaSupplierItemSubmitBill(@RequestParam(required = true) String params){
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			TtaSupplierItemHeaderEntity_HI result = iTtaSupplierItemHeader.saveSubmitBill(parameters,userId);
			return SToolUtils.convertResultJSONObj("S", "提交成功", 1, result).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "提交失败", 0, e).toJSONString();
		}
	}

    /**
     * 提交请求队列
     * @param params
     * @return
     */
	@RequestMapping(value = "submitTask")
	public String export(@RequestParam(required = true) String params){
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer userId = this.getSessionUserId();
			jsonObject.put("userId",userId);
			SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
			String type = jsonObject.getString("type");
			String createKey = "";
			String msgRemark = "";
			if (SaafToolUtils.isNullOrEmpty(type)){
				createKey = "COMMON_" + sdf.format(new Date()) + RandomUtils.nextInt(100);
			} else if ("1".equals(type)) {
				createKey = "APPOINT_" + sdf.format(new Date()) + RandomUtils.nextInt(100);//唯一标识
				msgRemark = "正在进行指定供应商操作";
			} else if ("2".equals(type)) {
				createKey = "CREATE_" + sdf.format(new Date()) + RandomUtils.nextInt(100);//唯一标识
				msgRemark = "正在进行生成明细数据操作";
			}
			if (jsonObject.getInteger("varUserId") != null) {
				createKey += jsonObject.getInteger("varUserId");
			}
			jsonObject.put("createKey",createKey);
			boolean result = queue.offer(jsonObject);
			Assert.isTrue(result,"当前提交操作人数过多,(共有:"+queue.size()+"),请稍后重试!");
			ttaBrandCountCRecordServer.saveProposalSplitRecord(jsonObject,request,response,msgRemark);
			return SToolUtils.convertResultJSONObj("S","执行成功",0,jsonObject).toJSONString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M",e.getMessage(),0,null).toJSONString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("M","执行失败",0,null).toJSONString();
		}
	}

    @RequestMapping(value = "appointVendorNbrStatus")
    public String appointVendorNbrStatus(@RequestParam(required = true) String params){
        try {
            JSONObject parseObject = this.parseObject(params);
            SaafToolUtils.validateJsonParms(parseObject,"createKey");
            JSONObject result= iTtaSupplierItemHeader.appointVendorNbrStatus(parseObject.getString("createKey"));
            if (result == null) {
                return SToolUtils.convertResultJSONObj("W", "正在执行中.....", 0, null).toJSONString();
            } else if (result.isEmpty()){//没有参数信息
                return SToolUtils.convertResultJSONObj("E","执行失败",0,null).toJSONString();
            }
            return SToolUtils.convertResultJSONObj("S","执行成功",0,result).toJSONString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M",e.getMessage(),0,null).toJSONString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            return SToolUtils.convertResultJSONObj("M","执行失败",0,null).toJSONString();
        }
    }

	/**
	 * 单据作废
	 * @param params
	 * @return
	 */
	@PostMapping("ttaSupplierItemDiscard")
	public String ttaSupplierItemDiscard(@RequestParam(required = true) String params){
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			TtaSupplierItemHeaderEntity_HI result = iTtaSupplierItemHeader.saveSupplierItemDiscard(parameters,userId);
			return SToolUtils.convertResultJSONObj("S", "作废成功", 1, result).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "作废失败", 0, e).toJSONString();
		}
	}

	/**
	 * 生成拆分条件明细
	 * @param params
	 * @return
	 */
	@PostMapping("ttaSupplierItemSplitConditionDetail")
	public String ttaSupplierItemSplitConditionDetail(@RequestParam(required = true) String params){
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			List<TtaSupplierItemMidEntity_HI> result = iTtaSupplierItemHeader.saveTtaSupplierItemSplitConditionDetail(parameters,userId);
			return SToolUtils.convertResultJSONObj("S", "操作成功", result.size(), result).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "操作失败:" + e.getMessage(), 0, e).toJSONString();
		}
	}

}
