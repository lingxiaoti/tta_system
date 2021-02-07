package com.sie.saaf.rule.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * RuleBusinessLineEntity_HI Entity Object
 * Mon May 08 23:15:35 CST 2017  Auto Generate
 */
@Entity
@Table(name = "rule_business_line")
public class RuleBusinessLineEntity_HI {
    private Integer ruleBusinessLineId;//分类ID
    private String ruleBusinessLineCode;//分类编码
    private String ruleBusinessLineType;//业务类型分类
    private String ruleBusinessLineName;//分类名称
    private String ruleBusinessLineDesc;//分类说明
    private String ruleBusinessLineParentCode;//分类父级编码
    private String ruleBusinessLineMapptype;//匹配类型
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    public void setRuleBusinessLineId(Integer ruleBusinessLineId) {
        this.ruleBusinessLineId = ruleBusinessLineId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_RULE_BUSINESS_LINE", sequenceName = "SEQ_RULE_BUSINESS_LINE", allocationSize = 1)
   	@GeneratedValue(generator = "SEQ_RULE_BUSINESS_LINE", strategy = GenerationType.SEQUENCE)
    @Column(name = "rule_business_line_id", nullable = false, length = 11)
    public Integer getRuleBusinessLineId() {
        return ruleBusinessLineId;
    }

    public void setRuleBusinessLineCode(String ruleBusinessLineCode) {
        this.ruleBusinessLineCode = ruleBusinessLineCode;
    }

    @Column(name="rule_business_line_type",nullable = true,length = 20)
    public String getRuleBusinessLineType() {
        return ruleBusinessLineType;
    }

    public void setRuleBusinessLineType(String ruleBusinessLineType) {
        this.ruleBusinessLineType = ruleBusinessLineType;
    }

    @Column(name = "rule_business_line_code", nullable = true, length = 100)
    public String getRuleBusinessLineCode() {
        return ruleBusinessLineCode;
    }

    public void setRuleBusinessLineName(String ruleBusinessLineName) {
        this.ruleBusinessLineName = ruleBusinessLineName;
    }

    @Column(name = "rule_business_line_name", nullable = true, length = 500)
    public String getRuleBusinessLineName() {
        return ruleBusinessLineName;
    }

    public void setRuleBusinessLineDesc(String ruleBusinessLineDesc) {
        this.ruleBusinessLineDesc = ruleBusinessLineDesc;
    }

    @Column(name = "rule_business_line_desc", nullable = true, length = 500)
    public String getRuleBusinessLineDesc() {
        return ruleBusinessLineDesc;
    }

    public void setRuleBusinessLineParentCode(String ruleBusinessLineParentCode) {
        this.ruleBusinessLineParentCode = ruleBusinessLineParentCode;
    }

    @Column(name = "rule_business_line_parent_code", nullable = true, length = 100)
    public String getRuleBusinessLineParentCode() {
        return ruleBusinessLineParentCode;
    }

    public void setRuleBusinessLineMapptype(String ruleBusinessLineMapptype) {
        this.ruleBusinessLineMapptype = ruleBusinessLineMapptype;
    }

    @Column(name = "rule_business_line_mapptype", nullable = true, length = 100)
    public String getRuleBusinessLineMapptype() {
        return ruleBusinessLineMapptype;
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

