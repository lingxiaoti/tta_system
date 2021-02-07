package com.sie.saaf.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * MsgHistoryEntity_HI Entity Object
 * Thu Jun 07 09:32:32 CST 2018  Auto Generate
 */
@Entity
@Table(name = "msg_history")
public class MsgHistoryEntity_HI {
	private Integer msgHistoryId; //消息记录ID
	private Integer orgId; //OU
	private String jobId;
	private String channelType; //渠道类型
	private String msgTypeCode; //消息类型CODE
	private Integer msgCfgId;
	private String receiveId;
	private String receiveCode; //接收对象
	private String receiveName;
	private String sourceType; //记录来源于哪个系统
	private String bizType; //请求源功能ID
	private String bizId; //请求源记录的主键ID
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date msgTransactionDate; //消息请求时间
	private String msgTemplateId;
	private String valueList;
	private String msgSubject; //消息标题
	private String msgContent; //消息内容
	private Integer msgPriority;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date sendDate; //发送时间
	private String sendStatus; //发送状态：1.待发送，2.已发送成功   3.发送异常
	private Integer failureTimes;
	private String returnMsg; //返回消息
	private String synchro; //Y  同步 N  异步
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //最后更新时间
	private Integer lastUpdatedBy; //最后更新用户ID
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建时间
	private Integer createdBy; //创建用户ID
	private Integer lastUpdateLogin;
	private Integer isDelete; //是否已删除
	private Integer operatorUserId;
	private String requestId;
	private String msgUrl;//消息链接

	@Column(name = "msg_url", nullable = true, length = 255)
	public String getMsgUrl() {
		return msgUrl;
	}

	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl;
	}

	@Column(name = "request_id", nullable = false, length = 96)
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_MSG_HISTORY", sequenceName = "SEQ_MSG_HISTORY", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_MSG_HISTORY", strategy = GenerationType.SEQUENCE)
	@Column(name = "msg_history_id", nullable = false, length = 20)
	public Integer getMsgHistoryId() {
		return msgHistoryId;
	}

	public void setMsgHistoryId(Integer msgHistoryId) {
		this.msgHistoryId = msgHistoryId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = false, length = 8)
	public Integer getOrgId() {
		return orgId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Column(name = "job_id", nullable = true, length = 96)
	public String getJobId() {
		return jobId;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name = "channel_type", nullable = false, length = 30)
	public String getChannelType() {
		return channelType;
	}

	public void setMsgTypeCode(String msgTypeCode) {
		this.msgTypeCode = msgTypeCode;
	}

	@Column(name = "msg_type_code", nullable = false, length = 20)
	public String getMsgTypeCode() {
		return msgTypeCode;
	}

	public void setMsgCfgId(Integer msgCfgId) {
		this.msgCfgId = msgCfgId;
	}

	@Column(name = "msg_cfg_id", nullable = false, length = 15)
	public Integer getMsgCfgId() {
		return msgCfgId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	@Column(name = "receive_id", nullable = true, length = 64)
	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}

	@Column(name = "receive_code", nullable = true, length = 1024)
	public String getReceiveCode() {
		return receiveCode;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	@Column(name = "receive_name", nullable = true, length = 255)
	public String getReceiveName() {
		return receiveName;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "source_type", nullable = true, length = 50)
	public String getSourceType() {
		return sourceType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	@Column(name = "biz_type", nullable = false, length = 64)
	public String getBizType() {
		return bizType;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	@Column(name = "biz_id", nullable = false, length = 96)
	public String getBizId() {
		return bizId;
	}

	public void setMsgTransactionDate(Date msgTransactionDate) {
		this.msgTransactionDate = msgTransactionDate;
	}

	@Column(name = "msg_transaction_date", nullable = true, length = 0)
	public Date getMsgTransactionDate() {
		return msgTransactionDate;
	}

	public void setMsgTemplateId(String msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}

	@Column(name = "msg_template_id", nullable = true, length = 64)
	public String getMsgTemplateId() {
		return msgTemplateId;
	}

	public void setValueList(String valueList) {
		this.valueList = valueList;
	}

	@Column(name = "value_list", nullable = true, length = 1024)
	public String getValueList() {
		return valueList;
	}

	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}

	@Column(name = "msg_subject", nullable = true, length = 200)
	public String getMsgSubject() {
		return msgSubject;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@Column(name = "msg_content", nullable = true, length = 0)
	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgPriority(Integer msgPriority) {
		this.msgPriority = msgPriority;
	}

	@Column(name = "msg_priority", nullable = true, length = 11)
	public Integer getMsgPriority() {
		return msgPriority;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "send_date", nullable = true, length = 0)
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	@Column(name = "send_status", nullable = true, length = 30)
	public String getSendStatus() {
		return sendStatus;
	}

	public void setFailureTimes(Integer failureTimes) {
		this.failureTimes = failureTimes;
	}

	@Column(name = "failure_times", nullable = true, length = 15)
	public Integer getFailureTimes() {
		return failureTimes;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@Column(name = "return_msg", nullable = true, length = 1024)
	public String getReturnMsg() {
		return returnMsg;
	}

	public void setSynchro(String synchro) {
		this.synchro = synchro;
	}

	@Column(name = "synchro", nullable = true, length = 1)
	public String getSynchro() {
		return synchro;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = false, length = 20)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = false, length = 0)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = false, length = 20)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "is_delete", nullable = false, length = 11)
	public Integer getIsDelete() {
		return isDelete;
	}

	@Transient
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
