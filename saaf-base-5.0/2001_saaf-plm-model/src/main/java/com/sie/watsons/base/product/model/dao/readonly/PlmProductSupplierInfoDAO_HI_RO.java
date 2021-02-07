package com.sie.watsons.base.product.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.product.model.entities.readonly.PlmProductSupplierInfoEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("plmProductSupplierInfoDAO_HI_RO")
public class PlmProductSupplierInfoDAO_HI_RO extends DynamicViewObjectImpl<PlmProductSupplierInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductSupplierInfoDAO_HI_RO.class);
	public PlmProductSupplierInfoDAO_HI_RO() {
		super();
	}

}
