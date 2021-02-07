package com.sie.watsons.base.pos.blacklist.model.dao.readonly;

import com.sie.watsons.base.pos.blacklist.model.entities.readonly.EquPosSupplierBlacklistEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("equPosSupplierBlacklistDAO_HI_RO")
public class EquPosSupplierBlacklistDAO_HI_RO extends DynamicViewObjectImpl<EquPosSupplierBlacklistEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierBlacklistDAO_HI_RO.class);
	public EquPosSupplierBlacklistDAO_HI_RO() {
		super();
	}

}
