package com.sie.watsons.base.pos.qualityAudit.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pos.qualityAudit.model.entities.EquPosSupplierQualityAuditEntity_HI;
import com.sie.watsons.base.pos.qualityAudit.model.inter.IEquPosSupplierQualityAudit;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
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
@RequestMapping("/equPosSupplierQualityAuditService")
public class EquPosSupplierQualityAuditService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierQualityAuditService.class);

	@Autowired
	private IEquPosSupplierQualityAudit equPosSupplierQualityAuditServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierQualityAuditServer;
	}

	/**
	 * 供应商质量审核单据查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierQualityAudit")
	public String findSupplierQualityAudit(@RequestParam(required = false) String params,
								   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSZLSH");
			//权限校验end
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSupplierQualityAuditServer.findSupplierQualityAudit(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("sceneType");
			incomingParam.add("qualityAuditStatus");
			incomingParam.add("qualificationStatus");
			incomingParam.add("creditAuditScore");
			incomingParam.add("qualityAuditResult");
			efferentParam.add("sceneTypeMeaning");
			efferentParam.add("qualityAuditStatusMeaning");
			efferentParam.add("qualificationStatusMeaning");
			efferentParam.add("creditAuditScoreMeaning");
			efferentParam.add("qualityAuditResultMeaning");
			typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
			typeParam.add("EQU_QUALITY_AUDIT_STATUS");
			typeParam.add("EQU_QUALIFICATION_STATUS");
			typeParam.add("EQU_CREDIT_AUDIT_SCORE");
			typeParam.add("EQU_QUALITY_AUDIT_RESULT");
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
	 * 供应商质量审核单据保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierQualityAudit")
	public String saveSupplierQualityAudit(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosSupplierQualityAuditEntity_HI instance = equPosSupplierQualityAuditServer.saveSupplierQualityAudit(jsonObject);
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
	 * 供应商质量审核单据删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierQualityAudit")
	public String deleteSupplierQualityAudit(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosSupplierQualityAuditServer.deleteSupplierQualityAudit(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商质量审核单据提交
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "submitSupplierQualityAudit")
	public String submitSupplierQualityAudit(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPosSupplierQualityAuditServer.submitSupplierQualityAudit(jsonObject);
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