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
 * PlmProductMedicalfileEntity_HI Entity Object Tue Sep 17 11:52:44 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_MEDICALFILE")
public class PlmProductMedicalfileEntity_HI {
	private Integer id;
	@columnNames(name = "医疗器械资质组id")
	private String groupId;
	private String productEc;
	private String standardFile;
	private String tagClass;
	private String noteClass;
	@columnNames(name = "医疗器械资质文件类型")
	private String fileType;
	private String fileUrl;
	@columnNames(name = "医疗器械证书编号类型")
	private String certificateType;
	@columnNames(name = "医疗器械证书编号")
	private String certificateNum;
	@columnNames(name = "医疗器械日期类型")
	private String dateType;

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
	@columnNames(name = "医疗器械资质文件日期")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date selectDate;
	private String flags;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_MEDICALF", sequenceName = "SEQ_PLM_PRODUCT_MEDICALF", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_MEDICALF", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, length = 22)
	public Integer getId() {
		return id;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "group_id", nullable = true, length = 255)
	public String getGroupId() {
		return groupId;
	}

	public void setProductEc(String productEc) {
		this.productEc = productEc;
	}

	@Column(name = "product_ec", nullable = true, length = 255)
	public String getProductEc() {
		return productEc;
	}

	public void setStandardFile(String standardFile) {
		this.standardFile = standardFile;
	}

	@Column(name = "standard_file", nullable = true, length = 255)
	public String getStandardFile() {
		return standardFile;
	}

	public void setTagClass(String tagClass) {
		this.tagClass = tagClass;
	}

	@Column(name = "tag_class", nullable = true, length = 255)
	public String getTagClass() {
		return tagClass;
	}

	public void setNoteClass(String noteClass) {
		this.noteClass = noteClass;
	}

	@Column(name = "note_class", nullable = true, length = 255)
	public String getNoteClass() {
		return noteClass;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "file_type", nullable = true, length = 255)
	public String getFileType() {
		return fileType;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name = "file_url", nullable = true, length = 255)
	public String getFileUrl() {
		return fileUrl;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	@Column(name = "certificate_type", nullable = true, length = 255)
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}

	@Column(name = "certificate_num", nullable = true, length = 255)
	public String getCertificateNum() {
		return certificateNum;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	@Column(name = "date_type", nullable = true, length = 255)
	public String getDateType() {
		return dateType;
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

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "select_date", nullable = true, length = 7)
	public Date getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

	@Column(name = "flags", nullable = true, length = 255)
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

}
