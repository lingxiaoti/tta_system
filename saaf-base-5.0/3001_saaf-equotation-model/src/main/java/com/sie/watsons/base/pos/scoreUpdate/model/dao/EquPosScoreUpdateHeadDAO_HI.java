package com.sie.watsons.base.pos.scoreUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.EquPosScoreUpdateHeadEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosScoreUpdateHeadDAO_HI")
public class EquPosScoreUpdateHeadDAO_HI extends BaseCommonDAO_HI<EquPosScoreUpdateHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosScoreUpdateHeadDAO_HI.class);

	public EquPosScoreUpdateHeadDAO_HI() {
		super();
	}

}
