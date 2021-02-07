package com.sie.watsons.base.report.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaPromotionalLeafletEntity_HI_RO Entity Object
 * Thu Jul 25 18:54:50 CST 2019  Auto Generate
 */

public class TtaPromotionalLeafletEntity_HI_RO {

	//DW (promotional leaflet) 报表
	public static final String QUERY_DW_REPORT_SELECT ="select \n" +
            "tplef.promotional_leaf_id as promotionalLeafId,\n" +
            "pg.prom_number as promotionalPeriod,--促销期间\n" +
            "tdfl.dm_page as dmPage, \n" +
            "pg.osd as pDisPosition,--促销陈列位置\n" +
            "get_lookup_desc('REGIONAL_COR',1,tdfl.effective_area) as effectiveArea, --生效区域\n" +
            "get_lookup_desc('MAP_UNIT_VALUES',1,tdfl.bmp) as mapPosition,--图位\n" +
            "tdfl.dm_pament as pActivity,--促销活动\n" +
            "pg.itemcode as productCode, --产品编码\n" +
            "item.item_desc_cn as productName,--产品名称\n" +
            "item.brand_cn as brandName, --品牌\n" +
            "pg.prom_number||tdfl.dm_page as activityContent,--活动内容\n" +
            "item.group_desc  as deptName,--部门\n" +
            "pg.dept as categoryCode,--category code\n" +
            "item.DEPT_DESC as categoryName, --category(品类)\n" +
            "item.vendor_nbr as supplierCode, --供应商编号\n" +
            "item.vendor_name as supplierName, --供应商名称\n" +
            "'合作状态在供应商那里添加一个字段' as contractStatus, \n" +
            "proposal.proposal_year as contractYear,--合同年份\n" +
            "fee.line_code as contractClause,--合同条款\n" +
            "CASE WHEN fee.STANDARD_VALUE1 is null  THEN '部门标准' ELSE '其他协议标准' END  CHARGE_STANDARDS, --计费标准\n" +
            "tplef.recei_amount as receiAmount,  --应收金额(页码 * 单位的值 代码中计算)\n" +
            "tplef.unconfirm_vendor_code as unconfirmVendorCode,\n" +
            "tplef.unconfirm_vendor_name as unconfirmVendorName,\n" +
            "tplef.affirm_amount_dm as affirmAmountDm,\n" +
            "tplef.affirm_amount_mkt asaffirmAmountMkt,\n" +
            "tplef.diff_value1 as diffValue1,\n" +
            "tplef.diff_value2 as diffValue2,\n" +
            "tplef.purchasing_reply as purchasingReply,\n" +
            "tplef.exemption_reason as exemptionReason,\n" +
            "tplef.exemption_reason2 as exemptionReason2,\n" +
            "tplef.ti_form_no as tiFormNo,\n" +
            "tplef.bic_remark as bicRemark,\n" +
            "tplef.contract_master_dept as contractMasterDept,\n" +
            "item.DEPT_DESC as itemMasterDept,\n" +
            "base.meaning as meaning,\n" +
            "fee.standard_value1 as standardValue1 \n" +
            "from tta_promotional_leaflet tplef\n" +
            "left join tta_promotion_guideline pg\n" +
            "on tplef.promotion_guideline_id = pg.osd_id\n" +
            "left join tta_dm_full_line tdfl \n" +
            "on tplef.dm_full_line_id = tdfl.dm_full_line_id\n" +
            "left join tta_item item\n" +
            "on pg.ITEMCODE = item.ITEM_NBR\n" +
            "left join tta_contract_line tcl on item.vendor_nbr = tcl.vendor_code and \n" +
            "tcl.contract_l_id =(select max(c.contract_l_id) from tta_contract_line c where c.vendor_code = item.vendor_nbr)\n" +
            "left join tta_proposal_header proposal on tcl.proposal_id = proposal.proposal_id\n" +
            "left join TTA_DEPT_FEE fee\n" +
            "on fee.PROPOSAL_ID = proposal.PROPOSAL_ID \n" +
            "LEFT JOIN (SELECT a.lookup_type lookupType,\n" +
            "a.lookup_code lookupCode,a.meaning meaning\n" +
            "FROM base_lookup_values a LEFT JOIN base_lookup_values b on a.parent_lookup_values_id = b.lookup_values_id\n" +
            "where a.delete_flag='0'  and a.system_code = 'BASE' and a.lookup_type = 'UNIT'  ) base \n" +
            "on BASE.lookupCode = fee.unit1 \n" +
            "where proposal.PROPOSAL_YEAR = '2019'  \n" +
            "and proposal.VERSION_STATUS = 1 ";

	//以上年份等条件需要动态传参

    private Integer promotionalLeafId;
    private Integer dmFullLineId;
    private String promotionalPeriod;
    private Integer dmPage;
    private String pDisPosition;
    private String effectiveArea;
    private String mapPosition;
    private String pActivity;
    private String productCode;
    private String productName;
    private String brandName;
    private String activityContent;
    private String deptName;
    private String categoryCode;
    private String categoryName;
    private String supplierCode;
    private String supplierName;
    private String contractStatus;
    private String contractYear;
    private String contractClause;
    private String collStandard;
    private String receiAmount;
    private String unconfirmVendorCode;
    private String unconfirmVendorName;
    private String affirmAmountDm;
    private String affirmAmountMkt;
    private String diffValue1;
    private String diffValue2;
    private String purchasingReply;
    private String exemptionReason;
    private String exemptionReason2;
    private String tiFormNo;
    private String bicRemark;
    private String contractMasterDept;
    private String itemMasterDept;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	private Integer promotionGuidelineId;//外键:表-->TTA_PROMOTION_GUIDELINE
	private Integer salesSiteId;//外键:表 -- >TTA_OSD_SALES_SITE

