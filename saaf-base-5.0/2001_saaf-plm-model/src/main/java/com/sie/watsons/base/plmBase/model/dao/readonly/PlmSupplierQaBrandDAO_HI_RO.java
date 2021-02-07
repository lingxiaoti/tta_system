package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierQaBrandEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupplierQaBrandDAO_HI_RO")
public class PlmSupplierQaBrandDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplierQaBrandEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierQaBrandDAO_HI_RO.class);
	public PlmSupplierQaBrandDAO_HI_RO() {
		super();
	}

}
