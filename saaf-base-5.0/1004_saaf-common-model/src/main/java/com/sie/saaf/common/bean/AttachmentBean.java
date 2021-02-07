package com.sie.saaf.common.bean;

import java.util.Date;

/**
 * @author ray.zhang
 */
public class AttachmentBean {
	//文件ID
	private long fileId;
	//文件原名称
	private String sourceFileName;
	//文件来源(文档、会议、申请单据等功能ID)
	private long functionId;
	//业务来源主键
	private long businessId;
	//文件物理相对路径
	private String filePath;
	//文件物理文件名
	private String phyFileName;
	//状态
	private String status;
	//创建人
	private long creationBy;
	//创建时间
	private Date creationDate;
	//最后修改人
	private long lastUpdateBy;
	//最后修改时间
	private Date lastUpdateDate;
	//是否删除
	private String isDelete;
	//文件大小(字节)
	private double fileSize;
	//文件类型
	private String fileType;
	//文件说明
	private String fileDesc;
	//上传成功与否
	private boolean importStatus;
	private String importDate;
	//转换后的文件大小
	private String fileSizes;
	//图片的路径
	private String imagePath;
	//异常信息
	private String errorMessage;
	//是否可以预览
	private boolean isPerview;
	//创建人
	private String personName;
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public String getSourceFileName() {
		return sourceFileName;
	}
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	public long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(long functionId) {
		this.functionId = functionId;
	}
	public long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	public long getCreationBy() {
		return creationBy;
	}
	public void setCreationBy(long creationBy) {
		this.creationBy = creationBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public long getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public boolean isImportStatus() {
		return importStatus;
	}
	public void setImportStatus(boolean importStatus) {
		this.importStatus = importStatus;
	}
	public String getImportDate() {
		return importDate;
	}
	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}
	public String getFileSizes() {
		return fileSizes;
	}
	public void setFileSizes(String fileSizes) {
		this.fileSizes = fileSizes;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public boolean getIsPerview() {
		return isPerview;
	}
	public void setIsPerview(boolean isPerview) {
		this.isPerview = isPerview;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	
}
