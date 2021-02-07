package com.sie.watsons.base.pon.quotation.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationInfoDAO_HI")
public class EquPonQuotationInfoDAO_HI extends BaseCommonDAO_HI<EquPonQuotationInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInfoDAO_HI.class);

	public EquPonQuotationInfoDAO_HI() {
		super();
	}

}
