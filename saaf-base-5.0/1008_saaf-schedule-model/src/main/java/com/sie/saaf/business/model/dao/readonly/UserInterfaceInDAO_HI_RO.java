package com.sie.saaf.business.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.saaf.business.model.entities.readonly.UserInterfaceInEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("userInterfaceInDAO_HI_RO")
public class UserInterfaceInDAO_HI_RO extends DynamicViewObjectImpl<UserInterfaceInEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInterfaceInDAO_HI_RO.class);
	public UserInterfaceInDAO_HI_RO() {
		super();
	}

}
