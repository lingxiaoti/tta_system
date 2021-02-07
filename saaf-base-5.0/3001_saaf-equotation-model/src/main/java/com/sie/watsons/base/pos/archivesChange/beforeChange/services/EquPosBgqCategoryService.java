package com.sie.watsons.base.pos.archivesChange.beforeChange.services;

import com.alibaba.fastjson.JSONArray;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCategoryEntity_HI;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqCategory;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/equPosBgqCategoryService")
public class EquPosBgqCategoryService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqCategoryService.class);

	@Autowired
	private IEquPosBgqCategory equPosBgqCategoryServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosBgqCategoryServer;
	}

	/**
	 * 档案变更前-供应商品类查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBgqSupplierCategorysInfo")
	public String findBgqSupplierCategorysInfo(@RequestParam(required = false) String params,
											  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											  @RequestParam(required = false, defaultValue = "999") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosBgqCategoryServer.findBgqSupplierCategorysInfo(paramsJONS,pageIndex,pageRows);

//			List<String> incomingParam = new ArrayList<>();
//			List<String> efferentParam = new ArrayList<>();
//			List<String> typeParam = new ArrayList<>();
//			incomingParam.add("supplierType");
//			efferentParam.add("supplierTypeMeaning");
//			typeParam.add("EQU_SUPPLIER_TYPE");
//			JSONArray data = result.getJSONArray("data");
//			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
//			result.put("data",data);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("status");
			efferentParam.add("statusMeaning");
			typeParam.add("EQU_SUPPLIER_CATEGORY_STATUS");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
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
	 * 档案变更前-供应商品类保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveBgqSupplierCategorysInfo")
	public String saveBgqSupplierCategorysInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosBgqCategoryEntity_HI instance = equPosBgqCategoryServer.saveBgqSupplierCategorysInfo(jsonObject);
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
	 * 档案变更前-供应商品类删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteBgqSupplierCategorysInfo")
	public String deleteBgqSupplierCategorysInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosBgqCategoryServer.deleteBgqSupplierCategorysInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}