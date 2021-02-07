package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseUsersDAO_HI")
public class BaseUsersDAO_HI extends BaseCommonDAO_HI<BaseUsersEntity_HI> {
	public BaseUsersDAO_HI() {
		super();
	}
}
