package com.sie.watsons.base.pos.archivesChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.model.entities.EquPosSupplierChangeEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSupplierChangeDAO_HI")
public class EquPosSupplierChangeDAO_HI extends BaseCommonDAO_HI<EquPosSupplierChangeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierChangeDAO_HI.class);

	public EquPosSupplierChangeDAO_HI() {
		super();
	}

}
