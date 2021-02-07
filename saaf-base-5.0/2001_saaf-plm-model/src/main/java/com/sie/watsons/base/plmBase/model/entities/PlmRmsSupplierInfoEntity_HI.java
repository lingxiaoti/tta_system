package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmRmsSupplierInfoEntity_HI Entity Object
 * Wed Oct 23 15:32:19 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_RMS_SUPPLIER_INFO")
public class PlmRmsSupplierInfoEntity_HI {
    private Integer plmRmsSupplierInfoId;
    private String supplierCode;
    private String supplierName;
    private String purchaseType;
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

	public void setPlmRmsSupplierInfoId(Integer plmRmsSupplierInfoId) {
		this.plmRmsSupplierInfoId = plmRmsSupplierInfoId;
	}

	@Id	
	@SequenceGenerator(name="plmRmsSupplierInfoEntity_HISeqGener", sequenceName="SEQ_PLM_RMS_SUPPLIER_INFO", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmRmsSupplierInfoEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_rms_supplier_info_id", nullable=false, length=22)	
	public Integer getPlmRmsSupplierInfoId() {
		return plmRmsSupplierInfoId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=50)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=100)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Column(name="purchase_type", nullable=true, length=50)	
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setSupplierTax(java.math.BigDecimal supplierTax) {
		this.supplierTax = supplierTax;
	}

	@Column(name="supplier_tax", nullable=true, length=22)	
	public java.math.BigDecimal getSupplierTax() {
		return supplierTax;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
