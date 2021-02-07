package com.sie.watsons.base.questionnaire.model.entities.readonly;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaQuestionChoiceLineEntity_HI_RO Entity Object
 * Thu Jul 25 21:34:11 CST 2019  Auto Generate
 */

public class TtaQuestionChoiceLineEntity_HI_RO {


	public static final String START_SQL = "select a.sql_values \"sqlValues\" ,c.q_header_id as \"qHeaderId\", b.choice_line_id as \"choiceLineId\" \n" +
			"  from TTA_BASE_RULE a\n" +
			" inner join tta_question_choice_line b\n" +
			"    on a.rule_id = b.rule_id inner join \n" +
			"    tta_question_header c on c.q_header_id = b.q_header_id where c.serial_number = 1\n" +
			"    and b.is_enable = 'Y' and c.is_enable = 'Y' ";

	public static final  String QUERY_HEADERIDS_SQL = "select a.q_header_id as \"qHeaderId\" \n" +
			"  from tta_question_header a\n" +
			" inner join tta_base_rule_line b\n" +
			"    on a.q_header_id = b.q_header_id\n" +
			" where a.is_enable = 'Y'\n" +
			"   and b.is_enable = 'Y'\n" +
			"   AND b.rule_id in ( select d.rule_id\n" +
			"                       from tta_question_header a\n" +
			"                      inner join tta_question_choice_line b\n" +
			"                         on a.q_header_id = b.q_header_id\n" +
			"                      inner join TTA_BASE_RULE_HEADER d\n" +
			"                         on d.q_header_id = a.q_header_id\n" +
			"						   and d.choice_line_id = b.choice_line_id \n" +
			"                      inner join TTA_BASE_RULE c\n" +
			"                         on c.rule_id = b.rule_id\n" +
			"                      where c.business_type = '1' \n" +
			"                        AND D.RESULT_VALUE =:resultValue\n" +
			"                        AND a.q_header_id =:qHeaderId \n" +
			" 					     and b.choice_line_id =:choiceLineId) \t";

	public static final String VALUE_SQL_QHEADERID_SQL = "select a.sql_values \"sqlValues\" ,c.q_header_id \"qHeaderId\" ,b.choice_line_id as \"choiceLineId\"\n" +
			"  from TTA_BASE_RULE a\n" +
			" inner join tta_question_choice_line b\n" +
			"    on a.rule_id = b.rule_id inner join \n" +
			"    tta_question_header c on c.q_header_id = b.q_header_id where 1 = 1\n" +
			"    and b.is_enable = 'Y' and c.is_enable = 'Y'  and  c.q_header_id=:qHeaderId ";


	/**
	 * 功能描述： 查询规则对应的题目
	 * @date 2019/8/8
	 * @param
	 * @return
	 */
	public static final String QUERY_Q_HEADER_ID_RULE_ID_SQL = "select a.q_header_id\n" +
			"  from tta_question_header a\n" +
			" inner join tta_base_rule_line b\n" +
			"    on a.q_header_id = b.q_header_id\n" +
			" where 1 = 1 \t";
			//" where b.rule_id = 153 order by a.serial_number asc";


	
	public static final String QUERY_BY_HEADER_ID = "select \n" +
			"       t.is_show_status,\n" +
			"       t.choice_line_id,\n" +
			"       t.version_num,\n" +
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
			"       r.sql_values,\n" +
			"       s.choice_cn_content_flag,\n" +
			"       s.choice_en_content_flag,\n" +
			"       s.rule_name_flag,\n" +
			"       t.lookup_type,\n" +
			"       blt.meaning as lookup_type_name,\n" +
			"       nvl(t.control_type,0) as control_type, \n" +
			"       blvp.meaning as control_type_name\n" +
			"  from tta_question_choice_line t\n" +
			"  left join tta_base_rule r\n" +
			"    on t.rule_id = r.rule_id\n" +
			"  left join (select 1 choice_type,\n" +
			"                    1 as choice_cn_content_flag,\n" +
			"                    1 as choice_en_content_flag,\n" +
			"                    0 rule_name_flag\n" +
			"               from dual\n" +
			"             union all\n" +
			"             select 2 choice_type,\n" +
			"                    1 as choice_cn_content_flag,\n" +
			"                    1 as choice_en_content_flag,\n" +
			"                    1 rule_name_flag\n" +
			"               from dual\n" +
			"             union all\n" +
			"             select 3 choice_type,\n" +
			"                    0 as choice_cn_content_flag,\n" +
			"                    0 as choice_en_content_flag,\n" +
			"                    1 rule_name_flag\n" +
			"               from dual\n" +
			"             union all\n" +
			"             select 4 choice_type,\n" +
			"                    0 as choice_cn_content_flag,\n" +
			"                    0 as choice_en_content_flag,\n" +
			"                    0 rule_name_flag\n" +
			"               from dual\n" +
			"             union all\n" +
			"             select 5 choice_type,\n" +
			"                    0 as choice_cn_content_flag,\n" +
			"                    0 as choice_en_content_flag,\n" +
			"                    1 rule_name_flag\n" +
			"               from dual) s\n" +
			"    on s.choice_type = t.choice_type\n" +
			"  left join base_lookup_values blvp\n" +
			"    on blvp.lookup_type = 'TTA_CONTROL_TYPE'\n" +
			"   and BLVP.ENABLED_FLAG = 'Y'\n" +
			"   AND BLVP.DELETE_FLAG = '0'\n" +
			"   and BLVP.LOOKUP_CODE = t.control_type\n" +
			"   left join base_lookup_types blt \n" +
			"   on blt.lookup_type = t.lookup_type\n" +
			" where 1 = 1 \n";

