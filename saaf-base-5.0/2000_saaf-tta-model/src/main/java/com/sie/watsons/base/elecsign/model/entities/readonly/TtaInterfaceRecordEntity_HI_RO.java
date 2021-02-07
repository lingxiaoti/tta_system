package com.sie.watsons.base.elecsign.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaInterfaceRecordEntity_HI_RO Entity Object
 * Thu Jul 09 18:22:22 CST 2020  Auto Generate
 */

public class TtaInterfaceRecordEntity_HI_RO {
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

	
	public Integer getRecordId() {
		return recordId;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	
	public String getRequestData() {
		return requestData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	
	public String getResponseData() {
		return responseData;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	
	public String getBusinessType() {
		return businessType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
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

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
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
