package com.sie.watsons.base.clause.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaClauseTreeEntity_HI_RO Entity Object
 * Thu May 23 16:15:13 CST 2019  Auto Generate
 */

public class TtaClauseTreeEntity_HI_RO {
	/**
	 * 查询树级结构
	 */
	public static final String QUERY_CLAUSE_TREE_LIST ="select \n" +
			"    act.clause_cn clauseCn\n" +
			"   ,act.clause_id clauseId\n" +
			"   ,act.team_framework_id teamFrameworkId\n" +
			"   ,nvl(act.p_code,0) pCode\n" +
			"   ,code\n" +
			"   ,act.is_Leaf isLeaf\n" +
			"   ,act.order_No orderNo\n" +
			"   from   tta_clause_tree act where nvl(act.delete_flag,0)=0";
	/**
	 * 查询单条树
	 */
	public static final String QUERY_CLAUSE_TREE_ONE ="select act.clause_id clauseId\n" +
			"               ,act.team_framework_id teamFrameworkId\n" +
			"               ,act.clause_code clauseCode\n" +
			"               ,act.clause_cn clauseCn\n" +
			"               ,act.clause_en clauseEn\n" +
			"               ,act.status status\n" +
			"               ,act.parent_id parentId\n" +
			"               ,act.pass_date passDate\n" +
			"               ,act.code code\n" +
			"               ,act.is_Global_Variable isGlobalVariable\n" +
			"               ,act.is_Global_tta isGlobalTta\n" +
			"               ,act.is_Global_fee isGlobalFee\n" +
			"               ,act.p_code pCode\n" +
			"               ,act.effective_date effectiveDate\n" +
			"               ,act.is_leaf isLeaf\n" +
			"               ,act.is_hierarchy_show isHierarchyShow\n" +
			"               ,act.payment_method paymentMethod\n" +
			"               ,act.business_type businessType\n" +
			"               ,act.expression_value expressionValue\n" +
			"               ,act.expression_value_fee expressionValueFee\n" +
			"               ,act.expression_value_tta expressionValueTta\n" +
			"               ,act.global_value_con globalValueCon\n" +
			"               ,act.expression_value_con expressionValueCon\n" +
			"               ,act.is_default isDefault\n" +
			"               ,get_clause_analysis(act.is_global_variable,act.expression_value) expressionValueText\n" +
			"               ,get_clause_analysis(act.is_global_tta,act.expression_value_tta)  expressionValueTtaText\n" +
			"               ,get_clause_analysis(act.is_global_fee,act.expression_value_fee)  expressionValueFeeText\n" +
			"               ,act.business_version businessVersion\n" +
			"               ,act.order_no orderNo\n" +
			"               ,act.old_order_no oldOrderNo\n" +
			"               ,act.created_by createdBy\n" +
			"               ,act.last_updated_by lastUpdatedBy\n" +
			"               ,act.last_update_date lastUpdateDate\n" +
			"               ,act.creation_date creationDate\n" +
			"               ,act.last_update_login lastUpdateLogin\n" +
			"               ,act.version_num versionNum\n" +
			"               ,act.delete_flag  deleteFlag\n" +
			"               ,pact.clause_cn clauseCnP\n" +
			"               ,act.global_value_tta globalValueTta\n" +
			"               ,act.global_value_fee globalValueFee\n" +
			"               ,act.global_value globalValue\n" +
			"               ,act.term_category termCategory\n" +
			"               from  tta_clause_tree act,\n" +
			"                    tta_clause_tree pact\n" +
			"               where  act.p_code = pact.code(+) and nvl(act.delete_flag,0)=0 and act.team_framework_id = pact.team_framework_id(+)";

	/**
	 * 查询单条树
	 */
	public static final String QUERY_CLAUSE_TREE_CHANGE ="select act.clause_id clauseId\n" +
			"               ,act.team_framework_id teamFrameworkId\n" +
			"               ,act.clause_code clauseCode\n" +
			"               ,act.clause_cn clauseCn\n" +
			"               ,act.clause_en clauseEn\n" +
			"               ,act.status status\n" +
			"               ,act.parent_id parentId\n" +
			"               ,act.pass_date passDate\n" +
			"               ,act.code code\n" +
			"               ,act.is_Global_Variable isGlobalVariable\n" +
			"               ,act.is_Global_tta isGlobalTta\n" +
			"               ,act.is_Global_fee isGlobalFee\n" +
			"               ,act.p_code pCode\n" +
			"               ,act.effective_date effectiveDate\n" +
			"               ,act.is_leaf isLeaf\n" +
			"               ,act.is_hierarchy_show isHierarchyShow\n" +
			"               ,act.payment_method paymentMethod\n" +
			"               ,act.business_type businessType\n" +
			"               ,act.expression_value expressionValue\n" +
			"               ,act.expression_value_fee expressionValueFee\n" +
			"               ,act.expression_value_tta expressionValueTta\n" +
			"               ,act.business_version businessVersion\n" +
			"               ,act.order_no orderNo\n" +
			"               ,act.old_order_no oldOrderNo\n" +
			"               ,act.created_by createdBy\n" +
			"               ,act.last_updated_by lastUpdatedBy\n" +
			"               ,act.last_update_date lastUpdateDate\n" +
			"               ,act.creation_date creationDate\n" +
			"               ,act.last_update_login lastUpdateLogin\n" +
			"               ,act.version_num versionNum\n" +
			"               ,act.delete_flag  deleteFlag\n" +
			"               ,act.global_value_tta globalValueTta\n" +
			"               ,act.global_value_fee globalValueFee\n" +
			"               ,act.global_value globalValue\n" +
			"               ,act.term_category termCategory\n" +
			"               from  tta_clause_tree act,\n" +
			"                    tta_clause_tree_h acth\n" +
			"               where   act.clause_id = acth.resouce_id";


