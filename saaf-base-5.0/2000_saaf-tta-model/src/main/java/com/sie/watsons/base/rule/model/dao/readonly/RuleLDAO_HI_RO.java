package com.sie.watsons.base.rule.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.rule.model.entities.readonly.RuleLEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ruleLDAO_HI_RO")
public class RuleLDAO_HI_RO extends DynamicViewObjectImpl<RuleLEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleLDAO_HI_RO.class);

	public RuleLDAO_HI_RO() {
		super();
	}

}
