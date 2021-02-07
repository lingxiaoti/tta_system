package com.sie.watsons.base.rule.model.dao;

import com.sie.watsons.base.rule.model.entities.TempRuleDefEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;

@Component("tempRuleDefDAO_HI")
public class TempRuleDefDAO_HI extends BaseCommonDAO_HI<TempRuleDefEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TempRuleDefDAO_HI.class);

	public TempRuleDefDAO_HI() {
		super();
	}

}
