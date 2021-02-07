package com.sie.watsons.base.proposal.model.entities;

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
 * TtaBrandplnHEntity_HI Entity Object
 * Tue Jun 04 08:38:50 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_brandpln_header")
public class TtaBrandplnHEntity_HI {
    private Integer brandplnHId;
    private String newOrExisting;
    private String vendorNbr;
    private String vendorName;
    private BigDecimal poRecordSum;
    private BigDecimal salesSum;
    private BigDecimal actualGp;

	private BigDecimal poRecordSum2;
	private BigDecimal salesSum2;
	private BigDecimal actualGp2;

    private BigDecimal fcsPurchase;
    private BigDecimal purchaseGrowth;
    private BigDecimal fcsSales;
    private BigDecimal salesGrowth;
    private BigDecimal gp;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer proposalId;
    private Integer operatorUserId;

    private String yearCode;
    private BigDecimal adjustGp;

    private String isSplit;
    private BigDecimal fcsSplitPurchse;
    private BigDecimal fcsSplitSales;
    private BigDecimal splitGp;

	public void setBrandplnHId(Integer brandplnHId) {
		this.brandplnHId = brandplnHId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_BRANDPLN_HEADER", sequenceName = "SEQ_TTA_BRANDPLN_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_BRANDPLN_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="brandpln_h_id", nullable=false, length=22)	
	public Integer getBrandplnHId() {
		return brandplnHId;
	}

	public void setNewOrExisting(String newOrExisting) {
		this.newOrExisting = newOrExisting;
	}

	@Column(name="new_or_existing", nullable=false, length=50)	
	public String getNewOrExisting() {
		return newOrExisting;
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

	public void setPoRecordSum(BigDecimal poRecordSum) {
		this.poRecordSum = poRecordSum;
	}

	@Column(name="po_record_sum", nullable=true, length=22)	
	public BigDecimal getPoRecordSum() {
		return poRecordSum;
	}

	public void setSalesSum(BigDecimal salesSum) {
		this.salesSum = salesSum;
	}

	@Column(name="sales_sum", nullable=true, length=22)	
	public BigDecimal getSalesSum() {
		return salesSum;
	}

	public void setActualGp(BigDecimal actualGp) {
		this.actualGp = actualGp;
	}

	@Column(name="actual_gp", nullable=true, length=22)	
	public BigDecimal getActualGp() {
		return actualGp;
	}

	public void setFcsPurchase(BigDecimal fcsPurchase) {
		this.fcsPurchase = fcsPurchase;
	}

	@Column(name="fcs_purchase", nullable=true, length=22)	
	public BigDecimal getFcsPurchase() {
		return fcsPurchase;
	}

	public void setPurchaseGrowth(BigDecimal purchaseGrowth) {
		this.purchaseGrowth = purchaseGrowth;
	}

	@Column(name="purchase_growth", nullable=true, length=22)	
	public BigDecimal getPurchaseGrowth() {
		return purchaseGrowth;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	@Column(name="fcs_sales", nullable=true, length=22)	
	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setSalesGrowth(BigDecimal salesGrowth) {
		this.salesGrowth = salesGrowth;
	}

	@Column(name="sales_growth", nullable=true, length=22)	
	public BigDecimal getSalesGrowth() {
		return salesGrowth;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	@Column(name="gp", nullable=true, length=22)	
	public BigDecimal getGp() {
		return gp;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=2030)	
	public String getRemark() {
		return remark;
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

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name="po_record_sum2")
	public BigDecimal getPoRecordSum2() {
		return poRecordSum2;
	}

	public void setPoRecordSum2(BigDecimal poRecordSum2) {
		this.poRecordSum2 = poRecordSum2;
	}
	@Column(name="sales_sum2")
	public BigDecimal getSalesSum2() {
		return salesSum2;
	}


	public void setSalesSum2(BigDecimal salesSum2) {
		this.salesSum2 = salesSum2;
	}
	@Column(name="actual_gp2")
	public BigDecimal getActualGp2() {
		return actualGp2;
	}


	public void setActualGp2(BigDecimal actualGp2) {
		this.actualGp2 = actualGp2;
	}

	@Column(name="year_code")
	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	@Column(name = "adjust_gp",nullable = true,length = 22)
	public BigDecimal getAdjustGp() {
		return adjustGp;
	}

	public void setAdjustGp(BigDecimal adjustGp) {
		this.adjustGp = adjustGp;
	}

	@Column(name = "is_split",nullable = true,length = 100)
	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	@Column(name = "fcs_split_purchse",nullable = true,length = 22)
	public BigDecimal getFcsSplitPurchse() {
		return fcsSplitPurchse;
	}

	public void setFcsSplitPurchse(BigDecimal fcsSplitPurchse) {
		this.fcsSplitPurchse = fcsSplitPurchse;
	}

	@Column(name = "fcs_split_sales",nullable = true,length = 22)
	public BigDecimal getFcsSplitSales() {
		return fcsSplitSales;
	}

	public void setFcsSplitSales(BigDecimal fcsSplitSales) {
		this.fcsSplitSales = fcsSplitSales;
	}

	@Column(name = "split_gp",nullable = true,length = 22)
	public BigDecimal getSplitGp() {
		return splitGp;
	}

	public void setSplitGp(BigDecimal splitGp) {
		this.splitGp = splitGp;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
