package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgSupplierEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgSupplierDAO_HI")
public class EquPosBgSupplierDAO_HI extends BaseCommonDAO_HI<EquPosBgSupplierEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgSupplierDAO_HI.class);

	public EquPosBgSupplierDAO_HI() {
		super();
	}

}
