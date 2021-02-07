package com.sie.watsons.base.product.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductDrugfileEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmProductDrugfile;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductDrugfileServer")
public class PlmProductDrugfileServer extends BaseCommonServer<PlmProductDrugfileEntity_HI> implements IPlmProductDrugfile{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductDrugfileServer.class);

	@Autowired
	private ViewObject<PlmProductDrugfileEntity_HI> plmProductDrugfileDAO_HI;

	public PlmProductDrugfileServer() {
		super();
	}

}
