package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.PerAllPeopleFEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("perAllPeopleFDAO_HI")
public class PerAllPeopleFDAO_HI extends BaseCommonDAO_HI<PerAllPeopleFEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PerAllPeopleFDAO_HI.class);
	public PerAllPeopleFDAO_HI() {
		super();
	}

}
