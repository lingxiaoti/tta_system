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
 * SaafQuestionnaireLEntity_HI Entity Object
 * Mon Nov 12 09:28:33 CST 2018  Auto Generate
 */
@Entity
@Table(name="tta_questionnaire_line")
public class SaafQuestionnaireLEntity_HI {
    private Integer qnLineId; //表ID，主键，供其他表做外键
    private Integer serialNumber; //题目序号
    private String projectType; //题目类型(单选/多选/文本)
    private String projectTitle; //题目标题
    private String projectTitleAlt; //题目标题别名
    private String requireFlag; //是否必需标记
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
	private String calcRule; //计算规则
	private String projectCategory; //题目类别 1:一级题目，2:二级题目 3:规则题型


	public void setQnLineId(Integer qnLineId) {
		this.qnLineId = qnLineId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_QUESTIONNAIRE_LINE", sequenceName = "SEQ_TTA_QUESTIONNAIRE_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_QUESTIONNAIRE_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="qn_line_id", nullable=false, length=11)	
	public Integer getQnLineId() {
		return qnLineId;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name="serial_number", nullable=true, length=11)	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	@Column(name="project_type", nullable=true, length=100)	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	@Column(name="project_title", nullable=true, length=500)	
	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitleAlt(String projectTitleAlt) {
		this.projectTitleAlt = projectTitleAlt;
	}

	@Column(name="project_title_alt", nullable=true, length=500)	
	public String getProjectTitleAlt() {
		return projectTitleAlt;
	}

	public void setRequireFlag(String requireFlag) {
		this.requireFlag = requireFlag;
	}

	@Column(name="require_flag", nullable=true, length=100)	
	public String getRequireFlag() {
		return requireFlag;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setCalcRule(String calcRule) {
		this.calcRule = calcRule;
	}

	@Column(name="calc_rule", nullable=true, length=50)
	public String getCalcRule() {
		return calcRule;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	@Column(name="project_category", nullable=true, length=2)
	public String getProjectCategory() {
		return projectCategory;
	}
}
