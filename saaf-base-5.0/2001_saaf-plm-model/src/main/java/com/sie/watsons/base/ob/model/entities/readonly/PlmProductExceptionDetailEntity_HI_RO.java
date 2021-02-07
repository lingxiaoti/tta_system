package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmProductExceptionDetailEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:10 CST 2019  Auto Generate
 */

public class PlmProductExceptionDetailEntity_HI_RO {
    private Integer complaintSamplesNumber;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer plmProductExcepDetailId;
    private Integer plmProductExceptionInfoId;
    private String barcode;
    private String projectRange;
    private Integer plmDevelopmentInfoId;
    private String projectName;
    private String productName;
    private String productNameCn;
    private String productNameEn;
    private String productCategory;
    private String supplierName;
    private String producer;
    private String batchNumber;
    private Integer operatorUserId;

    private String productNo;

    public static final String QUERY_SQL = " SELECT\n" +
			"       pred.PLM_PRODUCT_EXCEP_DETAIL_ID as plmProductExcepDetailId,\n" +
			"       pred.PLM_PRODUCT_EXCEPTION_INFO_ID as plmProductExceptionInfoId,\n" +
			"       pred.BARCODE as barcode,\n" +
			"       pred.PRODUCT_NO as productNo,\n" +
			"       pred.PROJECT_RANGE   as projectRange,\n" +
			"       pred.PLM_DEVELOPMENT_INFO_ID       as plmDevelopmentInfoId,\n" +
			"       pred.PROJECT_NAME    as projectName,\n" +
			"       pred.PRODUCT_NAME as productName,\n" +
			"       pred.PRODUCT_NAME_EN as productNameEn,\n" +
			"       pred.PRODUCT_NAME_CN as productNameCn,\n" +
			"       pred.PRODUCT_CATEGORY       as productCategory,\n" +
			"       pred.SUPPLIER_NAME   as supplierName,\n" +
			"       pred.PRODUCER as producer,\n" +
			"       pred.BATCH_NUMBER    as batchNumber,\n" +
			"       pred.COMPLAINT_SAMPLES_NUMBER      as complaintSamplesNumber,\n" +
			"       pred.CREATED_BY      as createdBy,\n" +
			"       pred.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       pred.LAST_UPDATE_DATE       as lastUpdateDate,\n" +
			"       pred.LAST_UPDATE_LOGIN      as lastUpdateLogin,\n" +
			"       pred.VERSION_NUM     as versionNum,\n" +
			"       pred.CREATION_DATE   as creationDate\n" +
			"FROM PLM_PRODUCT_EXCEPTION_DETAIL pred\n" +
			"WHERE 1=1 ";

	public void setComplaintSamplesNumber(Integer complaintSamplesNumber) {
		this.complaintSamplesNumber = complaintSamplesNumber;
	}

	
	public Integer getComplaintSamplesNumber() {
		return complaintSamplesNumber;
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

	public void setPlmProductExcepDetailId(Integer plmProductExcepDetailId) {
		this.plmProductExcepDetailId = plmProductExcepDetailId;
	}

	
	public Integer getPlmProductExcepDetailId() {
		return plmProductExcepDetailId;
	}

	public void setPlmProductExceptionInfoId(Integer plmProductExceptionInfoId) {
		this.plmProductExceptionInfoId = plmProductExceptionInfoId;
	}

	
	public Integer getPlmProductExceptionInfoId() {
		return plmProductExceptionInfoId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	
	public String getBarcode() {
		return barcode;
	}

	public void setProjectRange(String projectRange) {
		this.projectRange = projectRange;
	}

	
	public String getProjectRange() {
		return projectRange;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	public String getProjectName() {
		return projectName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setProductNameCn(String productNameCn) {
		this.productNameCn = productNameCn;
	}

	
	public String getProductNameCn() {
		return productNameCn;
	}

	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}

	
	public String getProductNameEn() {
		return productNameEn;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	
	public String getProductCategory() {
		return productCategory;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	
	public String getProducer() {
		return producer;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	
	public String getBatchNumber() {
		return batchNumber;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
}
