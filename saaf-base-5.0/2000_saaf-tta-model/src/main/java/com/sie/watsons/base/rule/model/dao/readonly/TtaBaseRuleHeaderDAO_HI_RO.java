package com.sie.watsons.base.rule.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.rule.model.entities.readonly.TtaBaseRuleHeaderEntity_HI_RO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaBaseRuleHeaderDAO_HI_RO")
public class TtaBaseRuleHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaBaseRuleHeaderEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBaseRuleHeaderDAO_HI_RO.class);
	public TtaBaseRuleHeaderDAO_HI_RO() {
		super();
	}

}
