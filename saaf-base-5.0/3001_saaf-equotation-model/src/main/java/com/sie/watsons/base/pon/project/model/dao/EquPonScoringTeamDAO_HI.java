package com.sie.watsons.base.pon.project.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonScoringTeamEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonScoringTeamDAO_HI")
public class EquPonScoringTeamDAO_HI extends BaseCommonDAO_HI<EquPonScoringTeamEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonScoringTeamDAO_HI.class);

	public EquPonScoringTeamDAO_HI() {
		super();
	}

}
