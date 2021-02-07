package com.sie.watsons.base.supplier.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaRelSupplierDAO_HI_RO")
public class TtaRelSupplierDAO_HI_RO extends DynamicViewObjectImpl<TtaRelSupplierEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierDAO_HI_RO.class);
	public TtaRelSupplierDAO_HI_RO() {
		super();
	}

}
