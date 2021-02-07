package com.sie.watsons.base.rule.model.entities.readonly;

import java.util.Date;


/**
 * TempParamRuleMidleEntity_HI_RO Entity Object
 * Wed May 29 16:22:30 CST 2019  Auto Generate
 */

public class RuleLEntity_HI_RO {

	//查询需要动态加载题目
	public static final String QUERY_NEED_LOAD_PROJECT = " select a.rule_id\n" +
			"    upper(case when (select nvl(listagg(d.qn_choice_result,',') within group (order by d.qn_choice_result), '-999')  from tta_test_questionnaire_line c\n" +
			"    inner join tta_test_questionnaire_choice d on c.test_qn_line_id = d.test_qn_line_id where c.original_qn_line_id = b.qn_head_id) = upper(B.option_valus)\n" +
			"    then  b.qn_head_id else -1  end) as qn_head_id\n" +
			"    from tta_base_rule_header a\n" +
			"   inner join tta_base_rule_line b\n" +
			"   on a.rule_id = b.rule_id\n" +
			"   and a.open = 'Y'";
	
	private Integer ruleLineId;
    private Integer qnHeadId;
    private String qnTitle;
    private String optionValus;
    private Integer ruleId;
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
	public Integer getRuleLineId() {
		return ruleLineId;
	}
	public void setRuleLineId(Integer ruleLineId) {
		this.ruleLineId = ruleLineId;
	}
	public Integer getQnHeadId() {
		return qnHeadId;
	}
	public void setQnHeadId(Integer qnHeadId) {
		this.qnHeadId = qnHeadId;
	}
	public String getQnTitle() {
		return qnTitle;
	}
	public void setQnTitle(String qnTitle) {
		this.qnTitle = qnTitle;
	}
	public String getOptionValus() {
		return optionValus;
	}
	public void setOptionValus(String optionValus) {
		this.optionValus = optionValus;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}
	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}
	public Integer getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

}
