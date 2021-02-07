package com.sie.watsons.base.proposal.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaBrandCountCRecordEntity_HI_RO Entity Object
 * Mon Jun 15 16:33:56 CST 2020  Auto Generate
 */

public class TtaBrandCountCRecordEntity_HI_RO {
    private Integer brandCountCReId;
    private String createKey;
    private String proposalId;
    private String requestUrl;
    private String requestParams;
    private String functionName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date completeDate;
    private String status;
    private String msgCode;
    private String msgRemark;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setBrandCountCReId(Integer brandCountCReId) {
		this.brandCountCReId = brandCountCReId;
	}

	
	public Integer getBrandCountCReId() {
		return brandCountCReId;
	}

	public void setCreateKey(String createKey) {
		this.createKey = createKey;
	}

	
	public String getCreateKey() {
		return createKey;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	
	public String getProposalId() {
		return proposalId;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	
	public String getRequestParams() {
		return requestParams;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	
	public String getFunctionName() {
		return functionName;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	
	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgRemark(String msgRemark) {
		this.msgRemark = msgRemark;
	}

	
	public String getMsgRemark() {
		return msgRemark;
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
