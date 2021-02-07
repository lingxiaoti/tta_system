package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TtaReportProcessHeaderEntity_HI_RO Entity Object
 * Fri Nov 22 14:15:42 CST 2019  Auto Generate
 */

public class TtaReportProcessHeaderEntity_HI_RO {
    private Integer reportProcessHeaderId;
    private String batchCode;
    private String status;
    private String reportType;
    private String backUp;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date passDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String createdByName;
	private String statusName;


	public static String TTA_REPORT_PROCCES_HEADER = "select h.*,\n" +
			" usr.user_full_name as created_by_name, lookup2.meaning statusName\n" +
			"  from tta_report_process_header h \n" +
			" left join base_users usr ON h.last_updated_by = usr.user_id \n" +
			" left join  (select lookup_type,lookup_code,meaning\n" +
			"        from base_lookup_values where lookup_type='TTA_OI_STATUS' and enabled_flag='Y'\n" +
			"      and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='BASE'\n" +
			"      ) lookup2 on lookup2.lookup_code = h.status\n" +
			" where 1=1";

	public void setReportProcessHeaderId(Integer reportProcessHeaderId) {
		this.reportProcessHeaderId = reportProcessHeaderId;
	}

	
	public Integer getReportProcessHeaderId() {
		return reportProcessHeaderId;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	
	public String getBatchCode() {
		return batchCode;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	
	public String getReportType() {
		return reportType;
	}

	public void setBackUp(String backUp) {
		this.backUp = backUp;
	}

	
	public String getBackUp() {
		return backUp;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	
	public Date getPassDate() {
		return passDate;
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

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
