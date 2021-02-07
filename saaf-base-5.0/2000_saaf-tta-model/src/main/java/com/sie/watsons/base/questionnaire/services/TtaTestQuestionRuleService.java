package com.sie.watsons.base.questionnaire.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionNewMapDetailEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.TtaTestQuestionHeaderEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionHeaderEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionChoiceLine;
import com.sie.watsons.base.questionnaire.model.inter.ITtaTestQuestionChoiceLine;
import com.sie.watsons.base.questionnaire.model.inter.ITtaTestQuestionHeader;
import com.sie.watsons.base.supplement.model.inter.ITtaSideAgrtHeader;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/ttaTestQuestionChoiceLineService")
public class TtaTestQuestionRuleService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTestQuestionRuleService.class);

	@Autowired
	private ITtaTestQuestionChoiceLine ttaTestQuestionChoiceLineServer;
	
	@Autowired
	private ITtaTestQuestionHeader ITtaTestQuestionHeader;

	@Autowired
	private ITtaSideAgrtHeader ttaSideAgrtHeaderServer;
	

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaTestQuestionChoiceLineServer;
	}

	@Autowired
	private ITtaQuestionChoiceLine ttaQuestionChoiceLineServer;

	@Autowired
	private ITtaProposalHeader ttaProposalHeaderServer;

	/**
	 * 初始化问卷
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveTestQuestionnaireToCopy")
	public String saveTestQuestionnaireToCopy(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonParam = this.parseObject(params);
			ITtaTestQuestionHeader.saveTestQuestionnaireToCopy(jsonParam);
			LOGGER.info(".saveTestQuestionnaireToCopy params:{}", params);
			return this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null);
		} catch (Exception e) {
			LOGGER.error(".saveTestQuestionnaireToCopy:{}", e);
			return this.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, null);
		}
	}

	/**
	 * 功能描述： 递归调用显示模板数据
	 * @author xiaoga
	 * @date 2019/8/9
	 * @param  
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findRecQuestionHeaderList")
	public String findRecQuestionHeaderList(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonResult = new JSONObject();
			Object  ttaQuestionListObject = null;
			JSONObject jsonObject = this.parseObject(params);
			Integer proposalId = jsonObject.getInteger("proposalId");
			//1.判断ttaterms 是否确认
			TtaProposalHeaderEntity_HI proposalEntity = ttaProposalHeaderServer.getById(proposalId);
			if (proposalEntity == null || !"Y".equalsIgnoreCase(proposalEntity.getIsTermsConfirm())) {
				return SToolUtils.convertResultJSONObj("E", "请先确认TTA Terms！", 0, null).toString();
			}
			List<TtaTestQuestionHeaderEntity_HI> ttaQuestionList = ITtaTestQuestionHeader.findList(new JSONObject().fluentPut("proposalId", proposalId));
			if (ttaQuestionList == null || ttaQuestionList.isEmpty()) {
				ttaQuestionListObject = ttaQuestionChoiceLineServer.findRecQuestionHeaderList(jsonObject);
				ttaQuestionChoiceLineServer.saveInitQuestion((List<TtaQuestionHeaderEntity_HI_RO>)ttaQuestionListObject, proposalId, proposalEntity.getProposalYear());
			}
			ttaQuestionListObject = ttaQuestionChoiceLineServer.findTestQuestionList(jsonObject);
			List<TtaProposalHeaderEntity_HI> proposalIdEntity = ttaProposalHeaderServer.findList(new JSONObject().fluentPut("proposalId", proposalId));
			String isQuestConfirm = null;
			if (proposalIdEntity != null &&  !proposalIdEntity.isEmpty()) {
				isQuestConfirm = proposalIdEntity.get(0).getIsQuestConfirm();
			}
			jsonResult.fluentPut("isQuestConfirm", isQuestConfirm);
			jsonResult.fluentPut("questionData", ttaQuestionListObject);
			String result = SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonResult).toString();
			LOGGER.info(".findRecQuestionHeaderList入参:{} , 出参：", params, result);
			return result;
		} catch (Exception e) {
			LOGGER.error("findRecQuestionHeaderList:{}", e);
			return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "getEnglish")
	public String getEnglish(@RequestParam(required = true) String params) {
		JSONObject parseObject = this.parseObject(params);
		Integer proposalId = parseObject.getInteger("proposalId");
		return ttaQuestionChoiceLineServer.findQuestionEnglishRemark(proposalId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "submitQuestionTest")
	public String submitQuestionTest(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer proposalId = jsonObject.getInteger("proposalId");
			TtaProposalHeaderEntity_HI entityHi = ttaProposalHeaderServer.getById(proposalId);
			String status = jsonObject.getString("status");
			JSONArray lineArr = jsonObject.getJSONArray("lineArr");
			List<TtaQuestionNewMapDetailEntity_HI> newMapList = JSON.parseArray(lineArr.toJSONString(), TtaQuestionNewMapDetailEntity_HI.class);
			//提交状态且为新供应商.
			if ("2".equalsIgnoreCase(status) && "New_Vendor".equalsIgnoreCase(entityHi.getNewOrExisting())){
				//1.需提供全新产品清单
				if (newMapList == null || newMapList.isEmpty()) {
					return SToolUtils.convertResultJSONObj("E", "确认失败，需提供全新产品清单。", 0, null).toString();
				}
				for (TtaQuestionNewMapDetailEntity_HI entity : newMapList) {
					BigDecimal cost = entity.getCost();
					String normalGp = entity.getNormalGp();
					BigDecimal pomoPrice = entity.getPromoPrice();
					String remark = entity.getRemark();
					BigDecimal retailPrice = entity.getRetailPrice();
                    String sukuDesc = entity.getSkuDesc();
					String promoGp = entity.getPromoGp();

					if (cost == null || normalGp == null || pomoPrice == null
							|| retailPrice == null ||  sukuDesc == null || promoGp == null) {
						return SToolUtils.convertResultJSONObj("E", "确认失败,全新产品需要提供产品清单有为空的行。", 0, null).toString();
					}
				}
				//2.需上传全新产品附件
				JSONObject queryMap = new JSONObject();
				queryMap.fluentPut("functionId", "tta_proposal_header").fluentPut("businessId", proposalId);
				Pagination<BaseAttachmentEntity_HI_RO> baseAttachmentEntity = ttaSideAgrtHeaderServer.findBaseAttachmentEntity(queryMap, 0, 10);
				if (baseAttachmentEntity == null || baseAttachmentEntity.getData() == null || baseAttachmentEntity.getData().size() == 0) {
					return SToolUtils.convertResultJSONObj("E", "确认失败，需上传全新产品附件。", 0, null).toString();
				}
			}
			ttaQuestionChoiceLineServer.saveSubmitQuestion(jsonObject, newMapList);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("submitQuestionTest:{}", e);
			return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
		}
	}
	/**
	 * 功能描述： 问卷取消确认
	 * @author xiaoga
	 * @date 2019/8/11
	 * @param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "cancelQuestionTest")
	public String cancelQuestionTest(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			Integer proposalId = jsonObject.getInteger("proposalId");
			ttaQuestionChoiceLineServer.deleteCancelQuestionTest(proposalId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_STATUS, 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("cancelQuestionTest:{}", e);
			return SToolUtils.convertResultJSONObj("E", ERROR_MSG, 0, null).toString();
		}
	}
}