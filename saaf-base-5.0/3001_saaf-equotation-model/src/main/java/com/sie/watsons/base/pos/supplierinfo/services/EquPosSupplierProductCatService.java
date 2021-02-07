package com.sie.watsons.base.pos.supplierinfo.services;

import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierProductCatEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosSupplierProductCat;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
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
@RequestMapping("/equPosSupplierProductCatService")
public class EquPosSupplierProductCatService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierProductCatService.class);

	@Autowired
	private IEquPosSupplierProductCat equPosSupplierProductCatServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosSupplierProductCatServer;
	}

	/**
	 * 供应商品类查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierCategorysInfo")
	public String findSupplierCategorysInfo(@RequestParam(required = false) String params,
											@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosSupplierProductCatServer.findSupplierCategorysInfo(paramsJONS,pageIndex,pageRows);
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
	 * 供应商品类-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSupplierCategorysInfo")
	public String saveSupplierCategorysInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosSupplierProductCatEntity_HI instance = equPosSupplierProductCatServer.saveSupplierCategorysInfo(jsonObject);
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
	 * 供应商品类-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierCategorysInfo")
	public String deleteSupplierCategorysInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosSupplierProductCatServer.deleteSupplierCategorysInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}