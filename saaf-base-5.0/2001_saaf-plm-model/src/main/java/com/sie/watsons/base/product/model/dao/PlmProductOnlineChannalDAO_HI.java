package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductOnlineChannalEntity_HI;

@Component("plmProductOnlineChannalDAO_HI")
public class PlmProductOnlineChannalDAO_HI extends BaseCommonDAO_HI<PlmProductOnlineChannalEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductOnlineChannalDAO_HI.class);

	public PlmProductOnlineChannalDAO_HI() {
		super();
	}

}
