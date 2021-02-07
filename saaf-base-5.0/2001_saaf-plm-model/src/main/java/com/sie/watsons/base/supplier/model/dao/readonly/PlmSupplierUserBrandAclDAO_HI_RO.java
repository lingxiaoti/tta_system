package com.sie.watsons.base.supplier.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplier.model.entities.readonly.PlmSupplierUserBrandAclEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSupplierUserBrandAclDAO_HI_RO")
public class PlmSupplierUserBrandAclDAO_HI_RO extends DynamicViewObjectImpl<PlmSupplierUserBrandAclEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierUserBrandAclDAO_HI_RO.class);
	public PlmSupplierUserBrandAclDAO_HI_RO() {
		super();
	}

}
