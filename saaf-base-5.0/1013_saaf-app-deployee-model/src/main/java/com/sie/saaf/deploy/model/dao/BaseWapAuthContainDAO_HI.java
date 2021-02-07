package com.sie.saaf.deploy.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.deploy.model.entities.BaseAppAuthContainEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("baseWapAuthContainDAO_HI")
public class BaseWapAuthContainDAO_HI extends BaseCommonDAO_HI<BaseAppAuthContainEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseWapAuthContainDAO_HI.class);
	public BaseWapAuthContainDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseAppAuthContainEntity_HI entity) {
		return super.save(entity);
	}
}
