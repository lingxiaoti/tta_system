package com.sie.watsons.base.questionnaire.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * SaafQuestionnaireResultEntity_HI_RO Entity Object
 * Mon Nov 12 09:28:36 CST 2018  Auto Generate
 */

public class SaafQuestionnaireResultEntity_HI_RO {
    private Integer resultChoiceId; //表ID，主键，供其他表做外键
    private Integer proposalId; //propsoalId
    private Integer qnLineId; //行ID
    private Integer qnHeadId; //头ID
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

	
	public Integer getResultChoiceId() {
		return resultChoiceId;
	}

	public void setProposalId(Integer proposalId) {
		this.proposalId = proposalId;
	}

	public Integer getProposalId() {
		return proposalId;
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

	public void setQnChoiceId(String qnChoiceId) {
		this.qnChoiceId = qnChoiceId;
	}

	
	public String getQnChoiceId() {
		return qnChoiceId;
	}

	public void setResultNum(Integer resultNum) {
		this.resultNum = resultNum;
	}

	
	public Integer getResultNum() {
		return resultNum;
	}

	public void setQnChoiceResult(String qnChoiceResult) {
		this.qnChoiceResult = qnChoiceResult;
	}

	
	public String getQnChoiceResult() {
		return qnChoiceResult;
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
}
