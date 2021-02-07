package com.sie.watsons.base.pos.archivesChange.afterChange.services;

import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgContactsEntity_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.inter.IEquPosBgContacts;

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
@RequestMapping("/equPosBgContactsService")
public class EquPosBgContactsService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgContactsService.class);

	@Autowired
	private IEquPosBgContacts equPosBgContactsServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosBgContactsServer;
	}

	/**
	 * 档案变更后-供应商联系人目录查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBgSupplierContactsInfo")
	public String findBgSupplierContactsInfo(@RequestParam(required = false) String params,
											   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosBgContactsServer.findBgSupplierContactsInfo(paramsJONS,pageIndex,pageRows);
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
	 * 档案变更后-供应商联系人信息保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveBgSupplierContactsInfo")
	public String saveBgSupplierContactsInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosBgContactsEntity_HI instance = equPosBgContactsServer.saveBgSupplierContactsInfo(jsonObject);
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
	 * 档案变更后-供应商联系人信息删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteBgSupplierContactsInfo")
	public String deleteBgSupplierContactsInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosBgContactsServer.deleteBgSupplierContactsInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}