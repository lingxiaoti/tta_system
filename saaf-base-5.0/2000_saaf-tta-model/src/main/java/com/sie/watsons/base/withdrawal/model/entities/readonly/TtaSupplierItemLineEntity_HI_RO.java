package com.sie.watsons.base.withdrawal.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSupplierItemLineEntity_HI_RO Entity Object
 * Sat Jul 20 17:29:31 CST 2019  Auto Generate
 */

public class TtaSupplierItemLineEntity_HI_RO {
	private Integer supplierItemLineId;
	private String supplierCode;
	private String supplierName;
	private String splitCondition;
	private String deptName;
	private String deptCode;
	private String brandName;
	private String brandCode;
	private String itemCode;
	private String itemName;
	private String originalSupplierCode;
	private String originalSupplierName;
	private Integer versionNum;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setSupplierItemLineId(Integer supplierItemLineId) {
		this.supplierItemLineId = supplierItemLineId;
	}


	public Integer getSupplierItemLineId() {
		return supplierItemLineId;
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

	public void setSplitCondition(String splitCondition) {
		this.splitCondition = splitCondition;
	}


	public String getSplitCondition() {
		return splitCondition;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getDeptName() {
		return deptName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}


	public String getDeptCode() {
		return deptCode;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getBrandName() {
		return brandName;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}


	public String getBrandCode() {
		return brandCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	public String getItemCode() {
		return itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getItemName() {
		return itemName;
	}

	public void setOriginalSupplierCode(String originalSupplierCode) {
		this.originalSupplierCode = originalSupplierCode;
	}


	public String getOriginalSupplierCode() {
		return originalSupplierCode;
	}

	public void setOriginalSupplierName(String originalSupplierName) {
		this.originalSupplierName = originalSupplierName;
	}


	public String getOriginalSupplierName() {
		return originalSupplierName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
