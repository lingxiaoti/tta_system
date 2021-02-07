package com.sie.watsons.base.pos.archivesChange.afterChange.services;

import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCredentialsEntity_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgCredentials;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
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
@RequestMapping("/equPosBgCredentialsService")
public class EquPosBgCredentialsService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCredentialsService.class);

	@Autowired
	private IEquPosBgCredentials equPosBgCredentialsServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosBgCredentialsServer;
	}

	/**
	 * 档案变更后-供应商资质信息查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBgSupplierCredentialsInfo")
	public String findBgSupplierCredentialsInfo(@RequestParam(required = false) String params,
												  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
												  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosBgCredentialsServer.findBgSupplierCredentialsInfo(paramsJONS,pageIndex,pageRows);
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
	 * 档案变更后-供应商资质信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveBgSupplierCredentialsInfo")
	public String saveBgSupplierCredentialsInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosBgCredentialsEntity_HI instance = equPosBgCredentialsServer.saveBgSupplierCredentialsInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}
}