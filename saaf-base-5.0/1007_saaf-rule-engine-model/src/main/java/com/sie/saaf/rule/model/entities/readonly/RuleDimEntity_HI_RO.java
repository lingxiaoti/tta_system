package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;


/**
 * Created by Administrator on Thu Aug 17 11:46:29 CST 2017
 */
public class RuleDimEntity_HI_RO{


    public static final String query="SELECT   rd.placeholder   AS placeholder,rd.RULE_DIM_ID AS ruleDimId,   rd.rule_business_line_code AS ruleBusinessLineCode,   rbl.rule_business_line_name AS ruleBusinessLineName,   rbl.rule_business_line_Desc AS ruleBusinessLineDesc,   rbl.rule_business_line_type AS ruleBusinessLineType,   slv3.meaning AS ruleBusinessLineTypeMeaning,   rd.RULE_view_TYPE AS ruleViewType,   rd.RULE_DIM_NAME AS ruleDimName,   rd.RULE_DIM_DATA_TYPE AS ruleDimDataType,   rd.RULE_DIM_default_VALUE AS ruleDimDefaultValue,   rd.RULE_DIM_CODE AS ruleDimCode,   rd.RULE_DIM_DESC AS ruleDimDesc,   rd.rule_dim_target_source AS ruleDimTargetSource,   rd.rule_dim_reference_code AS ruleDimReferenceCode,   slv.MEANING AS ruleDimReferenceFromMeaning,   rd.rule_dim_reference_from AS ruleDimReferenceFrom,   slv2.MEANING AS ruleDimReferenceCodeMeaning,   rd.rule_DIM_value_from AS ruleDimValueFrom,   slv4.MEANING AS ruleDimValueFromMeaning,   rd.rule_dim_target_source AS ruleDimTargetSource,   rd.EFFECT_DATE AS effectDate,   rd.EFFECT_END_DATE AS effectEndDate  FROM rule_dim rd LEFT JOIN rule_business_line rbl ON rbl.rule_business_line_code = rd.rule_business_line_code   LEFT JOIN base_lookup_values slv     ON slv.LOOKUP_TYPE = 'DIM_VALUE_SOURCES' AND slv.LOOKUP_CODE = rd.rule_dim_reference_from   LEFT JOIN base_lookup_values slv2 ON slv2.LOOKUP_TYPE = 'DIM_VALUE' AND slv2.LOOKUP_CODE = rd.rule_dim_reference_code   LEFT JOIN base_lookup_values slv3     ON slv3.LOOKUP_TYPE = 'BUSINESS_TYPE' AND slv3.LOOKUP_CODE = rbl.rule_business_line_type   LEFT JOIN base_lookup_values slv4 ON slv4 .LOOKUP_TYPE='' AND slv4.LOOKUP_CODE=rd.rule_DIM_value_from WHERE 1=1";
    private Integer ruleDimId;
    private String ruleBusinessLineCode;
    private String ruleBusinessLineName;
    private String ruleBusinessLineDesc;
    private String ruleBusinessLineType;
    private String ruleBusinessLineTypeMeaning;
    private String ruleViewType;
    private String ruleDimName;
    private String ruleDimDataType;
    private String ruleDimDefaultValue;
    private String ruleDimCode;
    private String ruleDimDesc;
    private String ruleDimTargetSource;
    private String ruleDimReferenceCode;
    private String ruleDimReferenceFromMeaning;
    private String ruleDimReferenceFrom;
    private String ruleDimReferenceCodeMeaning;
    private String ruleDimValueFrom;
    private String ruleDimValueFromMeaning;
    private String rdRuleDimTargetSource;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectEndDate;
    private String placeholder;

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public Integer getRuleDimId() {
        return this.ruleDimId;
    }

    public void setRuleDimId(Integer ruleDimId) {
        this.ruleDimId=ruleDimId;
    }

    public String getRuleBusinessLineCode() {
        return this.ruleBusinessLineCode;
    }

    public void setRuleBusinessLineCode(String ruleBusinessLineCode) {
        this.ruleBusinessLineCode=ruleBusinessLineCode;
    }

    public String getRuleBusinessLineName() {
        return this.ruleBusinessLineName;
    }

    public void setRuleBusinessLineName(String ruleBusinessLineName) {
        this.ruleBusinessLineName=ruleBusinessLineName;
    }

    public String getRuleBusinessLineDesc() {
        return this.ruleBusinessLineDesc;
    }

