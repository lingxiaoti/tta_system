package com.sie.saaf.rule.constant;

/**
 * 规则引擎常量类-新建
 *
 * @author Zhangbingshan(sam)
 * @email bszzhang@qq.com
 * @date 2019-01-25 17:43
 */
public interface RuleConstant {

    String RULE_ACTION_INSTANCE = "ruleActionInstance";

    String RULE_BUSINESS_POINT = "businessPoint";

    //执行数学公式的方式,对应块码：RULE_ACTION_FORMULA
    String RULE_ACTION_FORMULA_NO = "N";
    String RULE_ACTION_FORMULA_IN = "IN_ACTION";
    String RULE_ACTION_FORMULA_BEFORE = "BEFORE_ACTION";
}
