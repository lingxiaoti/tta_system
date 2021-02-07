package com.sie.watsons.base.productEco.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductPriceEcoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductPriceEcoDAO_HI")
public class PlmProductPriceEcoDAO_HI extends BaseCommonDAO_HI<PlmProductPriceEcoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPriceEcoDAO_HI.class);

	public PlmProductPriceEcoDAO_HI() {
		super();
	}

}
