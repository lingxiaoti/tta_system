package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmSupplierQaDealerEntity_HI Entity Object Wed Oct 09 10:07:18 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_SUPPLIER_QA_DEALER")
public class PlmSupplierQaDealerEntity_HI {
	private Integer plmSupplierQaDealerId;
	private Integer plmSupplierQaNonObInfoId;
	private String personName;
	private String personType;
	private String personTypeName;
	private String personQaType;
	private String fileName;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date uploadDate;
	private String fileAddress;
	private String fileType;
	private String caCodeType;
	private String caCodeTypeName;
	private String caCode;
	private String dateType;
	private String dateTypeName;
	@JSONField(format = "yyyy-MM-dd")
	private Date chooseDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;
	private String specialProductType;
	private String specialProductTypeName;
	private String isSpa;

	@Column(name="is_Spa", nullable=true, length=200)
	public String getIsSpa() {return isSpa;}
	public void setIsSpa(String isSpa) {this.isSpa = isSpa;}

	public void setSpecialProductType(String specialProductType) {
		this.specialProductType = specialProductType;
	}

	@Column(name = "special_product_type", nullable = true, length = 20)
	public String getSpecialProductType() {
		return specialProductType;
	}

	public void setSpecialProductTypeName(String specialProductTypeName) {
		this.specialProductTypeName = specialProductTypeName;
	}

	@Column(name = "special_product_type_name", nullable = true, length = 50)
	public String getSpecialProductTypeName() {
		return specialProductTypeName;
	}

	public void setPlmSupplierQaDealerId(Integer plmSupplierQaDealerId) {
		this.plmSupplierQaDealerId = plmSupplierQaDealerId;
	}

	@Id
	@SequenceGenerator(name = "plmSupplierQaDealerEntity_HISeqGener", sequenceName = "SEQ_PLM_SUPPLIER_QA_DEALER", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "plmSupplierQaDealerEntity_HISeqGener", strategy = GenerationType.SEQUENCE)
	@Column(name = "plm_supplier_qa_dealer_id", nullable = false, length = 22)
	public Integer getPlmSupplierQaDealerId() {
		return plmSupplierQaDealerId;
	}

	public void setPlmSupplierQaNonObInfoId(Integer plmSupplierQaNonObInfoId) {
		this.plmSupplierQaNonObInfoId = plmSupplierQaNonObInfoId;
	}

	@Column(name = "plm_supplier_qa_non_ob_info_id", nullable = true, length = 22)
	public Integer getPlmSupplierQaNonObInfoId() {
		return plmSupplierQaNonObInfoId;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "person_name", nullable = true, length = 200)
	public String getPersonName() {
		return personName;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	@Column(name = "person_type", nullable = true, length = 20)
	public String getPersonType() {
		return personType;
	}

	public void setPersonTypeName(String personTypeName) {
		this.personTypeName = personTypeName;
	}

	@Column(name = "person_type_name", nullable = true, length = 200)
	public String getPersonTypeName() {
		return personTypeName;
	}

	public void setPersonQaType(String personQaType) {
		this.personQaType = personQaType;
	}

	@Column(name = "person_qa_type", nullable = true, length = 20)
	public String getPersonQaType() {
		return personQaType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_name", nullable = true, length = 50)
	public String getFileName() {
		return fileName;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "upload_date", nullable = true, length = 7)
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	@Column(name = "file_address", nullable = true, length = 500)
	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "file_type", nullable = true, length = 50)
	public String getFileType() {
		return fileType;
	}

	public void setCaCodeType(String caCodeType) {
		this.caCodeType = caCodeType;
	}

	@Column(name = "ca_code_type", nullable = true, length = 20)
	public String getCaCodeType() {
		return caCodeType;
	}

	public void setCaCodeTypeName(String caCodeTypeName) {
		this.caCodeTypeName = caCodeTypeName;
	}

	@Column(name = "ca_code_type_name", nullable = true, length = 50)
	public String getCaCodeTypeName() {
		return caCodeTypeName;
	}

	public void setCaCode(String caCode) {
		this.caCode = caCode;
	}

	@Column(name = "ca_code", nullable = true, length = 50)
	public String getCaCode() {
		return caCode;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	@Column(name = "date_type", nullable = true, length = 20)
	public String getDateType() {
		return dateType;
	}

	public void setDateTypeName(String dateTypeName) {
		this.dateTypeName = dateTypeName;
	}

	@Column(name = "date_type_name", nullable = true, length = 50)
	public String getDateTypeName() {
		return dateTypeName;
	}

	public void setChooseDate(Date chooseDate) {
		this.chooseDate = chooseDate;
	}

	@Column(name = "choose_date", nullable = true, length = 7)
	public Date getChooseDate() {
		return chooseDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
