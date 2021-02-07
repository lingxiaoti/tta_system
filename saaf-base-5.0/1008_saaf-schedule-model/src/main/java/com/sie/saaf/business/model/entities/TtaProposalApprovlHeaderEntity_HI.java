package com.sie.saaf.business.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaProposalApprovlHeaderEntity_HI Entity Object
 * Wed Mar 25 11:07:42 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_PROPOSAL_APPROVL_HEADER")
public class TtaProposalApprovlHeaderEntity_HI {
    private Integer approveId;
    private String status;
    private String way;
	private String sendWay;
    private String orderNo;
    private String reason;
    private String receiver;
    private String onlyCode;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date beginDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String isSameBatch;
	private String orderKey;
	private String userCode;
	private String type;
	public void setApproveId(Integer approveId) {
		this.approveId = approveId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_PROPOSAL_APPROVL_HEADER", sequenceName = "SEQ_TTA_PROPOSAL_APPROVL_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_PROPOSAL_APPROVL_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="approve_id", nullable=true, length=22)	
	public Integer getApproveId() {
		return approveId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=10)	
	public String getStatus() {
		return status;
	}

	public void setWay(String way) {
		this.way = way;
	}

	@Column(name="way", nullable=true, length=100)
	public String getWay() {
		return way;
	}

	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}

	@Column(name="send_way", nullable=true, length=100)
	public String getSendWay() {
		return sendWay;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=100)	
	public String getOrderNo() {
		return orderNo;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name="reason", nullable=true, length=4000)	
	public String getReason() {
		return reason;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name="receiver", nullable=true, length=4000)	
	public String getReceiver() {
		return receiver;
	}

	public void setOnlyCode(String onlyCode) {
		this.onlyCode = onlyCode;
	}

	@Column(name="only_code", nullable=true, length=4000)	
	public String getOnlyCode() {
		return onlyCode;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name="begin_date", nullable=true, length=7)	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=true, length=7)	
	public Date getEndDate() {
		return endDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=2030)	
	public String getRemark() {
		return remark;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="is_same_batch", nullable=true, length=100)
	public String getIsSameBatch() {
		return isSameBatch;
	}

	public void setIsSameBatch(String isSameBatch) {
		this.isSameBatch = isSameBatch;
	}

	@Column(name="user_code", nullable=true, length=100)
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name="order_key", nullable=true, length=100)
	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	@Column(name="type", nullable=true, length=100)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
