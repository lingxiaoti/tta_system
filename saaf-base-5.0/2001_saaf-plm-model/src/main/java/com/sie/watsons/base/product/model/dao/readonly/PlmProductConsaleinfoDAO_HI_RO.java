package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductConsaleinfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductConsaleinfoDAO_HI_RO")
public class PlmProductConsaleinfoDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductConsaleinfoEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductConsaleinfoDAO_HI_RO.class);

	public PlmProductConsaleinfoDAO_HI_RO() {
		super();
	}

}
