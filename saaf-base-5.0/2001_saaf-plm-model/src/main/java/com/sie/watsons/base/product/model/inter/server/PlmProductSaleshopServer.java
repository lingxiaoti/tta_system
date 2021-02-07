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
import com.sie.watsons.base.product.model.entities.PlmProductSaleshopEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmProductSaleshop;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductSaleshopServer")
public class PlmProductSaleshopServer extends BaseCommonServer<PlmProductSaleshopEntity_HI> implements IPlmProductSaleshop{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductSaleshopServer.class);

	@Autowired
	private ViewObject<PlmProductSaleshopEntity_HI> plmProductSaleshopDAO_HI;

	public PlmProductSaleshopServer() {
		super();
	}

}
