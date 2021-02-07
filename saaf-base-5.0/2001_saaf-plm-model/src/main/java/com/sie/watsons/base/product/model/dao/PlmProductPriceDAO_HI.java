package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductPriceEntity_HI;

@Component("plmProductPriceDAO_HI")
public class PlmProductPriceDAO_HI extends BaseCommonDAO_HI<PlmProductPriceEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPriceDAO_HI.class);

	public PlmProductPriceDAO_HI() {
		super();
	}

}
