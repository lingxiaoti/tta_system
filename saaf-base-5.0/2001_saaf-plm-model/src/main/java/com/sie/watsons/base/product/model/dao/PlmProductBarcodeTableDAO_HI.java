package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductBarcodeTableEntity_HI;

@Component("plmProductBarcodeTableDAO_HI")
public class PlmProductBarcodeTableDAO_HI extends BaseCommonDAO_HI<PlmProductBarcodeTableEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductBarcodeTableDAO_HI.class);

	public PlmProductBarcodeTableDAO_HI() {
		super();
	}

}
