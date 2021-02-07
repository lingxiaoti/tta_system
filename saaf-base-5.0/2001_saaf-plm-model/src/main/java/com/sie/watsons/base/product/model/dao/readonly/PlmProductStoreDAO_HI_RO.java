package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductStoreEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductStoreDAO_HI_RO")
public class PlmProductStoreDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductStoreEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductStoreDAO_HI_RO.class);

	public PlmProductStoreDAO_HI_RO() {
		super();
	}

}
