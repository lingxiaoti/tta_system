package com.sie.watsons.base.supplement.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSaNonStandarLineEntity_HI_RO Entity Object
 * Mon Apr 20 16:01:06 CST 2020  Auto Generate
 */

public class TtaSaNonStandarLineEntity_HI_RO {

	public static final String TTA_LIST = "          SELECT tssl.sa_non_standar_line_id,\n" +
			"                 tssl.proposal_id,\n" +
			"                 tssl.sa_non_standar_header_id,\n" +
			"                 tssl.version_num,\n" +
			"                 tssl.creation_date,\n" +
			"                 tssl.created_by,\n" +
			"                 tssl.last_updated_by,\n" +
			"                 tssl.last_update_date,\n" +
			"                 tssl.last_update_login,\n" +
			"                 tssl.contract_h_id,\n" +
			"                 tssl.proposal_order,\n" +
			"                 tssl.proposal_version,\n" +
			"                 tssl.proposal_year,\n" +
			"                 tssl.vendor_code,\n" +
			"                 tssl.vendor_name\n" +
			"                 \n" +
			"                  FROM  tta_sa_non_standar_line tssl where 1=1 ";

    private Integer saNonStandarLineId;
    private Integer proposalId;
	private String proposalCode;
	private Integer proposalVersion;
	private Integer proposalYear;
	private String supplierCode;
	private String supplierName;
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
	private String vendorCode;
	private String vendorName;

	public void setSaNonStandarLineId(Integer saNonStandarLineId) {
		this.saNonStandarLineId = saNonStandarLineId;
	}

	
	public Integer getSaNonStandarLineId() {
		return saNonStandarLineId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setSaNonStandarHeaderId(Integer saNonStandarHeaderId) {
		this.saNonStandarHeaderId = saNonStandarHeaderId;
	}

	
	public Integer getSaNonStandarHeaderId() {
		return saNonStandarHeaderId;
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

	public Integer getContractHId() {
		return contractHId;
	}

	public void setContractHId(Integer contractHId) {
		this.contractHId = contractHId;
	}

	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
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

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProposalOrder() {
		return proposalOrder;
	}

	public void setProposalOrder(String proposalOrder) {
		this.proposalOrder = proposalOrder;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
}
