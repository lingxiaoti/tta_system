package com.sie.saaf.message.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * MsgLogEntity_HI Entity Object
 * Thu Jun 07 09:35:21 CST 2018  Auto Generate
 */
@Entity
@Table(name = "msg_log")
public class MsgLogEntity_HI {
    private Integer logId; //消息配置ID
    private String requestParam;
    private String returnData;
    private String jobId;
    private Integer orgId;
    private String userName;
    private String userId;
    private String requestId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdatedBy; //最后更新用户ID
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建用户ID
	private Integer lastUpdateLogin;
    private Integer isDelete; //是否已删除
    private Integer operatorUserId;
	private Integer versionNum;


    private String orgName;
	@Column(name = "user_id", nullable = false, length = 20)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_MSG_LOG", sequenceName = "SEQ_MSG_LOG", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_MSG_LOG", strategy = GenerationType.SEQUENCE)
	@Column(name = "log_id", nullable = false, length = 20)	
	public Integer getLogId() {
		return logId;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	@Column(name = "request_param", nullable = true, length = 2048)	
	public String getRequestParam() {
		return requestParam;
	}

	public void setReturnData(String returnData) {
		this.returnData = returnData;
	}

	@Column(name = "return_data", nullable = true, length = 1024)	
	public String getReturnData() {
		return returnData;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Column(name = "job_id", nullable = true, length = 96)	
	public String getJobId() {
		return jobId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = true, length = 20)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_name", nullable = true, length = 96)	
	public String getUserName() {
		return userName;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column(name = "request_id", nullable = true, length = 96)	
	public String getRequestId() {
		return requestId;
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

	@Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)
	public Integer getVersionNum() {
		return versionNum;
	}

}
