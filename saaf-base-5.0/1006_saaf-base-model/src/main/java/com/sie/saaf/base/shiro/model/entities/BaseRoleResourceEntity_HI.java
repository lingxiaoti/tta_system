package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseRoleResourceEntity_HI Entity Object
 * Tue Dec 12 19:08:06 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_role_resource")
public class BaseRoleResourceEntity_HI {
	private Integer roleResourceId; //主键ID
	private Integer roleId; //角色Id
	private Integer resourceId; //资源标识
	private String resourceValue; //值
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建日期
	private Integer createdBy; //创建人
	private Integer lastUpdatedBy; //更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新日期
	private Integer versionNum; //版本号
	private Integer lastUpdateLogin; //last_update_login
	private Integer operatorUserId;


	@Id
	@SequenceGenerator(name = "SEQ_BASE_ROLE_RESOURCE", sequenceName = "SEQ_BASE_ROLE_RESOURCE", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_ROLE_RESOURCE", strategy = GenerationType.SEQUENCE)
	@Column(name = "role_resource_id", nullable = false, length = 11)
	public Integer getRoleResourceId() {
		return roleResourceId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleResourceId(Integer roleResourceId) {
		this.roleResourceId = roleResourceId;
	}

	@Column(name = "role_id", nullable = true, length = 11)
	public Integer getRoleId() {
		return roleId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "resource_id", nullable = true, length = 11)
	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}

	@Column(name = "resource_value", nullable = true, length = 100)
	public String getResourceValue() {
		return resourceValue;
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
