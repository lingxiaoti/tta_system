package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectExceptionEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProjectExceptionDAO_HI")
public class PlmProjectExceptionDAO_HI extends BaseCommonDAO_HI<PlmProjectExceptionEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectExceptionDAO_HI.class);
	public PlmProjectExceptionDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmProjectExceptionEntity_HI entity) {
		return super.save(entity);
	}
}
