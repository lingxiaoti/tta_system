package com.sie.watsons.base.proposal.model.entities.readonly;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldDesc;
import com.sie.watsons.base.proposal.utils.CustomDecimalIntegerConverter;

import java.math.BigDecimal;
import java.util.Date;

public class TtaBrandplnLEntity_HI_RO_Model {
	@ExcelIgnore
	@FieldDesc(isUpdateWhereKey = true)//是否为更新条件
    private Integer brandplnLId;

	@ExcelProperty(value = "Brand Details")
    private String brandDetails;

	@ExcelProperty(value = "Group Code",converter = CustomDecimalIntegerConverter.class)
    private String groupCode;

	@ExcelProperty(value = "Group Desc")
    private String groupDesc;

	@ExcelProperty(value = "Dept Code")
    private String deptCode;

	@ExcelProperty(value = "DEPT_DESC")
    private String deptDesc;

	@ExcelProperty(value = "BRAND_CN")
    private String brandCn;

	@ExcelProperty(value = "BRAND_EN")
	private String brandEn;

	@ExcelProperty(value = "Purchase （PO Record）")
    private BigDecimal poRecord;

	@ExcelProperty(value = "Sales")
    private BigDecimal sales;

	@ExcelProperty(value = "Actual GP%")
    private BigDecimal actualGp;

	@ExcelProperty(value = "Annual Purchase")
	private BigDecimal annualPurchase;

	@ExcelProperty(value = "Annual Sales")
	private BigDecimal annualSales;

	@ExcelProperty(value = "F’cs Purchase （PO Record）")
	@FieldDesc(isUpdateWhereKey = false)
    private BigDecimal fcsPurchase;

	@ExcelProperty(value = "Purchase Growth%")
	@FieldDesc(isUpdateWhereKey = false)
    private BigDecimal purchaseGrowth;

	@ExcelProperty(value = "F’cs Sales")
	@FieldDesc(isUpdateWhereKey = false)
    private BigDecimal fcsSales;

	@ExcelProperty(value = "Sales Growth%")
	@FieldDesc(isUpdateWhereKey = false)
    private BigDecimal salesGrowth;

	@ExcelProperty(value = "GP%")
	@FieldDesc(isUpdateWhereKey = false)
    private BigDecimal gp;

    @ExcelIgnore
    private Integer brandplnHId;

	@ExcelIgnore
    private Integer proposalId;

	@ExcelIgnore
	private Integer createdBy;

	@ExcelIgnore
	@FieldDesc(isUpdateWhereKey = false)
	private Integer lastUpdatedBy;

	@ExcelIgnore
	@FieldDesc(isUpdateWhereKey = false)
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;

	@ExcelIgnore
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;

	@ExcelIgnore
	@FieldDesc(isUpdateWhereKey = false)
	private Integer lastUpdateLogin;

	public Integer getBrandplnLId() {
		return brandplnLId;
	}

	public void setBrandplnLId(Integer brandplnLId) {
		this.brandplnLId = brandplnLId;
	}

	public String getBrandDetails() {
		return brandDetails;
	}

	public void setBrandDetails(String brandDetails) {
		this.brandDetails = brandDetails;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	public BigDecimal getPoRecord() {
		return poRecord;
	}

	public void setPoRecord(BigDecimal poRecord) {
		this.poRecord = poRecord;
	}

	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	public BigDecimal getActualGp() {
		return actualGp;
	}

	public void setActualGp(BigDecimal actualGp) {
		this.actualGp = actualGp;
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

	public BigDecimal getFcsPurchase() {
		return fcsPurchase;
	}

	public void setFcsPurchase(BigDecimal fcsPurchase) {
		this.fcsPurchase = fcsPurchase;
	}

	public BigDecimal getPurchaseGrowth() {
		return purchaseGrowth;
	}

	public void setPurchaseGrowth(BigDecimal purchaseGrowth) {
		this.purchaseGrowth = purchaseGrowth;
	}

	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	public BigDecimal getSalesGrowth() {
		return salesGrowth;
	}

	public void setSalesGrowth(BigDecimal salesGrowth) {
		this.salesGrowth = salesGrowth;
	}

	public BigDecimal getGp() {
		return gp;
	}

	public void setGp(BigDecimal gp) {
		this.gp = gp;
	}

	public Integer getBrandplnHId() {
		return brandplnHId;
	}

	public void setBrandplnHId(Integer brandplnHId) {
		this.brandplnHId = brandplnHId;
	}

	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}
}
