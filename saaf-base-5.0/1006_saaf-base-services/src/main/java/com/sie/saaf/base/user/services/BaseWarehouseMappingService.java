package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.accredit.annotation.Permission;
import com.sie.saaf.base.user.model.entities.BaseWarehouseMappingEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseWarehouseMapping_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseWarehouseMapping;
import com.sie.saaf.common.bean.ProFileBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 对外接口：子库信息表操作
 * @author ZhangJun
 * @createTime 2017-12-27 18:06
 * @description 子库信息表操作
 */
@RestController
@RequestMapping("/baseWarehouseMappingService")
public class BaseWarehouseMappingService extends CommonAbstractService {
	private static final Logger logger = LoggerFactory.getLogger(BaseWarehouseMappingService.class);
	@Autowired
	private IBaseWarehouseMapping baseWarehouseMappingServer;

//	@Autowired
//	private InvDataHandleServer invDataHandleServer;

	@Override
	public IBaseCommon<BaseWarehouseMappingEntity_HI> getBaseCommonServer() {
		return baseWarehouseMappingServer;
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
	@Permission(menuCode = "ZKCX")
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			queryParamJSON.put("operationOrgIds", baseAccreditCacheServer.getOrgId(getSessionUserId()));
            //清除空值
            Set<String> strings = queryParamJSON.keySet();
            Iterator<String> iter = strings.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                if ("".equals(queryParamJSON.getString(key))) {
                    iter.remove();
                }
            }
            Pagination findList = this.baseWarehouseMappingServer.findROPagination(queryParamJSON,pageIndex,pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	@RequestMapping(method = RequestMethod.POST, value = "findWarehouse")
    public String findWarehouse(@RequestParam(required = false) String params,
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
            Pagination findList = this.baseWarehouseMappingServer.findROPagination(queryParamJSON, pageIndex, pageRows);
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
	 * 根据操作字库编码获取所属经销商下属子库
	 * @param params {
	 *     warehouseCode:子库编码
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/30
	 * @description 根据操作字库编码获取所属经销商下属子库
	 */
	@RequestMapping(method = RequestMethod.POST,value="findChildrenWarehouseMapping")
	public String findChildrenWarehouseMapping(@RequestParam(required=false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List<BaseWarehouseMappingEntity_HI> findList = this.baseWarehouseMappingServer.findChildrenWarehouseMapping(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询列表
	 * @author ZhangJun
	 * @createTime 2018/2/7
	 * @description 查询列表
	 */
	@RequestMapping(method = RequestMethod.POST,value="findList")
	@Override
	public String findList(String params) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			List list = this.baseWarehouseMappingServer.findCacheWarehouse(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", list.size(), list).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	@RequestMapping(method = RequestMethod.POST,value="findBaseWarehouseMappingEntityInfo")
	public String findBaseWarehouseMappingEntityInfo(String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List<BaseWarehouseMappingEntity_HI> findList = this.baseWarehouseMappingServer.findBaseWarehouseMappingEntityInfo(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	@RequestMapping(method = RequestMethod.POST,value="findBaseWarehouseMappingEntityInfoByCode")
	public String findBaseWarehouseMappingEntityInfoByCode(String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List<BaseWarehouseMappingEntity_HI> findList = this.baseWarehouseMappingServer.findBaseWarehouseMappingEntityInfoByCode(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST,value="findDelaerList")
	public String findDealaerList(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
	        List findList = this.baseWarehouseMappingServer.findDelaerList(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}


	/**
	 * 门店盘点查询主子库
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value="findMainInv")
	public String findMainInv(@RequestParam(required=false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			List<BaseWarehouseMapping_HI_RO> findList = baseWarehouseMappingServer.findMainInv(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 根据orgId获取可访问的子库编码
	 * @author ZhangJun
	 * @createTime 2018/4/17
	 * @description 根据orgId获取可访问的子库编码
	 */
	@RequestMapping(method = RequestMethod.POST,value="findWarehouseCodeByOrgId")
	public String findWarehouseCodeByOrgId(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			List<String> findList = baseWarehouseMappingServer.findWarehouseCodeByOrgId(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", findList.size(), findList).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}

	@RequestMapping(method = RequestMethod.POST,value="saveSyncBaseWarehouseMapping")
	public String saveSyncBaseWarehouseMapping(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
	        JSONObject result = this.baseWarehouseMappingServer.saveSyncBaseWarehouseMapping(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, new JSONArray().fluentAdd(result)).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}


//	@RequestMapping(value = "/invImport/{respId}", method = RequestMethod.POST, produces = "application/json")
//	public String invImport(@PathVariable Integer respId, @RequestParam("file") MultipartFile file) {
//		try {
//			Assert.notNull(file, "上传内容为空");
//			if (respId==null)
//				throw new IllegalArgumentException("缺少参数 respId");
//			UserSessionBean userSessionBean=getUserSessionBean();
//			ProFileBean org=baseAccreditCacheServer.getOrg(userSessionBean.getUserId(),respId);
//			Assert.notNull(org,"当前用户未配置ou权限");
//			JSONObject jsonObject=new JSONObject()
//					.fluentPut("orgId",org.getProfileValue());
//			Map<String,String> map=new HashMap<>();
//			map.put("invCode","仓库编码");
//			map.put("invName","仓库名称");
//			invDataHandleServer.setNeedHandlerFields("invCode");
//			invDataHandleServer.setBussnessData(jsonObject);
//			ExcelImportResult<InvBean> result= ExcelImportUtil.importExcel(file.getInputStream(),map,invDataHandleServer, InvBean.class,false);
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


