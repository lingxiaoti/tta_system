package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductSaleshopEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductSaleshopDAO_HI_RO")
public class PlmProductSaleshopDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductSaleshopEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductSaleshopDAO_HI_RO.class);

	public PlmProductSaleshopDAO_HI_RO() {
		super();
	}

}
