package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductPicfileTableEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductPicfileTableDAO_HI_RO")
public class PlmProductPicfileTableDAO_HI_RO extends DynamicViewObjectImpl<PlmProductPicfileTableEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPicfileTableDAO_HI_RO.class);
	public PlmProductPicfileTableDAO_HI_RO() {
		super();
	}

}
