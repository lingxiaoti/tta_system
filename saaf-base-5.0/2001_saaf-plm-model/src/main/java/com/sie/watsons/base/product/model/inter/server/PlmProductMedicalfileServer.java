package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductMedicalfileEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductMedicalfile;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductMedicalfileServer")
public class PlmProductMedicalfileServer extends
		BaseCommonServer<PlmProductMedicalfileEntity_HI> implements
		IPlmProductMedicalfile {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductMedicalfileServer.class);

	@Autowired
	private ViewObject<PlmProductMedicalfileEntity_HI> plmProductMedicalfileDAO_HI;

	public PlmProductMedicalfileServer() {
		super();
	}

}
