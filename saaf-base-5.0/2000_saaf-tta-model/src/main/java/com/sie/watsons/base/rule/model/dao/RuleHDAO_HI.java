package com.sie.watsons.base.rule.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.rule.model.entities.RuleHEntity_HI;

@Component("ruleHDAO_HI")
public class RuleHDAO_HI extends BaseCommonDAO_HI<RuleHEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleHDAO_HI.class);

	public RuleHDAO_HI() {
		super();
	}

}
