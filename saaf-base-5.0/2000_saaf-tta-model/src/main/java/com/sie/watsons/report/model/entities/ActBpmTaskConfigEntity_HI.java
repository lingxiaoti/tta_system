package com.sie.watsons.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * ActBpmTaskConfigEntity_HI Entity Object
 * Mon Feb 17 15:10:30 CST 2020  Auto Generate
 */
@Entity
@Table(name="act_bpm_task_config")
public class ActBpmTaskConfigEntity_HI {
    private Integer configId;
    private String procDefKey;
    private String taskDefId;
    private String taskName;
    private String pcformUrl;
    private String mobformUrl;
    private String pcDataUrl;
    private String mobDataUrl;
    private String processerIds;
    private String processerRoleKeys;
    private String ccIds;
    private String ccRoleKeys;
    private String variables;
    private String callbackUrl;
    private Integer editStatus;
    private Integer enableAutojump;
    private String extend;
    private Integer sortId;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer operatorUserId;

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	@Id
	@SequenceGenerator(name="seq_act_bpm_task_config", sequenceName="seq_act_bpm_task_config", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="seq_act_bpm_task_config",strategy=GenerationType.SEQUENCE)
	@Column(name="config_id", nullable=false, length=22)
	public Integer getConfigId() {
		return configId;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	@Column(name="proc_def_key", nullable=true, length=64)	
	public String getProcDefKey() {
		return procDefKey;
	}

	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}

	@Column(name="task_def_id", nullable=true, length=64)	
	public String getTaskDefId() {
		return taskDefId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name="task_name", nullable=true, length=255)	
	public String getTaskName() {
		return taskName;
	}

	public void setPcformUrl(String pcformUrl) {
		this.pcformUrl = pcformUrl;
	}

	@Column(name="pcform_url", nullable=true, length=255)	
	public String getPcformUrl() {
		return pcformUrl;
	}

	public void setMobformUrl(String mobformUrl) {
		this.mobformUrl = mobformUrl;
	}

	@Column(name="mobform_url", nullable=true, length=255)	
	public String getMobformUrl() {
		return mobformUrl;
	}

	public void setPcDataUrl(String pcDataUrl) {
		this.pcDataUrl = pcDataUrl;
	}

	@Column(name="pc_data_url", nullable=true, length=255)	
	public String getPcDataUrl() {
		return pcDataUrl;
	}

	public void setMobDataUrl(String mobDataUrl) {
		this.mobDataUrl = mobDataUrl;
	}

	@Column(name="mob_data_url", nullable=true, length=255)	
	public String getMobDataUrl() {
		return mobDataUrl;
	}

	public void setProcesserIds(String processerIds) {
		this.processerIds = processerIds;
	}

	@Column(name="processer_ids", nullable=true, length=255)	
	public String getProcesserIds() {
		return processerIds;
	}

	public void setProcesserRoleKeys(String processerRoleKeys) {
		this.processerRoleKeys = processerRoleKeys;
	}

	@Column(name="processer_role_keys", nullable=true, length=255)	
	public String getProcesserRoleKeys() {
		return processerRoleKeys;
	}

	public void setCcIds(String ccIds) {
		this.ccIds = ccIds;
	}

	@Column(name="cc_ids", nullable=true, length=255)	
	public String getCcIds() {
		return ccIds;
	}

	public void setCcRoleKeys(String ccRoleKeys) {
		this.ccRoleKeys = ccRoleKeys;
	}

	@Column(name="cc_role_keys", nullable=true, length=255)	
	public String getCcRoleKeys() {
		return ccRoleKeys;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

	@Column(name="variables", nullable=true, length=4000)	
	public String getVariables() {
		return variables;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	@Column(name="callback_url", nullable=true, length=255)	
	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}

	@Column(name="edit_status", nullable=true, length=22)	
	public Integer getEditStatus() {
		return editStatus;
	}

	public void setEnableAutojump(Integer enableAutojump) {
		this.enableAutojump = enableAutojump;
	}

	@Column(name="enable_autojump", nullable=true, length=22)	
	public Integer getEnableAutojump() {
		return enableAutojump;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	@Column(name="extend", nullable=true, length=4000)	
	public String getExtend() {
		return extend;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	@Column(name="sort_id", nullable=true, length=22)	
	public Integer getSortId() {
		return sortId;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=22)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
