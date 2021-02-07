package com.sie.watsons.base.questionnaire.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.questionnaire.model.entities.RuleEntity_HI;

@Component("ruleDAO_HI")
public class RuleDAO_HI extends BaseCommonDAO_HI<RuleEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleDAO_HI.class);

	public RuleDAO_HI() {
		super();
	}

}
