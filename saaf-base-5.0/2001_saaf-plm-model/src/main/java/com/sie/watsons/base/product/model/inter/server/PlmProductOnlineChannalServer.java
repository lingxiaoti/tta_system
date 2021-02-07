package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductOnlineChannalEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductOnlineChannal;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductOnlineChannalServer")
public class PlmProductOnlineChannalServer extends BaseCommonServer<PlmProductOnlineChannalEntity_HI> implements IPlmProductOnlineChannal{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductOnlineChannalServer.class);

	@Autowired
	private ViewObject<PlmProductOnlineChannalEntity_HI> plmProductOnlineChannalDAO_HI;

	public PlmProductOnlineChannalServer() {
		super();
	}

}
