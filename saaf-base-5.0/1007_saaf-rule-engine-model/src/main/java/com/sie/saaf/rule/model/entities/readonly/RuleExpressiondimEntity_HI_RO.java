package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * RuleExpressiondimEntity_HI_RO Entity Object
 * Mon May 08 23:15:35 CST 2017  Auto Generate
 */

public class RuleExpressiondimEntity_HI_RO  implements Comparable{
    public static final String query="SELECT   rd.rule_dim_reference_code      AS ruleDimReferenceCode, rd.rule_dim_target_source AS ruleDimTargetSource,  rd.rule_dim_reference_from      AS ruleDimReferenceFrom,   rd.rule_DIM_value_from          AS ruleDimValueFrom,   rd.placeholder   AS placeholder,  re.RULE_EXP_DIM_ID AS ruleExpDimId,   rd.RULE_DIM_ID                  AS ruleDimId,   re.rule_business_line_code AS ruleBusinessLineCode,   rbl.rule_business_line_name AS ruleBusinessLineName,   re.rule_order_Id AS ruleOrderId,   rd.RULE_DIM_CODE AS ruleDimCode,   rd.RULE_DIM_NAME AS ruleDimName,   rd.RULE_DIM_DATA_TYPE AS ruleDimDataType,   re.rule_dim_operators AS ruleDimOperators,   re.rule_dim_value AS ruleDimValue,   re.RULE_EXP_CODE AS ruleExpCode,   re.EFFECT_DATE AS effectDate,   re.EFFECT_END_DATE AS effectEndDate,   rbl.rule_business_line_mappType AS ruleBusinessLineMapptype  FROM rule_expressiondim re LEFT JOIN rule_business_line rbl ON rbl.rule_business_line_code = re.rule_business_line_code   LEFT JOIN rule_dim rd ON rd.RULE_DIM_CODE=re.RULE_DIM_CODE AND rd.rule_business_line_code=re.rule_business_line_code WHERE 1=1";
    public static final String rightQuery="SELECT   rd.rule_dim_reference_code      AS ruleDimReferenceCode,   rd.rule_dim_reference_from      AS ruleDimReferenceFrom,   rd.rule_DIM_value_from          AS ruleDimValueFrom,  rd.placeholder   AS placeholder,   re.RULE_EXP_DIM_ID AS ruleExpDimId,   rd.RULE_DIM_ID                  AS ruleDimId,   rd.rule_business_line_code AS ruleBusinessLineCode,   rbl.rule_business_line_name AS ruleBusinessLineName,   re.rule_order_Id AS ruleOrderId,   rd.RULE_DIM_CODE AS ruleDimCode,   rd.RULE_DIM_NAME AS ruleDimName,   rd.RULE_DIM_DATA_TYPE AS ruleDimDataType,   re.rule_dim_operators AS ruleDimOperators,   re.rule_dim_value AS ruleDimValue,   re.RULE_EXP_CODE AS ruleExpCode,   re.EFFECT_DATE AS effectDate,   re.EFFECT_END_DATE AS effectEndDate,   rbl.rule_business_line_mappType AS ruleBusinessLineMapptype  FROM rule_expressiondim re RIGHT JOIN rule_dim rd    ON rd.RULE_DIM_CODE = re.RULE_DIM_CODE AND rd.rule_business_line_code = re.rule_business_line_code #replace# LEFT JOIN rule_business_line rbl ON rbl.rule_business_line_code = rd.rule_business_line_code WHERE 1=1 AND  re.RULE_EXP_DIM_ID IS NULL ";

    //查询该业务线中有多少的维度并关联已经保存到表达式中的维度
    public static final String QUERY_RULE_EXP_DIM = " SELECT     \r\n"
            + " expDim.RULE_EXP_DIM_ID ruleExpDimId,    \r\n"
            + " dim.RULE_DIM_NAME ruleDimName,    \r\n"
            + " dim.RULE_DIM_CODE ruleDimCode,    \r\n"
            + " dim.RULE_DIM_DATA_TYPE ruleDimDataType,    \r\n"
            + " dim.rule_dim_target_source ruleDimTargetSource,    \r\n"
            + " expDim.rule_dim_operators ruleDimOperators,    \r\n"
            + " expDim.rule_dim_value ruleDimValue,    \r\n"
            + " expDim.RULE_EXP_CODE ruleExpCode,    \r\n"
            + " expDim.version_num versionNum    \r\n"
            + " FROM rule_dim dim     \r\n"
            + " LEFT JOIN rule_expressiondim expDim on dim.RULE_DIM_CODE = expDim.RULE_DIM_CODE AND expDim.RULE_EXP_CODE = :ruleExpCode    \r\n"
            + " where dim.rule_business_line_code= :ruleBussinessLineCode    " ;
    private Integer ruleExpDimId;
    private String ruleBusinessLineCode;
    private String ruleBusinessLineName;
    private String ruleOrderId;
    private String ruleDimCode;
    private String ruleDimName;
    private String ruleDimDataType;
    private String ruleDimOperators;
    private String ruleDimValue;
    private String ruleExpCode;
    private String ruleDimTargetSource;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectEndDate;
    private String ruleBusinessLineMapptype;
    private Integer ruleDimId;
    private String placeholder;
    private String ruleDimValueFrom;
    private String ruleDimReferenceFrom;
    private String ruleDimReferenceCode;


