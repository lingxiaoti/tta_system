package com.sie.watsons.base.rule.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TempRuleDefEntity_HI Entity Object
 * Wed May 29 16:12:16 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_BASE_RULE_LINE")
public class RuleLEntity_HI {
    private Integer ruleLineId;
    private Integer qnHeadId;
    private String qnTitle;
    private String optionValus;
    private Integer ruleId;
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    public void setRuleLineId(Integer ruleLineId) {
		this.ruleLineId = ruleLineId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_TEMP_RULE_DEF", sequenceName="SEQ_TTA_TEMP_RULE_DEF", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_TEMP_RULE_DEF",strategy=GenerationType.SEQUENCE)
	@Column(name="RULE_LINE_ID", nullable=false, length=22)	
	public Integer getRuleLineId() {
		return ruleLineId;
	}

	@Column(name="QN_HEAD_ID", nullable=true, length=100)
	public Integer getQnHeadId() {
		return qnHeadId;
	}

	public void setQnHeadId(Integer qnHeadId) {
		this.qnHeadId = qnHeadId;
	}

	@Column(name="QN_TITLE", nullable=true, length=100)
	public String getQnTitle() {
		return qnTitle;
	}

	public void setQnTitle(String qnTitle) {
		this.qnTitle = qnTitle;
	}

	@Column(name="OPTION_VALUS", nullable=true, length=100)
	public String getOptionValus() {
		return optionValus;
	}

	public void setOptionValus(String optionValus) {
		this.optionValus = optionValus;
	}

	@Column(name="RULE_ID", nullable=true, length=100)
	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	@Column(name="CREATION_DATE", nullable=true, length=100)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Column(name="CREATED_BY", nullable=true, length=100)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="LAST_UPDATED_BY", nullable=true, length=100)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="LAST_UPDATE_DATE", nullable=true, length=100)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="LAST_UPDATE_LOGIN", nullable=true, length=100)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="VERSION_NUM", nullable=true, length=100)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	
	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
	
}
