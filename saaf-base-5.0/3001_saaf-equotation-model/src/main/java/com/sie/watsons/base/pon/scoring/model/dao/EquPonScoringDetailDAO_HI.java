package com.sie.watsons.base.pon.scoring.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringDetailEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonScoringDetailDAO_HI")
public class EquPonScoringDetailDAO_HI extends BaseCommonDAO_HI<EquPonScoringDetailEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonScoringDetailDAO_HI.class);

	public EquPonScoringDetailDAO_HI() {
		super();
	}

}
