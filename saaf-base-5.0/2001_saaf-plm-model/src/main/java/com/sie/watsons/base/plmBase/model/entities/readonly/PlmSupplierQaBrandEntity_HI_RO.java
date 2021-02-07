package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmSupplierQaBrandEntity_HI_RO Entity Object Wed Oct 09 10:07:17 CST 2019
 * Auto Generate
 */

public class PlmSupplierQaBrandEntity_HI_RO {
	private Integer plmSupplierQaBrandId;
	private Integer plmSupplierQaNonObInfoId;
	private String brandCnName;
	private String brandEnName;
	private Integer plmBrandId;
	private String brandQaType;
	private String brandQaTypeName;
	private String fileName;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date uploadDate;
	private String fileAddress;
	private String fileType;
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
	private String dateType;
	private String dateTypeName;
	@JSONField(format = "yyyy-MM-dd")
	private Date chooseDate;

	public Date getChooseDate() {
		return chooseDate;
	}

	public void setChooseDate(Date chooseDate) {
		this.chooseDate = chooseDate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getDateTypeName() {
		return dateTypeName;
	}

	public void setDateTypeName(String dateTypeName) {
		this.dateTypeName = dateTypeName;
	}

	public static final String QUERY_SQL = "SELECT psqb.PLM_SUPPLIER_QA_BRAND_ID       as plmSupplierQaBrandId,\n"
			+ "       psqb.PLM_SUPPLIER_QA_NON_OB_INFO_ID as plmSupplierQaNonObInfoId,\n"
			+ "       psqb.BRAND_CN_NAME                  as brandCnName,\n"
			+ "       psqb.BRAND_EN_NAME                  as brandEnName,\n"
			+ "       psqb.PLM_BRAND_ID                   as plmBrandId,\n"
			+ "       psqb.BRAND_QA_TYPE                  as brandQaType,\n"
			+ "       psqb.BRAND_QA_TYPE_NAME             as brandQaTypeName,\n"
			+ "       psqb.FILE_NAME                      as fileName,\n"
			+ "       psqb.UPLOAD_DATE                    as uploadDate,\n"
			+ "       psqb.FILE_ADDRESS                   as fileAddress,\n"
			+ "       psqb.FILE_TYPE                      as fileType,\n"
			+ "       psqb.CREATED_BY                     as createdBy,\n"
			+ "       psqb.LAST_UPDATED_BY                as lastUpdatedBy,\n"
			+ "       psqb.LAST_UPDATE_DATE               as lastUpdateDate,\n"
			+ "       psqb.LAST_UPDATE_LOGIN              as lastUpdateLogin,\n"
			+ "       psqb.VERSION_NUM                    as versionNum,\n"
			+ "       psqb.CREATION_DATE                  as creationDate,\n"
			+ "       psqb.SPECIAL_PRODUCT_TYPE           as specialProductType,\n"
			+ "       psqb.SPECIAL_PRODUCT_TYPE_NAME      as specialProductTypeName,\n"
			+ "       psqb.DATE_TYPE                      as dateType,\n"
			+ "       psqb.DATE_TYPE_NAME                 as dateTypeName,\n"
			+ "       psqb.CHOOSE_DATE                    as chooseDate\n"
			+ " FROM PLM_SUPPLIER_QA_BRAND psqb\n" + " WHERE 1 = 1 ";

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

	public void setPlmSupplierQaBrandId(Integer plmSupplierQaBrandId) {
		this.plmSupplierQaBrandId = plmSupplierQaBrandId;
	}

	public Integer getPlmSupplierQaBrandId() {
		return plmSupplierQaBrandId;
	}

	public void setPlmSupplierQaNonObInfoId(Integer plmSupplierQaNonObInfoId) {
		this.plmSupplierQaNonObInfoId = plmSupplierQaNonObInfoId;
	}

	public Integer getPlmSupplierQaNonObInfoId() {
		return plmSupplierQaNonObInfoId;
	}

	public void setBrandCnName(String brandCnName) {
		this.brandCnName = brandCnName;
	}

	public String getBrandCnName() {
		return brandCnName;
	}

	public void setBrandEnName(String brandEnName) {
		this.brandEnName = brandEnName;
	}

	public String getBrandEnName() {
		return brandEnName;
	}

	public void setPlmBrandId(Integer plmBrandId) {
		this.plmBrandId = plmBrandId;
	}

	public Integer getPlmBrandId() {
		return plmBrandId;
	}

	public void setBrandQaType(String brandQaType) {
		this.brandQaType = brandQaType;
	}

	public String getBrandQaType() {
		return brandQaType;
	}

	public void setBrandQaTypeName(String brandQaTypeName) {
		this.brandQaTypeName = brandQaTypeName;
	}

	public String getBrandQaTypeName() {
		return brandQaTypeName;
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
