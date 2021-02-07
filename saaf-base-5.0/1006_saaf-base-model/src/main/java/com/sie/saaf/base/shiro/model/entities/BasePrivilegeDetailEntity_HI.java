package com.sie.saaf.base.shiro.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * BasePrivilegeDetailEntity_HI Entity Object
 * Thu Jan 11 19:06:53 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_privilege_detail")
public class BasePrivilegeDetailEntity_HI {
    private Integer privilegeDetailId; //主键
    private Integer privilegeRecordId; //授权头表Id
    private String privilegeDetailCode; //行表编码
    private String privilegeDetailValue; //行表值
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setPrivilegeDetailId(Integer privilegeDetailId) {
		this.privilegeDetailId = privilegeDetailId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_PRIVILEGE_DETAIL", sequenceName = "SEQ_BASE_PRIVILEGE_DETAIL", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PRIVILEGE_DETAIL", strategy = GenerationType.SEQUENCE)	
	@Column(name = "privilege_detail_id", nullable = false, length = 11)	
	public Integer getPrivilegeDetailId() {
		return privilegeDetailId;
	}

	public void setPrivilegeRecordId(Integer privilegeRecordId) {
		this.privilegeRecordId = privilegeRecordId;
	}

	@Column(name = "privilege_record_id", nullable = true, length = 11)	
	public Integer getPrivilegeRecordId() {
		return privilegeRecordId;
	}

	public void setPrivilegeDetailCode(String privilegeDetailCode) {
		this.privilegeDetailCode = privilegeDetailCode;
	}

	@Column(name = "privilege_detail_code", nullable = true, length = 150)	
	public String getPrivilegeDetailCode() {
		return privilegeDetailCode;
	}

	public void setPrivilegeDetailValue(String privilegeDetailValue) {
		this.privilegeDetailValue = privilegeDetailValue;
	}

	@Column(name = "privilege_detail_value", nullable = true, length = 50)	
	public String getPrivilegeDetailValue() {
		return privilegeDetailValue;
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
