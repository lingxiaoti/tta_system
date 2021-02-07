package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * 
 */
@Entity
@Table(name = "act_bpm_task_delegate_config")
public class ActBpmTaskDelegateConfigEntity_HI {


	private Integer configId;
	@JSONField(format = "yyyy-MM-dd")
	private Date startTime;
	@JSONField(format = "yyyy-MM-dd")
	private Date endTime;
	private Integer clientUserId;
	private Integer delegateUserId;
	private String categoryIds;
	private String processDefinitionKeys;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer lastUpdatedBy;
	private Integer versionNum;
	private Integer deleteFlag;
	private Boolean disabled;
	private Integer operatorUserId;

	// Constructors

	/** default constructor */
	public ActBpmTaskDelegateConfigEntity_HI() {
	}

	

	// Property accessors
	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_TASK_DELEGATE_CONFIG", sequenceName = "SEQ_ACT_BPM_TASK_DELEGATE_CONFIG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_TASK_DELEGATE_CONFIG", strategy = GenerationType.SEQUENCE)
	@Column(name = "config_id", nullable = false)
	public Integer getConfigId() {
		return this.configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	@Column(name = "start_time", nullable = false, length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", nullable = false, length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "client_user_id", nullable = false)
	public Integer getClientUserId() {
		return this.clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	@Column(name = "delegate_user_id", nullable = false)
	public Integer getDelegateUserId() {
        return delegateUserId;
    }

    public void setDelegateUserId(Integer delegateUserId) {
        this.delegateUserId = delegateUserId;
    }
    
    @Column(name = "category_ids", nullable = false, length = 255)
    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    @Column(name = "proc_def_keys", nullable = false, length = 255)
    public String getProcessDefinitionKeys() {
        return processDefinitionKeys;
    }

    public void setProcessDefinitionKeys(String processDefinitionKeys) {
        this.processDefinitionKeys = processDefinitionKeys;
    }

	@Column(name = "created_by")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

    public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "creation_date", nullable = false, length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 19)
	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_login")
	public Integer getLastUpdateLogin() {
		return this.lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_updated_by", nullable = false)
	public Integer getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Version
	@Column(name = "version_num")
	public Integer getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Column(name="delete_flag",columnDefinition="bit default 0")
	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Column(name="disabled",columnDefinition="bit default 0")
	public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }


    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

}