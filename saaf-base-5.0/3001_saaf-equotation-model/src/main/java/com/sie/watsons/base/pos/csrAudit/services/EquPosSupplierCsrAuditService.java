package com.sie.watsons.base.pos.csrAudit.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pos.csrAudit.model.entities.EquPosSupplierCsrAuditEntity_HI;
import com.sie.watsons.base.pos.csrAudit.model.inter.IEquPosSupplierCsrAudit;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/equPosSupplierCsrAuditService")
public class EquPosSupplierCsrAuditService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCsrAuditService.class);

	@Autowired
	private IEquPosSupplierCsrAudit equPosSupplierCsrAuditServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierCsrAuditServer;
	}

	/**
	 * 供应商CSR审核单据查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierCsrAudit")
	public String findSupplierCsrAudit(@RequestParam(required = false) String params,
									   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
									   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSCSRSH");
			//权限校验end
			JSONObject result  =equPosSupplierCsrAuditServer.findSupplierCsrAudit(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("sceneType");
			incomingParam.add("csrAuditStatus");
			incomingParam.add("qualificationStatus");
			incomingParam.add("creditAuditScore");
			incomingParam.add("csrAuditResult");
			efferentParam.add("sceneTypeMeaning");
			efferentParam.add("csrAuditStatusMeaning");
			efferentParam.add("qualificationStatusMeaning");
			efferentParam.add("creditAuditScoreMeaning");
			efferentParam.add("csrAuditResultMeaning");
			typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
			typeParam.add("EQU_CSR_AUDIT_STATUS");
			typeParam.add("EQU_QUALIFICATION_STATUS");
			typeParam.add("EQU_CREDIT_AUDIT_SCORE");
			typeParam.add("EQU_CSR_AUDIT_RESULT");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
			result.put("data",data);

			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商CSR审核单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierCsrAudit")
	public String saveSupplierCsrAudit(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosSupplierCsrAuditEntity_HI instance = equPosSupplierCsrAuditServer.saveSupplierCsrAudit(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商CSR审核单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierCsrAudit")
	public String deleteSupplierCsrAudit(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosSupplierCsrAuditServer.deleteSupplierCsrAudit(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商CSR审核单据提交
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "submitSupplierCsrAudit")
	public String submitSupplierCsrAudit(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPosSupplierCsrAuditServer.submitSupplierCsrAudit(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}
}