package com.sie.watsons.base.usergroupdeptbrand.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserGroupDeptBrandEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaUserGroupDeptBrandDAO_HI_RO")
public class TtaUserGroupDeptBrandDAO_HI_RO extends DynamicViewObjectImpl<TtaUserGroupDeptBrandEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserGroupDeptBrandDAO_HI_RO.class);
	public TtaUserGroupDeptBrandDAO_HI_RO() {
		super();
	}

}
