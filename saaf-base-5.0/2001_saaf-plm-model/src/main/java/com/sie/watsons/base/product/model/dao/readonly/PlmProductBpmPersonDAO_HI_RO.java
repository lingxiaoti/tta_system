package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmPersonEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductBpmPersonDAO_HI_RO")
public class PlmProductBpmPersonDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductBpmPersonEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBpmPersonDAO_HI_RO.class);

	public PlmProductBpmPersonDAO_HI_RO() {
		super();
	}

}
