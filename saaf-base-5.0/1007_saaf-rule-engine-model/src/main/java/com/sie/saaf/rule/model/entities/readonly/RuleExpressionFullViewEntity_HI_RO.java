package com.sie.saaf.rule.model.entities.readonly;


/**
 * RuleExpressionFullViewEntity_HI_RO Entity Object
 * Mon May 08 23:15:35 CST 2017  Auto Generate
 */

public class RuleExpressionFullViewEntity_HI_RO {

    public static final String QUERY_SQL = "select " +
            "rule_exp.RULE_EXP_DESC              AS ruleExpDesc,\n" +
            "rule_exp_dim.RULE_EXP_DIM_ID ruleExpDimId,\n" +
            "rule_exp_dim.rule_order_id ruleOrderId,\n" +
            "	rule_exp.RULE_EXP_CODE ruleExpCode,\n" +
            "  rule_exp.rule_business_line_code ruleBusinessLineCode,\n" +
            "  rule_exp.RULE_EXP_WEIGHT ruleExpWeigth,\n" +
            "	rule_exp.RULE_SIMPLE_EXP as fullExp,\n" +
            "	 CONCAT('#', rule_exp_dim.RULE_DIM_CODE, '#', ' ',rule_exp_dim.rule_dim_operators, ' ',rule_exp_dim.rule_dim_value)  as attributeValue,\n" +
            " rule_exp_dim.RULE_DIM_CODE ruleDimCode,\n" +
            " rule_exp_dim.rule_dim_operators ruleDimOperators,\n" +
            " rule_exp_dim.rule_dim_value ruleDimValue,\n" +
            "dim.placeholder AS placeholder " +
            "	from rule_expression rule_exp, rule_expressiondim rule_exp_dim, rule_dim dim  \n" +
            "	where rule_exp.RULE_EXP_CODE = rule_exp_dim.RULE_EXP_CODE" + " and rule_exp.rule_business_line_code = :ruleBusinessLineCode" +
            "       andsysdate BETWEEN nvl(rule_exp.effect_date,sysdate-1) AND nvl(rule_exp.effect_end_date,sysdate+1) AND rule_exp_dim.rule_business_line_code = dim.rule_business_line_code AND dim.RULE_DIM_CODE = rule_exp_dim.RULE_DIM_CODE";


    public static final String query = "SELECT" +
            "  rule_exp.RULE_EXP_DESC              AS ruleExpDesc,\n" +
            "  rule_exp_dim.RULE_EXP_DIM_ID        AS ruleExpDimId,\n" +
            "  rule_exp_dim.rule_order_id          AS ruleOrderId,\n" +
            "  rule_exp.RULE_EXP_CODE              AS ruleExpCode,\n" +
            "  rule_exp.rule_business_line_code    AS ruleBusinessLineCode,\n" +
            "  rule_exp.RULE_EXP_WEIGHT            AS ruleExpWeigth,\n" +
            "  rule_exp.RULE_SIMPLE_EXP            AS fullExp,\n" +
            "  CONCAT('#', rule_exp_dim.RULE_DIM_CODE, '#', ' ', rule_exp_dim.rule_dim_operators, ' ',\n" +
            "         rule_exp_dim.rule_dim_value) AS attributeValue,\n" +
            "  rule_exp_dim.RULE_DIM_CODE          AS ruleDimCode,\n" +
            "  rule_exp_dim.rule_dim_operators     AS ruleDimOperators,\n" +
            "  rule_exp_dim.rule_dim_value         AS ruleDimValue,\n" +
            "  rule_line.rule_business_line_type   AS ruleBusinessLineType,\n" +
            "  dim.placeholder                     AS placeholder\n" +
            " FROM rule_expression rule_exp\n" +
            "  JOIN rule_expressiondim rule_exp_dim\n" +
            "    ON rule_exp.RULE_EXP_CODE = rule_exp_dim.RULE_EXP_CODE ANDsysdate BETWEEN nvl(rule_exp.effect_date,sysdate - 1) AND nvl(rule_exp.effect_end_date,sysdate + 1)\n" +
            "  LEFT JOIN rule_business_line rule_line ON rule_line.rule_business_line_code = rule_exp.rule_business_line_code\n" +
            "  LEFT JOIN rule_dim dim ON rule_exp_dim.rule_business_line_code = dim.rule_business_line_code AND dim.RULE_DIM_CODE = rule_exp_dim.RULE_DIM_CODE " +
            "WHERE\n" +
            "   rule_exp.rule_business_line_code = :ruleBusinessLineCode AND rule_exp.RULE_EXP_CODE = :ruleExpCode";


