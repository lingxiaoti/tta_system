package com.sie.watsons.base.rule.model.dao;

import com.sie.watsons.base.rule.model.entities.RuleFileTemplateEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;

@Component("ruleFileTemplateDAO_HI")
public class RuleFileTemplateDAO_HI extends BaseCommonDAO_HI<RuleFileTemplateEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleFileTemplateDAO_HI.class);

	public RuleFileTemplateDAO_HI() {
		super();
	}

}
