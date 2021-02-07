package com.sie.watsons.base.pos.blacklist.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.manage.model.entities.readonly.EquPosSceneManageEntity_HI_RO;
import com.sie.watsons.base.pos.blacklist.model.entities.EquPosSupplierBlacklistEntity_HI;
import com.sie.watsons.base.pos.blacklist.model.entities.readonly.EquPosSupplierBlacklistEntity_HI_RO;
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
import com.sie.watsons.base.pos.blacklist.model.inter.IEquPosSupplierBlacklist;


@RestController
@RequestMapping("/equPosSupplierBlacklistService")
public class EquPosSupplierBlacklistService extends CommonAbstractService {
private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierBlacklistService.class);
	@Autowired
	private IEquPosSupplierBlacklist equPosSupplierBlacklistServer;
	public EquPosSupplierBlacklistService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	/**
	 * 查询供应商黑名单（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEquPosBlacklistInfo")
	public String findEquPosBlacklistInfo(@RequestParam(required = false) String params,
											@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSHMD");
			//权限校验end
			Pagination<EquPosSupplierBlacklistEntity_HI_RO> infoList = equPosSupplierBlacklistServer.findEquPosBlacklistInfo(params, pageIndex, pageRows);
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supBlacklistStatus");
			incomingParam.add("supplierStatus");
			efferentParam.add("supBlacklistStatusName");
			efferentParam.add("supplierStatusName");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			typeParam.add("EQU_SUPPLIER_STATUS");
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
			returnJson.put("data",data);

			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


    /**
     * 查询供应商黑名单详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupBlacklistDatail")
    public String findSupBlacklistDatail(@RequestParam  String params ) {
        LOGGER.info(params);
        try {
			EquPosSupplierBlacklistEntity_HI_RO  returnJson = equPosSupplierBlacklistServer.findSupBlacklistDatail(params );

			JSONObject json =  JSONObject.parseObject(JSONObject.toJSONString(returnJson));
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supBlacklistStatus");
			incomingParam.add("supplierStatus");
			efferentParam.add("supBlacklistStatusName");
			efferentParam.add("supplierStatusName");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			typeParam.add("EQU_SUPPLIER_STATUS");
			json = ResultUtils.getLookUpValues(  json ,   incomingParam,  efferentParam,  typeParam);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS   , "", 0, json).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

	@RequestMapping(method = RequestMethod.POST, value = "findRelateDeptUser")
	public String findRelateDeptUser(@RequestParam  String params ) {
		LOGGER.info(params);
		try {
			EquPosSupplierBlacklistEntity_HI_RO  returnJson = equPosSupplierBlacklistServer.findSupBlacklistDatail(params );

			returnJson.setUserId(returnJson.getRelateDeptUserId());
			returnJson.setUserName(returnJson.getRelateDeptUserNumber());

			JSONObject json =  JSONObject.parseObject(JSONObject.toJSONString(returnJson));

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
	 * @description 查询供应商列表LOV（带分页 字典）
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
			Pagination<EquPosSupplierBlacklistEntity_HI_RO> result = equPosSupplierBlacklistServer.findSupplierLov(jsonObject, pageIndex, pageRows);
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
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosBlacklist")
	public String saveEquPosBlacklist(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			EquPosSupplierBlacklistEntity_HI renturnEntity = equPosSupplierBlacklistServer.saveEquPosBlacklist(jsonObject, userId);
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
	 * @description 提交
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosBlack")
	public String saveEquPosBlack(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			EquPosSupplierBlacklistEntity_HI renturnEntity = equPosSupplierBlacklistServer.saveEquPosBlack(jsonObject, userId);
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
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierBlacklist")
	public String deleteSupplierBlacklist(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Integer listId = equPosSupplierBlacklistServer.deleteSupplierBlacklist(jsonObject, userId);
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
	 * 供应商黑名单审批回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "backListApprovalCallback")
	public String backListApprovalCallback(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPosSupplierBlacklistEntity_HI entity = equPosSupplierBlacklistServer.backListApprovalCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}
