package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseOrgResponsibilityEntity_HI Entity Object
 * Wed Jan 10 10:11:35 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_org_responsibility")
public class BaseOrgResponsibilityEntity_HI {
    private Integer orgRespId; //主键
    private Integer responsibilityId; //职责标识
    private Integer orgId; //组织机构Id
    @JSONField(format = "yyyy-MM-dd")
    private Date startDateActive; //生效时间
    @JSONField(format = "yyyy-MM-dd")
    private Date endDateActive; //失效时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setOrgRespId(Integer orgRespId) {
		this.orgRespId = orgRespId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_ORG_RESPONSIBILITY", sequenceName = "SEQ_BASE_ORG_RESPONSIBILITY", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_ORG_RESPONSIBILITY", strategy = GenerationType.SEQUENCE)	
	@Column(name = "org_resp_id", nullable = false, length = 11)	
	public Integer getOrgRespId() {
		return orgRespId;
	}

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	@Column(name = "responsibility_id", nullable = true, length = 11)	
	public Integer getResponsibilityId() {
		return responsibilityId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = true, length = 11)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name = "start_date_active", nullable = true, length = 0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name = "end_date_active", nullable = true, length = 0)	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
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
