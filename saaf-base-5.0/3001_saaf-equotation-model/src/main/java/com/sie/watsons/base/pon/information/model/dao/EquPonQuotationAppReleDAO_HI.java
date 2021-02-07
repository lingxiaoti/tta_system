package com.sie.watsons.base.pon.information.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationAppReleEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationAppReleDAO_HI")
public class EquPonQuotationAppReleDAO_HI extends BaseCommonDAO_HI<EquPonQuotationAppReleEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationAppReleDAO_HI.class);

	public EquPonQuotationAppReleDAO_HI() {
		super();
	}

}
