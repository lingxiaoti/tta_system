package com.sie.watsons.base.questionnaire.model.entities;

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
 * TtaQuestionHeaderEntity_HI Entity Object
 * Thu Jul 25 21:34:10 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_question_header")
public class TtaQuestionHeaderEntity_HI {
    private Integer qHeaderId;
    private Integer serialNumber;
    private String projectType;
    private String projectCnTitle;
    private String projectEnTitle;
    private String isEnable;
    private String description;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setQHeaderId(Integer qHeaderId) {
		this.qHeaderId = qHeaderId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_QUESTION_HEADER", sequenceName = "SEQ_TTA_QUESTION_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_QUESTION_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="q_header_id", nullable=false, length=22)	
	public Integer getQHeaderId() {
		return qHeaderId;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name="serial_number", nullable=true, length=22)	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	@Column(name="project_type", nullable=true, length=100)	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectCnTitle(String projectCnTitle) {
		this.projectCnTitle = projectCnTitle;
	}

	@Column(name="project_cn_title", nullable=true, length=500)	
	public String getProjectCnTitle() {
		return projectCnTitle;
	}

	public void setProjectEnTitle(String projectEnTitle) {
		this.projectEnTitle = projectEnTitle;
	}

	@Column(name="project_en_title", nullable=true, length=500)	
	public String getProjectEnTitle() {
		return projectEnTitle;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=true, length=100)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=500)	
	public String getDescription() {
		return description;
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
}
