package com.sie.saaf.base.user.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * BaseChannelPrivilegeEntity_HI Entity Object
 * Wed Mar 14 18:50:51 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_channel_privilege")
public class BaseChannelPrivilegeEntity_HI {
    private Integer channelPrivilegeId; //主键
    private Integer orgId; //OU组织Id
    private String accessType; //访问类型
    private String channelType; //渠道类型
    private Integer transactionTypeId; //交易Id、物料Id
    private String itemCode; //产品编码
    private String validFlag; //是否有效
    private String customerOrderFlag; //可否下单
    private String defaultType; //用于在不同渠道订单权限中，指定缺省订单类型，ORDER--缺省订单，RETURN-缺省退货订单
    private String attributeCategory; //ATTRIBUTE_CATEGORY
    private String attribute1; //可下单用户类型：10 全局用户是否可用
    private String attribute2; //可下单用户类型：20 员工用户是否可用
    private String attribute3; //可下单用户类型：30 经销商用户是否可用
    private String attribute4; //可下单物料类型：FG 产成品是否可用
    private String attribute5; //可下单物料类型：PROMOTION 促销品是否可用
    private String attribute6; //ATTRIBUTE6
    private String attribute7; //ATTRIBUTE7
    private String attribute8; //ATTRIBUTE8
    private String attribute9; //ATTRIBUTE9
    private String attribute10; //ATTRIBUTE10
    private String isBcreimburseheo; //是否关联费用申请（Y/N）
    private Integer expensePriceListId; //费用价目表，订单计入费用科目时使用，当IS_BCREIMBURSEHEO字段为Y时必填
    private String isLinkCashOrder; //是否关联现金订单（Y/N）
    private Integer expenseAccount; //费用科目code
    private String isQuotaControl; //是否配额控制（Y/N）
    private String isPromotionControl; //是否促销规则控制（Y/N）
    private String isPaymentControl; //是否控制现金比例（Y/N）
    private String isModAmount; //是否允许调整货款金额（Y/N）
    private String preferredPaymentAccount; //首选付款科目
    private String balanceCheckInApprove; //审批中查看帐户余额（Y/N）
    private String viewBalanceInOdCreate; //订单创建查看可用库存（Y/N）
    private String viewBalanceInApprove; //订单审批查看可用库存（Y/N）
    private String balanceControlOdSubmit; //库存控制订单提交（Y/N）
    private String balanceControlOdApprove; //库存控制订单审批（Y/N）
    private String carrierInApprove; //订单审批选择承运商（Y/N）
    private String isPurchaseRuleControl; //是否下单规则控制（Y/N）
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setChannelPrivilegeId(Integer channelPrivilegeId) {
		this.channelPrivilegeId = channelPrivilegeId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_CHANNEL_PRIVILEGE", sequenceName = "SEQ_BASE_CHANNEL_PRIVILEGE", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_CHANNEL_PRIVILEGE", strategy = GenerationType.SEQUENCE)		
	@Column(name = "channel_privilege_id", nullable = false, length = 11)	
	public Integer getChannelPrivilegeId() {
		return channelPrivilegeId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name = "org_id", nullable = true, length = 11)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	@Column(name = "access_type", nullable = true, length = 10)	
	public String getAccessType() {
		return accessType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name = "channel_type", nullable = true, length = 30)	
	public String getChannelType() {
		return channelType;
	}

