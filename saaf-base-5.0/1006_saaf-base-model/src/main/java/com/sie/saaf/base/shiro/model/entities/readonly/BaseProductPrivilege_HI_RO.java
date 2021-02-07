package com.sie.saaf.base.shiro.model.entities.readonly;

/**
 * @author ZhangJun
 * @createTime 2018-01-16 20:02
 * @description
 */
public class BaseProductPrivilege_HI_RO {

	public static final String QUERY_PRODUCT_PRIVILEGE_RECORD = "select * from (SELECT distinct\n" +
			"  productInfo.channel_code as Code,\n" +
			"  productInfo.item_code as itemCodechannel,\n" +
			"  productInfo.org_id AS orgId,\n" +
			"  detail.ACCESS_TYPE AS accessType,\n" +
			"  productInfo.item_id AS itemId,\n" +
			"  detail.enabled as enabled,\n" +
			"  max(case detail.privilege_detail_code when 'CUSTOMER_ORDER_FLAG' then detail.privilege_detail_value else null end) as 'customerOrderFlag',\n" +
			"  max(case detail.privilege_detail_code when 'DEFAULT_TYPE' then detail.privilege_detail_value else null end) as 'defaultType',\n" +
			"  max(case detail.privilege_detail_code when 'GLOBAL_USER_VALID_FLAG' then detail.privilege_detail_value else null end) as 'globalUserValidFlag',\n" +
			"  max(case detail.privilege_detail_code when 'PERSON_USER_VALID_FLAG' then detail.privilege_detail_value else null end) as 'personUserValidFlag',\n" +
			"  max(case detail.privilege_detail_code when 'DEALER_USER_VALID_FLAG' then detail.privilege_detail_value else null end) as 'dealerUserValidFlag',\n" +
			"  max(case detail.privilege_detail_code when 'FG_VALID_FLAG' then detail.privilege_detail_value else null end) as 'fgValidFlag',\n" +
			"  max(case detail.privilege_detail_code when 'PROMOTION_VALID_FLAG' then detail.privilege_detail_value else null end) as 'promotionValidFlag',\n" +
			"  max(case detail.privilege_detail_code when 'IS_BCREIMBURSEHEO' then detail.privilege_detail_value else null end) as 'isBcreimburseheo',\n" +
			"  max(case detail.privilege_detail_code when 'EXPENSE_PRICE_LIST_ID' then detail.privilege_detail_value else null end) as 'expensePriceListId',\n" +
			"  max(case detail.privilege_detail_code when 'IS_LINK_CASH_ORDER' then detail.privilege_detail_value else null end) as 'isLinkCashOrder',\n" +
			"  max(case detail.privilege_detail_code when 'EXPENSE_ACCOUNT' then detail.privilege_detail_value else null end) as 'expenseAccount',\n" +
			"  max(case detail.privilege_detail_code when 'IS_QUOTA_CONTROL' then detail.privilege_detail_value else null end) as 'isQuotaControl',\n" +
			"  max(case detail.privilege_detail_code when 'IS_PROMOTION_CONTROL' then detail.privilege_detail_value else null end) as 'isPromotionControl',\n" +
			"  max(case detail.privilege_detail_code when 'IS_PAYMENT_CONTROL' then detail.privilege_detail_value else null end) as 'isPaymentControl',\n" +
			"  max(case detail.privilege_detail_code when 'IS_MOD_AMOUNT' then detail.privilege_detail_value else null end) as 'isModAmount',\n" +
			"  max(case detail.privilege_detail_code when 'PREFERRED_PAYMENT_ACCOUNT' then detail.privilege_detail_value else null end) as 'preferredPaymentAccount',\n" +
			"  max(case detail.privilege_detail_code when 'BALANCE_CHECK_IN_APPROVE' then detail.privilege_detail_value else null end) as 'balanceCheckInApprove',\n" +
			"  max(case detail.privilege_detail_code when 'VIEW_BALANCE_IN_OD_CREATE' then detail.privilege_detail_value else null end) as 'viewBalanceInOdCreate',\n" +
			"  max(case detail.privilege_detail_code when 'VIEW_BALANCE_IN_APPROVE' then detail.privilege_detail_value else null end) as 'viewBalanceInApprove',\n" +
			"  max(case detail.privilege_detail_code when 'BALANCE_CONTROL_OD_SUBMIT' then detail.privilege_detail_value else null end) as 'balanceControlOdSubmit',\n" +
			"  max(case detail.privilege_detail_code when 'BALANCE_CONTROL_OD_APPROVE' then detail.privilege_detail_value else null end) as 'balanceControlOdApprove',\n" +
			"  max(case detail.privilege_detail_code when 'CARRIER_IN_APPROVE' then detail.privilege_detail_value else null end) as 'carrierInApprove',\n" +
			"  max(case detail.privilege_detail_code when 'IS_PURCHASE_RULE_CONTROL' then detail.privilege_detail_value else null end) as 'isPurchaseRuleControl'\n" +
			"FROM\n" +
			"  base_product_info productInfo\n" +
			"  left join (\n" +
			"    select record.org_id,record.access_type,record.business_code,record.business_table_name,record.enabled,detail.privilege_detail_code,detail.privilege_detail_value\n" +
			"    from base_privilege_record record,base_privilege_detail detail \n" +
			"    where record.privilege_record_id = detail.privilege_record_id and record.business_table_name =:businessTableName and record.enabled=:enabled and record.access_type=:accessType) detail\n" +
			"  on productInfo.item_id=detail.business_code AND productInfo.org_id=detail.org_id\n" +
			"  GROUP BY productInfo.channel_code,productInfo.item_code,detail.org_id,detail.ACCESS_TYPE,detail.business_code,detail.enabled) privilege WHERE 1=1 \n";


