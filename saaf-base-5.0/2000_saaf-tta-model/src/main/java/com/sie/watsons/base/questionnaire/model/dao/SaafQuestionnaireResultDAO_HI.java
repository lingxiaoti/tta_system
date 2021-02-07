package com.sie.watsons.base.questionnaire.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireResultEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("saafQuestionnaireResultDAO_HI")
public class SaafQuestionnaireResultDAO_HI extends BaseCommonDAO_HI<SaafQuestionnaireResultEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireResultDAO_HI.class);

	public SaafQuestionnaireResultDAO_HI() {
		super();
	}

}
