package com.sie.watsons.base.report.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaFreeGoodsEntity_HI Entity Object
 * Tue Nov 12 14:07:10 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_FREE_GOODS")
public class TtaFreeGoodsEntity_HI {
    private Integer ttaFreeGoodsId;
    private String vendorNbr;//供应商编号
    private String vendorName;//供应商名称
    private String brand;//品牌
    private String freeTerms;//免费货品_条款
    private String incomeType;//收款方式
    private BigDecimal receivFreeAmt;//本年度应收免费货金额（不含税）
    private BigDecimal netPurchase;//本年度实际折扣前采购额（含税）
    private BigDecimal futerPurchase;//本年度预估全年折扣前采购额（含税
    private String groupDesc;
    private BigDecimal totalPromotionAmt;//促销免费货（不含税）
    private BigDecimal totalContractAmt;//合同免费货（不含税）
    private BigDecimal totalSackAmt;//试用装免费货（不含税）
    private BigDecimal totalFreeAmt;//实收免费货总额
    private BigDecimal differAmt;//差异金额
    private String purchaseAct;//采购行动
    private String status;//状态
    private String remark;//备注
    private String timesVersion;//批次号
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer changePerson;//转办人id
    private String changePersonName;//转办人名称
    private Integer operatorUserId;
    private BigDecimal borrowAdjAmt;//货款调整金额
    private BigDecimal aboiRecevieAmt;//ABOI实收金额
    private BigDecimal caRecevieAmt;//CA实收金额
    private String exemptionReason;//豁免原因1
    private String exemptionReason2;//豁免原因2
    private Integer transferInPerson;//被转办人id
    private String transferInPersonName;//被转办人名称
    private String proposalOrder;
    private Integer proposalUserId;
    private Integer proposalId;

    public void setTtaFreeGoodsId(Integer ttaFreeGoodsId) {
        this.ttaFreeGoodsId = ttaFreeGoodsId;
    }

