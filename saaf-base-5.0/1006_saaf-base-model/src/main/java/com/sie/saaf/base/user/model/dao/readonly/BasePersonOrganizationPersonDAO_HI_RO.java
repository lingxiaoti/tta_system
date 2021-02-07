package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BasePersonOrganizationPerson_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("basePersonOrganizationPersonDAO_HI_RO")
public class BasePersonOrganizationPersonDAO_HI_RO extends DynamicViewObjectImpl<BasePersonOrganizationPerson_HI_RO> {
	public BasePersonOrganizationPersonDAO_HI_RO() {
		super();
	}

}
