package com.sie.saaf.bpm.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * SaafBpmMsg entity
 */
@Entity
@Table(name = "act_bpm_communicate")
public class ActBpmCommunicateEntity_HI{

	// Fields

	private Integer communicateId;
	private String type;  //沟通类型，发起沟通：COMMON；催办：URGE
	private String content;
	private String processInstanceId;
	private String processDefinitionKey;
	private String taskId;
	private String taskName;
	private Integer receiverId;
	private String messageChannel; //发送渠道，","分隔
	private String attachmentUrl;//附件
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
	private String title;

	// Constructors

	/** default constructor */
	public ActBpmCommunicateEntity_HI() {
	}

	

	// Property accessors
	@Id
	@SequenceGenerator(name = "SEQ_ACT_BPM_COMMUNICATE", sequenceName = "SEQ_ACT_BPM_COMMUNICATE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_ACT_BPM_COMMUNICATE", strategy = GenerationType.SEQUENCE)
    @Column(name = "communicate_id", nullable = false, length = 11)
	public Integer getCommunicateId() {
		return communicateId;
	}

	public void setCommunicateId(Integer communicateId) {
		this.communicateId = communicateId;
	}

	@Column(name = "type", nullable = false, length = 16)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Column(name = "content", length = 4000)
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

	@Column(name = "message_channel", length = 255)
	public String getMessageChannel() {
		return messageChannel;
	}

	public void setMessageChannel(String messageChannel) {
		this.messageChannel = messageChannel;
	}
	
	@Column(name = "attachment_url", length = 255)
	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
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
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}