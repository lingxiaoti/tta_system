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
 * TtaReportAboiSummaryFixLineEntity_HI Entity Object
 * Fri May 22 17:28:29 CST 2020  Auto Generate
 */
@Entity
@Table(name="TTA_REPORT_ABOI_SUMMARY_FIX_LINE")
public class TtaReportAboiSummaryFixLineEntity_HI {
    private Integer aboiFixId;
	private Integer proposalId;
	private BigDecimal purchase;
	private Integer reportYear;
    private String remake;
    private String actionCode;
    private String reasonCode;
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
	private String vendorNbr;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date beginDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date endDate;
    private Integer operatorUserId;

	public void setAboiFixId(Integer aboiFixId) {
		this.aboiFixId = aboiFixId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_REPORT_ABOI_SUMMARY_FIX_LINE", sequenceName = "SEQ_TTA_REPORT_ABOI_SUMMARY_FIX_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REPORT_ABOI_SUMMARY_FIX_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="aboi_fix_id", nullable=false, length=22)	
	public Integer getAboiFixId() {
		return aboiFixId;
	}

	public void setRemake(String remake) {
		this.remake = remake;
	}

	@Column(name="remake", nullable=true, length=2000)	
	public String getRemake() {
		return remake;
	}

	@Column(name="purchase", nullable=true, length=22)
	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	@Column(name="action_code", nullable=true, length=200)	
	public String getActionCode() {
		return actionCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	@Column(name="reason_code", nullable=true, length=200)	
	public String getReasonCode() {
		return reasonCode;
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

	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}

	@Column(name="report_year", nullable=true, length=22)
	public Integer getReportYear() {
		return reportYear;
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

	@Column(name="vendor_nbr", nullable=true, length=200)
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="begin_date", nullable=true, length=7)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name="end_date", nullable=true, length=7)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
