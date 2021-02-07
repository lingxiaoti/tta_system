package com.sie.watsons.base.questionnaire.model.inter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.questionnaire.model.entities.RuleEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.RulePerson_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

/**
 * 接口：对用户表Base_Users进行CRUD操作<br>
 *
 * @author ZhangGangHui
 * @creteTime 2017-12-11
 */
public interface IRule extends IBaseCommon<RuleEntity_HI> {

	/**
     * 新增规则
     * @param workId
     * @return
     */
	RuleEntity_HI saveRule(JSONObject parameters,Integer userId) throws Exception;
	
	/**
     * 删除规则
     * @param workId
     * @return
     */
	void deleteRule(Integer ruleId) throws Exception;
	
	/**
     * 查询规则
     * @param parameters
     * @param pageIndex
     * @param pageRows
     * @return
     */
	 Pagination<RulePerson_HI_RO> queryRulePage(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

 	/**
     * 查询规则
     * @param parameters
     * @param pageIndex
     * @param pageRows
     * @return
     */
	 List<RulePerson_HI_RO> queryRuleList(JSONObject parameters) throws Exception;
	 
	 /**
     * 查询规则单个
     * @param parameters
     * @param pageIndex
     * @param pageRows
     * @return
     */
	 RulePerson_HI_RO queryRule(JSONObject parameters) throws Exception;


}
