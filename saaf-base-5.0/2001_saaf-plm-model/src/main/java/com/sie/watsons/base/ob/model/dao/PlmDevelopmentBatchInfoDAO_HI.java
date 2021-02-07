package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentBatchInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmDevelopmentBatchInfoDAO_HI")
public class PlmDevelopmentBatchInfoDAO_HI extends BaseCommonDAO_HI<PlmDevelopmentBatchInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentBatchInfoDAO_HI.class);
	public PlmDevelopmentBatchInfoDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmDevelopmentBatchInfoEntity_HI entity) {
		return super.save(entity);
	}
}
