package com.sie.watsons.base.pos.spendUpdate.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pos.spendUpdate.model.entities.EquPosSpendUpdateLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPosSpendUpdateLineDAO_HI")
public class EquPosSpendUpdateLineDAO_HI extends BaseCommonDAO_HI<EquPosSpendUpdateLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSpendUpdateLineDAO_HI.class);

	public EquPosSpendUpdateLineDAO_HI() {
		super();
	}

}
