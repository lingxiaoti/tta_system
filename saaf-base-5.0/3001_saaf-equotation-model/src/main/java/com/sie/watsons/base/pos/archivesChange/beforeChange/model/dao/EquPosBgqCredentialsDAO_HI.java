package com.sie.watsons.base.pos.archivesChange.beforeChange.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.EquPosBgqCredentialsEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosBgqCredentialsDAO_HI")
public class EquPosBgqCredentialsDAO_HI extends BaseCommonDAO_HI<EquPosBgqCredentialsEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosBgqCredentialsDAO_HI.class);

	public EquPosBgqCredentialsDAO_HI() {
		super();
	}

}
