package com.sie.watsons.base.questionnaire.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * SaafQuestionnaireChoiceEntity_HI_RO Entity Object
 * Mon Nov 12 09:28:29 CST 2018  Auto Generate
 */

public class SaafQuestionnaireChoiceEntity_HI_RO {

	public static final String QUERY_CHOICE = "select has_sec_line,\n" +
			"       qn_choice_id,\n" +
			"       qn_line_id,\n" +
			"       qn_choice,\n" +
			"       qn_choice_content,\n" +
			"       qn_choice_content_alt,\n" +
			"       qn_answer,\n" +
			"       display_flag,\n" +
			"       description,\n" +
			"       select_qn_line_id,\n" +
			"       version_num,\n" +
			"       creation_date,\n" +
			"       created_by,\n" +
			"       last_updated_by,\n" +
			"       last_update_date,\n" +
			"       last_update_login,\n" +
			"       (select to_char(listagg(t1.project_title, ',') within\n" +
			"                       group(order by(t1.project_title)))\n" +
			"          from tta_subject_choice_sec_mid t\n" +
			"         inner join tta_questionnaire_line t1\n" +
			"            on t1.qn_line_id = t.qn_line_id\n" +
			"         where a.qn_choice_id = t.qn_choice_id) as childProjectTitle\n" +
			"  from tta_questionnaire_choice a\n" +
			" where 1 = 1 \n";

    private Integer qnChoiceId; //表ID，主键，供其他表做外键
    private Integer qnLineId; //行ID
    private Integer qnHeadId; //头ID
    private String qnChoice; //选项
    private String qnChoiceContent; //选项内容/文本内容
    private String qnAnswer; //题目答案
    private String displayFlag; //是否显示标志
    private String description; //说明、备注
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String hasSecLine; //是否有二级题目，0否，1是
	private String childProjectTitle;

	private String selectQnLineId;//选中的行ID集

	private String  qnChoiceContentAlt; //选项英文名称

	public String getSelectQnLineId() {
		return selectQnLineId;
	}

	public void setSelectQnLineId(String selectQnLineId) {
		this.selectQnLineId = selectQnLineId;
	}



	public void setQnChoiceId(Integer qnChoiceId) {
		this.qnChoiceId = qnChoiceId;
	}

	
	public Integer getQnChoiceId() {
		return qnChoiceId;
	}

	public void setQnLineId(Integer qnLineId) {
		this.qnLineId = qnLineId;
	}

	
	public Integer getQnLineId() {
		return qnLineId;
	}

	public void setQnHeadId(Integer qnHeadId) {
		this.qnHeadId = qnHeadId;
	}

	
	public Integer getQnHeadId() {
		return qnHeadId;
	}

	public void setQnChoice(String qnChoice) {
		this.qnChoice = qnChoice;
	}

	
	public String getQnChoice() {
		return qnChoice;
	}

	public void setQnChoiceContent(String qnChoiceContent) {
		this.qnChoiceContent = qnChoiceContent;
	}

	
	public String getQnChoiceContent() {
		return qnChoiceContent;
	}

	public void setQnAnswer(String qnAnswer) {
		this.qnAnswer = qnAnswer;
	}

	
	public String getQnAnswer() {
		return qnAnswer;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	
	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
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

	public void setHasSecLine(String hasSecLine) {
		this.hasSecLine = hasSecLine;
	}

	public String getHasSecLine() {
		return hasSecLine;
	}

	public void setChildProjectTitle(String childProjectTitle) {
		this.childProjectTitle = childProjectTitle;
	}

	public String getChildProjectTitle() {
		return childProjectTitle;
	}

	public void setQnChoiceContentAlt(String qnChoiceContentAlt) {
		this.qnChoiceContentAlt = qnChoiceContentAlt;
	}

	public String getQnChoiceContentAlt() {
		return qnChoiceContentAlt;
	}

}
