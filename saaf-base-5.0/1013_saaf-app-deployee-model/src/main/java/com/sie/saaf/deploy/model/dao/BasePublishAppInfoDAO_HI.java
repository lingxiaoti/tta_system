package com.sie.saaf.deploy.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.deploy.model.entities.BasePublishAppInfoEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("basePublishAppInfoDAO_HI")
public class BasePublishAppInfoDAO_HI extends BaseCommonDAO_HI<BasePublishAppInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BasePublishAppInfoDAO_HI.class);
	public BasePublishAppInfoDAO_HI() {
		super();
	}

	@Override
	public Object save(BasePublishAppInfoEntity_HI entity) {
		return super.save(entity);
	}
}
