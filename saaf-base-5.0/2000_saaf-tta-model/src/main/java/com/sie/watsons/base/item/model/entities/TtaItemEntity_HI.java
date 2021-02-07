package com.sie.watsons.base.item.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaItemEntity_HI Entity Object
 * Sat Aug 03 16:55:13 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_item")
public class TtaItemEntity_HI {
    private Integer itemId;
    private String itemNbr;
    private String itemDescCn;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String itemDescEn;
    private String status;
    private String retailGroup;
    private java.math.BigDecimal unitCost;
    private Integer vendorNbr;
    private String vendorName;
    private Integer deptCode;
    private String deptDesc;
    private Integer groupCode;
    private String groupDesc;
    private Integer classCode;
    private String classDesc;
    private Integer subClassCode;
    private String subClassDesc;
    private String upc;
    private String brandCode;
    private String brandCn;
    private String brandEn;
    private String orginalPlace;
    private String specs;
    private String unit;
    private String uda4;
    private String uda6;
    private String uda8;
    private String uda29;
    private String uda41;
    private String uda49;
    private String uda62;
    private String uda63;
    private String uda666;
    private String uda13;
    private String uda64;
    private String uda50;
    private String uda51;
    private String sourceSystem;
    private String sourceCode;
    private java.math.BigDecimal packSize;
    private String remark;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	@Id
	@SequenceGenerator(name = "SEQ_TTA_ITEM", sequenceName = "SEQ_TTA_ITEM", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_ITEM", strategy = GenerationType.SEQUENCE)
	@Column(name="item_id", nullable=false, length=22)	
	public Integer getItemId() {
		return itemId;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	@Column(name="item_nbr", nullable=false, length=50)	
	public String getItemNbr() {
		return itemNbr;
	}

	public void setItemDescCn(String itemDescCn) {
		this.itemDescCn = itemDescCn;
	}

	@Column(name="item_desc_cn", nullable=true, length=800)	
	public String getItemDescCn() {
		return itemDescCn;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="start_date", nullable=true, length=7)	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=true, length=7)	
	public Date getEndDate() {
		return endDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="create_date", nullable=true, length=7)	
	public Date getCreateDate() {
		return createDate;
	}

	public void setItemDescEn(String itemDescEn) {
		this.itemDescEn = itemDescEn;
	}

	@Column(name="item_desc_en", nullable=true, length=500)	
	public String getItemDescEn() {
		return itemDescEn;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=5)	
	public String getStatus() {
		return status;
	}

	public void setRetailGroup(String retailGroup) {
		this.retailGroup = retailGroup;
	}

	@Column(name="retail_group", nullable=true, length=100)	
	public String getRetailGroup() {
		return retailGroup;
	}

	public void setUnitCost(java.math.BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	@Column(name="unit_cost", nullable=true, length=22)	
	public java.math.BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setVendorNbr(Integer vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="vendor_nbr", nullable=true, length=22)	
	public Integer getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=150)	
	public String getVendorName() {
		return vendorName;
	}

	public void setDeptCode(Integer deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=22)	
	public Integer getDeptCode() {
		return deptCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=50)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=22)	
	public Integer getGroupCode() {
		return groupCode;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=100)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}

	@Column(name="class_code", nullable=true, length=22)	
	public Integer getClassCode() {
		return classCode;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	@Column(name="class_desc", nullable=true, length=100)	
	public String getClassDesc() {
		return classDesc;
	}

	public void setSubClassCode(Integer subClassCode) {
		this.subClassCode = subClassCode;
	}

	@Column(name="sub_class_code", nullable=true, length=22)	
	public Integer getSubClassCode() {
		return subClassCode;
	}

	public void setSubClassDesc(String subClassDesc) {
		this.subClassDesc = subClassDesc;
	}

	@Column(name="sub_class_desc", nullable=true, length=500)	
	public String getSubClassDesc() {
		return subClassDesc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	@Column(name="upc", nullable=true, length=100)	
	public String getUpc() {
		return upc;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name="brand_code", nullable=true, length=100)	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=500)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=500)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setOrginalPlace(String orginalPlace) {
		this.orginalPlace = orginalPlace;
	}

	@Column(name="orginal_place", nullable=true, length=500)	
	public String getOrginalPlace() {
		return orginalPlace;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	@Column(name="specs", nullable=true, length=500)	
	public String getSpecs() {
		return specs;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=500)	
	public String getUnit() {
		return unit;
	}

	public void setUda4(String uda4) {
		this.uda4 = uda4;
	}

	@Column(name="uda4", nullable=true, length=500)	
	public String getUda4() {
		return uda4;
	}

	public void setUda6(String uda6) {
		this.uda6 = uda6;
	}

	@Column(name="uda6", nullable=true, length=500)	
	public String getUda6() {
		return uda6;
	}

	public void setUda8(String uda8) {
		this.uda8 = uda8;
	}

	@Column(name="uda8", nullable=true, length=500)	
	public String getUda8() {
		return uda8;
	}

	public void setUda29(String uda29) {
		this.uda29 = uda29;
	}

	@Column(name="uda29", nullable=true, length=500)	
	public String getUda29() {
		return uda29;
	}

	public void setUda41(String uda41) {
		this.uda41 = uda41;
	}

	@Column(name="uda41", nullable=true, length=500)	
	public String getUda41() {
		return uda41;
	}

	public void setUda49(String uda49) {
		this.uda49 = uda49;
	}

	@Column(name="uda49", nullable=true, length=500)	
	public String getUda49() {
		return uda49;
	}

	public void setUda62(String uda62) {
		this.uda62 = uda62;
	}

	@Column(name="uda62", nullable=true, length=500)	
	public String getUda62() {
		return uda62;
	}

	public void setUda63(String uda63) {
		this.uda63 = uda63;
	}

	@Column(name="uda63", nullable=true, length=500)	
	public String getUda63() {
		return uda63;
	}

	public void setUda666(String uda666) {
		this.uda666 = uda666;
	}

	@Column(name="uda666", nullable=true, length=500)	
	public String getUda666() {
		return uda666;
	}

	public void setUda13(String uda13) {
		this.uda13 = uda13;
	}

	@Column(name="uda13", nullable=true, length=500)	
	public String getUda13() {
		return uda13;
	}

	public void setUda64(String uda64) {
		this.uda64 = uda64;
	}

	@Column(name="uda64", nullable=true, length=500)	
	public String getUda64() {
		return uda64;
	}

	public void setUda50(String uda50) {
		this.uda50 = uda50;
	}

	@Column(name="uda50", nullable=true, length=500)	
	public String getUda50() {
		return uda50;
	}

	public void setUda51(String uda51) {
		this.uda51 = uda51;
	}

	@Column(name="uda51", nullable=true, length=500)	
	public String getUda51() {
		return uda51;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	@Column(name="source_system", nullable=true, length=500)	
	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Column(name="source_code", nullable=true, length=500)	
	public String getSourceCode() {
		return sourceCode;
	}

	public void setPackSize(java.math.BigDecimal packSize) {
		this.packSize = packSize;
	}

	@Column(name="pack_size", nullable=true, length=22)	
	public java.math.BigDecimal getPackSize() {
		return packSize;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=500)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
