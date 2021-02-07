package com.sie.watsons.base.usergroupdeptbrand.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserDataTypeEntity_HI_RO;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component("ttaUserDataTypeDAO_HI_RO")
public class TtaUserDataTypeDAO_HI_RO extends DynamicViewObjectImpl<TtaUserDataTypeEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserDataTypeDAO_HI_RO.class);
	public TtaUserDataTypeDAO_HI_RO() {
		super();
	}

}
