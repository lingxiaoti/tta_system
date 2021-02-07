package com.sie.saaf.schedule.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * ScheduleSchedulesErrorEntity_HI_RO Entity Object
 * Tue Mar 27 09:20:44 CST 2018  Auto Generate
 */

public class ScheduleSchedulesErrorEntity_HI_RO {


	public static final String QUERY_SQL =
			"select sse.error_id            errorId, " +
					"       sse.schedule_id          scheduleId, " +
					"       sse.schedule_data       scheduleData, " +
				//	"       sse.error_str   errorStr, " +
					"       sse.status            status, " +
					"       sse.description  description, " +
					"       sse.version_num            versionNum, " +
					"       sse.CREATION_DATE     creationDate, " +
					"       sse.CREATED_BY        createdBy, " +
					"       sse.LAST_UPDATED_BY   lastUpdateBy, " +
					"       sse.LAST_UPDATE_DATE  lastUpdateDate, " +
					"       sse.LAST_UPDATE_LOGIN lastUpdateLogin, " +
					"       lv1.MEANING          statusMeaning, " +
					"       sj.JOB_NAME          jobName  " +

					"  FROM       " +
					"       schedule_schedules_error sse       " +
					"       LEFT JOIN base_lookup_values lv1 ON lv1.LOOKUP_TYPE = 'CP_STATUS_CODE'        " +
					"       AND lv1.LOOKUP_CODE = sse.STATUS,       " +
					"       schedule_schedules ss,       " +
					"       schedule_jobs sj        " +
					"WHERE       " +
					"       sse.SCHEDULE_ID = ss.SCHEDULE_ID        " +
					"       AND ss.JOB_ID = sj.JOB_ID ";




	private Integer errorId; //表ID，主键，供其他表做外键
    private Integer scheduleId; //调度ID
    private String scheduleData; //调度执行信息
    private String errorStr; //执行异常信息
    private String status; //处理状态
    private String description; //说明、备注
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
//    private Integer operatorUserId;



	//
	private String statusMeaning;
	private String jobName;
	//


	public String getStatusMeaning() {
		return statusMeaning;
	}

	public void setStatusMeaning(String statusMeaning) {
		this.statusMeaning = statusMeaning;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
	}

	
	public Integer getErrorId() {
		return errorId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	
	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleData(String scheduleData) {
		this.scheduleData = scheduleData;
	}

	
	public String getScheduleData() {
		return scheduleData;
	}

	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}

	
	public String getErrorStr() {
		return errorStr;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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

//	public void setOperatorUserId(Integer operatorUserId) {
//		this.operatorUserId = operatorUserId;
//	}
//
//
//	public Integer getOperatorUserId() {
//		return operatorUserId;
//	}
}
