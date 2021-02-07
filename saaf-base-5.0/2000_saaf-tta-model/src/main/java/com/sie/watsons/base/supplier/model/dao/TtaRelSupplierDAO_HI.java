package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaRelSupplierDAO_HI")
public class TtaRelSupplierDAO_HI extends BaseCommonDAO_HI<TtaRelSupplierEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierDAO_HI.class);

	public TtaRelSupplierDAO_HI() {
		super();
	}

}
