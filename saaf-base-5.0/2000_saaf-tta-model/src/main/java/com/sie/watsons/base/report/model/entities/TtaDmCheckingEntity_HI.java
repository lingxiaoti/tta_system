package com.sie.watsons.base.report.model.entities;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.common.bean.FieldDesc;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TtaDmCheckingEntity_HI Entity Object
 * Mon Nov 18 20:28:25 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_dm_checking")
public class TtaDmCheckingEntity_HI {
    private Integer dmId;
    private String durationPeriod;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;
    private String dmPage;
    private String locationCode;
    private String effectiveArea;
    private Integer effectiveAreaCnt;
    private String mapPosition;
    private String acceptUnit;
    private String itemNbr;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer yearMonth;
    private String content;
    private String groupCode;
    private String deptDesc;
    private String itemCn;
    private String brandCn;
    private String priorVendorCode;
    private String priorVendorName;
    private String contractYear;
    private String contractStatus;
    private String chargeStandards;
    private BigDecimal chargeMoney;
    private BigDecimal receiveAmount;
    private BigDecimal unconfirmAmount;
    private BigDecimal advanceAmount;
    private String advanceCode;
    private BigDecimal difference;
    private BigDecimal noDifference;
    private String collect;
    private String purchase;
    private String exemptionReason;
    private String exemptionReason2;
    private String debitOrderCode;
    private BigDecimal receipts;
    private Integer status;
    private Integer parentId;
    private String parentVendorCode;
    private String parentVendorName;
    private String batchCode;
    private String contractTerms;
    private Integer transferOutPerson;
    private Integer transferLastPerson;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferInDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferOutDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;
    private Integer transferInPerson;
    private Integer operatorUserId;
	private String groupDesc;
	private BigDecimal additionRate;
	private Integer processId; //流程ID

	private Integer adjustBy;
	private BigDecimal adjustAmount;
	private BigDecimal adjustReceiveAmount;

	private String remark;
	private BigDecimal noReceiveAmount; //应收金额（不含加成）
	private BigDecimal noUnconfirmAmount; //需采购确认收取金额（不含加成）
	private String type;
	private BigDecimal noAdjustAmount	;// 调整实收金额（不含加成手动录入）
	private BigDecimal noAdjustReceiveAmount; //调整应收金额（不含加成）



	public void setDmId(Integer dmId) {
		this.dmId = dmId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_DM_CHECKING", sequenceName = "SEQ_TTA_DM_CHECKING", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_DM_CHECKING", strategy = GenerationType.SEQUENCE)
	@Column(name="dm_id", nullable=false, length=22)
	public Integer getDmId() {
		return dmId;
	}

	public void setDurationPeriod(String durationPeriod) {
		this.durationPeriod = durationPeriod;
	}

	@Column(name="duration_period", nullable=true, length=500)	
	public String getDurationPeriod() {
		return durationPeriod;
	}

