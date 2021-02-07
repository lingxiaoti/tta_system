package com.sie.watsons.base.poc.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.poc.model.entities.XxPromStoreEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("xxPromStoreDAO_HI")
public class XxPromStoreDAO_HI extends BaseCommonDAO_HI<XxPromStoreEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(XxPromStoreDAO_HI.class);
	public XxPromStoreDAO_HI() {
		super();
	}

	@Override
	public Object save(XxPromStoreEntity_HI entity) {
		return super.save(entity);
	}
}
