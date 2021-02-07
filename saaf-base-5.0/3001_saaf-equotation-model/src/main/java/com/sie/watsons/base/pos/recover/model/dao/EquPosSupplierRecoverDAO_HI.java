package com.sie.watsons.base.pos.recover.model.dao;

import com.sie.watsons.base.pos.recover.model.entities.EquPosSupplierRecoverEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierRecoverDAO_HI")
public class EquPosSupplierRecoverDAO_HI extends ViewObjectImpl<EquPosSupplierRecoverEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierRecoverDAO_HI.class);
	public EquPosSupplierRecoverDAO_HI() {
		super();
	}

	@Override
	public Object save(EquPosSupplierRecoverEntity_HI entity) {
		return super.save(entity);
	}
}
