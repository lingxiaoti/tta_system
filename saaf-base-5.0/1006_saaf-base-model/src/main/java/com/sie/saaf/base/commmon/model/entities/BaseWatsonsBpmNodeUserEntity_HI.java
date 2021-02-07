package com.sie.saaf.base.commmon.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BaseWatsonsBpmNodeUserEntity_HI Entity Object Fri May 08 09:50:31 CST 2020
 * Auto Generate
 */
@Entity
@Table(name = "base_watsons_bpm_node_user")
public class BaseWatsonsBpmNodeUserEntity_HI {
	private Integer seqId;
	private String processName;
	private String nodeName;
	private String userName;
	private Integer userId;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdatedBy;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_BPM_NODE_USER", sequenceName = "SEQ_BPM_NODE_USER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BPM_NODE_USER", strategy = GenerationType.SEQUENCE)
	@Column(name = "seq_id", nullable = false, length = 22)
	public Integer getSeqId() {
		return seqId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	@Column(name = "process_name", nullable = true, length = 100)
	public String getProcessName() {
		return processName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "node_name", nullable = true, length = 100)
	public String getNodeName() {
		return nodeName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_name", nullable = true, length = 100)
	public String getUserName() {
		return userName;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "user_id", nullable = true, length = 22)
	public Integer getUserId() {
		return userId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
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
