package com.sie.saaf.base.fileupload.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

public class SaafFileUploadEntity_HI_RO {
	
	public static String QUERY_SQL =""
			+ "SELECT\r\n" + 
			"  vfu.UPLOAD_ID uploadId,\r\n" +
			"  vfu.FILE_NAME fileName,\r\n" + 
			"  vfu.FILE_SIZE fileSize,\r\n" + 
			"  vfu.FILE_TYPE fileType,\r\n" + 
			"  vfu.SOURCE_CODE sourceCode,\r\n" + 
			"  vfu.SOURCE_ID sourceId,\r\n" + 
			"  vfu.`STATUS` ,\r\n" + 
			"  vfu.FILE_ADDRESS fileAddress,\r\n" + 
			"  vfu.START_DATE_ACTIVE startDateActive,\r\n" + 
			"  vfu.END_DATE_ACTIVE endDateActive,\r\n" + 
			"  vfu.DESCRIPTION description,\r\n" + 
			"  slv.meaning sourceCodeName,\r\n" +
			"  vfu.CREATION_DATE creationDate,\r\n" +
			"  bu.user_name userName\r\n" +
			"FROM\r\n" +
			"	saaf_file_upload vfu,\r\n" +
			"	base_lookup_values slv,\r\n" +
			"	base_users bu\r\n" +
			"WHERE\r\n" + 
			"	vfu.SOURCE_CODE = slv.lookup_code\r\n" + 
			"AND slv.lookup_type = 'FILE_SOURCE'\r\n" +
			"AND bu.user_id = vfu.CREATED_BY\r\n" +
			"AND vfu.`STATUS` = 'NEW'\r\n";
	
    private Integer uploadId; //表ID，主键，供其他表做外键
    private String fileName; //文件名
    private BigDecimal fileSize; //文件大小
    private String fileType; //文件类型
    private String sourceCode; //来源
    private Integer sourceId; //来源ID
    private String status; //状态
    private String fileAddress; //文件存储地址
    @JSONField(format = "yyyy-MM-dd")
    private Date startDateActive; //起始日期
    @JSONField(format = "yyyy-MM-dd")
    private Date endDateActive; //终止日期
	@JSONField(format = "yyyy-MM-dd")
    private Date creationDate; //创建时间
    private String description; //说明、备注
    private String sourceCodeName;
    private String userName;//创建人

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUploadId() {
		return uploadId;
	}
	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileAddress() {
		return fileAddress;
	}
	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}
	public Date getStartDateActive() {
		return startDateActive;
	}
	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}
	public Date getEndDateActive() {
		return endDateActive;
	}
	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSourceCodeName() {
		return sourceCodeName;
	}
	public void setSourceCodeName(String sourceCodeName) {
		this.sourceCodeName = sourceCodeName;
	}

}
