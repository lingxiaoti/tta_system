package com.sie.watsons.base.supplement.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSideAgrtLineEntity_HI_RO Entity Object
 * Wed Jun 19 12:21:46 CST 2019  Auto Generate
 */

public class TtaSideAgrtLineEntity_HI_RO {
    private Integer sideAgrtLId;
    private Integer sideAgrtHId;
    private String sideAgrtVersion;
    private String proposalContractCode;
    private String vendorName;
    private String vendorCode;
    private String status;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setSideAgrtLId(Integer sideAgrtLId) {
		this.sideAgrtLId = sideAgrtLId;
	}

	
	public Integer getSideAgrtLId() {
		return sideAgrtLId;
	}

	public void setSideAgrtHId(Integer sideAgrtHId) {
		this.sideAgrtHId = sideAgrtHId;
	}

	
	public Integer getSideAgrtHId() {
		return sideAgrtHId;
	}

	public void setSideAgrtVersion(String sideAgrtVersion) {
		this.sideAgrtVersion = sideAgrtVersion;
	}

	
	public String getSideAgrtVersion() {
		return sideAgrtVersion;
	}

	public void setProposalContractCode(String proposalContractCode) {
		this.proposalContractCode = proposalContractCode;
	}

	
	public String getProposalContractCode() {
		return proposalContractCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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
