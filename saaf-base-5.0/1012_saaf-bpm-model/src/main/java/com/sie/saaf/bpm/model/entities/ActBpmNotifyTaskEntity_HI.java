package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * ActBpmNotifyTaskEntity_HI entity
 */
@Entity
@Table(name = "act_bpm_notify_task")
public class ActBpmNotifyTaskEntity_HI{

	// Fields

	private Integer notifyId;
	private String title;
	private String content;
	private String processInstanceId;
	private String processDefinitionKey;
	private String taskId;
	private String taskName;
	private Integer receiverId;
	private Integer status;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date readTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer lastUpdatedBy;
	private Integer versionNum;
	private Integer deleteFlag;
	private Integer operatorUserId;

	// Constructors

	/** default constructor */
	public ActBpmNotifyTaskEntity_HI() {
	}

	

	// Property accessors
	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_NOTIFY_TASK", sequenceName = "SEQ_ACT_BPM_NOTIFY_TASK", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_NOTIFY_TASK", strategy = GenerationType.SEQUENCE)
    @Column(name = "notify_id", nullable = false, length = 11)
	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}

	@Column(name = "title", length = 64)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", length = 16777215)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "proc_inst_id", nullable = false, length = 64)
	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	@Column(name = "proc_def_key", nullable = false, length = 64)
	public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }



    @Column(name = "task_id", length = 64)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "task_name", length = 64)
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "receiver_id")
	public Integer getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	
	@Column(name = "read_time")
	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Column(name = "last_updated_by")
	public Integer getLastUpdatedBy() {
		return this.lastUpdatedBy;
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

	@Column(name = "delete_flag")
	public Integer getDeleteFlag() {
		return this.deleteFlag;
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
    public String getStatusName() {
        switch(status) {
        case 0:return "未阅";
        case 1:return "已阅";
        default:break;
        }
        return null;
    }


}