package com.sie.watsons.base.questionnaire.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.questionnaire.model.entities.readonly.SaafQuestionnaireLEntity_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.ISaafQuestionnaireL;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/saafQuestionnaireLService")
public class SaafQuestionnaireLService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireLService.class);

	@Autowired
	private ISaafQuestionnaireL saafQuestionnaireLServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.saafQuestionnaireLServer;
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "findSecQuestionnairePagination")
	public String findSecQuestionnairePagination(@RequestParam(required = false) String params, @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject jsonParam = this.parseObject(params);
			Pagination<SaafQuestionnaireLEntity_HI_RO> pagination = saafQuestionnaireLServer.findSecQuestionnairePagination(jsonParam, pageIndex, pageRows);
			return this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, pagination);
		}catch (Exception e) {
			LOGGER.error(".findSecQuestionnairePagination error:{}", e);
		}
		return this.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, null);
	}
	
}