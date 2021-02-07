package com.sie.saaf.base.feedback.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BaseIssueFeedbackEntity_HI Entity Object
 * Mon Jul 30 15:53:29 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_issue_feedback")
public class BaseIssueFeedbackEntity_HI {
    private Integer feedbackId; //主键ID
    private String title; //标题
    private String content; //内容
    private String systemCode; //来源系统
    private Integer ouId; //事业部
    private String status; //状态(C-草稿,S-已提交,R-已回复)
    private Integer createdBy; //创建人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer lastUpdatedBy; //更新人
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新时间
    private Integer lastUpdateLogin; //更新登录ID
    private Integer deleteFlag; //删除标识
    private Integer versionNum; //版本号
    private Integer operatorUserId;
    private String comment;
    private String source;

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_ISSUE_FEEDBACK", sequenceName = "SEQ_BASE_ISSUE_FEEDBACK", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_ISSUE_FEEDBACK", strategy = GenerationType.SEQUENCE)	
	@Column(name="feedback_id", nullable=false, length=11)	
	public Integer getFeedbackId() {
		return feedbackId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="title", nullable=true, length=100)	
	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="content", nullable=true, length=0)	
	public String getContent() {
		return content;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name="system_code", nullable=true, length=100)	
	public String getSystemCode() {
		return systemCode;
	}

	public void setOuId(Integer ouId) {
		this.ouId = ouId;
	}

	@Column(name="ou_id", nullable=true, length=11)	
	public Integer getOuId() {
		return ouId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=20)	
	public String getStatus() {
		return status;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=0)	
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

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=11)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="comment", nullable=true, length=1000)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name="source", nullable=true, length=20)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
