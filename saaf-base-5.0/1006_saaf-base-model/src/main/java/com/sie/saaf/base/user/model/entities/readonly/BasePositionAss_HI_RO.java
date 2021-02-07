package com.sie.saaf.base.user.model.entities.readonly;

public class BasePositionAss_HI_RO {
    public static String QUERY_POSITION_SQL="SELECT\n" +
            "\tassign.ou_id orgId,\n" +
            "\tdepartment.channel channelType,\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tmeaning\n" +
            "\t\tFROM\n" +
            "\t\t\tbase_lookup_values\n" +
            "\t\tWHERE\n" +
            "\t\t\tLOOKUP_TYPE = 'CHANNEL_TYPE'\n" +
            "\t\tAND lookup_code = department.channel\n" +
            "\t) channelTypeName,\n" +
            "\tdepartment.department_id deptId,\n" +
            "\tdepartment.department_name deptName,\n" +
            "\tperson.person_id personId,\n" +
            "\tperson.person_name personName,\n" +
            "\tposition.position_id positionId,\n" +
            "\tposition.position_name positionName,\n" +
            "\tjob.job_id jobId,\n" +
            "\tjob.job_name jobName,\n" +
            "\tassign.primary_flag primaryFlag,\n" +
            "\tusers.user_id userId\n" +
            "FROM\n" +
            "\tbase_person_assign assign,\n" +
            "\tbase_position position,\n" +
            "\tbase_person person,\n" +
            "\tbase_users users,\n" +
            "\tbase_department department,\n" +
            "\tbase_job job\n" +
            "WHERE\n" +
            "\tassign.position_id = position.position_id\n" +
            "AND person.person_id = users.person_id\n" +
            "AND assign.person_id = person.person_id\n" +
            "AND position.department_id = department.department_id\n" +
            "AND position.job_id = job.job_id\n" +
            "AND assign.enable_flag = 'Y'";

    private String channelType;//渠道编码

    private String channelTypeName;//渠道名称

    private Integer deptId;//部门ID

    private String deptName;//部门名称

    private Integer personId;//人员ID

    private String personName;//人员姓名

    private Integer positionId;//职位ID

    private String positionName;//职位名称

    private Integer jobId;//职务ID

    private String jobName;//职务名称

    private String primaryFlag;//是否主职位

    private Integer userId;//用户ID

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(String primaryFlag) {
        this.primaryFlag = primaryFlag;
    }
}
