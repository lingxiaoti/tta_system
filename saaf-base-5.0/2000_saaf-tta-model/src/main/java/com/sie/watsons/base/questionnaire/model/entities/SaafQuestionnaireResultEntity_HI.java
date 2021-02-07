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
 * SaafQuestionnaireResultEntity_HI Entity Object
 * Mon Nov 12 09:28:36 CST 2018  Auto Generate
 */
@Entity
@Table(name="tta_questionnaire_result")
public class SaafQuestionnaireResultEntity_HI {
    private Integer resultChoiceId; //表ID，主键，供其他表做外键
    private Integer proposalId; //proposalId
    private Integer qnLineId; //行ID
    private String qnChoiceId; //选项ID
    private Integer resultNum; //第几个答题
    private String qnChoiceResult; //选项答案
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

	public void setResultChoiceId(Integer resultChoiceId) {
		this.resultChoiceId = resultChoiceId;
	}


	@Id
	@SequenceGenerator(name = "SEQ_TTA_QUESTIONNAIRE_RESULT", sequenceName = "SEQ_TTA_QUESTIONNAIRE_RESULT", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_QUESTIONNAIRE_RESULT", strategy = GenerationType.SEQUENCE)
	@Column(name="result_choice_id", nullable=false, length=11)	
	public Integer getResultChoiceId() {
		return resultChoiceId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	@Column(name="proposal_id", nullable=true, length=11)
	public Integer getProposalId() {
		return proposalId;
	}

	public void setQnLineId(Integer qnLineId) {
		this.qnLineId = qnLineId;
	}

	@Column(name="qn_line_id", nullable=true, length=11)	
	public Integer getQnLineId() {
		return qnLineId;
	}

	public void setQnChoiceId(String qnChoiceId) {
		this.qnChoiceId = qnChoiceId;
	}

	@Column(name="qn_choice_id", nullable=true, length=500)	
	public String getQnChoiceId() {
		return qnChoiceId;
	}

	public void setResultNum(Integer resultNum) {
		this.resultNum = resultNum;
	}

	@Column(name="result_num", nullable=true, length=11)	
	public Integer getResultNum() {
		return resultNum;
	}

	public void setQnChoiceResult(String qnChoiceResult) {
		this.qnChoiceResult = qnChoiceResult;
	}

	@Column(name="qn_choice_result", nullable=true, length=500)	
	public String getQnChoiceResult() {
		return qnChoiceResult;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
