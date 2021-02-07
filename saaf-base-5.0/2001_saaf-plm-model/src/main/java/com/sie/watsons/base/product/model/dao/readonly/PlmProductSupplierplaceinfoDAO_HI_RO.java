package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductSupplierplaceinfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductSupplierplaceinfoDAO_HI_RO")
public class PlmProductSupplierplaceinfoDAO_HI_RO extends
		DynamicViewObjectImpl<PlmProductSupplierplaceinfoEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductSupplierplaceinfoDAO_HI_RO.class);

	public PlmProductSupplierplaceinfoDAO_HI_RO() {
		super();
	}

}
