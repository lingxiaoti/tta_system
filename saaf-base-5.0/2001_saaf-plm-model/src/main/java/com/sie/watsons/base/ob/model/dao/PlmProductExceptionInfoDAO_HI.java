package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmProductExceptionInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductExceptionInfoDAO_HI")
public class PlmProductExceptionInfoDAO_HI extends BaseCommonDAO_HI<PlmProductExceptionInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductExceptionInfoDAO_HI.class);
	public PlmProductExceptionInfoDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmProductExceptionInfoEntity_HI entity) {
		return super.save(entity);
	}
}
