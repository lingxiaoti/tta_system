package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductDrugfileEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductDrugfileDAO_HI_RO")
public class PlmProductDrugfileDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductDrugfileEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductDrugfileDAO_HI_RO.class);

	public PlmProductDrugfileDAO_HI_RO() {
		super();
	}

}
