package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * 流程管理权限
 *
 */
@Entity
@Table(name = "act_bpm_permission")
public class ActBpmPermissionEntity_HI {
	private Integer permissionId; // 主键
	private String processDefinitionKey; // 流程定义KEY
	private String systemCode; // 系统代码
	private Integer ouId;  // 事业部ID
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer lastUpdatedBy;
	private Integer versionNum;
	private Integer deleteFlag;
	private Integer operatorUserId;
	private List<Integer> ouIds;
	
	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_PERMISSION", sequenceName = "SEQ_ACT_BPM_PERMISSION", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_PERMISSION", strategy = GenerationType.SEQUENCE)
	@Column(name = "permission_id", nullable = false, length = 11)
	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	
	@Column(name = "proc_def_key", nullable = false, length = 64)
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	@Column(name = "system_code", nullable = false, length = 64)
	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "ou_id", nullable = false, length = 11)
	public Integer getOuId() {
		return ouId;
	}

	public void setOuId(Integer ouId) {
		this.ouId = ouId;
	}

	@Column(name = "created_by", nullable = true, length = 11)
	public Integer getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "creation_date", nullable = false, length = 0)
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}
	

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)
	public Integer getVersionNum() {
		return versionNum;
	}
	
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
	
	@Column(name="delete_flag",columnDefinition="bit default 0")
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public List<Integer> getOuIds() {
		return ouIds;
	}

	public void setOuIds(List<Integer> ouIds) {
		this.ouIds = ouIds;
	}	
	
}
