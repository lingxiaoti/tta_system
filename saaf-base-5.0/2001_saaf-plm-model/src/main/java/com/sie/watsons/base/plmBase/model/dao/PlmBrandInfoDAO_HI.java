package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandInfoEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmBrandInfoDAO_HI")
public class PlmBrandInfoDAO_HI extends BaseCommonDAO_HI<PlmBrandInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmBrandInfoDAO_HI.class);
	public PlmBrandInfoDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmBrandInfoEntity_HI entity) {
		return super.save(entity);
	}
}
