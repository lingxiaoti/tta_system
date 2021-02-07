package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductBpmUserEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProductBpmUserDAO_HI")
public class PlmProductBpmUserDAO_HI extends BaseCommonDAO_HI<PlmProductBpmUserEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductBpmUserDAO_HI.class);

	public PlmProductBpmUserDAO_HI() {
		super();
	}

}
