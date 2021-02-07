package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentQaSummaryEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmDevelopmentQaSummaryDAO_HI")
public class PlmDevelopmentQaSummaryDAO_HI extends BaseCommonDAO_HI<PlmDevelopmentQaSummaryEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentQaSummaryDAO_HI.class);
	public PlmDevelopmentQaSummaryDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmDevelopmentQaSummaryEntity_HI entity) {
		return super.save(entity);
	}
}
