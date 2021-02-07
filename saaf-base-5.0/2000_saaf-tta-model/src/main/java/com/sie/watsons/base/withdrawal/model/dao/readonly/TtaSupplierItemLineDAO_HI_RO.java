package com.sie.watsons.base.withdrawal.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("ttaSupplierItemLineDAO_HI_RO")
public class TtaSupplierItemLineDAO_HI_RO extends DynamicViewObjectImpl<TtaSupplierItemLineEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemLineDAO_HI_RO.class);

	public TtaSupplierItemLineDAO_HI_RO() {
		super();
	}

}
