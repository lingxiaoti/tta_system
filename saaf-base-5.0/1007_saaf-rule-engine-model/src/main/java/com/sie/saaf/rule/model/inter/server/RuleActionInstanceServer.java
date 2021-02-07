package com.sie.saaf.rule.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.rule.model.entities.RuleActionInstanceEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleActionInstanceEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleActionInstance;
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
 * @Description:规则引擎执行服务实例表
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
@Component("ruleActionInstanceServer")
public class RuleActionInstanceServer extends BaseCommonServer<RuleActionInstanceEntity_HI> implements IRuleActionInstance {
	
	@Autowired
	private ViewObject<RuleActionInstanceEntity_HI> ruleActionInstanceDAO_HI;
	
	@Autowired
	private BaseViewObject<RuleActionInstanceEntity_HI_RO> ruleActionInstanceDAO_HI_RO;

	@Autowired
	private GenerateCodeServer generateCodeServer;
	
	@Override
	public Pagination<RuleActionInstanceEntity_HI_RO> findRuleActionInstancePage(
			JSONObject parameters, Integer pageIndex, Integer pageRows)
			throws Exception {
		
		StringBuffer pageSql = new StringBuffer(RuleActionInstanceEntity_HI_RO.QUERY_SQL);
		Map<String, Object> conditonMap = new HashMap<String, Object>();
		String ruleExpCode = parameters.getString("ruleExpCode");
		conditonMap.put("ruleExpCode",ruleExpCode);
		Pagination<RuleActionInstanceEntity_HI_RO> page = ruleActionInstanceDAO_HI_RO.findPagination(pageSql, conditonMap, pageIndex, pageRows);
		return page;
	}
	
	@Override
	public RuleActionInstanceEntity_HI_RO getRuleActionInstance(Integer actionInstanceId)
			throws Exception {
		StringBuffer detailSql = new StringBuffer(RuleActionInstanceEntity_HI_RO.QUERY_SQL);
		Map<String, Object> conditonMap = new HashMap<String, Object>();
		conditonMap.put("actionInstanceId", actionInstanceId);
		detailSql.append(" and t.action_instance_id =:actionInstanceId ");
		
		List<RuleActionInstanceEntity_HI_RO> ruleActionInstanceList = ruleActionInstanceDAO_HI_RO.findList(detailSql, conditonMap);
		if(ruleActionInstanceList.size() >0) {
			return ruleActionInstanceList.get(0);
		}
		return null;
	}
	
	@Override
	public  RuleActionInstanceEntity_HI saveRuleActionInstance(JSONObject parameters) throws Exception {
		Integer userId = parameters.getInteger("varUserId");
		if(userId == null) {
			userId = -999;
		}
		RuleActionInstanceEntity_HI ruleActionInstance = JSONObject.toJavaObject(parameters, RuleActionInstanceEntity_HI.class);
		if(StringUtils.isBlank(ruleActionInstance.getActionInstanceCode())) {
			String actionInstanceCode = generateCodeServer.generateCode("RA_INS",new SimpleDateFormat("yyyyMMdd"),3);
			ruleActionInstance.setActionInstanceCode(actionInstanceCode);
		}
		ruleActionInstance.setOperatorUserId(userId);
		ruleActionInstanceDAO_HI.saveOrUpdate(ruleActionInstance);
		return ruleActionInstance;
	}
	
	@Override
	public String deleteRuleActionInstance(Integer actionInstanceId) throws Exception {
		ruleActionInstanceDAO_HI.delete(actionInstanceId);
		return null;
	}

	@Override
	public List<RuleActionInstanceEntity_HI_RO> findRuleActionInstanceList(String ruleExpCode) throws Exception {
		StringBuffer pageSql = new StringBuffer(RuleActionInstanceEntity_HI_RO.QUERY_SQL_RULE);
		Map<String, Object> conditonMap = new HashMap<String, Object>();
		conditonMap.put("ruleExpCode",ruleExpCode);
		List<RuleActionInstanceEntity_HI_RO> ruleActionInstanceList = ruleActionInstanceDAO_HI_RO.findList(pageSql, conditonMap);
		return ruleActionInstanceList;
	}

}
