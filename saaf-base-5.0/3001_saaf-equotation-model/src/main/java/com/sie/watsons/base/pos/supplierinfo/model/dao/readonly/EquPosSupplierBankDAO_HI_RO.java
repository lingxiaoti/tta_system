package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierBankEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosSupplierBankDAO_HI_RO")
public class EquPosSupplierBankDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierBankEntity_HI_RO>  {
	public EquPosSupplierBankDAO_HI_RO() {
		super();
	}

}
