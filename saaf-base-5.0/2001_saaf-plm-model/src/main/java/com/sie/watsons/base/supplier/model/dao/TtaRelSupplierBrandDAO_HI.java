package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierBrandEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaRelSupplierBrandDAO_HI")
public class TtaRelSupplierBrandDAO_HI extends BaseCommonDAO_HI<TtaRelSupplierBrandEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierBrandDAO_HI.class);

	public TtaRelSupplierBrandDAO_HI() {
		super();
	}

}
