package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierContactsEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosSupplierContactsDAO_HI_RO")
public class EquPosSupplierContactsDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierContactsEntity_HI_RO>  {
	public EquPosSupplierContactsDAO_HI_RO() {
		super();
	}

}
