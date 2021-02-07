package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentIngredientsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmDevelopmentIngredientsDAO_HI")
public class PlmDevelopmentIngredientsDAO_HI extends BaseCommonDAO_HI<PlmDevelopmentIngredientsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentIngredientsDAO_HI.class);
	public PlmDevelopmentIngredientsDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmDevelopmentIngredientsEntity_HI entity) {
		return super.save(entity);
	}
}
