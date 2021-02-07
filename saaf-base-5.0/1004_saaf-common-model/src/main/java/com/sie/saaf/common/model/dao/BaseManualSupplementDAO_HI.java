package com.sie.saaf.common.model.dao;

import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseManualSupplementDAO_HI")
public class BaseManualSupplementDAO_HI extends BaseCommonDAO_HI<BaseManualSupplementEntity_HI>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseManualSupplementDAO_HI.class);
	public BaseManualSupplementDAO_HI() {
		super();
	}

	@Override
	public Object save(BaseManualSupplementEntity_HI entity) {
		return super.save(entity);
	}
}
