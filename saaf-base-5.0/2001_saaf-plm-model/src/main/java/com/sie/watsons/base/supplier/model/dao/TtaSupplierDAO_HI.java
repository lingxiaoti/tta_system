package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSupplierDAO_HI")
public class TtaSupplierDAO_HI extends BaseCommonDAO_HI<TtaSupplierEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierDAO_HI.class);

	public TtaSupplierDAO_HI() {
		super();
	}

}
