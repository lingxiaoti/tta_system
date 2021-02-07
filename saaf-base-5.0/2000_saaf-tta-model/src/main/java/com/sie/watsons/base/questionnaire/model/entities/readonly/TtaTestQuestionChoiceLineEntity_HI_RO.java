package com.sie.watsons.base.questionnaire.model.entities.readonly;

import javax.persistence.Version;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaTestQuestionChoiceLineEntity_HI_RO Entity Object
 * Tue Jul 30 13:08:26 CST 2019  Auto Generate
 */

public class TtaTestQuestionChoiceLineEntity_HI_RO {

	public static final String QUERY_BY_TEST_HEADER_ID = "select t.version_num,\n" +
			"       t.creation_date,\n" +
			"       t.created_by,\n" +
			"       t.last_updated_by,\n" +
			"       t.last_update_date,\n" +
			"       t.last_update_login,\n" +
			"       t.choice_line_id,\n" +
			"       t.q_header_id,\n" +
			"       t.serial_number,\n" +
			"       t.choice_type,\n" +
			"       t.choice_cn_content,\n" +
			"       t.choice_en_content,\n" +
			"       t.is_show_child,\n" +
			"       t.is_enable,\n" +
			"       t.qn_choice,\n" +
			"       t.rule_id,\n" +
			"       t.parent_id,\n" +
			"       r.rule_name,\n" +
			"       t.choice_result,\n" +
			"       t.choice_result as auto_calc_result,\n" +
			"       t.control_type,\n" +
			"       t.lookup_type,\n" +
			"       blt.meaning as lookup_type_name,\n" +
			"       blvp.meaning as control_type_name\n" +
			"  from tta_test_question_choice_line t\n" +
			"  left join tta_base_rule r\n" +
			"    on t.rule_id = r.rule_id\n" +
			"    left join base_lookup_values blvp\n" +
			"    on blvp.lookup_type = 'TTA_CONTROL_TYPE'\n" +
			"    and BLVP.ENABLED_FLAG = 'Y'\n" +
			"    AND BLVP.DELETE_FLAG = '0'\n" +
			"    and BLVP.LOOKUP_CODE = t.control_type\n" +
			"    left join base_lookup_types blt\n" +
			"    on blt.lookup_type = t.lookup_type\n" +
			" where 1 = 1\n";

    private Integer choiceLineId;
    private Integer qHeaderId;
    private Integer serialNumber;
    private String choiceType;
    private String choiceCnContent;
    private String choiceEnContent;
    private String isShowChild;
    private String isEnable;
    private Integer status;
    private String qnChoice;
    private Integer ruleId;
    private Integer parentId;
    private Integer sourceChoiceLineId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String  ruleName;
	private String  choiceResult;
	private String autoCalcResult;

	private String lookupType;//选项题型快码值
	private String lookupTypeName;//选择题型快码名称

	private String controlType;//控件类型
	private String controlTypeName;//控件名称


	private List<TtaTestQuestionChoiceLineEntity_HI_RO> choiceChildList = new ArrayList<>();


	public void setChoiceLineId(Integer choiceLineId) {
		this.choiceLineId = choiceLineId;
	}

	
	public Integer getChoiceLineId() {
		return choiceLineId;
	}

	public void setQHeaderId(Integer qHeaderId) {
		this.qHeaderId = qHeaderId;
	}

	
	public Integer getQHeaderId() {
		return qHeaderId;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setChoiceType(String choiceType) {
		this.choiceType = choiceType;
	}

	
	public String getChoiceType() {
		return choiceType;
	}

	public void setChoiceCnContent(String choiceCnContent) {
		this.choiceCnContent = choiceCnContent;
	}

	
	public String getChoiceCnContent() {
		return choiceCnContent;
	}

	public void setChoiceEnContent(String choiceEnContent) {
		this.choiceEnContent = choiceEnContent;
	}

	
	public String getChoiceEnContent() {
		return choiceEnContent;
	}

	public void setIsShowChild(String isShowChild) {
		this.isShowChild = isShowChild;
	}

	
	public String getIsShowChild() {
		return isShowChild;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setQnChoice(String qnChoice) {
		this.qnChoice = qnChoice;
	}

	
	public String getQnChoice() {
		return qnChoice;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	
	public Integer getRuleId() {
		return ruleId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
	public Integer getParentId() {
		return parentId;
	}

	public void setSourceChoiceLineId(Integer sourceChoiceLineId) {
		this.sourceChoiceLineId = sourceChoiceLineId;
	}

	
	public Integer getSourceChoiceLineId() {
		return sourceChoiceLineId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setChoiceChildList(List<TtaTestQuestionChoiceLineEntity_HI_RO> choiceChildList) {
		this.choiceChildList = choiceChildList;
	}

	public List<TtaTestQuestionChoiceLineEntity_HI_RO> getChoiceChildList() {
		return choiceChildList;
	}


	public void setAutoCalcResult(String autoCalcResult) {
		this.autoCalcResult = autoCalcResult;
	}

	public String getAutoCalcResult() {
		return autoCalcResult;
	}

	public String getChoiceResult() {
		return choiceResult;
	}

	public void setChoiceResult(String choiceResult) {
		this.choiceResult = choiceResult;
	}

	public String getLookupType() {
		return lookupType;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}

	public String getLookupTypeName() {
		return lookupTypeName;
	}

	public void setLookupTypeName(String lookupTypeName) {
		this.lookupTypeName = lookupTypeName;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getControlTypeName() {
		return controlTypeName;
	}

	public void setControlTypeName(String controlTypeName) {
		this.controlTypeName = controlTypeName;
	}
}
