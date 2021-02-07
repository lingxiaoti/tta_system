package com.sie.watsons.base.questionnaire.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireHEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("saafQuestionnaireHDAO_HI")
public class SaafQuestionnaireHDAO_HI extends BaseCommonDAO_HI<SaafQuestionnaireHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireHDAO_HI.class);

	public SaafQuestionnaireHDAO_HI() {
		super();
	}

}
