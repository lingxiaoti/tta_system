package com.sie.saaf.rule.model.entities.readonly;


/**
 * RuleExpressiondimViewEntity_HI_RO Entity Object
 * Mon May 08 23:15:35 CST 2017  Auto Generate
 */

public class RuleExpressiondimViewEntity_HI_RO {
    private String ruleDimCode;
    private String ruleExpCode;
    private long dimSize;

    public void setRuleDimCode(String ruleDimCode) {
        this.ruleDimCode = ruleDimCode;
    }


    public String getRuleDimCode() {
        return ruleDimCode;
    }

    public void setRuleExpCode(String ruleExpCode) {
        this.ruleExpCode = ruleExpCode;
    }


    public String getRuleExpCode() {
        return ruleExpCode;
    }

    public void setDimSize(long dimSize) {
        this.dimSize = dimSize;
    }


    public long getDimSize() {
        return dimSize;
    }
}

