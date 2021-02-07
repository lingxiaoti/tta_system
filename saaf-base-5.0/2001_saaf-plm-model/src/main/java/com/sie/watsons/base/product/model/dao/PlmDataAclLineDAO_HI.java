package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmDataAclLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmDataAclLineDAO_HI")
public class PlmDataAclLineDAO_HI extends BaseCommonDAO_HI<PlmDataAclLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDataAclLineDAO_HI.class);

	public PlmDataAclLineDAO_HI() {
		super();
	}

}
