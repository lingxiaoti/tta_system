package com.sie.watsons.base.productEco.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductDrugEcoEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProductDrugEcoDAO_HI_RO")
public class PlmProductDrugEcoDAO_HI_RO extends DynamicViewObjectImpl<PlmProductDrugEcoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductDrugEcoDAO_HI_RO.class);
	public PlmProductDrugEcoDAO_HI_RO() {
		super();
	}

}
