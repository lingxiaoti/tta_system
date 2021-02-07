package com.sie.watsons.base.questionnaire.model.dao.readonly;

import org.springframework.stereotype.Component;

import com.sie.watsons.base.questionnaire.model.entities.readonly.RulePerson_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ruleDAO_HI_RO")
public class RuleDAO_HI_RO extends DynamicViewObjectImpl<RulePerson_HI_RO> {
	public RuleDAO_HI_RO() {
		super();
	}

}
