package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadtempleEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductHeadtempleDAO_HI_RO")
public class PlmProductHeadtempleDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductHeadtempleEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductHeadtempleDAO_HI_RO.class);

	public PlmProductHeadtempleDAO_HI_RO() {
		super();
	}

}
