package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentInfoEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDevelopmentInfoDAO_HI_RO")
public class PlmDevelopmentInfoDAO_HI_RO extends DynamicViewObjectImpl<PlmDevelopmentInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentInfoDAO_HI_RO.class);
	public PlmDevelopmentInfoDAO_HI_RO() {
		super();
	}

}
