package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmDataAclHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmDataAclHeaderDAO_HI")
public class PlmDataAclHeaderDAO_HI extends BaseCommonDAO_HI<PlmDataAclHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDataAclHeaderDAO_HI.class);

	public PlmDataAclHeaderDAO_HI() {
		super();
	}

}
