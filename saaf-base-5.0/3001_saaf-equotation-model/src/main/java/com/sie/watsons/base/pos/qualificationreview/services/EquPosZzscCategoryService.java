package com.sie.watsons.base.pos.qualificationreview.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCategoryEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscCategory;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/equPosZzscCategoryService")
public class EquPosZzscCategoryService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCategoryService.class);

	@Autowired
	private IEquPosZzscCategory equPosZzscCategoryServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosZzscCategoryServer;
	}

	/**
	 * 资质审查-供应商品类查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findZzscSupplierCategorysInfo")
	public String findZzscSupplierCategorysInfo(@RequestParam(required = false) String params,
											@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosZzscCategoryServer.findZzscSupplierCategorysInfo(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("status");
			efferentParam.add("statusMeaning");
			typeParam.add("EQU_SUPPLIER_CATEGORY_STATUS");
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
	 * 资质审查-供应商品类保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveZzscSupplierCategorysInfo")
	public String saveZzscSupplierCategorysInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosZzscCategoryEntity_HI instance = equPosZzscCategoryServer.saveZzscSupplierCategorysInfo(jsonObject);
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
	 * 资质审查-供应商品类删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteZzscSupplierCategorysInfo")
	public String deleteZzscSupplierCategorysInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosZzscCategoryServer.deleteZzscSupplierCategorysInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}