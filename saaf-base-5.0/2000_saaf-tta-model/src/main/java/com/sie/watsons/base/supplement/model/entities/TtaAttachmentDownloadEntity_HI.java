package com.sie.watsons.base.supplement.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaAttachmentDownloadEntity_HI Entity Object
 * Sat Jul 20 11:29:44 CST 2019  Auto Generate
 * 附件下载表
 */
@Entity
@Table(name="TTA_ATTACHMENT_DOWNLOAD")
public class TtaAttachmentDownloadEntity_HI {
    private Integer fileId;//文件id
    private String sourceFileName;//文件原名称
    private String functionId;//模块id
    private Long businessId;//业务来源主键
    private Integer fileStoreSystem;//文件存储系统--1阿里sso 2文档系统
    private String filePath;//文件路径
    private String bucketName;//目录名
    private String phyFileName;//文件物理名
    private String status;//状态
    private java.math.BigDecimal fileSize;//文件大小
    private String fileType;//文件类型
    private Integer deleteFlag;//是否删除 0未删除1 已删除
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;//版本号
    private Integer operatorUserId;

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Id
	@SequenceGenerator(name="SEQ_ATTACHMENT_DOWNLOAD", sequenceName="SEQ_ATTACHMENT_DOWNLOAD", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_ATTACHMENT_DOWNLOAD",strategy=GenerationType.SEQUENCE)
	@Column(name="file_id", nullable=false, length=22)	
	public Integer getFileId() {
		return fileId;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	@Column(name="source_file_name", nullable=true, length=200)	
	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	@Column(name="function_id", nullable=true, length=50)	
	public String getFunctionId() {
		return functionId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	@Column(name="business_id", nullable=true, length=22)	
	public Long getBusinessId() {
		return businessId;
	}

	public void setFileStoreSystem(Integer fileStoreSystem) {
		this.fileStoreSystem = fileStoreSystem;
	}

	@Column(name="file_store_system", nullable=true, length=22)	
	public Integer getFileStoreSystem() {
		return fileStoreSystem;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="file_path", nullable=true, length=200)	
	public String getFilePath() {
		return filePath;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	@Column(name="bucket_name", nullable=true, length=100)	
	public String getBucketName() {
		return bucketName;
	}

	public void setPhyFileName(String phyFileName) {
		this.phyFileName = phyFileName;
	}

	@Column(name="phy_file_name", nullable=true, length=100)	
	public String getPhyFileName() {
		return phyFileName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=2)	
	public String getStatus() {
		return status;
	}

	public void setFileSize(java.math.BigDecimal fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name="file_size", nullable=true, length=22)	
	public java.math.BigDecimal getFileSize() {
		return fileSize;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=true, length=20)	
	public String getFileType() {
		return fileType;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=22)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
