package com.sie.saaf.base.commmon.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BaseWatsonsBpmNodeUserEntity_HI_RO Entity Object Fri May 08 09:50:31 CST 2020
 * Auto Generate
 */

public class BaseWatsonsBpmNodeUserEntity_HI_RO {

	public static String query_sql = "SELECT\r\n"
			+ "     node.seq_id              AS seqId,\r\n"
			+ "     node.process_name        AS processName,\r\n"
			+ "     node.node_name           AS nodeName,\r\n"
			+ "     node.user_name           AS userName,\r\n"
			+ "     node.user_id             AS userId,\r\n"
			+ "     node.version_num         AS versionNum,\r\n"
			+ "     node.creation_date       AS creationDate,\r\n"
			+ "     node.created_by          AS createdBy,\r\n"
			+ "     node.last_update_date    AS lastUpdateDate,\r\n"
			+ "     node.last_updated_by     AS lastUpdatedBy,\r\n"
			+ "     node.last_update_login   AS lastUpdateLogin\r\n"
			+ " FROM\r\n" + "     base_watsons_bpm_node_user node where 1=1 ";

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

	public Integer getSeqId() {
		return seqId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
