package com.sie.watsons.base.pon.information.model.dao.readonly;

import com.sie.watsons.base.pon.information.model.entities.readonly.EquPonQuotationAppReleEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPonQuotationAppReleDAO_HI_RO")
public class EquPonQuotationAppReleDAO_HI_RO extends DynamicViewObjectImpl<EquPonQuotationAppReleEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationAppReleDAO_HI_RO.class);
	public EquPonQuotationAppReleDAO_HI_RO() {
		super();
	}

}
