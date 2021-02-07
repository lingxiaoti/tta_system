package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierCredentialsEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosSupplierCredentialsDAO_HI_RO")
public class EquPosSupplierCredentialsDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierCredentialsEntity_HI_RO>  {
	public EquPosSupplierCredentialsDAO_HI_RO() {
		super();
	}

}
