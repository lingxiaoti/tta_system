package com.sie.watsons.base.questionnaire.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionChoiceLineEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionChoiceLineEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionChoiceLine;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionHeader;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/ttaQuestionChoiceLineService")
public class TtaQuestionChoiceLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaQuestionChoiceLineService.class);

	@Autowired
	private ITtaQuestionChoiceLine ttaQuestionChoiceLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaQuestionChoiceLineServer;
	}
	
	@Autowired
	private ITtaQuestionHeader iTtaQuestionHeader;
	
	/**
	 * 保存问卷行信息
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveQuestionChoiceLine")
	public String savequestionChoiceLine(@RequestParam(required = true) String params){
		try {
			JSONObject jsonParam = this.parseObject(params);
			TtaQuestionChoiceLineEntity_HI entity = JSON.parseObject(jsonParam.toJSONString(), TtaQuestionChoiceLineEntity_HI.class);
			ttaQuestionChoiceLineServer.saveOrUpdateChoiceLine(entity);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entity).toString();
		} catch (Exception e) {
			LOGGER.error("savequestionChoiceLine:{}", e);
			return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
		}
	}
	
	/**
	 *  通过父id查询下层选项信息
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findQuestionChoiceLineChild")
	public String findQuestionChoiceLineChild(@RequestParam(required = true) String params){
		try {
			JSONObject jsonParam = this.parseObject(params);
			List<TtaQuestionChoiceLineEntity_HI_RO>  entityList = ttaQuestionChoiceLineServer.findQuestionChoiceLineChild(jsonParam);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entityList).toString();
		} catch (Exception e) {
			LOGGER.error("findQuestionChoiceLineChild:{}", e);
			return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpadateChoiceLineList")
	 public String saveOrUpdateChild(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			JSONObject queryJson = new JSONObject();
			ttaQuestionChoiceLineServer.saveOrUpadateChoiceLineList(jsonObject);
			queryJson.fluentPut("qHeaderId", jsonObject.getInteger("qHeaderId")).fluentPut("parentId",jsonObject.getInteger("parentId"));
			List<TtaQuestionChoiceLineEntity_HI_RO> entityList = ttaQuestionChoiceLineServer.findQuestionChoiceLineChild(queryJson);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entityList).toString();
		} catch (Exception e) {
			LOGGER.error("saveOrUpdateChild:{}", e);
			return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
		}
	 }



}