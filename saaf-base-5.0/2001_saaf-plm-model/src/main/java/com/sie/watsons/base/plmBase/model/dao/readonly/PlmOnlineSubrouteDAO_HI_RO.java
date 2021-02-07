package com.sie.watsons.base.plmBase.model.dao.readonly;

import com.sie.watsons.base.plmBase.model.entities.readonly.PlmOnlineSubrouteEntity_HI_RO;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmOnlineSubrouteDAO_HI_RO")
public class PlmOnlineSubrouteDAO_HI_RO extends DynamicViewObjectImpl<PlmOnlineSubrouteEntity_HI_RO>  {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmOnlineSubrouteDAO_HI_RO.class);
	public PlmOnlineSubrouteDAO_HI_RO() {
		super();
	}

}
