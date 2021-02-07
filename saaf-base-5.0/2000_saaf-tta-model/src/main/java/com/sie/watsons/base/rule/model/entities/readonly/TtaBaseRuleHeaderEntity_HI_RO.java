package com.sie.watsons.base.rule.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaBaseRuleHeaderEntity_HI_RO Entity Object
 * Mon Aug 05 15:50:32 CST 2019  Auto Generate
 */

public class TtaBaseRuleHeaderEntity_HI_RO {

	public static final String QUERY_HEADER_SQL = "  select a.rule_id,\n" +
			"        a.q_header_id,\n" +
			"        a.choice_line_id,\n" +
			"        a.result_value,\n" +
			"        a.is_enable,\n" +
			"        b.serial_number,\n" +
			"        b.project_cn_title,\n" +
			"        c.choice_cn_content\n" +
			"   from tta_base_rule_header a\n" +
			"  inner join tta_question_header b\n" +
			"     on a.q_header_id = b.q_header_id\n" +
			"  inner join tta_question_choice_line c\n" +
			"     on c.q_header_id = b.q_header_id \n" +
			"     and a.choice_line_id = c.choice_line_id\n" +
			"  where 1 = 1 ";
	
	public static final String QUERY_CHOICE_HEADER_SQL = "  select a.q_header_id,\n" +
			"         b.choice_line_id,\n" +
			"         a.project_cn_title,\n" +
			"         a.project_type,\n" +
			"         d.meaning as project_type_name,\n" +
			"         b.choice_cn_content,\n" +
			"         b.choice_type,\n" +
			"         C.MEANING AS choice_type_name\n" +
			"    from tta_question_header a\n" +
			"   inner join tta_question_choice_line b\n" +
			"      on a.q_header_id = b.q_header_id\n" +
			"    left join base_lookup_values c\n" +
			"      on c.lookup_code = b.choice_type\n" +
			"     and c.lookup_type = 'CHOICE_TYPE'\n" +
			"     and c.delete_flag = 0\n" +
			"     and c.enabled_flag = 'Y'\n" +
			"    left join base_lookup_values d on d.lookup_type = 'PROJECT_TYPE'\n" +
			"     and d.delete_flag = 0\n" +
			"     and d.lookup_code = a.project_type\n" +
			"     and d.enabled_flag = 'Y'\n" +
			"   where a.is_enable = 'Y'\n"; //+
			// "     and b.is_enable = 'Y'and not exists (select 1 from TTA_BASE_RULE_HEADER t where t.q_header_id = a.q_header_id and t.choice_line_id = b.choice_line_id) \n" ;

	public static final String QUERY_CHILD_LINE_SQL = "select a.q_header_id," +
			" 		a.project_cn_title,\n " +
			"		a.project_type, \n" +
			"		d.meaning as project_type_name\n" +
			"  from tta_question_header a\n" +
			"  left join base_lookup_values d\n" +
			"  on d.lookup_type = 'PROJECT_TYPE'\n" +
			"  and d.delete_flag = 0\n" +
			"  and d.lookup_code = a.project_type\n" +
			" where  a.is_enable = 'Y' \t" ;
			//"  and  not exists (select 1  from tta_base_rule_header b where b.q_header_id = a.q_header_id ) \t " ;
			//"  and  not exists (select 1 from tta_base_rule_line t where t.q_header_id = a.q_header_id and t.rule_id =:ruleId) \t ";

    private Integer ruleId;
    private Integer qHeaderId;
    private Integer choiceLineId;
    private Integer serialNumber;
    private String isEnable;
    private String resultValue;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    private String projectTypeName;
    private String choiceTypeName;
	private String projectCnTitle;
	private String choiceCnContent;

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	
	public Integer getRuleId() {
		return ruleId;
	}

	public void setQHeaderId(Integer qHeaderId) {
		this.qHeaderId = qHeaderId;
	}

	
	public Integer getQHeaderId() {
		return qHeaderId;
	}

	public void setChoiceLineId(Integer choiceLineId) {
		this.choiceLineId = choiceLineId;
	}

	
	public Integer getChoiceLineId() {
		return choiceLineId;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	
	public String getResultValue() {
		return resultValue;
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

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setChoiceTypeName(String choiceTypeName) {
		this.choiceTypeName = choiceTypeName;
	}

	public String getChoiceTypeName() {
		return choiceTypeName;
	}

	public void setProjectCnTitle(String projectCnTitle) {
		this.projectCnTitle = projectCnTitle;
	}

	public String getProjectCnTitle() {
		return projectCnTitle;
	}

	public void setChoiceCnContent(String choiceCnContent) {
		this.choiceCnContent = choiceCnContent;
	}

	public String getChoiceCnContent() {
		return choiceCnContent;
	}
}
