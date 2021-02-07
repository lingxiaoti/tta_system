package com.sie.watsons.base.pos.recover.model.dao.readonly;

import com.sie.watsons.base.pos.recover.model.entities.readonly.EquPosSupplierRecoverEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosSupplierRecoverDAO_HI_RO")
public class EquPosSupplierRecoverDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierRecoverEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierRecoverDAO_HI_RO.class);
	public EquPosSupplierRecoverDAO_HI_RO() {
		super();
	}

}
