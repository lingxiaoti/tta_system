package com.sie.watsons.base.questionnaire.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * SaafQuestionnaireHEntity_HI Entity Object
 * Tue Nov 13 15:56:38 CST 2018  Auto Generate
 */
@Entity
@Table(name="tta_questionnaire_header")
public class SaafQuestionnaireHEntity_HI {
    private Integer qnHeadId; //表ID，主键，供其他表做外键
    private String qnTitle; //问卷标题
    private String qnCode; //问卷编码
    private String qnPlatform; //问卷应用平台
    private String qnType; //问卷类型
    private Integer orgId; //组织ID
    private String investigateObject; //调查对象
    private String qnStatus; //问卷状态
    private String status; //状态
    private String breakPointAnswer; //断点续传
    private String processInstanceId; //流程ID
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDateActive; //起始日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDateActive; //终止日期
    private String description; //说明、备注
    private String bgColor; //背景颜色
    private String bgImagePath; //背景图片
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

	public void setQnHeadId(Integer qnHeadId) {
		this.qnHeadId = qnHeadId;
	}
	
	@Id
	@SequenceGenerator(name = "SEQ_TTA_QUESTIONNAIRE_HEADER", sequenceName = "SEQ_TTA_QUESTIONNAIRE_HEADER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_QUESTIONNAIRE_HEADER", strategy = GenerationType.SEQUENCE)
	@Column(name="qn_head_id", nullable=false, length=11)	
	public Integer getQnHeadId() {
		return qnHeadId;
	}

	public void setQnTitle(String qnTitle) {
		this.qnTitle = qnTitle;
	}

	@Column(name="qn_title", nullable=true, length=500)	
	public String getQnTitle() {
		return qnTitle;
	}

	public void setQnCode(String qnCode) {
		this.qnCode = qnCode;
	}

	@Column(name="qn_code", nullable=true, length=100)	
	public String getQnCode() {
		return qnCode;
	}

	public void setQnPlatform(String qnPlatform) {
		this.qnPlatform = qnPlatform;
	}

	@Column(name="qn_platform", nullable=true, length=100)	
	public String getQnPlatform() {
		return qnPlatform;
	}

	public void setQnType(String qnType) {
		this.qnType = qnType;
	}

	@Column(name="qn_type", nullable=true, length=100)	
	public String getQnType() {
		return qnType;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Column(name="org_id", nullable=true, length=11)	
	public Integer getOrgId() {
		return orgId;
	}

	public void setInvestigateObject(String investigateObject) {
		this.investigateObject = investigateObject;
	}

	@Column(name="investigate_object", nullable=true, length=100)	
	public String getInvestigateObject() {
		return investigateObject;
	}

	public void setQnStatus(String qnStatus) {
		this.qnStatus = qnStatus;
	}

	@Column(name="qn_status", nullable=true, length=100)	
	public String getQnStatus() {
		return qnStatus;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status", nullable=true, length=100)	
	public String getStatus() {
		return status;
	}

	public void setBreakPointAnswer(String breakPointAnswer) {
		this.breakPointAnswer = breakPointAnswer;
	}

	@Column(name="break_point_answer", nullable=true, length=100)	
	public String getBreakPointAnswer() {
		return breakPointAnswer;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Column(name="process_instance_id", nullable=true, length=100)	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	@Column(name="start_date_active", nullable=true, length=0)	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	@Column(name="end_date_active", nullable=true, length=0)	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=500)	
	public String getDescription() {
		return description;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	@Column(name="bg_color", nullable=true, length=100)	
	public String getBgColor() {
		return bgColor;
	}

	public void setBgImagePath(String bgImagePath) {
		this.bgImagePath = bgImagePath;
	}

	@Column(name="bg_image_path", nullable=true, length=500)	
	public String getBgImagePath() {
		return bgImagePath;
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
