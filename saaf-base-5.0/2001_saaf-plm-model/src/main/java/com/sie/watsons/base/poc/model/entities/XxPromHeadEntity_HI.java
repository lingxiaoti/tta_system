package com.sie.watsons.base.poc.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * XxPromHeadEntity_HI Entity Object
 * Tue Aug 13 12:21:34 CST 2019  Auto Generate
 */
@Entity
@Table(name="xx_prom_head")
public class XxPromHeadEntity_HI {
    private String xxPromId;
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
    private java.math.BigDecimal threshold;
    private java.math.BigDecimal secondThreshold;
    private java.math.BigDecimal minPurchase;
    private String rewardType;
    private String rewardMemberAccount;
    private String rewardTender;
    private java.math.BigDecimal reward;
    private String rewardOn;
    private java.math.BigDecimal rewardThreshold;
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
    private java.math.BigDecimal couponCoCharge;
    private java.math.BigDecimal couponSupCharge;
    private java.math.BigDecimal couponSupplier;
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
    private java.math.BigDecimal promotionDiscType;
    private String bookInd;
    private String delayPromFg;
    private String prirtyBcktRwrdCalc;
    private String rfndAllwdFg;
    private java.math.BigDecimal eventId;
    private String eopAction;
    private java.math.BigDecimal allocId;
    private java.math.BigDecimal supplier;
    private String priceChangeInd;
    private String costChangeInd;
    private String dealInd;
    private String allocInd;
    private String cogsAdjInd;
    private String simplePromExtractInd;
    private java.math.BigDecimal simplePromId;
    private String crossOutPriceInd;
    private java.math.BigDecimal returnWh;
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
    private java.math.BigDecimal lmtRwrdValInTicket;
    private String grpMmbrAcct;
    private String crdtCalc;
    private java.math.BigDecimal maxSpendOnRwrdCalc;
    private java.math.BigDecimal minTresholdForTrig;
    private String onlinePromInd;
    private String couponType;
    private String voucherCode;
    private String depRoleInd;
    private String suspendedInd;
    private String compenCode;
    private Integer operatorUserId;

	public void setXxPromId(String xxPromId) {
		this.xxPromId = xxPromId;
	}

	@Id
	@Column(name="xx_prom_id", nullable=false, length=22)	
	public String getXxPromId() {
		return xxPromId;
	}

	public void setXxPromDesc(String xxPromDesc) {
		this.xxPromDesc = xxPromDesc;
	}

	@Column(name="xx_prom_desc", nullable=false, length=100)	
	public String getXxPromDesc() {
		return xxPromDesc;
	}

	public void setBuParentId(String buParentId) {
		this.buParentId = buParentId;
	}

	@Column(name="bu_parent_id", nullable=false, length=6)	
	public String getBuParentId() {
		return buParentId;
	}

	public void setXxOfferId(String xxOfferId) {
		this.xxOfferId = xxOfferId;
	}

	@Column(name="xx_offer_id", nullable=true, length=20)	
	public String getXxOfferId() {
		return xxOfferId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=1)	
	public String getStatus() {
		return status;
	}

	public void setXxPromType(String xxPromType) {
		this.xxPromType = xxPromType;
	}

