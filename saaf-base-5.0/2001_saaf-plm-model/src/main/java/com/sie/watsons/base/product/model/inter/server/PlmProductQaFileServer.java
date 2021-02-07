package com.sie.watsons.base.product.model.inter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.PlmProductQaFileEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductQaFile;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("plmProductQaFileServer")
public class PlmProductQaFileServer extends BaseCommonServer<PlmProductQaFileEntity_HI> implements IPlmProductQaFile{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductQaFileServer.class);

	@Autowired
	private ViewObject<PlmProductQaFileEntity_HI> plmProductQaFileDAO_HI;

	public PlmProductQaFileServer() {
		super();
	}

}
