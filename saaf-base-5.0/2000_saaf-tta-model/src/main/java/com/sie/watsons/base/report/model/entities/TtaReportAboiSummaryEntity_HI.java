package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaReportAboiSummaryEntity_HI Entity Object
 * Fri May 22 17:28:11 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_REPORT_ABOI_SUMMARY")
public class TtaReportAboiSummaryEntity_HI {
    private String vendorNbr;
	private String orderNo;
    private Integer reportYear;
    private String name;
    private BigDecimal feeNotax;
    private BigDecimal feeIntas;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private String groupCode;
    private String groupDesc;
    private String deptCode;
    private String deptDesc;
    private String brandCn;
    private String brandEn;
    private Integer aboiFixId;
	private Integer proposalId;
	private BigDecimal purchase;
	private Integer aboiId;
    private Integer operatorUserId;

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=true, length=30)	
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}

	@Column(name="report_year", nullable=true, length=22)	
	public Integer getReportYear() {
		return reportYear;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="name", nullable=true, length=300)	
	public String getName() {
		return name;
	}

	public void setFeeNotax(BigDecimal feeNotax) {
		this.feeNotax = feeNotax;
	}

	@Column(name="fee_notax", nullable=true, length=22)	
	public BigDecimal getFeeNotax() {
		return feeNotax;
	}

	public void setFeeIntas(BigDecimal feeIntas) {
		this.feeIntas = feeIntas;
	}

	@Column(name="fee_intas", nullable=true, length=22)	
	public BigDecimal getFeeIntas() {
		return feeIntas;
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

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=300)	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=300)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=300)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=300)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=300)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=300)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setAboiFixId(Integer aboiFixId) {
		this.aboiFixId = aboiFixId;
	}

	@Column(name="aboi_fix_id", nullable=true, length=22)	
	public Integer getAboiFixId() {
		return aboiFixId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_REPORT_ABOI_SUMMARY", sequenceName = "SEQ_TTA_REPORT_ABOI_SUMMARY", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REPORT_ABOI_SUMMARY", strategy = GenerationType.SEQUENCE)
	@Column(name="aboi_id", nullable=true, length=22)
	public Integer getAboiId() {
		return aboiId;
	}

	public void setAboiId(Integer aboiId) {
		this.aboiId = aboiId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="proposal_id", nullable=true, length=22)
	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="purchase", nullable=true, length=22)
	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	@Column(name="order_no", nullable=true, length=22)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
