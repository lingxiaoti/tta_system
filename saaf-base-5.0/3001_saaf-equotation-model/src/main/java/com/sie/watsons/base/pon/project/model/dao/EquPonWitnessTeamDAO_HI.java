package com.sie.watsons.base.pon.project.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.project.model.entities.EquPonWitnessTeamEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonWitnessTeamDAO_HI")
public class EquPonWitnessTeamDAO_HI extends BaseCommonDAO_HI<EquPonWitnessTeamEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonWitnessTeamDAO_HI.class);

	public EquPonWitnessTeamDAO_HI() {
		super();
	}

}
