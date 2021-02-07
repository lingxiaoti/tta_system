package com.sie.watsons.base.product.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.product.model.entities.PlmProductMedicalinfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductMedicalinfoDAO_HI")
public class PlmProductMedicalinfoDAO_HI extends BaseCommonDAO_HI<PlmProductMedicalinfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductMedicalinfoDAO_HI.class);

	public PlmProductMedicalinfoDAO_HI() {
		super();
	}

}
