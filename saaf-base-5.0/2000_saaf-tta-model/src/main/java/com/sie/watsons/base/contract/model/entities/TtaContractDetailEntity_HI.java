package com.sie.watsons.base.contract.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaContractDetailEntity_HI Entity Object
 * Wed Jun 19 10:25:07 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_contract_detail")
public class TtaContractDetailEntity_HI {
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

	public void setContractDId(Integer contractDId) {
		this.contractDId = contractDId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_CONTRACT_DETAIL", sequenceName = "SEQ_TTA_CONTRACT_DETAIL", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_CONTRACT_DETAIL", strategy = GenerationType.SEQUENCE)
	@Column(name="contract_d_id", nullable=false, length=22)	
	public Integer getContractDId() {
		return contractDId;
	}

	public void setContractLId(Integer contractLId) {
		this.contractLId = contractLId;
	}

	@Column(name="contract_l_id", nullable=true, length=22)	
	public Integer getContractLId() {
		return contractLId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	@Column(name="contract_h_id", nullable=true, length=22)	
	public Integer getContractHId() {
		return contractHId;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=true, length=50)	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=200)	
	public String getVendorName() {
		return vendorName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=260)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setPurchase(java.math.BigDecimal purchase) {
		this.purchase = purchase;
	}

	@Column(name="purchase", nullable=true, length=22)	
	public java.math.BigDecimal getPurchase() {
		return purchase;
	}

	public void setSales(java.math.BigDecimal sales) {
		this.sales = sales;
	}

	@Column(name="sales", nullable=true, length=22)	
	public java.math.BigDecimal getSales() {
		return sales;
	}

	public void setSalesArea(String salesArea) {
		this.salesArea = salesArea;
	}

	@Column(name="sales_area", nullable=true, length=200)	
	public String getSalesArea() {
		return salesArea;
	}

	public void setDeliveryGranary(String deliveryGranary) {
		this.deliveryGranary = deliveryGranary;
	}

	@Column(name="delivery_granary", nullable=true, length=200)	
	public String getDeliveryGranary() {
		return deliveryGranary;
	}

	public void setOiType(String oiType) {
		this.oiType = oiType;
	}

	@Column(name="oi_type", nullable=true, length=50)	
	public String getOiType() {
		return oiType;
	}

	public void setTermsName(String termsName) {
		this.termsName = termsName;
	}

	@Column(name="terms_name", nullable=true, length=100)	
	public String getTermsName() {
		return termsName;
	}

	public void setCollectionMethod(String collectionMethod) {
		this.collectionMethod = collectionMethod;
	}

	@Column(name="collection_method", nullable=true, length=100)	
	public String getCollectionMethod() {
		return collectionMethod;
	}

	public void setTtaValue(String ttaValue) {
		this.ttaValue = ttaValue;
	}

	@Column(name="tta_value", nullable=true, length=50)	
	public String getTtaValue() {
		return ttaValue;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=50)	
	public String getUnit() {
		return unit;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	@Column(name="terms", nullable=true, length=200)	
	public String getTerms() {
		return terms;
	}

	public void setFeeIntas(java.math.BigDecimal feeIntas) {
		this.feeIntas = feeIntas;
	}

	@Column(name="fee_intas", nullable=true, length=22)	
	public java.math.BigDecimal getFeeIntas() {
		return feeIntas;
	}

	public void setFeeNotax(java.math.BigDecimal feeNotax) {
		this.feeNotax = feeNotax;
	}

	@Column(name="fee_notax", nullable=true, length=22)	
	public java.math.BigDecimal getFeeNotax() {
		return feeNotax;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=2)	
	public String getStatus() {
		return status;
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

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=350)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column(name="brand", nullable=true, length=50)	
	public String getBrand() {
		return brand;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
