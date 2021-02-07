package com.sie.saaf.common.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseManualSupplementEntity_HI Entity Object
 * Thu May 17 11:36:10 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_manual_supplement")
public class BaseManualSupplementEntity_HI {
    private Integer manualId;
    private Integer messageIndex; //消息体序号
    private String messageBody; //消息体内容
    private String requestUrl; //请求地址
    private String queueName; //队列名
    private String status; //状态
    private Integer sendQueueTimes; //发送到mq 的次数
    private String messageContentBean; //在mq中的完整的消息内容
    private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setManualId(Integer manualId) {
		this.manualId = manualId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_MANUAL_SUPPLEMENT", sequenceName = "SEQ_BASE_MANUAL_SUPPLEMENT", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_MANUAL_SUPPLEMENT", strategy = GenerationType.SEQUENCE)	
	@Column(name = "manual_id", nullable = false, length = 11)	
	public Integer getManualId() {
		return manualId;
	}

	public void setMessageIndex(Integer messageIndex) {
		this.messageIndex = messageIndex;
	}

	@Column(name = "message_index", nullable = true, length = 11)	
	public Integer getMessageIndex() {
		return messageIndex;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	@Column(name = "message_body", nullable = true, length = 0)	
	public String getMessageBody() {
		return messageBody;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Column(name = "request_url", nullable = true, length = 1500)	
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Column(name = "queue_name", nullable = true, length = 200)	
	public String getQueueName() {
		return queueName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "status", nullable = true, length = 255)	
	public String getStatus() {
		return status;
	}

	public void setSendQueueTimes(Integer sendQueueTimes) {
		this.sendQueueTimes = sendQueueTimes;
	}

	@Column(name = "send_queue_times", nullable = true, length = 2)	
	public Integer getSendQueueTimes() {
		return sendQueueTimes;
	}

	public void setMessageContentBean(String messageContentBean) {
		this.messageContentBean = messageContentBean;
	}

	@Column(name = "message_content_bean", nullable = true, length = 0)	
	public String getMessageContentBean() {
		return messageContentBean;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "delete_flag", nullable = true, length = 11)	
	public Integer getDeleteFlag() {
		return deleteFlag;
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
