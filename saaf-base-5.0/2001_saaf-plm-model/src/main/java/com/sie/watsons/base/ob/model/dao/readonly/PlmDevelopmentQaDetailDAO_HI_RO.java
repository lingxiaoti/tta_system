package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentQaDetailEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDevelopmentQaDetailDAO_HI_RO")
public class PlmDevelopmentQaDetailDAO_HI_RO extends DynamicViewObjectImpl<PlmDevelopmentQaDetailEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentQaDetailDAO_HI_RO.class);
	public PlmDevelopmentQaDetailDAO_HI_RO() {
		super();
	}

}
