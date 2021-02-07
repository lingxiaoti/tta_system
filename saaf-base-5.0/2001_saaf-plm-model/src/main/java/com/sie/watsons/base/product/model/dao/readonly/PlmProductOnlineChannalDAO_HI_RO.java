package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductOnlineChannalEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductOnlineChannalDAO_HI_RO")
public class PlmProductOnlineChannalDAO_HI_RO extends DynamicViewObjectImpl<PlmProductOnlineChannalEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductOnlineChannalDAO_HI_RO.class);
	public PlmProductOnlineChannalDAO_HI_RO() {
		super();
	}

}
