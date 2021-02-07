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
import com.sie.watsons.base.productEco.model.entities.PlmProductPriceEcoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.productEco.model.inter.IPlmProductPriceEco;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductPriceEcoServer")
public class PlmProductPriceEcoServer extends BaseCommonServer<PlmProductPriceEcoEntity_HI> implements IPlmProductPriceEco{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPriceEcoServer.class);

	@Autowired
	private ViewObject<PlmProductPriceEcoEntity_HI> plmProductPriceEcoDAO_HI;

	public PlmProductPriceEcoServer() {
		super();
	}

}
