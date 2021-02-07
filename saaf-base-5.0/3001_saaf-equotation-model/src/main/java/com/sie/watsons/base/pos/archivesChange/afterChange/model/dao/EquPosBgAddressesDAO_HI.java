package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgAddressesEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgAddressesDAO_HI")
public class EquPosBgAddressesDAO_HI extends BaseCommonDAO_HI<EquPosBgAddressesEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgAddressesDAO_HI.class);

	public EquPosBgAddressesDAO_HI() {
		super();
	}

}
