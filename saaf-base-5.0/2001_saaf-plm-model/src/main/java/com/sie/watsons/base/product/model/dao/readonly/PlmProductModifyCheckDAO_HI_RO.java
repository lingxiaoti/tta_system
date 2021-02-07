package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductModifyCheckEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductModifyCheckDAO_HI_RO")
public class PlmProductModifyCheckDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductModifyCheckEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductModifyCheckDAO_HI_RO.class);

	public PlmProductModifyCheckDAO_HI_RO() {
		super();
	}

}
