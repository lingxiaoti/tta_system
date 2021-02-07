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
 * EquPosZzscCredentialAttachEntity_HI Entity Object
 * Fri Sep 20 18:01:32 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pos_zzsc_credential_attach")
public class EquPosZzscCredentialAttachEntity_HI {
    private Integer attachmentId;
    private Integer supplierId;
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
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer fileId;
    private String description;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date invalidDate;
    private String fileName;
    private String filePath;
    private String deptmentCode;
    private String attachmentName;
    private String isProductFactory;
    private String fixFlag;
    private Integer supplierAddressId;
    private String fileType;
	private Integer qualificationId;
    private Integer operatorUserId;

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	@Id
	@SequenceGenerator(name = "equ_pos_zzsc_credent_attach_s", sequenceName = "equ_pos_zzsc_credent_attach_s", allocationSize = 1)
	@GeneratedValue(generator = "equ_pos_zzsc_credent_attach_s", strategy = GenerationType.SEQUENCE)
	@Column(name="attachment_id", nullable=false, length=22)	
	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=false, length=22)	
	public Integer getSupplierId() {
		return supplierId;
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

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_id", nullable=true, length=22)	
	public Integer getFileId() {
		return fileId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=100)	
	public String getDescription() {
		return description;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	@Column(name="invalid_date", nullable=true, length=7)	
	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_name", nullable=true, length=100)	
	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="file_path", nullable=true, length=240)	
	public String getFilePath() {
		return filePath;
	}

	public void setDeptmentCode(String deptmentCode) {
		this.deptmentCode = deptmentCode;
	}

	@Column(name="deptment_code", nullable=true, length=20)	
	public String getDeptmentCode() {
		return deptmentCode;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	@Column(name="attachment_name", nullable=true, length=100)	
	public String getAttachmentName() {
		return attachmentName;
	}

	public void setIsProductFactory(String isProductFactory) {
		this.isProductFactory = isProductFactory;
	}

	@Column(name="is_product_factory", nullable=true, length=1)	
	public String getIsProductFactory() {
		return isProductFactory;
	}

	public void setFixFlag(String fixFlag) {
		this.fixFlag = fixFlag;
	}

	@Column(name="fix_flag", nullable=true, length=1)	
	public String getFixFlag() {
		return fixFlag;
	}

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

	@Column(name="supplier_address_id", nullable=true, length=22)	
	public Integer getSupplierAddressId() {
		return supplierAddressId;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=true, length=20)	
	public String getFileType() {
		return fileType;
	}

	@Column(name="qualification_id", nullable=false, length=22)
	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
