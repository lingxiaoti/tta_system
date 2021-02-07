package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSpecialProductTypeEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSpecialProductTypeDAO_HI")
public class PlmSpecialProductTypeDAO_HI extends BaseCommonDAO_HI<PlmSpecialProductTypeEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSpecialProductTypeDAO_HI.class);
	public PlmSpecialProductTypeDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSpecialProductTypeEntity_HI entity) {
		return super.save(entity);
	}
}
