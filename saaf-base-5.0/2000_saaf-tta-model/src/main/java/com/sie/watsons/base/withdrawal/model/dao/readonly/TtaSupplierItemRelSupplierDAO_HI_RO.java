package com.sie.watsons.base.withdrawal.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemRelSupplierEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSupplierItemRelSupplierDAO_HI_RO")
public class TtaSupplierItemRelSupplierDAO_HI_RO extends DynamicViewObjectImpl<TtaSupplierItemRelSupplierEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemRelSupplierDAO_HI_RO.class);
	public TtaSupplierItemRelSupplierDAO_HI_RO() {
		super();
	}

}
