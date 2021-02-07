package com.sie.watsons.base.pos.spendUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.spendUpdate.model.entities.EquPosSpendUpdateHeadEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSpendUpdateHeadDAO_HI")
public class EquPosSpendUpdateHeadDAO_HI extends BaseCommonDAO_HI<EquPosSpendUpdateHeadEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSpendUpdateHeadDAO_HI.class);

	public EquPosSpendUpdateHeadDAO_HI() {
		super();
	}

}
