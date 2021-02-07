package com.sie.saaf.deploy.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.deploy.model.entities.BaseAppAuthExcludeEntity_HI;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("baseAppAuthExcludeDAO_HI")
public class BaseAppAuthExcludeDAO_HI extends BaseCommonDAO_HI<BaseAppAuthExcludeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAppAuthExcludeDAO_HI.class);
	public BaseAppAuthExcludeDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseAppAuthExcludeEntity_HI entity) {
		return super.save(entity);
	}
}
