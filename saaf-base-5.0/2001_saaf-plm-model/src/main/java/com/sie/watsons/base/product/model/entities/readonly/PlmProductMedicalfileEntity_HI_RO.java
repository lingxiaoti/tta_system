package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductMedicalfileEntity_HI_RO Entity Object Tue Sep 17 11:52:44 CST 2019
 * Auto Generate
 */

public class PlmProductMedicalfileEntity_HI_RO {
	private Integer id;
	private String groupId;
	private String productEc;
	private String standardFile;
	private String tagClass;
	private String noteClass;
	private String fileType;
	private String fileUrl;
	private String certificateType;
	private String certificateNum;
	private String dateType;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date date;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer productHeadId;
	private Integer operatorUserId;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date selectDate;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setProductEc(String productEc) {
		this.productEc = productEc;
	}

	public String getProductEc() {
		return productEc;
	}

	public void setStandardFile(String standardFile) {
		this.standardFile = standardFile;
	}

	public String getStandardFile() {
		return standardFile;
	}

	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}

	public String getTagClass() {
		return tagClass;
	}

	public void setNoteClass(String noteClass) {
		this.noteClass = noteClass;
	}

	public String getNoteClass() {
		return noteClass;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	public String getCertificateNum() {
		return certificateNum;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
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

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Date getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

}
