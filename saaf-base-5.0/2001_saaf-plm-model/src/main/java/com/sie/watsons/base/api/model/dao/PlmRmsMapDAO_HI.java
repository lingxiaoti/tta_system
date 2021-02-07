package com.sie.watsons.base.api.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.api.model.entities.PlmRmsMapEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmRmsMapDAO_HI")
public class PlmRmsMapDAO_HI extends BaseCommonDAO_HI<PlmRmsMapEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmRmsMapDAO_HI.class);

	public PlmRmsMapDAO_HI() {
		super();
	}

}
