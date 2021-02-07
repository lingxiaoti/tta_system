package com.sie.watsons.base.pos.supplierinfo.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosAssociatedSupplierEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.inter.IEquPosAssociatedSupplier;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/equPosAssociatedSupplierService")
public class EquPosAssociatedSupplierService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosAssociatedSupplierService.class);

	@Autowired
	private IEquPosAssociatedSupplier equPosAssociatedSupplierServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosAssociatedSupplierServer;
	}

	/**
	 * 供应商关联工厂查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findAssociatedSupplier")
	public String findAssociatedSupplier(@RequestParam(required = false) String params,
								   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			if(paramsJONS.containsKey("pageIndex")){
				pageIndex = paramsJONS.getInteger("pageIndex");
			}
			if(paramsJONS.containsKey("pageRows")){
				pageRows = paramsJONS.getInteger("pageRows");
			}
			JSONObject result  =equPosAssociatedSupplierServer.findAssociatedSupplier(paramsJONS,pageIndex,pageRows);

//			List<String> incomingParam = new ArrayList<>();
//			List<String> efferentParam = new ArrayList<>();
//			List<String> typeParam = new ArrayList<>();
//			incomingParam.add("supplierStatus");
//			efferentParam.add("supplierStatusMeaning");
//			typeParam.add("EQU_SUPPLIER_STATUS");
//			JSONArray data = result.getJSONArray("data");
//			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
//			result.put("data",data);

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
	 * 供应商关联工厂-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveAssociatedSupplier")
	public String saveSupplierInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosAssociatedSupplierEntity_HI instance = equPosAssociatedSupplierServer.saveAssociatedSupplier(jsonObject);
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
	 * 供应商关联工厂-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteAssociatedSupplier")
	public String deleteAssociatedSupplier(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosAssociatedSupplierServer.deleteAssociatedSupplier(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}