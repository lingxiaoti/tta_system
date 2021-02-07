package com.sie.saaf.deploy.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("basePublishAppInfoDAO_HI_RO")
public class BasePublishAppInfoDAO_HI_RO extends DynamicViewObjectImpl<BasePublishAppInfoDAO_HI_RO>   {
	private static final Logger LOGGER = LoggerFactory.getLogger(BasePublishAppInfoDAO_HI_RO.class);
	public BasePublishAppInfoDAO_HI_RO() {
		super();
	}

}
