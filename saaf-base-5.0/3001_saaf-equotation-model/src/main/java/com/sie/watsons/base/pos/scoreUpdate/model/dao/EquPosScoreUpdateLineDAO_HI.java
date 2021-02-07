package com.sie.watsons.base.pos.scoreUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.EquPosScoreUpdateLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosScoreUpdateLineDAO_HI")
public class EquPosScoreUpdateLineDAO_HI extends BaseCommonDAO_HI<EquPosScoreUpdateLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosScoreUpdateLineDAO_HI.class);

	public EquPosScoreUpdateLineDAO_HI() {
		super();
	}

}
