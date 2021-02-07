package com.sie.watsons.base.questionnaire.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.SaafQuestionnaireLEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("saafQuestionnaireLDAO_HI")
public class SaafQuestionnaireLDAO_HI extends BaseCommonDAO_HI<SaafQuestionnaireLEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SaafQuestionnaireLDAO_HI.class);

	public SaafQuestionnaireLDAO_HI() {
		super();
	}

}
