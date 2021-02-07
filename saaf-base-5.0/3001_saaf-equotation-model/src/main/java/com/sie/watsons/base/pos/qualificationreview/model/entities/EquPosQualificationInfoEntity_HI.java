package com.sie.watsons.base.pos.qualificationreview.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosQualificationInfoEntity_HI Entity Object
 * Wed Sep 04 16:59:16 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_POS_QUALIFICATION_INFO")
public class EquPosQualificationInfoEntity_HI {
	private Integer qualificationId;
	private String qualificationNumber;
	private Integer supplierId;
	private String sceneType;
	private String qualificationStatus;
	private String qualificationResult;
	private Integer qualificationFileId;
	private String description;
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private String attributeCategory;
	private String attribute1;
	private String attribute2;
	private String fileName;
	private String filePath;
	private String attribute3;
	private String attribute4;
	private String attribute5;
	private String attribute6;
	private String attribute7;
	private String attribute8;
	private String attribute9;
	private String attribute10;
	private String deptCode;
	private String contactPhoneNumber;
	private String officeTelephone;
	private String rejectionReason;
	private Integer operatorUserId;

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	@Id
	@SequenceGenerator(name = "EQU_POS_QUALIFICATION_INFO_S", sequenceName = "EQU_POS_QUALIFICATION_INFO_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_POS_QUALIFICATION_INFO_S", strategy = GenerationType.SEQUENCE)
	@Column(name="qualification_id", nullable=false, length=22)
	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationNumber(String qualificationNumber) {
		this.qualificationNumber = qualificationNumber;
	}

	@Column(name="qualification_number", nullable=false, length=30)
	public String getQualificationNumber() {
		return qualificationNumber;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=false, length=22)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	@Column(name="file_Name", nullable=true, length=240)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_Path", nullable=true, length=240)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="scene_type", nullable=true, length=30)
	public String getSceneType() {
		return sceneType;
	}

	public void setQualificationStatus(String qualificationStatus) {
		this.qualificationStatus = qualificationStatus;
	}

	@Column(name="qualification_status", nullable=true, length=30)
	public String getQualificationStatus() {
		return qualificationStatus;
	}

	public void setQualificationResult(String qualificationResult) {
		this.qualificationResult = qualificationResult;
	}

	@Column(name="qualification_result", nullable=true, length=30)
	public String getQualificationResult() {
		return qualificationResult;
	}

	public void setQualificationFileId(Integer qualificationFileId) {
		this.qualificationFileId = qualificationFileId;
	}

	@Column(name="qualification_file_id", nullable=true, length=22)
	public Integer getQualificationFileId() {
		return qualificationFileId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=240)
	public String getDescription() {
		return description;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name="attribute_category", nullable=true, length=30)
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)
	public String getAttribute10() {
		return attribute10;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=false, length=20)
	public String getDeptCode() {
		return deptCode;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	@Column(name="contact_phone_number", nullable=true, length=30)
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public void setOfficeTelephone(String officeTelephone) {
		this.officeTelephone = officeTelephone;
	}

	@Column(name="office_telephone", nullable=true, length=30)
	public String getOfficeTelephone() {
		return officeTelephone;
	}

	@Column(name="rejection_reason", nullable=true, length=500)
	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
