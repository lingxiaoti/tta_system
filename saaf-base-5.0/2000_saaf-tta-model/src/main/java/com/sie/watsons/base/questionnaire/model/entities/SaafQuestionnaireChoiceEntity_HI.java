package com.sie.watsons.base.questionnaire.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * SaafQuestionnaireChoiceEntity_HI Entity Object
 * Mon Nov 12 09:28:29 CST 2018  Auto Generate
 */
@Entity
@Table(name="tta_questionnaire_choice")
public class SaafQuestionnaireChoiceEntity_HI {
    private Integer qnChoiceId; //表ID，主键，供其他表做外键
    private Integer qnLineId; //行ID
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
	private String selectQnLineId;//选中的行ID集
	private String hasSecLine; //是否有二级题目，0否，1是
	private String  qnChoiceContentAlt; //选项英文名称

	public void setQnChoiceId(Integer qnChoiceId) {
		this.qnChoiceId = qnChoiceId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_QUESTIONNAIRE_CHOICE", sequenceName = "SEQ_TTA_QUESTIONNAIRE_CHOICE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_QUESTIONNAIRE_CHOICE", strategy = GenerationType.SEQUENCE)
	@Column(name="qn_choice_id", nullable=false, length=11)	
	public Integer getQnChoiceId() {
		return qnChoiceId;
	}

	public void setQnLineId(Integer qnLineId) {
		this.qnLineId = qnLineId;
	}

	@Column(name="select_qn_line_id", nullable=true, length=500)
	public String getSelectQnLineId() {
		return selectQnLineId;
	}
	public void setSelectQnLineId(String selectQnLineId) {
		this.selectQnLineId = selectQnLineId;
	}

	@Column(name="qn_line_id", nullable=true, length=11)	
	public Integer getQnLineId() {
		return qnLineId;
	}

	public void setQnChoice(String qnChoice) {
		this.qnChoice = qnChoice;
	}

	@Column(name="qn_choice", nullable=true, length=100)	
	public String getQnChoice() {
		return qnChoice;
	}

	public void setQnChoiceContent(String qnChoiceContent) {
		this.qnChoiceContent = qnChoiceContent;
	}

	@Column(name="qn_choice_content", nullable=true, length=500)	
	public String getQnChoiceContent() {
		return qnChoiceContent;
	}

	public void setQnAnswer(String qnAnswer) {
		this.qnAnswer = qnAnswer;
	}

	@Column(name="qn_answer", nullable=true, length=100)	
	public String getQnAnswer() {
		return qnAnswer;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	@Column(name="display_flag", nullable=true, length=100)	
	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=500)	
	public String getDescription() {
		return description;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setHasSecLine(String hasSecLine) {
		this.hasSecLine = hasSecLine;
	}

	@Column(name="has_sec_line", nullable=true, length=2)
	public String getHasSecLine() {
		return hasSecLine;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setQnChoiceContentAlt(String qnChoiceContentAlt) {
		this.qnChoiceContentAlt = qnChoiceContentAlt;
	}

	@Column(name="qn_choice_content_alt", nullable=true, length=500)
	public String getQnChoiceContentAlt() {
		return qnChoiceContentAlt;
	}
}
