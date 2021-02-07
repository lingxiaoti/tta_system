package com.sie.watsons.base.supplier.model.dao.readonly;

import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSupplierDAO_HI_RO")
public class TtaSupplierDAO_HI_RO extends DynamicViewObjectImpl<TtaSupplierEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierDAO_HI_RO.class);
	public TtaSupplierDAO_HI_RO() {
		super();
	}

}
