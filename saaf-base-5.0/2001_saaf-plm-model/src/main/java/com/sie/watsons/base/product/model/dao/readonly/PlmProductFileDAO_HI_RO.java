package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductFileEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductFileDAO_HI_RO")
public class PlmProductFileDAO_HI_RO extends DynamicViewObjectImpl<PlmProductFileEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductFileDAO_HI_RO.class);
	public PlmProductFileDAO_HI_RO() {
		super();
	}

}
