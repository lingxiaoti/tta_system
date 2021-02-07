package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BaseChannelPrivilegeEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("baseChannelPrivilegeDAO_HI")
public class BaseChannelPrivilegeDAO_HI extends BaseCommonDAO_HI<BaseChannelPrivilegeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseChannelPrivilegeDAO_HI.class);
	public BaseChannelPrivilegeDAO_HI() {
		super();
	}
}
