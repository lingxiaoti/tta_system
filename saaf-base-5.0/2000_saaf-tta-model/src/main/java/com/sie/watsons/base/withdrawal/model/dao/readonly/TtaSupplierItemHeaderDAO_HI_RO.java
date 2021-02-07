package com.sie.watsons.base.withdrawal.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemHeaderEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;


@Component("ttaSupplierItemHeaderDAO_HI_RO")
public class TtaSupplierItemHeaderDAO_HI_RO extends DynamicViewObjectImpl<TtaSupplierItemHeaderEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemHeaderDAO_HI_RO.class);

	public TtaSupplierItemHeaderDAO_HI_RO() {
		super();
	}

}
