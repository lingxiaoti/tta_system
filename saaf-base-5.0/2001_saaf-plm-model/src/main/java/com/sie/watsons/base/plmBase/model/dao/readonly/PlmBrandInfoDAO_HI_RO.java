package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBrandInfoEntity_HI_RO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmBrandInfoDAO_HI_RO")
public class PlmBrandInfoDAO_HI_RO extends DynamicViewObjectImpl<PlmBrandInfoEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmBrandInfoDAO_HI_RO.class);
	public PlmBrandInfoDAO_HI_RO() {
		super();
	}

}
