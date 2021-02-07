package com.sie.saaf.common.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseManualSupplementEntity_HI Entity Object
 * Thu May 17 11:36:10 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_common_txn_confirm")
public class BaseCommonTransactionConfirmEntity_HI {
    private Integer confirmId;
    private Integer messageIndex; //消息体序号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setConfirmId(Integer confirmId) {
		this.confirmId = confirmId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_COMMON_TXN_CONFIRM", sequenceName = "SEQ_BASE_COMMON_TXN_CONFIRM", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_COMMON_TXN_CONFIRM", strategy = GenerationType.SEQUENCE)	
	@Column(name = "confirmId", nullable = false, length = 11)
	public Integer getConfirmId() {
		return confirmId;
	}

	public void setMessageIndex(Integer messageIndex) {
		this.messageIndex = messageIndex;
	}

	@Column(name = "messageIndex", nullable = true, length = 11)
	public Integer getMessageIndex() {
		return messageIndex;
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
