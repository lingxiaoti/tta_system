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
import com.sie.watsons.base.product.model.entities.PlmProductDrugEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmProductDrug;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductDrugServer")
public class PlmProductDrugServer extends BaseCommonServer<PlmProductDrugEntity_HI> implements IPlmProductDrug{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductDrugServer.class);

	@Autowired
	private ViewObject<PlmProductDrugEntity_HI> plmProductDrugDAO_HI;

	public PlmProductDrugServer() {
		super();
	}

}
