package com.sie.watsons.base.plmBase.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmBaseClassEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("plmBaseClassDAO_HI")
public class PlmBaseClassDAO_HI extends BaseCommonDAO_HI<PlmBaseClassEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmBaseClassDAO_HI.class);

	public PlmBaseClassDAO_HI() {
		super();
	}

}
