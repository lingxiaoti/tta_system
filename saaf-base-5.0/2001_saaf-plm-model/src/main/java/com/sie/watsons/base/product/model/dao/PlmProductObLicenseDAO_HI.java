package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductObLicenseEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProductObLicenseDAO_HI")
public class PlmProductObLicenseDAO_HI extends BaseCommonDAO_HI<PlmProductObLicenseEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductObLicenseDAO_HI.class);

	public PlmProductObLicenseDAO_HI() {
		super();
	}

}