    @Id
    @SequenceGenerator(name = "SEQ_TTA_FREE_GOODS", sequenceName = "SEQ_TTA_FREE_GOODS", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_FREE_GOODS", strategy = GenerationType.SEQUENCE)
    @Column(name="tta_free_goods_id", nullable=false, length=22)
    public Integer getTtaFreeGoodsId() {
        return ttaFreeGoodsId;
    }

    public void setVendorNbr(String vendorNbr) {
        this.vendorNbr = vendorNbr;
    }

    @Column(name="vendor_nbr", nullable=true, length=100)
    public String getVendorNbr() {
        return vendorNbr;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Column(name="vendor_name", nullable=true, length=100)
    public String getVendorName() {
        return vendorName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name="brand", nullable=true, length=100)
    public String getBrand() {
        return brand;
    }

    public void setFreeTerms(String freeTerms) {
        this.freeTerms = freeTerms;
    }

    @Column(name="free_terms", nullable=true, length=50)
    public String getFreeTerms() {
        return freeTerms;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }

    @Column(name="income_type", nullable=true, length=50)
    public String getIncomeType() {
        return incomeType;
    }

    public void setReceivFreeAmt(BigDecimal receivFreeAmt) {
        this.receivFreeAmt = receivFreeAmt;
    }

    @Column(name="receiv_free_amt", nullable=true, length=22)
    public BigDecimal getReceivFreeAmt() {
        return receivFreeAmt;
    }

    public void setNetPurchase(BigDecimal netPurchase) {
        this.netPurchase = netPurchase;
    }

    @Column(name="net_purchase", nullable=true, length=22)
    public BigDecimal getNetPurchase() {
        return netPurchase;
    }

    public void setFuterPurchase(BigDecimal futerPurchase) {
        this.futerPurchase = futerPurchase;
    }

    @Column(name="futer_purchase", nullable=true, length=22)
    public BigDecimal getFuterPurchase() {
        return futerPurchase;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    @Column(name="group_desc", nullable=true, length=1000)
    public String getGroupDesc() {
        return groupDesc;
    }

    public void setTotalPromotionAmt(BigDecimal totalPromotionAmt) {
        this.totalPromotionAmt = totalPromotionAmt;
    }

    @Column(name="total_promotion_amt", nullable=true, length=22)
    public BigDecimal getTotalPromotionAmt() {
        return totalPromotionAmt;
    }

    public void setTotalContractAmt(BigDecimal totalContractAmt) {
        this.totalContractAmt = totalContractAmt;
    }

    @Column(name="total_contract_amt", nullable=true, length=22)
    public BigDecimal getTotalContractAmt() {
        return totalContractAmt;
    }

    public void setTotalSackAmt(BigDecimal totalSackAmt) {
        this.totalSackAmt = totalSackAmt;
    }

    @Column(name="total_sack_amt", nullable=true, length=22)
    public BigDecimal getTotalSackAmt() {
        return totalSackAmt;
    }

    public void setTotalFreeAmt(BigDecimal totalFreeAmt) {
        this.totalFreeAmt = totalFreeAmt;
    }

    @Column(name="total_free_amt", nullable=true, length=22)
    public BigDecimal getTotalFreeAmt() {
        return totalFreeAmt;
    }

    public void setDifferAmt(BigDecimal differAmt) {
        this.differAmt = differAmt;
    }

    @Column(name="differ_amt", nullable=true, length=22)
    public BigDecimal getDifferAmt() {
        return differAmt;
    }

    public void setPurchaseAct(String purchaseAct) {
        this.purchaseAct = purchaseAct;
    }

    @Column(name="purchase_act", nullable=true, length=800)
    public String getPurchaseAct() {
        return purchaseAct;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name="status", nullable=true, length=50)
    public String getStatus() {
        return status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="remark", nullable=true, length=500)
    public String getRemark() {
        return remark;
    }

    public void setTimesVersion(String timesVersion) {
        this.timesVersion = timesVersion;
    }

    @Column(name="times_version", nullable=true, length=60)
    public String getTimesVersion() {
        return timesVersion;
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

    public void setChangePerson(Integer changePerson) {
        this.changePerson = changePerson;
    }

    @Column(name="change_person", nullable=true, length=22)
    public Integer getChangePerson() {
        return changePerson;
    }

    public void setChangePersonName(String changePersonName) {
        this.changePersonName = changePersonName;
    }

    @Column(name="change_person_name", nullable=true, length=100)
    public String getChangePersonName() {
        return changePersonName;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    @Column(name="borrow_adj_amt", nullable=true, length=22)
    public BigDecimal getBorrowAdjAmt() {
        return borrowAdjAmt;
    }

    public void setBorrowAdjAmt(BigDecimal borrowAdjAmt) {
        this.borrowAdjAmt = borrowAdjAmt;
    }

    @Column(name="aboi_recevie_amt", nullable=true, length=22)
    public BigDecimal getAboiRecevieAmt() {
        return aboiRecevieAmt;
    }

    public void setAboiRecevieAmt(BigDecimal aboiRecevieAmt) {
        this.aboiRecevieAmt = aboiRecevieAmt;
    }

    @Column(name="ca_recevie_amt", nullable=true, length=22)
    public BigDecimal getCaRecevieAmt() {
        return caRecevieAmt;
    }

    public void setCaRecevieAmt(BigDecimal caRecevieAmt) {
        this.caRecevieAmt = caRecevieAmt;
    }

    @Column(name="exemption_reason", nullable=true, length=255)
    public String getExemptionReason() {
        return exemptionReason;
    }

    public void setExemptionReason(String exemptionReason) {
        this.exemptionReason = exemptionReason;
    }

    @Column(name="exemption_reason2", nullable=true, length=255)
    public String getExemptionReason2() {
        return exemptionReason2;
    }

    public void setExemptionReason2(String exemptionReason2) {
        this.exemptionReason2 = exemptionReason2;
    }

    @Column(name="transfer_in_person", nullable=true, length=22)
    public Integer getTransferInPerson() {
        return transferInPerson;
    }

    public void setTransferInPerson(Integer transferInPerson) {
        this.transferInPerson = transferInPerson;
    }

    @Column(name="transfer_in_person_name", nullable=true, length=100)
    public String getTransferInPersonName() {
        return transferInPersonName;
    }

    public void setTransferInPersonName(String transferInPersonName) {
        this.transferInPersonName = transferInPersonName;
    }

    @Column(name="proposal_order", nullable=true, length=100)
    public String getProposalOrder() {
        return proposalOrder;
    }

    public void setProposalOrder(String proposalOrder) {
        this.proposalOrder = proposalOrder;
    }

    @Column(name="proposal_user_id", nullable=true, length=22)
    public Integer getProposalUserId() {
        return proposalUserId;
    }

    public void setProposalUserId(Integer proposalUserId) {
        this.proposalUserId = proposalUserId;
    }

    @Column(name="proposal_id", nullable=true, length=22)
    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }
}
