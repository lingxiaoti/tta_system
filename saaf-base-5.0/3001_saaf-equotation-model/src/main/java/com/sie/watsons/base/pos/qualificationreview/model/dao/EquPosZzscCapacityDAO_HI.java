package com.sie.watsons.base.pos.qualificationreview.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCapacityEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosZzscCapacityDAO_HI")
public class EquPosZzscCapacityDAO_HI extends BaseCommonDAO_HI<EquPosZzscCapacityEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosZzscCapacityDAO_HI.class);

	public EquPosZzscCapacityDAO_HI() {
		super();
	}

}
