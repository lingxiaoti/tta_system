package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmBaseLevelEntity_HI Entity Object Thu Nov 14 17:27:58 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_BASE_LEVEL")
public class PlmBaseLevelEntity_HI {
	private Integer levelId;
	private String levelName;
	private String levelDesc;
	private String levelNumber;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private Integer parentLevelId;
	private String parentLevelName;
	private String createdstr;

	@Column(name = "createdstr", nullable = true, length = 255)
	public String getCreatedstr() {
		return createdstr;
	}

	public void setCreatedstr(String createdstr) {
		this.createdstr = createdstr;
	}

	@Column(name = "parent_level_name", nullable = true, length = 255)
	public String getParentLevelName() {
		return parentLevelName;
	}

	public void setParentLevelName(String parentLevelName) {
		this.parentLevelName = parentLevelName;
	}

	@Column(name = "parent_level_id", nullable = true, length = 11)
	public Integer getParentLevelId() {
		return parentLevelId;
	}

	public void setParentLevelId(Integer parentLevelId) {
		this.parentLevelId = parentLevelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_LEVEL", sequenceName = "SEQ_PLM_LEVEL", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "SEQ_PLM_LEVEL", strategy = GenerationType.SEQUENCE)
	@Column(name = "level_id", nullable = false, length = 22)
	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	@Column(name = "level_name", nullable = true, length = 255)
	public String getLevelName() {
		return levelName;
	}

	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}

	@Column(name = "level_desc", nullable = true, length = 255)
	public String getLevelDesc() {
		return levelDesc;
	}

	public void setLevelNumber(String levelNumber) {
		this.levelNumber = levelNumber;
	}

	@Column(name = "level_number", nullable = true, length = 22)
	public String getLevelNumber() {
		return levelNumber;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
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
