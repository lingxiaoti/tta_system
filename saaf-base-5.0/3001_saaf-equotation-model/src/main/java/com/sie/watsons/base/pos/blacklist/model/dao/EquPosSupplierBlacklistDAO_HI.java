package com.sie.watsons.base.pos.blacklist.model.dao;

import com.yhg.hibernate.core.dao.ViewObjectImpl;
import com.sie.watsons.base.pos.blacklist.model.entities.EquPosSupplierBlacklistEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierBlacklistDAO_HI")
public class EquPosSupplierBlacklistDAO_HI extends ViewObjectImpl<EquPosSupplierBlacklistEntity_HI>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierBlacklistDAO_HI.class);
	public EquPosSupplierBlacklistDAO_HI() {
		super();
	}

	@Override
	public Object save(EquPosSupplierBlacklistEntity_HI entity) {
		return super.save(entity);
	}
}