	/*常量，记录保存在base_privilege_detail中的字段名*/
	public static final String CUSTOMER_ORDER_FLAG = "customerOrderFlag";
	public static final String DEFAULT_TYPE = "defaultType";
	public static final String GLOBAL_USER_VALID_FLAG = "globalUserValidFlag";
	public static final String PERSON_USER_VALID_FLAG = "personUserValidFlag";
	public static final String DEALER_USER_VALID_FLAG = "dealerUserValidFlag";
	public static final String FG_VALID_FLAG = "fgValidFlag";
	public static final String PROMOTION_VALID_FLAG = "promotionValidFlag";
	public static final String IS_BCREIMBURSEHEO = "isBcreimburseheo";
	public static final String EXPENSE_PRICE_LIST_ID = "expensePriceListId";
	public static final String IS_LINK_CASH_ORDER = "isLinkCashOrder";
	public static final String EXPENSE_ACCOUNT = "expenseAccount";
	public static final String IS_QUOTA_CONTROL = "isQuotaControl";
	public static final String IS_PROMOTION_CONTROL = "isPromotionControl";
	public static final String IS_PAYMENT_CONTROL = "isPaymentControl";
	public static final String IS_MOD_AMOUNT = "isModAmount";
	public static final String PREFERRED_PAYMENT_ACCOUNT = "preferredPaymentAccount";
	public static final String BALANCE_CHECK_IN_APPROVE = "balanceCheckInApprove";
	public static final String VIEW_BALANCE_IN_OD_CREATE = "viewBalanceInOdCreate";
	public static final String VIEW_BALANCE_IN_APPROVE = "viewBalanceInApprove";
	public static final String BALANCE_CONTROL_OD_SUBMIT = "balanceControlOdSubmit";
	public static final String BALANCE_CONTROL_OD_APPROVE = "balanceControlOdApprove";
	public static final String CARRIER_IN_APPROVE = "carrierInApprove";
	public static final String IS_PURCHASE_RULE_CONTROL = "isPurchaseRuleControl";

	private String channelCode;//渠道编码
	private String itemCode;//产品编码

	private Integer orgId;//OU组织
	private String accessType;//访问类型
	private String itemId;//业务表值（ID或Code等）
	private String enabled;//是否启用

	/*base_privilege_detail*/
	private String customerOrderFlag; //可否下单
	private String defaultType; //用于在不同渠道订单权限中，指定缺省订单类型，ORDER--缺省订单，RETURN-缺省退货订单
	private String globalUserValidFlag; //可下单用户类型：10 全局用户是否可用
	private String personUserValidFlag; //可下单用户类型：20 员工用户是否可用
	private String dealerUserValidFlag; //可下单用户类型：30 经销商用户是否可用
	private String fgValidFlag; //可下单物料类型：FG 产成品是否可用
	private String promotionValidFlag; //可下单物料类型：PROMOTION 促销品是否可用
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

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getCustomerOrderFlag() {
		return customerOrderFlag;
	}

