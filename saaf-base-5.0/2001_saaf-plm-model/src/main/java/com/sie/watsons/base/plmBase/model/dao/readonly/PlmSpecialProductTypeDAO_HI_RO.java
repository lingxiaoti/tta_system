package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSpecialProductTypeEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmSpecialProductTypeDAO_HI_RO")
public class PlmSpecialProductTypeDAO_HI_RO extends DynamicViewObjectImpl<PlmSpecialProductTypeEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSpecialProductTypeDAO_HI_RO.class);
	public PlmSpecialProductTypeDAO_HI_RO() {
		super();
	}

}