	@Column(name="xx_prom_type", nullable=false, length=10)	
	public String getXxPromType() {
		return xxPromType;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="start_date", nullable=false, length=7)	
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="end_date", nullable=false, length=7)	
	public Date getEndDate() {
		return endDate;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	@Column(name="activation_code", nullable=true, length=10)	
	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationOption(String activationOption) {
		this.activationOption = activationOption;
	}

	@Column(name="activation_option", nullable=true, length=6)	
	public String getActivationOption() {
		return activationOption;
	}

	public void setXxBuyCondition(String xxBuyCondition) {
		this.xxBuyCondition = xxBuyCondition;
	}

	@Column(name="xx_buy_condition", nullable=true, length=6)	
	public String getXxBuyCondition() {
		return xxBuyCondition;
	}

	public void setThreshold(java.math.BigDecimal threshold) {
		this.threshold = threshold;
	}

	@Column(name="threshold", nullable=true, length=22)	
	public java.math.BigDecimal getThreshold() {
		return threshold;
	}

	public void setSecondThreshold(java.math.BigDecimal secondThreshold) {
		this.secondThreshold = secondThreshold;
	}

	@Column(name="second_threshold", nullable=true, length=22)	
	public java.math.BigDecimal getSecondThreshold() {
		return secondThreshold;
	}

	public void setMinPurchase(java.math.BigDecimal minPurchase) {
		this.minPurchase = minPurchase;
	}

	@Column(name="min_purchase", nullable=true, length=22)	
	public java.math.BigDecimal getMinPurchase() {
		return minPurchase;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	@Column(name="reward_type", nullable=true, length=6)	
	public String getRewardType() {
		return rewardType;
	}

	public void setRewardMemberAccount(String rewardMemberAccount) {
		this.rewardMemberAccount = rewardMemberAccount;
	}

	@Column(name="reward_member_account", nullable=true, length=6)	
	public String getRewardMemberAccount() {
		return rewardMemberAccount;
	}

	public void setRewardTender(String rewardTender) {
		this.rewardTender = rewardTender;
	}

	@Column(name="reward_tender", nullable=true, length=6)	
	public String getRewardTender() {
		return rewardTender;
	}

	public void setReward(java.math.BigDecimal reward) {
		this.reward = reward;
	}

	@Column(name="reward", nullable=true, length=22)	
	public java.math.BigDecimal getReward() {
		return reward;
	}

	public void setRewardOn(String rewardOn) {
		this.rewardOn = rewardOn;
	}

	@Column(name="reward_on", nullable=true, length=1)	
	public String getRewardOn() {
		return rewardOn;
	}

	public void setRewardThreshold(java.math.BigDecimal rewardThreshold) {
		this.rewardThreshold = rewardThreshold;
	}

	@Column(name="reward_threshold", nullable=true, length=22)	
	public java.math.BigDecimal getRewardThreshold() {
		return rewardThreshold;
	}

	public void setRewardOccurLimit(String rewardOccurLimit) {
		this.rewardOccurLimit = rewardOccurLimit;
	}

	@Column(name="reward_occur_limit", nullable=true, length=6)	
	public String getRewardOccurLimit() {
		return rewardOccurLimit;
	}

	public void setRewardApportionment(String rewardApportionment) {
		this.rewardApportionment = rewardApportionment;
	}

	@Column(name="reward_apportionment", nullable=true, length=6)	
	public String getRewardApportionment() {
		return rewardApportionment;
	}

	public void setLastUpdateId(String lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	@Column(name="last_update_id", nullable=false, length=40)	
	public String getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Column(name="last_update_datetime", nullable=false, length=7)	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	@Column(name="approval_id", nullable=true, length=40)	
	public String getApprovalId() {
		return approvalId;
	}

	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

	@Column(name="extract_status", nullable=false, length=1)	
	public String getExtractStatus() {
		return extractStatus;
	}

	public void setExtractDatetime(Date extractDatetime) {
		this.extractDatetime = extractDatetime;
	}

	@Column(name="extract_datetime", nullable=true, length=7)	
	public Date getExtractDatetime() {
		return extractDatetime;
	}

	public void setCouponItem(String couponItem) {
		this.couponItem = couponItem;
	}

	@Column(name="coupon_item", nullable=true, length=25)	
	public String getCouponItem() {
		return couponItem;
	}

	public void setCouponCoCharge(java.math.BigDecimal couponCoCharge) {
		this.couponCoCharge = couponCoCharge;
	}

	@Column(name="coupon_co_charge", nullable=true, length=22)	
	public java.math.BigDecimal getCouponCoCharge() {
		return couponCoCharge;
	}

	public void setCouponSupCharge(java.math.BigDecimal couponSupCharge) {
		this.couponSupCharge = couponSupCharge;
	}

	@Column(name="coupon_sup_charge", nullable=true, length=22)	
	public java.math.BigDecimal getCouponSupCharge() {
		return couponSupCharge;
	}

	public void setCouponSupplier(java.math.BigDecimal couponSupplier) {
		this.couponSupplier = couponSupplier;
	}

	@Column(name="coupon_supplier", nullable=true, length=22)	
	public java.math.BigDecimal getCouponSupplier() {
		return couponSupplier;
	}

	public void setCouponRtnSupInd(String couponRtnSupInd) {
		this.couponRtnSupInd = couponRtnSupInd;
	}

	@Column(name="coupon_rtn_sup_ind", nullable=true, length=1)	
	public String getCouponRtnSupInd() {
		return couponRtnSupInd;
	}

	public void setAdhocInd(String adhocInd) {
		this.adhocInd = adhocInd;
	}

	@Column(name="adhoc_ind", nullable=true, length=1)	
	public String getAdhocInd() {
		return adhocInd;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	@Column(name="cancel_id", nullable=true, length=40)	
	public String getCancelId() {
		return cancelId;
	}

	public void setCancelDatetime(Date cancelDatetime) {
		this.cancelDatetime = cancelDatetime;
	}

	@Column(name="cancel_datetime", nullable=true, length=7)	
	public Date getCancelDatetime() {
		return cancelDatetime;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name="create_id", nullable=true, length=40)	
	public String getCreateId() {
		return createId;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name="create_datetime", nullable=true, length=7)	
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setApprovalDatetime(Date approvalDatetime) {
		this.approvalDatetime = approvalDatetime;
	}

	@Column(name="approval_datetime", nullable=true, length=7)	
	public Date getApprovalDatetime() {
		return approvalDatetime;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name="comments", nullable=true, length=255)	
	public String getComments() {
		return comments;
	}

	public void setPromotionDiscType(java.math.BigDecimal promotionDiscType) {
		this.promotionDiscType = promotionDiscType;
	}

	@Column(name="promotion_disc_type", nullable=false, length=22)	
	public java.math.BigDecimal getPromotionDiscType() {
		return promotionDiscType;
	}

	public void setBookInd(String bookInd) {
		this.bookInd = bookInd;
	}

	@Column(name="book_ind", nullable=false, length=1)	
	public String getBookInd() {
		return bookInd;
	}

	public void setDelayPromFg(String delayPromFg) {
		this.delayPromFg = delayPromFg;
	}

	@Column(name="delay_prom_fg", nullable=true, length=1)	
	public String getDelayPromFg() {
		return delayPromFg;
	}

	public void setPrirtyBcktRwrdCalc(String prirtyBcktRwrdCalc) {
		this.prirtyBcktRwrdCalc = prirtyBcktRwrdCalc;
	}

	@Column(name="prirty_bckt_rwrd_calc", nullable=true, length=2)	
	public String getPrirtyBcktRwrdCalc() {
		return prirtyBcktRwrdCalc;
	}

	public void setRfndAllwdFg(String rfndAllwdFg) {
		this.rfndAllwdFg = rfndAllwdFg;
	}

	@Column(name="rfnd_allwd_fg", nullable=false, length=1)	
	public String getRfndAllwdFg() {
		return rfndAllwdFg;
	}

	public void setEventId(java.math.BigDecimal eventId) {
		this.eventId = eventId;
	}

	@Column(name="event_id", nullable=true, length=22)	
	public java.math.BigDecimal getEventId() {
		return eventId;
	}

	public void setEopAction(String eopAction) {
		this.eopAction = eopAction;
	}

	@Column(name="eop_action", nullable=true, length=2)	
	public String getEopAction() {
		return eopAction;
	}

	public void setAllocId(java.math.BigDecimal allocId) {
		this.allocId = allocId;
	}

	@Column(name="alloc_id", nullable=true, length=22)	
	public java.math.BigDecimal getAllocId() {
		return allocId;
	}

	public void setSupplier(java.math.BigDecimal supplier) {
		this.supplier = supplier;
	}

	@Column(name="supplier", nullable=true, length=22)	
	public java.math.BigDecimal getSupplier() {
		return supplier;
	}

	public void setPriceChangeInd(String priceChangeInd) {
		this.priceChangeInd = priceChangeInd;
	}

	@Column(name="price_change_ind", nullable=true, length=1)	
	public String getPriceChangeInd() {
		return priceChangeInd;
	}

	public void setCostChangeInd(String costChangeInd) {
		this.costChangeInd = costChangeInd;
	}

	@Column(name="cost_change_ind", nullable=true, length=1)	
	public String getCostChangeInd() {
		return costChangeInd;
	}

	public void setDealInd(String dealInd) {
		this.dealInd = dealInd;
	}

	@Column(name="deal_ind", nullable=true, length=1)	
	public String getDealInd() {
		return dealInd;
	}

	public void setAllocInd(String allocInd) {
		this.allocInd = allocInd;
	}

	@Column(name="alloc_ind", nullable=true, length=1)	
	public String getAllocInd() {
		return allocInd;
	}

	public void setCogsAdjInd(String cogsAdjInd) {
		this.cogsAdjInd = cogsAdjInd;
	}

	@Column(name="cogs_adj_ind", nullable=true, length=1)	
	public String getCogsAdjInd() {
		return cogsAdjInd;
	}

	public void setSimplePromExtractInd(String simplePromExtractInd) {
		this.simplePromExtractInd = simplePromExtractInd;
	}

	@Column(name="simple_prom_extract_ind", nullable=true, length=1)	
	public String getSimplePromExtractInd() {
		return simplePromExtractInd;
	}

	public void setSimplePromId(java.math.BigDecimal simplePromId) {
		this.simplePromId = simplePromId;
	}

	@Column(name="simple_prom_id", nullable=true, length=22)	
	public java.math.BigDecimal getSimplePromId() {
		return simplePromId;
	}

	public void setCrossOutPriceInd(String crossOutPriceInd) {
		this.crossOutPriceInd = crossOutPriceInd;
	}

	@Column(name="cross_out_price_ind", nullable=true, length=1)	
	public String getCrossOutPriceInd() {
		return crossOutPriceInd;
	}

	public void setReturnWh(java.math.BigDecimal returnWh) {
		this.returnWh = returnWh;
	}

	@Column(name="return_wh", nullable=true, length=22)	
	public java.math.BigDecimal getReturnWh() {
		return returnWh;
	}

	public void setXxRtlOptBhv(String xxRtlOptBhv) {
		this.xxRtlOptBhv = xxRtlOptBhv;
	}

	@Column(name="xx_rtl_opt_bhv", nullable=true, length=1)	
	public String getXxRtlOptBhv() {
		return xxRtlOptBhv;
	}

	public void setXxTimePromInd(String xxTimePromInd) {
		this.xxTimePromInd = xxTimePromInd;
	}

	@Column(name="xx_time_prom_ind", nullable=true, length=1)	
	public String getXxTimePromInd() {
		return xxTimePromInd;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name="start_time", nullable=true, length=7)	
	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="end_time", nullable=true, length=7)	
	public Date getEndTime() {
		return endTime;
	}

	public void setPriceChangeStartDate(Date priceChangeStartDate) {
		this.priceChangeStartDate = priceChangeStartDate;
	}

	@Column(name="price_change_start_date", nullable=true, length=7)	
	public Date getPriceChangeStartDate() {
		return priceChangeStartDate;
	}

	public void setImmediateEndInd(String immediateEndInd) {
		this.immediateEndInd = immediateEndInd;
	}

	@Column(name="immediate_end_ind", nullable=true, length=1)	
	public String getImmediateEndInd() {
		return immediateEndInd;
	}

	public void setRewardByProm(String rewardByProm) {
		this.rewardByProm = rewardByProm;
	}

	@Column(name="reward_by_prom", nullable=true, length=1)	
	public String getRewardByProm() {
		return rewardByProm;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name="template_name", nullable=true, length=20)	
	public String getTemplateName() {
		return templateName;
	}

	public void setVoucherItem(String voucherItem) {
		this.voucherItem = voucherItem;
	}

	@Column(name="voucher_item", nullable=true, length=25)	
	public String getVoucherItem() {
		return voucherItem;
	}

	public void setRedeemItem(String redeemItem) {
		this.redeemItem = redeemItem;
	}

	@Column(name="redeem_item", nullable=true, length=25)	
	public String getRedeemItem() {
		return redeemItem;
	}

	public void setLmtRwrdValInTicket(java.math.BigDecimal lmtRwrdValInTicket) {
		this.lmtRwrdValInTicket = lmtRwrdValInTicket;
	}

	@Column(name="lmt_rwrd_val_in_ticket", nullable=true, length=22)	
	public java.math.BigDecimal getLmtRwrdValInTicket() {
		return lmtRwrdValInTicket;
	}

	public void setGrpMmbrAcct(String grpMmbrAcct) {
		this.grpMmbrAcct = grpMmbrAcct;
	}

	@Column(name="grp_mmbr_acct", nullable=true, length=6)	
	public String getGrpMmbrAcct() {
		return grpMmbrAcct;
	}

	public void setCrdtCalc(String crdtCalc) {
		this.crdtCalc = crdtCalc;
	}

	@Column(name="crdt_calc", nullable=true, length=3)	
	public String getCrdtCalc() {
		return crdtCalc;
	}

	public void setMaxSpendOnRwrdCalc(java.math.BigDecimal maxSpendOnRwrdCalc) {
		this.maxSpendOnRwrdCalc = maxSpendOnRwrdCalc;
	}

	@Column(name="max_spend_on_rwrd_calc", nullable=true, length=22)	
	public java.math.BigDecimal getMaxSpendOnRwrdCalc() {
		return maxSpendOnRwrdCalc;
	}

	public void setMinTresholdForTrig(java.math.BigDecimal minTresholdForTrig) {
		this.minTresholdForTrig = minTresholdForTrig;
	}

	@Column(name="min_treshold_for_trig", nullable=true, length=22)	
	public java.math.BigDecimal getMinTresholdForTrig() {
		return minTresholdForTrig;
	}

	public void setOnlinePromInd(String onlinePromInd) {
		this.onlinePromInd = onlinePromInd;
	}

	@Column(name="online_prom_ind", nullable=true, length=1)	
	public String getOnlinePromInd() {
		return onlinePromInd;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	@Column(name="coupon_type", nullable=true, length=2)	
	public String getCouponType() {
		return couponType;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	@Column(name="voucher_code", nullable=true, length=25)	
	public String getVoucherCode() {
		return voucherCode;
	}

	public void setDepRoleInd(String depRoleInd) {
		this.depRoleInd = depRoleInd;
	}

	@Column(name="dep_role_ind", nullable=true, length=1)	
	public String getDepRoleInd() {
		return depRoleInd;
	}

	public void setSuspendedInd(String suspendedInd) {
		this.suspendedInd = suspendedInd;
	}

	@Column(name="suspended_ind", nullable=true, length=1)	
	public String getSuspendedInd() {
		return suspendedInd;
	}

	public void setCompenCode(String compenCode) {
		this.compenCode = compenCode;
	}

	@Column(name="compen_code", nullable=true, length=10)	
	public String getCompenCode() {
		return compenCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
