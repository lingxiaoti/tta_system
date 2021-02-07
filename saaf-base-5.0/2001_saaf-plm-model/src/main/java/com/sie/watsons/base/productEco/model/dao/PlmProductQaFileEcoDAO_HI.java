package com.sie.watsons.base.productEco.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductQaFileEcoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmProductQaFileEcoDAO_HI")
public class PlmProductQaFileEcoDAO_HI extends BaseCommonDAO_HI<PlmProductQaFileEcoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductQaFileEcoDAO_HI.class);

	public PlmProductQaFileEcoDAO_HI() {
		super();
	}

}
