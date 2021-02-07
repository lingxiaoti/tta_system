package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductFileEntity_HI;

@Component("plmProductFileDAO_HI")
public class PlmProductFileDAO_HI extends BaseCommonDAO_HI<PlmProductFileEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductFileDAO_HI.class);

	public PlmProductFileDAO_HI() {
		super();
	}

}
