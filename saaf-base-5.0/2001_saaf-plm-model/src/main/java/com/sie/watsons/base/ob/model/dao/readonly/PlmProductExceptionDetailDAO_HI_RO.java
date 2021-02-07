package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmProductExceptionDetailEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProductExceptionDetailDAO_HI_RO")
public class PlmProductExceptionDetailDAO_HI_RO extends DynamicViewObjectImpl<PlmProductExceptionDetailEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductExceptionDetailDAO_HI_RO.class);
	public PlmProductExceptionDetailDAO_HI_RO() {
		super();
	}

}