    public void setRuleBusinessLineDesc(String ruleBusinessLineDesc) {
        this.ruleBusinessLineDesc=ruleBusinessLineDesc;
    }

    public String getRuleBusinessLineType() {
        return this.ruleBusinessLineType;
    }

    public void setRuleBusinessLineType(String ruleBusinessLineType) {
        this.ruleBusinessLineType=ruleBusinessLineType;
    }

    public String getRuleBusinessLineTypeMeaning() {
        return this.ruleBusinessLineTypeMeaning;
    }

    public void setRuleBusinessLineTypeMeaning(String ruleBusinessLineTypeMeaning) {
        this.ruleBusinessLineTypeMeaning=ruleBusinessLineTypeMeaning;
    }

    public String getRuleViewType() {
        return this.ruleViewType;
    }

    public void setRuleViewType(String ruleViewType) {
        this.ruleViewType=ruleViewType;
    }

    public String getRuleDimName() {
        return this.ruleDimName;
    }

    public void setRuleDimName(String ruleDimName) {
        this.ruleDimName=ruleDimName;
    }

    public String getRuleDimDataType() {
        return this.ruleDimDataType;
    }

    public void setRuleDimDataType(String ruleDimDataType) {
        this.ruleDimDataType=ruleDimDataType;
    }

    public String getRuleDimDefaultValue() {
        return this.ruleDimDefaultValue;
    }

    public void setRuleDimDefaultValue(String ruleDimDefaultValue) {
        this.ruleDimDefaultValue=ruleDimDefaultValue;
    }

    public String getRuleDimCode() {
        return this.ruleDimCode;
    }

    public void setRuleDimCode(String ruleDimCode) {
        this.ruleDimCode=ruleDimCode;
    }

    public String getRuleDimDesc() {
        return this.ruleDimDesc;
    }

    public void setRuleDimDesc(String ruleDimDesc) {
        this.ruleDimDesc=ruleDimDesc;
    }

    public String getRuleDimTargetSource() {
        return this.ruleDimTargetSource;
    }

    public void setRuleDimTargetSource(String ruleDimTargetSource) {
        this.ruleDimTargetSource=ruleDimTargetSource;
    }

    public String getRuleDimReferenceCode() {
        return this.ruleDimReferenceCode;
    }

    public void setRuleDimReferenceCode(String ruleDimReferenceCode) {
        this.ruleDimReferenceCode=ruleDimReferenceCode;
    }

    public String getRuleDimReferenceFromMeaning() {
        return this.ruleDimReferenceFromMeaning;
    }

    public void setRuleDimReferenceFromMeaning(String ruleDimReferenceFromMeaning) {
        this.ruleDimReferenceFromMeaning=ruleDimReferenceFromMeaning;
    }

    public String getRuleDimReferenceFrom() {
        return this.ruleDimReferenceFrom;
    }

    public void setRuleDimReferenceFrom(String ruleDimReferenceFrom) {
        this.ruleDimReferenceFrom=ruleDimReferenceFrom;
    }

    public String getRuleDimReferenceCodeMeaning() {
        return this.ruleDimReferenceCodeMeaning;
    }

    public void setRuleDimReferenceCodeMeaning(String ruleDimReferenceCodeMeaning) {
        this.ruleDimReferenceCodeMeaning=ruleDimReferenceCodeMeaning;
    }

    public String getRuleDimValueFrom() {
        return this.ruleDimValueFrom;
    }

    public void setRuleDimValueFrom(String ruleDimValueFrom) {
        this.ruleDimValueFrom=ruleDimValueFrom;
    }

    public String getRuleDimValueFromMeaning() {
        return this.ruleDimValueFromMeaning;
    }

    public void setRuleDimValueFromMeaning(String ruleDimValueFromMeaning) {
        this.ruleDimValueFromMeaning=ruleDimValueFromMeaning;
    }

    public String getRdRuleDimTargetSource() {
        return this.rdRuleDimTargetSource;
    }

    public void setRdRuleDimTargetSource(String rdRuleDimTargetSource) {
        this.rdRuleDimTargetSource=rdRuleDimTargetSource;
    }

    public Date getEffectDate() {
        return this.effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate=effectDate;
    }

    public Date getEffectEndDate() {
        return this.effectEndDate;
    }

    public void setEffectEndDate(Date effectEndDate) {
        this.effectEndDate=effectEndDate;
    }
}