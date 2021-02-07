package com.sie.watsons.base.supplement.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSaStdProposalLineEntity_HI Entity Object
 * Tue Mar 31 15:25:14 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_SA_STD_PROPOSAL_LINE")
public class TtaSaStdProposalLineEntity_HI {
    private Integer saStdProposalLine;
    private Integer saStdHeaderId;
    private String proposalOrder;
    private Integer proposalVersion;
    private Integer proposalYear;
    private String vendorCode;
	private String vendorName;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private Integer proposalId;
    private Integer contractHId;


	public void setSaStdProposalLine(Integer saStdProposalLine) {
		this.saStdProposalLine = saStdProposalLine;
	}

	@Id
	@SequenceGenerator(name="SEQ_SA_STD_PROPOSAL_LINE", sequenceName="SEQ_SA_STD_PROPOSAL_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_SA_STD_PROPOSAL_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="sa_std_proposal_line", nullable=false, length=22)	
	public Integer getSaStdProposalLine() {
		return saStdProposalLine;
	}

	public void setSaStdHeaderId(Integer saStdHeaderId) {
		this.saStdHeaderId = saStdHeaderId;
	}

	@Column(name="sa_std_header_id", nullable=true, length=22)	
	public Integer getSaStdHeaderId() {
		return saStdHeaderId;
	}

	public void setProposalOrder(String proposalOrder) {
		this.proposalOrder = proposalOrder;
	}

	@Column(name="proposal_order", nullable=true, length=80)	
	public String getProposalOrder() {
		return proposalOrder;
	}

	public void setProposalVersion(Integer proposalVersion) {
		this.proposalVersion = proposalVersion;
	}

	@Column(name="proposal_version", nullable=true, length=22)	
	public Integer getProposalVersion() {
		return proposalVersion;
	}

	public void setProposalYear(Integer proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="proposal_year", nullable=true, length=22)	
	public Integer getProposalYear() {
		return proposalYear;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=true, length=80)	
	public String getVendorCode() {
		return vendorCode;
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

	@Column(name = "vendor_name",nullable = true,length = 300)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name = "proposal_id",nullable = true,length = 22)
	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name = "contract_h_id",nullable = true,length = 22)
	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
