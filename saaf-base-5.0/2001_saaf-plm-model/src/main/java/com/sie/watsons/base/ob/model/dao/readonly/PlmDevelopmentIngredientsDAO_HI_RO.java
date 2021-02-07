package com.sie.watsons.base.ob.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentIngredientsEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmDevelopmentIngredientsDAO_HI_RO")
public class PlmDevelopmentIngredientsDAO_HI_RO extends DynamicViewObjectImpl<PlmDevelopmentIngredientsEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentIngredientsDAO_HI_RO.class);
	public PlmDevelopmentIngredientsDAO_HI_RO() {
		super();
	}

}
