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
import com.sie.watsons.base.productEco.model.entities.PlmProductQaFileEcoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.productEco.model.inter.IPlmProductQaFileEco;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductQaFileEcoServer")
public class PlmProductQaFileEcoServer extends BaseCommonServer<PlmProductQaFileEcoEntity_HI> implements IPlmProductQaFileEco{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductQaFileEcoServer.class);

	@Autowired
	private ViewObject<PlmProductQaFileEcoEntity_HI> plmProductQaFileEcoDAO_HI;

	public PlmProductQaFileEcoServer() {
		super();
	}

}
