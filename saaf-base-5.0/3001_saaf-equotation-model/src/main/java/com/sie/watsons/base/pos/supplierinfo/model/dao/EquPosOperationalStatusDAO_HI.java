package com.sie.watsons.base.pos.supplierinfo.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosOperationalStatusEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosOperationalStatusDAO_HI")
public class EquPosOperationalStatusDAO_HI extends BaseCommonDAO_HI<EquPosOperationalStatusEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosOperationalStatusDAO_HI.class);

	public EquPosOperationalStatusDAO_HI() {
		super();
	}

}
