package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductDrugEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductDrugDAO_HI_RO")
public class PlmProductDrugDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductDrugEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductDrugDAO_HI_RO.class);

	public PlmProductDrugDAO_HI_RO() {
		super();
	}

}
