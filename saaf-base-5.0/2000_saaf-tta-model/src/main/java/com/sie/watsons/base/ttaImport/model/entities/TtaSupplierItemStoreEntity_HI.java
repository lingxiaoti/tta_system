package com.sie.watsons.base.ttaImport.model.entities;

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
 * TtaSupplierItemStoreEntity_HI Entity Object
 * Thu Oct 24 11:09:13 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SUPPLIER_ITEM_STORE")
public class TtaSupplierItemStoreEntity_HI {
    private Integer mappinigId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date tranDate;
    private String storeCode;
    private String itemCode;
    private String vendorCode;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setMappinigId(Integer mappinigId) {
		this.mappinigId = mappinigId;
	}
    @Id
    @SequenceGenerator(name = "SEQ_TTA_SUPPLIER_ITEM_STORE", sequenceName = "SEQ_TTA_SUPPLIER_ITEM_STORE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_SUPPLIER_ITEM_STORE", strategy = GenerationType.SEQUENCE)
	@Column(name="mappinig_id", nullable=true, length=22)	
	public Integer getMappinigId() {
		return mappinigId;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	@Column(name="tran_date", nullable=true, length=7)
	public Date getTranDate() {
		return tranDate;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@Column(name="store_code", nullable=true, length=50)	
	public String getStoreCode() {
		return storeCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=50)	
	public String getItemCode() {
		return itemCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=true, length=50)	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
