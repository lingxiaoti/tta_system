package com.sie.watsons.base.clause.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.util.SaafToolUtils;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.clause.model.entities.readonly.TtaTermsFrameHeaderEntity_HI_RO;
import com.sie.watsons.base.clause.model.inter.ITtaTermsFrameHeader;
import com.yhg.base.utils.SToolUtils;

import com.yhg.hibernate.core.paging.Pagination;
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
@RequestMapping("/ttaTermsFrameHeaderService")
public class TtaTermsFrameHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsFrameHeaderService.class);

	@Autowired
	private ITtaTermsFrameHeader ttaTermsFrameHeaderServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaTermsFrameHeaderServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findTtaTermsFrameHeaderPagination")
	public String findTtaTermsFrameHeaderPagination(@RequestParam(required = false) String params,
													@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
													@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<TtaTermsFrameHeaderEntity_HI_RO> baseJobPagination = ttaTermsFrameHeaderServer.findTtaTermsFrameHeaderPagination(queryParamJSON, pageIndex, pageRows);
			JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(baseJobPagination));
			jsonResult.put("status", SUCCESS_STATUS);
			return JSON.toJSONString(jsonResult);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateTraermsAll")
	public String saveOrUpdateTraermsAll(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONObject jsonObject = ttaTermsFrameHeaderServer.saveOrUpdateTraermsAll(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("新增&修改条款框架信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("新增&修改条款框架信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}

	/**
	 * 条款框架复制
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateCopy")
	public String saveOrUpdateCopy(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONObject jsonObject = ttaTermsFrameHeaderServer.saveOrUpdateCopy(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("复制信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("复制信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常"+e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 待审批
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateStatus")
	public String updateStatus(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONArray jsonObject = ttaTermsFrameHeaderServer.updateStatus(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("条款框架审批异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("条款框架审批异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "条款框架服务异常", 0, null).toString();
		}
	}

	/**
	 * 条款框架变更
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "changeTraermsAll")
	public String saveChangeTraermsAll(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			SaafToolUtils.validateJsonParms(paramsJSON, "teamFrameworkId");
			JSONObject jsonObject = ttaTermsFrameHeaderServer.saveChangeTraermsAll(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("条款框架变更异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("条款框架变更异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "条款框架服务变更异常"+e.getMessage(), 0, null).toString();
		}
	}
}