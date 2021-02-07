package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BasePersonOrganizationEntity_HI Entity Object Wed Dec 06 10:57:26 CST 2017
 * Auto Generate
 */
@Entity
@Table(name = "base_person_organization")
public class BasePersonOrganizationEntity_HI {
	private Integer personOrgId; // 主键Id
	private Integer positionId; // 职位ID
	private Integer orgId; // 组织机构Id
	private Integer personId; // 人员Id
	@JSONField(format = "yyyy-MM-dd")
	private Date startDate; // 生效日期
	@JSONField(format = "yyyy-MM-dd")
	private Date endDate; // 失效日期
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; // 创建日期
	private Integer createdBy; // 创建人
	private Integer lastUpdatedBy; // 更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; // 更新日期
	private Integer versionNum; // 版本号
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setPersonOrgId(Integer personOrgId) {
		this.personOrgId = personOrgId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_BASE_PERSON_ORGANIZATION", sequenceName = "SEQ_BASE_PERSON_ORGANIZATION", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PERSON_ORGANIZATION", strategy = GenerationType.SEQUENCE)
	@Column(name = "person_org_id", nullable = false, length = 11)
	public Integer getPersonOrgId() {
		return personOrgId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	@Column(name = "position_id", nullable = true, length = 11)
	public Integer getPositionId() {
		return positionId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = true, length = 11)
	public Integer getOrgId() {
		return orgId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	@Column(name = "person_id", nullable = true, length = 11)
	public Integer getPersonId() {
		return personId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "start_date", nullable = true, length = 0)
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "end_date", nullable = true, length = 0)
	public Date getEndDate() {
		return endDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}
}
