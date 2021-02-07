package com.sie.watsons.base.questionnaire.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaTestQuestionChoiceLineEntity_HI Entity Object
 * Tue Jul 30 13:08:26 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_TEST_QUESTION_CHOICE_LINE")
public class TtaTestQuestionChoiceLineEntity_HI {
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
    private String  choiceResult;
    private String ruleName;
    private Integer proposalId;

    private String  controlType; //控件类型:0:默认文本 1.日期控件，2.单选控件，3.多选控件
	private String lookupType;//选项题型快码值

	private String isShowStatus; //1:前端显示，0前端不显示


	public void setChoiceLineId(Integer choiceLineId) {
		this.choiceLineId = choiceLineId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_TEST_QUESTION_CHOICE", sequenceName = "SEQ_TTA_TEST_QUESTION_CHOICE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_TEST_QUESTION_CHOICE", strategy = GenerationType.SEQUENCE)
	@Column(name="choice_line_id", nullable=false, length=22)	
	public Integer getChoiceLineId() {
		return choiceLineId;
	}

	public void setQHeaderId(Integer qHeaderId) {
		this.qHeaderId = qHeaderId;
	}

	@Column(name="q_header_id", nullable=false, length=22)	
	public Integer getQHeaderId() {
		return qHeaderId;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name="serial_number", nullable=true, length=22)	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setChoiceType(String choiceType) {
		this.choiceType = choiceType;
	}

	@Column(name="choice_type", nullable=true, length=50)	
	public String getChoiceType() {
		return choiceType;
	}

	public void setChoiceCnContent(String choiceCnContent) {
		this.choiceCnContent = choiceCnContent;
	}

	@Column(name="choice_cn_content", nullable=true, length=500)	
	public String getChoiceCnContent() {
		return choiceCnContent;
	}

	public void setChoiceEnContent(String choiceEnContent) {
		this.choiceEnContent = choiceEnContent;
	}

	@Column(name="choice_en_content", nullable=true, length=500)	
	public String getChoiceEnContent() {
		return choiceEnContent;
	}

	public void setIsShowChild(String isShowChild) {
		this.isShowChild = isShowChild;
	}

	@Column(name="is_show_child", nullable=true, length=2)	
	public String getIsShowChild() {
		return isShowChild;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name="is_enable", nullable=true, length=2)	
	public String getIsEnable() {
		return isEnable;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="status", nullable=false, length=22)	
	public Integer getStatus() {
		return status;
	}

	public void setQnChoice(String qnChoice) {
		this.qnChoice = qnChoice;
	}

	@Column(name="qn_choice", nullable=true, length=100)	
	public String getQnChoice() {
		return qnChoice;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	@Column(name="rule_id", nullable=true, length=22)	
	public Integer getRuleId() {
		return ruleId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name="parent_id", nullable=true, length=22)	
	public Integer getParentId() {
		return parentId;
	}

	public void setSourceChoiceLineId(Integer sourceChoiceLineId) {
		this.sourceChoiceLineId = sourceChoiceLineId;
	}

	@Column(name="source_choice_line_id", nullable=false, length=22)	
	public Integer getSourceChoiceLineId() {
		return sourceChoiceLineId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
	
	public void setChoiceResult(String choiceResult) {
		this.choiceResult = choiceResult;
	}
	
	@Column(name="choice_result", nullable=true, length=500)	
	public String getChoiceResult() {
		return choiceResult;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name="rule_name", length=500)
	public String getRuleName() {
		return ruleName;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id")
	public Integer getProposalId() {
		return proposalId;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	@Column(name="control_type")
	public String getControlType() {
		return controlType;
	}

	@Column(name="lookup_type")
	public String getLookupType() {
		return lookupType;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}


	@Column(name="is_show_status")
	public String getIsShowStatus() {
		return isShowStatus;
	}

	public void setIsShowStatus(String isShowStatus) {
		this.isShowStatus = isShowStatus;
	}

}
