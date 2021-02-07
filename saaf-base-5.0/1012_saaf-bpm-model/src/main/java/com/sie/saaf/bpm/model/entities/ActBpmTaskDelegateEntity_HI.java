package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * SaafBpmTaskAgent entity hibernate 
 */
@Entity
@Table(name = "act_bpm_task_delegate")
public class ActBpmTaskDelegateEntity_HI{

	// Fields
	private Integer delegateId;
	private String taskId;
	private Integer clientUserId;
	private Integer delegateUserId;
	private String taskName;
	private String processInstanceId;
    private String processDefinitionKey;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date taskTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	private Boolean isAuto;//是否系统自动创建的代办
	private String status;//PENDING待办；CANCELED取消；BACKED退回；RESOLVED完成；DESTROYED作废
	private Integer deleteFlag;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer lastUpdatedBy;
	private Integer versionNum;
	private Integer operatorUserId;

	// Constructors
	/** default constructor */
	public ActBpmTaskDelegateEntity_HI() {
	}


	// Property accessors
	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_TASK_DELEGATE", sequenceName = "SEQ_ACT_BPM_TASK_DELEGATE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_TASK_DELEGATE", strategy = GenerationType.SEQUENCE)
	@Column(name = "delegate_id", unique = true, nullable = false)
	public Integer getDelegateId() {
		return this.delegateId;
	}

	public void setDelegateId(Integer delegateId) {
		this.delegateId = delegateId;
	}

	@Column(name = "task_id", nullable = false, length = 64)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "client_user_id", nullable = false)
	public Integer getClientUserId() {
		return this.clientUserId;
	}

	public void setClientUserId(Integer clientUserId) {
		this.clientUserId = clientUserId;
	}

	@Column(name = "delegate_user_id")
	public Integer getDelegateUserId() {
		return this.delegateUserId;
	}

	public void setDelegateUserId(Integer delegateUserId) {
		this.delegateUserId = delegateUserId;
	}
	
	@Column(name = "task_name")
	public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name = "proc_inst_id")
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column(name = "proc_def_key")
    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    @Column(name = "task_time", length = 19)
	public Date getTaskTime() {
		return this.taskTime;
	}

	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}

	@Column(name = "start_time", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column(name = "delete_flag",columnDefinition="bit default 0")
    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

	@Column(name = "status")
	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    @Column(name = "is_auto")
    public Boolean getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Boolean isAuto) {
        this.isAuto = isAuto;
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

    @Transient
    public String getStatusName() {
        String statusName = null;
        if(status!=null) {
            switch(status) {
            case "RESOLVED":statusName="已办";break;
            case "PENDING":statusName="待办";break;
            case "CANCELED":statusName="已取消";break;
            case "BACKED":statusName="退回";break;
            case "FINISHED":statusName="完成";break;
            case "DESTROYED":statusName="作废";break;
            default:break;
            }
        }
        return statusName;
    }


    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

}