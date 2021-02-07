package com.sie.watsons.base.pon.quotation.model.dao.readonly;

import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationNopriceDocEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationNopriceDocDAO_HI_RO")
public class EquPonQuotationNopriceDocDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuotationNopriceDocEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationNopriceDocDAO_HI_RO.class);
	public EquPonQuotationNopriceDocDAO_HI_RO() {
		super();
	}

}
