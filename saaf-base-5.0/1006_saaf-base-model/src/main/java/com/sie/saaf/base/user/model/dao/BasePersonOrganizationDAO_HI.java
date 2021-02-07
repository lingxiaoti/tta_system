package com.sie.saaf.base.user.model.dao;

import com.sie.saaf.base.user.model.entities.BasePersonOrganizationEntity_HI;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import org.springframework.stereotype.Component;

@Component("basePersonOrganizationDAO_HI")
public class BasePersonOrganizationDAO_HI extends BaseCommonDAO_HI<BasePersonOrganizationEntity_HI> {
	public BasePersonOrganizationDAO_HI() {
		super();
	}
}
