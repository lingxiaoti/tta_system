package com.sie.saaf.base.shiro.model.dao;

import com.sie.saaf.base.shiro.model.entities.BaseRoleEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("baseRoleDAO_HI")
public class BaseRoleDAO_HI extends BaseCommonDAO_HI<BaseRoleEntity_HI> {
	public BaseRoleDAO_HI() {
		super();
	}

}