    private Integer choiceLineId;
    private Integer qHeaderId;
    private Integer serialNumber;
    private String choiceType;
    private String choiceCnContent;
    private String choiceEnContent;
    private String isShowChild;
    private String isEnable;
    private Integer ruleId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String qnChoice;
	private String ruleName;
	private String sqlValues;
	private Integer parentId;
	private Integer choiceCnContentFlag;
	private Integer choiceEnContentFlag;
	private Integer ruleNameFlag;
	private String  autoCalcResult; //自动计算结果值

	private String lookupType;//选项题型快码值
	private String lookupTypeName;//选择题型快码名称

	private String controlType;//控件类型
	private String controlTypeName;//控件名称

	private String isShowStatus; //1:前端显示，0前端不显示

	private List<TtaQuestionChoiceLineEntity_HI_RO> choiceChildList = new ArrayList<>();

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

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	
	public Integer getRuleId() {
		return ruleId;
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

	public void setQnChoice(String qnChoice) {
		this.qnChoice = qnChoice;
	}

	public String getQnChoice() {
		return qnChoice;
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

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setChoiceCnContentFlag(Integer choiceCnContentFlag) {
		this.choiceCnContentFlag = choiceCnContentFlag;
	}

	public Integer getChoiceCnContentFlag() {
		return choiceCnContentFlag;
	}

	public Integer getChoiceEnContentFlag() {
		return choiceEnContentFlag;
	}

	public void setChoiceEnContentFlag(Integer choiceEnContentFlag) {
		this.choiceEnContentFlag = choiceEnContentFlag;
	}

	public Integer getRuleNameFlag() {
		return ruleNameFlag;
	}

	public void setRuleNameFlag(Integer ruleNameFlag) {
		this.ruleNameFlag = ruleNameFlag;
	}

	public void setChoiceChildList(List<TtaQuestionChoiceLineEntity_HI_RO> choiceChildList) {
		this.choiceChildList = choiceChildList;
	}

	public List<TtaQuestionChoiceLineEntity_HI_RO> getChoiceChildList() {
		return choiceChildList;
	}

	public void setAutoCalcResult(String autoCalcResult) {
		this.autoCalcResult = autoCalcResult;
	}

	public String getAutoCalcResult() {
		return autoCalcResult;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}
	public String getLookupType() {
		return lookupType;
	}

	public String getLookupTypeName() {
		return lookupTypeName;
	}
	public void setLookupTypeName(String lookupTypeName) {
		this.lookupTypeName = lookupTypeName;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getControlType() {
		return controlType;
	}

	public String getControlTypeName() {
		return controlTypeName;
	}
	public void setControlTypeName(String controlTypeName) {
		this.controlTypeName = controlTypeName;
	}

	public String getIsShowStatus() {
		return isShowStatus;
	}

	public void setIsShowStatus(String isShowStatus) {
		this.isShowStatus = isShowStatus;
	}
}
