package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmCountryOfOriginEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmCountryOfOriginDAO_HI")
public class PlmCountryOfOriginDAO_HI extends BaseCommonDAO_HI<PlmCountryOfOriginEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmCountryOfOriginDAO_HI.class);
	public PlmCountryOfOriginDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmCountryOfOriginEntity_HI entity) {
		return super.save(entity);
	}
}
