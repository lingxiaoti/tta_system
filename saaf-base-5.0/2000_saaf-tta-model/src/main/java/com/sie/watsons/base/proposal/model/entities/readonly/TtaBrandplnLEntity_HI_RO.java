package com.sie.watsons.base.proposal.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaBrandplnLEntity_HI_RO Entity Object
 * Tue Jun 04 08:38:50 CST 2019  Auto Generate
 */

public class TtaBrandplnLEntity_HI_RO {
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
	private BigDecimal brandPlanAdjust;
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

	public static final String TTA_ITEM_V = "select * from tta_brandpln_line_V V where 1=1 ";

	public static final String SELECT_BRANDPLNL_DETAIL_DATA_BYPROPOSALID = "select tbl.brandpln_l_id brandplnLId,tbl.proposal_id proposalId," +
			"tbl.brand_details brandDetails from tta_brandpln_line tbl where tbl.brand_details not in('New_Brand','New Brand') and  1=1 ";

	public static final String FIND_BRNADPLN_DEPT = "select max(v.PROPOSAL_ID) PROPOSAL_ID,v.DEPT_CODE,v.DEPT_DESC from tta_brandpln_line_V v where 1=1 ";

	public static final String FIND_BRNADPLN_BRAND = "select max(v.PROPOSAL_ID) PROPOSAL_ID,v.BRAND_CN,v.BRAND_EN from tta_brandpln_line_V v where 1=1 ";

	public static String getBrandplnDetailSql() {
		return "select * from tta_brandpln_line tbl where tbl.proposal_id =:proposalId";
	}

	public static String getUpdateFcsAmt(Integer proposalId){
		return "update tta_brandpln_line tbl \n" +
				"       set \n" +
				"          tbl.fcs_split_purchse = tbl.fcs_purchase,\n" +
				"          tbl.fcs_split_sales = tbl.fcs_sales,\n" +
				"          tbl.split_gp = tbl.gp\n" +
				"  where tbl.proposal_id =" + proposalId;
	}

    public void setBrandplnLId(Integer brandplnLId) {
		this.brandplnLId = brandplnLId;
	}

	
	public Integer getBrandplnLId() {
		return brandplnLId;
	}

	public void setBrandDetails(String brandDetails) {
		this.brandDetails = brandDetails;
	}

	
	public String getBrandDetails() {
		return brandDetails;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setPoRecord(BigDecimal poRecord) {
		this.poRecord = poRecord;
	}

	
	public BigDecimal getPoRecord() {
		return poRecord;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	
	public BigDecimal getSales() {
		return sales;
	}

	public void setActualGp(BigDecimal actualGp) {
		this.actualGp = actualGp;
	}

	
	public BigDecimal getActualGp() {
		return actualGp;
	}

	public void setFcsPurchase(BigDecimal fcsPurchase) {
		this.fcsPurchase = fcsPurchase;
	}

	
	public BigDecimal getFcsPurchase() {
		return fcsPurchase;
	}

	public void setPurchaseGrowth(BigDecimal purchaseGrowth) {
		this.purchaseGrowth = purchaseGrowth;
	}

	
	public BigDecimal getPurchaseGrowth() {
		return purchaseGrowth;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	
	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setSalesGrowth(BigDecimal salesGrowth) {
		this.salesGrowth = salesGrowth;
	}

	
	public BigDecimal getSalesGrowth() {
		return salesGrowth;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	
	public BigDecimal getGp() {
		return gp;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public void setBrandplnHId(Integer brandplnHId) {
		this.brandplnHId = brandplnHId;
	}

	
	public Integer getBrandplnHId() {
		return brandplnHId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public BigDecimal getPoRecord2() {
		return poRecord2;
	}

	public void setPoRecord2(BigDecimal poRecord2) {
		this.poRecord2 = poRecord2;
	}

	public BigDecimal getSales2() {
		return sales2;
	}

	public void setSales2(BigDecimal sales2) {
		this.sales2 = sales2;
	}

	public BigDecimal getActualGp2() {
		return actualGp2;
	}

	public void setActualGp2(BigDecimal actualGp2) {
		this.actualGp2 = actualGp2;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public BigDecimal getBrandPlanAdjust() {
		return brandPlanAdjust;
	}

	public void setBrandPlanAdjust(BigDecimal brandPlanAdjust) {
		this.brandPlanAdjust = brandPlanAdjust;
	}

	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	public BigDecimal getAnnualPurchase() {
		return annualPurchase;
	}

	public void setAnnualPurchase(BigDecimal annualPurchase) {
		this.annualPurchase = annualPurchase;
	}

	public BigDecimal getAnnualSales() {
		return annualSales;
	}

	public void setAnnualSales(BigDecimal annualSales) {
		this.annualSales = annualSales;
	}

	public String getFcsPurchaseCon() {
		return fcsPurchaseCon;
	}

	public void setFcsPurchaseCon(String fcsPurchaseCon) {
		this.fcsPurchaseCon = fcsPurchaseCon;
	}

	public String getPurchaseGrowthCon() {
		return purchaseGrowthCon;
	}

	public void setPurchaseGrowthCon(String purchaseGrowthCon) {
		this.purchaseGrowthCon = purchaseGrowthCon;
	}

	public String getFcsSalesCon() {
		return fcsSalesCon;
	}

	public void setFcsSalesCon(String fcsSalesCon) {
		this.fcsSalesCon = fcsSalesCon;
	}

	public String getSalesGrowthCon() {
		return salesGrowthCon;
	}

	public void setSalesGrowthCon(String salesGrowthCon) {
		this.salesGrowthCon = salesGrowthCon;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(String isSplit) {
		this.isSplit = isSplit;
	}

	public BigDecimal getFcsSplitPurchse() {
		return fcsSplitPurchse;
	}

	public void setFcsSplitPurchse(BigDecimal fcsSplitPurchse) {
		this.fcsSplitPurchse = fcsSplitPurchse;
	}

	public BigDecimal getFcsSplitSales() {
		return fcsSplitSales;
	}

	public void setFcsSplitSales(BigDecimal fcsSplitSales) {
		this.fcsSplitSales = fcsSplitSales;
	}

	public BigDecimal getSplitGp() {
		return splitGp;
	}

	public void setSplitGp(BigDecimal splitGp) {
		this.splitGp = splitGp;
	}
}
