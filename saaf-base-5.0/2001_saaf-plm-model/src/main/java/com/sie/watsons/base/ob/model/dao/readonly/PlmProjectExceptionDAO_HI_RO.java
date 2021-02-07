package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectExceptionEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProjectExceptionDAO_HI_RO")
public class PlmProjectExceptionDAO_HI_RO extends DynamicViewObjectImpl<PlmProjectExceptionEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectExceptionDAO_HI_RO.class);
	public PlmProjectExceptionDAO_HI_RO() {
		super();
	}

}
