package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmHelpStatementsEntity_HI_RO Entity Object
 * Sat Nov 09 11:47:05 CST 2019  Auto Generate
 */

public class PlmHelpStatementsEntity_HI_RO {
    private String creator;
    private Integer plmHelpStatementsId;
    private String fieldCode;
    private String fieldMeaning;
    private String billStatusName;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    public static final String QUERY_SQL = "SELECT phs.PLM_HELP_STATEMENTS_ID as plmHelpStatementsId,\n" +
			"       phs.FIELD_CODE as fieldCode,\n" +
			"       phs.FIELD_MEANING as fieldMeaning,\n" +
			"       phs.BILL_STATUS_NAME as billStatusName,\n" +
			"       phs.CREATOR as creator,\n" +
			"       phs.CREATED_BY as createdBy,\n" +
			"       phs.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       phs.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       phs.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       phs.VERSION_NUM as versionNum,\n" +
			"       phs.CREATION_DATE as creationDate\n" +
			"FROM PLM_HELP_STATEMENTS phs\n" +
			"WHERE 1=1 ";

	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	public String getCreator() {
		return creator;
	}

	public void setPlmHelpStatementsId(Integer plmHelpStatementsId) {
		this.plmHelpStatementsId = plmHelpStatementsId;
	}

	
	public Integer getPlmHelpStatementsId() {
		return plmHelpStatementsId;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	
	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldMeaning(String fieldMeaning) {
		this.fieldMeaning = fieldMeaning;
	}

	
	public String getFieldMeaning() {
		return fieldMeaning;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	
	public String getBillStatusName() {
		return billStatusName;
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

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
