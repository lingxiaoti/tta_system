package com.sie.watsons.base.questionnaire.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * SaafTestQuestionnaireLEntity_HI_RO Entity Object
 * Sat Feb 16 14:17:42 CST 2019  Auto Generate
 */

public class SaafTestQuestionnaireLEntity_HI_RO {

	public static final String QUERY_TEST_QUESTIONNAIRE = "" +
			" select a.qn_line_id,\n" +
			"     a.serial_number,\n" +
			"     a.project_type,\n" +
			"     a.project_title,\n" +
			"     a.project_title_alt,\n" +
			"     a.require_flag,\n" +
			"     a.display_flag,\n" +
			"     a.description,\n" +
			"     a.calc_rule,\n" +
			"     a.version_num,\n" +
			"     a.creation_date,\n" +
			"     a.created_by,\n" +
			"     a.last_updated_by,\n" +
			"     a.last_update_date,\n" +
			"     a.last_update_login,\n" +
			"     b.rule_name as calc_rule_name,\n" +
			"     a.original_qn_line_id,\n" +
			"     a.proposal_id,"
			+ "   a.project_category\n" +
			"from tta_test_questionnaire_line a\n" +
			"left join tta_base_rule b\n" +
			"  on a.calc_rule = b.rule_id\n" +
			"where 1 = 1 ";

    private Integer qnLineId; //表ID，主键，供其他表做外键
    private Integer qnHeadId; //头ID
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
    private Integer proposalId;
	private Integer originalQnLineId;
	private String projectCategory; //题目类别 1:一级题目，2:二级题目 3:规则题型

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

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	
	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitleAlt(String projectTitleAlt) {
		this.projectTitleAlt = projectTitleAlt;
	}

	
	public String getProjectTitleAlt() {
		return projectTitleAlt;
	}

	public void setRequireFlag(String requireFlag) {
		this.requireFlag = requireFlag;
	}

	
	public String getRequireFlag() {
		return requireFlag;
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

	public void setOriginalQnLineId(Integer originalQnLineId) {
		this.originalQnLineId = originalQnLineId;
	}

	public Integer getOriginalQnLineId() {
		return originalQnLineId;
	}
	
	public String getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}
}
