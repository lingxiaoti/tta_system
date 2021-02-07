package com.sie.watsons.base.proposal.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnLEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandCountCRecord;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandplnH;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequestMapping("/ttaBrandplnHService")
public class TtaBrandplnHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandplnHService.class);

	@Autowired
	private ITtaBrandplnH ttaBrandplnHServer;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private ITtaBrandCountCRecord ttaBrandCountCRecordServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaBrandplnHServer;
	}

	//用于控制并发查询的任务数
	private static ExecutorService concurrentExportPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	//阻塞队列
	private LinkedBlockingQueue<JSONObject> queue = new LinkedBlockingQueue<JSONObject>(1000);

	public TtaBrandplnHService() {
		LOGGER.info("*****************品牌计划类初始化******************************");
		for (int i=0;i<8;i++){
			Runnable runnable=new Runnable() {
				@Override
				public void run() {
					while (true){
						JSONObject countParams = null;
						try {
							LOGGER.info("{}开始处理，当前任务数:{}",Thread.currentThread().getName(),queue.size());
							//获取参数
							countParams = queue.take();
							Integer userId = countParams.getInteger("userId") != null ? countParams.getInteger("userId") : -1;
							//执行方法
							ttaBrandplnHServer.callCount(countParams,userId);
						}catch (Exception e){
							jedisCluster.setex(countParams.getString("createKey"), 3600, "{}");
							LOGGER.error(e.getMessage(),e);
							try {
								ttaBrandCountCRecordServer.updateBrandRecordStatus(countParams,e);
							} catch (Exception e1) {
								e1.printStackTrace();
								LOGGER.error("品牌计算操作记录更新操作失败:{}",e1.getMessage());
							}
						}
					}
				}
			};
			concurrentExportPool.submit(runnable);
		}
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<TtaBrandplnHEntity_HI_RO> result = ttaBrandplnHServer.find(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaBrandplnHEntity_HI instance = ttaBrandplnHServer.saveOrUpdate(jsonObject, userId);
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
	 * @param params- 主键
	 * @description 删除数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {
			if (StringUtils.isBlank(params)) {
				return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
			}
			JSONObject jsonObject = JSON.parseObject(params);
			String[] ids = jsonObject.getString("ids").split(",");
			for (String pkId : ids) {
				ttaBrandplnHServer.delete(Integer.parseInt(pkId));
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
	/**
	 * 查询详情
	 *
	 * @param params
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(String params) {
		try{
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaBrandplnHEntity_HI_RO instance = ttaBrandplnHServer.callfindByRoId(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			String ExMsg = e.getMessage();
			if(e instanceof UndeclaredThrowableException && ExMsg ==null ){
				ExMsg =((UndeclaredThrowableException)e).getUndeclaredThrowable().getMessage();
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ExMsg, 0, null).toString();
		}
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findLov")
	public String findLov(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<TtaBrandplnHEntity_HI_RO> result = ttaBrandplnHServer.find(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}



	/**
	 * @param params
	 * @description 确认
	 */
	@RequestMapping(method = RequestMethod.POST, value = "confirm")
	public String confirm(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaBrandplnHEntity_HI instance = ttaBrandplnHServer.updateconfirm(jsonObject, userId);
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
	 * @param params
	 * @description 取消确认
	 */
	@RequestMapping(method = RequestMethod.POST, value = "cancelConfirm")
	public String cancelConfirm(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaProposalHeaderEntity_HI instance = ttaBrandplnHServer.updatecancelConfirm(jsonObject, userId);
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
	 * 检查其他模块是否为非确定状态
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "brandplnHCheckConfirm")
	public String brandplnHCheckConfirm(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			String instance = ttaBrandplnHServer.checkConfirm(jsonObject, userId);
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
	 * @param params
	 * @description 计算
	 */
	@RequestMapping(method = RequestMethod.POST, value = "count")
	public String count(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			List<TtaBrandplnLEntity_HI> instance = ttaBrandplnHServer.callCount(jsonObject, userId);
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
	 * 品牌拉取数据功能
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "submitBrandCountTask")
	public String export(@RequestParam(required = true) String params){
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer userId = this.getSessionUserId();
			jsonObject.put("userId",userId);
			SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
			String createKey = sdf.format(new Date()) + RandomUtils.nextInt(100);
			if (jsonObject.getInteger("varUserId") != null) {
				createKey += jsonObject.getInteger("varUserId");
			}
			jsonObject.put("createKey",createKey);
			boolean result = queue.offer(jsonObject);
			Assert.isTrue(result,"当前执行品牌计算操作人数过多,(共有:"+queue.size()+"),请稍后重试!");
			ttaBrandCountCRecordServer.saveBrandplHCountRecordInfo(jsonObject,request,response);
			return SToolUtils.convertResultJSONObj("S","执行成功",0,jsonObject).toJSONString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M",e.getMessage(),0,null).toJSONString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("M","执行失败",0,null).toJSONString();
		}
	}

	/**
	 *查询品牌生成状态
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "getBrandCountCreateStatus")
	public String getBrandCountCreateStatus(@RequestParam(required = true) String params){
		try {
			JSONObject parseObject = this.parseObject(params);
			SaafToolUtils.validateJsonParms(parseObject,"createKey");
			JSONObject result= ttaBrandplnHServer.getBrandCreateResult(parseObject.getString("createKey"));
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
	 * 更新品牌计划汇总数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "brandplnHUpdate")
	public String brandplnHUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			int instance = ttaBrandplnHServer.brandplnHUpdate(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}