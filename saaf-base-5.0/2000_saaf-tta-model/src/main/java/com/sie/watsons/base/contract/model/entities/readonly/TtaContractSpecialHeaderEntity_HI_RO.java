package com.sie.watsons.base.contract.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaContractSpecialHeaderEntity_HI_RO Entity Object
 * Fri Jun 19 10:43:52 CST 2020  Auto Generate
 */

public class TtaContractSpecialHeaderEntity_HI_RO {

	public static final String TTA_ITEM_V = "               SELECT tcs.tta_contract_special_header_id,\n" +
			"                      tcs.creation_date,\n" +
			"                      tcs.created_by,\n" +
			"                      tcs.last_updated_by,\n" +
			"                      tcs.last_update_date,\n" +
			"                      tcs.last_update_login,\n" +
			"                      tcs.version_num,\n" +
			"                      tcs.order_no,\n" +
			"                      tcs.year,\n" +
			"                      tcs.vendor_nbr,\n" +
			"                      tcs.vendor_name,\n" +
			"                      tcs.brand_cn,\n" +
			"                      tcs.brand_en,\n" +
			"                      tcs.org_code,\n" +
			"                      tcs.dept_code,\n" +
			"                      tcs.org_name,\n" +
			"                      tcs.dept_name,\n" +
			"                      tcs.submit_by,\n" +
			"                      bu2.user_full_name                  submit_by_name,\n" +
			"                      tcs.status,\n" +
			"                      bu.user_full_name                  created_by_name,\n" +
			"                      look_status.meaning                status_name,\n" +
			"                      tcs.payment_date,\n" +
			"                      tcs.payment_date_reason,\n" +
			"                      tcs.return_clause_reason,\n" +
			"                      tcs.advance_payment_reason,\n" +
			"                      tcs.others_reason\n" +
			"                 FROM TTA_CONTRACT_SPECIAL_HEADER tcs\n" +
			"                 left join (select blv.lookup_code, blv.meaning\n" +
			"                              from base_lookup_values blv\n" +
			"                             where blv.lookup_type = 'TTA_CSH_STATUS') look_status\n" +
			"                   on look_status.lookup_code = tcs.status\n" +
			"                 left join base_users bu\n" +
			"                   on tcs.created_by = bu.user_id\n" +
			"                 left join base_users bu2\n" +
			"                   on tcs.submit_by = bu2.user_id\n" +
			"                where 1 = 1  ";

    private Integer ttaContractSpecialHeaderId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
	private String createdByName;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String orderNo;
    private Integer year;
    private String vendorNbr;
    private String vendorName;
    private String brandCn;
    private String brandEn;
    private String orgCode;
    private String deptCode;
    private String orgName;
    private String deptName;
    private Integer submitBy;
	private String submitByName;
    private String status;
	private String statusName;
    private Integer paymentDate;
    private String paymentDateReason;
    private String returnClauseReason;
    private String advancePaymentReason;
    private String othersReason;
    private Integer operatorUserId;

	public void setTtaContractSpecialHeaderId(Integer ttaContractSpecialHeaderId) {
		this.ttaContractSpecialHeaderId = ttaContractSpecialHeaderId;
	}

	
	public Integer getTtaContractSpecialHeaderId() {
		return ttaContractSpecialHeaderId;
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

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	
	public String getOrderNo() {
		return orderNo;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	
	public Integer getYear() {
		return year;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	
	public String getBrandEn() {
		return brandEn;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	
	public String getOrgCode() {
		return orgCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
	public String getOrgName() {
		return orgName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setSubmitBy(Integer submitBy) {
		this.submitBy = submitBy;
	}

	
	public Integer getSubmitBy() {
		return submitBy;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setPaymentDate(Integer paymentDate) {
		this.paymentDate = paymentDate;
	}

	
	public Integer getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDateReason(String paymentDateReason) {
		this.paymentDateReason = paymentDateReason;
	}

	
	public String getPaymentDateReason() {
		return paymentDateReason;
	}

	public void setReturnClauseReason(String returnClauseReason) {
		this.returnClauseReason = returnClauseReason;
	}

	
	public String getReturnClauseReason() {
		return returnClauseReason;
	}

	public void setAdvancePaymentReason(String advancePaymentReason) {
		this.advancePaymentReason = advancePaymentReason;
	}

	
	public String getAdvancePaymentReason() {
		return advancePaymentReason;
	}

	public void setOthersReason(String othersReason) {
		this.othersReason = othersReason;
	}

	
	public String getOthersReason() {
		return othersReason;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getSubmitByName() {
		return submitByName;
	}

	public void setSubmitByName(String submitByName) {
		this.submitByName = submitByName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
