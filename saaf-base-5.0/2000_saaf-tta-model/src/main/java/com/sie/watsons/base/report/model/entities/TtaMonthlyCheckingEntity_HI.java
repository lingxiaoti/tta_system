package com.sie.watsons.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/16/016.
 */
@Entity
@Table(name="TTA_OSD_MONTHLY_CHECKING")
public class TtaMonthlyCheckingEntity_HI {

    private Integer osdId;
    private String promotionSection;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionStartDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date promotionEndDate;
    private String promotionLocation;
    private Integer timeDimension;
    private Integer storesNum;
    private String groupDesc;
    private String deptDesc;
    private String deptCode;
    private String groupCode;
    private String content;
    private String itemCode;
    private String cnName;
    private String brandCn;
    private String brandEn;
    private String priorVendorCode;
    private String priorVendorName;
    private String contractYear;
    private String contractStatus;
    private String chargeStandards;
    private BigDecimal chargeMoney;
    private String chargeUnit;
    private BigDecimal receiveAmount;
    private BigDecimal originalAmount;
    private BigDecimal unconfirmAmount;
    private BigDecimal difference;
    private String collect;
    private String purchase;
    private String exemptionReason;
    private String exemptionReason2;
    private String debitOrderCode;
    private BigDecimal receipts;
    private String batchCode;
    private Integer transferInPerson;
    private String remark;
    private Integer status;
    private Integer parentId;
    private String parentVendorCode;
    private String parentVendorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer transferOutPerson;
    private Integer transferLastPerson;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferInDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferOutDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date transferLastDate;
    private String chargeUnitName;
    private BigDecimal originalBeforeAmount;
    private BigDecimal additionRate;
    private String referenceVendorCode;
    private Integer processId;
    private Integer osdYear;
    private BigDecimal noReceiveAmount;
    private BigDecimal noUnconfirmAmount;
    private Integer operatorUserId;
    private Integer adjustBy;
    private BigDecimal adjustAmount;
    private BigDecimal adjustReceiveAmount;
    private BigDecimal noAdjustAmount;
    private BigDecimal noAdjustReceiveAmount;

    public void setOsdId(Integer osdId) {
        this.osdId = osdId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_TTA_OSD_MONTHLY_CHECKING", sequenceName = "SEQ_TTA_OSD_MONTHLY_CHECKING", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_OSD_MONTHLY_CHECKING", strategy = GenerationType.SEQUENCE)
    @Column(name="osd_id", nullable=false, length=22)
    public Integer getOsdId() {
        return osdId;
    }

    public void setPromotionSection(String promotionSection) {
        this.promotionSection = promotionSection;
    }

