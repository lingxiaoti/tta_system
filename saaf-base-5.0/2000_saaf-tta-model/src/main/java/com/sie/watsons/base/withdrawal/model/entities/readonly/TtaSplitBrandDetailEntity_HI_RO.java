package com.sie.watsons.base.withdrawal.model.entities.readonly;

import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSplitBrandDetailEntity_HI_RO Entity Object
 * Thu Dec 26 20:11:06 CST 2019  Auto Generate
 */

public class TtaSplitBrandDetailEntity_HI_RO {

	public static final String QUERY_BRAND = "select *\n" +
			"  from tta_split_brand_detail tsbd\n" +
			" where tsbd.split_status =:splitStatus\n" +
			"   and tsbd.supplier_code =:supplierCode\n" +
			"   and tsbd.proposal_year =:proposalYear\n" +
			"   and tsbd.split_supplier_code <>:splitSupplierCode ";

	public static final String QUERY_NOT_BRAND = "select *\n" +
			"  from tta_split_brand_detail tsbd\n" +
			" where tsbd.split_status = :splitStatus\n" +
			"   and tsbd.supplier_code <>:supplierCode\n" +
			"   and tsbd.proposal_year =:proposalYear\n" +
			"   and tsbd.split_supplier_code =:splitSupplierCode ";

    public static final String QUERY_MONEY = "select\n" +
			"  min(total_fcs_purchase) total_fcs_purchase,\n" +
			"  min(total_fcs_sales) total_fcs_sales,\n" +
			"  min(split_brand_id) split_brand_id,\n" +
			"  sum(fcs_sum_purchase) fcs_split_purchase,\n" +
			"  sum(fcs_sum_sales) fcs_split_sales\n" +
			" from (\n" +
			"\n" +
			" select \n" +
			"    min(tsbd.split_brand_id) split_brand_id,\n" +
			"    min(nvl(tsbd.total_fcs_purchase,0)) total_fcs_purchase,\n" +
			"    min(nvl(tsbd.total_fcs_sales,0)) total_fcs_sales,\n" +
			"    sum(0-nvl(tsbd.fcs_purchase,0)) fcs_sum_purchase,\n" +
			"    sum(0-nvl(tsbd.fcs_sales,0)) fcs_sum_sales\n" +
			" from tta_split_brand_detail tsbd\n" +
			" where tsbd.split_status =:splitStatus\n" +
			"   and tsbd.supplier_code =:supplierCode\n" +
			"   and tsbd.proposal_year =:proposalYear\n" +
			"   and tsbd.split_supplier_code <>:supplierCode\n" +
			"   \n" +
			"   union all\n" +
			"   \n" +
			" select \n" +
			"   min(tsbd.split_brand_id) split_brand_id,\n" +
			"   min(nvl(tsbd.total_fcs_purchase,0)) total_fcs_purchase,\n" +
			"   min(nvl(tsbd.total_fcs_sales,0)) total_fcs_sales,\n" +
			"   sum(nvl(tsbd.fcs_purchase,0)) fcs_sum_purchase,\n" +
			"   sum(nvl(tsbd.fcs_sales,0)) fcs_sum_sales\n" +
			" from tta_split_brand_detail tsbd\n" +
			" where tsbd.split_status = :splitStatus\n" +
			"   and tsbd.supplier_code <>:supplierCode\n" +
			"   and tsbd.proposal_year =:proposalYear\n" +
			"   and tsbd.split_supplier_code =:supplierCode \n" +
			"   )" ;

	public static final String QUERY_SPLIT_FCS_PURCAHSE_SALES = "select\n" +
			"  min(split_brand_id) split_brand_id,\n" +
			"  sum(fcs_sum_purchase) fcs_split_purchase,\n" +
			"  sum(fcs_sum_sales) fcs_split_sales\n" +
			" from (\n" +
			"\n" +
			" select --当前供应商拆分金额给其他供应商\n" +
			"    min(tsbd.split_brand_id) split_brand_id,\n" +
			"    sum(0 - nvl(tsbd.fcs_purchase,0)) fcs_sum_purchase,\n" +
			"    sum(0 - nvl(tsbd.fcs_sales,0)) fcs_sum_sales\n" +
			" from tta_split_brand_detail tsbd\n" +
			" where tsbd.split_status =:splitStatus\n" +
			"   and tsbd.supplier_code =:supplierCode\n" +
			"   and tsbd.proposal_year =:proposalYear\n" +
			"   and tsbd.split_supplier_code <>:supplierCode\n" +
			"   \n" +
			"   union all\n" +
			"   \n" +
			" select --其他供应商拆分金额给当前供应商\n" +
			"   min(tsbd.split_brand_id) split_brand_id,\n" +
			"   sum(nvl(tsbd.fcs_purchase,0)) fcs_sum_purchase,\n" +
			"   sum(nvl(tsbd.fcs_sales,0)) fcs_sum_sales\n" +
			" from tta_split_brand_detail tsbd\n" +
			" where tsbd.split_status = :splitStatus\n" +
			"   and tsbd.supplier_code <>:supplierCode\n" +
			"   and tsbd.proposal_year =:proposalYear\n" +
			"   and tsbd.split_supplier_code =:supplierCode \n" +
			"   )" ;



