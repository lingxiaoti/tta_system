package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductDrugfileEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductDrugfileDAO_HI")
public class PlmProductDrugfileDAO_HI extends BaseCommonDAO_HI<PlmProductDrugfileEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductDrugfileDAO_HI.class);

	public PlmProductDrugfileDAO_HI() {
		super();
	}

}
