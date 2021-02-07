package com.sie.watsons.base.product.model.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.VmiWarehouseEntity_HI2;

@Component("vmiWarehouseDAO_HI2")
public class VmiWarehouseDAO_HI2 extends
		BaseCommonDAO_HI<VmiWarehouseEntity_HI2> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VmiWarehouseDAO_HI2.class);

	public VmiWarehouseDAO_HI2() {
		super();
	}

}
