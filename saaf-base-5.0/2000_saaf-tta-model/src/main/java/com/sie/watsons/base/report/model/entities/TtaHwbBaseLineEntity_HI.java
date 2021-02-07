package com.sie.watsons.base.report.model.entities;

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
 * TtaHwbBaseLineEntity_HI Entity Object
 * Thu Nov 28 12:20:45 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_HWB_BASE_LINE")
public class TtaHwbBaseLineEntity_HI {
    private Integer hwbBaseLineId;
    private String itemCode;
    private Integer storesNum;
    private Integer timeDimension;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer hwbYear;
    private String promotionLocation;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;
    private String promotionSection;
    private String isCreate;
    private String deptNameMajor;
    private String descriptionCn;
    private String awardDescription;
    private String groupDesc;
    private String deptDesc;
    private String brandCn;
    private String vendorCode;
    private String vendorName;
    private Integer companyStandard;
    private Integer agreementStandard;
    private String chargeStandards;
    private Integer num;
    private String hwbType;
    private Integer operatorUserId;

	public void setHwbBaseLineId(Integer hwbBaseLineId) {
		this.hwbBaseLineId = hwbBaseLineId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_HWB_BASE_LINE", sequenceName = "SEQ_TTA_HWB_BASE_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_HWB_BASE_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="hwb_base_line_id", nullable=false, length=22)	
	public Integer getHwbBaseLineId() {
		return hwbBaseLineId;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=200)	
	public String getItemCode() {
		return itemCode;
	}

	public void setStoresNum(Integer storesNum) {
		this.storesNum = storesNum;
	}

	@Column(name="stores_num", nullable=true, length=22)	
	public Integer getStoresNum() {
		return storesNum;
	}

	public void setTimeDimension(Integer timeDimension) {
		this.timeDimension = timeDimension;
	}

	@Column(name="time_dimension", nullable=true, length=22)	
	public Integer getTimeDimension() {
		return timeDimension;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	@Column(name="hwb_year")
	public Integer getHwbYear() {
		return hwbYear;
	}

	public void setHwbYear(Integer hwbYear) {
		this.hwbYear = hwbYear;
	}

	public void setPromotionLocation(String promotionLocation) {
		this.promotionLocation = promotionLocation;
	}

	@Column(name="promotion_location", nullable=true, length=200)	
	public String getPromotionLocation() {
		return promotionLocation;
	}

	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	@Column(name="promotion_end_date", nullable=true, length=7)	
	public Date getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	@Column(name="promotion_start_date", nullable=true, length=7)	
	public Date getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionSection(String promotionSection) {
		this.promotionSection = promotionSection;
	}

	@Column(name="promotion_section", nullable=true, length=200)	
	public String getPromotionSection() {
		return promotionSection;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	@Column(name="is_create", nullable=true, length=20)	
	public String getIsCreate() {
		return isCreate;
	}

	public void setDeptNameMajor(String deptNameMajor) {
		this.deptNameMajor = deptNameMajor;
	}

	@Column(name="dept_name_major", nullable=true, length=200)	
	public String getDeptNameMajor() {
		return deptNameMajor;
	}

	public void setDescriptionCn(String descriptionCn) {
		this.descriptionCn = descriptionCn;
	}

	@Column(name="description_cn", nullable=true, length=2000)	
	public String getDescriptionCn() {
		return descriptionCn;
	}

	public void setAwardDescription(String awardDescription) {
		this.awardDescription = awardDescription;
	}

	@Column(name="award_description", nullable=true, length=2000)	
	public String getAwardDescription() {
		return awardDescription;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=500)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=500)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=500)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	@Column(name="vendor_code", nullable=true, length=500)	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="vendor_name", nullable=true, length=500)	
	public String getVendorName() {
		return vendorName;
	}

	public void setCompanyStandard(Integer companyStandard) {
		this.companyStandard = companyStandard;
	}

	@Column(name="company_standard", nullable=true, length=22)	
	public Integer getCompanyStandard() {
		return companyStandard;
	}

	public void setAgreementStandard(Integer agreementStandard) {
		this.agreementStandard = agreementStandard;
	}

	@Column(name="agreement_standard", nullable=true, length=22)	
	public Integer getAgreementStandard() {
		return agreementStandard;
	}

	public void setChargeStandards(String chargeStandards) {
		this.chargeStandards = chargeStandards;
	}

	@Column(name="charge_standards", nullable=true, length=100)	
	public String getChargeStandards() {
		return chargeStandards;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name="num", nullable=true, length=22)	
	public Integer getNum() {
		return num;
	}

	public void setHwbType(String hwbType) {
		this.hwbType = hwbType;
	}

	@Column(name="hwb_type", nullable=true, length=100)	
	public String getHwbType() {
		return hwbType;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
