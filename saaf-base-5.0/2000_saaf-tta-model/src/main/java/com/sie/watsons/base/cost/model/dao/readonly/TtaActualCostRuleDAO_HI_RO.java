package com.sie.watsons.base.cost.model.dao.readonly;

import com.sie.watsons.base.cost.model.entities.readonly.TtaActualCostRuleEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaActualCostRuleDAO_HI_RO")
public class TtaActualCostRuleDAO_HI_RO extends DynamicViewObjectImpl<TtaActualCostRuleEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaActualCostRuleDAO_HI_RO.class);
	public TtaActualCostRuleDAO_HI_RO() {
		super();
	}

}
