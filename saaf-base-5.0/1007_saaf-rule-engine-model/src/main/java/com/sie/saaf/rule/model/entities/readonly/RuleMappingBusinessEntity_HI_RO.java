package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * RuleMappingBusinessEntity_HI_RO Entity Object
 * Mon May 08 23:15:35 CST 2017  Auto Generate
 */

public class RuleMappingBusinessEntity_HI_RO {
    public static final String query =
        "SELECT   rmb.RULE_MAPP_BUS_ID AS ruleMappBusId,   rmb.RULE_BUS_dim AS ruleBusDim,   rd.RULE_DIM_NAME AS ruleDimName,   rd.RULE_DIM_DATA_TYPE AS ruleDimDataType,   rmb.rule_bus_dim_operator AS ruleBusDimOperator,   rmb.rule_bus_dim_value AS ruleBusDimValue,   rmb.rule_bus_target_type AS ruleBusTargetType,   rmb.rule_bus_target_source AS ruleBusTargetSource,   rmb.rule_bus_result_dim AS ruleBusResultDim,   rmb.rule_bus_param AS ruleBusParam,   rmb.rule_exc_code AS ruleExcCode,   re.rule_business_line_code AS ruleBusinessLineCode,   rbl.rule_business_line_name AS ruleBusinessLineName,   re.RULE_EXP_NAME AS ruleExpName,   rmb.rule_bus_level AS ruleBusLevel,   rmb.EFFECT_DATE AS effectDate,   rmb.EFFECT_END_DATE AS effectEndDate  FROM rule_mapping_business rmb LEFT JOIN rule_expression re ON re.RULE_EXP_CODE = rmb.rule_exc_code   LEFT JOIN rule_business_line rbl ON rbl.rule_business_line_code = re.rule_business_line_code   LEFT JOIN rule_dim rd ON rmb.RULE_BUS_dim =rd.RULE_DIM_CODE AND rd.rule_business_line_code=re.rule_business_line_code WHERE 1=1";
    private Integer ruleMappBusId;
    private String ruleBusDim;
    private String ruleDimName;
    private String ruleDimDataType;
    private String ruleBusDimOperator;
    private String ruleBusDimValue;
    private String ruleBusTargetType;
    private String ruleBusTargetSource;
    private String ruleBusResultDim;
    private String ruleBusParam;
    private String ruleExcCode;
    private String ruleBusinessLineCode;
    private String ruleBusinessLineName;
    private String ruleExpName;
    private Integer ruleBusLevel;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectEndDate;

    public Integer getRuleMappBusId() {
        return this.ruleMappBusId;
    }

    public void setRuleMappBusId(Integer ruleMappBusId) {
        this.ruleMappBusId = ruleMappBusId;
    }

    public String getRuleBusDim() {
        return this.ruleBusDim;
    }

    public void setRuleBusDim(String ruleBusDim) {
        this.ruleBusDim = ruleBusDim;
    }

    public String getRuleDimName() {
        return this.ruleDimName;
    }

    public void setRuleDimName(String ruleDimName) {
        this.ruleDimName = ruleDimName;
    }

    public String getRuleDimDataType() {
        return this.ruleDimDataType;
    }

    public void setRuleDimDataType(String ruleDimDataType) {
        this.ruleDimDataType = ruleDimDataType;
    }

    public String getRuleBusDimOperator() {
        return this.ruleBusDimOperator;
    }

    public void setRuleBusDimOperator(String ruleBusDimOperator) {
        this.ruleBusDimOperator = ruleBusDimOperator;
    }

    public String getRuleBusDimValue() {
        return this.ruleBusDimValue;
    }

    public void setRuleBusDimValue(String ruleBusDimValue) {
        this.ruleBusDimValue = ruleBusDimValue;
    }

    public String getRuleBusTargetType() {
        return this.ruleBusTargetType;
    }

    public void setRuleBusTargetType(String ruleBusTargetType) {
        this.ruleBusTargetType = ruleBusTargetType;
    }

    public String getRuleBusTargetSource() {
        return this.ruleBusTargetSource;
    }

    public void setRuleBusTargetSource(String ruleBusTargetSource) {
        this.ruleBusTargetSource = ruleBusTargetSource;
    }

    public String getRuleBusResultDim() {
        return this.ruleBusResultDim;
    }

    public void setRuleBusResultDim(String ruleBusResultDim) {
        this.ruleBusResultDim = ruleBusResultDim;
    }

    public String getRuleBusParam() {
        return this.ruleBusParam;
    }

    public void setRuleBusParam(String ruleBusParam) {
        this.ruleBusParam = ruleBusParam;
    }

    public String getRuleExcCode() {
        return this.ruleExcCode;
    }

    public void setRuleExcCode(String ruleExcCode) {
        this.ruleExcCode = ruleExcCode;
    }

    public String getRuleBusinessLineCode() {
        return this.ruleBusinessLineCode;
    }

    public void setRuleBusinessLineCode(String ruleBusinessLineCode) {
        this.ruleBusinessLineCode = ruleBusinessLineCode;
    }

    public String getRuleBusinessLineName() {
        return this.ruleBusinessLineName;
    }

    public void setRuleBusinessLineName(String ruleBusinessLineName) {
        this.ruleBusinessLineName = ruleBusinessLineName;
    }

    public String getRuleExpName() {
        return this.ruleExpName;
    }

    public void setRuleExpName(String ruleExpName) {
        this.ruleExpName = ruleExpName;
    }

    public Integer getRuleBusLevel() {
        return this.ruleBusLevel;
    }

    public void setRuleBusLevel(Integer ruleBusLevel) {
        this.ruleBusLevel = ruleBusLevel;
    }

    public Date getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getEffectEndDate() {
        return this.effectEndDate;
    }

    public void setEffectEndDate(Date effectEndDate) {
        this.effectEndDate = effectEndDate;
    }
}

