package com.sie.watsons.base.pos.qualityUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.EquPosQualityUpdateHeadEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosQualityUpdateHeadDAO_HI")
public class EquPosQualityUpdateHeadDAO_HI extends BaseCommonDAO_HI<EquPosQualityUpdateHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualityUpdateHeadDAO_HI.class);

	public EquPosQualityUpdateHeadDAO_HI() {
		super();
	}

}
