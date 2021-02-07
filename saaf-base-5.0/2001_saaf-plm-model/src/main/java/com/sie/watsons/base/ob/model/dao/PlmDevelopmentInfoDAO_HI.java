package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmDevelopmentInfoDAO_HI")
public class PlmDevelopmentInfoDAO_HI extends BaseCommonDAO_HI<PlmDevelopmentInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentInfoDAO_HI.class);
	public PlmDevelopmentInfoDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmDevelopmentInfoEntity_HI entity) {
		return super.save(entity);
	}
}
