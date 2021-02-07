package com.sie.watsons.base.productEco.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductPicfileTableEcoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductPicfileTableEcoDAO_HI")
public class PlmProductPicfileTableEcoDAO_HI extends BaseCommonDAO_HI<PlmProductPicfileTableEcoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductPicfileTableEcoDAO_HI.class);

	public PlmProductPicfileTableEcoDAO_HI() {
		super();
	}

}
