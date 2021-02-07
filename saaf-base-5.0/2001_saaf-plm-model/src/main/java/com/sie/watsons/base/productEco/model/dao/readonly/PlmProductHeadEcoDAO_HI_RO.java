package com.sie.watsons.base.productEco.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductHeadEcoEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmProductHeadEcoDAO_HI_RO")
public class PlmProductHeadEcoDAO_HI_RO extends DynamicViewObjectImpl<PlmProductHeadEcoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductHeadEcoDAO_HI_RO.class);
	public PlmProductHeadEcoDAO_HI_RO() {
		super();
	}

}
