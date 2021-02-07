package com.sie.watsons.base.productEco.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductDrugEcoEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductDrugEcoDAO_HI")
public class PlmProductDrugEcoDAO_HI extends BaseCommonDAO_HI<PlmProductDrugEcoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductDrugEcoDAO_HI.class);

	public PlmProductDrugEcoDAO_HI() {
		super();
	}

}
