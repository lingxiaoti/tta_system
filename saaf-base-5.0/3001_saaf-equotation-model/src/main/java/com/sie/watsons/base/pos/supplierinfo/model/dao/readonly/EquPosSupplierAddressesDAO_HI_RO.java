package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierAddressesEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosSupplierAddressesDAO_HI_RO")
public class EquPosSupplierAddressesDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierAddressesEntity_HI_RO>  {
	public EquPosSupplierAddressesDAO_HI_RO() {
		super();
	}

}
