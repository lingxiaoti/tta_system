package com.sie.watsons.base.pon.information.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.information.model.entities.EquPonQuotationInformationEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationInformationDAO_HI")
public class EquPonQuotationInformationDAO_HI extends BaseCommonDAO_HI<EquPonQuotationInformationEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInformationDAO_HI.class);

	public EquPonQuotationInformationDAO_HI() {
		super();
	}

}