	public void setPromotionStartDate(Date promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	@Column(name="promotion_start_date", nullable=true, length=7)	
	public Date getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionEndDate(Date promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	@Column(name="promotion_end_date", nullable=true, length=7)	
	public Date getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setDmPage(String dmPage) {
		this.dmPage = dmPage;
	}

	@Column(name="dm_page", nullable=true, length=22)	
	public String getDmPage() {
		return dmPage;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Column(name="location_code", nullable=true, length=500)	
	public String getLocationCode() {
		return locationCode;
	}

	public void setEffectiveArea(String effectiveArea) {
		this.effectiveArea = effectiveArea;
	}

	@Column(name="effective_area", nullable=true, length=500)	
	public String getEffectiveArea() {
		return effectiveArea;
	}

	public void setEffectiveAreaCnt(Integer effectiveAreaCnt) {
		this.effectiveAreaCnt = effectiveAreaCnt;
	}

	@Column(name="effective_area_cnt", nullable=true, length=22)	
	public Integer getEffectiveAreaCnt() {
		return effectiveAreaCnt;
	}

	public void setMapPosition(String mapPosition) {
		this.mapPosition = mapPosition;
	}

	@Column(name="map_position", nullable=true, length=500)	
	public String getMapPosition() {
		return mapPosition;
	}

	public void setAcceptUnit(String acceptUnit) {
		this.acceptUnit = acceptUnit;
	}

	@Column(name="accept_unit", nullable=true, length=500)	
	public String getAcceptUnit() {
		return acceptUnit;
	}

	public void setItemNbr(String itemNbr) {
		this.itemNbr = itemNbr;
	}

	@Column(name="item_nbr", nullable=true, length=100)	
	public String getItemNbr() {
		return itemNbr;
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

	public void setYearMonth(Integer yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Column(name="year_month", nullable=true, length=22)	
	public Integer getYearMonth() {
		return yearMonth;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="content", nullable=true, length=500)	
	public String getContent() {
		return content;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=100)	
	public String getGroupCode() {
		return groupCode;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=100)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setItemCn(String itemCn) {
		this.itemCn = itemCn;
	}

	@Column(name="item_cn", nullable=true, length=100)	
	public String getItemCn() {
		return itemCn;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=100)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setPriorVendorCode(String priorVendorCode) {
		this.priorVendorCode = priorVendorCode;
	}

	@Column(name="prior_vendor_code", nullable=true, length=80)	
	public String getPriorVendorCode() {
		return priorVendorCode;
	}

	public void setPriorVendorName(String priorVendorName) {
		this.priorVendorName = priorVendorName;
	}

	@Column(name="prior_vendor_name", nullable=true, length=250)	
	public String getPriorVendorName() {
		return priorVendorName;
	}

	public void setContractYear(String contractYear) {
		this.contractYear = contractYear;
	}

	@Column(name="contract_year", nullable=true, length=50)	
	public String getContractYear() {
		return contractYear;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	@Column(name="contract_status", nullable=true, length=50)	
	public String getContractStatus() {
		return contractStatus;
	}

	public void setChargeStandards(String chargeStandards) {
		this.chargeStandards = chargeStandards;
	}

	@Column(name="charge_standards", nullable=true, length=100)	
	public String getChargeStandards() {
		return chargeStandards;
	}

	public void setChargeMoney(BigDecimal chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	@Column(name="charge_money", nullable=true, length=22)	
	public BigDecimal getChargeMoney() {
		return chargeMoney;
	}

	public void setReceiveAmount(BigDecimal receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	@Column(name="receive_amount", nullable=true, length=22)	
	public BigDecimal getReceiveAmount() {
		return receiveAmount;
	}

	public void setUnconfirmAmount(BigDecimal unconfirmAmount) {
		this.unconfirmAmount = unconfirmAmount;
	}

	@Column(name="unconfirm_amount", nullable=true, length=22)	
	public BigDecimal getUnconfirmAmount() {
		return unconfirmAmount;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	@Column(name="advance_amount", nullable=true, length=22)	
	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceCode(String advanceCode) {
		this.advanceCode = advanceCode;
	}

	@Column(name="advance_code", nullable=true, length=100)	
	public String getAdvanceCode() {
		return advanceCode;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	@Column(name="difference", nullable=true, length=22)	
	public BigDecimal getDifference() {
		return difference;
	}

	public void setCollect(String collect) {
		this.collect = collect;
	}

	@Column(name="collect", nullable=true, length=50)	
	public String getCollect() {
		return collect;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	@Column(name="purchase", nullable=true, length=50)	
	public String getPurchase() {
		return purchase;
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

	public void setDebitOrderCode(String debitOrderCode) {
		this.debitOrderCode = debitOrderCode;
	}

	@Column(name="debit_order_code", nullable=true, length=50)	
	public String getDebitOrderCode() {
		return debitOrderCode;
	}

	public void setReceipts(BigDecimal receipts) {
		this.receipts = receipts;
	}

	@Column(name="receipts", nullable=true, length=22)	
	public BigDecimal getReceipts() {
		return receipts;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=22)	
	public Integer getStatus() {
		return status;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name="parent_id", nullable=true, length=22)	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentVendorCode(String parentVendorCode) {
		this.parentVendorCode = parentVendorCode;
	}

	@Column(name="parent_vendor_code", nullable=true, length=255)	
	public String getParentVendorCode() {
		return parentVendorCode;
	}

	public void setParentVendorName(String parentVendorName) {
		this.parentVendorName = parentVendorName;
	}

	@Column(name="parent_vendor_name", nullable=true, length=255)	
	public String getParentVendorName() {
		return parentVendorName;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	@Column(name="batch_code", nullable=false, length=50)	
	public String getBatchCode() {
		return batchCode;
	}

	public void setContractTerms(String contractTerms) {
		this.contractTerms = contractTerms;
	}

	@Column(name="contract_terms", nullable=true, length=2000)	
	public String getContractTerms() {
		return contractTerms;
	}

	public void setTransferOutPerson(Integer transferOutPerson) {
		this.transferOutPerson = transferOutPerson;
	}

	@Column(name="transfer_out_person", nullable=true, length=22)	
	public Integer getTransferOutPerson() {
		return transferOutPerson;
	}

	public void setTransferLastPerson(Integer transferLastPerson) {
		this.transferLastPerson = transferLastPerson;
	}

	@Column(name="transfer_last_person", nullable=true, length=22)	
	public Integer getTransferLastPerson() {
		return transferLastPerson;
	}

	public void setTransferInDate(Date transferInDate) {
		this.transferInDate = transferInDate;
	}

	@Column(name="transfer_in_date", nullable=true, length=7)	
	public Date getTransferInDate() {
		return transferInDate;
	}

	public void setTransferOutDate(Date transferOutDate) {
		this.transferOutDate = transferOutDate;
	}

	@Column(name="transfer_out_date", nullable=true, length=7)	
	public Date getTransferOutDate() {
		return transferOutDate;
	}

	public void setTransferLastDate(Date transferLastDate) {
		this.transferLastDate = transferLastDate;
	}

	@Column(name="transfer_last_date", nullable=true, length=7)	
	public Date getTransferLastDate() {
		return transferLastDate;
	}

	public void setTransferInPerson(Integer transferInPerson) {
		this.transferInPerson = transferInPerson;
	}

	@Column(name="transfer_in_person", nullable=true, length=22)	
	public Integer getTransferInPerson() {
		return transferInPerson;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=1000)
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setAdditionRate(BigDecimal additionRate) {
		this.additionRate = additionRate;
	}
	@Column(name="addition_rate")
	public BigDecimal getAdditionRate() {
		return additionRate;
	}

	@Column(name="process_id", nullable=true, length=22)
	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	@Column(name="adjust_by", nullable=true, length=22)
	public Integer getAdjustBy() {
		return adjustBy;
	}

	public void setAdjustBy(Integer adjustBy) {
		this.adjustBy = adjustBy;
	}

	@Column(name="adjust_amount", nullable=true, length=22)
	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	@Column(name="adjust_receive_amount", nullable=true, length=22)
	public BigDecimal getAdjustReceiveAmount() {
		return adjustReceiveAmount;
	}

	public void setAdjustReceiveAmount(BigDecimal adjustReceiveAmount) {
		this.adjustReceiveAmount = adjustReceiveAmount;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true)
	public String getRemark() {
		return remark;
	}

	@Column(name="no_receive_amount", nullable=true)
	public BigDecimal getNoReceiveAmount() {
		return noReceiveAmount;
	}

	public void setNoReceiveAmount(BigDecimal noReceiveAmount) {
		this.noReceiveAmount = noReceiveAmount;
	}

	@Column(name="no_unconfirm_amount", nullable=true)
	public BigDecimal getNoUnconfirmAmount() {
		return noUnconfirmAmount;
	}

	public void setNoUnconfirmAmount(BigDecimal noUnconfirmAmount) {
		this.noUnconfirmAmount = noUnconfirmAmount;
	}

	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="no_adjust_amount")
	public BigDecimal getNoAdjustAmount() {
		return noAdjustAmount;
	}

	public void setNoAdjustAmount(BigDecimal noAdjustAmount) {
		this.noAdjustAmount = noAdjustAmount;
	}

	@Column(name="no_adjust_receive_amount")
	public BigDecimal getNoAdjustReceiveAmount() {
		return noAdjustReceiveAmount;
	}

	public void setNoAdjustReceiveAmount(BigDecimal noAdjustReceiveAmount) {
		this.noAdjustReceiveAmount = noAdjustReceiveAmount;
	}

	@Column(name="no_difference")
	public BigDecimal getNoDifference() {
		return noDifference;
	}

	public void setNoDifference(BigDecimal noDifference) {
		this.noDifference = noDifference;
	}

}
