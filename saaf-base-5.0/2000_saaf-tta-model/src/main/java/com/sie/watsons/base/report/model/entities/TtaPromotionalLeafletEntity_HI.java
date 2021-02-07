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
 * TtaPromotionalLeafletEntity_HI Entity Object
 * Thu Jul 25 18:54:50 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_promotional_leaflet")
public class TtaPromotionalLeafletEntity_HI {
    private Integer promotionalLeafId;//主键id 关联表:TTA_DM_FULL_LINE
    private Integer dmFullLineId;//外键
    private String promotionalPeriod;//促销期间
    private Integer dmPage;//DM页码
    private String pDisPosition;//促销陈列位置
    private String effectiveArea;//生效区域
    private String mapPosition;//图位
    private String pActivity;//促销活动
    private String productCode;//产品编号
    private String productName;//产品名称
    private String brandName;//品牌名称
    private String activityContent;//活动内容
    private String deptName;//部门名称
    private String categoryCode;//品类编码
    private String categoryName;//品类
    private String supplierCode;//供应商编号
    private String supplierName;//供应商名称
    private String contractStatus;//合作状态
    private String contractYear;//合同年份
    private String contractClause;//合同条款
    private String collStandard;//计收标准
    private String receiAmount;//应收金额
    private String unconfirmVendorCode;//采购确认供应商编号
    private String unconfirmVendorName;//采购确认供应商名称
    private String affirmAmountDm;//采购确认下单金额DM(BIC下单金额)
    private String affirmAmountMkt;//采购确认下单金额MKT cost(Non-trade) (BIC下单金额)
    private String diffValue1;//差异(实收-应收)
    private String diffValue2;//差异(仅计算负数)
    private String purchasingReply;//采购回复(↓BIC筛选收取/折扣收取下单)
    private String exemptionReason;//采购Z列为 "确认不收取"/ "折扣收取" 需填写(↓请选reason code ,如需增加，请通知BIC)
    private String exemptionReason2;//采购Z列为 "已收取" 需填借记单编号/备注
    private String tiFormNo;//TI Form NO.
    private String bicRemark;//BIC备注
    private String contractMasterDept;//Contract master department
    private String itemMasterDept;//Item master deparment
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

	public void setPromotionalLeafId(Integer promotionalLeafId) {
		this.promotionalLeafId = promotionalLeafId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PROMOTIONAL_LEAFLET", sequenceName = "SEQ_PROMOTIONAL_LEAFLET", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PROMOTIONAL_LEAFLET", strategy = GenerationType.SEQUENCE)
	@Column(name="promotional_leaf_id", nullable=false, length=22)	
	public Integer getPromotionalLeafId() {
		return promotionalLeafId;
	}

	public void setDmFullLineId(Integer dmFullLineId) {
		this.dmFullLineId = dmFullLineId;
	}

	@Column(name="dm_full_line_id", nullable=true, length=22)	
	public Integer getDmFullLineId() {
		return dmFullLineId;
	}

	public void setPromotionalPeriod(String promotionalPeriod) {
		this.promotionalPeriod = promotionalPeriod;
	}

	@Column(name="promotional_period", nullable=true, length=100)	
	public String getPromotionalPeriod() {
		return promotionalPeriod;
	}

	public void setDmPage(Integer dmPage) {
		this.dmPage = dmPage;
	}

	@Column(name="dm_page", nullable=true, length=22)	
	public Integer getDmPage() {
		return dmPage;
	}

	public void setPDisPosition(String pDisPosition) {
		this.pDisPosition = pDisPosition;
	}

	@Column(name="p_dis_position", nullable=true, length=50)	
	public String getPDisPosition() {
		return pDisPosition;
	}

	public void setEffectiveArea(String effectiveArea) {
		this.effectiveArea = effectiveArea;
	}

	@Column(name="effective_area", nullable=true, length=60)	
	public String getEffectiveArea() {
		return effectiveArea;
	}

	public void setMapPosition(String mapPosition) {
		this.mapPosition = mapPosition;
	}

	@Column(name="map_position", nullable=true, length=100)	
	public String getMapPosition() {
		return mapPosition;
	}

	public void setPActivity(String pActivity) {
		this.pActivity = pActivity;
	}

	@Column(name="p_activity", nullable=true, length=255)	
	public String getPActivity() {
		return pActivity;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Column(name="product_code", nullable=true, length=255)	
	public String getProductCode() {
		return productCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="product_name", nullable=true, length=255)	
	public String getProductName() {
		return productName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name="brand_name", nullable=true, length=255)	
	public String getBrandName() {
		return brandName;
	}

	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}

	@Column(name="activity_content", nullable=true, length=200)	
	public String getActivityContent() {
		return activityContent;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=100)	
	public String getDeptName() {
		return deptName;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Column(name="category_code", nullable=true, length=100)	
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Column(name="category_name", nullable=true, length=100)	
	public String getCategoryName() {
		return categoryName;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=200)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=255)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	@Column(name="contract_status", nullable=true, length=50)	
	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractYear(String contractYear) {
		this.contractYear = contractYear;
	}

