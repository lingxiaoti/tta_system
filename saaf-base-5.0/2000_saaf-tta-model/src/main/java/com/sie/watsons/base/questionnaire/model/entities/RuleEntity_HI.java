package com.sie.watsons.base.questionnaire.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TTA_BASE_RULE")
public class RuleEntity_HI {
	private Integer ruleId;        //表id
	private String ruleName;       //类型
	private String sqlValues;      //中文名称
	private String businessType;   //英文名称
	private String remarks;        //备注
	
	@Id
	@SequenceGenerator(name="SEQ_TTA_BASE_RULE", sequenceName="SEQ_TTA_BASE_RULE", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_BASE_RULE",strategy=GenerationType.SEQUENCE)
	@Column(name = "RULE_ID", nullable = false, length = 11)
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	
	@Column(name="RULE_NAME", nullable=true, length=100)	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	@Column(name="SQL_VALUES", nullable=true, length=100)	
	public String getSqlValues() {
		return sqlValues;
	}
	public void setSqlValues(String sqlValues) {
		this.sqlValues = sqlValues;
	}
	
	@Column(name="BUSINESS_TYPE", nullable=true, length=100)	
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@Column(name="REMARKS", nullable=true, length=100)
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
		
}
