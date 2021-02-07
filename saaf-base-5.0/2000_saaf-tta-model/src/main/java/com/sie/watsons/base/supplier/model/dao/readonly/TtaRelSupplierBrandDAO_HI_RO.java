package com.sie.watsons.base.supplier.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierBrandEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaRelSupplierBrandDAO_HI_RO")
public class TtaRelSupplierBrandDAO_HI_RO extends DynamicViewObjectImpl<TtaRelSupplierBrandEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaRelSupplierBrandDAO_HI_RO.class);
	public TtaRelSupplierBrandDAO_HI_RO() {
		super();
	}

}
