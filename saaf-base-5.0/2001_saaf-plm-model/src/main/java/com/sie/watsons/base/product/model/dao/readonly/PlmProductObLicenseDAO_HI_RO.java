package com.sie.watsons.base.product.model.dao.readonly;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductObLicenseEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProductObLicenseDAO_HI_RO")
public class PlmProductObLicenseDAO_HI_RO extends DynamicViewObjectImpl<PlmProductObLicenseEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductObLicenseDAO_HI_RO.class);
	public PlmProductObLicenseDAO_HI_RO() {
		super();
	}

}
