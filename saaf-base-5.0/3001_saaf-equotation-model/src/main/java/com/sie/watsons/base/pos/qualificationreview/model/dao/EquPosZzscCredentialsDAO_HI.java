package com.sie.watsons.base.pos.qualificationreview.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCredentialsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosZzscCredentialsDAO_HI")
public class EquPosZzscCredentialsDAO_HI extends BaseCommonDAO_HI<EquPosZzscCredentialsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCredentialsDAO_HI.class);

	public EquPosZzscCredentialsDAO_HI() {
		super();
	}

}
