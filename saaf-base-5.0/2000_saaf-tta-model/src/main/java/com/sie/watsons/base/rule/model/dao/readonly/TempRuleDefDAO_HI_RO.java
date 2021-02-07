package com.sie.watsons.base.rule.model.dao.readonly;

import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("tempRuleDefDAO_HI_RO")
public class TempRuleDefDAO_HI_RO extends DynamicViewObjectImpl<TempRuleDefEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TempRuleDefDAO_HI_RO.class);

	public TempRuleDefDAO_HI_RO() {
		super();
	}

}
