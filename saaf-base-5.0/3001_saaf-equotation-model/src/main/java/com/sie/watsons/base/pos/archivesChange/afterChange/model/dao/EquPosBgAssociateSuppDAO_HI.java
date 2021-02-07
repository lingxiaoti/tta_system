package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgAssociateSuppEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgAssociateSuppDAO_HI")
public class EquPosBgAssociateSuppDAO_HI extends BaseCommonDAO_HI<EquPosBgAssociateSuppEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgAssociateSuppDAO_HI.class);

	public EquPosBgAssociateSuppDAO_HI() {
		super();
	}

}
