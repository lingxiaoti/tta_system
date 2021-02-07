package com.sie.watsons.base.rule.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.rule.model.entities.RuleLEntity_HI;

@Component("ruleLDAO_HI")
public class RuleLDAO_HI extends BaseCommonDAO_HI<RuleLEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleLDAO_HI.class);

	public RuleLDAO_HI() {
		super();
	}

}
