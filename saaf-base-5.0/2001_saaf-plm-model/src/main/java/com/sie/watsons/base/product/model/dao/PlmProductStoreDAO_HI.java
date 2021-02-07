package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductStoreEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductStoreDAO_HI")
public class PlmProductStoreDAO_HI extends BaseCommonDAO_HI<PlmProductStoreEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductStoreDAO_HI.class);

	public PlmProductStoreDAO_HI() {
		super();
	}

}
