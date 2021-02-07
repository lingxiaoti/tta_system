package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductBpmPersonEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductBpmPersonDAO_HI")
public class PlmProductBpmPersonDAO_HI extends BaseCommonDAO_HI<PlmProductBpmPersonEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductBpmPersonDAO_HI.class);

	public PlmProductBpmPersonDAO_HI() {
		super();
	}

}
