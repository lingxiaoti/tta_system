package com.sie.watsons.base.product.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductDrugfileEntity_HI Entity Object Mon Sep 09 17:42:33 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_DRUGFILE")
public class PlmProductDrugfileEntity_HI {
	private Integer drugfileId;
	private String fileId;
	@columnNames(name = "药品资质文件备注")
	private String fileDesc;
	@columnNames(name = "药品资质文件")
	private String fileName;
	private String fileUrl;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private Integer productHeadId;
	@columnNames(name = "药品资质文件类型")
	private String fileType;
	@columnNames(name = "药品资质组Id")
	private String groupId;// 资质组id
	@columnNames(name = "药品证书编号类型")
	private String fileCodetype;// 证书编号类型
	@columnNames(name = "药品证书编号")
	private String fileCode;// 证书编号
	@columnNames(name = "药品日期类型")
	private String dateType;// 日期类型
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@columnNames(name = "药品日期")
	private Date selectDate;// 日期
	private String flags;

	public void setDrugfileId(Integer drugfileId) {
		this.drugfileId = drugfileId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_DRUGFILE", sequenceName = "SEQ_PLM_PRODUCT_DRUGFILE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_DRUGFILE", strategy = GenerationType.SEQUENCE)
	@Column(name = "drugfile_id", nullable = false, length = 22)
	public Integer getDrugfileId() {
		return drugfileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Column(name = "file_id", nullable = true, length = 255)
	public String getFileId() {
		return fileId;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	@Column(name = "file_desc", nullable = true, length = 255)
	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_name", nullable = true, length = 255)
	public String getFileName() {
		return fileName;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name = "file_url", nullable = true, length = 255)
	public String getFileUrl() {
		return fileUrl;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "group_id", nullable = true, length = 255)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "file_codetype", nullable = true, length = 255)
	public String getFileCodetype() {
		return fileCodetype;
	}

	public void setFileCodetype(String fileCodetype) {
		this.fileCodetype = fileCodetype;
	}

	@Column(name = "file_code", nullable = true, length = 255)
	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	@Column(name = "date_type", nullable = true, length = 255)
	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	@Column(name = "select_date", nullable = true, length = 7)
	public Date getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

	@Column(name = "file_type", nullable = true, length = 255)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "flags", nullable = true, length = 255)
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

}
