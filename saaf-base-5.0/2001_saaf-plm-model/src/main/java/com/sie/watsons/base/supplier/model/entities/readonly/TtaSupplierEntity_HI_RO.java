package com.sie.watsons.base.supplier.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TtaSupplierEntity_HI_RO Entity Object
 * Wed May 22 12:51:33 CST 2019  Auto Generate
 */

public class TtaSupplierEntity_HI_RO {
    public static final String TTA_SUPPLIER = "select * from tta_supplier s where 1=1";
	public static final String TTA_SUPPLIER_V = "select * from tta_supplier_V V where 1=1";
	public static final String TTA_SUPPLIER_IS_V = "select * from tta_supplier_IS_V V where 1=1";
    private Integer supplierId;
    private String supplierCode;
    private String supplierName;
    private String status;
    private String isLatent;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String ownerDept;
    private String deptName;
    private String ownerGroup;
	private String ownerGroupName;
    private String contractOutput;
	private String isNew;
	private String isNewName;
	private String purchaseMode;
    private String proposalBrandGroup;
    private String formalCode;
    private String formalName;
    private Integer operatorUserId;
    private String statusName;
	private String lastUpdatedName;
	private String createdName;

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
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

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setIsLatent(String isLatent) {
		this.isLatent = isLatent;
	}

	
	public String getIsLatent() {
		return isLatent;
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

	public void setOwnerDept(String ownerDept) {
		this.ownerDept = ownerDept;
	}

	
	public String getOwnerDept() {
		return ownerDept;
	}

	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	
	public String getOwnerGroup() {
		return ownerGroup;
	}

	public void setContractOutput(String contractOutput) {
		this.contractOutput = contractOutput;
	}

	
	public String getContractOutput() {
		return contractOutput;
	}

	public void setPurchaseMode(String purchaseMode) {
		this.purchaseMode = purchaseMode;
	}

	
	public String getPurchaseMode() {
		return purchaseMode;
	}

    public String getFormalCode() {
        return formalCode;
    }

    public void setFormalCode(String formalCode) {
        this.formalCode = formalCode;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getProposalBrandGroup() {
		return proposalBrandGroup;
	}

	public void setProposalBrandGroup(String proposalBrandGroup) {
		this.proposalBrandGroup = proposalBrandGroup;
	}


	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOwnerGroupName() {
		return ownerGroupName;
	}

	public String getLastUpdatedName() {
		return lastUpdatedName;
	}

	public void setLastUpdatedName(String lastUpdatedName) {
		this.lastUpdatedName = lastUpdatedName;
	}

	public String getCreatedName() {
		return createdName;
	}

	public void setCreatedName(String createdName) {
		this.createdName = createdName;
	}

	public void setOwnerGroupName(String ownerGroupName) {
		this.ownerGroupName = ownerGroupName;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getIsNewName() {
		return isNewName;
	}

	public void setIsNewName(String isNewName) {
		this.isNewName = isNewName;
	}
}
