package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierQaBrandEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSupplierQaBrandDAO_HI")
public class PlmSupplierQaBrandDAO_HI extends BaseCommonDAO_HI<PlmSupplierQaBrandEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierQaBrandDAO_HI.class);
	public PlmSupplierQaBrandDAO_HI() {
		super();
	}

	@Override
	public Object save(PlmSupplierQaBrandEntity_HI entity) {
		return super.save(entity);
	}
}