	public void setCustomerOrderFlag(String customerOrderFlag) {
		this.customerOrderFlag = customerOrderFlag;
	}

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}

	public String getGlobalUserValidFlag() {
		return globalUserValidFlag;
	}

	public void setGlobalUserValidFlag(String globalUserValidFlag) {
		this.globalUserValidFlag = globalUserValidFlag;
	}

	public String getPersonUserValidFlag() {
		return personUserValidFlag;
	}

	public void setPersonUserValidFlag(String personUserValidFlag) {
		this.personUserValidFlag = personUserValidFlag;
	}

	public String getDealerUserValidFlag() {
		return dealerUserValidFlag;
	}

	public void setDealerUserValidFlag(String dealerUserValidFlag) {
		this.dealerUserValidFlag = dealerUserValidFlag;
	}

	public String getFgValidFlag() {
		return fgValidFlag;
	}

	public void setFgValidFlag(String fgValidFlag) {
		this.fgValidFlag = fgValidFlag;
	}

	public String getPromotionValidFlag() {
		return promotionValidFlag;
	}

	public void setPromotionValidFlag(String promotionValidFlag) {
		this.promotionValidFlag = promotionValidFlag;
	}

	public String getIsBcreimburseheo() {
		return isBcreimburseheo;
	}

	public void setIsBcreimburseheo(String isBcreimburseheo) {
		this.isBcreimburseheo = isBcreimburseheo;
	}

	public Integer getExpensePriceListId() {
		return expensePriceListId;
	}

	public void setExpensePriceListId(Integer expensePriceListId) {
		this.expensePriceListId = expensePriceListId;
	}

	public String getIsLinkCashOrder() {
		return isLinkCashOrder;
	}

	public void setIsLinkCashOrder(String isLinkCashOrder) {
		this.isLinkCashOrder = isLinkCashOrder;
	}

	public Integer getExpenseAccount() {
		return expenseAccount;
	}

	public void setExpenseAccount(Integer expenseAccount) {
		this.expenseAccount = expenseAccount;
	}

	public String getIsQuotaControl() {
		return isQuotaControl;
	}

	public void setIsQuotaControl(String isQuotaControl) {
		this.isQuotaControl = isQuotaControl;
	}

	public String getIsPromotionControl() {
		return isPromotionControl;
	}

	public void setIsPromotionControl(String isPromotionControl) {
		this.isPromotionControl = isPromotionControl;
	}

	public String getIsPaymentControl() {
		return isPaymentControl;
	}

	public void setIsPaymentControl(String isPaymentControl) {
		this.isPaymentControl = isPaymentControl;
	}

	public String getIsModAmount() {
		return isModAmount;
	}

	public void setIsModAmount(String isModAmount) {
		this.isModAmount = isModAmount;
	}

	public String getPreferredPaymentAccount() {
		return preferredPaymentAccount;
	}

	public void setPreferredPaymentAccount(String preferredPaymentAccount) {
		this.preferredPaymentAccount = preferredPaymentAccount;
	}

	public String getBalanceCheckInApprove() {
		return balanceCheckInApprove;
	}

	public void setBalanceCheckInApprove(String balanceCheckInApprove) {
		this.balanceCheckInApprove = balanceCheckInApprove;
	}

	public String getViewBalanceInOdCreate() {
		return viewBalanceInOdCreate;
	}

	public void setViewBalanceInOdCreate(String viewBalanceInOdCreate) {
		this.viewBalanceInOdCreate = viewBalanceInOdCreate;
	}

	public String getViewBalanceInApprove() {
		return viewBalanceInApprove;
	}

	public void setViewBalanceInApprove(String viewBalanceInApprove) {
		this.viewBalanceInApprove = viewBalanceInApprove;
	}

	public String getBalanceControlOdSubmit() {
		return balanceControlOdSubmit;
	}

	public void setBalanceControlOdSubmit(String balanceControlOdSubmit) {
		this.balanceControlOdSubmit = balanceControlOdSubmit;
	}

	public String getBalanceControlOdApprove() {
		return balanceControlOdApprove;
	}

	public void setBalanceControlOdApprove(String balanceControlOdApprove) {
		this.balanceControlOdApprove = balanceControlOdApprove;
	}

	public String getCarrierInApprove() {
		return carrierInApprove;
	}

	public void setCarrierInApprove(String carrierInApprove) {
		this.carrierInApprove = carrierInApprove;
	}

	public String getIsPurchaseRuleControl() {
		return isPurchaseRuleControl;
	}

	public void setIsPurchaseRuleControl(String isPurchaseRuleControl) {
		this.isPurchaseRuleControl = isPurchaseRuleControl;
	}
}
