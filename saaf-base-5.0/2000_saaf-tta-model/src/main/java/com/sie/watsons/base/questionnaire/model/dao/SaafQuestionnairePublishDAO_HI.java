package com.sie.watsons.base.questionnaire.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnairePublishEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("saafQuestionnairePublishDAO_HI")
public class SaafQuestionnairePublishDAO_HI extends BaseCommonDAO_HI<SaafQuestionnairePublishEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnairePublishDAO_HI.class);

	public SaafQuestionnairePublishDAO_HI() {
		super();
	}

}
