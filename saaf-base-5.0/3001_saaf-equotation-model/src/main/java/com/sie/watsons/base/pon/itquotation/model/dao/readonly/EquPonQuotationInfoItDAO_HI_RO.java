package com.sie.watsons.base.pon.itquotation.model.dao.readonly;

import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuotationInfoItEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationInfoItDAO_HI_RO")
public class EquPonQuotationInfoItDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuotationInfoItEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInfoItDAO_HI_RO.class);
	public EquPonQuotationInfoItDAO_HI_RO() {
		super();
	}

}
