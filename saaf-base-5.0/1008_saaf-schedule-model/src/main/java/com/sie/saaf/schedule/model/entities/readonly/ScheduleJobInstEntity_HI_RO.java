package com.sie.saaf.schedule.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

import java.util.Date;


public class ScheduleJobInstEntity_HI_RO implements Serializable {


  

    //增加查询子节点的组织ID
    public static String QUERY_JOB_INST_TREE_SQL = "SELECT\n" +
    		"	sua.access_org_id accessOrgId,\n" +
    		"	sua.org_id orgId,\n" +
    		"	sua.job_id  jobId,\n" +
    		"	si.org_name orgName,\n" +
    		"	sua.start_date startDate,\n" +
    		"	sua.end_date endDate,\n" +
    		"	sua.platform_code platformCode\n" +
    		"FROM\n" +
    		"	schedule_job_access_orgs sua,\n" +
    		"	base_organization si\n" +
    		"WHERE\n" +
    		"	sua.org_id = si.org_id ";


    private Integer accessOrgId;
    private Integer parentInstId;
    private Integer orgId;
//    private String instCode;
    private String orgName;
    private Integer jobId;

    private String employeeName;
    @JSONField(format = "yyyy-MM-dd")
    private Date startDate;
    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;
    private String platformCode;


    public void setAccessOrgId(Integer accessOrgId) {
        this.accessOrgId = accessOrgId;
    }

    public Integer getAccessOrgId() {
        return accessOrgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}


    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setParentInstId(Integer parentInstId) {
        this.parentInstId = parentInstId;
    }

    public Integer getParentInstId() {
        return parentInstId;
    }

//    public void setInstCode(String instCode) {
//        this.instCode = instCode;
//    }
//
//    public String getInstCode() {
//        return instCode;
//    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }
}
