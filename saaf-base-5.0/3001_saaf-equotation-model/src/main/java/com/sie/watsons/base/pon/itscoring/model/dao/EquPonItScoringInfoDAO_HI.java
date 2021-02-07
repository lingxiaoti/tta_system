package com.sie.watsons.base.pon.itscoring.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringInfoEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonItScoringInfoDAO_HI")
public class EquPonItScoringInfoDAO_HI extends BaseCommonDAO_HI<EquPonItScoringInfoEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringInfoDAO_HI.class);

	public EquPonItScoringInfoDAO_HI() {
		super();
	}

}
