package com.sie.watsons.base.clause.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaCollectTypeLineEntity_HI Entity Object
 * Fri Jun 14 10:48:14 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_COLLECT_TYPE_LINE")
public class TtaCollectTypeLineEntity_HI {
    private Integer collectTypeId;
    private Integer clauseId;
    private String collectType;
    private String standardValue;
    private String unitValue;
    private String isEnable;
    private String isDefaultValue;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer teamFrameworkId;
	private Integer deleteFlag;
	private Integer parentId;
	private String isMajor;
	private String rule;

	public void setCollectTypeId(Integer collectTypeId) {
		this.collectTypeId = collectTypeId;
	}
	@Id
	@SequenceGenerator(name="SEQ_TTA_COLLECT_TYPE_LINE", sequenceName="SEQ_TTA_COLLECT_TYPE_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_COLLECT_TYPE_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="collect_type_id", nullable=false, length=22)	
	public Integer getCollectTypeId() {
		return collectTypeId;
	}

	public void setClauseId(Integer clauseId) {
		this.clauseId = clauseId;
	}

	@Column(name="clause_id", nullable=false, length=22)	
	public Integer getClauseId() {
		return clauseId;
	}

	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}

	@Column(name="collect_type", nullable=true, length=50)
	public String getCollectType() {
		return collectType;
	}

	public void setStandardValue(String standardValue) {
		this.standardValue = standardValue;
	}

	@Column(name="standard_value", nullable=true, length=50)
	public String getStandardValue() {
		return standardValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	@Column(name="unit_value", nullable=false, length=50)	
	public String getUnitValue() {
		return unitValue;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=false, length=2)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setIsDefaultValue(String isDefaultValue) {
		this.isDefaultValue = isDefaultValue;
	}

	@Column(name="is_default_value", nullable=false, length=2)	
	public String getIsDefaultValue() {
		return isDefaultValue;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
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


	@Column(name="team_framework_id", nullable=false, length=22)
	public Integer getTeamFrameworkId() {
		return teamFrameworkId;
	}

	public void setTeamFrameworkId(Integer teamFrameworkId) {
		this.teamFrameworkId = teamFrameworkId;
	}
	@Column(name="delete_flag", nullable=false, length=22)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="parent_id", nullable=true, length=22)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name="is_major", nullable=true, length=2)
	public String getIsMajor() {
		return isMajor;
	}

	public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}

	@Column(name="rule")
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
}
