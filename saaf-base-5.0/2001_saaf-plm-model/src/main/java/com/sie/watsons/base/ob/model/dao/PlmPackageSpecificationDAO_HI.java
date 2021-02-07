package com.sie.watsons.base.ob.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ob.model.entities.PlmPackageSpecificationEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmPackageSpecificationDAO_HI")
public class PlmPackageSpecificationDAO_HI extends BaseCommonDAO_HI<PlmPackageSpecificationEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmPackageSpecificationDAO_HI.class);
	public PlmPackageSpecificationDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmPackageSpecificationEntity_HI entity) {
		return super.save(entity);
	}
}
