package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BasePrivilegeRecordEntity_HI Entity Object
 * Thu Jan 11 19:06:49 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_privilege_record")
public class BasePrivilegeRecordEntity_HI {
    private Integer privilegeRecordId; //主键
    private Integer orgId; //库存组织Id
    private String accessType; //使用/访问 类型
    private String businessTableName; //关联业务表
    private String businessCode; //关联业务编码/Id
    private String enabled; //是否启用（Y：启用；N：禁用）
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setPrivilegeRecordId(Integer privilegeRecordId) {
		this.privilegeRecordId = privilegeRecordId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_PRIVILEGE_RECORD", sequenceName = "SEQ_BASE_PRIVILEGE_RECORD", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PRIVILEGE_RECORD", strategy = GenerationType.SEQUENCE)	
	@Column(name = "privilege_record_id", nullable = false, length = 11)	
	public Integer getPrivilegeRecordId() {
		return privilegeRecordId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = true, length = 11)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	@Column(name = "access_type", nullable = true, length = 10)	
	public String getAccessType() {
		return accessType;
	}

	public void setBusinessTableName(String businessTableName) {
		this.businessTableName = businessTableName;
	}

	@Column(name = "businessTableName", nullable = true, length = 30)
	public String getBusinessTableName() {
		return businessTableName;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	@Column(name = "business_code", nullable = true, length = 36)	
	public String getBusinessCode() {
		return businessCode;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "enabled", nullable = true, length = 5)	
	public String getEnabled() {
		return enabled;
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
