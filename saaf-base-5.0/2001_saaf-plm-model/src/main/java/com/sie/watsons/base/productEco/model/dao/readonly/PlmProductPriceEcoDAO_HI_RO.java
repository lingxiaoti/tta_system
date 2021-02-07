package com.sie.watsons.base.productEco.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductPriceEcoEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("plmProductPriceEcoDAO_HI_RO")
public class PlmProductPriceEcoDAO_HI_RO extends DynamicViewObjectImpl<PlmProductPriceEcoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPriceEcoDAO_HI_RO.class);
	public PlmProductPriceEcoDAO_HI_RO() {
		super();
	}

}
