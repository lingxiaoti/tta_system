package com.sie.watsons.base.pos.archivesChange.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.archivesChange.model.entities.readonly.EquPosSupplierChangeEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosSupplierChangeDAO_HI_RO")
public class EquPosSupplierChangeDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierChangeEntity_HI_RO>  {
	public EquPosSupplierChangeDAO_HI_RO() {
		super();
	}

}
