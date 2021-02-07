package com.sie.watsons.base.product.model.dao.readonly;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductBpmUserEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProductBpmUserDAO_HI_RO")
public class PlmProductBpmUserDAO_HI_RO extends DynamicViewObjectImpl<PlmProductBpmUserEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductBpmUserDAO_HI_RO.class);
	public PlmProductBpmUserDAO_HI_RO() {
		super();
	}

}
