package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductSalesPropertiesEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductSalesProperties;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductSalesPropertiesServer")
public class PlmProductSalesPropertiesServer extends BaseCommonServer<PlmProductSalesPropertiesEntity_HI> implements IPlmProductSalesProperties{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductSalesPropertiesServer.class);

	@Autowired
	private ViewObject<PlmProductSalesPropertiesEntity_HI> plmProductSalesPropertiesDAO_HI;

	public PlmProductSalesPropertiesServer() {
		super();
	}

}
