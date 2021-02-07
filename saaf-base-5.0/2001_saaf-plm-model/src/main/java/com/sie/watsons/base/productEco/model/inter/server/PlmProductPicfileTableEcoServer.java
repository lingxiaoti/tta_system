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
import com.sie.watsons.base.productEco.model.entities.PlmProductPicfileTableEcoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.productEco.model.inter.IPlmProductPicfileTableEco;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductPicfileTableEcoServer")
public class PlmProductPicfileTableEcoServer extends BaseCommonServer<PlmProductPicfileTableEcoEntity_HI> implements IPlmProductPicfileTableEco{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPicfileTableEcoServer.class);

	@Autowired
	private ViewObject<PlmProductPicfileTableEcoEntity_HI> plmProductPicfileTableEcoDAO_HI;

	public PlmProductPicfileTableEcoServer() {
		super();
	}

}
