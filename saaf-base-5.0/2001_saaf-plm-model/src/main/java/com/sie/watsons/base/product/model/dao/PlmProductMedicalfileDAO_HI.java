package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductMedicalfileEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductMedicalfileDAO_HI")
public class PlmProductMedicalfileDAO_HI extends BaseCommonDAO_HI<PlmProductMedicalfileEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductMedicalfileDAO_HI.class);

	public PlmProductMedicalfileDAO_HI() {
		super();
	}

}
