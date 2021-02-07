package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.VmiShopEntity_HI_RO2;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("vmiShopDAO_HI_RO2")
public class VmiShopDAO_HI_RO2 extends
		DynamicViewObjectImpl<VmiShopEntity_HI_RO2> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VmiShopDAO_HI_RO2.class);

	public VmiShopDAO_HI_RO2() {
		super();
	}

}
