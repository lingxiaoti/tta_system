package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductPriceEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductPrice;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductPriceServer")
public class PlmProductPriceServer extends BaseCommonServer<PlmProductPriceEntity_HI> implements IPlmProductPrice{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPriceServer.class);

	@Autowired
	private ViewObject<PlmProductPriceEntity_HI> plmProductPriceDAO_HI;

	public PlmProductPriceServer() {
		super();
	}

}
