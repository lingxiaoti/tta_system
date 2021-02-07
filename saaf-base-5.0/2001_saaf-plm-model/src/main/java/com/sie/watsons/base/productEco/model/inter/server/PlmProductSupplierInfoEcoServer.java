package com.sie.watsons.base.productEco.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.productEco.model.entities.PlmProductSupplierInfoEcoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.productEco.model.inter.IPlmProductSupplierInfoEco;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductSupplierInfoEcoServer")
public class PlmProductSupplierInfoEcoServer extends BaseCommonServer<PlmProductSupplierInfoEcoEntity_HI> implements IPlmProductSupplierInfoEco{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductSupplierInfoEcoServer.class);

	@Autowired
	private ViewObject<PlmProductSupplierInfoEcoEntity_HI> plmProductSupplierInfoEcoDAO_HI;

	public PlmProductSupplierInfoEcoServer() {
		super();
	}

}
