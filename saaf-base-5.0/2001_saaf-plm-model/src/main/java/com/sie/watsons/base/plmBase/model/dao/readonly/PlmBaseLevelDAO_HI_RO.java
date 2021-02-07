package com.sie.watsons.base.plmBase.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBaseLevelEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmBaseLevelDAO_HI_RO")
public class PlmBaseLevelDAO_HI_RO extends
		DynamicViewObjectImpl<PlmBaseLevelEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBaseLevelDAO_HI_RO.class);

	public PlmBaseLevelDAO_HI_RO() {
		super();
	}

}
