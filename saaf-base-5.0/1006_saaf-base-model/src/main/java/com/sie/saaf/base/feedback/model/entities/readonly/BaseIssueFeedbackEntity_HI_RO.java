package com.sie.saaf.base.feedback.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BaseIssueFeedbackEntity_HI_RO Entity Object
 * Mon Jul 30 15:53:29 CST 2018  Auto Generate
 */

public class BaseIssueFeedbackEntity_HI_RO {
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
    private String userName;
    private String userFullName;
    private String updatedUserName;
    private String updatedUserFullName;
	private String comment;
	private String orgName;
	private String phoneNumber;
	private String statusName;
	private String source;

    public static final String QUERY_FEEDBACK_LIST="SELECT   " +
			"    bif.feedback_id AS feedbackId,   " +
			"    bif.title AS title,   " +
			"    bif.content AS content,   " +
			"    bif.\"COMMENT\",   " +
			"    bif.source AS source,   " +
			"    bif.system_code AS systemCode,   " +
			"    bif.ou_id AS ouId,   " +
			"    bu.user_name AS userName,   " +
			"    bu.user_full_name AS userFullName,   " +
			"    bup.user_name AS updatedUserName,   " +
			"    bup.user_full_name AS updatedUserFullName,   " +
			"    bif.status AS status,   " +
			"CASE   WHEN bif.STATUS = 'S' THEN   " +
			"        '已提交'    " +
			"       WHEN bif.STATUS = 'R' THEN   " +
			"        '已回复' ELSE '草稿'    " +
			"    END AS statusName,   " +
			"    bif.created_by AS createdBy,   " +
			"    bif.creation_date AS creationDate,   " +
			"    bif.last_updated_by AS lastUpdatedBy,   " +
			"    bif.last_update_date AS lastUpdateDate,   " +
			"    bu.phone_number AS phoneNumber,   " +
			"    blv.meaning AS orgName    " +
			"FROM   " +
			"    base_issue_feedback bif   " +
			"    LEFT JOIN base_lookup_values blv ON blv.lookup_type = 'BASE_OU'    " +
			"    AND blv.system_code = 'BASE'    " +
			"    AND blv.lookup_code = bif.ou_id,   " +
			"    base_users bu,   " +
			"    base_users bup    " +
			"WHERE   " +
			"    bu.user_id = bif.created_by    " +
			"    AND bup.user_id = bif.last_updated_by  ";
    
	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}

	
	public Integer getFeedbackId() {
		return feedbackId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getContent() {
		return content;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	
	public String getSystemCode() {
		return systemCode;
	}

	public void setOuId(Integer ouId) {
		this.ouId = ouId;
	}

	
	public Integer getOuId() {
		return ouId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
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

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUpdatedUserName() {
		return updatedUserName;
	}

	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}

	public String getUpdatedUserFullName() {
		return updatedUserFullName;
	}

	public void setUpdatedUserFullName(String updatedUserFullName) {
		this.updatedUserFullName = updatedUserFullName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
