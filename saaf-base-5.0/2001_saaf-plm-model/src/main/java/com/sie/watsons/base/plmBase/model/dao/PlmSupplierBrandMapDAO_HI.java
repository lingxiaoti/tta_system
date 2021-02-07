package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierBrandMapEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmSupplierBrandMapDAO_HI")
public class PlmSupplierBrandMapDAO_HI extends BaseCommonDAO_HI<PlmSupplierBrandMapEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierBrandMapDAO_HI.class);

	public PlmSupplierBrandMapDAO_HI() {
		super();
	}

}
