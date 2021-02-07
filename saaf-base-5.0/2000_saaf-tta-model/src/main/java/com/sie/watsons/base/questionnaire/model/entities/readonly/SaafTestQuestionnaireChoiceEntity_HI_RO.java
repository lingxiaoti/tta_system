package com.sie.watsons.base.questionnaire.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * SaafTestQuestionnaireChoiceEntity_HI_RO Entity Object
 * Sat Feb 16 14:17:43 CST 2019  Auto Generate
 */

public class SaafTestQuestionnaireChoiceEntity_HI_RO {

	public static String QUERY_CHOICE = " select a.proposal_id,\n" +
			"        a.stauts,\n" +
			"        a.original_qn_choice_id,\n" +
			"        a.qn_choice_result,\n" +
			"        a.qn_choice_id,\n" +
			"        a.qn_line_id,\n" +
			"        a.qn_choice,\n" +
			"        a.qn_choice_content,\n" +
			"        a.qn_answer,\n" +
			"        a.display_flag,\n" +
			"        a.description,\n" +
			"        a.select_qn_line_id,\n" +
			"        a.version_num,\n" +
			"        a.creation_date,\n" +
			"        a.created_by,\n" +
			"        a.last_updated_by,\n" +
			"        a.last_update_date,\n" +
			"        a.last_update_login,\n" +
			"        b.qn_choice_result as qnChoiceResult,\n" +
			"        (case when (nvl(a.qn_choice,-1) = nvl(b.qn_choice_result,0)) then 1 else 0 end) as checkFlag\n" + //针对选择题，1选中，0未选中
			"   from tta_test_questionnaire_choice a left join tta_questionnaire_result b\n" +
			"   on a.qn_choice_id = b.qn_choice_id and a.qn_line_id = b.qn_line_id\n" +
			"  where 1 = 1 ";
    private Integer qnChoiceId; //表ID，主键，供其他表做外键
    private Integer qnLineId; //行ID
    private Integer testQnHeadId; //头ID
    private String qnChoice; //选项
    private String qnChoiceContent; //选项内容/文本内容
    private String qnAnswer; //题目答案
    private String displayFlag; //是否显示标志
    private String description; //说明、备注
    private String selectQnLineId; //选中的行ID集
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer proposalId;
	private Integer checkFlag;//针对选择题，1选中，0未选中
	private String qnChoiceResult;//答案

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

	public void setTestQnHeadId(Integer testQnHeadId) {
		this.testQnHeadId = testQnHeadId;
	}

	
	public Integer getTestQnHeadId() {
		return testQnHeadId;
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

	public void setSelectQnLineId(String selectQnLineId) {
		this.selectQnLineId = selectQnLineId;
	}

	
	public String getSelectQnLineId() {
		return selectQnLineId;
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

	public Integer getProposalId() {
		return proposalId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}


	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setQnChoiceResult(String qnChoiceResult) {
		this.qnChoiceResult = qnChoiceResult;
	}

	public String getQnChoiceResult() {
		return qnChoiceResult;
	}
}
