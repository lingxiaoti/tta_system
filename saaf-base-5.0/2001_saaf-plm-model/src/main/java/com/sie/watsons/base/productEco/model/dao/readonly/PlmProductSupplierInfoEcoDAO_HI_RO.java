package com.sie.watsons.base.productEco.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductSupplierInfoEcoEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("plmProductSupplierInfoEcoDAO_HI_RO")
public class PlmProductSupplierInfoEcoDAO_HI_RO extends DynamicViewObjectImpl<PlmProductSupplierInfoEcoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductSupplierInfoEcoDAO_HI_RO.class);
	public PlmProductSupplierInfoEcoDAO_HI_RO() {
		super();
	}

}
