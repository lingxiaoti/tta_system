package com.sie.watsons.base.questionnaire.model.entities.readonly;

/**
 * TtaFeeDeptDEntity_HI_RO Entity Object
 * Wed May 29 11:24:38 CST 2019  Auto Generate
 */

public class RulePerson_HI_RO {
	public static final String QUERY_SQL = "SELECT  \n" +
			"RULE_ID ruleId,\t  \n" +
			"RULE_NAME ruleName,  \n" +
			"SQL_VALUES sqlValues,      \n" +
			"BUSINESS_TYPE businessType,  \n" +
			"lookup.meaning businessTypeName,  \n" +
			"REMARKS remarks  \n" +
			"FROM                  \n" +
			"TTA_BASE_RULE  tbr,\n" +
			"    (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='BASE_RULE' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup\n" +
			"\n" +
			"WHERE 1=1\n" +
			"\n" +
			"and  tbr.business_type =  lookup.lookup_code(+)";
	
	private Integer ruleId;        //表id
	private String ruleName;       //类型
	private String sqlValues;      //中文名称
	private String businessType;   //英文名称
	private String businessTypeName;   //英文名称
	private String remarks;        //备注
	
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getSqlValues() {
		return sqlValues;
	}
	public void setSqlValues(String sqlValues) {
		this.sqlValues = sqlValues;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
}
