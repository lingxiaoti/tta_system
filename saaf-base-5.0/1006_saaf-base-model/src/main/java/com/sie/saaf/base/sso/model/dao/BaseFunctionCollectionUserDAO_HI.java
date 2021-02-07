package com.sie.saaf.base.sso.model.dao;

import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionUserEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseFunctionCollectionUserDAO_HI")
public class BaseFunctionCollectionUserDAO_HI extends ViewObjectImpl<BaseFunctionCollectionUserEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseFunctionCollectionUserDAO_HI.class);
	public BaseFunctionCollectionUserDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseFunctionCollectionUserEntity_HI entity) {
		return super.save(entity);
	}
}
