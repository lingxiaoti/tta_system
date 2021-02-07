package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineSubrouteEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmOnlineSubrouteDAO_HI")
public class PlmOnlineSubrouteDAO_HI extends BaseCommonDAO_HI<PlmOnlineSubrouteEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmOnlineSubrouteDAO_HI.class);
	public PlmOnlineSubrouteDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmOnlineSubrouteEntity_HI entity) {
		return super.save(entity);
	}
}
