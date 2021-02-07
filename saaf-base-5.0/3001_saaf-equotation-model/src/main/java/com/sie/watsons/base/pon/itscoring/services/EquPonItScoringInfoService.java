package com.sie.watsons.base.pon.itscoring.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringInfoEntity_HI;
import com.sie.watsons.base.pon.itscoring.model.inter.IEquPonItScoringInfo;

import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/equPonItScoringInfoService")
public class EquPonItScoringInfoService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringInfoService.class);

	@Autowired
	private IEquPonItScoringInfo equPonItScoringInfoServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPonItScoringInfoServer;
	}

	/**
	 * 报价管理-评分单据查询，分页查询
	 *
	 * @param params    参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItScoringInfo")
	public String findItScoringInfo(@RequestParam(required = false) String params,
								  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
								  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS = parseObject(params);
			JSONObject result = equPonItScoringInfoServer.findScoringInfo(paramsJONS, pageIndex, pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("scoringStatus");
			incomingParam.add("projectStatus");
			incomingParam.add("relevantCatetory");
			incomingParam.add("projectCategory");
			incomingParam.add("sensoryTestTypes");
			efferentParam.add("scoringStatusMeaning");
			efferentParam.add("projectStatusName");
			efferentParam.add("relevantCatetoryMeaning");
			efferentParam.add("projectCategoryMeaning");
			efferentParam.add("sensoryTestTypesMeaning");
			typeParam.add("EQU_SCORING_ORDER_STATUS");
			typeParam.add("EQU_PON_PROJECT_STATUS");
			typeParam.add("EQU_PROJECT_CATEGORY");
			typeParam.add("EQU_PROJECT_TYPE");
			typeParam.add("EQU_SENSORY_TEST_TYPE");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
			result.put("data", data);
			result.put(SToolUtils.STATUS, SUCCESS_STATUS);
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
	 * 报价管理-评分单据查询，流程查询
	 *
	 * @param params    参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItScoringInfoForFlow")
	public String findScoringInfoForFlow(@RequestParam(required = false) String params,
										 @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
										 @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS = parseObject(params);
			if(paramsJONS.containsKey("id")){
				paramsJONS.put("scoringId",paramsJONS.getInteger("id"));
			}
			JSONObject result = equPonItScoringInfoServer.findScoringInfoForFlow(paramsJONS, pageIndex, pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("scoringStatus");
			incomingParam.add("projectStatus");
			incomingParam.add("relevantCatetory");
			incomingParam.add("projectCategory");
			incomingParam.add("sensoryTestTypes");
			efferentParam.add("scoringStatusMeaning");
			efferentParam.add("projectStatusName");
			efferentParam.add("relevantCatetoryMeaning");
			efferentParam.add("projectCategoryMeaning");
			efferentParam.add("sensoryTestTypesMeaning");
			typeParam.add("EQU_SCORING_ORDER_STATUS");
			typeParam.add("EQU_PON_PROJECT_STATUS");
			typeParam.add("EQU_PROJECT_CATEGORY");
			typeParam.add("EQU_PROJECT_TYPE");
			typeParam.add("EQU_SENSORY_TEST_TYPE");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
			result.put("data", data);
			result.put(SToolUtils.STATUS, SUCCESS_STATUS);
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();

//			result.put(SToolUtils.STATUS, "S");
//			result.put(SToolUtils.MSG, SUCCESS_MSG);
//			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 报价管理-评分单据保存
	 *
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveItScoringInfo")
	public String saveItScoringInfo(@RequestParam(required = false) String params) {
		JSONObject jsonObject = null;
		try{
			jsonObject = parseObject(params);
		}catch(JSONException ex){
			jsonObject = parseObject("{\"scoringHeaderInfo\":"+ params.substring(1,params.length()-1) + "}");
		}


		try {
//            JSONObject jsonObject = parseObject(params);
			EquPonItScoringInfoEntity_HI instance = equPonItScoringInfoServer.saveScoringInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 报价管理-评分单据删除
	 *
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteItScoringInfo")
	public String deleteItScoringInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			Integer listId = equPonItScoringInfoServer.deleteScoringInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, listId).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 报价管理-评分单据删除
	 *
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "checkItQuotationStatus")
	public String checkItQuotationStatus(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonItScoringInfoServer.checkQuotationStatus(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveItNonPriceCalculate")
	public String saveItNonPriceCalculate(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonItScoringInfoServer.saveNonPriceCalculate(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveItQuotationScoreCalculate")
	public String saveItQuotationScoreCalculate(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			equPonItScoringInfoServer.saveQuotationScoreCalculate(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询报价资料开启LOV
	 *
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows  每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItPonInformationInfoLov")
	public String findItPonInformationInfoLov(@RequestParam(required = false) String params,
											@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS = this.parseJson(params);
			JSONObject result = equPonItScoringInfoServer.findPonInformationInfoLov(paramsJONS, pageIndex, pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("informationStatus");
			incomingParam.add("projectStatus");
			incomingParam.add("projectCategory");
			incomingParam.add("sensoryTestTypes");
			efferentParam.add("informationStatusName");
			efferentParam.add("projectStatusName");
			efferentParam.add("projectCategoryName");
			efferentParam.add("sensoryTestTypesName");
			typeParam.add("EQU_PON_QUOTATION_STATUS");
			typeParam.add("EQU_PON_PROJECT_STATUS");
			typeParam.add("EQU_PROJECT_TYPE");
			typeParam.add("EQU_SENSORY_TEST_TYPE");
			JSONObject returnJson = (JSONObject) JSON.toJSON(result);
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues(data, incomingParam, efferentParam, typeParam);
			returnJson.put("data", data);
			returnJson.put("status", "S");
			return JSON.toJSONString(returnJson);

//			result.put(SToolUtils.STATUS, "S");
//			result.put(SToolUtils.MSG, SUCCESS_MSG);
//			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 评分单据审批回调接口
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "itScoringApprovalCallback")
	public String itScoringApprovalCallback(@RequestParam(required = false) String params) {
		try {
			System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPonItScoringInfoEntity_HI entity = equPonItScoringInfoServer.scoringApprovalCallback(paramsObject, userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}