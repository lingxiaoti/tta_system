package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BasePositionEntity_HI_RO Entity Object
 * Wed Dec 06 10:57:32 CST 2017  Auto Generate
 */

public class BasePositionPerson_HI_RO {
	/*查询职位字段*/
	public static final String QUERY_POSITION_FIELD = "	basePosition.position_id AS positionId ,\r\n" + 
    		//"	basePosition.org_id AS orgId ,\r\n" + 
    		"	basePosition.position_name AS positionName ,\r\n" + 
    		"	basePosition.source_system_id AS sourceSystemId ,\r\n" + 
    		"	basePosition.start_date AS startDate ,\r\n" +
    		"	basePosition.end_date AS endDate ,\r\n" + 
    		"	basePosition.enabled AS enabled,\r\n" + 
    		"	basePosition.creation_date AS creationDate ,\r\n" + 
    		"	basePosition.created_by AS createdBy ,\r\n" + 
    		"	basePosition.last_updated_by AS lastUpdatedBy ,\r\n" + 
    		"	basePosition.last_update_date AS lastUpdateDate ,\r\n" + 
    		"	basePosition.version_num AS versionNum\r\n" ;
    
    /*查询组织、人员与职位关系*/
    public static final String QUERY_JOIN_PERSON_ORGANIZATION_SQL = "SELECT \r\n" + 
    		QUERY_POSITION_FIELD + 
    		" FROM \r\n" +
    		"   base_position AS basePosition, \r\n"+
    		"   base_person_organization  basePersonOrganization \r\n" +
    		" WHERE basePosition.position_id=basePersonOrganization.position_id \r\n";

	/*查询人员、职位、部门关系*/
	public static final String QUERY_PERSON_POSITION_ORG_RELATION = "SELECT bp.position_id positionId \n" +
			"\t\t\t,bp.position_name positionName \n" +
			"\t\t\t,bd.ou_id orgId\n" +
			"\t\t\t,bpa.primary_flag primaryFlag \n" +
			"\t\t\t,bd.department_id deptId \n" +
			"\t\t\t,bd.department_code deptCode \n" +
			"\t\t\t,bd.department_name deptName \n" +
			"\t\t\t,job.job_id jobId\n" +
			"\t\t\t,job.job_code jobCode\n" +
			"\t\t\t,job.job_name jobName\n" +
			"\t\t\t,bp.channel channel \n" +
			"\t\t\t,basePerson.person_id personId\n" +
			"\t\t\t,basePerson.person_name personName\n" +
			"\tFROM base_person basePerson\n" +
			"\t\t\t,base_person_assign bpa\n" +
			"\t\t\t,base_position bp\n" +
			"\t\t\t,base_department bd\n" +
			"\t\t\t,base_job job\n" +
			" WHERE 1 = 1\n" +
			"\t AND basePerson.person_id = :personId\n" +
			"\t AND basePerson.person_id = bpa.person_id \n" +
			"\t AND bpa.enable_flag = 'Y'\n" +
			"\t AND bpa.date_from <= sysdate\n" +
			"\t AND (bpa.date_to is null or bpa.date_to >= sysdate)\n" +
			"\t AND bpa.position_id = bp.position_id\n" +
			"\t AND bp.department_id = bd.department_id\n" +
			"\t AND bd.enable_flag = 'Y'\n" +
			"\t AND bp.job_id = job.job_id\n" +
			"\t AND job.enable_flag = 'Y'\n" +
			"\t AND job.delete_flag = 0\n";

	private String primaryFlag; //主职位标识
	private Integer deptId; //部门Id
	private String deptCode; //部门编码
	private String deptName; //部门名称
	private Integer jobId; //职务ID
	private String jobCode; //职务编码
	private String jobName; //职务名称
	private String channel; //渠道
	private Integer personId;
	private String personName;

	private Integer positionId; //职位ID
    private Integer orgId; //组织机构Id
    private String positionName; //职位名称
    private String sourceSystemId; //源系统Id
    @JSONField(format = "yyyy-MM-dd")
    private Date startDate; //启用日期
    @JSONField(format = "yyyy-MM-dd")
    private Date endDate; //失效日期
    private String enabled; //是否启用（Y：启用；N：禁用）
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer operatorUserId;

	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	public static String getQueryPositionField() {
		return QUERY_POSITION_FIELD;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	
	public Integer getPositionId() {
		return positionId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	
	public Integer getOrgId() {
		return orgId;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	
	public String getPositionName() {
		return positionName;
	}

	public void setSourceSystemId(String sourceSystemId) {
		this.sourceSystemId = sourceSystemId;
	}

	
	public String getSourceSystemId() {
		return sourceSystemId;
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

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	
	public String getEnabled() {
		return enabled;
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
}
