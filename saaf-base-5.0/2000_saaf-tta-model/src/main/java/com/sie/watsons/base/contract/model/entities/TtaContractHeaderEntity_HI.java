package com.sie.watsons.base.contract.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaContractHeaderEntity_HI Entity Object
 * Wed Jun 19 10:25:06 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_contract_header")
public class TtaContractHeaderEntity_HI {
    private Integer contractHId;
    private String contractCode;
    private String vendorNbr;
    private String vendorName;
    private String billStatus;
    private String proposalCode;
    private Integer proposalYear;
    private Integer proposalVersion;
    private String isSplit;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date confirmDate;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date submitDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	private String isSpecial;//是否特批
	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_CONTRACT_HEADER", sequenceName = "SEQ_TTA_CONTRACT_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_CONTRACT_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="contract_h_id", nullable=false, length=22)	
	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	@Column(name="contract_code", nullable=true, length=50)	
	public String getContractCode() {
		return contractCode;
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

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name="bill_status", nullable=true, length=10)	
	public String getBillStatus() {
		return billStatus;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	@Column(name="proposal_code", nullable=true, length=200)	
	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalYear(Integer proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="proposal_year", nullable=true, length=22)	
	public Integer getProposalYear() {
		return proposalYear;
	}

	public void setProposalVersion(Integer proposalVersion) {
		this.proposalVersion = proposalVersion;
	}

	@Column(name="proposal_version", nullable=true, length=22)	
	public Integer getProposalVersion() {
		return proposalVersion;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	@Column(name="is_split", nullable=true, length=2)	
	public String getIsSplit() {
		return isSplit;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name="confirm_date", nullable=true, length=7)	
	public Date getConfirmDate() {
		return confirmDate;
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

	@Column(name="submit_date")
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
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

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	@Column(name="is_special")
	public String getIsSpecial() {
		return isSpecial;
	}
}
