package com.sie.watsons.base.supplier.model.dao.readonly;

import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierDeptEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaRelSupplierDeptDAO_HI_RO")
public class TtaRelSupplierDeptDAO_HI_RO extends DynamicViewObjectImpl<TtaRelSupplierDeptEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierDeptDAO_HI_RO.class);
	public TtaRelSupplierDeptDAO_HI_RO() {
		super();
	}

}
