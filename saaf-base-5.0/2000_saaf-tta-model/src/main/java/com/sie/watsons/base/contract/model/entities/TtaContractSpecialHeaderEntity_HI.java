package com.sie.watsons.base.contract.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaContractSpecialHeaderEntity_HI Entity Object
 * Fri Jun 19 10:43:52 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_CONTRACT_SPECIAL_HEADER")
public class TtaContractSpecialHeaderEntity_HI {
    private Integer ttaContractSpecialHeaderId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
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
    private String status;
    private Integer paymentDate;
    private String paymentDateReason;
    private String returnClauseReason;
    private String advancePaymentReason;
    private String othersReason;
    private Integer operatorUserId;

	public void setTtaContractSpecialHeaderId(Integer ttaContractSpecialHeaderId) {
		this.ttaContractSpecialHeaderId = ttaContractSpecialHeaderId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_CONTRACT_SPECIAL_HEADER", sequenceName = "SEQ_TTA_CONTRACT_SPECIAL_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_CONTRACT_SPECIAL_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="tta_contract_special_header_id", nullable=true, length=22)	
	public Integer getTtaContractSpecialHeaderId() {
		return ttaContractSpecialHeaderId;
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

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=true, length=50)	
	public String getOrderNo() {
		return orderNo;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name="year", nullable=true, length=22)	
	public Integer getYear() {
		return year;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=false, length=30)	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=false, length=230)	
	public String getVendorName() {
		return vendorName;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=1000)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=1000)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name="org_code", nullable=true, length=50)	
	public String getOrgCode() {
		return orgCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name="org_name", nullable=true, length=500)	
	public String getOrgName() {
		return orgName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=500)	
	public String getDeptName() {
		return deptName;
	}

	public void setSubmitBy(Integer submitBy) {
		this.submitBy = submitBy;
	}

	@Column(name="submit_by", nullable=true, length=22)	
	public Integer getSubmitBy() {
		return submitBy;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=30)	
	public String getStatus() {
		return status;
	}

	public void setPaymentDate(Integer paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Column(name="payment_date", nullable=true, length=22)	
	public Integer getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDateReason(String paymentDateReason) {
		this.paymentDateReason = paymentDateReason;
	}

	@Column(name="payment_date_reason", nullable=true, length=1000)	
	public String getPaymentDateReason() {
		return paymentDateReason;
	}

	public void setReturnClauseReason(String returnClauseReason) {
		this.returnClauseReason = returnClauseReason;
	}

	@Column(name="return_clause_reason", nullable=true, length=1000)	
	public String getReturnClauseReason() {
		return returnClauseReason;
	}

	public void setAdvancePaymentReason(String advancePaymentReason) {
		this.advancePaymentReason = advancePaymentReason;
	}

	@Column(name="advance_payment_reason", nullable=true, length=1000)	
	public String getAdvancePaymentReason() {
		return advancePaymentReason;
	}

	public void setOthersReason(String othersReason) {
		this.othersReason = othersReason;
	}

	@Column(name="others_reason", nullable=true, length=1000)	
	public String getOthersReason() {
		return othersReason;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
