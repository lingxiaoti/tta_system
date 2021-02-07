package com.sie.watsons.base.pon.quotation.model.dao.readonly;

import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationPriceDocEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationPriceDocDAO_HI_RO")
public class EquPonQuotationPriceDocDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuotationPriceDocEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationPriceDocDAO_HI_RO.class);
	public EquPonQuotationPriceDocDAO_HI_RO() {
		super();
	}

}
