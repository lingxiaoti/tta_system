package com.sie.watsons.base.pos.archivesChange.beforeChange.services;

import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCredentialAttachEntity_HI;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.inter.IEquPosBgqCredentialAttach;

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
@RequestMapping("/equPosBgqCredentialAttachService")
public class EquPosBgqCredentialAttachService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqCredentialAttachService.class);

	@Autowired
	private IEquPosBgqCredentialAttach equPosBgqCredentialAttachServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosBgqCredentialAttachServer;
	}

	/**
	 * 档案变更前-供应商资质文件查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findBgqCredentialsAttachmentInfo")
	public String findBgqCredentialsAttachmentInfo(@RequestParam(required = false) String params,
												  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
												  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosBgqCredentialAttachServer.findBgqCredentialsAttachmentInfo(paramsJONS,pageIndex,pageRows);
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
	 * 档案变更前-供应商资质文件保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveBgqCredentialsAttachmentInfo")
	public String saveBgqCredentialsAttachmentInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosBgqCredentialAttachEntity_HI instance = equPosBgqCredentialAttachServer.saveBgqCredentialsAttachmentInfo(jsonObject);
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
	 * 档案变更前-供应商资质文件删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteBgqCredentialsAttachmentInfo")
	public String deleteBgqCredentialsAttachmentInfo(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			return equPosBgqCredentialAttachServer.deleteBgqCredentialsAttachmentInfo(jsonObject);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}