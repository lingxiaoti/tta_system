package com.sie.watsons.base.api.model.dao.readonly;

import com.sie.watsons.base.api.model.entities.readonly.PlmRmsMapEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmRmsMapDAO_HI_RO")
public class PlmRmsMapDAO_HI_RO extends DynamicViewObjectImpl<PlmRmsMapEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmRmsMapDAO_HI_RO.class);
	public PlmRmsMapDAO_HI_RO() {
		super();
	}

}
