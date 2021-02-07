package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductFileEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductFile;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductFileServer")
public class PlmProductFileServer extends BaseCommonServer<PlmProductFileEntity_HI> implements IPlmProductFile{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductFileServer.class);

	@Autowired
	private ViewObject<PlmProductFileEntity_HI> plmProductFileDAO_HI;

	public PlmProductFileServer() {
		super();
	}

}
