package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductQaFileEntity_HI;

@Component("plmProductQaFileDAO_HI")
public class PlmProductQaFileDAO_HI extends BaseCommonDAO_HI<PlmProductQaFileEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductQaFileDAO_HI.class);

	public PlmProductQaFileDAO_HI() {
		super();
	}

}
