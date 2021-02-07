package com.sie.saaf.rule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.RuleActionEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleActionEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;


/**
 * @Description:该表定义的是规则引擎中，表达式匹配成功后需要执行的服务定义，非实例
 * 
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-14 15:49:08
 */
public interface IRuleAction extends IBaseCommon<RuleActionEntity_HI> {
	
	/**
	 * 
	 * @Description:分页查询
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	Pagination<RuleActionEntity_HI_RO> findRuleActionPage(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;
	
	/**
	 * 
	 * @Description: 详情情获取
	 * @param executeActionId
	 * @return
	 * @throws Exception
	 */
	RuleActionEntity_HI_RO getRuleAction(Integer executeActionId) throws Exception;
	
	/**
	 * 
	 * @Description: 保存
	 * @return
	 * @throws Exception
	 */
	RuleActionEntity_HI saveRuleAction(JSONObject parameters) throws Exception;
	
	/**
	 * 
	 * @Description: 删除
	 * @param executeActionId
	 * @return
	 * @throws Exception
	 */
	String deleteRuleAction(Integer executeActionId) throws Exception;
}