	public void setTransactionTypeId(Integer transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	@Column(name = "transaction_type_id", nullable = true, length = 11)	
	public Integer getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "item_code", nullable = true, length = 30)	
	public String getItemCode() {
		return itemCode;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	@Column(name = "valid_flag", nullable = true, length = 5)	
	public String getValidFlag() {
		return validFlag;
	}

	public void setCustomerOrderFlag(String customerOrderFlag) {
		this.customerOrderFlag = customerOrderFlag;
	}

	@Column(name = "customer_order_flag", nullable = true, length = 5)	
	public String getCustomerOrderFlag() {
		return customerOrderFlag;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}

	@Column(name = "default_type", nullable = true, length = 30)	
	public String getDefaultType() {
		return defaultType;
	}

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name = "attribute_category", nullable = true, length = 30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name = "attribute1", nullable = true, length = 150)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name = "attribute2", nullable = true, length = 150)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name = "attribute3", nullable = true, length = 150)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name = "attribute4", nullable = true, length = 150)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name = "attribute5", nullable = true, length = 150)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name = "attribute6", nullable = true, length = 150)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name = "attribute7", nullable = true, length = 150)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name = "attribute8", nullable = true, length = 150)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name = "attribute9", nullable = true, length = 150)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name = "attribute10", nullable = true, length = 150)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setIsBcreimburseheo(String isBcreimburseheo) {
		this.isBcreimburseheo = isBcreimburseheo;
	}

	@Column(name = "is_bcreimburseheo", nullable = true, length = 5)	
	public String getIsBcreimburseheo() {
		return isBcreimburseheo;
	}

	public void setExpensePriceListId(Integer expensePriceListId) {
		this.expensePriceListId = expensePriceListId;
	}

	@Column(name = "expense_price_list_id", nullable = true, length = 11)	
	public Integer getExpensePriceListId() {
		return expensePriceListId;
	}

	public void setIsLinkCashOrder(String isLinkCashOrder) {
		this.isLinkCashOrder = isLinkCashOrder;
	}

	@Column(name = "is_link_cash_order", nullable = true, length = 5)	
	public String getIsLinkCashOrder() {
		return isLinkCashOrder;
	}

	public void setExpenseAccount(Integer expenseAccount) {
		this.expenseAccount = expenseAccount;
	}

	@Column(name = "expense_account", nullable = true, length = 11)	
	public Integer getExpenseAccount() {
		return expenseAccount;
	}

	public void setIsQuotaControl(String isQuotaControl) {
		this.isQuotaControl = isQuotaControl;
	}

	@Column(name = "is_quota_control", nullable = true, length = 5)	
	public String getIsQuotaControl() {
		return isQuotaControl;
	}

	public void setIsPromotionControl(String isPromotionControl) {
		this.isPromotionControl = isPromotionControl;
	}

	@Column(name = "is_promotion_control", nullable = true, length = 5)	
	public String getIsPromotionControl() {
		return isPromotionControl;
	}

	public void setIsPaymentControl(String isPaymentControl) {
		this.isPaymentControl = isPaymentControl;
	}

	@Column(name = "is_payment_control", nullable = true, length = 5)	
	public String getIsPaymentControl() {
		return isPaymentControl;
	}

	public void setIsModAmount(String isModAmount) {
		this.isModAmount = isModAmount;
	}

	@Column(name = "is_mod_amount", nullable = true, length = 5)	
	public String getIsModAmount() {
		return isModAmount;
	}

	public void setPreferredPaymentAccount(String preferredPaymentAccount) {
		this.preferredPaymentAccount = preferredPaymentAccount;
	}

	@Column(name = "preferred_payment_account", nullable = true, length = 5)	
	public String getPreferredPaymentAccount() {
		return preferredPaymentAccount;
	}

	public void setBalanceCheckInApprove(String balanceCheckInApprove) {
		this.balanceCheckInApprove = balanceCheckInApprove;
	}

	@Column(name = "balance_check_in_approve", nullable = true, length = 5)	
	public String getBalanceCheckInApprove() {
		return balanceCheckInApprove;
	}

	public void setViewBalanceInOdCreate(String viewBalanceInOdCreate) {
		this.viewBalanceInOdCreate = viewBalanceInOdCreate;
	}

	@Column(name = "view_balance_in_od_create", nullable = true, length = 5)	
	public String getViewBalanceInOdCreate() {
		return viewBalanceInOdCreate;
	}

	public void setViewBalanceInApprove(String viewBalanceInApprove) {
		this.viewBalanceInApprove = viewBalanceInApprove;
	}

	@Column(name = "view_balance_in_approve", nullable = true, length = 5)	
	public String getViewBalanceInApprove() {
		return viewBalanceInApprove;
	}

	public void setBalanceControlOdSubmit(String balanceControlOdSubmit) {
		this.balanceControlOdSubmit = balanceControlOdSubmit;
	}

	@Column(name = "balance_control_od_submit", nullable = true, length = 5)	
	public String getBalanceControlOdSubmit() {
		return balanceControlOdSubmit;
	}

	public void setBalanceControlOdApprove(String balanceControlOdApprove) {
		this.balanceControlOdApprove = balanceControlOdApprove;
	}

	@Column(name = "balance_control_od_approve", nullable = true, length = 5)	
	public String getBalanceControlOdApprove() {
		return balanceControlOdApprove;
	}

	public void setCarrierInApprove(String carrierInApprove) {
		this.carrierInApprove = carrierInApprove;
	}

	@Column(name = "carrier_in_approve", nullable = true, length = 5)	
	public String getCarrierInApprove() {
		return carrierInApprove;
	}

	public void setIsPurchaseRuleControl(String isPurchaseRuleControl) {
		this.isPurchaseRuleControl = isPurchaseRuleControl;
	}

	@Column(name = "is_purchase_rule_control", nullable = true, length = 5)	
	public String getIsPurchaseRuleControl() {
		return isPurchaseRuleControl;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
