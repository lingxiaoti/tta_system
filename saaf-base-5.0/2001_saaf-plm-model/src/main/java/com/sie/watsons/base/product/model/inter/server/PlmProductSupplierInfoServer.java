package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductSupplierInfoEntity_HI;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductSupplierInfoEntity_HI_RO;
import com.sie.watsons.base.product.model.inter.IPlmProductSupplierInfo;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductSupplierInfoServer")
public class PlmProductSupplierInfoServer extends
		BaseCommonServer<PlmProductSupplierInfoEntity_HI> implements
		IPlmProductSupplierInfo {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductSupplierInfoServer.class);

	@Autowired
	private ViewObject<PlmProductSupplierInfoEntity_HI> plmProductSupplierInfoDAO_HI;

	@Autowired
	private BaseViewObject<PlmProductSupplierInfoEntity_HI_RO> plmProductSupplierInfoEntity_HI_RO;

	public PlmProductSupplierInfoServer() {
		super();
	}

}
