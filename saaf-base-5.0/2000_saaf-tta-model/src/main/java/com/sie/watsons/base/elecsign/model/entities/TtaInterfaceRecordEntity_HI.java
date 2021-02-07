package com.sie.watsons.base.elecsign.model.entities;

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
 * TtaInterfaceRecordEntity_HI Entity Object
 * Thu Jul 09 18:22:22 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_INTERFACE_RECORD")
public class TtaInterfaceRecordEntity_HI {
    private Integer recordId;
    private String requestData;
    private String responseData;
    private String businessKey;
    private String businessType;
    private String status;
    private String description;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_INTERFACE_RECORD", sequenceName="SEQ_TTA_INTERFACE_RECORD", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_INTERFACE_RECORD",strategy=GenerationType.SEQUENCE)
	@Column(name="record_id", nullable=false, length=22)
	public Integer getRecordId() {
		return recordId;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	@Column(name="request_data", nullable=true, length=4000)	
	public String getRequestData() {
		return requestData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	@Column(name="response_data", nullable=true, length=4000)	
	public String getResponseData() {
		return responseData;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Column(name="business_key", nullable=false, length=50)	
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name="business_type", nullable=false, length=50)	
	public String getBusinessType() {
		return businessType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=30)	
	public String getStatus() {
		return status;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=500)	
	public String getDescription() {
		return description;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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
