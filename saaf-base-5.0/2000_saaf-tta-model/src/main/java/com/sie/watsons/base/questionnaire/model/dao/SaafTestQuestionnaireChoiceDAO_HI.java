package com.sie.watsons.base.questionnaire.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireChoiceEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("saafTestQuestionnaireChoiceDAO_HI")
public class SaafTestQuestionnaireChoiceDAO_HI extends BaseCommonDAO_HI<SaafTestQuestionnaireChoiceEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafTestQuestionnaireChoiceDAO_HI.class);

	public SaafTestQuestionnaireChoiceDAO_HI() {
		super();
	}

}
