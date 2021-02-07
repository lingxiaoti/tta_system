package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;

@Component("plmProductHeadDAO_HI")
public class PlmProductHeadDAO_HI extends BaseCommonDAO_HI<PlmProductHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductHeadDAO_HI.class);

	public PlmProductHeadDAO_HI() {
		super();
	}

}
