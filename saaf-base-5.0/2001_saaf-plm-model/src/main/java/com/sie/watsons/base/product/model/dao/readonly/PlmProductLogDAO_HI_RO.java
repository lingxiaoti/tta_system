package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductLogEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductLogDAO_HI_RO")
public class PlmProductLogDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductLogEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductLogDAO_HI_RO.class);

	public PlmProductLogDAO_HI_RO() {
		super();
	}

}
