package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.BaseOrganizationPerson_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("baseOrganizationPersonDAO_HI_RO")
public class BaseOrganizationPersonDAO_HI_RO extends DynamicViewObjectImpl<BaseOrganizationPerson_HI_RO> {
	public BaseOrganizationPersonDAO_HI_RO() {
		super();
	}

}
