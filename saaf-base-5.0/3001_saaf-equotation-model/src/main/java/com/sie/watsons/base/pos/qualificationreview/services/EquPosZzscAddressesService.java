package com.sie.watsons.base.pos.qualificationreview.services;

import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscAddressesEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosZzscAddresses;

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
@RequestMapping("/equPosZzscAddressesService")
public class EquPosZzscAddressesService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscAddressesService.class);

	@Autowired
	private IEquPosZzscAddresses equPosZzscAddressesServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosZzscAddressesServer;
	}

	/**
	 * 资质审查-查询供应商地址信息
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findZzscSupplierAddressInfo")
	public String findZzscSupplierAddressInfo(@RequestParam(required = false) String params,
										  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
										  @RequestParam(required = false, defaultValue = "999") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosZzscAddressesServer.findZzscSupplierAddressInfo(paramsJONS,pageIndex,pageRows);
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
	 * 资质审查-供应商地址信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveZzscSupplierAddressInfo")
	public String saveZzscSupplierAddressInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosZzscAddressesEntity_HI instance = equPosZzscAddressesServer.saveZzscSupplierAddressInfo(jsonObject);
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
	 * 资质审查-供应商地址信息删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteZzscSupplierAddressInfo")
	public String deleteZzscSupplierAddressInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosZzscAddressesServer.deleteZzscSupplierAddressInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}