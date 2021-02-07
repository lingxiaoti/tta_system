package com.sie.watsons.base.withdrawal.model.entities.readonly;

import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaSupplierItemMidEntity_HI_RO Entity Object
 * Sat Jul 20 19:53:36 CST 2019  Auto Generate
 */

public class TtaSupplierItemMidEntity_HI_RO {
    private Integer mid;//主键
    private Integer supplierItemHId;//proposal拆分与合同头表id
    private Integer orginalId;//记录被拆分表id
    private String supplierCode;//供应商编码
    private String supplierName;//供应商名称
    private String deptName;//部门名称
    private String deptCode;//部门编码
    private String brandName;//品牌名称
	private String brandNameEn;//品牌英文名
    private String brandCode;//品牌编码
    private String itemCode;//物料编码
    private String itemName;//物料名称
    private String splitSupplierCode;//拆分(指定)后的供应商编码
    private String splitSupplierName;//拆分(指定)后的供应商名称
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	private String lastSplitSupplierCode;
	private String lastSplitSupplierName;

	private BigDecimal fcsPurchase;
	private BigDecimal fcsSales;
	private BigDecimal gpAmt;

	private BigDecimal purchase;
	private BigDecimal sales;
	private BigDecimal cost;
	private String purchType;

	private BigDecimal brandFcsPurchase;
	private BigDecimal brandFcsSales;

	public static String getQuerySql(String uniqueNumber) {
		return "select \n" +
				"'"+ uniqueNumber + "'" + " as r_id,\n" +
				"       -1 AS r_io_id,\n" +
				"       t2.vendor_nbr,\n" +
				"       t2.split_supplier_code,\n" +
				"       t2.sales_amt / t3.sales_amt as sales_amt_rate,\n" +
				"       t2.po_amt / t3.po_amt as po_amt_rate,\n" +
				"       sysdate as creation_date,\n" +
				"       sysdate as last_update_date,\n" +
				"       0 as version_num,\n" +
				"       -1 as created_by,\n" +
				"       -1 as last_updated_by,\n" +
				"       -1 as last_update_login\n" +
				"  from (\n" +
				"        select t4.vendor_nbr,\n" +
				"               t4.split_supplier_code,\n" +
				"               sum(t4.sales_amt) as sales_amt,\n" +
				"               sum(t4.po_amt) as po_amt\n" +
				"          from tta_sale_io_mid_record t4  where t4.r_id=:rId group by t4.vendor_nbr, t4.split_supplier_code\n" +
				"        ) t2\n" +
				"  left join\n" +
				" (\n" +
				"  SELECT vendor_nbr， sum(sales_amt) as sales_amt, sum(po_amt) as po_amt\n" +
				"    from  tta_sale_io_mid_record where r_id=:rId\n" +
				"   group by vendor_nbr\n" +
				"  ) t3\n" +
				"    on t2.vendor_nbr = t3.vendor_nbr order  by t2.vendor_nbr ";
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	
	public Integer getMid() {
		return mid;
	}

	public void setSupplierItemHId(Integer supplierItemHId) {
		this.supplierItemHId = supplierItemHId;
	}

	
	public Integer getSupplierItemHId() {
		return supplierItemHId;
	}

	public void setOrginalId(Integer orginalId) {
		this.orginalId = orginalId;
	}

	
	public Integer getOrginalId() {
		return orginalId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	
	public String getBrandCode() {
		return brandCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
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


	public String getLastSplitSupplierCode() {
		return lastSplitSupplierCode;
	}

	public void setLastSplitSupplierCode(String lastSplitSupplierCode) {
		this.lastSplitSupplierCode = lastSplitSupplierCode;
	}

	public String getLastSplitSupplierName() {
		return lastSplitSupplierName;
	}

	public void setLastSplitSupplierName(String lastSplitSupplierName) {
		this.lastSplitSupplierName = lastSplitSupplierName;
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

	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getPurchType() {
		return purchType;
	}

	public void setPurchType(String purchType) {
		this.purchType = purchType;
	}

	public BigDecimal getBrandFcsPurchase() {
		return brandFcsPurchase;
	}

	public void setBrandFcsPurchase(BigDecimal brandFcsPurchase) {
		this.brandFcsPurchase = brandFcsPurchase;
	}

	public BigDecimal getBrandFcsSales() {
		return brandFcsSales;
	}

	public void setBrandFcsSales(BigDecimal brandFcsSales) {
		this.brandFcsSales = brandFcsSales;
	}

	public BigDecimal getGpAmt() {
		return gpAmt;
	}

	public void setGpAmt(BigDecimal gpAmt) {
		this.gpAmt = gpAmt;
	}

	public String getBrandNameEn() {
		return brandNameEn;
	}

	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}
}
