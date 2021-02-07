package com.sie.watsons.base.poc.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * XxPromHeadEntity_HI_RO Entity Object
 * Tue Aug 13 12:21:34 CST 2019  Auto Generate
 */

public class XxPromHeadEntity_HI_RO {
    private BigDecimal xxPromId;
    private String xxPromDesc;
    private String buParentId;
    private String xxOfferId;
    private String status;
    private String xxPromType;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String activationCode;
    private String activationOption;
    private String xxBuyCondition;
    private BigDecimal threshold;
    private BigDecimal secondThreshold;
    private BigDecimal minPurchase;
    private String rewardType;
    private String rewardMemberAccount;
    private String rewardTender;
    private BigDecimal reward;
    private String rewardOn;
    private BigDecimal rewardThreshold;
    private String rewardOccurLimit;
    private String rewardApportionment;
    private String lastUpdateId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDatetime;
    private String approvalId;
    private String extractStatus;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date extractDatetime;
    private String couponItem;
    private BigDecimal couponCoCharge;
    private BigDecimal couponSupCharge;
    private BigDecimal couponSupplier;
    private String couponRtnSupInd;
    private String adhocInd;
    private String cancelId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date cancelDatetime;
    private String createId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDatetime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approvalDatetime;
    private String comments;
    private BigDecimal promotionDiscType;
    private String bookInd;
    private String delayPromFg;
    private String prirtyBcktRwrdCalc;
    private String rfndAllwdFg;
    private BigDecimal eventId;
    private String eopAction;
    private BigDecimal allocId;
    private BigDecimal supplier;
    private String priceChangeInd;
    private String costChangeInd;
    private String dealInd;
    private String allocInd;
    private String cogsAdjInd;
    private String simplePromExtractInd;
    private BigDecimal simplePromId;
    private String crossOutPriceInd;
    private BigDecimal returnWh;
    private String xxRtlOptBhv;
    private String xxTimePromInd;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date priceChangeStartDate;
    private String immediateEndInd;
    private String rewardByProm;
    private String templateName;
    private String voucherItem;
    private String redeemItem;
    private BigDecimal lmtRwrdValInTicket;
    private String grpMmbrAcct;
    private String crdtCalc;
    private BigDecimal maxSpendOnRwrdCalc;
    private BigDecimal minTresholdForTrig;
    private String onlinePromInd;
    private String couponType;
    private String voucherCode;
    private String depRoleInd;
    private String suspendedInd;
    private String compenCode;
    private Integer operatorUserId;

	public void setXxPromId(BigDecimal xxPromId) {
		this.xxPromId = xxPromId;
	}

	
	public BigDecimal getXxPromId() {
		return xxPromId;
	}

	public void setXxPromDesc(String xxPromDesc) {
		this.xxPromDesc = xxPromDesc;
	}

	
	public String getXxPromDesc() {
		return xxPromDesc;
	}

	public void setBuParentId(String buParentId) {
		this.buParentId = buParentId;
	}

	
	public String getBuParentId() {
		return buParentId;
	}

