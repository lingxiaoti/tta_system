package com.sie.watsons.base.productEco.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductMedicalinfoEcoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductMedicalinfoEcoDAO_HI_RO")
public class PlmProductMedicalinfoEcoDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductMedicalinfoEcoEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductMedicalinfoEcoDAO_HI_RO.class);

	public PlmProductMedicalinfoEcoDAO_HI_RO() {
		super();
	}

}
