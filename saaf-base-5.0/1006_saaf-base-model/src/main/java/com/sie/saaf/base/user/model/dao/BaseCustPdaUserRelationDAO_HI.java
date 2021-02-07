package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BaseCustPdaUserRelationEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseCustPdaUserRelationDAO_HI")
public class BaseCustPdaUserRelationDAO_HI extends BaseCommonDAO_HI<BaseCustPdaUserRelationEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseCustPdaUserRelationDAO_HI.class);

	public BaseCustPdaUserRelationDAO_HI() {
		super();
	}

}
