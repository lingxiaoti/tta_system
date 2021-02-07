package com.sie.watsons.base.withdrawal.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemMidEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ttaSupplierItemMidDAO_HI_RO")
public class TtaSupplierItemMidDAO_HI_RO extends DynamicViewObjectImpl<TtaSupplierItemMidEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemMidDAO_HI_RO.class);
	public TtaSupplierItemMidDAO_HI_RO() {
		super();
	}

}
