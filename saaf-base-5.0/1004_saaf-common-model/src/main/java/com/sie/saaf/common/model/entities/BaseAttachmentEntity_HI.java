package com.sie.saaf.common.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BaseAttachmentEntity_HI Entity Object
 * Mon Aug 20 14:29:11 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_attachment")
public class BaseAttachmentEntity_HI {
    private Long fileId; //文件ID
    private String sourceFileName; //文件原名称
    private String functionId; //文件来源(文档、会议、申请单据等功能ID)
    private Long businessId; //业务来源主键
    private Integer fileStoreSystem; //文件存储系统--1阿里sso 2文档系统
    private String filePath; //文件物理相对路径
	private String bucketName;
    private String phyFileName; //文件物理文件名
    private String status; //状态
    private BigDecimal fileSize; //文件大小(MB)
    private String fileType; //文件类型
    private Integer deleteFlag; //是否删除0未删除1已删除
    private Integer createdBy; //创建用户ID
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer lastUpdatedBy; //最后修改用户ID
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后修改时间
    private Integer lastUpdateLogin; //LAST_UPDATE_LOGIN
    private Integer versionNum; //版本号
    private Integer operatorUserId;
	private String accessPath;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date uploadDate;

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_ATTACHMENT", sequenceName = "SEQ_BASE_ATTACHMENT", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_ATTACHMENT", strategy = GenerationType.SEQUENCE)	
	@Column(name="file_id", nullable=false, length=20)	
	public Long getFileId() {
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

	@Column(name="business_id", nullable=true, length=20)	
	public Long getBusinessId() {
		return businessId;
	}

	public void setFileStoreSystem(Integer fileStoreSystem) {
		this.fileStoreSystem = fileStoreSystem;
	}

	@Column(name="file_store_system", nullable=true, length=11)	
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

	@Column(name="bucket_name",length = 100)
	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
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

	public void setFileSize(BigDecimal fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name="file_size", precision=10, scale=2)	
	public BigDecimal getFileSize() {
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

	@Column(name="delete_flag", nullable=true, length=11)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
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


	@Transient
	public String getAccessPath() {
		return this.filePath;
	}

	public void setAccessPath(String accessPath) {
		this.accessPath = filePath;
	}

	@Transient
	public Date getUploadDate() {
		return this.creationDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = creationDate;
	}
}
