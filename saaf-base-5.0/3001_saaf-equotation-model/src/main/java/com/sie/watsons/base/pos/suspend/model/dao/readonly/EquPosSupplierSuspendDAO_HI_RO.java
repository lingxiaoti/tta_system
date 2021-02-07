package com.sie.watsons.base.pos.suspend.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.pos.suspend.model.entities.readonly.EquPosSupplierSuspendEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosSupplierSuspendDAO_HI_RO")
public class EquPosSupplierSuspendDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierSuspendEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierSuspendDAO_HI_RO.class);
	public EquPosSupplierSuspendDAO_HI_RO() {
		super();
	}

}
