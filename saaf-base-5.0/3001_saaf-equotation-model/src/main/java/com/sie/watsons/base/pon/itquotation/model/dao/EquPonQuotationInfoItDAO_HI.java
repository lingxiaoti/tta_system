package com.sie.watsons.base.pon.itquotation.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itquotation.model.entities.EquPonQuotationInfoItEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationInfoItDAO_HI")
public class EquPonQuotationInfoItDAO_HI extends BaseCommonDAO_HI<EquPonQuotationInfoItEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInfoItDAO_HI.class);
	public EquPonQuotationInfoItDAO_HI() {
		super();
	}

	@Override
	public Object save(EquPonQuotationInfoItEntity_HI entity) {
		return super.save(entity);
	}
}
