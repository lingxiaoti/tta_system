package com.sie.watsons.report.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.report.model.entities.ActBpmTaskConfigEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("actBpmTaskConfigDAO_HI")
public class ActBpmTaskConfigDAO_HI extends BaseCommonDAO_HI<ActBpmTaskConfigEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActBpmTaskConfigDAO_HI.class);

	public ActBpmTaskConfigDAO_HI() {
		super();
	}

}
