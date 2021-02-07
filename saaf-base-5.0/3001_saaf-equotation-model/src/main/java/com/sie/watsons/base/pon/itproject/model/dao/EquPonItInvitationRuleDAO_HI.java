package com.sie.watsons.base.pon.itproject.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItInvitationRuleEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("equPonItInvitationRuleDAO_HI")
public class EquPonItInvitationRuleDAO_HI extends BaseCommonDAO_HI<EquPonItInvitationRuleEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPonItInvitationRuleDAO_HI.class);

	public EquPonItInvitationRuleDAO_HI() {
		super();
	}

}
