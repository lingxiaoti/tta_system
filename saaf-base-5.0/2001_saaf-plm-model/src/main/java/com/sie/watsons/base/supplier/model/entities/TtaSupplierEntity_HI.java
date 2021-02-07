package com.sie.watsons.base.supplier.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaSupplierEntity_HI Entity Object
 * Wed May 22 12:51:33 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_supplier")
public class TtaSupplierEntity_HI {
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
    private String ownerGroup;
    private String contractOutput;
    private String purchaseMode;
    private String proposalBrandGroup;
    private String formalCode;
    private String formalName;
    private Integer operatorUserId;
	private String deptName;
	private String ownerGroupName;

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_SUPPLIER", sequenceName = "SEQ_TTA_SUPPLIER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_SUPPLIER", strategy = GenerationType.SEQUENCE)
	@Column(name="supplier_id", nullable=false, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=false, length=50)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=false, length=230)
	public String getSupplierName() {
		return supplierName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=1)	
	public String getStatus() {
		return status;
	}

	public void setIsLatent(String isLatent) {
		this.isLatent = isLatent;
	}

	@Column(name="is_latent", nullable=false, length=1)	
	public String getIsLatent() {
		return isLatent;
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

	public void setOwnerDept(String ownerDept) {
		this.ownerDept = ownerDept;
	}

	@Column(name="owner_dept", nullable=true, length=20)	
	public String getOwnerDept() {
		return ownerDept;
	}

	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}

	@Column(name="owner_group", nullable=true, length=20)	
	public String getOwnerGroup() {
		return ownerGroup;
	}

	public void setContractOutput(String contractOutput) {
		this.contractOutput = contractOutput;
	}

	@Column(name="contract_output", nullable=true, length=20)	
	public String getContractOutput() {
		return contractOutput;
	}

	public void setPurchaseMode(String purchaseMode) {
		this.purchaseMode = purchaseMode;
	}

	@Column(name="purchase_mode", nullable=true, length=20)	
	public String getPurchaseMode() {
		return purchaseMode;
	}

	@Column(name="proposal_brand_group", nullable=true, length=20)
	public String getProposalBrandGroup() {
		return proposalBrandGroup;
	}

	public void setProposalBrandGroup(String proposalBrandGroup) {
		this.proposalBrandGroup = proposalBrandGroup;
	}

	@Column(name="formal_code", nullable=true, length=30)
	public String getFormalCode() {
		return formalCode;
	}

	public void setFormalCode(String formalCode) {
		this.formalCode = formalCode;
	}

	@Column(name="formal_name", nullable=true, length=230)
	public String getFormalName() {
		return formalName;
	}

	public void setFormalName(String formalName) {
		this.formalName = formalName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="dept_name", nullable=true, length=200)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="owner_group_name", nullable=true, length=230)
	public String getOwnerGroupName() {
		return ownerGroupName;
	}

	public void setOwnerGroupName(String ownerGroupName) {
		this.ownerGroupName = ownerGroupName;
	}
}
