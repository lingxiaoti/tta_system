package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierDeptEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaRelSupplierDeptDAO_HI")
public class TtaRelSupplierDeptDAO_HI extends BaseCommonDAO_HI<TtaRelSupplierDeptEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierDeptDAO_HI.class);

	public TtaRelSupplierDeptDAO_HI() {
		super();
	}

}
