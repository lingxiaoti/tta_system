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
@Table(name="TTA_BASE_RULE_HEADER")
public class RuleHEntity_HI {
    private Integer ruleId;
    private String ruleName;
    private String open;
    private Integer versionNum;
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	
    public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_TEMP_RULE_DEF", sequenceName="SEQ_TTA_TEMP_RULE_DEF", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_TEMP_RULE_DEF",strategy=GenerationType.SEQUENCE)
	@Column(name="RULE_ID", nullable=false, length=22)	
	public Integer getRuleId() {
		return ruleId;
	}
	
	@Column(name="RULE_NAME", nullable=true, length=100)	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name="OPEN", nullable=true, length=100)
	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	@Column(name="VERSION_NUM", nullable=true, length=100)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
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
	
	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
	
}
