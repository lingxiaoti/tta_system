package com.sie.watsons.base.pos.creditAudit.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosCreditAuditChangeHEntity_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosSupplierCreditAuditEntity_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosSupplierCreditAuditEntity_HI_RO;
import com.sie.watsons.base.pos.creditAudit.model.inter.IEquPosSupplierCreditAudit;

import com.sie.watsons.base.pos.recover.model.entities.EquPosSupplierRecoverEntity_HI;
import com.sie.watsons.base.pos.recover.model.entities.readonly.EquPosSupplierRecoverEntity_HI_RO;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/equPosSupplierCreditAuditService")
public class EquPosSupplierCreditAuditService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCreditAuditService.class);

	@Autowired
	private IEquPosSupplierCreditAudit equPosSupplierCreditAuditServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierCreditAuditServer;
	}


	/**
	 * 查询供应商暂停淘汰INFO（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEquPosCreditAuditInfo")
	public String findEquPosCreditAuditInfo(@RequestParam(required = false) String params,
										@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {

            //权限校验begin
//            JSONObject checkParamsJONS =parseObject(params);
//            CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSXYSH");
            //权限校验end

			Pagination<EquPosSupplierCreditAuditEntity_HI_RO> infoList = equPosSupplierCreditAuditServer.findEquPosCreditAuditInfo(params, pageIndex, pageRows);
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("supCreditAuditType");
            incomingParam.add("specialResults");
            incomingParam.add("creditAuditScore");
            incomingParam.add("supCreditAuditStatus");
            incomingParam.add("supplierStatus");
            efferentParam.add("supCreditAuditTypeName");
            efferentParam.add("specialResultsName");
            efferentParam.add("creditAuditScoreName");
            efferentParam.add("supCreditAuditStatusName");
            efferentParam.add("supplierStatusName");
            typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
            typeParam.add("EQU_CREDIT_AUDIT_RESULT");
            typeParam.add("EQU_CREDIT_AUDIT_SCORE");
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
     * 查询供应商暂停淘汰INFO（分页）
     * @param params
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findQualificationLov")
    public String findQualificationLov(@RequestParam(required = false) String params,
                                            @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                            @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
        LOGGER.info(params);
        try {
            JSONObject json =  this.parseObject(params) ;
            params = JSONObject.toJSONString(json);
            Pagination<EquPosSupplierCreditAuditEntity_HI_RO> infoList = equPosSupplierCreditAuditServer.findQualificationLov(params, pageIndex, pageRows);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("sceneType");
            incomingParam.add("qualificationStatus");
            efferentParam.add("sceneTypeName");
            efferentParam.add("statusName");
            typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
            typeParam.add("EQU_QUALIFICATION_STATUS");
            JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
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
     * 查询详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupCreditAuditDatail")
    public String findSupCreditAuditDatail(@RequestParam  String params ) {
        LOGGER.info(params);
        try {
            EquPosSupplierCreditAuditEntity_HI_RO returnJson = equPosSupplierCreditAuditServer.findSupCreditAuditDatail(params );

            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(returnJson));
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("supCreditAuditType");
            incomingParam.add("specialResults");
            incomingParam.add("creditAuditScore");
            incomingParam.add("supCreditAuditStatus");
            incomingParam.add("supplierStatus");
            efferentParam.add("supCreditAuditTypeName");
            efferentParam.add("specialResultsName");
            efferentParam.add("creditAuditScoreName");
            efferentParam.add("supCreditAuditStatusName");
            efferentParam.add("supplierStatusName");
            typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
            typeParam.add("EQU_CREDIT_AUDIT_RESULT");
            typeParam.add("EQU_CREDIT_AUDIT_SCORE");
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
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveEquPosCreditAudit")
    public String saveEquPosCreditAudit(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            EquPosSupplierCreditAuditEntity_HI renturnEntity = equPosSupplierCreditAuditServer.saveEquPosCreditAudit(jsonObject, userId);
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
    @RequestMapping(method = RequestMethod.POST, value = "saveEquPosCreditAuditSubmit")
    public String saveEquPosCreditAuditSubmit(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            EquPosSupplierCreditAuditEntity_HI renturnEntity = equPosSupplierCreditAuditServer.saveEquPosCreditAuditSubmit(jsonObject, userId);
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
     * @description 刪除
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteSupplierCreditAudit")
    public String deleteSupplierCreditAudit(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            equPosSupplierCreditAuditServer.deleteSupplierCreditAudit(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,null).toString();
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
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateCreditAuditApprovalCallback")
    public String updateCreditAuditApprovalCallback(@RequestParam(required = false) String params) {
        try {
            System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
            JSONObject paramsObject = parseObject(params);
            int userId = paramsObject.getIntValue("userId");
            EquPosSupplierCreditAuditEntity_HI entity = equPosSupplierCreditAuditServer.updateCreditAuditApprovalCallback(paramsObject,userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
        }catch (Exception e){
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}