    private Integer splitBrandId;
    private String proposalCode;
    private Integer proposalId;
	private String proposalYear;
    private String splitStatus;
    private String groupCode;
    private String groupName;
    private String deptCode;
    private String deptName;
    private String brandCode;
    private String brandName;
    private String brandNameEn;
    private String itemNbr;
    private String itemName;
    private String splitSupplierCode;
    private String splitSupplierName;
    private Integer supplierItemId;
    private Integer mid;
    private Integer brandplnLId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	private BigDecimal fcsPurchase;
	private BigDecimal fcsSales;
	private BigDecimal amoutRateFcspur;//fcs purchase总额
	private BigDecimal amoutRateFcssal;//fcs sales总额
	private String splitCondition;
	private String supplierCode;
	private String supplierName;
	private BigDecimal totalFcsPurchase;
	private BigDecimal totalFcsSales;
	private BigDecimal fcsSplitPurchase;
	private BigDecimal fcsSplitSales;

	public void setSplitBrandId(Integer splitBrandId) {
		this.splitBrandId = splitBrandId;
	}

	
	public Integer getSplitBrandId() {
		return splitBrandId;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	
	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	
	public String getProposalYear() {
		return proposalYear;
	}

	public void setSplitStatus(String splitStatus) {
		this.splitStatus = splitStatus;
	}

	
	public String getSplitStatus() {
		return splitStatus;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	public String getGroupName() {
		return groupName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	
	public String getBrandName() {
		return brandName;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	
	public String getItemNbr() {
		return itemNbr;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
	public String getItemName() {
		return itemName;
	}

	public void setSplitSupplierCode(String splitSupplierCode) {
		this.splitSupplierCode = splitSupplierCode;
	}

	
	public String getSplitSupplierCode() {
		return splitSupplierCode;
	}

	public void setSplitSupplierName(String splitSupplierName) {
		this.splitSupplierName = splitSupplierName;
	}

	
	public String getSplitSupplierName() {
		return splitSupplierName;
	}

	public void setSupplierItemId(Integer supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

	
	public Integer getSupplierItemId() {
		return supplierItemId;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	
	public Integer getMid() {
		return mid;
	}

	public void setBrandplnLId(Integer brandplnLId) {
		this.brandplnLId = brandplnLId;
	}

	
	public Integer getBrandplnLId() {
		return brandplnLId;
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

	public BigDecimal getFcsPurchase() {
		return fcsPurchase;
	}

	public void setFcsPurchase(BigDecimal fcsPurchase) {
		this.fcsPurchase = fcsPurchase;
	}

	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	public BigDecimal getAmoutRateFcspur() {
		return amoutRateFcspur;
	}

	public void setAmoutRateFcspur(BigDecimal amoutRateFcspur) {
		this.amoutRateFcspur = amoutRateFcspur;
	}

	public BigDecimal getAmoutRateFcssal() {
		return amoutRateFcssal;
	}

	public void setAmoutRateFcssal(BigDecimal amoutRateFcssal) {
		this.amoutRateFcssal = amoutRateFcssal;
	}

	public String getSplitCondition() {
		return splitCondition;
	}

	public void setSplitCondition(String splitCondition) {
		this.splitCondition = splitCondition;
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

	public BigDecimal getTotalFcsPurchase() {
		return totalFcsPurchase;
	}

	public void setTotalFcsPurchase(BigDecimal totalFcsPurchase) {
		this.totalFcsPurchase = totalFcsPurchase;
	}

	public BigDecimal getTotalFcsSales() {
		return totalFcsSales;
	}

	public void setTotalFcsSales(BigDecimal totalFcsSales) {
		this.totalFcsSales = totalFcsSales;
	}

	public BigDecimal getFcsSplitPurchase() {
		return fcsSplitPurchase;
	}

	public void setFcsSplitPurchase(BigDecimal fcsSplitPurchase) {
		this.fcsSplitPurchase = fcsSplitPurchase;
	}

	public BigDecimal getFcsSplitSales() {
		return fcsSplitSales;
	}

	public void setFcsSplitSales(BigDecimal fcsSplitSales) {
		this.fcsSplitSales = fcsSplitSales;
	}

	public String getBrandNameEn() {
		return brandNameEn;
	}

	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}
}
