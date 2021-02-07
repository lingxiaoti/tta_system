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
import com.sie.watsons.base.product.model.entities.PlmDataAclLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmDataAclLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmDataAclLineServer")
public class PlmDataAclLineServer extends BaseCommonServer<PlmDataAclLineEntity_HI> implements IPlmDataAclLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDataAclLineServer.class);

	@Autowired
	private ViewObject<PlmDataAclLineEntity_HI> plmDataAclLineDAO_HI;

	public PlmDataAclLineServer() {
		super();
	}

}
