package com.sie.watsons.base.ob.model.dao.readonly;

import com.sie.watsons.base.ob.model.entities.readonly.PlmProjectInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProjectInfoDAO_HI_RO")
public class PlmProjectInfoDAO_HI_RO extends DynamicViewObjectImpl<PlmProjectInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectInfoDAO_HI_RO.class);
	public PlmProjectInfoDAO_HI_RO() {
		super();
	}

}
