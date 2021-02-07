package com.sie.saaf.rule.model.entities.readonly;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * RuleExpressionEntity_HI_RO Entity Object
 * Mon May 08 23:15:35 CST 2017  Auto Generate
 */

public class RuleExpressionEntity_HI_RO {
    public static final String query = " SELECT     \r\n"
            + " re.RULE_EXP_ID AS ruleExpId,    \r\n"
            + " re.rule_return_msg AS ruleReturnMsg,    \r\n"
            + " re.rule_business_point AS ruleBusinessPoint,    \r\n"
            + " re.rule_exp_params AS ruleExpParams,    \r\n"
            + " re.rule_exp AS ruleExp,    \r\n"
            + " rbl.rule_business_line_code AS ruleBusinessLineCode,    \r\n"
            + " rbl.rule_business_line_name AS ruleBusinessLineName,    \r\n"
            + " rbl.rule_business_line_mappType AS ruleBusinessLineMapptype,    \r\n"
            + " re.RULE_EXP_NAME AS ruleExpName,    \r\n"
            + " re.RULE_EXP_CODE AS ruleExpCode,    \r\n"
            + " re.RULE_EXP_DESC AS ruleExpDesc,    \r\n"
            + " re.RULE_SIMPLE_EXP AS ruleSimpleExp,    \r\n"
            + " re.RULE_EXP_WEIGHT AS ruleExpWeight,    \r\n"
            + " re.EFFECT_DATE AS effectDate,    \r\n"
            + " re.EFFECT_END_DATE AS effectEndDate,    \r\n"
            + " re.rule_use_formula ruleUseFormula,    \r\n"
            + " re.rule_use_action ruleUseAction,    \r\n"
            + " re.rule_use_vaule ruleUseVaule,    \r\n"
            + " re.rule_formula ruleFormula,    \r\n"
            + " re.rule_formula_dynamic_param ruleFormulaDynamicParam,    \r\n"
            + " re.rule_formula_static_param ruleFormulaStaticParam,    \r\n"
            + " re.rule_formula_static_value ruleFormulaStaticValue,    \r\n"
            + " re.rule_value ruleValue    \r\n"
            + " FROM    \r\n"
            + " 	base.rule_expression re    \r\n"
            + " LEFT JOIN base.rule_business_line rbl ON rbl.rule_business_line_code = re.rule_business_line_code    \r\n"
            + " WHERE    \r\n"
            + " 	1 = 1    " ;
    public static final String QUERY_PARAMS = "SELECT re.rule_exp_params ruleExpParams from base.rule_expression re where re.RULE_EXP_CODE = :ruleExpCode";
    public static final String QUERY_EXP_LIST = " SELECT     \r\n"
            + " t.RULE_EXP_ID ruleExpId,    \r\n"
            + " t.rule_business_line_code ruleBusinessLineCode,    \r\n"
            + " t.rule_business_point ruleBusinessPoint,    \r\n"
            + " t.RULE_EXP_NAME ruleExpName,    \r\n"
            + " t.RULE_EXP_CODE ruleExpCode,    \r\n"
            + " t.RULE_EXP_DESC ruleExpDesc,    \r\n"
            + " t.RULE_SIMPLE_EXP ruleSimpleExp,    \r\n"
            + " t.rule_use_formula ruleUseFormula,    \r\n"
            + " t.rule_use_action ruleUseAction,    \r\n"
            + " t.rule_use_vaule ruleUseVaule,    \r\n"
            + " t.rule_formula ruleFormula,    \r\n"
            + " t.rule_formula_dynamic_param ruleFormulaDynamicParam,    \r\n"
            + " t.rule_formula_static_param ruleFormulaStaticParam,    \r\n"
            + " t.rule_formula_static_value ruleFormulaStaticValue,    \r\n"
            + " t.rule_value ruleValue,    \r\n"
            + " t.rule_exp ruleExp,    \r\n"
            + " t.rule_return_msg ruleReturnMsg,    \r\n"
            + " t.rule_exp_params ruleExpParams,    \r\n"
            + " t.RULE_EXP_WEIGHT ruleExpWeight,    \r\n"
            + " t.EFFECT_DATE effectDate,    \r\n"
            + " t.EFFECT_END_DATE effectEndDate,    \r\n"
            + " t.version_num versionNum,    \r\n"
            + " t.CREATION_DATE creationDate,    \r\n"
            + " t.CREATED_BY createdBy,    \r\n"
            + " t.LAST_UPDATED_BY lastUpdatedBy,    \r\n"
            + " t.LAST_UPDATE_DATE lastUpdateDate,    \r\n"
            + " t.LAST_UPDATE_LOGIN lastUpdateLogin    \r\n"
            + " FROM    \r\n"
            + " base.rule_expression AS t    where 1=1"  ;


    //查询的sql
    public static final String QUERY_SQL = "";

    //
    private Integer ruleExpId;

    //规则表达式所属的业务线
    private String ruleBusinessLineCode;

    //业务点，以业务线和业务点做唯一关联规则判断
    private String ruleBusinessPoint;

    //
    private String ruleExpName;

    //
    private String ruleExpCode;

