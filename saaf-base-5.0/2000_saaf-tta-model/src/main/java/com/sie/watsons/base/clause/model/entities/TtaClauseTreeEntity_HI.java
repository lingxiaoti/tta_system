package com.sie.watsons.base.clause.model.entities;

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
 * TtaClauseTreeEntity_HI Entity Object
 * Thu May 23 16:15:13 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_CLAUSE_TREE")
public class TtaClauseTreeEntity_HI {
    private Integer clauseId;
    private Integer teamFrameworkId;
    private String clauseCode;
    private String clauseCn;
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
	private String  isGlobalVariable;
    private Integer businessVersion;
    private String orderNo;
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
	private String code;
	private String  pCode ;
	private Integer  oldClauseId ;
	private String  oldOrderNo;
	private String  isGlobalFee;
	private String  isGlobalTta;
	private String expressionValueFee;
	private String expressionValueTta;
	private String termCategory;
	private String globalValue;
	private String globalValueTta;
	private String globalValueFee;
	private String globalValueCon;
	private String expressionValueCon;
	private String isDefault;

	public void setClauseId(Integer clauseId) {
		this.clauseId = clauseId;
	}

	@Id	
	@SequenceGenerator(name="ttaClauseTreeEntity_HISeqGener", sequenceName="SEQ_TTA_CLAUSE_TREE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="ttaClauseTreeEntity_HISeqGener",strategy=GenerationType.SEQUENCE)
	@Column(name="clause_id", nullable=false, length=22)	
	public Integer getClauseId() {
		return clauseId;
	}

	public void setTeamFrameworkId(Integer teamFrameworkId) {
		this.teamFrameworkId = teamFrameworkId;
	}

	@Column(name="team_framework_id", nullable=true, length=22)	
	public Integer getTeamFrameworkId() {
		return teamFrameworkId;
	}

	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}

	@Column(name="clause_code", nullable=true, length=50)
	public String getClauseCode() {
		return clauseCode;
	}

	public void setClauseCn(String clauseCn) {
		this.clauseCn = clauseCn;
	}

	@Column(name="clause_cn", nullable=false, length=200)
	public String getClauseCn() {
		return clauseCn;
	}

	public void setClauseEn(String clauseEn) {
		this.clauseEn = clauseEn;
	}

	@Column(name="clause_en", nullable=true, length=200)
	public String getClauseEn() {
		return clauseEn;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=22)	
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

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	@Column(name="pass_date", nullable=true, length=7)	
	public Date getPassDate() {
		return passDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name="effective_date", nullable=true, length=7)	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name="is_leaf", nullable=true, length=22)
	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsHierarchyShow(Integer isHierarchyShow) {
		this.isHierarchyShow = isHierarchyShow;
	}

	@Column(name="is_hierarchy_show", nullable=false, length=22)	
	public Integer getIsHierarchyShow() {
		return isHierarchyShow;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name="payment_method", nullable=false, length=100)	
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name="business_type", nullable=false, length=100)	
	public String getBusinessType() {
		return businessType;
	}

	public void setExpressionValue(String expressionValue) {
		this.expressionValue = expressionValue;
	}

	@Column(name="expression_value", nullable=false, length=800)	
	public String getExpressionValue() {
		return expressionValue;
	}

	public void setBusinessVersion(Integer businessVersion) {
		this.businessVersion = businessVersion;
	}

	@Column(name="business_version", nullable=false, length=22)	
	public Integer getBusinessVersion() {
		return businessVersion;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="order_no", nullable=false, length=100)
	public String getOrderNo() {
		return orderNo;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="delete_flag", nullable=true, length=22)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="p_code", nullable=true, length=100)
	public String getPCode() {
		return pCode;
	}

	public void setPCode(String pCode) {
		this.pCode = pCode;
	}

	@Column(name="code", nullable=true, length=100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="is_global_variable", nullable=true, length=1)
	public String getIsGlobalVariable() {
		return isGlobalVariable;
	}

	public void setIsGlobalVariable(String isGlobalVariable) {
		this.isGlobalVariable = isGlobalVariable;
	}
	@Column(name="old_order_no", nullable=true, length=100)
	public String getOldOrderNo() {
		return oldOrderNo;
	}

	public void setOldOrderNo(String oldOrderNo) {
		this.oldOrderNo = oldOrderNo;
	}
	@Column(name="old_clause_id", nullable=true, length=22)
	public Integer getOldClauseId() {
		return oldClauseId;
	}

	public void setOldClauseId(Integer oldClauseId) {
		this.oldClauseId = oldClauseId;
	}


	@Column(name="is_global_fee", nullable=true, length=1)
	public String getIsGlobalFee() {
		return isGlobalFee;
	}

	public void setIsGlobalFee(String isGlobalFee) {
		this.isGlobalFee = isGlobalFee;
	}
	@Column(name="is_global_tta", nullable=true, length=1)
	public String getIsGlobalTta() {
		return isGlobalTta;
	}

	public void setIsGlobalTta(String isGlobalTta) {
		this.isGlobalTta = isGlobalTta;
	}
	@Column(name="expression_value_fee", nullable=true, length=800)
	public String getExpressionValueFee() {
		return expressionValueFee;
	}

	public void setExpressionValueFee(String expressionValueFee) {
		this.expressionValueFee = expressionValueFee;
	}

	@Column(name="expression_value_tta", nullable=true, length=800)
	public String getExpressionValueTta() {
		return expressionValueTta;
	}

	public void setExpressionValueTta(String expressionValueTta) {
		this.expressionValueTta = expressionValueTta;
	}

	@Column(name="term_category", nullable=true, length=2)
	public String getTermCategory() {
		return termCategory;
	}

	public void setTermCategory(String termCategory) {
		this.termCategory = termCategory;
	}
	@Column(name="global_value_fee", nullable=true, length=200)
	public String getGlobalValueFee() {
		return globalValueFee;
	}

	public void setGlobalValueFee(String globalValueFee) {
		this.globalValueFee = globalValueFee;
	}
	@Column(name="global_value_tta", nullable=true, length=200)
	public String getGlobalValueTta() {
		return globalValueTta;
	}

	public void setGlobalValueTta(String globalValueTta) {
		this.globalValueTta = globalValueTta;
	}
	@Column(name="global_value", nullable=true, length=200)
	public String getGlobalValue() {
		return globalValue;
	}

	public void setGlobalValue(String globalValue) {
		this.globalValue = globalValue;
	}

	@Column(name="global_value_con")
	public String getGlobalValueCon() {
		return globalValueCon;
	}

	public void setGlobalValueCon(String globalValueCon) {
		this.globalValueCon = globalValueCon;
	}

	@Column(name="expression_value_con")
	public String getExpressionValueCon() {
		return expressionValueCon;
	}

	public void setExpressionValueCon(String expressionValueCon) {
		this.expressionValueCon = expressionValueCon;
	}

	@Column(name="is_default")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}
