package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.base.user.model.entities.BaseProductInfoEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInfoEntity_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseProductInfo;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseAccreditCache;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.server.BaseAccreditCacheServer;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 对外接口：产品表的操作
 * @author ZhangJun
 * @createTime 2017-12-27 18:07
 * @description 产品表的操作
 */
@RestController
@RequestMapping("/baseProductInfoService")
public class BaseProductInfoService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseProductInfoService.class);
	@Autowired
	private IBaseProductInfo baseProductInfoServer;

	@Autowired
	private IBaseAccreditCache baseAccreditCacheServer;

//	@Autowired
//	private ProductDataHandleServer productDataHandleServer;



	@Override
	public IBaseCommon<BaseProductInfoEntity_HI> getBaseCommonServer() {
		return baseProductInfoServer;
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @创建时间 2017/12/27
	 */
	@Permission(menuCode = "CPGL")
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			queryParamJSON.put("operationOrgIds",baseAccreditCacheServer.getOrgId(getSessionUserId()));
			queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON, "itemType","isValid","ouOrgId","innerItemCode","organizationName","itemCode");
			Pagination findList = baseProductInfoServer.findProductInfoROPagination(queryParamJSON,pageIndex,pageRows);

			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findProductInfo")
	public String findProductInfo(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(queryParamJSON, "respId");
			ProFileBean proFileBean= baseAccreditCacheServer.getOrg(getSessionUserId(),queryParamJSON.getInteger("respId"));
			Assert.notNull(proFileBean,"用户当前职责未配置ou");
			Set<String> orgIds=new HashSet<>();
			orgIds.add(proFileBean.getProfileValue());
			queryParamJSON.put("operationOrgIds",orgIds );
			queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON, "itemType","isValid","ouOrgId","innerItemCode","organizationName","itemCode");
			Pagination findList = baseProductInfoServer.findProductInfoROPagination(queryParamJSON,pageIndex,pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 保存或更新数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @创建时间 2017/12/27
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		return super.saveOrUpdate(params);
	}

	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return
	 * @author ZhangJun
	 * @创建时间 2017/12/27
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}
	
	/**
	 * 查询产品信息
	 * @param params 查询参数
	 * {
	 *     productCodes:产品编码集合
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/6 14:08
	 * @description 查询产品信息
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseProductInfoEntities")
	public String findBaseProductInfoEntities(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
	        List<BaseProductInfoEntity_HI_RO> findList = baseProductInfoServer.findBaseProductInfoEntities(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), JSON.toJSONString(findList)).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 查询产品名称
	 * @param params 查询参数
	 * {
	 *     productCodes:产品编码集合,
	 *     organizationId:组织编码
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/6 14:05
	 * @description 查询产品名称
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseProductInfoItemName")
	public String findBaseProductInfoItemName(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			List<BaseProductInfoEntity_HI_RO> findList = baseProductInfoServer.findBaseProductInfoItemName(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), JSON.toJSONString(findList)).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 查询产品描述
	 * @param params 查询参数
	 * {
	 *     productCodes:产品编码集合
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/6 14:06
	 * @description 查询产品描述
	 */
	@RequestMapping(method = RequestMethod.POST,value="findBaseProductInfoItemDesc")
	public String findBaseProductInfoItemDesc(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			List<BaseProductInfoEntity_HI_RO> findList = baseProductInfoServer.findBaseProductInfoItemDesc(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), JSON.toJSONString(findList)).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 查询列表
	 * @param params
	 * @return {@link BaseProductInfoEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/1/31
	 * @description 查询列表
	 */
	@RequestMapping(method = RequestMethod.POST,value="findList")
	@Override
	public String findList(@RequestParam(required=false) String params) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			List list = this.baseProductInfoServer.findCacheList(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", list.size(), list).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
	

	/**
	 * 同步产品
	 * @author ZhangJun
	 * @createTime 2018/3/13
	 * @description 同步产品
	 */
	@RequestMapping(method = RequestMethod.POST,value="saveSyncProductInfo")
	public String saveSyncProductInfo(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			JSONObject result = this.baseProductInfoServer.saveSyncProductInfo(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, new JSONArray().fluentAdd(result)).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	/**
	 * 交易汇总相关查询（仓库发货确认）
	 * @param params  {
	 *                   unitTraQuantity
	 *                   itemCode :物料编码
	 *                   }
	 * @author yuzhenli
	 * @description 通过unitTraQuantity+获取boxUnit
	 */
	@RequestMapping(method = RequestMethod.POST,value="getBoxUnit")
	public String getBoxUnit(@RequestParam(required=true) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(queryParamJSON,"unitTraQuantity","itemCode");
			BaseProductInfoEntity_HI_RO result = this.baseProductInfoServer.getBoxUnit(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, new JSONArray().fluentAdd(result)).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  部分盘点查询品规信息
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItemInfo", produces = "application/json")
	public String getProductItemInfo(@RequestParam(required = false) String params,
							   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
							   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params))
				jsonObject = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(jsonObject,"respId");
			UserSessionBean userSessionBean=getUserSessionBean();
			Integer respId=jsonObject.getInteger("respId");
			ProFileBean org=baseAccreditCacheServer.getOrg(userSessionBean.getUserId(),respId);
			Assert.notNull(org,"当前用户未配置ou权限");
			ProFileBean channel=baseAccreditCacheServer.getChannelType(userSessionBean.getUserId(),respId);
			Assert.notNull(channel,"当前用户未配置渠道权限");
			jsonObject.fluentPut("orgId",org.getProfileValue())
					.fluentPut("channelCode",channel.getProfileValue());
			Pagination<BaseProductInfoEntity_HI_RO> result = baseProductInfoServer.findItemInfo(jsonObject,pageIndex,pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "网络异常" , 0, null).toString();
		}
	}


//	@RequestMapping(value = "/productImport/{respId}", method = RequestMethod.POST, produces = "application/json")
//	public String productImport(@PathVariable Integer respId, @RequestParam("file") MultipartFile file) {
//		try {
//			Assert.notNull(file, "上传内容为空");
//			if(respId==null)
//				throw new IllegalArgumentException("缺少参数 respId");
//			UserSessionBean userSessionBean=getUserSessionBean();
//			ProFileBean org=baseAccreditCacheServer.getOrg(userSessionBean.getUserId(),respId);
//			Assert.notNull(org,"当前用户未配置ou权限");
//			ProFileBean channel=baseAccreditCacheServer.getChannelType(userSessionBean.getUserId(),respId);
//			Assert.notNull(channel,"当前用户未配置渠道权限");
//			JSONObject jsonObject=new JSONObject()
//					.fluentPut("orgId",org.getProfileValue())
//					.fluentPut("channelCode",channel.getProfileValue());
//			Map<String,String> map=new HashMap<>();
//			map.put("itemCode","产品编码");
//			map.put("itemName","产品名称");
//			productDataHandleServer.setNeedHandlerFields("itemCode");
//			productDataHandleServer.setBussnessData(jsonObject);
//			ExcelImportResult<ProductBean> result= ExcelImportUtil.importExcel(file.getInputStream(),map,productDataHandleServer, ProductBean.class,false);
//			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", result.getSuccessDataList().size(), result).toJSONString();
//		} catch (IllegalArgumentException e) {
//			logger.warn(e.getMessage());
//			return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 ", 0, null).toJSONString();
//		}
//	}

}
