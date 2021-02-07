package com.sie.saaf.rule.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.rule.model.entities.RuleActionEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleActionEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleAction;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:该表定义的是规则引擎中，表达式匹配成功后需要执行的服务定义，非实例
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
@Component("ruleActionServer")
public class RuleActionServer extends BaseCommonServer<RuleActionEntity_HI> implements IRuleAction {
	
	@Autowired
	private ViewObject<RuleActionEntity_HI> ruleActionDAO_HI;
	
	@Autowired
	private BaseViewObject<RuleActionEntity_HI_RO> ruleActionDAO_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;
	
	@Override
	public Pagination<RuleActionEntity_HI_RO> findRuleActionPage(
			JSONObject parameters, Integer pageIndex, Integer pageRows)
			throws Exception {
		
		StringBuffer pageSql = new StringBuffer(RuleActionEntity_HI_RO.QUERY_SQL);
		Map<String, Object> conditonMap = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters,"t.action_name","actionName",pageSql,conditonMap,"fulllike");
		SaafToolUtils.parperParam(parameters,"t.action_code","actionCode",pageSql,conditonMap,"fulllike");

		Pagination<RuleActionEntity_HI_RO> page = ruleActionDAO_HI_RO.findPagination(pageSql, conditonMap, pageIndex, pageRows);
		return page;
	}
	
	@Override
	public RuleActionEntity_HI_RO getRuleAction(Integer executeActionId)
			throws Exception {
		StringBuffer detailSql = new StringBuffer(RuleActionEntity_HI_RO.QUERY_SQL);
		Map<String, Object> conditonMap = new HashMap<String, Object>();
		conditonMap.put("executeActionId", executeActionId);
		detailSql.append(" and t.execute_action_id =:executeActionId ");
		
		List<RuleActionEntity_HI_RO> ruleActionList = ruleActionDAO_HI_RO.findList(detailSql, conditonMap);
		if(ruleActionList.size() >0) {
			return ruleActionList.get(0);
		}
		return null;
	}
	
	@Override
	public  RuleActionEntity_HI saveRuleAction(JSONObject parameters) throws Exception {
		Integer userId = parameters.getInteger("varUserId");
		if(userId == null) {
			userId = -999;
		}
		RuleActionEntity_HI ruleAction = JSONObject.toJavaObject(parameters, RuleActionEntity_HI.class);
		if(StringUtils.isBlank(ruleAction.getActionCode())) {
			String actionCode = generateCodeServer.generateCode("RA_",new SimpleDateFormat("yyyyMMdd"),4);
			ruleAction.setActionCode(actionCode);
		}
		ruleAction.setOperatorUserId(userId);
		ruleActionDAO_HI.saveOrUpdate(ruleAction);
		return ruleAction;
	}
	
	@Override
	public String deleteRuleAction(Integer executeActionId) throws Exception {
		ruleActionDAO_HI.delete(executeActionId);
		return null;
	}

}
