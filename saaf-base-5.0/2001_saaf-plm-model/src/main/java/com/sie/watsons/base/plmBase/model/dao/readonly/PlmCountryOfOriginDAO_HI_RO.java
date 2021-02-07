package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmCountryOfOriginEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmCountryOfOriginDAO_HI_RO")
public class PlmCountryOfOriginDAO_HI_RO extends DynamicViewObjectImpl<PlmCountryOfOriginEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmCountryOfOriginDAO_HI_RO.class);
	public PlmCountryOfOriginDAO_HI_RO() {
		super();
	}

}
