package com.sie.watsons.base.pon.quotation.model.dao.readonly;

import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationProductInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationProductInfoDAO_HI_RO")
public class EquPonQuotationProductInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuotationProductInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationProductInfoDAO_HI_RO.class);
	public EquPonQuotationProductInfoDAO_HI_RO() {
		super();
	}

}
