package com.sie.watsons.base.fieldconfig.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.fieldconfig.model.entities.TtaOiFieldMappingEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaOiFieldMappingDAO_HI")
public class TtaOiFieldMappingDAO_HI extends BaseCommonDAO_HI<TtaOiFieldMappingEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiFieldMappingDAO_HI.class);

	public TtaOiFieldMappingDAO_HI() {
		super();
	}

}
