package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierInfoEntity_HI;

@Component("plmProductSupplierInfoDAO_HI")
public class PlmProductSupplierInfoDAO_HI extends BaseCommonDAO_HI<PlmProductSupplierInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductSupplierInfoDAO_HI.class);

	public PlmProductSupplierInfoDAO_HI() {
		super();
	}

}
