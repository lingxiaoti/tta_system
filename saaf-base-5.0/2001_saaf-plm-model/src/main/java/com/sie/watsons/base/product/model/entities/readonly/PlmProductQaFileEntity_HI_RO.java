package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductQaFileEntity_HI_RO Entity Object Thu Aug 29 10:51:52 CST 2019 Auto
 * Generate
 */

public class PlmProductQaFileEntity_HI_RO {
    public static final String HIS_SQL = "select d.list_id as productHeadId," +
			" d.file_path as qaUrl " +
			" from dim_license_mag_list d " +
			" where 1=1 ";
    private Integer qaId;
	private Integer qaFileId;
	private String qaUrl;
	private String qaFileName;
	private String description;
	private Integer productHeadId;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String supplierId;

	private String qaFiletype;
	private String qaCodetype;
	private String qaCode;
	private String dateType;
	@JSONField(format = "yyyy-MM-dd")
	private Date datecurent;

	private String groupId; // 资质组id
	private String groupName; // 资质组名称
	private String returnReson; // 退回原因
	private String supplierName;
	private String isUpdate;

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public void setQaId(Integer qaId) {
		this.qaId = qaId;
	}

	public Integer getQaId() {
		return qaId;
	}

	public void setQaFileId(Integer qaFileId) {
		this.qaFileId = qaFileId;
	}

	public Integer getQaFileId() {
		return qaFileId;
	}

	public void setQaUrl(String qaUrl) {
		this.qaUrl = qaUrl;
	}

	public String getQaUrl() {
		return qaUrl;
	}

	public void setQaFileName(String qaFileName) {
		this.qaFileName = qaFileName;
	}

	public String getQaFileName() {
		return qaFileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	public Integer getProductHeadId() {
		return productHeadId;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getQaFiletype() {
		return qaFiletype;
	}

	public void setQaFiletype(String qaFiletype) {
		this.qaFiletype = qaFiletype;
	}

	public String getQaCodetype() {
		return qaCodetype;
	}

	public void setQaCodetype(String qaCodetype) {
		this.qaCodetype = qaCodetype;
	}

	public String getQaCode() {
		return qaCode;
	}

	public void setQaCode(String qaCode) {
		this.qaCode = qaCode;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Date getDatecurent() {
		return datecurent;
	}

	public void setDatecurent(Date datecurent) {
		this.datecurent = datecurent;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getReturnReson() {
		return returnReson;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

}
