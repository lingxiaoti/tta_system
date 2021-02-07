package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmRmsSupplierInfoEntity_HI_RO Entity Object
 * Wed Oct 23 15:32:19 CST 2019  Auto Generate
 */

public class PlmRmsSupplierInfoEntity_HI_RO {
    private Integer plmRmsSupplierInfoId;
    private String supplierCode;
    private String supplierName;
    private String purchaseType;
    private String purchaseMode;
    private java.math.BigDecimal supplierTax;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    public static final String TTA_SUPPLIER = "select s.SUPPLIER_CODE as supplierCode," +
            "s.SUPPLIER_NAME as supplierName," +
            "s.PURCHASE_MODE as purchaseMode " +
            " from tta_supplier s where 1=1 ";

    public static final String QUERY_SQL = "SELECT\n" +
			"prsi.PLM_RMS_SUPPLIER_INFO_ID as plmRmsSupplierInfoId,\n" +
			"       prsi.SUPPLIER_CODE as supplierCode,\n" +
			"       prsi.SUPPLIER_NAME as supplierName,\n" +
			"       prsi.PURCHASE_TYPE as purchaseType,\n" +
			"       prsi.SUPPLIER_TAX as supplierTax,\n" +
			"       prsi.CREATED_BY as createdBy,\n" +
			"       prsi.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       prsi.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       prsi.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       prsi.VERSION_NUM as versionNum,\n" +
			"       prsi.CREATION_DATE as creationDate\n" +
			"FROM PLM_RMS_SUPPLIER_INFO prsi\n" +
			"WHERE 1=1 ";

	public void setPlmRmsSupplierInfoId(Integer plmRmsSupplierInfoId) {
		this.plmRmsSupplierInfoId = plmRmsSupplierInfoId;
	}

	
	public Integer getPlmRmsSupplierInfoId() {
		return plmRmsSupplierInfoId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setSupplierTax(java.math.BigDecimal supplierTax) {
		this.supplierTax = supplierTax;
	}

	
	public java.math.BigDecimal getSupplierTax() {
		return supplierTax;
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

    public String getPurchaseMode() {
        return purchaseMode;
    }

    public void setPurchaseMode(String purchaseMode) {
        this.purchaseMode = purchaseMode;
    }
}
