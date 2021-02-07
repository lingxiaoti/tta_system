package com.sie.watsons.base.rule.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.rule.model.entities.readonly.RuleHEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ruleHDAO_HI_RO")
public class RuleHDAO_HI_RO extends DynamicViewObjectImpl<RuleHEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleHDAO_HI_RO.class);

	public RuleHDAO_HI_RO() {
		super();
	}

}
