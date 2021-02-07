package com.sie.saaf.rule.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.rule.model.entities.RuleExpressionEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressionEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IRuleExpression extends IBaseCommon<RuleExpressionEntity_HI> {


    RuleExpressionEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException;

    boolean delete(Integer id);

    Pagination<RuleExpressionEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize);

    List<RuleExpressionEntity_HI> findByProperty(String name, Object value);

    List<RuleExpressionEntity_HI> findByProperty(Map<String, Object> map);

    RuleExpressionEntity_HI findById(Integer id);

    Pagination<RuleExpressionEntity_HI_RO> findRuleExpressionPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //通过相关参数来获取特定的规则表达式
    List<RuleExpressionEntity_HI_RO> getRuleExpressionByCondition(JSONObject conditonMap) throws Exception;

    //通过业务线和业务点来检验表达式的唯一性
    boolean validateExpressionUnique(String ruleBusinessLineCode, String ruleBusinessPoint) throws Exception;

    boolean validateRuleExpWeight(Integer ruleExpWeight, String ruleBusinessLineCode) throws Exception;

}