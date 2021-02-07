package com.sie.saaf.base.commmon.model.dao.readonly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sie.saaf.base.commmon.model.entities.readonly.BaseWatsonsBpmNodeUserEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;

@Component("baseWatsonsBpmNodeUserDAO_HI_RO")
public class BaseWatsonsBpmNodeUserDAO_HI_RO extends
		DynamicViewObjectImpl<BaseWatsonsBpmNodeUserEntity_HI_RO> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseWatsonsBpmNodeUserDAO_HI_RO.class);

	public BaseWatsonsBpmNodeUserDAO_HI_RO() {
		super();
	}

}
