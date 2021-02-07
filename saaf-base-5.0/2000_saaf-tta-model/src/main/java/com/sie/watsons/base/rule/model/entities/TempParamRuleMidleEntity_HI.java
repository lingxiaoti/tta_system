package com.sie.watsons.base.rule.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TempParamRuleMidleEntity_HI Entity Object
 * Wed May 29 16:22:30 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_temp_param_rule_midle")
public class TempParamRuleMidleEntity_HI implements Serializable {
	
    private Integer ruleParamId;
    private Integer ruleId;
    private Integer paramId;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setRuleParamId(Integer ruleParamId) {
		this.ruleParamId = ruleParamId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_TEMP_PARAM_RULE_MIDLE", sequenceName="SEQ_TTA_TEMP_PARAM_RULE_MIDLE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_TEMP_PARAM_RULE_MIDLE",strategy=GenerationType.SEQUENCE)
	@Column(name="rule_param_id", nullable=false, length=22)	
	public Integer getRuleParamId() {
		return ruleParamId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	@Column(name="rule_id", nullable=false, length=22)	
	public Integer getRuleId() {
		return ruleId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	@Column(name="param_id", nullable=false, length=22)	
	public Integer getParamId() {
		return paramId;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
