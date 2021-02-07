package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosAssociatedSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("equPosAssociatedSupplierDAO_HI_RO")
public class EquPosAssociatedSupplierDAO_HI_RO extends DynamicViewObjectImpl<EquPosAssociatedSupplierEntity_HI_RO> {
	public EquPosAssociatedSupplierDAO_HI_RO() {
		super();
	}

}