	private Integer clauseId;
	private Integer teamFrameworkId;
	private String clauseCode;
	private String clauseCn;
	private String clauseCnP;
	private Integer  isChange;
	private String  isGlobalVariable;
	private String code;
	private String pCode;
	private String clauseEn;
	private Integer status;
	private Integer parentId;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date passDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date effectiveDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Integer isLeaf;
	private Integer isHierarchyShow;
	private String paymentMethod;
	private String businessType;
	private String expressionValue;
	private Integer businessVersion;
	private String orderNo;
	private String  oldOrderNo;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;
	private Integer deleteFlag;
	private String  isGlobalFee;
	private String  isGlobalTta;
	private String expressionValueFee;
	private String expressionValueTta;
	private String termCategory;
	private String globalValue;
	private String globalValueTta;
	private String globalValueFee;
	private String expressionValueText;
	private String expressionValueFeeText;
	private String expressionValueTtaText;
	private String globalValueCon;
	private String expressionValueCon;
	private String isDefault;
	public void setClauseId(Integer clauseId) {
		this.clauseId = clauseId;
	}


	public Integer getClauseId() {
		return clauseId;
	}

	public void setTeamFrameworkId(Integer teamFrameworkId) {
		this.teamFrameworkId = teamFrameworkId;
	}


	public Integer getTeamFrameworkId() {
		return teamFrameworkId;
	}

	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}


	public String getClauseCode() {
		return clauseCode;
	}

	public void setClauseCn(String clauseCn) {
		this.clauseCn = clauseCn;
	}


	public String getClauseCn() {
		return clauseCn;
	}

	public void setClauseEn(String clauseEn) {
		this.clauseEn = clauseEn;
	}


	public String getClauseEn() {
		return clauseEn;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getStatus() {
		return status;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	public Integer getParentId() {
		return parentId;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}


	public Date getPassDate() {
		return passDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}


	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}


	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsHierarchyShow(Integer isHierarchyShow) {
		this.isHierarchyShow = isHierarchyShow;
	}


	public Integer getIsHierarchyShow() {
		return isHierarchyShow;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public String getBusinessType() {
		return businessType;
	}

	public void setExpressionValue(String expressionValue) {
		this.expressionValue = expressionValue;
	}


	public String getExpressionValue() {
		return expressionValue;
	}

	public void setBusinessVersion(Integer businessVersion) {
		this.businessVersion = businessVersion;
	}


	public Integer getBusinessVersion() {
		return businessVersion;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getOrderNo() {
		return orderNo;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getClauseCnP() {
		return clauseCnP;
	}

	public void setClauseCnP(String clauseCnP) {
		this.clauseCnP = clauseCnP;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPCode() {
		return pCode;
	}

	public void setPCode(String pCode) {
		this.pCode = pCode;
	}

	public String getIsGlobalVariable() {
		return isGlobalVariable;
	}

	public void setIsGlobalVariable(String isGlobalVariable) {
		this.isGlobalVariable = isGlobalVariable;
	}

	public Integer getIsChange() {
		return isChange;
	}

	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}

	public String getIsGlobalFee() {
		return isGlobalFee;
	}

	public void setIsGlobalFee(String isGlobalFee) {
		this.isGlobalFee = isGlobalFee;
	}

	public String getIsGlobalTta() {
		return isGlobalTta;
	}

	public void setIsGlobalTta(String isGlobalTta) {
		this.isGlobalTta = isGlobalTta;
	}

	public String getExpressionValueFee() {
		return expressionValueFee;
	}

	public void setExpressionValueFee(String expressionValueFee) {
		this.expressionValueFee = expressionValueFee;
	}

	public String getExpressionValueTta() {
		return expressionValueTta;
	}

	public void setExpressionValueTta(String expressionValueTta) {
		this.expressionValueTta = expressionValueTta;
	}

	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}

	public String getTermCategory() {
		return termCategory;
	}

	public void setTermCategory(String termCategory) {
		this.termCategory = termCategory;
	}

	public String getGlobalValue() {
		return globalValue;
	}

	public void setGlobalValue(String globalValue) {
		this.globalValue = globalValue;
	}

	public String getGlobalValueTta() {
		return globalValueTta;
	}

	public void setGlobalValueTta(String globalValueTta) {
		this.globalValueTta = globalValueTta;
	}

	public String getGlobalValueFee() {
		return globalValueFee;
	}

	public void setGlobalValueFee(String globalValueFee) {
		this.globalValueFee = globalValueFee;
	}

	public String getExpressionValueText() {
		return expressionValueText;
	}

	public void setExpressionValueText(String expressionValueText) {
		this.expressionValueText = expressionValueText;
	}

	public String getExpressionValueFeeText() {
		return expressionValueFeeText;
	}

	public void setExpressionValueFeeText(String expressionValueFeeText) {
		this.expressionValueFeeText = expressionValueFeeText;
	}

	public String getExpressionValueTtaText() {
		return expressionValueTtaText;
	}

	public void setExpressionValueTtaText(String expressionValueTtaText) {
		this.expressionValueTtaText = expressionValueTtaText;
	}

	public String getGlobalValueCon() {
		return globalValueCon;
	}

	public void setGlobalValueCon(String globalValueCon) {
		this.globalValueCon = globalValueCon;
	}

	public String getExpressionValueCon() {
		return expressionValueCon;
	}

	public void setExpressionValueCon(String expressionValueCon) {
		this.expressionValueCon = expressionValueCon;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}
