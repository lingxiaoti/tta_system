package com.sie.watsons.base.supplement.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaAttachmentDownloadEntity_HI_RO Entity Object
 * Sat Jul 20 11:29:44 CST 2019  Auto Generate
 * 附加下载表
 */

public class TtaAttachmentDownloadEntity_HI_RO {
    private Integer fileId;
    private String sourceFileName;
    private String functionId;
    private Long businessId;
    private Integer fileStoreSystem;
    private String filePath;
    private String bucketName;
    private String phyFileName;
    private String status;
    private java.math.BigDecimal fileSize;
    private String fileType;
    private Integer deleteFlag;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
	private String createdByUser;//创建用户名

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	
	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	
	public String getFunctionId() {
		return functionId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	
	public Long getBusinessId() {
		return businessId;
	}

	public void setFileStoreSystem(Integer fileStoreSystem) {
		this.fileStoreSystem = fileStoreSystem;
	}

	
	public Integer getFileStoreSystem() {
		return fileStoreSystem;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public String getFilePath() {
		return filePath;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	
	public String getBucketName() {
		return bucketName;
	}

	public void setPhyFileName(String phyFileName) {
		this.phyFileName = phyFileName;
	}

	
	public String getPhyFileName() {
		return phyFileName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setFileSize(java.math.BigDecimal fileSize) {
		this.fileSize = fileSize;
	}

	
	public java.math.BigDecimal getFileSize() {
		return fileSize;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getFileType() {
		return fileType;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}
}
