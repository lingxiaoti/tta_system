package com.sie.watsons.base.pon.information.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuoSecondResultEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuoSecondResultDAO_HI")
public class EquPonQuoSecondResultDAO_HI extends BaseCommonDAO_HI<EquPonQuoSecondResultEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuoSecondResultDAO_HI.class);

	public EquPonQuoSecondResultDAO_HI() {
		super();
	}

}
