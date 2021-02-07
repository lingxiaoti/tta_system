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
 * TtaSplitBrandDetailEntity_HI Entity Object
 * Thu Dec 26 20:11:06 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_split_brand_detail")
public class TtaSplitBrandDetailEntity_HI {
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
	private String splitCondition;//拆分条件
	private String supplierCode;
	private String supplierName;
	private BigDecimal totalFcsPurchase;
	private BigDecimal totalFcsSales;

	public void setSplitBrandId(Integer splitBrandId) {
		this.splitBrandId = splitBrandId;
	}

	@Id
	@SequenceGenerator(name="seq_split_brand_detail", sequenceName="seq_split_brand_detail", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="seq_split_brand_detail",strategy=GenerationType.SEQUENCE)
	@Column(name="split_brand_id", nullable=false, length=22)	
	public Integer getSplitBrandId() {
		return splitBrandId;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	@Column(name="proposal_code", nullable=true, length=100)	
	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=22)	
	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalYear(String proposalYear) {
		this.proposalYear = proposalYear;
	}

	@Column(name="proposal_year", nullable=true, length=100)
	public String getProposalYear() {
		return proposalYear;
	}

	public void setSplitStatus(String splitStatus) {
		this.splitStatus = splitStatus;
	}

	@Column(name="split_status", nullable=true, length=20)	
	public String getSplitStatus() {
		return splitStatus;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=30)	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="group_name", nullable=true, length=150)	
	public String getGroupName() {
		return groupName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=50)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=150)	
	public String getDeptName() {
		return deptName;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name="brand_code", nullable=true, length=100)	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name="brand_name", nullable=true, length=150)	
	public String getBrandName() {
		return brandName;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	@Column(name="item_nbr", nullable=true, length=100)	
	public String getItemNbr() {
		return itemNbr;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name="item_name", nullable=true, length=150)	
	public String getItemName() {
		return itemName;
	}

	public void setSplitSupplierCode(String splitSupplierCode) {
		this.splitSupplierCode = splitSupplierCode;
	}

	@Column(name="split_supplier_code", nullable=true, length=100)	
	public String getSplitSupplierCode() {
		return splitSupplierCode;
	}

	public void setSplitSupplierName(String splitSupplierName) {
		this.splitSupplierName = splitSupplierName;
	}

	@Column(name="split_supplier_name", nullable=true, length=200)	
	public String getSplitSupplierName() {
		return splitSupplierName;
	}

	public void setSupplierItemId(Integer supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

	@Column(name="supplier_item_id", nullable=true, length=22)
	public Integer getSupplierItemId() {
		return supplierItemId;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	@Column(name="mid", nullable=true, length=22)	
	public Integer getMid() {
		return mid;
	}

	public void setBrandplnLId(Integer brandplnLId) {
		this.brandplnLId = brandplnLId;
	}

	@Column(name="brandpln_l_id", nullable=true, length=22)	
	public Integer getBrandplnLId() {
		return brandplnLId;
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

	@Column(name="last_updated_by", nullable=false, length=22)	
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

	@Column(name="fcs_purchase", nullable=false, length=22)
	public BigDecimal getFcsPurchase() {
		return fcsPurchase;
	}

	public void setFcsPurchase(BigDecimal fcsPurchase) {
		this.fcsPurchase = fcsPurchase;
	}

	@Column(name="fcs_sales", nullable=false, length=22)
	public BigDecimal getFcsSales() {
		return fcsSales;
	}

	public void setFcsSales(BigDecimal fcsSales) {
		this.fcsSales = fcsSales;
	}

	@Column(name="amout_rate_fcspur", nullable=false, length=22)
	public BigDecimal getAmoutRateFcspur() {
		return amoutRateFcspur;
	}

	public void setAmoutRateFcspur(BigDecimal amoutRateFcspur) {
		this.amoutRateFcspur = amoutRateFcspur;
	}

	@Column(name="amout_rate_fcssal", nullable=false, length=22)
	public BigDecimal getAmoutRateFcssal() {
		return amoutRateFcssal;
	}

	public void setAmoutRateFcssal(BigDecimal amoutRateFcssal) {
		this.amoutRateFcssal = amoutRateFcssal;
	}

	@Column(name="split_condition", nullable=true, length=100)
	public String getSplitCondition() {
		return splitCondition;
	}

	public void setSplitCondition(String splitCondition) {
		this.splitCondition = splitCondition;
	}

	@Column(name="supplier_code", nullable=true, length=50)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_name", nullable=true, length=150)
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="total_fcs_purchase", nullable=true, length=22)
	public BigDecimal getTotalFcsPurchase() {
		return totalFcsPurchase;
	}

	public void setTotalFcsPurchase(BigDecimal totalFcsPurchase) {
		this.totalFcsPurchase = totalFcsPurchase;
	}

	@Column(name="total_fcs_sales", nullable=true, length=22)
	public BigDecimal getTotalFcsSales() {
		return totalFcsSales;
	}

	public void setTotalFcsSales(BigDecimal totalFcsSales) {
		this.totalFcsSales = totalFcsSales;
	}

	@Column(name="brand_name_en", nullable=true, length=100)
	public String getBrandNameEn() {
		return brandNameEn;
	}

	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}
}
