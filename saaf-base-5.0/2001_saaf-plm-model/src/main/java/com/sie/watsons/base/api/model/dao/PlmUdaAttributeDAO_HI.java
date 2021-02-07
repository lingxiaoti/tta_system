package com.sie.watsons.base.api.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.api.model.entities.PlmUdaAttributeEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmUdaAttributeDAO_HI")
public class PlmUdaAttributeDAO_HI extends BaseCommonDAO_HI<PlmUdaAttributeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmUdaAttributeDAO_HI.class);
	public PlmUdaAttributeDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmUdaAttributeEntity_HI entity) {
		return super.save(entity);
	}
}
