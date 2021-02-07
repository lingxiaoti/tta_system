package com.sie.watsons.base.cost.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.cost.model.entities.TtaActualCostRuleEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaActualCostRuleDAO_HI")
public class TtaActualCostRuleDAO_HI extends BaseCommonDAO_HI<TtaActualCostRuleEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaActualCostRuleDAO_HI.class);

	public TtaActualCostRuleDAO_HI() {
		super();
	}

}
