package com.sie.watsons.base.withdrawal.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Transient;

/**
 * TtaSupplierItemMidEntity_HI Entity Object
 * Sat Jul 20 19:53:35 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_supplier_item_mid")
public class TtaSupplierItemMidEntity_HI {
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
    private String groupCode;
    private String groupName;
    private String lastSplitSupplierCode;//上一版本的拆分供应商
	private String lastSplitSupplierName;//上一版本的拆分供应商名字

	private BigDecimal fcsPurchase;
	private BigDecimal fcsSales;
	private BigDecimal gpAmt;

	private BigDecimal purchase;
	private BigDecimal sales;
	private BigDecimal cost;
	private String purchType;

	private BigDecimal brandFcsPurchase;
	private BigDecimal brandFcsSales;

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SUPPLIER_ITEM_MID", sequenceName="SEQ_TTA_SUPPLIER_ITEM_MID", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SUPPLIER_ITEM_MID",strategy=GenerationType.SEQUENCE)
	@Column(name="mid", nullable=false, length=22)	
	public Integer getMid() {
		return mid;
	}

	public void setSupplierItemHId(Integer supplierItemHId) {
		this.supplierItemHId = supplierItemHId;
	}

	@Column(name="supplier_item_h_id", nullable=true, length=22)
	public Integer getSupplierItemHId() {
		return supplierItemHId;
	}

	public void setOrginalId(Integer orginalId) {
		this.orginalId = orginalId;
	}

	@Column(name="orginal_id", nullable=true, length=22)	
	public Integer getOrginalId() {
		return orginalId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=false, length=50)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=100)
	public String getSupplierName() {
		return supplierName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=100)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)
	public String getDeptCode() {
		return deptCode;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name="brand_name", nullable=true, length=100)
	public String getBrandName() {
		return brandName;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name="brand_code", nullable=true, length=100)
	public String getBrandCode() {
		return brandCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=50)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name="item_name", nullable=true, length=100)
	public String getItemName() {
		return itemName;
	}

	public void setSplitSupplierCode(String splitSupplierCode) {
		this.splitSupplierCode = splitSupplierCode;
	}

	@Column(name="split_supplier_code", nullable=true, length=50)	
	public String getSplitSupplierCode() {
		return splitSupplierCode;
	}

	public void setSplitSupplierName(String splitSupplierName) {
		this.splitSupplierName = splitSupplierName;
	}

	@Column(name="split_supplier_name", nullable=true, length=100)	
	public String getSplitSupplierName() {
		return splitSupplierName;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
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

	@Column(name="last_update_date", nullable=false, length=7)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="group_code", nullable=false, length=30)
	public String getGroupCode() {
		return groupCode;
	}


	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_name", nullable=false, length=100)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="last_split_supplier_code", nullable=true, length=50)
	public String getLastSplitSupplierCode() {
		return lastSplitSupplierCode;
	}

	public void setLastSplitSupplierCode(String lastSplitSupplierCode) {
		this.lastSplitSupplierCode = lastSplitSupplierCode;
	}

	@Column(name="last_split_supplier_name", nullable=true, length=150)
	public String getLastSplitSupplierName() {
		return lastSplitSupplierName;
	}

	public void setLastSplitSupplierName(String lastSplitSupplierName) {
		this.lastSplitSupplierName = lastSplitSupplierName;
	}

	@Column(name="fcs_Purchase", nullable=true, length=22)
	public BigDecimal getFcsPurchase() {
		return fcsPurchase;
	}

	public void setFcsPurchase(BigDecimal fcsPurchase) {
		this.fcsPurchase = fcsPurchase;
	}

	@Column(name="fcs_Sales", nullable=true, length=22)
	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	@Column(name="purchase", nullable=true, length=22)
	public BigDecimal getPurchase() {
		return purchase;
	}

	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}

	@Column(name="sales", nullable=true, length=22)
	public BigDecimal getSales() {
		return sales;
	}

	public void setSales(BigDecimal sales) {
		this.sales = sales;
	}

	@Column(name="cost", nullable=true, length=22)
	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Column(name="purch_type", nullable=true, length=50)
	public String getPurchType() {
		return purchType;
	}

	public void setPurchType(String purchType) {
		this.purchType = purchType;
	}

	@Column(name="brand_fcs_purchase", nullable=true, length=22)
	public BigDecimal getBrandFcsPurchase() {
		return brandFcsPurchase;
	}

	public void setBrandFcsPurchase(BigDecimal brandFcsPurchase) {
		this.brandFcsPurchase = brandFcsPurchase;
	}

	@Column(name="brand_fcs_sales", nullable=true, length=22)
	public BigDecimal getBrandFcsSales() {
		return brandFcsSales;
	}

	public void setBrandFcsSales(BigDecimal brandFcsSales) {
		this.brandFcsSales = brandFcsSales;
	}

	@Column(name="gp_amt", nullable=true, length=22)
	public BigDecimal getGpAmt() {
		return gpAmt;
	}

	public void setGpAmt(BigDecimal gpAmt) {
		this.gpAmt = gpAmt;
	}

	@Column(name="brand_name_en", nullable=true, length=100)
	public String getBrandNameEn() {
		return brandNameEn;
	}

	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}
}
