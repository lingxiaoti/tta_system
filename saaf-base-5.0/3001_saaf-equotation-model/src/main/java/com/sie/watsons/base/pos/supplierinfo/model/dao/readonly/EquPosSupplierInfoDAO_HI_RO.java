package com.sie.watsons.base.pos.supplierinfo.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.supplierinfo.model.entities.readonly.EquPosSupplierInfoEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosSupplierInfoDAO_HI_RO")
public class EquPosSupplierInfoDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierInfoEntity_HI_RO>  {
	public EquPosSupplierInfoDAO_HI_RO() {
		super();
	}

}
