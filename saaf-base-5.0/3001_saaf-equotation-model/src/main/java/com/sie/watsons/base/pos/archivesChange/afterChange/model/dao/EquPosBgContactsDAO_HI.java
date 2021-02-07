package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgContactsEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgContactsDAO_HI")
public class EquPosBgContactsDAO_HI extends BaseCommonDAO_HI<EquPosBgContactsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgContactsDAO_HI.class);

	public EquPosBgContactsDAO_HI() {
		super();
	}

}
