package com.sie.watsons.base.pos.archivesChange.afterChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.EquPosBgCredentialsEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgCredentialsDAO_HI")
public class EquPosBgCredentialsDAO_HI extends BaseCommonDAO_HI<EquPosBgCredentialsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgCredentialsDAO_HI.class);

	public EquPosBgCredentialsDAO_HI() {
		super();
	}

}
