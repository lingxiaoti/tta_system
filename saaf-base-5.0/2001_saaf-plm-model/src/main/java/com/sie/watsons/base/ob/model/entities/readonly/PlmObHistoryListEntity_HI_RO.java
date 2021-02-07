package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmObHistoryListEntity_HI_RO Entity Object
 * Thu Nov 21 17:09:21 CST 2019  Auto Generate
 */

public class PlmObHistoryListEntity_HI_RO {
    private Integer plmObHistoryListId;
    private String barcode;
    private String productName;
    private String supplierName;
    private String supplierCode;
    private String fileName;
    private String fileAddress;
    private String fileType;
    private String fileStatus;
    private String fileStatusName;
    private String creator;
    private String mongoId;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private String errorRemarks;
    private Integer operatorUserId;
    private String fileCreator;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date fileCreationDate;

    public static final String QUERY_SQL = "SELECT pohl.PLM_OB_HISTORY_LIST_ID as plmObHistoryListId,\n" +
			"       pohl.BARCODE as barcode,\n" +
			"       pohl.PRODUCT_NAME as productName,\n" +
			"       pohl.SUPPLIER_NAME as supplierName,\n" +
			"       pohl.SUPPLIER_CODE as supplierCode,\n" +
			"       pohl.FILE_NAME as fileName,\n" +
			"       pohl.FILE_ADDRESS as fileAddress,\n" +
			"       pohl.FILE_TYPE as fileType,\n" +
			"       pohl.FILE_STATUS as fileStatus,\n" +
			"       pohl.FILE_STATUS_NAME as fileStatusName,\n" +
			"       pohl.CREATOR as creator,\n" +
			"       pohl.MONGO_ID as mongoId,\n" +
			"       pohl.CREATED_BY as createdBy,\n" +
			"       pohl.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       pohl.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       pohl.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       pohl.VERSION_NUM as versionNum,\n" +
			"       pohl.CREATION_DATE as creationDate,\n" +
			"       pohl.ERROR_REMARKS as errorRemarks,\n" +
			"		pohl.FILE_CREATOR as fileCreator,\n" +
			"		pohl.FILE_CREATION_DATE as fileCreationDate\n" +
			"FROM PLM_OB_HISTORY_LIST pohl\n" +
			"WHERE 1=1 ";

	public void setPlmObHistoryListId(Integer plmObHistoryListId) {
		this.plmObHistoryListId = plmObHistoryListId;
	}

	
	public Integer getPlmObHistoryListId() {
		return plmObHistoryListId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	
	public String getBarcode() {
		return barcode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileName() {
		return fileName;
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

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	
	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatusName(String fileStatusName) {
		this.fileStatusName = fileStatusName;
	}

	
	public String getFileStatusName() {
		return fileStatusName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	public String getCreator() {
		return creator;
	}

	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	
	public String getMongoId() {
		return mongoId;
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

	public void setErrorRemarks(String errorRemarks) {
		this.errorRemarks = errorRemarks;
	}

	
	public String getErrorRemarks() {
		return errorRemarks;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getFileCreator() {
		return fileCreator;
	}

	public void setFileCreator(String fileCreator) {
		this.fileCreator = fileCreator;
	}

	public Date getFileCreationDate() {
		return fileCreationDate;
	}

	public void setFileCreationDate(Date fileCreationDate) {
		this.fileCreationDate = fileCreationDate;
	}
}
