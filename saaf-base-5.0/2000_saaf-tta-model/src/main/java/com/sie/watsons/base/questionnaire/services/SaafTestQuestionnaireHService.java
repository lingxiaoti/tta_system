package com.sie.watsons.base.questionnaire.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafTestQuestionnaireHEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ISaafTestQuestionnaireH;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
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
@RequestMapping("/saafTestQuestionnaireHService")
public class SaafTestQuestionnaireHService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafTestQuestionnaireHService.class);

	@Autowired
	private ISaafTestQuestionnaireH saafTestQuestionnaireHServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.saafTestQuestionnaireHServer;
	}

	/**
	 * 查询
	 * @param params
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSaafTestQuestionnaireList")
	public String findSaafTestQuestionnaireList(@RequestParam(required = false) String params, @RequestParam(required = false) Integer pageIndex,@RequestParam(required = false) Integer pageRows) {
		JSONObject jsonParam = this.parseObject(params);
		Pagination<SaafTestQuestionnaireHEntity_HI_RO> respList = saafTestQuestionnaireHServer.findSaafTestQuestionnaireList(jsonParam, pageIndex, pageRows);
		JSONObject  results=JSONObject.parseObject(JSON.toJSONString(respList));
		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
		results.put(SToolUtils.MSG, "成功");
		return results.toString();
	}

	/**
	 * 问卷保存
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSaafTestQuestionnaireH")
	public String saveSaafTestQuestionnaireH(@RequestParam(required = true) String params){
		JSONObject jsonParam = this.parseObject(params);
		JSONObject results = null;
		try {
			results = saafTestQuestionnaireHServer.saveSaafTestQuestionnaireH(jsonParam);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			return convertResultJSONObj(ERROR_STATUS,ERROR_MSG,e.getMessage());
		}
	}

	/**
	 * 问卷行保存
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveSaafTestQuestionnaireL")
	public String saveSaafTestQuestionnaireL(@RequestParam(required = true) String params){
		JSONObject jsonParam = this.parseObject(params);
		JSONObject results = null;
		try {
			boolean flag = saafTestQuestionnaireHServer.saveSaafTestQuestionnaireL(jsonParam);
			if (flag){
				return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
			}
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
		} catch (Exception e) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询行及choice
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findLineAndchoice")
	public String findLineAndchoice(@RequestParam(required = true) String params){
		JSONObject jsonParam = this.parseObject(params);
		try {
			List<SaafTestQuestionnaireHEntity_HI_RO> list = saafTestQuestionnaireHServer.findLineAndchoice(jsonParam);
//			return convertResultJSONObj(SUCCESS_STATUS,SUCCESS_MSG,list);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, list.size(), list).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return convertResultJSONObj(ERROR_STATUS,ERROR_MSG,e.getMessage());
		}
	}

	/**
	 * 删除行
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteLine")
	public String deleteLine(@RequestParam(required = true) String params){
		JSONObject jsonParam = this.parseObject(params);
		try {
			boolean s = saafTestQuestionnaireHServer.deleteLine(jsonParam);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return convertResultJSONObj(ERROR_STATUS,ERROR_MSG,e.getMessage());
		}
	}

	/**
	 * 查询问卷调查列表 LOV
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSaafTestQuestionnaireLov")
	public String findSaafTestQuestionnaireLov(@RequestParam(required = false) String params, @RequestParam(required = false) Integer pageIndex,@RequestParam(required = false) Integer pageRows) {
		JSONObject jsonParam = this.parseObject(params);
		Pagination<SaafTestQuestionnaireHEntity_HI_RO> respList = saafTestQuestionnaireHServer.findSaafTestQuestionnaireLov(jsonParam, pageIndex, pageRows);
		JSONObject  results=JSONObject.parseObject(JSON.toJSONString(respList));
		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
		results.put(SToolUtils.MSG, "成功");
		return results.toString();
	}

}