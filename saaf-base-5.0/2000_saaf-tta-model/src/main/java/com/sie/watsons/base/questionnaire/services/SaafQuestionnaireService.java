package com.sie.watsons.base.questionnaire.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireHEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ISaafQuestionnaire;
import com.sie.watsons.base.questionnaire.model.inter.server.SaafTestQuestionnaireHServer;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("/saafQuestionnaireService")
public class SaafQuestionnaireService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireService.class);

	@Autowired
	private ISaafQuestionnaire saafQuestionnaireServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.saafQuestionnaireServer;
	}

	@Autowired
	private SaafTestQuestionnaireHServer saafTestQuestionnaireHServer;

	/**
	 * 功能描述：初始化问卷操作
	 * @author xiaoga
	 * @date 2019/6/17
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "initTestQuestionnaireToCopy")
	public String initTestQuestionnaireToCopy(@RequestParam(required = true) String params){
			try {
				JSONObject jsonParam = this.parseObject(params);
				return saafQuestionnaireServer.saveTestQuestionnaireToCopy(jsonParam).toJSONString();
			} catch (Exception e) {
				LOGGER.error(".initTestQuestionnaireToCopy error:{}", e);
				return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e).toJSONString();
			}
	}
	
	/**
	 * 功能描述： 通过题目和选项查询对应规则的题目列表
	 * @author xiaoga
	 * @date 2019/6/17
	 * @param  
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findLoadRuleTestQuestionnaire")
	public String findLoadRuleTestQuestionnaire(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			JSONObject result = saafQuestionnaireServer.findLoadRuleTestQuestionnaire(jsonObject);
			LOGGER.info(".findLoadRuleTestQuestionnaire info: {}" , result);
			return  result.toJSONString();
		} catch (Exception e) {
			LOGGER.error(".findLoadRuleTestQuestionnaire error: {}", e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e).toJSONString();
		}
	}

	/**
	 * 查询
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnaireList")
	public String findSaafQuestionnaireList(@RequestParam(required = false) String params, @RequestParam(required = false) Integer pageIndex,
											@RequestParam(required = false) Integer pageRows) {
		    JSONObject jsonParam = this.parseObject(params);
			Pagination<SaafQuestionnaireHEntity_HI_RO> respList = saafQuestionnaireServer.findSaafQuestionnaireList(jsonParam, pageIndex, pageRows);
			JSONObject  results=JSONObject.parseObject(JSON.toJSONString(respList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	/**
	 * 通过instId查询用户信息
	 * @param params
	 * @param
	 * @return
	 */

