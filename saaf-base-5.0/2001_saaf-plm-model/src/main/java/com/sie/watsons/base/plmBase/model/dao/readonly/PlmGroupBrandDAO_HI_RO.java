package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmGroupBrandEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmGroupBrandDAO_HI_RO")
public class PlmGroupBrandDAO_HI_RO extends DynamicViewObjectImpl<PlmGroupBrandEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmGroupBrandDAO_HI_RO.class);
	public PlmGroupBrandDAO_HI_RO() {
		super();
	}

}
