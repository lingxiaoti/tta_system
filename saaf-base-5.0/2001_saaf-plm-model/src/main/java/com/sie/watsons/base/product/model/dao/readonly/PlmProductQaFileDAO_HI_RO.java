package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductQaFileEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductQaFileDAO_HI_RO")
public class PlmProductQaFileDAO_HI_RO extends DynamicViewObjectImpl<PlmProductQaFileEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductQaFileDAO_HI_RO.class);
	public PlmProductQaFileDAO_HI_RO() {
		super();
	}

}
