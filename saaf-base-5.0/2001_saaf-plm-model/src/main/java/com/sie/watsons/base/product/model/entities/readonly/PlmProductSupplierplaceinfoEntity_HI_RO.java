package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductSupplierplaceinfoEntity_HI_RO Entity Object Thu Dec 05 17:07:01 CST
 * 2019 Auto Generate
 */

public class PlmProductSupplierplaceinfoEntity_HI_RO {
	public static final String QUERY = "select id,product_head_id as productHeadId,"
			+ " loc_type as locType,location,rms_id as rmsId,status,version_num as versionNum,"
			+ " creation_date as creationDate,created_by as createdBy,last_updated_by as lastUpdatedBy,"
			+ " last_update_login,supplier_code as supplierCode,product_name as productName,"
			+ " supplier_name as supplierName from plm_product_supplierplaceinfo where 1=1";
	private Integer id;
	private String productHeadId;
	private String locType;
	private String location;
	private String rmsId;
	private String status;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private String supplierCode;
	private String productName;
	private String supplierName;
	private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setProductHeadId(String productHeadId) {
		this.productHeadId = productHeadId;
	}

	public String getProductHeadId() {
		return productHeadId;
	}

	public void setLocType(String locType) {
		this.locType = locType;
	}

	public String getLocType() {
		return locType;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setRmsId(String rmsId) {
		this.rmsId = rmsId;
	}

	public String getRmsId() {
		return rmsId;
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

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierCode() {
		return supplierCode;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
