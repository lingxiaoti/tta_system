package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmProjectProductDetailEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProjectProductDetailDAO_HI")
public class PlmProjectProductDetailDAO_HI extends BaseCommonDAO_HI<PlmProjectProductDetailEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProjectProductDetailDAO_HI.class);
	public PlmProjectProductDetailDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmProjectProductDetailEntity_HI entity) {
		return super.save(entity);
	}
}
