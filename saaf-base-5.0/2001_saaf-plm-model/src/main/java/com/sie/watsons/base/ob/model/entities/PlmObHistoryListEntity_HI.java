package com.sie.watsons.base.ob.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmObHistoryListEntity_HI Entity Object
 * Thu Nov 21 17:09:21 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_OB_HISTORY_LIST")
public class PlmObHistoryListEntity_HI {
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
    private String fileCreator;
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
//	@JSONField(format="yyyy-MM-dd HH:mm:ss")
//	private Date fileCreationDate;

	public void setPlmObHistoryListId(Integer plmObHistoryListId) {
		this.plmObHistoryListId = plmObHistoryListId;
	}

	@Id	
	@SequenceGenerator(name="plmObHistoryListEntity_HISeqGener", sequenceName="SEQ_PLM_OB_HISTORY_LIST", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmObHistoryListEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_ob_history_list_id", nullable=false, length=22)	
	public Integer getPlmObHistoryListId() {
		return plmObHistoryListId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Column(name="barcode", nullable=true, length=100)	
	public String getBarcode() {
		return barcode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="product_name", nullable=true, length=200)	
	public String getProductName() {
		return productName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=200)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=100)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_name", nullable=true, length=200)	
	public String getFileName() {
		return fileName;
	}

	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}

	@Column(name="file_address", nullable=true, length=200)	
	public String getFileAddress() {
		return fileAddress;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=true, length=100)	
	public String getFileType() {
		return fileType;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	@Column(name="file_status", nullable=true, length=100)	
	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatusName(String fileStatusName) {
		this.fileStatusName = fileStatusName;
	}

	@Column(name="file_status_name", nullable=true, length=100)	
	public String getFileStatusName() {
		return fileStatusName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name="creator", nullable=true, length=100)	
	public String getCreator() {
		return creator;
	}

	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	@Column(name="mongo_id", nullable=true, length=100)	
	public String getMongoId() {
		return mongoId;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
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

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setErrorRemarks(String errorRemarks) {
		this.errorRemarks = errorRemarks;
	}

	@Column(name="error_remarks", nullable=true, length=200)	
	public String getErrorRemarks() {
		return errorRemarks;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="file_creator", nullable=true, length=100)
	public String getFileCreator() {
		return fileCreator;
	}

	public void setFileCreator(String fileCreator) {
		this.fileCreator = fileCreator;
	}

//	@Column(name="file_creation_date", nullable=true, length=7)
//	public Date getFileCreationDate() {
//		return fileCreationDate;
//	}
//
//	public void setFileCreationDate(Date fileCreationDate) {
//		this.fileCreationDate = fileCreationDate;
//	}
}
