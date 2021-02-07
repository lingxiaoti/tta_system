package com.sie.watsons.base.pos.warehousing.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.warehousing.model.entities.EquPosSupplierWarehousingEntity_HI;
import com.sie.watsons.base.pos.warehousing.model.entities.readonly.EquPosSupplierWarehousingEntity_HI_RO;
import com.sie.watsons.base.pos.warehousing.model.inter.IEquPosSupplierWarehousing;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/equPosSupplierWarehousingService")
public class EquPosSupplierWarehousingService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierWarehousingService.class);

	@Autowired
	private IEquPosSupplierWarehousing equPosSupplierWarehousingServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierWarehousingServer;
	}

	/**
	 * 查询供应商暂停淘汰INFO（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEquPosWarehousingInfo")
	public String findEquPosWarehousingInfo(@RequestParam(required = false) String params,
										@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSRKSH");
			//权限校验end
			Pagination<EquPosSupplierWarehousingEntity_HI_RO> infoList = equPosSupplierWarehousingServer.findEquPosWarehousingInfo(params, pageIndex, pageRows);
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("sceneType");
			incomingParam.add("supWarehousingStatus");
			efferentParam.add("sceneTypeName");
			efferentParam.add("supWarehousingStatusName");
			typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
			returnJson.put("data",data);

			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}



	/**
	 * 查询供应商入库审批详情
	 * @param params
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupWarehousingDatail")
	public String findSupWarehousingDatail(@RequestParam  String params ) {
		LOGGER.info(params);
		try {
			EquPosSupplierWarehousingEntity_HI_RO returnJson = equPosSupplierWarehousingServer.findSupWarehousingDatail(params );

			JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(returnJson));
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("sceneType");
			incomingParam.add("supWarehousingStatus");
			efferentParam.add("sceneTypeName");
			efferentParam.add("supWarehousingStatusName");
			typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
            json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS   , "", 0, json).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * @param params
	 * @description 提交
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupWarehousingDatailSumbit")
	public String saveSupWarehousingDatailSumbit(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			EquPosSupplierWarehousingEntity_HI renturnEntity = equPosSupplierWarehousingServer.saveSupWarehousingDatailSumbit(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, renturnEntity).toString();
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
    @RequestMapping(method = RequestMethod.POST, value = "saveEquPosWarehousing")
    public String saveEquPosWarehousing(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            EquPosSupplierWarehousingEntity_HI renturnEntity = equPosSupplierWarehousingServer.saveEquPosWarehousing(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, renturnEntity).toString();
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
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosWarehousingForFlow")
	public String saveEquPosWarehousingForFlow(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
//			JSONObject jsonObject = JSON.parseObject(params);

			JSONObject jsonObject = null;
			jsonObject = parseObject(params.substring(1,params.length()-1));
			jsonObject.put("action","save");


//			EquPosSupplierWarehousingEntity_HI renturnEntity = equPosSupplierWarehousingServer.saveEquPosWarehousing(jsonObject, userId);
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
	 * @param params
	 * @description 删除
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierWarehousing")
	public String deleteSupplierWarehousing(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Integer listId = equPosSupplierWarehousingServer.deleteSupplierWarehousing(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, listId).toString();
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
	 * @description
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupWarehousingDatailLine")
	public String findSupWarehousingDatailLine(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			JSONObject returnJson =  equPosSupplierWarehousingServer.findSupWarehousingDatailLine(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, returnJson).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 审批回调接口
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateWarehousingCallback")
	public String updateWarehousingCallback(@RequestParam(required = false) String params) {
		try {
			System.out.println("!!!回调开始了!!!");
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPosSupplierWarehousingEntity_HI entity = equPosSupplierWarehousingServer.updateWarehousingCallback(paramsObject, userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 审批回调接口
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "getActivitiesHistoric")
	public String getActivitiesHistoric(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject activityJson = ResultUtils.getActivitiesHistoric(paramsJONS.getString("code"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, activityJson.getString("procInstId")).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}