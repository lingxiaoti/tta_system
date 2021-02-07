package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductMedicalinfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductMedicalinfoDAO_HI_RO")
public class PlmProductMedicalinfoDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductMedicalinfoEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductMedicalinfoDAO_HI_RO.class);

	public PlmProductMedicalinfoDAO_HI_RO() {
		super();
	}

}
