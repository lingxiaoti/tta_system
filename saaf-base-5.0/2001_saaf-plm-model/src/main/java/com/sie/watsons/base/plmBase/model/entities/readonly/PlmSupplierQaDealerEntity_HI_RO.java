package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmSupplierQaDealerEntity_HI_RO Entity Object Wed Oct 09 10:07:18 CST 2019
 * Auto Generate
 */

public class PlmSupplierQaDealerEntity_HI_RO {
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

	public static final String QUERY_SQL = "SELECT \n"
			+ "  psqd.PLM_SUPPLIER_QA_DEALER_ID as plmSupplierQaDealerId,\n"
			+ "       psqd.PLM_SUPPLIER_QA_NON_OB_INFO_ID as plmSupplierQaNonObInfoId,\n"
			+ "       psqd.PERSON_NAME as personName,\n"
			+ "       psqd.PERSON_TYPE as personType,\n"
			+ "       psqd.PERSON_TYPE_NAME as personTypeName,\n"
			+ "       psqd.PERSON_QA_TYPE as personQaType,\n"
			+ "       psqd.FILE_NAME as fileName,\n"
			+ "       psqd.UPLOAD_DATE as uploadDate,\n"
			+ "       psqd.FILE_ADDRESS as fileAddress,\n"
			+ "       psqd.FILE_TYPE as fileType,\n"
			+ "       psqd.CA_CODE_TYPE as caCodeType,\n"
			+ "       psqd.CA_CODE_TYPE_NAME as caCodeTypeName,\n"
			+ "       psqd.CA_CODE as caCode,\n"
			+ "       psqd.DATE_TYPE as dateType,\n"
			+ "       psqd.DATE_TYPE_NAME as dateTypeName,\n"
			+ "       psqd.CHOOSE_DATE as chooseDate,\n"
			+ "       psqd.CREATED_BY as createdBy,\n"
			+ "       psqd.LAST_UPDATED_BY as lastUpdatedBy,\n"
			+ "       psqd.LAST_UPDATE_DATE as lastUpdateDate,\n"
			+ "       psqd.LAST_UPDATE_LOGIN as lastUpdateLogin,\n"
			+ "       psqd.VERSION_NUM as versionNum,\n"
			+ "       psqd.CREATION_DATE as creationDate,\n"
			+ "       psqd.SPECIAL_PRODUCT_TYPE           as specialProductType,\n"
			+ "       psqd.SPECIAL_PRODUCT_TYPE_NAME      as specialProductTypeName \n"
			+ " FROM PLM_SUPPLIER_QA_DEALER psqd\n" + " WHERE 1=1 ";

	public String getSpecialProductType() {
		return specialProductType;
	}

	public void setSpecialProductType(String specialProductType) {
		this.specialProductType = specialProductType;
	}

	public String getSpecialProductTypeName() {
		return specialProductTypeName;
	}

	public void setSpecialProductTypeName(String specialProductTypeName) {
		this.specialProductTypeName = specialProductTypeName;
	}

	public void setPlmSupplierQaDealerId(Integer plmSupplierQaDealerId) {
		this.plmSupplierQaDealerId = plmSupplierQaDealerId;
	}

	public Integer getPlmSupplierQaDealerId() {
		return plmSupplierQaDealerId;
	}

	public void setPlmSupplierQaNonObInfoId(Integer plmSupplierQaNonObInfoId) {
		this.plmSupplierQaNonObInfoId = plmSupplierQaNonObInfoId;
	}

	public Integer getPlmSupplierQaNonObInfoId() {
		return plmSupplierQaNonObInfoId;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonTypeName(String personTypeName) {
		this.personTypeName = personTypeName;
	}

	public String getPersonTypeName() {
		return personTypeName;
	}

	public void setPersonQaType(String personQaType) {
		this.personQaType = personQaType;
	}

	public String getPersonQaType() {
		return personQaType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setCaCodeType(String caCodeType) {
		this.caCodeType = caCodeType;
	}

	public String getCaCodeType() {
		return caCodeType;
	}

	public void setCaCodeTypeName(String caCodeTypeName) {
		this.caCodeTypeName = caCodeTypeName;
	}

	public String getCaCodeTypeName() {
		return caCodeTypeName;
	}

	public void setCaCode(String caCode) {
		this.caCode = caCode;
	}

	public String getCaCode() {
		return caCode;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateTypeName(String dateTypeName) {
		this.dateTypeName = dateTypeName;
	}

	public String getDateTypeName() {
		return dateTypeName;
	}

	public void setChooseDate(Date chooseDate) {
		this.chooseDate = chooseDate;
	}

	public Date getChooseDate() {
		return chooseDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
