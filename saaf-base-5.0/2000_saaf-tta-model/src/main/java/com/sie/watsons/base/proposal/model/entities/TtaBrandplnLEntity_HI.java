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
 * TtaBrandplnLEntity_HI Entity Object
 * Tue Jun 04 08:38:50 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_brandpln_line")
public class TtaBrandplnLEntity_HI {
    private Integer brandplnLId;
    private String brandDetails;
    private String groupCode;
    private String groupDesc;
    private String deptCode;
    private String deptDesc;
    private String brandCode;
    private String brandCn;
    private BigDecimal poRecord;
    private BigDecimal sales;
    private BigDecimal actualGp;

	private BigDecimal poRecord2;
	private BigDecimal sales2;
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
    private Integer brandplnHId;
    private Integer proposalId;
    private Integer operatorUserId;
    private BigDecimal brandPlanAdjust;//品牌计划调整比例

	private String brandEn;//品牌英文名
	private BigDecimal annualPurchase;
	private BigDecimal annualSales;

	//按钮控制
	private String fcsPurchaseCon;
	private String purchaseGrowthCon;
	private String fcsSalesCon;
	private String salesGrowthCon;

	private Integer groupId;
	private String isSplit;
	private BigDecimal fcsSplitPurchse;
	private BigDecimal fcsSplitSales;
	private BigDecimal splitGp;


	public void setBrandplnLId(Integer brandplnLId) {
		this.brandplnLId = brandplnLId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_BRANDPLN_LINE", sequenceName = "SEQ_TTA_BRANDPLN_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_BRANDPLN_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="brandpln_l_id", nullable=false, length=22)	
	public Integer getBrandplnLId() {
		return brandplnLId;
	}

	public void setBrandDetails(String brandDetails) {
		this.brandDetails = brandDetails;
	}

	@Column(name="brand_details", nullable=false, length=50)	
	public String getBrandDetails() {
		return brandDetails;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=false, length=30)	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=false, length=230)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=30)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=false, length=230)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name="brand_code", nullable=true, length=30)	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=false, length=230)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setPoRecord(BigDecimal poRecord) {
		this.poRecord = poRecord;
	}

	@Column(name="po_record", nullable=true, length=22)	
	public BigDecimal getPoRecord() {
		return poRecord;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	@Column(name="sales", nullable=true, length=22)	
	public BigDecimal getSales() {
		return sales;
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

	public void setBrandplnHId(Integer brandplnHId) {
		this.brandplnHId = brandplnHId;
	}

	@Column(name="brandpln_h_id", nullable=true, length=22)	
	public Integer getBrandplnHId() {
		return brandplnHId;
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

	@Column(name="po_record2")
	public BigDecimal getPoRecord2() {
		return poRecord2;
	}

	public void setPoRecord2(BigDecimal poRecord2) {
		this.poRecord2 = poRecord2;
	}

	@Column(name="sales2")
	public BigDecimal getSales2() {
		return sales2;
	}

	public void setSales2(BigDecimal sales2) {
		this.sales2 = sales2;
	}

	@Column(name="actual_gp2")
	public BigDecimal getActualGp2() {
		return actualGp2;
	}

	public void setActualGp2(BigDecimal actualGp2) {
		this.actualGp2 = actualGp2;
	}

	@Column(name = "brand_Plan_Adjust")
	public BigDecimal getBrandPlanAdjust() {
		return brandPlanAdjust;
	}

	public void setBrandPlanAdjust(BigDecimal brandPlanAdjust) {
		this.brandPlanAdjust = brandPlanAdjust;
	}

	@Column(name="brand_en", nullable=false, length=120)
	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="annual_purchase", nullable=true, length=22)
	public BigDecimal getAnnualPurchase() {
		return annualPurchase;
	}

	public void setAnnualPurchase(BigDecimal annualPurchase) {
		this.annualPurchase = annualPurchase;
	}

	@Column(name="annual_sales", nullable=true, length=22)
	public BigDecimal getAnnualSales() {
		return annualSales;
	}

	public void setAnnualSales(BigDecimal annualSales) {
		this.annualSales = annualSales;
	}

	@Column(name="fcs_purchase_con", nullable=true, length=10)
	public String getFcsPurchaseCon() {
		return fcsPurchaseCon;
	}

	public void setFcsPurchaseCon(String fcsPurchaseCon) {
		this.fcsPurchaseCon = fcsPurchaseCon;
	}

	@Column(name="purchase_growth_con", nullable=true, length=10)
	public String getPurchaseGrowthCon() {
		return purchaseGrowthCon;
	}

	public void setPurchaseGrowthCon(String purchaseGrowthCon) {
		this.purchaseGrowthCon = purchaseGrowthCon;
	}

	@Column(name="fcs_sales_con", nullable=true, length=10)
	public String getFcsSalesCon() {
		return fcsSalesCon;
	}

	public void setFcsSalesCon(String fcsSalesCon) {
		this.fcsSalesCon = fcsSalesCon;
	}

	@Column(name="sales_growth_con", nullable=true, length=10)
	public String getSalesGrowthCon() {
		return salesGrowthCon;
	}

	public void setSalesGrowthCon(String salesGrowthCon) {
		this.salesGrowthCon = salesGrowthCon;
	}

	@Column(name="group_id", nullable=true, length=22)
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name="is_split", nullable=true, length=100)
	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	@Column(name="fcs_split_purchse", nullable=true, length=22)
	public BigDecimal getFcsSplitPurchse() {
		return fcsSplitPurchse;
	}

	public void setFcsSplitPurchse(BigDecimal fcsSplitPurchse) {
		this.fcsSplitPurchse = fcsSplitPurchse;
	}

	@Column(name="fcs_split_sales", nullable=true, length=22)
	public BigDecimal getFcsSplitSales() {
		return fcsSplitSales;
	}

	public void setFcsSplitSales(BigDecimal fcsSplitSales) {
		this.fcsSplitSales = fcsSplitSales;
	}

	@Column(name="split_gp", nullable=true, length=22)
	public BigDecimal getSplitGp() {
		return splitGp;
	}

	public void setSplitGp(BigDecimal splitGp) {
		this.splitGp = splitGp;
	}
}
