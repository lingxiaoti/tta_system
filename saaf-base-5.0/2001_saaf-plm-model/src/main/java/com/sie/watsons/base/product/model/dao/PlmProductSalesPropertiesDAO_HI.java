package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductSalesPropertiesEntity_HI;

@Component("plmProductSalesPropertiesDAO_HI")
public class PlmProductSalesPropertiesDAO_HI extends BaseCommonDAO_HI<PlmProductSalesPropertiesEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductSalesPropertiesDAO_HI.class);

	public PlmProductSalesPropertiesDAO_HI() {
		super();
	}

}
