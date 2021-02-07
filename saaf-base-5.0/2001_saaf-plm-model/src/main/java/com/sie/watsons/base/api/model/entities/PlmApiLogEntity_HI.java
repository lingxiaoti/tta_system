package com.sie.watsons.base.api.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmApiLogEntity_HI Entity Object
 * Wed Dec 18 11:22:57 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_API_LOG")
public class PlmApiLogEntity_HI {
    private Integer headId;
    private String requestId;
    private String item;
    private String status;
    private String remark;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String returnFlag;
	private String logType;



	@Id
	@SequenceGenerator(name="PLM_API_LOG_SEQ", sequenceName="PLM_API_LOG_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="PLM_API_LOG_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="head_id", nullable=false, length=22)
	public Integer getHeadId() {
		return headId;
	}

	public void setHeadId(Integer headId) {
		this.headId = headId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column(name="request_id", nullable=false, length=100)	
	public String getRequestId() {
		return requestId;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name="item", nullable=false, length=100)	
	public String getItem() {
		return item;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=100)	
	public String getStatus() {		return status;	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	@Column(name="return_Flag", nullable=true, length=100)
	public String getReturnFlag() {		return returnFlag;	}

	@Column(name="log_Type", nullable=true, length=100)
	public String getLogType() {		return logType;	}

	public void setLogType(String logType) {		this.logType = logType;	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=200)	
	public String getRemark() {
		return remark;
	}

//	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
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
}
