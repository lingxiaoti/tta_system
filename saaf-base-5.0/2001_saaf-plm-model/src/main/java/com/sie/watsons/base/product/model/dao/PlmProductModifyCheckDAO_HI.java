package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductModifyCheckEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductModifyCheckDAO_HI")
public class PlmProductModifyCheckDAO_HI extends BaseCommonDAO_HI<PlmProductModifyCheckEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductModifyCheckDAO_HI.class);

	public PlmProductModifyCheckDAO_HI() {
		super();
	}

}
