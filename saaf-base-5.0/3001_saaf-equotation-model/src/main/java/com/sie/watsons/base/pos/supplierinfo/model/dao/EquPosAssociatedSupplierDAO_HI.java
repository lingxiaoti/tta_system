package com.sie.watsons.base.pos.supplierinfo.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosAssociatedSupplierEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosAssociatedSupplierDAO_HI")
public class EquPosAssociatedSupplierDAO_HI extends BaseCommonDAO_HI<EquPosAssociatedSupplierEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosAssociatedSupplierDAO_HI.class);

	public EquPosAssociatedSupplierDAO_HI() {
		super();
	}

}