	public void setXxOfferId(String xxOfferId) {
		this.xxOfferId = xxOfferId;
	}

	
	public String getXxOfferId() {
		return xxOfferId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setXxPromType(String xxPromType) {
		this.xxPromType = xxPromType;
	}

	
	public String getXxPromType() {
		return xxPromType;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getEndDate() {
		return endDate;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	
	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationOption(String activationOption) {
		this.activationOption = activationOption;
	}

	
	public String getActivationOption() {
		return activationOption;
	}

	public void setXxBuyCondition(String xxBuyCondition) {
		this.xxBuyCondition = xxBuyCondition;
	}

	
	public String getXxBuyCondition() {
		return xxBuyCondition;
	}

	public void setThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}

	
	public BigDecimal getThreshold() {
		return threshold;
	}

	public void setSecondThreshold(BigDecimal secondThreshold) {
		this.secondThreshold = secondThreshold;
	}

	
	public BigDecimal getSecondThreshold() {
		return secondThreshold;
	}

	public void setMinPurchase(BigDecimal minPurchase) {
		this.minPurchase = minPurchase;
	}

	
	public BigDecimal getMinPurchase() {
		return minPurchase;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	
	public String getRewardType() {
		return rewardType;
	}

	public void setRewardMemberAccount(String rewardMemberAccount) {
		this.rewardMemberAccount = rewardMemberAccount;
	}

	
	public String getRewardMemberAccount() {
		return rewardMemberAccount;
	}

	public void setRewardTender(String rewardTender) {
		this.rewardTender = rewardTender;
	}

	
	public String getRewardTender() {
		return rewardTender;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}

	
	public BigDecimal getReward() {
		return reward;
	}

	public void setRewardOn(String rewardOn) {
		this.rewardOn = rewardOn;
	}

	
	public String getRewardOn() {
		return rewardOn;
	}

	public void setRewardThreshold(BigDecimal rewardThreshold) {
		this.rewardThreshold = rewardThreshold;
	}

	
	public BigDecimal getRewardThreshold() {
		return rewardThreshold;
	}

	public void setRewardOccurLimit(String rewardOccurLimit) {
		this.rewardOccurLimit = rewardOccurLimit;
	}

	
	public String getRewardOccurLimit() {
		return rewardOccurLimit;
	}

	public void setRewardApportionment(String rewardApportionment) {
		this.rewardApportionment = rewardApportionment;
	}

	
	public String getRewardApportionment() {
		return rewardApportionment;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	
	public String getApprovalId() {
		return approvalId;
	}

	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

	
	public String getExtractStatus() {
		return extractStatus;
	}

	public void setExtractDatetime(Date extractDatetime) {
		this.extractDatetime = extractDatetime;
	}

	
	public Date getExtractDatetime() {
		return extractDatetime;
	}

	public void setCouponItem(String couponItem) {
		this.couponItem = couponItem;
	}

	
	public String getCouponItem() {
		return couponItem;
	}

	public void setCouponCoCharge(BigDecimal couponCoCharge) {
		this.couponCoCharge = couponCoCharge;
	}

	
	public BigDecimal getCouponCoCharge() {
		return couponCoCharge;
	}

	public void setCouponSupCharge(BigDecimal couponSupCharge) {
		this.couponSupCharge = couponSupCharge;
	}

	
	public BigDecimal getCouponSupCharge() {
		return couponSupCharge;
	}

	public void setCouponSupplier(BigDecimal couponSupplier) {
		this.couponSupplier = couponSupplier;
	}

	
	public BigDecimal getCouponSupplier() {
		return couponSupplier;
	}

	public void setCouponRtnSupInd(String couponRtnSupInd) {
		this.couponRtnSupInd = couponRtnSupInd;
	}

	
	public String getCouponRtnSupInd() {
		return couponRtnSupInd;
	}

	public void setAdhocInd(String adhocInd) {
		this.adhocInd = adhocInd;
	}

	
	public String getAdhocInd() {
		return adhocInd;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	
	public String getCancelId() {
		return cancelId;
	}

	public void setCancelDatetime(Date cancelDatetime) {
		this.cancelDatetime = cancelDatetime;
	}

	
	public Date getCancelDatetime() {
		return cancelDatetime;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	
	public String getCreateId() {
		return createId;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setApprovalDatetime(Date approvalDatetime) {
		this.approvalDatetime = approvalDatetime;
	}

	
	public Date getApprovalDatetime() {
		return approvalDatetime;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public String getComments() {
		return comments;
	}

	public void setPromotionDiscType(BigDecimal promotionDiscType) {
		this.promotionDiscType = promotionDiscType;
	}

	
	public BigDecimal getPromotionDiscType() {
		return promotionDiscType;
	}

	public void setBookInd(String bookInd) {
		this.bookInd = bookInd;
	}

	
	public String getBookInd() {
		return bookInd;
	}

	public void setDelayPromFg(String delayPromFg) {
		this.delayPromFg = delayPromFg;
	}

	
	public String getDelayPromFg() {
		return delayPromFg;
	}

	public void setPrirtyBcktRwrdCalc(String prirtyBcktRwrdCalc) {
		this.prirtyBcktRwrdCalc = prirtyBcktRwrdCalc;
	}

	
	public String getPrirtyBcktRwrdCalc() {
		return prirtyBcktRwrdCalc;
	}

	public void setRfndAllwdFg(String rfndAllwdFg) {
		this.rfndAllwdFg = rfndAllwdFg;
	}

	
	public String getRfndAllwdFg() {
		return rfndAllwdFg;
	}

	public void setEventId(BigDecimal eventId) {
		this.eventId = eventId;
	}

	
	public BigDecimal getEventId() {
		return eventId;
	}

	public void setEopAction(String eopAction) {
		this.eopAction = eopAction;
	}

	
	public String getEopAction() {
		return eopAction;
	}

	public void setAllocId(BigDecimal allocId) {
		this.allocId = allocId;
	}

	
	public BigDecimal getAllocId() {
		return allocId;
	}

	public void setSupplier(BigDecimal supplier) {
		this.supplier = supplier;
	}

	
	public BigDecimal getSupplier() {
		return supplier;
	}

	public void setPriceChangeInd(String priceChangeInd) {
		this.priceChangeInd = priceChangeInd;
	}

	
	public String getPriceChangeInd() {
		return priceChangeInd;
	}

	public void setCostChangeInd(String costChangeInd) {
		this.costChangeInd = costChangeInd;
	}

	
	public String getCostChangeInd() {
		return costChangeInd;
	}

	public void setDealInd(String dealInd) {
		this.dealInd = dealInd;
	}

	
	public String getDealInd() {
		return dealInd;
	}

	public void setAllocInd(String allocInd) {
		this.allocInd = allocInd;
	}

	
	public String getAllocInd() {
		return allocInd;
	}

	public void setCogsAdjInd(String cogsAdjInd) {
		this.cogsAdjInd = cogsAdjInd;
	}

	
	public String getCogsAdjInd() {
		return cogsAdjInd;
	}

	public void setSimplePromExtractInd(String simplePromExtractInd) {
		this.simplePromExtractInd = simplePromExtractInd;
	}

	
	public String getSimplePromExtractInd() {
		return simplePromExtractInd;
	}

	public void setSimplePromId(BigDecimal simplePromId) {
		this.simplePromId = simplePromId;
	}

	
	public BigDecimal getSimplePromId() {
		return simplePromId;
	}

	public void setCrossOutPriceInd(String crossOutPriceInd) {
		this.crossOutPriceInd = crossOutPriceInd;
	}

	
	public String getCrossOutPriceInd() {
		return crossOutPriceInd;
	}

	public void setReturnWh(BigDecimal returnWh) {
		this.returnWh = returnWh;
	}

	
	public BigDecimal getReturnWh() {
		return returnWh;
	}

	public void setXxRtlOptBhv(String xxRtlOptBhv) {
		this.xxRtlOptBhv = xxRtlOptBhv;
	}

	
	public String getXxRtlOptBhv() {
		return xxRtlOptBhv;
	}

	public void setXxTimePromInd(String xxTimePromInd) {
		this.xxTimePromInd = xxTimePromInd;
	}

	
	public String getXxTimePromInd() {
		return xxTimePromInd;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	
	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	public Date getEndTime() {
		return endTime;
	}

	public void setPriceChangeStartDate(Date priceChangeStartDate) {
		this.priceChangeStartDate = priceChangeStartDate;
	}

	
	public Date getPriceChangeStartDate() {
		return priceChangeStartDate;
	}

	public void setImmediateEndInd(String immediateEndInd) {
		this.immediateEndInd = immediateEndInd;
	}

	
	public String getImmediateEndInd() {
		return immediateEndInd;
	}

	public void setRewardByProm(String rewardByProm) {
		this.rewardByProm = rewardByProm;
	}

	
	public String getRewardByProm() {
		return rewardByProm;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	
	public String getTemplateName() {
		return templateName;
	}

	public void setVoucherItem(String voucherItem) {
		this.voucherItem = voucherItem;
	}

	
	public String getVoucherItem() {
		return voucherItem;
	}

	public void setRedeemItem(String redeemItem) {
		this.redeemItem = redeemItem;
	}

	
	public String getRedeemItem() {
		return redeemItem;
	}

	public void setLmtRwrdValInTicket(BigDecimal lmtRwrdValInTicket) {
		this.lmtRwrdValInTicket = lmtRwrdValInTicket;
	}

	
	public BigDecimal getLmtRwrdValInTicket() {
		return lmtRwrdValInTicket;
	}

	public void setGrpMmbrAcct(String grpMmbrAcct) {
		this.grpMmbrAcct = grpMmbrAcct;
	}

	
	public String getGrpMmbrAcct() {
		return grpMmbrAcct;
	}

	public void setCrdtCalc(String crdtCalc) {
		this.crdtCalc = crdtCalc;
	}

	
	public String getCrdtCalc() {
		return crdtCalc;
	}

	public void setMaxSpendOnRwrdCalc(BigDecimal maxSpendOnRwrdCalc) {
		this.maxSpendOnRwrdCalc = maxSpendOnRwrdCalc;
	}

	
	public BigDecimal getMaxSpendOnRwrdCalc() {
		return maxSpendOnRwrdCalc;
	}

	public void setMinTresholdForTrig(BigDecimal minTresholdForTrig) {
		this.minTresholdForTrig = minTresholdForTrig;
	}

	
	public BigDecimal getMinTresholdForTrig() {
		return minTresholdForTrig;
	}

	public void setOnlinePromInd(String onlinePromInd) {
		this.onlinePromInd = onlinePromInd;
	}

	
	public String getOnlinePromInd() {
		return onlinePromInd;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	
	public String getCouponType() {
		return couponType;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	
	public String getVoucherCode() {
		return voucherCode;
	}

	public void setDepRoleInd(String depRoleInd) {
		this.depRoleInd = depRoleInd;
	}

	
	public String getDepRoleInd() {
		return depRoleInd;
	}

	public void setSuspendedInd(String suspendedInd) {
		this.suspendedInd = suspendedInd;
	}

	
	public String getSuspendedInd() {
		return suspendedInd;
	}

	public void setCompenCode(String compenCode) {
		this.compenCode = compenCode;
	}

	
	public String getCompenCode() {
		return compenCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
