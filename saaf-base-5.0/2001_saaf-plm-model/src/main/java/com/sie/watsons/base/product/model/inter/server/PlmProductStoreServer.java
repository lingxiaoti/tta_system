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
import com.sie.watsons.base.product.model.entities.PlmProductStoreEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmProductStore;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductStoreServer")
public class PlmProductStoreServer extends BaseCommonServer<PlmProductStoreEntity_HI> implements IPlmProductStore{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductStoreServer.class);

	@Autowired
	private ViewObject<PlmProductStoreEntity_HI> plmProductStoreDAO_HI;

	public PlmProductStoreServer() {
		super();
	}

}
