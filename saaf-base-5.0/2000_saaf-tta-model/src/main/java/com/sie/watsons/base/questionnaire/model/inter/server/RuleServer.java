package com.sie.watsons.base.questionnaire.model.inter.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.questionnaire.model.entities.RuleEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.RulePerson_HI_RO;
import com.sie.watsons.base.questionnaire.model.inter.IRule;
import com.yhg.hibernate.core.dao.DynamicViewObjectImpl;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * 对用户表Base_Users进行CRUD操作<br>
 *
 * @author ZhangGangHui
 * @creteTime 2019-6-4
 */
@Component("answerServer")
public class RuleServer extends BaseCommonServer<RuleEntity_HI> implements IRule {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleServer.class);

    @Autowired
    private BaseCommonDAO_HI<RuleEntity_HI> ruleEntity;
    
    @Autowired
    private DynamicViewObjectImpl<RulePerson_HI_RO> rulePerson;
	@Autowired
	private ViewObject<RuleEntity_HI> ruleDAO_HI;
        
    public RuleServer() {
        super();
    }
    
    /**
     * 
     * @param parameters
     * @return
     */
	@Override
	public RuleEntity_HI saveRule(JSONObject params,Integer userId) throws Exception {
		RuleEntity_HI instance = SaafToolUtils.setEntity(RuleEntity_HI.class, params, ruleDAO_HI, userId);
		ruleDAO_HI.saveOrUpdate(instance);
		return  instance ;
	}
	
	/**
     * 删除规则
     * @param workId
     * @return
     */
	@Override
	public void deleteRule(Integer ruleId) throws Exception {		
		ruleEntity.delete(ruleId);		
	}
	
	/**
     * 查询规则
     * @param parameters
     * @param pageIndex
     * @param pageRows
     * @return
     */
	@Override
	public Pagination<RulePerson_HI_RO> queryRulePage(JSONObject parameters, Integer pageIndex,
			Integer pageRows) throws Exception {
		StringBuffer queryString = new StringBuffer(RulePerson_HI_RO.QUERY_SQL);    
        Map<String, Object> map = new HashMap<String, Object>();
		SaafToolUtils.parperParam(parameters, "tbr.RULE_NAME", "ruleName", queryString, map, "like");
		SaafToolUtils.parperParam(parameters, "tbr.remarks", "remarks", queryString, map, "like");
		SaafToolUtils.parperParam(parameters, "tbr.BUSINESS_TYPE", "businessType", queryString, map, "like");
		SaafToolUtils.changeQuerySort(parameters,queryString," tbr.RULE_ID desc ",false);
		Pagination<RulePerson_HI_RO> pagination = rulePerson.findPagination(queryString,SaafToolUtils.getSqlCountString(queryString),map, pageIndex, pageRows);
		return pagination;
	}

	@Override
	public List<RulePerson_HI_RO> queryRuleList(JSONObject parameters)
			throws Exception {
		StringBuffer queryString = new StringBuffer(RulePerson_HI_RO.QUERY_SQL);    
        Map<String, Object> map = new HashMap<String, Object>(); 
        List<RulePerson_HI_RO> ruleList = rulePerson.findList(queryString, map);
		return ruleList;
	}

	@Override
	public RulePerson_HI_RO queryRule(JSONObject parameters) throws Exception {
		StringBuffer queryString = new StringBuffer(RulePerson_HI_RO.QUERY_SQL);    
        Map<String, Object> map = new HashMap<String, Object>(); 
        List<RulePerson_HI_RO> ruleList = rulePerson.findList(queryString, map);
        if (ruleList.size() > 0) {
			return ruleList.get(0);
		}
		return null;
	}

}
