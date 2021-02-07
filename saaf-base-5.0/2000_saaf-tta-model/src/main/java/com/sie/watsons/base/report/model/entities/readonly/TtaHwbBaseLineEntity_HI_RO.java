package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaHwbBaseLineEntity_HI_RO Entity Object
 * Thu Nov 28 12:20:45 CST 2019  Auto Generate
 */

public class TtaHwbBaseLineEntity_HI_RO {

	public static final String  QUERY = " SELECT tobl.hwb_base_line_id,\n" +
			"        tobl.item_code,\n" +
			"        tobl.stores_num,\n" +
			"        tobl.time_dimension,\n" +
			"        tobl.created_by,\n" +
			"        tobl.last_updated_by,\n" +
			"        tobl.last_update_date,\n" +
			"        tobl.creation_date,\n" +
			"        tobl.last_update_login,\n" +
			"        tobl.version_num,\n" +
			"        tobl.hwb_year,\n" +
			"        tobl.promotion_location,\n" +
			"        tobl.promotion_end_date,\n" +
			"        tobl.promotion_start_date,\n" +
			"        tobl.promotion_section,\n" +
			"        NVL(tobl.is_create,'N') is_create,\n" +
			"        tobl.dept_name_major,\n" +
			"        tobl.description_cn,\n" +
			"        tobl.award_description,\n" +
			"        tobl.group_desc,\n" +
			"        tobl.dept_desc,\n" +
			"        tobl.brand_cn,\n" +
			"        tobl.vendor_code,\n" +
			"        tobl.vendor_name,\n" +
			"        tobl.company_standard,\n" +
			"        tobl.agreement_standard,\n" +
			"        tobl.charge_standards,\n" +
			"        tobl.num,\n" +
			"        tobl.hwb_type,\n" +
			"        bu.user_full_name createdByName\n" +
			"        \n" +
			"         FROM  tta_hwb_base_line tobl\n" +
			"         left join base_users bu on tobl.created_by = bu.user_id\n" +
			"         \n" +
			"         where 1=1 ";
	public static final String  CREATE_QUERY =  "select *\n" +
			"  from (select tob.hwb_base_line_id,\n" +
			"               tob.promotion_section,\n" +
			"               nvl(tob.is_create, 'N') is_create,\n" +
			"               row_number() over(partition by tob.promotion_section order by nvl(tob.is_create, 'N') desc) rn\n" +
			"          from tta_hwb_base_line tob) tobl\n" +
			" where tobl.rn = 1 ";
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
	private String createdByName;
    private Integer operatorUserId;

	public void setHwbBaseLineId(Integer hwbBaseLineId) {
		this.hwbBaseLineId = hwbBaseLineId;
	}

	
	public Integer getHwbBaseLineId() {
		return hwbBaseLineId;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	
	public String getItemCode() {
		return itemCode;
	}

	public void setStoresNum(Integer storesNum) {
		this.storesNum = storesNum;
	}

	
	public Integer getStoresNum() {
		return storesNum;
	}

	public void setTimeDimension(Integer timeDimension) {
		this.timeDimension = timeDimension;
	}

	
	public Integer getTimeDimension() {
		return timeDimension;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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

	public Integer getHwbYear() {
		return hwbYear;
	}

	public void setHwbYear(Integer hwbYear) {
		this.hwbYear = hwbYear;
	}

	public void setPromotionLocation(String promotionLocation) {
		this.promotionLocation = promotionLocation;
	}

	
	public String getPromotionLocation() {
		return promotionLocation;
	}

	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	
	public Date getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	
	public Date getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionSection(String promotionSection) {
		this.promotionSection = promotionSection;
	}

	
	public String getPromotionSection() {
		return promotionSection;
	}

	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	
	public String getIsCreate() {
		return isCreate;
	}

	public void setDeptNameMajor(String deptNameMajor) {
		this.deptNameMajor = deptNameMajor;
	}

	
	public String getDeptNameMajor() {
		return deptNameMajor;
	}

	public void setDescriptionCn(String descriptionCn) {
		this.descriptionCn = descriptionCn;
	}

	
	public String getDescriptionCn() {
		return descriptionCn;
	}

	public void setAwardDescription(String awardDescription) {
		this.awardDescription = awardDescription;
	}

	
	public String getAwardDescription() {
		return awardDescription;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	
	public String getBrandCn() {
		return brandCn;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	
	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	
	public String getVendorName() {
		return vendorName;
	}

	public void setCompanyStandard(Integer companyStandard) {
		this.companyStandard = companyStandard;
	}

	
	public Integer getCompanyStandard() {
		return companyStandard;
	}

	public void setAgreementStandard(Integer agreementStandard) {
		this.agreementStandard = agreementStandard;
	}

	
	public Integer getAgreementStandard() {
		return agreementStandard;
	}

	public void setChargeStandards(String chargeStandards) {
		this.chargeStandards = chargeStandards;
	}

	
	public String getChargeStandards() {
		return chargeStandards;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	
	public Integer getNum() {
		return num;
	}

	public void setHwbType(String hwbType) {
		this.hwbType = hwbType;
	}

	
	public String getHwbType() {
		return hwbType;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
}
