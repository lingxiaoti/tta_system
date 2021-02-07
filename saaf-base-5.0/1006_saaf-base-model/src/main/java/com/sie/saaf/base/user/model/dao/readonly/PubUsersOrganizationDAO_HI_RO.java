package com.sie.saaf.base.user.model.dao.readonly;

import com.sie.saaf.base.user.model.entities.readonly.PubUsersOrganizationEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.springframework.stereotype.Component;

@Component("pubUsersOrganizationDAO_HI_RO")
public class PubUsersOrganizationDAO_HI_RO extends DynamicViewObjectImpl<PubUsersOrganizationEntity_HI_RO> {
	public PubUsersOrganizationDAO_HI_RO() {
		super();
	}

}
