package com.sie.saaf.rule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.rule.model.beans.OperatorBean;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * RuleDimEntity_HI Entity Object
 * Thu Jul 06 10:50:38 CST 2017  Auto Generate
 */
@Entity
@Table(name = "rule_dim")
public class RuleDimEntity_HI {
    private Integer ruleDimId;//主键ID
    private String ruleBusinessLineCode;//业务线类型编码
    private String ruleViewType;
    private String ruleDimName;//维度名称
    private String ruleDimDataType;//数据类型
    private String ruleDimDefaultValue;//默认值
    private String ruleDimCode;//维度编码
    private String ruleDimDesc;//维度描述
    private String ruleDimValueFrom;//维度值来源
    private String ruleDimTargetSource;//维度值目标值
    private List<OperatorBean> operatorBeans = operatorBeans_();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectDate;//有效开始日期
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectEndDate;//有效结束日期
    private String ruleDimReferenceFrom;//参考值来源
    private String ruleDimReferenceCode;//参考值编码
    private String placeholder;//占位符
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    private static List<OperatorBean> operatorBeans_ (){
        List<OperatorBean> operatorBeans = new ArrayList<OperatorBean>();
        OperatorBean in = new OperatorBean("in", "包含");
        OperatorBean notIn = new OperatorBean("not in", "不包含");
        OperatorBean eqal = new OperatorBean("=", "等于");
        OperatorBean notEqal = new OperatorBean("<>", "不等于");
        OperatorBean betweenAnd = new OperatorBean("between##and", "在两者之间");
        OperatorBean gt = new OperatorBean(">", "大于");
        OperatorBean notLt = new OperatorBean(">=", "大于等于");
        OperatorBean lt = new OperatorBean("<", "小于");
        OperatorBean notGt = new OperatorBean("<=", "小于等于");
        operatorBeans.add(in);
        operatorBeans.add(notIn);
        operatorBeans.add(eqal);
        operatorBeans.add(notEqal);
        operatorBeans.add(betweenAnd);
        operatorBeans.add(gt);
        operatorBeans.add(notLt);
        operatorBeans.add(lt);
        operatorBeans.add(notGt);
        return operatorBeans;
    }

    public void setRuleDimId(Integer ruleDimId) {
        this.ruleDimId = ruleDimId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_RULE_DIM", sequenceName = "SEQ_RULE_DIM", allocationSize = 1)
   	@GeneratedValue(generator = "SEQ_RULE_DIM", strategy = GenerationType.SEQUENCE)
    @Column(name = "rule_dim_id", nullable = false, length = 10)
    public Integer getRuleDimId() {
        return ruleDimId;
    }

    public void setRuleBusinessLineCode(String ruleBusinessLineCode) {
        this.ruleBusinessLineCode = ruleBusinessLineCode;
    }

    @Column(name = "rule_business_line_code", nullable = true, length = 100)
    public String getRuleBusinessLineCode() {
        return ruleBusinessLineCode;
    }

    public void setRuleViewType(String ruleViewType) {
        this.ruleViewType = ruleViewType;
    }

    @Column(name = "rule_view_type", nullable = true, length = 30)
    public String getRuleViewType() {
        return ruleViewType;
    }

    public void setRuleDimName(String ruleDimName) {
        this.ruleDimName = ruleDimName;
    }

    @Column(name = "rule_dim_name", nullable = true, length = 150)
    public String getRuleDimName() {
        return ruleDimName;
    }

    public void setRuleDimDataType(String ruleDimDataType) {
        this.ruleDimDataType = ruleDimDataType;
    }

    @Column(name = "rule_dim_data_type", nullable = true, length = 15)
    public String getRuleDimDataType() {
        return ruleDimDataType;
    }

    public void setRuleDimDefaultValue(String ruleDimDefaultValue) {
        this.ruleDimDefaultValue = ruleDimDefaultValue;
    }

    @Column(name = "rule_dim_default_value", nullable = true, length = 150)
    public String getRuleDimDefaultValue() {
        return ruleDimDefaultValue;
    }

    public void setRuleDimCode(String ruleDimCode) {
        this.ruleDimCode = ruleDimCode;
    }

    @Column(name = "rule_dim_code", nullable = true, length = 150)
    public String getRuleDimCode() {
        return ruleDimCode;
    }

    public void setRuleDimDesc(String ruleDimDesc) {
        this.ruleDimDesc = ruleDimDesc;
    }

    @Column(name = "rule_dim_desc", nullable = true, length = 500)
    public String getRuleDimDesc() {
        return ruleDimDesc;
    }

    public void setRuleDimValueFrom(String ruleDimValueFrom) {
        this.ruleDimValueFrom = ruleDimValueFrom;
    }

    @Column(name = "rule_dim_value_from", nullable = true, length = 100)
    public String getRuleDimValueFrom() {
        return ruleDimValueFrom;
    }

    public void setRuleDimTargetSource(String ruleDimTargetSource) {
        this.ruleDimTargetSource = ruleDimTargetSource;
    }

    @Column(name = "rule_dim_target_source", nullable = true, length = 500)
    public String getRuleDimTargetSource() {
        return ruleDimTargetSource;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    @Column(name = "effect_date", nullable = true)
    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectEndDate(Date effectEndDate) {
        this.effectEndDate = effectEndDate;
    }

    @Column(name = "effect_end_date", nullable = true)
    public Date getEffectEndDate() {
        return effectEndDate;
    }

    public void setRuleDimReferenceFrom(String ruleDimReferenceFrom) {
        this.ruleDimReferenceFrom = ruleDimReferenceFrom;
    }

    @Column(name = "rule_dim_reference_from", nullable = true, length = 100)
    public String getRuleDimReferenceFrom() {
        return ruleDimReferenceFrom;
    }

    public void setRuleDimReferenceCode(String ruleDimReferenceCode) {
        this.ruleDimReferenceCode = ruleDimReferenceCode;
    }

    @Column(name = "rule_dim_reference_code", nullable = true, length = 100)
    public String getRuleDimReferenceCode() {
        return ruleDimReferenceCode;
    }

    @Column(name = "placeholder", nullable = true, length = 45)
    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @Transient
    public List<OperatorBean> getOperatorBeans() {
        return operatorBeans;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 11)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "creation_date", nullable = false, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_by", nullable = false, length = 11)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_updated_by", nullable = false, length = 11)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_date", nullable = false, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}

