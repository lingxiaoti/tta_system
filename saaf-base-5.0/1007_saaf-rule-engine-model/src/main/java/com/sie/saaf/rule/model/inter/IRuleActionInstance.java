package com.sie.saaf.rule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.RuleActionInstanceEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleActionInstanceEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * @Description:规则引擎执行服务实例表
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
public interface IRuleActionInstance extends IBaseCommon<RuleActionInstanceEntity_HI> {
	
	/**
	 * 
	 * @Description:分页查询
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	Pagination<RuleActionInstanceEntity_HI_RO> findRuleActionInstancePage(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;
	
	/**
	 * 
	 * @Description: 详情情获取
	 * @param actionInstanceId
	 * @return
	 * @throws Exception
	 */
	RuleActionInstanceEntity_HI_RO getRuleActionInstance(Integer actionInstanceId) throws Exception;
	
	/**
	 * 
	 * @Description: 保存
	 * @param params
	 * @return
	 * @throws Exception
	 */
	RuleActionInstanceEntity_HI saveRuleActionInstance(JSONObject parameters) throws Exception;
	
	/**
	 * 
	 * @Description: 删除
	 * @param actionInstanceId
	 * @return
	 * @throws Exception
	 */
	String deleteRuleActionInstance(Integer actionInstanceId) throws Exception;

	List<RuleActionInstanceEntity_HI_RO> findRuleActionInstanceList(String ruleExpCode) throws Exception;
}