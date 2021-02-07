package com.sie.saaf.bpm.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 */

public class ActBpmTaskDelegateConfigEntity_HI_RO {

	
	public static final String QUERY_ALL_CONFIG_SQL = "SELECT \n" +
            "\t cfg.config_id AS configId,\n" +
            "\t cfg.start_time AS startTime,\n" +
            "\t cfg.end_time AS endTime,\n" +
            "\t cfg.client_user_id AS clientUserId,\n" +
            "\t cfg.delegate_user_id AS delegateUserId,\n" +
            "\t cfg.category_ids AS categoryIds,\n" +
            "\t cfg.proc_def_keys AS processDefinitionKeys,\n" +
            "\t cfg.disabled AS disabled\n" +
            " FROM \n" +
            "\t act_bpm_task_delegate_config cfg \n" +
            "\t WHERE\n" +
            "\t 1=1\n";

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


	/** default constructor */
	public ActBpmTaskDelegateConfigEntity_HI_RO() {
	}

	public Integer getConfigId() {
		return this.configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getClientUserId() {
		return this.clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	public Integer getDelegateUserId() {
        return delegateUserId;
    }

    public void setDelegateUserId(Integer delegateUserId) {
        this.delegateUserId = delegateUserId;
    }
    
    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getProcessDefinitionKeys() {
        return processDefinitionKeys;
    }

    public void setProcessDefinitionKeys(String processDefinitionKeys) {
        this.processDefinitionKeys = processDefinitionKeys;
    }

	public Integer getCreatedBy() {
		return this.createdBy;
	}

    public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdateLogin() {
		return this.lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(BigDecimal disabled) {
		if(disabled == null || disabled.compareTo(new BigDecimal("0")) == 0) {
			this.disabled = false;
		} else {
			this.disabled = true;
		}
    }

	public boolean getEnabled(){
		return disabled == null? true: !disabled;
	}

}