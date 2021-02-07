package com.sie.watsons.base.exclusive.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSoleNonStandarLineEntity_HI_RO Entity Object
 * Mon Apr 20 15:57:41 CST 2020  Auto Generate
 */

public class TtaSoleNonStandarLineEntity_HI_RO {

	public static final String TTA_LIST = "                  SELECT tsnsl.sole_non_standar_line_id,\n" +
			"                         tsnsl.sole_non_standar_header_id,\n" +
			"                         tsnsl.proposal_id,\n" +
			"                         tsnsl.version_num,\n" +
			"                         tsnsl.creation_date,\n" +
			"                         tsnsl.created_by,\n" +
			"                         tsnsl.last_updated_by,\n" +
			"                         tsnsl.last_update_date,\n" +
			"                         tsnsl.last_update_login,\n" +
			"                         tsnsl.contract_h_id,\n" +
			"                         tsnsl.proposal_order,\n" +
			"                         tsnsl.proposal_version,\n" +
			"                         tsnsl.proposal_year,\n" +
			"                         tsnsl.vendor_code,\n" +
			"                         tsnsl.vendor_name\n" +
			"                         \n" +
			"                          FROM tta_sole_non_standar_line tsnsl where 1=1 ";
    private Integer soleNonStandarLineId;
    private Integer soleNonStandarHeaderId;
    private Integer proposalId;
	private String proposalCode;
	private Integer proposalVersion;
	private Integer proposalYear;
	private String supplierCode;
	private String supplierName;
	private Integer contractHId;
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

	public void setSoleNonStandarLineId(Integer soleNonStandarLineId) {
		this.soleNonStandarLineId = soleNonStandarLineId;
	}

	
	public Integer getSoleNonStandarLineId() {
		return soleNonStandarLineId;
	}

	public void setSoleNonStandarHeaderId(Integer soleNonStandarHeaderId) {
		this.soleNonStandarHeaderId = soleNonStandarHeaderId;
	}

	
	public Integer getSoleNonStandarHeaderId() {
		return soleNonStandarHeaderId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
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
