package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmOnlineRouteEntity_HI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("plmOnlineRouteDAO_HI")
public class PlmOnlineRouteDAO_HI extends BaseCommonDAO_HI<PlmOnlineRouteEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmOnlineRouteDAO_HI.class);
	public PlmOnlineRouteDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmOnlineRouteEntity_HI entity) {
		return super.save(entity);
	}
}
