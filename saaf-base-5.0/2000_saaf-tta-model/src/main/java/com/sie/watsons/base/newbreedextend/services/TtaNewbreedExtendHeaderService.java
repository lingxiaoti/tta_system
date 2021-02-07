package com.sie.watsons.base.newbreedextend.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendHeaderEntity_HI_RO;
import com.sie.watsons.base.newbreedextend.model.inter.ITtaNewbreedExtendHeader;

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
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaNewbreedExtendHeaderService")
public class TtaNewbreedExtendHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedExtendHeaderService.class);

	@Autowired
	private ITtaNewbreedExtendHeader ttaNewbreedExtendHeaderServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaNewbreedExtendHeaderServer;
	}

	/**
	 * 查询详情
	 *
	 * @param params
	 *
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(@RequestParam(required = false) String params) {
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			int userId = getSessionUserId();
			TtaNewbreedExtendHeaderEntity_HI_RO instance = ttaNewbreedExtendHeaderServer.findByRoId(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			int userId = getSessionUserId();
			JSONObject jsonObject = ttaNewbreedExtendHeaderServer.saveOrUpdate(paramsJSON, userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("新增&修改新品种服务费用信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("新增&修改新品种服务费用信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}

	/**
	 * 新品种宣传服务费  确认
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveTtaNBEComfirm")
	public String saveTtaNBEComfirm(@RequestParam(required = false) String params) {
		try {

			JSONObject jsonObject = new JSONObject();
			int userId = getSessionUserId();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			ttaNewbreedExtendHeaderServer.saveTtaNBEComfirm(jsonObject,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 新品种宣传服务费  取消确认
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveTtaNBECancelComfirm")
	public String saveTtaNBECancelComfirm(@RequestParam(required = false) String params) {
		try {

			JSONObject jsonObject = new JSONObject();
			int userId = getSessionUserId();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			ttaNewbreedExtendHeaderServer.saveTtaNBECancelComfirm(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params- 主键
	 * @description 删除数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {

			JSONObject jsonObject = parseObject(params);
			SaafToolUtils.validateJsonParms(jsonObject,"hId");
			ttaNewbreedExtendHeaderServer.delete(jsonObject.getInteger("hId"));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}