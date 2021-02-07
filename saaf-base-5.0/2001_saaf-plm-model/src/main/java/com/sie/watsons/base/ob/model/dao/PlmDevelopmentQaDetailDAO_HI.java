package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaDetailEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmDevelopmentQaDetailDAO_HI")
public class PlmDevelopmentQaDetailDAO_HI extends BaseCommonDAO_HI<PlmDevelopmentQaDetailEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentQaDetailDAO_HI.class);
	public PlmDevelopmentQaDetailDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmDevelopmentQaDetailEntity_HI entity) {
		return super.save(entity);
	}
}
