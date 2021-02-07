package com.sie.watsons.base.contract.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaContractDetailEntity_HI_RO Entity Object
 * Wed Jun 19 10:25:07 CST 2019  Auto Generate
 */

public class TtaContractDetailEntity_HI_RO {
    private Integer contractDId;
    private Integer contractLId;
    private Integer contractHId;
    private String vendorNbr;
    private String vendorName;
    private String deptCode;
    private String brandCn;
    private java.math.BigDecimal purchase;
    private java.math.BigDecimal sales;
    private String salesArea;
    private String deliveryGranary;
    private String oiType;
    private String termsName;
    private String collectionMethod;
    private String ttaValue;
    private String unit;
    private String terms;
    private java.math.BigDecimal feeIntas;
    private java.math.BigDecimal feeNotax;
    private String status;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String deptDesc;
    private String brand;
    private Integer operatorUserId;

	public static final String TTA_ITEM_V = "select * from tta_contract_detail_V V where 1=1";

	public void setContractDId(Integer contractDId) {
		this.contractDId = contractDId;
	}

	
	public Integer getContractDId() {
		return contractDId;
	}

	public void setContractLId(Integer contractLId) {
		this.contractLId = contractLId;
	}

	
	public Integer getContractLId() {
		return contractLId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	
	public Integer getContractHId() {
		return contractHId;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setPurchase(java.math.BigDecimal purchase) {
		this.purchase = purchase;
	}

	
	public java.math.BigDecimal getPurchase() {
		return purchase;
	}

	public void setSales(java.math.BigDecimal sales) {
		this.sales = sales;
	}

	
	public java.math.BigDecimal getSales() {
		return sales;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	
	public String getSalesArea() {
		return salesArea;
	}

	public void setDeliveryGranary(String deliveryGranary) {
		this.deliveryGranary = deliveryGranary;
	}

	
	public String getDeliveryGranary() {
		return deliveryGranary;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	
	public String getOiType() {
		return oiType;
	}

	public void setTermsName(String termsName) {
		this.termsName = termsName;
	}

	
	public String getTermsName() {
		return termsName;
	}

	public void setCollectionMethod(String collectionMethod) {
		this.collectionMethod = collectionMethod;
	}

	
	public String getCollectionMethod() {
		return collectionMethod;
	}

	public void setTtaValue(String ttaValue) {
		this.ttaValue = ttaValue;
	}

	
	public String getTtaValue() {
		return ttaValue;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public String getUnit() {
		return unit;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	
	public String getTerms() {
		return terms;
	}

	public void setFeeIntas(java.math.BigDecimal feeIntas) {
		this.feeIntas = feeIntas;
	}

	
	public java.math.BigDecimal getFeeIntas() {
		return feeIntas;
	}

	public void setFeeNotax(java.math.BigDecimal feeNotax) {
		this.feeNotax = feeNotax;
	}

	
	public java.math.BigDecimal getFeeNotax() {
		return feeNotax;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	
	public String getBrand() {
		return brand;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
