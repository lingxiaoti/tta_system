package com.sie.watsons.base.pon.quotation.model.dao.readonly;

import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationInfoDAO_HI_RO")
public class EquPonQuotationInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuotationInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInfoDAO_HI_RO.class);
	public EquPonQuotationInfoDAO_HI_RO() {
		super();
	}

}
