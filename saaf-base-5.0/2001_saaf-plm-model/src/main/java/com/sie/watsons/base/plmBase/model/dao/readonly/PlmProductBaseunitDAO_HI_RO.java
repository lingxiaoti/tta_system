package com.sie.watsons.base.plmBase.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmProductBaseunitEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductBaseunitDAO_HI_RO")
public class PlmProductBaseunitDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductBaseunitEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductBaseunitDAO_HI_RO.class);

	public PlmProductBaseunitDAO_HI_RO() {
		super();
	}

}