	private String meaning;
	private String standardValue1;//协定标准值1

	public void setPromotionalLeafId(Integer promotionalLeafId) {
		this.promotionalLeafId = promotionalLeafId;
	}

	
	public Integer getPromotionalLeafId() {
		return promotionalLeafId;
	}

	public void setDmFullLineId(Integer dmFullLineId) {
		this.dmFullLineId = dmFullLineId;
	}

	
	public Integer getDmFullLineId() {
		return dmFullLineId;
	}

	public void setPromotionalPeriod(String promotionalPeriod) {
		this.promotionalPeriod = promotionalPeriod;
	}

	
	public String getPromotionalPeriod() {
		return promotionalPeriod;
	}

	public void setDmPage(Integer dmPage) {
		this.dmPage = dmPage;
	}

	
	public Integer getDmPage() {
		return dmPage;
	}

	public void setPDisPosition(String pDisPosition) {
		this.pDisPosition = pDisPosition;
	}

	
	public String getPDisPosition() {
		return pDisPosition;
	}

	public void setEffectiveArea(String effectiveArea) {
		this.effectiveArea = effectiveArea;
	}

	
	public String getEffectiveArea() {
		return effectiveArea;
	}

	public void setMapPosition(String mapPosition) {
		this.mapPosition = mapPosition;
	}

	
	public String getMapPosition() {
		return mapPosition;
	}

	public void setPActivity(String pActivity) {
		this.pActivity = pActivity;
	}

	
	public String getPActivity() {
		return pActivity;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	public String getProductCode() {
		return productCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	
	public String getBrandName() {
		return brandName;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	
	public String getActivityContent() {
		return activityContent;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	public String getCategoryName() {
		return categoryName;
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

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	
	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractYear(String contractYear) {
		this.contractYear = contractYear;
	}

	
	public String getContractYear() {
		return contractYear;
	}

	public void setContractClause(String contractClause) {
		this.contractClause = contractClause;
	}

	
	public String getContractClause() {
		return contractClause;
	}

	public void setCollStandard(String collStandard) {
		this.collStandard = collStandard;
	}

	
	public String getCollStandard() {
		return collStandard;
	}

	public void setReceiAmount(String receiAmount) {
		this.receiAmount = receiAmount;
	}

	
	public String getReceiAmount() {
		return receiAmount;
	}

	public void setUnconfirmVendorCode(String unconfirmVendorCode) {
		this.unconfirmVendorCode = unconfirmVendorCode;
	}

	
	public String getUnconfirmVendorCode() {
		return unconfirmVendorCode;
	}

	public void setUnconfirmVendorName(String unconfirmVendorName) {
		this.unconfirmVendorName = unconfirmVendorName;
	}

	
	public String getUnconfirmVendorName() {
		return unconfirmVendorName;
	}

	public void setAffirmAmountDm(String affirmAmountDm) {
		this.affirmAmountDm = affirmAmountDm;
	}

	
	public String getAffirmAmountDm() {
		return affirmAmountDm;
	}

	public void setAffirmAmountMkt(String affirmAmountMkt) {
		this.affirmAmountMkt = affirmAmountMkt;
	}

	
	public String getAffirmAmountMkt() {
		return affirmAmountMkt;
	}

	public void setDiffValue1(String diffValue1) {
		this.diffValue1 = diffValue1;
	}

	
	public String getDiffValue1() {
		return diffValue1;
	}

	public void setDiffValue2(String diffValue2) {
		this.diffValue2 = diffValue2;
	}

	
	public String getDiffValue2() {
		return diffValue2;
	}

	public void setPurchasingReply(String purchasingReply) {
		this.purchasingReply = purchasingReply;
	}

	
	public String getPurchasingReply() {
		return purchasingReply;
	}

	public void setExemptionReason(String exemptionReason) {
		this.exemptionReason = exemptionReason;
	}

	
	public String getExemptionReason() {
		return exemptionReason;
	}

	public void setExemptionReason2(String exemptionReason2) {
		this.exemptionReason2 = exemptionReason2;
	}

	
	public String getExemptionReason2() {
		return exemptionReason2;
	}

	public void setTiFormNo(String tiFormNo) {
		this.tiFormNo = tiFormNo;
	}

	
	public String getTiFormNo() {
		return tiFormNo;
	}

	public void setBicRemark(String bicRemark) {
		this.bicRemark = bicRemark;
	}

	
	public String getBicRemark() {
		return bicRemark;
	}

	public void setContractMasterDept(String contractMasterDept) {
		this.contractMasterDept = contractMasterDept;
	}

	
	public String getContractMasterDept() {
		return contractMasterDept;
	}

	public void setItemMasterDept(String itemMasterDept) {
		this.itemMasterDept = itemMasterDept;
	}

	
	public String getItemMasterDept() {
		return itemMasterDept;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getPromotionGuidelineId() {
		return promotionGuidelineId;
	}

	public void setPromotionGuidelineId(Integer promotionGuidelineId) {
		this.promotionGuidelineId = promotionGuidelineId;
	}

	public Integer getSalesSiteId() {
		return salesSiteId;
	}

	public void setSalesSiteId(Integer salesSiteId) {
		this.salesSiteId = salesSiteId;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getStandardValue1() {
		return standardValue1;
	}

	public void setStandardValue1(String standardValue1) {
		this.standardValue1 = standardValue1;
	}
}