    //
    private String ruleExpDesc;

    //维度生成的规则
    private String ruleSimpleExp;

    //规则表达式是否执行数学表达式，Y为true,N为false
    private String ruleUseFormula;

    //是否执行服务
    private String ruleUseAction;

    //是否返回具体值
    private String ruleUseVaule;

    //数学表达式
    private String ruleFormula;

    //数学表达式动态参数
    private String ruleFormulaDynamicParam;

    //规则表达式静态参数
    private String ruleFormulaStaticParam;

    //规则表达式静态参数值，json形式保存
    private String ruleFormulaStaticValue;

    //规则返回的特定值
    private String ruleValue;

    //真正的规则表达式，主要是用于执行sql
    private String ruleExp;

    //匹配成功后返回信息
    private String ruleReturnMsg;

    //表达式必传参数，以逗号隔开
    private String ruleExpParams;

    //
    private Integer ruleExpWeight;

    //
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectDate;

    //
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectEndDate;

    //
    private Integer versionNum;

    //
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    //
    private Integer createdBy;

    //
    private Integer lastUpdatedBy;

    //
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

    //
    private Integer lastUpdateLogin;

    private Integer operatorUserId;
    private String ruleBusinessLineName;
    private String ruleBusinessLineMapptype;

    public String getRuleUseFormula() {
        return ruleUseFormula;
    }

    public void setRuleUseFormula(String ruleUseFormula) {
        this.ruleUseFormula = ruleUseFormula;
    }

    public String getRuleUseAction() {
        return ruleUseAction;
    }

    public void setRuleUseAction(String ruleUseAction) {
        this.ruleUseAction = ruleUseAction;
    }

    public String getRuleUseVaule() {
        return ruleUseVaule;
    }

    public void setRuleUseVaule(String ruleUseVaule) {
        this.ruleUseVaule = ruleUseVaule;
    }

    public String getRuleFormula() {
        return ruleFormula;
    }

    public void setRuleFormula(String ruleFormula) {
        this.ruleFormula = ruleFormula;
    }

    public String getRuleFormulaDynamicParam() {
        return ruleFormulaDynamicParam;
    }

    public void setRuleFormulaDynamicParam(String ruleFormulaDynamicParam) {
        this.ruleFormulaDynamicParam = ruleFormulaDynamicParam;
    }

    public String getRuleFormulaStaticParam() {
        return ruleFormulaStaticParam;
    }

    public void setRuleFormulaStaticParam(String ruleFormulaStaticParam) {
        this.ruleFormulaStaticParam = ruleFormulaStaticParam;
    }

    public String getRuleFormulaStaticValue() {
        return ruleFormulaStaticValue;
    }

    public void setRuleFormulaStaticValue(String ruleFormulaStaticValue) {
        this.ruleFormulaStaticValue = ruleFormulaStaticValue;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getRuleBusinessPoint() {
        return ruleBusinessPoint;
    }

    public void setRuleBusinessPoint(String ruleBusinessPoint) {
        this.ruleBusinessPoint = ruleBusinessPoint;
    }

    public String getRuleExpParams() {
        return ruleExpParams;
    }

    public void setRuleExpParams(String ruleExpParams) {
        this.ruleExpParams = ruleExpParams;
    }

    public String getRuleReturnMsg() {
        return ruleReturnMsg;
    }

    public void setRuleReturnMsg(String ruleReturnMsg) {
        this.ruleReturnMsg = ruleReturnMsg;
    }

    public String getRuleExp() {
        return ruleExp;
    }

    public void setRuleExp(String ruleExp) {
        this.ruleExp = ruleExp;
    }

    public Integer getRuleExpId() {
        return ruleExpId;
    }

    public void setRuleExpId(Integer ruleExpId) {
        this.ruleExpId = ruleExpId;
    }

    public String getRuleBusinessLineCode() {
        return ruleBusinessLineCode;
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

    public String getRuleBusinessLineMapptype() {
        return ruleBusinessLineMapptype;
    }

    public void setRuleBusinessLineMapptype(String ruleBusinessLineMapptype) {
        this.ruleBusinessLineMapptype = ruleBusinessLineMapptype;
    }

    public String getRuleExpName() {
        return ruleExpName;
    }

    public void setRuleExpName(String ruleExpName) {
        this.ruleExpName = ruleExpName;
    }

    public String getRuleExpCode() {
        return ruleExpCode;
    }

    public void setRuleExpCode(String ruleExpCode) {
        this.ruleExpCode = ruleExpCode;
    }

    public String getRuleExpDesc() {
        return ruleExpDesc;
    }

    public void setRuleExpDesc(String ruleExpDesc) {
        this.ruleExpDesc = ruleExpDesc;
    }

    public String getRuleSimpleExp() {
        return ruleSimpleExp;
    }

    public void setRuleSimpleExp(String ruleSimpleExp) {
        this.ruleSimpleExp = ruleSimpleExp;
    }

    public Integer getRuleExpWeight() {
        return ruleExpWeight;
    }

    public void setRuleExpWeight(Integer ruleExpWeight) {
        this.ruleExpWeight = ruleExpWeight;
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
}

