package com.sie.watsons.base.rule.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.rule.model.entities.TtaBaseRuleHeaderEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaBaseRuleHeaderDAO_HI")
public class TtaBaseRuleHeaderDAO_HI extends BaseCommonDAO_HI<TtaBaseRuleHeaderEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBaseRuleHeaderDAO_HI.class);

	public TtaBaseRuleHeaderDAO_HI() {
		super();
	}

}
