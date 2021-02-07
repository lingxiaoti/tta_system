package com.sie.saaf.rule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;



/**
 * @Description:
 *
 * @author ZhangBingShan(Sam)
 * @email bszzhang@qq.com
 * @date 2019-01-25 09:20:16
 */

@Entity
@Table(name = "rule_expression")
public class RuleExpressionEntity_HI {

    //
    @Id
    @SequenceGenerator(name = "SEQ_RULE_EXPRESSION", sequenceName = "SEQ_RULE_EXPRESSION", allocationSize = 1)
   	@GeneratedValue(generator = "SEQ_RULE_EXPRESSION", strategy = GenerationType.SEQUENCE)
    @Column(name = "RULE_EXP_ID")
    private Integer ruleExpId;

    //规则表达式所属的业务线
    @Column(name = "rule_business_line_code")
    private String ruleBusinessLineCode;

    //业务点，以业务线和业务点做唯一关联规则判断
    @Column(name = "rule_business_point")
    private String ruleBusinessPoint;

    //
    @Column(name = "RULE_EXP_NAME")
    private String ruleExpName;

    //
    @Column(name = "RULE_EXP_CODE")
    private String ruleExpCode;

    //
    @Column(name = "RULE_EXP_DESC")
    private String ruleExpDesc;

    //维度生成的规则
    @Column(name = "RULE_SIMPLE_EXP")
    private String ruleSimpleExp;

    //规则表达式是否执行数学表达式，Y为true,N为false
    @Column(name = "rule_use_formula")
    private String ruleUseFormula;

    //是否执行服务
    @Column(name = "rule_use_action")
    private String ruleUseAction;

    //是否返回具体值
    @Column(name = "rule_use_vaule")
    private String ruleUseVaule;

    //数学表达式
    @Column(name = "rule_formula")
    private String ruleFormula;

    //数学表达式动态参数
    @Column(name = "rule_formula_dynamic_param")
    private String ruleFormulaDynamicParam;

    //规则表达式静态参数
    @Column(name = "rule_formula_static_param")
    private String ruleFormulaStaticParam;

    //规则表达式静态参数值，json形式保存
    @Column(name = "rule_formula_static_value")
    private String ruleFormulaStaticValue;

    //规则返回的特定值
    @Column(name = "rule_value")
    private String ruleValue;

    //真正的规则表达式，主要是用于执行sql
    @Column(name = "rule_exp")
    private String ruleExp;

    //匹配成功后返回信息
    @Column(name = "rule_return_msg")
    private String ruleReturnMsg;

    //表达式必传参数，以逗号隔开
    @Column(name = "rule_exp_params")
    private String ruleExpParams;

    //
    @Column(name = "RULE_EXP_WEIGHT")
    private Integer ruleExpWeight;

    //
    @Column(name = "EFFECT_DATE")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectDate;

    //
    @Column(name = "EFFECT_END_DATE")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectEndDate;

    //
    @Column(name = "version_num")
    private Integer versionNum;

    //
    @Column(name = "CREATION_DATE")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    //
    @Column(name = "CREATED_BY")
    private Integer createdBy;

    //
    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;

    //
    @Column(name = "LAST_UPDATE_DATE")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

    //
    @Column(name = "LAST_UPDATE_LOGIN")
    private Integer lastUpdateLogin;

    @Transient
    private Integer operatorUserId;

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

    public String getRuleBusinessPoint() {
        return ruleBusinessPoint;
    }

    public void setRuleBusinessPoint(String ruleBusinessPoint) {
        this.ruleBusinessPoint = ruleBusinessPoint;
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

    public String getRuleExp() {
        return ruleExp;
    }

    public void setRuleExp(String ruleExp) {
        this.ruleExp = ruleExp;
    }

    public String getRuleReturnMsg() {
        return ruleReturnMsg;
    }

    public void setRuleReturnMsg(String ruleReturnMsg) {
        this.ruleReturnMsg = ruleReturnMsg;
    }

    public String getRuleExpParams() {
        return ruleExpParams;
    }

    public void setRuleExpParams(String ruleExpParams) {
        this.ruleExpParams = ruleExpParams;
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


    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }


}
