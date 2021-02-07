package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductMedicalfileEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductMedicalfileDAO_HI_RO")
public class PlmProductMedicalfileDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductMedicalfileEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductMedicalfileDAO_HI_RO.class);

	public PlmProductMedicalfileDAO_HI_RO() {
		super();
	}

}
