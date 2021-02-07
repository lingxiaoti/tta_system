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
 * TtaSubjectChoiceSecMidEntity_HI Entity Object
 * Wed Jun 12 13:53:39 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SUBJECT_CHOICE_SEC_MID")
public class TtaSubjectChoiceSecMidEntity_HI {
    private Integer midId;
    private Integer qnChoiceId;
    private Integer qnLineId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setMidId(Integer midId) {
		this.midId = midId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_SUBJECT_CHOICE_SEC_MID", sequenceName = "SEQ_TTA_SUBJECT_CHOICE_SEC_MID", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_SUBJECT_CHOICE_SEC_MID", strategy = GenerationType.SEQUENCE)
	@Column(name="mid_id", nullable=false, length=22)	
	public Integer getMidId() {
		return midId;
	}

	public void setQnChoiceId(Integer qnChoiceId) {
		this.qnChoiceId = qnChoiceId;
	}

	@Column(name="qn_choice_id", nullable=false, length=22)	
	public Integer getQnChoiceId() {
		return qnChoiceId;
	}

	public void setQnLineId(Integer qnLineId) {
		this.qnLineId = qnLineId;
	}

	@Column(name="qn_line_id", nullable=false, length=22)	
	public Integer getQnLineId() {
		return qnLineId;
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
}
