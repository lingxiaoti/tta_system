package com.sie.watsons.base.rule.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.rule.model.entities.readonly.TtaBaseRuleLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaBaseRuleLineDAO_HI_RO")
public class TtaBaseRuleLineDAO_HI_RO extends DynamicViewObjectImpl<TtaBaseRuleLineEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBaseRuleLineDAO_HI_RO.class);
	public TtaBaseRuleLineDAO_HI_RO() {
		super();
	}

}
