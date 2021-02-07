package com.sie.saaf.rule.utils;

import com.sie.saaf.rule.model.entities.readonly.RuleActionInstanceEntity_HI_RO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 匹配表达式返回的结果对象信息
 *
 * @author Zhangbingshan(sam)
 * @email bszzhang@qq.com
 * @date 2019-01-18 17:05
 */
public class RuleMatchResult {
    //返回的状态，false为匹配失败，true为匹配成功
    private boolean status;
    //表达式编码
    private String ruleExpCode;
    //匹配成功后返回的表达式信息
    private String msg;

    //是否执行动作，true,false
    private boolean ruleUseAction;

    //表达式匹配成功的返回值，如果有要求的话
    private String ruleValue;

    //执行数学公式后返回值，如果有要求谁用数学公式
    private BigDecimal ruleFormulaReturnValue ;

    //返回执行动作后的结果集合
    private List<RuleActionResult> ruleActionResultList;

    //如果需要执行动作，返回需要执行动作的集合
    private List<RuleActionInstanceEntity_HI_RO> ruleActionList;

    public List<RuleActionInstanceEntity_HI_RO> getRuleActionList() {
        return ruleActionList;
    }

    public void setRuleActionList(List<RuleActionInstanceEntity_HI_RO> ruleActionList) {
        this.ruleActionList = ruleActionList;
    }

    public boolean isRuleUseAction() {
        return ruleUseAction;
    }

    public void setRuleUseAction(boolean ruleUseAction) {
        this.ruleUseAction = ruleUseAction;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public BigDecimal getRuleFormulaReturnValue() {
        return ruleFormulaReturnValue;
    }

    public void setRuleFormulaReturnValue(BigDecimal ruleFormulaReturnValue) {
        this.ruleFormulaReturnValue = ruleFormulaReturnValue;
    }



    public List<RuleActionResult> getRuleActionResultList() {
        return ruleActionResultList;
    }

    public void setRuleActionResultList(List<RuleActionResult> ruleActionResultList) {
        this.ruleActionResultList = ruleActionResultList;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getRuleExpCode() {
        return ruleExpCode;
    }

    public void setRuleExpCode(String ruleExpCode) {
        this.ruleExpCode = ruleExpCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
