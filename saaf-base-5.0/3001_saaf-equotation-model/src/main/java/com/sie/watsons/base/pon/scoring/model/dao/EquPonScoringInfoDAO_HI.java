package com.sie.watsons.base.pon.scoring.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.scoring.model.entities.EquPonScoringInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonScoringInfoDAO_HI")
public class EquPonScoringInfoDAO_HI extends BaseCommonDAO_HI<EquPonScoringInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonScoringInfoDAO_HI.class);

	public EquPonScoringInfoDAO_HI() {
		super();
	}

}
