package com.sie.saaf.base.sso.model.dao.readonly;

import com.sie.saaf.base.sso.model.entities.readonly.BaseFunctionCollectionUserEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseFunctionCollectionUserDAO_HI_RO")
public class BaseFunctionCollectionUserDAO_HI_RO extends DynamicViewObjectImpl<BaseFunctionCollectionUserEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseFunctionCollectionUserDAO_HI_RO.class);
	public BaseFunctionCollectionUserDAO_HI_RO() {
		super();
	}

}
