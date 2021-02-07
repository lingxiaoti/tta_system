package com.sie.watsons.base.supplement.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupShopDAO_HI")
public class PlmSupShopDAO_HI extends BaseCommonDAO_HI<PlmSupShopEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupShopDAO_HI.class);
	public PlmSupShopDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSupShopEntity_HI entity) {
		return super.save(entity);
	}
}
