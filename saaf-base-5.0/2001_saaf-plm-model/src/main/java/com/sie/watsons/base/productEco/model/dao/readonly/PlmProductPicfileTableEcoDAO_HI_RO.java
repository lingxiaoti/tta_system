package com.sie.watsons.base.productEco.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductPicfileTableEcoEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("plmProductPicfileTableEcoDAO_HI_RO")
public class PlmProductPicfileTableEcoDAO_HI_RO extends DynamicViewObjectImpl<PlmProductPicfileTableEcoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPicfileTableEcoDAO_HI_RO.class);
	public PlmProductPicfileTableEcoDAO_HI_RO() {
		super();
	}

}
