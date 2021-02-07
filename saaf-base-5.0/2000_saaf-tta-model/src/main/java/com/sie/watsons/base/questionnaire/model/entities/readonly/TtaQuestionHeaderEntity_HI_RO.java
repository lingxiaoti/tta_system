package com.sie.watsons.base.questionnaire.model.entities.readonly;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaQuestionHeaderEntity_HI_RO Entity Object
 * Thu Jul 25 21:34:10 CST 2019  Auto Generate
 */

public class TtaQuestionHeaderEntity_HI_RO {
	public static final String QUERY_HEADER_SQL = "select t.q_header_id,\n" +
			"t.serial_number,\n" +
			"t.project_type,\n" +
			"k.meaning as projectTypeName,\n" +
			"t.project_cn_title,\n" +
			"t.project_en_title,\n" +
			"t.is_enable,\n" +
			"t.description,\n" +
			"t.version_num,\n" +
			"t.creation_date,\n" +
			"t.created_by,\n" +
			"t.last_updated_by,\n" +
			"t.last_update_date,\n" +
			"t.last_update_login from tta_question_header t left join \n" +
			" base_lookup_values k on k.lookup_type = upper('PROJECT_TYPE')\n" +
			" and k.lookup_code=t.project_type  where 1 = 1 \n";
    private Integer qHeaderId;
    private Integer serialNumber;
    private String projectType;
    private String projectCnTitle;
    private String projectEnTitle;
    private String isEnable;
    private String description;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String projectTypeName; //题型名称，多选/单选
	//选项题信息
	private List<TtaQuestionChoiceLineEntity_HI_RO> qnChoiceData = new ArrayList<>();

	public void setQHeaderId(Integer qHeaderId) {
		this.qHeaderId = qHeaderId;
	}

	
	public Integer getQHeaderId() {
		return qHeaderId;
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

	public void setProjectCnTitle(String projectCnTitle) {
		this.projectCnTitle = projectCnTitle;
	}

	
	public String getProjectCnTitle() {
		return projectCnTitle;
	}

	public void setProjectEnTitle(String projectEnTitle) {
		this.projectEnTitle = projectEnTitle;
	}

	
	public String getProjectEnTitle() {
		return projectEnTitle;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
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

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setQnChoiceData(List<TtaQuestionChoiceLineEntity_HI_RO> qnChoiceData) {
		this.qnChoiceData = qnChoiceData;
	}

	public List<TtaQuestionChoiceLineEntity_HI_RO> getQnChoiceData() {
		return qnChoiceData;
	}

}
