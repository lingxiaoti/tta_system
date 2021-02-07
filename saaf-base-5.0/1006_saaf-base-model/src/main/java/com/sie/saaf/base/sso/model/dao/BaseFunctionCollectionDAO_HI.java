package com.sie.saaf.base.sso.model.dao;

import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionEntity_HI;
import com.yhg.hibernate.core.dao.ViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseFunctionCollectionDAO_HI")
public class BaseFunctionCollectionDAO_HI extends ViewObjectImpl<BaseFunctionCollectionEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseFunctionCollectionDAO_HI.class);
	public BaseFunctionCollectionDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseFunctionCollectionEntity_HI entity) {
		return super.save(entity);
	}
}
