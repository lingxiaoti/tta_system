package com.sie.watsons.base.pos.qualificationreview.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscSupplierEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.readonly.EquPosZzscSupplierEntity_HI_RO;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscSupplier;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import java.util.ArrayList;

import com.yhg.hibernate.core.paging.Pagination;
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
@RequestMapping("/equPosZzscSupplierService")
public class EquPosZzscSupplierService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscSupplierService.class);

	@Autowired
	private IEquPosZzscSupplier equPosZzscSupplierServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosZzscSupplierServer;
	}

	/**
	 * 资质审查-供应商基础信息查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findZzscSupplierInfo")
	public String findZzscSupplierInfo(@RequestParam(required = false) String params,
								   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosZzscSupplierServer.findZzscSupplierInfo(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierType");
			incomingParam.add("supplierStatus");
			incomingParam.add("country");
			efferentParam.add("supplierTypeMeaning");
			efferentParam.add("supplierStatusMeaning");
			efferentParam.add("countryMeaning");
			typeParam.add("EQU_SUPPLIER_TYPE");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EQU_SUPPLIER_COUNTRY");
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
	 * 资质审查-供应商基础信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveZzscSupplierInfo")
	public String saveZzscSupplierInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosZzscSupplierEntity_HI instance = equPosZzscSupplierServer.saveZzscSupplierInfo(jsonObject);
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
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询供应商列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findZzscSupplierLov")
	public String findZzscSupplierLov(@RequestParam(required = false) String params,
								  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<EquPosZzscSupplierEntity_HI_RO> result = equPosZzscSupplierServer.findZzscSupplierLov(jsonObject, pageIndex, pageRows);
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
}