package com.sie.watsons.base.pon.itscoring.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringMaintainEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonItScoringMaintainDAO_HI")
public class EquPonItScoringMaintainDAO_HI extends BaseCommonDAO_HI<EquPonItScoringMaintainEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringMaintainDAO_HI.class);

	public EquPonItScoringMaintainDAO_HI() {
		super();
	}

}
