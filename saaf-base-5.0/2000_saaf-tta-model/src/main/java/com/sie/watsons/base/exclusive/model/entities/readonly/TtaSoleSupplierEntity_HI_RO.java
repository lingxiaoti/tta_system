package com.sie.watsons.base.exclusive.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSoleSupplierEntity_HI_RO Entity Object
 * Tue Jun 25 10:44:36 CST 2019  Auto Generate
 */

public class TtaSoleSupplierEntity_HI_RO {
	public static final String QUERY_CONTRACT__SUPPLIER = "select tch.proposal_code as poposalCode,\n" +
			"       tcl.contract_l_id as contractLId,\n" +
			"       tcl.vendor_code as supplierCode,\n" +
			"       tcl.vendor_name as supplierName,\n" +
			"       tcl.proposal_id as poposalId,\n" +
			"       tcl.proposal_year as poposalYear\n" +
			"  from tta_contract_header tch\n" +
			"  left join TTA_CONTRACT_LINE tcl\n" +
			"    on tch.CONTRACT_H_ID = tcl.contract_h_id\n" +
			"  left join tta_proposal_header tph\n" +
			"  on tcl.proposal_id = tph.proposal_id\n" +
			" where  tph.status in ('B','C') and  tcl.status = '1' ";

	public static final String QUEREY_PROPOSAL_CONTRACT_VENDOR = "select * from tta_sole_supplier tss where 1=1 ";

    private Integer soleSupplierId;//主键
    private Integer soleAgrtHId;//外键
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
	private Integer poposalId;
	private Integer contractHId;
	private String conditionVendor;

	public void setSoleSupplierId(Integer soleSupplierId) {
		this.soleSupplierId = soleSupplierId;
	}

	
	public Integer getSoleSupplierId() {
		return soleSupplierId;
	}

	public void setSoleAgrtHId(Integer soleAgrtHId) {
		this.soleAgrtHId = soleAgrtHId;
	}

	
	public Integer getSoleAgrtHId() {
		return soleAgrtHId;
	}

	public void setPoposalId(Integer poposalId) {
		this.poposalId = poposalId;
	}

	
	public Integer getPoposalId() {
		return poposalId;
	}

	public void setPoposalCode(String poposalCode) {
		this.poposalCode = poposalCode;
	}

	
	public String getPoposalCode() {
		return poposalCode;
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

	public Integer getProposalVersion() {
		return proposalVersion;
	}

	public void setProposalVersion(Integer proposalVersion) {
		this.proposalVersion = proposalVersion;
	}

	public Integer getProposalYear() {
		return proposalYear;
	}

	public void setProposalYear(Integer proposalYear) {
		this.proposalYear = proposalYear;
	}

	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	public String getConditionVendor() {
		return conditionVendor;
	}

	public void setConditionVendor(String conditionVendor) {
		this.conditionVendor = conditionVendor;
	}
}