    public String getRuleDimTargetSource() {
        return ruleDimTargetSource;
    }

    public void setRuleDimTargetSource(String ruleDimTargetSource) {
        this.ruleDimTargetSource = ruleDimTargetSource;
    }

    public String getRuleDimValueFrom() {
        return ruleDimValueFrom;
    }

    public void setRuleDimValueFrom(String ruleDimValueFrom) {
        this.ruleDimValueFrom = ruleDimValueFrom;
    }

    public Integer getRuleDimId() {
        return ruleDimId;
    }

    public void setRuleDimId(Integer ruleDimId) {
        this.ruleDimId = ruleDimId;
    }

    public Integer getRuleExpDimId() {
        return ruleExpDimId;
    }

    public void setRuleExpDimId(Integer ruleExpDimId) {
        this.ruleExpDimId = ruleExpDimId;
    }

    public String getRuleBusinessLineCode() {
        return ruleBusinessLineCode;
    }

    public String getRuleDimDataType() {
        return ruleDimDataType;
    }

    public void setRuleDimDataType(String ruleDimDataType) {
        this.ruleDimDataType = ruleDimDataType;
    }

    public void setRuleBusinessLineCode(String ruleBusinessLineCode) {
        this.ruleBusinessLineCode = ruleBusinessLineCode;
    }

    public String getRuleBusinessLineName() {
        return ruleBusinessLineName;
    }

    public void setRuleBusinessLineName(String ruleBusinessLineName) {
        this.ruleBusinessLineName = ruleBusinessLineName;
    }

    public String getRuleOrderId() {
        return ruleOrderId;
    }

    public void setRuleOrderId(String ruleOrderId) {
        this.ruleOrderId = ruleOrderId;
    }

    public String getRuleDimCode() {
        return ruleDimCode;
    }

    public void setRuleDimCode(String ruleDimCode) {
        this.ruleDimCode = ruleDimCode;
    }

    public String getRuleDimName() {
        return ruleDimName;
    }

    public void setRuleDimName(String ruleDimName) {
        this.ruleDimName = ruleDimName;
    }

    public String getRuleDimOperators() {
        return ruleDimOperators;
    }

    public void setRuleDimOperators(String ruleDimOperators) {
        this.ruleDimOperators = ruleDimOperators;
    }

    public String getRuleDimValue() {
        return ruleDimValue;
    }

    public void setRuleDimValue(String ruleDimValue) {
        this.ruleDimValue = ruleDimValue;
    }

    public String getRuleExpCode() {
        return ruleExpCode;
    }

    public void setRuleExpCode(String ruleExpCode) {
        this.ruleExpCode = ruleExpCode;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getEffectEndDate() {
        return effectEndDate;
    }

    public void setEffectEndDate(Date effectEndDate) {
        this.effectEndDate = effectEndDate;
    }

    public String getRuleBusinessLineMapptype() {
        return ruleBusinessLineMapptype;
    }

    public void setRuleBusinessLineMapptype(String ruleBusinessLineMapptype) {
        this.ruleBusinessLineMapptype = ruleBusinessLineMapptype;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getRuleDimReferenceFrom() {
        return ruleDimReferenceFrom;
    }

    public void setRuleDimReferenceFrom(String ruleDimReferenceFrom) {
        this.ruleDimReferenceFrom = ruleDimReferenceFrom;
    }

    public String getRuleDimReferenceCode() {
        return ruleDimReferenceCode;
    }

    public void setRuleDimReferenceCode(String ruleDimReferenceCode) {
        this.ruleDimReferenceCode = ruleDimReferenceCode;
    }

    @Override
    public int compareTo(Object o) {
        if (o==null || this==null || getRuleDimId()==null)
            return 0;
        RuleExpressiondimEntity_HI_RO obj= (RuleExpressiondimEntity_HI_RO) o;
        if (obj.getRuleDimId()==null)
            return 1;
        return getRuleDimId().compareTo(obj.getRuleDimId());
    }
}

