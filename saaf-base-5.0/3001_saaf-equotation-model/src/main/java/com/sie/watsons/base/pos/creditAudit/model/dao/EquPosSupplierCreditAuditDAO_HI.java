package com.sie.watsons.base.pos.creditAudit.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.creditAudit.model.entities.EquPosSupplierCreditAuditEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierCreditAuditDAO_HI")
public class EquPosSupplierCreditAuditDAO_HI extends BaseCommonDAO_HI<EquPosSupplierCreditAuditEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierCreditAuditDAO_HI.class);

	public EquPosSupplierCreditAuditDAO_HI() {
		super();
	}

}
