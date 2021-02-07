package com.sie.saaf.base.sso.model.dao.readonly;

import com.sie.saaf.base.sso.model.entities.readonly.BaseFunctionCollectionEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseFunctionCollectionDAO_HI_RO")
public class BaseFunctionCollectionDAO_HI_RO extends DynamicViewObjectImpl<BaseFunctionCollectionEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseFunctionCollectionDAO_HI_RO.class);
	public BaseFunctionCollectionDAO_HI_RO() {
		super();
	}

}
