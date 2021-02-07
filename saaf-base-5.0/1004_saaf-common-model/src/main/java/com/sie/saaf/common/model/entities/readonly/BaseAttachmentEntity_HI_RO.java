package com.sie.saaf.common.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * BaseAttachmentEntity_HI Entity Object
 * Mon Aug 20 14:29:11 CST 2018  Auto Generate
 */
public class BaseAttachmentEntity_HI_RO {

	public static final  String QUERY_SELECT_SQL = "SELECT\n" +
			"\tba.file_id fileId,\n" +
			"\tba.source_file_name sourceFileName,\n" +
			"\tba.function_id functionId,\n" +
			"\tba.business_id businessId,\n" +
			"\tba.file_store_system fileStoreSystem,\n" +
			"\tba.file_path filePath,\n" +
			"\tba.bucket_name bucketName,\n" +
			"\tba.phy_file_name phyFileName,\n" +
			"\tba.status status,\n" +
			"\tba.file_size fileSize,\n" +
			"\tba.file_type fileType,\n" +
			"\tba.delete_flag deleteFlag,\n" +
			"\tba.created_by createdBy,\n" +
			"\tba.creation_date creationDate,\n" +
			"\tba.last_updated_by lastUpdatedBy,\n" +
			"\tba.last_update_date lastUpdateDate,\n" +
			"\tba.last_update_login lastUpdateLogin,\n" +
			"\tba.version_num versionNum,\n" +
			"\tbu.user_full_name createdByUser\n" +
			"FROM\n" +
			"\tbase_attachment ba,\n" +
			"\tbase_users bu\n" +
			"WHERE\n" +
			"\tba.created_by = bu.user_id\n" +
			" AND ba.business_id = ?\n" +
			" AND ba.function_id = ?\n" +
			" AND ba.delete_flag = '0';";

	public static final  String QUERY_SELECT_SQL_MAX = "select  \n" +
			"  ba.file_id fileId,\n" +
			"  ba.source_file_name sourceFileName,\n" +
			"  ba.function_id functionId,\n" +
			"  ba.business_id businessId,\n" +
			"  ba.file_store_system fileStoreSystem,\n" +
			"  ba.file_path filePath,\n" +
			"  ba.bucket_name bucketName,\n" +
			"  ba.phy_file_name phyFileName,\n" +
			"  ba.status status,\n" +
			"  ba.file_size fileSize,\n" +
			"  ba.file_type fileType,\n" +
			"  ba.delete_flag deleteFlag,\n" +
			"  ba.created_by createdBy,\n" +
			"  ba.creation_date creationDate,\n" +
			"  ba.last_updated_by lastUpdatedBy,\n" +
			"  ba.last_update_date lastUpdateDate,\n" +
			"  ba.last_update_login lastUpdateLogin,\n" +
			"  ba.version_num versionNum,\n" +
			"  bu.user_full_name createdByUser\n" +
			"  from base_attachment ba,\n" +
			"  base_users bu\n" +
			" where ba.created_by = bu.user_id and ba.delete_flag = '0' and ba.file_id = (select max(bac.file_id)\n" +
			"                        from base_attachment bac\n" +
			"                       where bac.delete_flag = '0' and \n" +
			"						  bac.function_id = ?\n" +
			"                         and bac.business_id = ?\n" +
			"                         ) ";

	public static final  String QUERY_SELECT_FILE_IDS_SQL = "SELECT listagg(ba.file_id,',')  within group(order by  ba.file_id asc) fileIds  " +
			"FROM  base_attachment  ba where ba.function_id = ? and ba.business_id = ?";

	public static final  String QUERY_SELECT_FILE_IDS_ALL_SQL = "SELECT ba.file_id  " +
			"FROM  base_attachment  ba where ba.function_id = ? and ba.business_id = ?";


    private Long fileId; //文件ID
	private String fileIds; //文件ID
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
	private String createdByUser; //创建用户名

	private String remark;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Integer getFileStoreSystem() {
		return fileStoreSystem;
	}

	public void setFileStoreSystem(Integer fileStoreSystem) {
		this.fileStoreSystem = fileStoreSystem;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getPhyFileName() {
		return phyFileName;
	}

	public void setPhyFileName(String phyFileName) {
		this.phyFileName = phyFileName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getFileSize() {
		return fileSize;
	}

	public void setFileSize(BigDecimal fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getAccessPath() {
		return accessPath;
	}

	public void setAccessPath(String accessPath) {
		this.accessPath = accessPath;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}
}
