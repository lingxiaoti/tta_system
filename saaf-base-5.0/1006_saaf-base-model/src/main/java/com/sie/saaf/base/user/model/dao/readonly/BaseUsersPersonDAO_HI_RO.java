package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseUsersPersonDAO_HI_RO")
public class BaseUsersPersonDAO_HI_RO extends DynamicViewObjectImpl<BaseUsersPerson_HI_RO> {
	public BaseUsersPersonDAO_HI_RO() {
		super();
	}

}
