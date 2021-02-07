package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmProjectExceptionEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:06 CST 2019  Auto Generate
 */

public class PlmProjectExceptionEntity_HI_RO {
    private Integer plmProjectExceptionId;
    private Integer plmProjectId;
    private Integer plmDevelopmentInfoId;
	private Integer plmProjectProductDetailId;
	private String exceptionProductName;
    @JSONField(format="yyyy-MM-dd")
    private Date exceptionOccurrenceTime;
    private String exceptionOccurrenceStage;
    private String exceptionDetails;
    private String results;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    public static final String QUERY_SQL = "SELECT ppe.PLM_PROJECT_EXCEPTION_ID      as plmProjectExceptionId,\n" +
			"       ppe.PLM_PROJECT_ID                as plmProjectId,\n" +
			"       ppe.PLM_DEVELOPMENT_INFO_ID       as plmDevelopmentInfoId,\n" +
			"       ppe.EXCEPTION_PRODUCT_NAME        as exceptionProductName,\n" +
			"       ppe.EXCEPTION_OCCURRENCE_TIME     as exceptionOccurrenceTime,\n" +
			"       ppe.EXCEPTION_OCCURRENCE_STAGE    as exceptionOccurrenceStage,\n" +
			"       ppe.EXCEPTION_DETAILS             as exceptionDetails,\n" +
			"       ppe.RESULTS                       as results,\n" +
			"       ppe.CREATED_BY                    as createdBy,\n" +
			"       ppe.LAST_UPDATED_BY               as lastUpdatedBy,\n" +
			"       ppe.LAST_UPDATE_LOGIN             as lastUpdateLogin,\n" +
			"       ppe.LAST_UPDATE_DATE              as lastUpdateDate,\n" +
			"       ppe.VERSION_NUM                   as versionNum,\n" +
			"       ppe.CREATION_DATE                 as creationDate,\n" +
			"       ppe.PLM_PROJECT_PRODUCT_DETAIL_ID as plmProjectProductDetailId\n" +
			"\n" +
			"FROM PLM_PROJECT_EXCEPTION ppe WHERE 1=1 ";

	public void setPlmProjectExceptionId(Integer plmProjectExceptionId) {
		this.plmProjectExceptionId = plmProjectExceptionId;
	}

	
	public Integer getPlmProjectExceptionId() {
		return plmProjectExceptionId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public String getExceptionProductName() {
		return exceptionProductName;
	}

	public void setExceptionProductName(String exceptionProductName) {
		this.exceptionProductName = exceptionProductName;
	}

	public void setExceptionOccurrenceTime(Date exceptionOccurrenceTime) {
		this.exceptionOccurrenceTime = exceptionOccurrenceTime;
	}

	
	public Date getExceptionOccurrenceTime() {
		return exceptionOccurrenceTime;
	}

	public void setExceptionOccurrenceStage(String exceptionOccurrenceStage) {
		this.exceptionOccurrenceStage = exceptionOccurrenceStage;
	}

	
	public String getExceptionOccurrenceStage() {
		return exceptionOccurrenceStage;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	
	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setResults(String results) {
		this.results = results;
	}

	
	public String getResults() {
		return results;
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

	public Integer getPlmProjectProductDetailId() {
		return plmProjectProductDetailId;
	}

	public void setPlmProjectProductDetailId(Integer plmProjectProductDetailId) {
		this.plmProjectProductDetailId = plmProjectProductDetailId;
	}
}