    @Column(name="promotion_section", nullable=true, length=200)
    public String getPromotionSection() {
        return promotionSection;
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

    public void setPromotionLocation(String promotionLocation) {
        this.promotionLocation = promotionLocation;
    }

    @Column(name="promotion_location", nullable=true, length=500)
    public String getPromotionLocation() {
        return promotionLocation;
    }

    public void setTimeDimension(Integer timeDimension) {
        this.timeDimension = timeDimension;
    }

    @Column(name="time_dimension", nullable=true, length=22)
    public Integer getTimeDimension() {
        return timeDimension;
    }

    public void setStoresNum(Integer storesNum) {
        this.storesNum = storesNum;
    }

    @Column(name="stores_num", nullable=true, length=22)
    public Integer getStoresNum() {
        return storesNum;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    @Column(name="group_desc", nullable=true, length=100)
    public String getGroupDesc() {
        return groupDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    @Column(name="dept_desc", nullable=true, length=100)
    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Column(name="dept_code", nullable=true, length=50)
    public String getDeptCode() {
        return deptCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Column(name="group_code", nullable=true, length=100)
    public String getGroupCode() {
        return groupCode;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name="content", nullable=true, length=500)
    public String getContent() {
        return content;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name="item_code", nullable=true, length=50)
    public String getItemCode() {
        return itemCode;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    @Column(name="cn_name", nullable=true, length=100)
    public String getCnName() {
        return cnName;
    }

    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }

    @Column(name="brand_cn", nullable=true, length=100)
    public String getBrandCn() {
        return brandCn;
    }

    public void setBrandEn(String brandEn) {
        this.brandEn = brandEn;
    }

    @Column(name="brand_en", nullable=true, length=100)
    public String getBrandEn() {
        return brandEn;
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

    public void setChargeUnit(String chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    @Column(name="charge_unit", nullable=true, length=50)
    public String getChargeUnit() {
        return chargeUnit;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    @Column(name="receive_amount", nullable=true, length=22)
    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    @Column(name="original_amount", nullable=true, length=22)
    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setUnconfirmAmount(BigDecimal unconfirmAmount) {
        this.unconfirmAmount = unconfirmAmount;
    }

    @Column(name="unconfirm_amount", nullable=true, length=22)
    public BigDecimal getUnconfirmAmount() {
        return unconfirmAmount;
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

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name="batch_code", nullable=true, length=255)
    public String getBatchCode() {
        return batchCode;
    }

    public void setTransferInPerson(Integer transferInPerson) {
        this.transferInPerson = transferInPerson;
    }

    @Column(name="transfer_in_person", nullable=true, length=22)
    public Integer getTransferInPerson() {
        return transferInPerson;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="remark", nullable=true, length=255)
    public String getRemark() {
        return remark;
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

    public void setChargeUnitName(String chargeUnitName) {
        this.chargeUnitName = chargeUnitName;
    }

    @Column(name="charge_unit_name", nullable=true, length=100)
    public String getChargeUnitName() {
        return chargeUnitName;
    }

    public void setOriginalBeforeAmount(BigDecimal originalBeforeAmount) {
        this.originalBeforeAmount = originalBeforeAmount;
    }

    @Column(name="original_before_amount", nullable=true, length=22)
    public BigDecimal getOriginalBeforeAmount() {
        return originalBeforeAmount;
    }

    public void setAdditionRate(BigDecimal additionRate) {
        this.additionRate = additionRate;
    }

    @Column(name="addition_rate", nullable=true, length=22)
    public BigDecimal getAdditionRate() {
        return additionRate;
    }

    public void setReferenceVendorCode(String referenceVendorCode) {
        this.referenceVendorCode = referenceVendorCode;
    }

    @Column(name="reference_vendor_code", nullable=true, length=100)
    public String getReferenceVendorCode() {
        return referenceVendorCode;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    @Column(name="process_id", nullable=true, length=22)
    public Integer getProcessId() {
        return processId;
    }

    public void setOsdYear(Integer osdYear) {
        this.osdYear = osdYear;
    }

    @Column(name="osd_year", nullable=true, length=22)
    public Integer getOsdYear() {
        return osdYear;
    }

    public void setNoReceiveAmount(BigDecimal noReceiveAmount) {
        this.noReceiveAmount = noReceiveAmount;
    }

    @Column(name="no_receive_amount", nullable=true, length=22)
    public BigDecimal getNoReceiveAmount() {
        return noReceiveAmount;
    }

    public void setNoUnconfirmAmount(BigDecimal noUnconfirmAmount) {
        this.noUnconfirmAmount = noUnconfirmAmount;
    }

    @Column(name="no_unconfirm_amount", nullable=true, length=22)
    public BigDecimal getNoUnconfirmAmount() {
        return noUnconfirmAmount;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
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

    @Column(name="no_adjust_amount", nullable=true, length=22)
    public BigDecimal getNoAdjustAmount() {
        return noAdjustAmount;
    }

    public void setNoAdjustAmount(BigDecimal noAdjustAmount) {
        this.noAdjustAmount = noAdjustAmount;
    }

    @Column(name="no_adjust_receive_amount", nullable=true, length=22)
    public BigDecimal getNoAdjustReceiveAmount() {
        return noAdjustReceiveAmount;
    }

    public void setNoAdjustReceiveAmount(BigDecimal noAdjustReceiveAmount) {
        this.noAdjustReceiveAmount = noAdjustReceiveAmount;
    }
}