	@Column(name="contract_year", nullable=true, length=60)	
	public String getContractYear() {
		return contractYear;
	}

	public void setContractClause(String contractClause) {
		this.contractClause = contractClause;
	}

	@Column(name="contract_clause", nullable=true, length=100)	
	public String getContractClause() {
		return contractClause;
	}

	public void setCollStandard(String collStandard) {
		this.collStandard = collStandard;
	}

	@Column(name="coll_standard", nullable=true, length=100)	
	public String getCollStandard() {
		return collStandard;
	}

	public void setReceiAmount(String receiAmount) {
		this.receiAmount = receiAmount;
	}

	@Column(name="recei_amount", nullable=true, length=50)	
	public String getReceiAmount() {
		return receiAmount;
	}

	public void setUnconfirmVendorCode(String unconfirmVendorCode) {
		this.unconfirmVendorCode = unconfirmVendorCode;
	}

	@Column(name="unconfirm_vendor_code", nullable=true, length=100)	
	public String getUnconfirmVendorCode() {
		return unconfirmVendorCode;
	}

	public void setUnconfirmVendorName(String unconfirmVendorName) {
		this.unconfirmVendorName = unconfirmVendorName;
	}

	@Column(name="unconfirm_vendor_name", nullable=true, length=255)	
	public String getUnconfirmVendorName() {
		return unconfirmVendorName;
	}

	public void setAffirmAmountDm(String affirmAmountDm) {
		this.affirmAmountDm = affirmAmountDm;
	}

	@Column(name="affirm_amount_dm", nullable=true, length=50)	
	public String getAffirmAmountDm() {
		return affirmAmountDm;
	}

	public void setAffirmAmountMkt(String affirmAmountMkt) {
		this.affirmAmountMkt = affirmAmountMkt;
	}

	@Column(name="affirm_amount_mkt", nullable=true, length=50)	
	public String getAffirmAmountMkt() {
		return affirmAmountMkt;
	}

	public void setDiffValue1(String diffValue1) {
		this.diffValue1 = diffValue1;
	}

	@Column(name="diff_value1", nullable=true, length=60)	
	public String getDiffValue1() {
		return diffValue1;
	}

	public void setDiffValue2(String diffValue2) {
		this.diffValue2 = diffValue2;
	}

	@Column(name="diff_value2", nullable=true, length=60)	
	public String getDiffValue2() {
		return diffValue2;
	}

	public void setPurchasingReply(String purchasingReply) {
		this.purchasingReply = purchasingReply;
	}

	@Column(name="purchasing_reply", nullable=true, length=4000)	
	public String getPurchasingReply() {
		return purchasingReply;
	}

	public void setExemptionReason(String exemptionReason) {
		this.exemptionReason = exemptionReason;
	}

	@Column(name="exemption_reason", nullable=true, length=255)	
	public String getExemptionReason() {
		return exemptionReason;
	}

	public void setExemptionReason2(String exemptionReason2) {
		this.exemptionReason2 = exemptionReason2;
	}

	@Column(name="exemption_reason2", nullable=true, length=255)	
	public String getExemptionReason2() {
		return exemptionReason2;
	}

	public void setTiFormNo(String tiFormNo) {
		this.tiFormNo = tiFormNo;
	}

	@Column(name="ti_form_no", nullable=true, length=130)	
	public String getTiFormNo() {
		return tiFormNo;
	}

	public void setBicRemark(String bicRemark) {
		this.bicRemark = bicRemark;
	}

	@Column(name="bic_remark", nullable=true, length=1000)	
	public String getBicRemark() {
		return bicRemark;
	}

	public void setContractMasterDept(String contractMasterDept) {
		this.contractMasterDept = contractMasterDept;
	}

	@Column(name="contract_master_dept", nullable=true, length=255)	
	public String getContractMasterDept() {
		return contractMasterDept;
	}

	public void setItemMasterDept(String itemMasterDept) {
		this.itemMasterDept = itemMasterDept;
	}

	@Column(name="item_master_dept", nullable=true, length=255)	
	public String getItemMasterDept() {
		return itemMasterDept;
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


	@Column(name="promotion_guideline_id", nullable=true, length=22)
	public Integer getPromotionGuidelineId() {
		return promotionGuidelineId;
	}

	public void setPromotionGuidelineId(Integer promotionGuidelineId) {
		this.promotionGuidelineId = promotionGuidelineId;
	}

	@Column(name="sales_site_id", nullable=false, length=22)
	public Integer getSalesSiteId() {
		return salesSiteId;
	}

	public void setSalesSiteId(Integer salesSiteId) {
		this.salesSiteId = salesSiteId;
	}
}
