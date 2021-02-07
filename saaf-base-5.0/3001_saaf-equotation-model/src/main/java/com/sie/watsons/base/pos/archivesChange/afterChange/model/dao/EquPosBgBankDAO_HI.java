package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgBankEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgBankDAO_HI")
public class EquPosBgBankDAO_HI extends BaseCommonDAO_HI<EquPosBgBankEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgBankDAO_HI.class);

	public EquPosBgBankDAO_HI() {
		super();
	}

}
