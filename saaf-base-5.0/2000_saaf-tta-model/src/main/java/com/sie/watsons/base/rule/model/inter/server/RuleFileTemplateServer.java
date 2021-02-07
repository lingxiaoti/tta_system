package com.sie.watsons.base.rule.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.rule.model.dao.readonly.RuleFileTemplateDAO_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.RuleFileTemplateEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.RuleFileTemplateEntity_HI;
import com.sie.watsons.base.rule.model.inter.IRuleFileTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ruleFileTemplateServer")
public class RuleFileTemplateServer extends BaseCommonServer<RuleFileTemplateEntity_HI> implements IRuleFileTemplate {
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleFileTemplateServer.class);

	@Autowired
	private ViewObject<RuleFileTemplateEntity_HI> ruleFileTemplateDAO_HI;

	@Autowired
	private RuleFileTemplateDAO_HI_RO tempRuleDefDAO_HI_RO;

	public RuleFileTemplateServer() {
		super();
	}

	@Override
	public RuleFileTemplateEntity_HI_RO findByBusinessType(String businessType) {


		StringBuffer sql = new StringBuffer();
		sql.append(RuleFileTemplateEntity_HI_RO.QUERY_SQL);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(new JSONObject().fluentPut("businessType", businessType), "t.business_type", "businessType", sql, paramsMap, "=");
		List<RuleFileTemplateEntity_HI_RO> list = tempRuleDefDAO_HI_RO.findList(sql.toString(), paramsMap);
		if (list != null) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateRuleFile(RuleFileTemplateEntity_HI entity_hi) {
		List<RuleFileTemplateEntity_HI> list = ruleFileTemplateDAO_HI.findByProperty(new JSONObject().fluentPut("businessType", entity_hi.getBusinessType()));
		if (list != null) {
			ruleFileTemplateDAO_HI.deleteAll(list);
		}
		ruleFileTemplateDAO_HI.save(entity_hi);
	}
}
