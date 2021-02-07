package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BaseLoginLogEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseLoginLogDAO_HI")
public class BaseLoginLogDAO_HI extends BaseCommonDAO_HI<BaseLoginLogEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseLoginLogDAO_HI.class);
	public BaseLoginLogDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseLoginLogEntity_HI entity) {
		return super.save(entity);
	}
}
