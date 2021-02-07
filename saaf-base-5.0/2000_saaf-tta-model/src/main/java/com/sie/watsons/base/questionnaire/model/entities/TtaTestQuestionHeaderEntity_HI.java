package com.sie.watsons.base.questionnaire.model.entities;

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
 * TtaTestQuestionHeaderEntity_HI Entity Object
 * Tue Jul 30 13:08:25 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_TEST_QUESTION_HEADER")
public class TtaTestQuestionHeaderEntity_HI {
    private Integer qHeaderId;
    private Integer proposalId;
    private Integer serialNumber;
    private String projectType;
    private String projectCnTitle;
    private String projectEnTitle;
    private String isEnable;
    private Integer status;
    private String description;
    private Integer sourceQHeaderId;
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
	@SequenceGenerator(name = "SEQ_TTA_TEST_QUESTION_HEADER", sequenceName = "SEQ_TTA_TEST_QUESTION_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_TEST_QUESTION_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="q_header_id", nullable=false, length=22)	
	public Integer getQHeaderId() {
		return qHeaderId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=false, length=22)	
	public Integer getProposalId() {
		return proposalId;
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

	@Column(name="project_type", nullable=true, length=50)	
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

	@Column(name="is_enable", nullable=true, length=5)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=22)	
	public Integer getStatus() {
		return status;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=500)	
	public String getDescription() {
		return description;
	}

	public void setSourceQHeaderId(Integer sourceQHeaderId) {
		this.sourceQHeaderId = sourceQHeaderId;
	}

	@Column(name="source_q_header_id", nullable=false, length=22)	
	public Integer getSourceQHeaderId() {
		return sourceQHeaderId;
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
