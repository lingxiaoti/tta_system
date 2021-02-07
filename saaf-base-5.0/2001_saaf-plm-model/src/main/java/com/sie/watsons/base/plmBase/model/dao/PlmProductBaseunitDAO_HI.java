package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmProductBaseunitEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductBaseunitDAO_HI")
public class PlmProductBaseunitDAO_HI extends BaseCommonDAO_HI<PlmProductBaseunitEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductBaseunitDAO_HI.class);

	public PlmProductBaseunitDAO_HI() {
		super();
	}

}
