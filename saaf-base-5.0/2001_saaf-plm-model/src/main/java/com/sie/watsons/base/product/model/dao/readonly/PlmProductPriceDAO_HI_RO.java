package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductPriceEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductPriceDAO_HI_RO")
public class PlmProductPriceDAO_HI_RO extends DynamicViewObjectImpl<PlmProductPriceEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPriceDAO_HI_RO.class);
	public PlmProductPriceDAO_HI_RO() {
		super();
	}

}
