package com.sie.watsons.base.pon.itproject.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItScoringTeamEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonItScoringTeamDAO_HI")
public class EquPonItScoringTeamDAO_HI extends BaseCommonDAO_HI<EquPonItScoringTeamEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringTeamDAO_HI.class);

	public EquPonItScoringTeamDAO_HI() {
		super();
	}

}
