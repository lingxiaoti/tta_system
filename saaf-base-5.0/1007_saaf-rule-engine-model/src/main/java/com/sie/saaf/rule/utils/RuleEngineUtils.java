package com.sie.saaf.rule.utils;

import com.sie.saaf.rule.model.entities.readonly.RuleActionInstanceEntity_HI_RO;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则引擎工具类
 *
 * @author Zhangbingshan(sam)
 * @email bszzhang@qq.com
 * @date 2019-03-01 14:21
 */
public class RuleEngineUtils {

    //遍历结果返回规则引擎执行动作集合
    public static List<RuleActionInstanceEntity_HI_RO> getRuleActionInstances(List<RuleMatchResult> ruleMatchResultList) {
        List<RuleActionInstanceEntity_HI_RO> result = new ArrayList<>();
        for (RuleMatchResult ruleMatchResult : ruleMatchResultList) {
            if(ruleMatchResult.getStatus()&& ruleMatchResult.isRuleUseAction()&& ruleMatchResult.getRuleActionList() != null) {
                result.addAll(ruleMatchResult.getRuleActionList());
            }
        }
        return result;
    }
}
