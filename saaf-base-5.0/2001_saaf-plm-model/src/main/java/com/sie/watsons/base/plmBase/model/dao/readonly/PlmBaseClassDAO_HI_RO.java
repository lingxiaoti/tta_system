package com.sie.watsons.base.plmBase.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseClassEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmBaseClassDAO_HI_RO")
public class PlmBaseClassDAO_HI_RO extends
		DynamicViewObjectImpl<PlmBaseClassEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseClassDAO_HI_RO.class);

	public PlmBaseClassDAO_HI_RO() {
		super();
	}

}
