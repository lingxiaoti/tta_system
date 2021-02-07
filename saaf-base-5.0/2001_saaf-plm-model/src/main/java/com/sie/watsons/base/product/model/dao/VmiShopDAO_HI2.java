package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.VmiShopEntity_HI2;

@Component("vmiShopDAO_HI2")
public class VmiShopDAO_HI2 extends BaseCommonDAO_HI<VmiShopEntity_HI2> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VmiShopDAO_HI2.class);

	public VmiShopDAO_HI2() {
		super();
	}

}
