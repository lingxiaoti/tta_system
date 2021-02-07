package com.sie.watsons.base.exclusive.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSoleSupplierEntity_HI Entity Object
 * Tue Jun 25 10:44:36 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SOLE_SUPPLIER")
public class TtaSoleSupplierEntity_HI {
    private Integer soleSupplierId;//主键
    private Integer soleAgrtHId;//外键:独家协议主信息
    private String poposalCode;//poposal合同号
    private String supplierCode;//供应商编号
    private String supplierName;//供应商名称
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private Integer proposalVersion;
    private Integer proposalYear;
	private Integer poposalId;//poposalId
	private Integer contractHId;
	private String conditionVendor;

	public void setSoleSupplierId(Integer soleSupplierId) {
		this.soleSupplierId = soleSupplierId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SOLE_SUPPLIER", sequenceName="SEQ_TTA_SOLE_SUPPLIER", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SOLE_SUPPLIER",strategy=GenerationType.SEQUENCE)
	@Column(name="sole_supplier_id", nullable=false, length=22)	
	public Integer getSoleSupplierId() {
		return soleSupplierId;
	}

	public void setSoleAgrtHId(Integer soleAgrtHId) {
		this.soleAgrtHId = soleAgrtHId;
	}

	@Column(name="sole_agrt_h_id", nullable=true, length=22)	
	public Integer getSoleAgrtHId() {
		return soleAgrtHId;
	}

	public void setPoposalId(Integer poposalId) {
		this.poposalId = poposalId;
	}

	@Column(name="poposal_id", nullable=true, length=22)	
	public Integer getPoposalId() {
		return poposalId;
	}

	public void setPoposalCode(String poposalCode) {
		this.poposalCode = poposalCode;
	}

	@Column(name="poposal_code", nullable=true, length=50)	
	public String getPoposalCode() {
		return poposalCode;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="proposal_version", nullable=true, length=22)
	public Integer getProposalVersion() {
		return proposalVersion;
	}

	public void setProposalVersion(Integer proposalVersion) {
		this.proposalVersion = proposalVersion;
	}

	@Column(name="proposal_year", nullable=true, length=22)
	public Integer getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(Integer proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="contract_h_id", nullable=true, length=22)
	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	@Column(name="condition_vendor", nullable=true, length=100)
	public String getConditionVendor() {
		return conditionVendor;
	}

	public void setConditionVendor(String conditionVendor) {
		this.conditionVendor = conditionVendor;
	}
}
