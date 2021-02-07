package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductPicfileTableEntity_HI;

@Component("plmProductPicfileTableDAO_HI")
public class PlmProductPicfileTableDAO_HI extends BaseCommonDAO_HI<PlmProductPicfileTableEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPicfileTableDAO_HI.class);

	public PlmProductPicfileTableDAO_HI() {
		super();
	}

}
