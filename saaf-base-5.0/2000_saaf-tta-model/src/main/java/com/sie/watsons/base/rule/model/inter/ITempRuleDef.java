package com.sie.watsons.base.rule.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.TempRuleDefEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface ITempRuleDef extends IBaseCommon<TempRuleDefEntity_HI>{

	
	
	/**
	 * 分页查询规则信息
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	Pagination<TempRuleDefEntity_HI_RO> findRulePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);


	/**
	 * 功能描述： 获取规则信息
	 * @author xiaoga
	 * @date 2019/5/30
	 * @param
	 * @return
	 */
	public TempRuleDefEntity_HI_RO findRuleById(Integer ruleId);


	/**
	 * 功能描述： 查询规则参数信息
	 * @author xiaoga
	 * @date 2019/5/30
	 * @param
	 * @return
	 */
	public Pagination<TempParamDefEntity_HI_RO> findParams(JSONObject jsonObject, Integer pageIndex, Integer pageRows);


	/**
	 * 功能描述： 保存或更新规则定义
	 * @author xiaoga
	 * @date 2019/5/31
	 * @param
	 * @return
	 */
	public void saveOrupdate(TempRuleDefEntity_HI entity_hi);

	/**
	 * 功能描述： 参数被哪些规则引用
	 * @author xiaoga
	 * @date 2019/8/15
	 * @param
	 * @return
	 */
	public List<TempRuleDefEntity_HI_RO> findRuleNameByParam(Integer paramId);

	/**
	 * 获取文档的输入流
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	JSONObject getDocInputStream(Integer ruleId) throws Exception;

	TempRuleDefEntity_HI_RO findRuleOne(JSONObject jsonObject);
}
