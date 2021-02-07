package com.sie.watsons.base.rule.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaBaseRuleLineEntity_HI_RO Entity Object
 * Mon Aug 05 15:50:33 CST 2019  Auto Generate
 */

public class TtaBaseRuleLineEntity_HI_RO {

	public static final String QUERY_LINE_HEADER_ID = "select " +
			"	a.rule_line_id, \t" +
			"	b.serial_number, \t" +
			"	b.project_cn_title,\t" +
			" 	a.is_enable\n" +
			" from TTA_BASE_RULE_LINE a\n" +
			" inner join tta_question_header b\n" +
			" on a.q_header_id = b.q_header_id where 1 = 1 ";

    private Integer ruleLineId;
    private Integer ruleId;
    private Integer qnHeadId;
    private Integer serialNumber;
    private String isEnable;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

    private String projectCnTitle;

	public void setRuleLineId(Integer ruleLineId) {
		this.ruleLineId = ruleLineId;
	}

	
	public Integer getRuleLineId() {
		return ruleLineId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	
	public Integer getRuleId() {
		return ruleId;
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

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	
	public String getIsEnable() {
		return isEnable;
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

	public void setProjectCnTitle(String projectCnTitle) {
		this.projectCnTitle = projectCnTitle;
	}

	public String getProjectCnTitle() {
		return projectCnTitle;
	}

}
