package com.sie.watsons.base.supplier.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.supplier.model.entities.TtaRelSupplierDeptEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaRelSupplierDeptDAO_HI")
public class TtaRelSupplierDeptDAO_HI extends BaseCommonDAO_HI<TtaRelSupplierDeptEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierDeptDAO_HI.class);

	public TtaRelSupplierDeptDAO_HI() {
		super();
	}

}
