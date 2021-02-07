package com.sie.watsons.base.ttaImport.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSupplierItemStoreEntity_HI_RO Entity Object
 * Thu Oct 24 11:09:13 CST 2019  Auto Generate
 */

public class TtaSupplierItemStoreEntity_HI_RO {

    public static final String  QUERY ="select *  from TTA_SUPPLIER_ITEM_STORE tbbl where 1=1 " ;

    private Integer mappinigId;
    @JSONField(format="yyyy-MM")
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

	
	public Integer getMappinigId() {
		return mappinigId;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	
	public Date getTranDate() {
		return tranDate;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	
	public String getStoreCode() {
		return storeCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	
	public String getVendorCode() {
		return vendorCode;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
