package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmGroupBrandEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmGroupBrandDAO_HI")
public class PlmGroupBrandDAO_HI extends BaseCommonDAO_HI<PlmGroupBrandEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmGroupBrandDAO_HI.class);
	public PlmGroupBrandDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmGroupBrandEntity_HI entity) {
		return super.save(entity);
	}
}
