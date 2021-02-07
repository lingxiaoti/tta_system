package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmProductExceptionDetailEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductExceptionDetailDAO_HI")
public class PlmProductExceptionDetailDAO_HI extends BaseCommonDAO_HI<PlmProductExceptionDetailEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductExceptionDetailDAO_HI.class);
	public PlmProductExceptionDetailDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmProductExceptionDetailEntity_HI entity) {
		return super.save(entity);
	}
}
