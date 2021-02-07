package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmMotherCompanyEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmMotherCompanyDAO_HI_RO")
public class PlmMotherCompanyDAO_HI_RO extends DynamicViewObjectImpl<PlmMotherCompanyEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmMotherCompanyDAO_HI_RO.class);
	public PlmMotherCompanyDAO_HI_RO() {
		super();
	}

}
