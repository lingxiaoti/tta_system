package com.sie.watsons.base.pos.creditAudit.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.creditAudit.model.entities.readonly.EquPosSupplierCreditAuditEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosSupplierCreditAuditDAO_HI_RO")
public class EquPosSupplierCreditAuditDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierCreditAuditEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCreditAuditDAO_HI_RO.class);
	public EquPosSupplierCreditAuditDAO_HI_RO() {
		super();
	}

}
