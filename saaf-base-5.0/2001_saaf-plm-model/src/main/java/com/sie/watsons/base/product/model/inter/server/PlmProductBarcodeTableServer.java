package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductBarcodeTableEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductBarcodeTable;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductBarcodeTableServer")
public class PlmProductBarcodeTableServer extends BaseCommonServer<PlmProductBarcodeTableEntity_HI> implements IPlmProductBarcodeTable{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductBarcodeTableServer.class);

	@Autowired
	private ViewObject<PlmProductBarcodeTableEntity_HI> plmProductBarcodeTableDAO_HI;

	public PlmProductBarcodeTableServer() {
		super();
	}

}
