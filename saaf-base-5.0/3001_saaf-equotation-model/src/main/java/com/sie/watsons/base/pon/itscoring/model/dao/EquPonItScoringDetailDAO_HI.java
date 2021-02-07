package com.sie.watsons.base.pon.itscoring.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itscoring.model.entities.EquPonItScoringDetailEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonItScoringDetailDAO_HI")
public class EquPonItScoringDetailDAO_HI extends BaseCommonDAO_HI<EquPonItScoringDetailEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItScoringDetailDAO_HI.class);

	public EquPonItScoringDetailDAO_HI() {
		super();
	}

}
