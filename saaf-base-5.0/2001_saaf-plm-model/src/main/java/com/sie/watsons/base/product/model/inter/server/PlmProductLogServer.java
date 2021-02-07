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
import com.sie.watsons.base.product.model.entities.PlmProductLogEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmProductLog;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductLogServer")
public class PlmProductLogServer extends BaseCommonServer<PlmProductLogEntity_HI> implements IPlmProductLog{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductLogServer.class);

	@Autowired
	private ViewObject<PlmProductLogEntity_HI> plmProductLogDAO_HI;

	public PlmProductLogServer() {
		super();
	}

}
