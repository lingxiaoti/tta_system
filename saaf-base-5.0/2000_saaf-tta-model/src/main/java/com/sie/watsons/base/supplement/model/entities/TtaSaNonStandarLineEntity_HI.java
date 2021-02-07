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
 * TtaSaNonStandarLineEntity_HI Entity Object
 * Mon Apr 20 16:01:06 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_SA_NON_STANDAR_LINE")
public class TtaSaNonStandarLineEntity_HI {
    private Integer saNonStandarLineId;
    private Integer proposalId;
	private Integer contractHId;
	private Integer saNonStandarHeaderId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String proposalOrder;
	private Integer proposalVersion;
	private Integer proposalYear;
	private String vendorCode;
	private String vendorName;

	public void setSaNonStandarLineId(Integer saNonStandarLineId) {
		this.saNonStandarLineId = saNonStandarLineId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SA_NON_STANDAR_LINE", sequenceName="SEQ_TTA_SA_NON_STANDAR_LINE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SA_NON_STANDAR_LINE",strategy=GenerationType.SEQUENCE)
	@Column(name="sa_non_standar_line_id", nullable=false, length=22)	
	public Integer getSaNonStandarLineId() {
		return saNonStandarLineId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)
	public Integer getProposalId() {
		return proposalId;
	}

	public void setSaNonStandarHeaderId(Integer saNonStandarHeaderId) {
		this.saNonStandarHeaderId = saNonStandarHeaderId;
	}

	@Column(name="sa_non_standar_header_id", nullable=false, length=22)	
	public Integer getSaNonStandarHeaderId() {
		return saNonStandarHeaderId;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="contract_h_id", nullable=true, length=22)
	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
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

	@Column(name = "vendor_name",nullable = true,length = 300)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
}
