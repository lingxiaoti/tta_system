package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * PubUsersOrganizationEntity_HI Entity Object
 * Fri Oct 26 11:12:47 CST 2018  Auto Generate
 */
@Entity
@Table(name="pub_users_organization")
public class PubUsersOrganizationEntity_HI {
    private Integer userOrgId; //主键Id
    private Integer orgId;
    private Integer userId; //用户ID
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate; //生效日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate; //失效日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setUserOrgId(Integer userOrgId) {
		this.userOrgId = userOrgId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PUB_USERS_ORGANIZATION", sequenceName = "SEQ_PUB_USERS_ORGANIZATION", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_PUB_USERS_ORGANIZATION", strategy = GenerationType.SEQUENCE)
	@Column(name="user_org_id", nullable=false, length=11)
	public Integer getUserOrgId() {
		return userOrgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name="org_id", nullable=true, length=11)
	public Integer getOrgId() {
		return orgId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name="user_id", nullable=true, length=11)
	public Integer getUserId() {
		return userId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="start_date", nullable=true, length=0)
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=true, length=0)
	public Date getEndDate() {
		return endDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=0)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)
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
