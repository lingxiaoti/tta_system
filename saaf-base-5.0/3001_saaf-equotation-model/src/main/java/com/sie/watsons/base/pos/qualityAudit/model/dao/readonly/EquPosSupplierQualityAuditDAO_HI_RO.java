package com.sie.watsons.base.pos.qualityAudit.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.qualityAudit.model.entities.readonly.EquPosSupplierQualityAuditEntity_HI_RO;
import org.springframework.stereotype.Component;

@Component("equPosSupplierQualityAuditDAO_HI_RO")
public class EquPosSupplierQualityAuditDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierQualityAuditEntity_HI_RO>  {
	public EquPosSupplierQualityAuditDAO_HI_RO() {
		super();
	}

}
