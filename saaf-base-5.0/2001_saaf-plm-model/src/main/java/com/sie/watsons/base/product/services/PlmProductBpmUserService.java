package com.sie.watsons.base.product.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmUserEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductBpmUser;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmProductBpmUserService")
public class PlmProductBpmUserService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBpmUserService.class);

	@Autowired
	private IPlmProductBpmUser plmProductBpmUserServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductBpmUserServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "findByCondition")
	public String findBpmUserByCondition(
			@RequestParam(required = true) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject param = parseObject(params);
			JSONObject queryParamJSON = parseObject(params);
			Pagination<PlmProductBpmUserEntity_HI_RO> results = plmProductBpmUserServer
					.findBpmUserByCondition(queryParamJSON, pageIndex, pageRows);
			queryParamJSON = (JSONObject) JSON.toJSON(results);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findByConditionList")
	public String findByConditionList(
			@RequestParam(required = true) String params) {
		try {
			// JSONObject param = parseObject(params);
			// JSONObject queryParamJSON = parseObject(params);
			// List<PlmProductBpmUserEntity_HI_RO> results =
			// plmProductBpmUserServer.plmProductBpmUserServer(queryParamJSON);
			// queryParamJSON = (JSONObject) JSON.toJSON(results);
			// queryParamJSON.put(SToolUtils.STATUS, "S");
			// queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			// return queryParamJSON.toString();
			JSONObject queryParamJSON = parseObject(params);
			List<PlmProductBpmUserEntity_HI_RO> results = plmProductBpmUserServer
					.plmProductBpmUserServer(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功",
					results.size(), results).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "deletedByCondition")
	public String deletedByCondition(
			@RequestParam(required = true) String params) {
		try {
			JSONObject parameters = this.parseObject(params);
			SaafToolUtils.validateJsonParms(parameters, "seqIds");
			JSONArray ids = parameters.getJSONArray("seqIds");
			List<Integer> headerIds = ids.toJavaList(Integer.class);
			for (Integer headerId : headerIds) {
				plmProductBpmUserServer.deletedByCondition(parameters);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					0, "S").toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveProductBpmUser")
	public String saveProductBpmUser(
			@RequestParam(required = true) String params) {
		try {
			JSONObject paramJson = JSONObject.parseObject(params);
			JSONArray array = paramJson.getJSONArray("bpmUsers");
			for (int i = 0; i < array.size(); i++) {
				JSONObject buJson = array.getJSONObject(i);
				JSONObject jsonObject = parseObject(buJson.toJSONString());
				plmProductBpmUserServer.saveProductBpmUser(jsonObject);
			}

			// SaafToolUtils.validateJsonParms(parameters, "seqIds");
			// JSONObject queryParamJSON = parseObject(params);
			// String results =
			// plmProductBpmUserServer.saveProductBpmUser(queryParamJSON);
			// JSONObject jsonObject = JSONObject.parseObject(results);
			return SToolUtils
					.convertResultJSONObj("S", "保存成功", 1, array.size())
					.toJSONString();
		} catch (Exception e) {
			// LOGGER.error(e.getMessage(), e);
			// return
			// SToolUtils.convertResultJSONObj(ERROR_STATUS,e.getMessage(), 0,
			// null).toString();
			return getException(e, LOGGER);
		}
	}

	public static void main(String[] args) {
		JSONObject AllJSON = new JSONObject(); // 总json
		AllJSON.put("responsibilityId", "990021");
		AllJSON.put("respId", "990021");
		AllJSON.put("processDefinitionKey", "PLM_PRODUCTADD.-999");
		AllJSON.put("saveonly", "false");
		AllJSON.put("responsibilityId", "990021");
		AllJSON.put("orgId", "0");
		AllJSON.put("businessKey", "1");
		AllJSON.put("title", "商品新增");
		AllJSON.put("positionId", "0");
		AllJSON.put("billNo", "1");
		AllJSON.put("userType", "20");

		JSONArray tasks_assignees = new JSONArray();
		JSONArray variables = new JSONArray();
		JSONObject properties = new JSONObject();
		properties.put("menucode", "HTGL");
		properties.put("opinion", "");
		AllJSON.put("extend", tasks_assignees);
		AllJSON.put("variables", variables);
		AllJSON.put("properties", properties);

		System.out.println(AllJSON.toString());
	}

}