//	@RequestMapping(method = RequestMethod.POST, value = "findUserByInstIdList")
//	public String findUserByInstIdList(@RequestParam(required = false) String params) {
//		    JSONObject jsonParam = this.parseObject(params);
//			List<SaafQuestionnaireHEntity_HI_RO> respList = saafQuestionnaireServer.findUserByInstIdList(jsonParam);
//			JSONObject results = JSONObject.parseObject(JSON.toJSONString(respList));
//			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
//			results.put(SToolUtils.MSG, "成功");
//			return results.toString();
//	}

	@RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnaireById")
	public String findSaafQuestionnaireById(@RequestParam(required = false) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results = saafQuestionnaireServer.findSaafQuestionnaireById(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}


	@RequestMapping(method = RequestMethod.POST, value = "findTestQuestionnaireByProposalId")
	public String findTestQuestionnaireByProposalId(@RequestParam(required = false) String params){
		JSONObject jsonParam = this.parseObject(params);
		JSONObject results = saafTestQuestionnaireHServer.findTestQuestionnaireByProposalId(jsonParam);
		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
		results.put(SToolUtils.MSG, "成功");
		return results.toString();
	}
	
    @RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnaire")
    public String findSaafQuestionnaire(
    		@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination<SaafQuestionnaireLEntity_HI> findList = saafQuestionnaireServer
            		.findSaafQuestionnaire(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toJSONString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnaires")
    public String findSaafQuestionnaires(
    		@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject queryParamJSON = parseObject(params);
            Pagination<SaafQuestionnaireLEntity_HI> findList = saafQuestionnaireServer
            		.findSaafQuestionnaire(queryParamJSON, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toJSONString();
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        	return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
        }
    }

	@RequestMapping(method = RequestMethod.POST, value = "saveSaafQuestionnaireH")
	public String saveSaafQuestionnaireH(@RequestParam(required = true) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results = saafQuestionnaireServer.saveSaafQuestionnaireH(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "deleteSaafQuestionnaireH")
	public String deleteSaafQuestionnaireH(@RequestParam(required = true) String params){
		 	JSONObject jsonParam = this.parseObject(params);
			JSONObject results = saafQuestionnaireServer.deleteSaafQuestionnaireH(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "updateSaafQuestionnaireToAbandon")
	public String updateSaafQuestionnaireToAbandon(@RequestParam(required = true) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results = saafQuestionnaireServer.updateSaafQuestionnairePublishToAbandon(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "updateSaafQuestionnaireToCopy")
	public String updateSaafQuestionnaireToCopy(@RequestParam(required = true) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results = saafQuestionnaireServer.updateSaafQuestionnaireToCopy(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveSaafQuestionnaireL")
	public String saveSaafQuestionnaireL(@RequestParam(required = true) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results = saafQuestionnaireServer.saveSaafQuestionnaireL(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "deleteSaafQuestionnaireL")
	public String deleteSaafQuestionnaireL(@RequestParam(required = true) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results = saafQuestionnaireServer.deleteSaafQuestionnaireL(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "findSelectProjectList")
	public String findSelectProjectList(@RequestParam(required = false) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results = saafQuestionnaireServer.findSelectProjectList(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}



	@RequestMapping(method = RequestMethod.POST, value = "deleteSurveyChoice")
	public String deleteQnChoice(@RequestParam(required = false) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results= saafQuestionnaireServer.deleteQnChoice(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveSaafQuestionnaireResult")
	public String saveSaafQuestionnaireResult(@RequestParam(required = false) String params) {
		JSONObject results = new JSONObject();
		JSONObject jsonParam1 = this.parseObject(params);
		results = saafQuestionnaireServer.saveSaafQuestionnaireResult(jsonParam1);
		return results.toJSONString();
	}


	@RequestMapping(method = RequestMethod.POST, value = "updateSaafQuestionnairePublishToPublish")
	public String updateSaafQuestionnairePublishToPublish(@RequestParam(required = false) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results= saafQuestionnaireServer.updateSaafQuestionnairePublishToPublish(jsonParam);
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "deleteSaafQuestionnairePublish")
	public String deleteSaafQuestionnairePublish(@RequestParam(required = false) String params){
		JSONObject jsonParam = this.parseObject(params);
		JSONObject results= saafQuestionnaireServer.deleteSaafQuestionnairePublish(jsonParam);
//		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
//		results.put(SToolUtils.MSG, "成功");
		return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "saveSaafQuestionnairePublish")
	public String saveSaafQuestionnairePublish(@RequestParam(required = false) String params){
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results= saafQuestionnaireServer.saveSaafQuestionnairePublish(jsonParam);
		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
		results.put(SToolUtils.MSG, "成功");
		return results.toString();
	}

	/**
	 * 查询列表
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnairePublishList")
	public String findSaafQuestionnairePublishList(@RequestParam(required = false) String params, @RequestParam(required = false)Integer pageIndex,
												   @RequestParam(required = false) Integer pageRows) {
		try {
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results= saafQuestionnaireServer.findSaafQuestionnairePublishList(jsonParam, pageIndex, pageRows);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 移动端查询问卷调查列表
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnaireListByMobile")
	public String findSaafQuestionnaireListByMobile(@RequestParam(required = false) String params) {
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results= saafQuestionnaireServer.findSaafQuestionnaireListByMobile(jsonParam);
			return results.toString();
	}

	/**
	 * 移动端查询问卷调查列表
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnaireByPublishId")
	public String findSaafQuestionnaireByPublishId(@RequestParam(required = false) String params) {
			JSONObject jsonParam = JSON.parseObject(params);
			JSONObject results= saafQuestionnaireServer.findSaafQuestionnaireByPublishId(jsonParam);
			return results.toString();
	}


	/**
	 * 通过publishId获取问卷调查答卷人信息
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findResultPerson")
	public String findResultPerson(@RequestParam(required = false) String params) {
			JSONObject jsonParam = JSON.parseObject(params);
			List<SaafQuestionnaireHEntity_HI_RO> list = saafQuestionnaireServer.findResultPerson(jsonParam);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, list.size(), list).toString();
//			return convertResultJSONObj(STATUS,SUCCESS_STATUS,list);
//			JSONObject results= JSONObject.parseObject(JSON.toJSONString(list));
//			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
//			results.put(SToolUtils.MSG, "成功");
//			return results.toString();
	}


	/**
	 * 通过publishId获取问卷调查答卷人信息
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findAnswerResultList")
	public String findAnswerResultList(@RequestParam(required = false) String params) {
		JSONObject jsonParam = JSON.parseObject(params);
		List<SaafQuestionnaireHEntity_HI_RO> list = saafQuestionnaireServer.findAnswerResultList(jsonParam);
//		JSONObject results = JSONObject.parseObject(JSON.toJSONString(list));
//		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
//		results.put(SToolUtils.MSG, "成功");
//		return results.toString();
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, list.size(), list).toString();
	}


	/**
	 * 通过publishId获取问卷调查答卷人信息
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnaireResult")
	public String findSaafQuestionnaireResult(@RequestParam(required = false) String params) {
		JSONObject jsonParam = JSON.parseObject(params);
		JSONObject results= saafQuestionnaireServer.findSaafQuestionnaireResult(jsonParam);
		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
		results.put(SToolUtils.MSG, "成功");
		return results.toString();
//		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, results.size(), results).toString();
	}

	/**
	 * 通过publishId获取问卷调查答卷人信息
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSaafQuestionnaireResultByUserId")
	public String findSaafQuestionnaireResultByUserId(@RequestParam(required = false) String params) {
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results= saafQuestionnaireServer.findSaafQuestionnaireResultByUserId(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	/**
	 * 通过publishId获取问卷调查报表
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findResultReport")
	public String findResultReport(@RequestParam(required = false) String params) {
			JSONObject jsonParam = JSON.parseObject(params);
			JSONObject results= saafQuestionnaireServer.findResultReport(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

//	/**
//	 * 发起审批
//	 * @param params
//	 * @param
//	 * @return
//	 */
//	@RequestMapping(method = RequestMethod.POST, value = "saveSubmitSaafQuestionnaire")
//	public String saveSubmitSaafQuestionnaire(@RequestParam(required = false) String params) {
//		try {
//			JSONObject jsonParam = this.parseObject(params);
//			JSONObject results= saafQuestionnaireServer.saveSubmitSaafQuestionnaire(jsonParam);
//			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
//			results.put(SToolUtils.MSG, "成功");
//			return results.toString();
//	}

	/**
	 * 修改状态
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateSaafQuestionnaireStatus")
	public String updateSaafQuestionnaireStatus(@RequestParam(required = false) String params) {
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results= saafQuestionnaireServer.updateSaafQuestionnaireStatus(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

//	/**
//	 * 发布发起审批
//	 * @param params
//	 * @param
//	 * @return
//	 */
//	@Path("saveSubmitSaafQuestionnairePublish")
//	@RequestMapping(method = RequestMethod.POST, value = "")
//	public String saveSubmitSaafQuestionnairePublish(@RequestParam(required = false) String params) {
//		try {
//			JSONObject jsonParam = this.parseObject(params);
//			JSONObject results= saafQuestionnaireServer.saveSubmitSaafQuestionnairePublish(jsonParam);
//			return JSON.toJSONString(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error("发起审批失败:" + e);
//			return CommonAbstractServices.convertResultJSONObj("E", "发起审批失败!" + e, 0, null);
//		}
//	}

	/**
	 * 修改状态
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateSaafQuestionnairePublishStatus")
	public String updateSaafQuestionnairePublishStatus(@RequestParam(required = false) String params) {
			JSONObject jsonParam = this.parseObject(params);
			JSONObject results= saafQuestionnaireServer.updateSaafQuestionnairePublishStatus(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "findReportToExport")
	public String findReportToExport(@RequestParam(required = false) String params, HttpServletRequest servletRequest,HttpServletResponse servletResponse){
			JSONObject parameters = JSONObject.parseObject(params);
			saafQuestionnaireServer.findReportToExport(parameters,servletRequest,servletResponse);
			JSONObject results= new JSONObject();
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "findQuestionnaireLAndQuestionnaireChoice")
	public String findQuestionnaireLAndQuestionnaireChoice(@RequestParam(required = false) String params, @RequestParam(required = false) Integer pageIndex,
														   @RequestParam(required = false) Integer pageRows) {
		JSONObject jsonParam = this.parseObject(params);
		JSONObject results = new JSONObject();
		Object obj = saafQuestionnaireServer.findQuestionnaireLAndQuestionnaireChoice(jsonParam,pageIndex,pageRows);
		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
		results.put(SToolUtils.MSG, "成功");
		results.put(DATA,obj);


		return convertResultJSONObj(SUCCESS_STATUS,SUCCESS_MSG,obj);

//		return results.toString();
	}
}