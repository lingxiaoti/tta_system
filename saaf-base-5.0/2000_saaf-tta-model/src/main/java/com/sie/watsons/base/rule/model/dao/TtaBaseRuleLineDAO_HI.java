package com.sie.watsons.base.rule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.rule.model.entities.TtaBaseRuleLineEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaBaseRuleLineDAO_HI")
public class TtaBaseRuleLineDAO_HI extends BaseCommonDAO_HI<TtaBaseRuleLineEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBaseRuleLineDAO_HI.class);

	public TtaBaseRuleLineDAO_HI() {
		super();
	}

}
