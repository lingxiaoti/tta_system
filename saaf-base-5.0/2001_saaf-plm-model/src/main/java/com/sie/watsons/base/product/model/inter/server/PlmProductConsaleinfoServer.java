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
import com.sie.watsons.base.product.model.entities.PlmProductConsaleinfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmProductConsaleinfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductConsaleinfoServer")
public class PlmProductConsaleinfoServer extends BaseCommonServer<PlmProductConsaleinfoEntity_HI> implements IPlmProductConsaleinfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductConsaleinfoServer.class);

	@Autowired
	private ViewObject<PlmProductConsaleinfoEntity_HI> plmProductConsaleinfoDAO_HI;

	public PlmProductConsaleinfoServer() {
		super();
	}

}