    private Integer ruleExpDimId;
    private String ruleOrderId;
    private Integer ruleExpId;
    private String ruleExpCode;
    private String ruleBusinessLineCode;
    private String fullExp;
    private String attributeValue;
    private Integer ruleExpWeigth;
    private String ruleDimCode;
    private String ruleDimOperators;
    private String ruleDimValue;
    private Integer index;
    private String ruleBusinessLineType;
    private String placeholder;
    private String ruleExpDesc;

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setRuleExpId(Integer ruleExpId) {
        this.ruleExpId = ruleExpId;
    }


    public Integer getRuleExpId() {
        return ruleExpId;
    }

    public void setRuleExpCode(String ruleExpCode) {
        this.ruleExpCode = ruleExpCode;
    }


    public String getRuleExpCode() {
        return ruleExpCode;
    }

    public void setRuleBusinessLineCode(String ruleBusinessLineCode) {
        this.ruleBusinessLineCode = ruleBusinessLineCode;
    }


    public String getRuleBusinessLineCode() {
        return ruleBusinessLineCode;
    }

    public void setFullExp(String fullExp) {
        this.fullExp = fullExp;
    }


    public String getFullExp() {
        return fullExp;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }


    public String getAttributeValue() {
        return attributeValue;
    }

    public void setRuleExpDimId(Integer ruleExpDimId) {
        this.ruleExpDimId = ruleExpDimId;
    }

    public Integer getRuleExpDimId() {
        return ruleExpDimId;
    }

    public void setRuleOrderId(String ruleOrderId) {
        this.ruleOrderId = ruleOrderId;
    }

    public String getRuleOrderId() {
        return ruleOrderId;
    }

    @Override
    public String toString() {
        return this.ruleExpCode;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        RuleExpressionFullViewEntity_HI_RO obj_ = (RuleExpressionFullViewEntity_HI_RO) obj;
        if (obj_.getRuleExpCode().equals(this.ruleExpCode)) {
            return true;
        } else {
            return false;
        }
    }

    public void setRuleExpWeigth(Integer ruleExpWeigth) {
        this.ruleExpWeigth = ruleExpWeigth;
    }

    public Integer getRuleExpWeigth() {
        return ruleExpWeigth;
    }

    public void setRuleDimCode(String ruleDimCode) {
        this.ruleDimCode = ruleDimCode;
    }

    public String getRuleDimCode() {
        return ruleDimCode;
    }

    public void setRuleDimOperators(String ruleDimOperators) {
        this.ruleDimOperators = ruleDimOperators;
    }

    public String getRuleDimOperators() {
        return ruleDimOperators;
    }

    public void setRuleDimValue(String ruleDimValue) {
        this.ruleDimValue = ruleDimValue;
    }

    public String getRuleDimValue() {
        return ruleDimValue;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public String getRuleExpDesc() {
        return ruleExpDesc;
    }

    public void setRuleExpDesc(String ruleExpDesc) {
        this.ruleExpDesc = ruleExpDesc;
    }

    public String getRuleBusinessLineType() {
        return ruleBusinessLineType;
    }

    public void setRuleBusinessLineType(String ruleBusinessLineType) {
        this.ruleBusinessLineType = ruleBusinessLineType;
    }
}

