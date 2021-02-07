package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("vmiWarehouseDAO_HI_RO2")
public class VmiWarehouseDAO_HI_RO2 extends
		DynamicViewObjectImpl<VmiWarehouseDAO_HI_RO2> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VmiWarehouseDAO_HI_RO2.class);

	public VmiWarehouseDAO_HI_RO2() {
		super();
	}

}
