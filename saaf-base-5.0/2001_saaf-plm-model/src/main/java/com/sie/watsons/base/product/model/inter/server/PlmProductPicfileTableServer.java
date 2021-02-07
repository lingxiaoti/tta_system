package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductPicfileTableEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductPicfileTable;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductPicfileTableServer")
public class PlmProductPicfileTableServer extends BaseCommonServer<PlmProductPicfileTableEntity_HI> implements IPlmProductPicfileTable{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPicfileTableServer.class);

	@Autowired
	private ViewObject<PlmProductPicfileTableEntity_HI> plmProductPicfileTableDAO_HI;

	public PlmProductPicfileTableServer() {
		super();
	}

}
