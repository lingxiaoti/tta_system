package com.sie.saaf.schedule.model.entities.readonly;


import java.io.Serializable;


public class ScheduleJobsRespEntity_HI_RO implements Serializable {

    public ScheduleJobsRespEntity_HI_RO() {
        super();
    }
    
    
    public static String QUERY_JOBS_SQL = "SELECT\n" +
    		"	er.responsibility_name responsibilityName,\n" +
    		"	er.responsibility_id responsibilityId,\n" +
    		"	ejr.job_resp_id jobRespId,\n" +
    		"	trunc(er.end_date_active) endDateActive,\n" +
    		"	trunc(er.start_date_active) startDateActive\n" +
    		"FROM\n" +
    		"	base_responsibility er,\n" +
    		"	schedule_job_resp ejr\n" +
    		"WHERE\n" +
    		"	er.responsibility_id = ejr.responsibility_id\n" +
    		"AND trunc(er.start_date_active) <= trunc(sysdate)\n" +
    		"AND (\n" +
    		"	er.end_date_active IS NULL\n" +
    		"	OR trunc(er.end_date_active) >= trunc(sysdate)\n" +
    		")\n" +
    		"AND ejr.job_id = :jobId ";
    //LOV:查询未分配给JOB的所有职责
    public static String QUERY_ASSIGNED_RESP_TO_JOB_SQL = "SELECT\n" +
    		"	sr.RESPONSIBILITY_ID responsibilityId,\n" +
    		"	sr.RESPONSIBILITY_NAME responsibilityName,\n" +
    		"	sr.RESPONSIBILITY_code responsibilityKey,\n" +
    		"	sr.system_CODE platformCode\n" +
    		"FROM\n" +
    		"	base_responsibility sr\n" +
    		"WHERE\n" +
    		"	1 = 1\n" +
    		"AND date_format(\n" +
    		"	sr.start_date_active,\n" +
    		"	'%Y-%m-%d'\n" +
    		") <= trunc(sysdate)\n" +
    		"AND (\n" +
    		"	sr.end_date_active IS NULL\n" +
    		"	OR date_format(\n" +
    		"		sr.end_date_active,\n" +
    		"		'%Y-%m-%d'\n" +
    		"	) >= trunc(sysdate)\n" +
    		")\n" +
    		"AND sr.RESPONSIBILITY_ID NOT IN (\n" +
    		"	SELECT\n" +
    		"		sur.RESPONSIBILITY_ID\n" +
    		"	FROM\n" +
    		"		schedule_job_resp sur\n" +
    		"	WHERE\n" +
    		"		sur.job_Id = :jobId\n" +
    		")";
    
    private Integer employeeId;
    private Integer memberId;
    private String isadmin;
    private String startDateActive;
    private String endDateActive;
   

    private String responsibilityName;
    private Integer responsibilityId;
    private Integer jobRespId;
    private Integer jobId;
    private String userName;
    private String userFullName;
    private String employeeName;
    private String platformCode;
    private String responsibilityKey;

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    public String getResponsibilityName() {
        return responsibilityName;
    }

    public void setResponsibilityId(Integer responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    public Integer getResponsibilityId() {
        return responsibilityId;
    }
    public Integer getJobRespId() {
		return jobRespId;
	}

	public void setJobRespId(Integer jobRespId) {
		this.jobRespId = jobRespId;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setResponsibilityKey(String responsibilityKey) {
        this.responsibilityKey = responsibilityKey;
    }

    public String getResponsibilityKey() {
        return responsibilityKey;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setIsadmin(String isadmin) {
        this.isadmin = isadmin;
    }

    public String getIsadmin() {
        return isadmin;
    }

    public void setStartDateActive(String startDateActive) {
        this.startDateActive = startDateActive;
    }

    public String getStartDateActive() {
        return startDateActive;
    }

    public void setEndDateActive(String endDateActive) {
        this.endDateActive = endDateActive;
    }

    public String getEndDateActive() {
        return endDateActive;
    }
}
