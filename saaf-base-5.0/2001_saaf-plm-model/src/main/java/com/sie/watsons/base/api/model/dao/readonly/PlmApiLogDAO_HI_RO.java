package com.sie.watsons.base.api.model.dao.readonly;

import com.sie.watsons.base.api.model.entities.readonly.PlmApiLogEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmApiLogDAO_HI_RO")
public class PlmApiLogDAO_HI_RO extends DynamicViewObjectImpl<PlmApiLogEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmApiLogDAO_HI_RO.class);
	public PlmApiLogDAO_HI_RO() {
		super();
	}

}
