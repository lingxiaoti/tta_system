package com.sie.watsons.base.pos.suspend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.manage.model.entities.readonly.EquPosSceneManageEntity_HI_RO;
import com.sie.watsons.base.pos.suspend.model.entities.EquPosSupplierSuspendEntity_HI;
import com.sie.watsons.base.pos.suspend.model.entities.readonly.EquPosSupplierSuspendEntity_HI_RO;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sie.watsons.base.pos.suspend.model.inter.IEquPosSupplierSuspend;


@RestController
@RequestMapping("/equPosSupplierSuspendService")
public class EquPosSupplierSuspendService extends CommonAbstractService {
private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierSuspendService.class);
	@Autowired
	private IEquPosSupplierSuspend equPosSupplierSuspendServer;
	public EquPosSupplierSuspendService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	/**
	 * 查询供应商暂停淘汰INFO（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEquPosSuspendInfo")
	public String findEquPosSuspendInfo(@RequestParam(required = false) String params,
											@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		JSONObject paramsJSON = parseObject(params);
		params = JSONObject.toJSONString(paramsJSON);
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSZTTT");
			//权限校验end
			Pagination<EquPosSupplierSuspendEntity_HI_RO> infoList = equPosSupplierSuspendServer.findEquPosSuspendInfo(params, pageIndex, pageRows);
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierDeptStatus");
			incomingParam.add("supSuspendStatus");
			incomingParam.add("piSupplierStatus");
			efferentParam.add("supplierDeptStatusName");
			efferentParam.add("supSuspendStatusName");
			efferentParam.add("supplierStatusName");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			typeParam.add("EQU_SUPPLIER_STATUS");
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
     * 查询供应商暂停淘汰详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupSuspendDatail")
    public String findSupSuspendDatail(@RequestParam  String params ) {
        LOGGER.info(params);
		JSONObject paramsJSON = parseObject(params);
		params = JSONObject.toJSONString(paramsJSON);
        try {
			EquPosSupplierSuspendEntity_HI_RO  returnJson = equPosSupplierSuspendServer.findSupSuspendDatail(params );

			JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(returnJson));
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierDeptStatus");
			incomingParam.add("supSuspendStatus");
			incomingParam.add("piSupplierStatus");
			efferentParam.add("supplierDeptStatusName");
			efferentParam.add("supSuspendStatusName");
			efferentParam.add("supplierStatusName");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			typeParam.add("EQU_SUPPLIER_STATUS");
			json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS   , "", 0, json).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询供应商列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierLov")
	public String findSupplierLov(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<EquPosSupplierSuspendEntity_HI_RO> result = equPosSupplierSuspendServer.findSupplierLov(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierStatus");
			efferentParam.add("supplierDeptStatusName");
			typeParam.add("EQU_SUPPLIER_STATUS");
			JSONArray data = jsonObject.getJSONArray("data");
			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
			jsonObject.put("data",data);

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
	 * @description  提交
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosSuspendSubmit")
	public String saveEquPosSuspendSubmit(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			EquPosSupplierSuspendEntity_HI renturnEntity = equPosSupplierSuspendServer.saveEquPosSuspendSubmit(jsonObject, userId);
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
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosSuspend")
	public String saveEquPosSuspend(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			EquPosSupplierSuspendEntity_HI renturnEntity = equPosSupplierSuspendServer.saveEquPosSuspend(jsonObject, userId);
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
	 * @description 删除
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSuspend")
	public String deleteSuspend(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Integer listId = equPosSupplierSuspendServer.deleteSuspend(jsonObject, userId);
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
	 * @description 删除行
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSuspendFile")
	public String deleteSuspendFile(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			equPosSupplierSuspendServer.deleteSuspendFile(jsonObject, userId);
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
	 * 供应商暂停/淘汰审批回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "suspendApprovalCallback")
	public String suspendApprovalCallback(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPosSupplierSuspendEntity_HI entity = equPosSupplierSuspendServer.suspendApprovalCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}
