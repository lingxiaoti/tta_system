package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmDevelopmentBatchInfoEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:09 CST 2019  Auto Generate
 */

public class PlmDevelopmentBatchInfoEntity_HI_RO {
    private Integer plmDevelopmentBatchInfoId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String fileAlterType;
    private String fileType;
    private String fileName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date uploadDate;
    private String productionBatch;
    private Integer productionCount;
    private String remarks;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    public static final String QUERY_SQL = "SELECT\n" +
            "		pdbi.PLM_DEVELOPMENT_BATCH_INFO_ID as plmDevelopmentBatchInfoId,\n" +
            "       pdbi.PLM_DEVELOPMENT_INFO_ID as plmDevelopmentInfoId,\n" +
            "       pdbi.PLM_PROJECT_ID as plmProjectId,\n" +
            "       pdbi.FILE_ALTER_TYPE as fileAlterType,\n" +
            "       pdbi.FILE_TYPE as fileType,\n" +
            "       pdbi.FILE_NAME as fileName,\n" +
            "       pdbi.FILE_ADDRESS as fileAddress,\n" +
            "       pdbi.UPLOAD_DATE as uploadDate,\n" +
            "       pdbi.PRODUCTION_BATCH as productionBatch,\n" +
            "       pdbi.PRODUCTION_COUNT as productionCount,\n" +
            "       pdbi.REMARKS as remarks,\n" +
            "       pdbi.CREATED_BY as createdBy,\n" +
            "       pdbi.LAST_UPDATED_BY as lastUpdatedBy,\n" +
            "       pdbi.LAST_UPDATE_DATE as lastUpdateDate,\n" +
            "       pdbi.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
            "       pdbi.VERSION_NUM as versionNum,\n" +
            "       pdbi.CREATION_DATE as creationDate\n" +
            "FROM PLM_DEVELOPMENT_BATCH_INFO pdbi\n" +
            "WHERE 1=1 ";

    private String fileAddress;
    private String productNo;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    private String productName;
    private String productNameCn;
    private String productNameEn;
    private String barcode;
    private String supplierName;
    private String obId;
    private Integer supplierId;

    public static final String REPORT_SQL = "SELECT pdi.PRODUCT_NO        as productNo,\n" +
            "       pdi.START_DATE        as startDate,\n" +
            "       pdi.OB_ID             as obId,\n" +
            "       pdi.PLM_DEVELOPMENT_INFO_ID as plmDevelopmentInfoId,\n" +
            "       pdi.PRODUCT_NAME      as productName,\n" +
            "       pdi.PRODUCT_NAME_CN   as productNameCn,\n" +
            "       pdi.PRODUCT_NAME_EN   as productNameEn,\n" +
            "       pdi.BARCODE           as barcode,\n" +
            "       pdi.SUPPLIER_NAME     as supplierName,\n" +
            "       pdi.SUPPLIER_ID       as supplierId,\n" +
            "       pdbi.last_update_date as lastUpdateDate,\n" +
            "       pdbi.PRODUCTION_COUNT as productionCount,\n" +
            "       pdbi.PRODUCTION_BATCH as productionBatch\n" +
            "FROM PLM_DEVELOPMENT_BATCH_INFO pdbi\n" +
            "       LEFT JOIN PLM_DEVELOPMENT_INFO pdi ON pdbi.PLM_DEVELOPMENT_INFO_ID = pdi.PLM_DEVELOPMENT_INFO_ID\n" +
            "WHERE pdbi.PRODUCTION_COUNT is not null ";

    public String getFileAddress() {
		return fileAddress;
	}


	public void setFileAddress(String fileAddress) {
		this.fileAddress = fileAddress;
	}


	public void setPlmDevelopmentBatchInfoId(Integer plmDevelopmentBatchInfoId) {
        this.plmDevelopmentBatchInfoId = plmDevelopmentBatchInfoId;
    }


    public Integer getPlmDevelopmentBatchInfoId() {
        return plmDevelopmentBatchInfoId;
    }

    public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
        this.plmDevelopmentInfoId = plmDevelopmentInfoId;
    }


    public Integer getPlmDevelopmentInfoId() {
        return plmDevelopmentInfoId;
    }

    public void setPlmProjectId(Integer plmProjectId) {
        this.plmProjectId = plmProjectId;
    }


    public Integer getPlmProjectId() {
        return plmProjectId;
    }

    public void setFileAlterType(String fileAlterType) {
        this.fileAlterType = fileAlterType;
    }


    public String getFileAlterType() {
        return fileAlterType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public String getFileType() {
        return fileType;
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

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }


    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionCount(Integer productionCount) {
        this.productionCount = productionCount;
    }


    public Integer getProductionCount() {
        return productionCount;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getRemarks() {
        return remarks;
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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNameCn() {
        return productNameCn;
    }

    public void setProductNameCn(String productNameCn) {
        this.productNameCn = productNameCn;
    }

    public String getProductNameEn() {
        return productNameEn;
    }

    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getObId() {
        return obId;
    }

    public void setObId(String obId) {
        this.obId = obId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
}
