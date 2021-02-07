package com.sie.saaf.base.orgStructure.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BasePositionEntity_HI_RO Entity Object
 * Sat Jul 21 09:15:32 CST 2018  Auto Generate
 */

public class BasePositionNewEntity_HI_RO {

    /**
     * 职位层级树
     */
    public static final String QUERY_POSITION_TREE=" SELECT\n" +
            "   bpl.mgr_position_id parentPositionId,\n" +
            "   bpl.mgr_person_id parentPersonId,\n" +
            "   bpl.position_id position_id,\n" +
            "   bpl.person_id person_id,\n" +
            "   t3.position_name position_name,\n" +
            "   t3.position_code position_code,\n" +
            "   t2.person_name person_name,\n" +
            "   CONCAT( t3.position_name, IF ( t2.person_name IS NULL, '', '-' ), nvl ( t2.person_name, '' ) ) all_name \n" +
            "FROM\n" +
            "   base_person_level bpl\n" +
            "   LEFT JOIN base_person t2 ON t2.person_id = bpl.person_id,\n" +
            "   base_position t3 \n" +
            "WHERE\n" +
            "      bpl.position_id = t3.position_id  "+
            "  AND bpl.ou_id=t3.ou_id   ";

    /*public static final String QUERY_POSITION_SQL = "SELECT department.department_id departmentId\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,position.position_id positionId\n" +
            "\t\t\t,position.position_code positionCode\n" +
            "\t\t\t,position.position_name positionName\n" +
            "\t\t\t,position.job_id jobId\n" +
            "\t\t\t,job.job_name jobName\n" +
            "\t\t\t,position.date_from dateFrom\n" +
            "\t\t\t,position.date_to dateTo\n" +
            "\t\t\t,position.enable_flag enableFlag\n" +
            "\t\t\t,position.biz_line_type bizLineType\n" +
            "\t\t\t,position.channel channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "\t\t\t,position.ou_id ouId\n" +
            "      ,ouBlv.meaning ouName\n" +
            "\t\t\t,users.user_name createdByName\n" +
            "\t\t\t,position.creation_date creationDate\n" +
            "  FROM base_position position\n" +
            "\t\t\t,base_department department\n" +
            "\t\t\t,base_job job\n" +
            "\t\t\t,base_users users\n" +
            "\t\t\t,base_channel channel\n" +
            "      ,base_lookup_values ouBlv\n" +
            " WHERE 1 = 1\n" +
            "\t AND position.ou_id = department.ou_id\n" +
            "\t AND position.department_id = department.department_id\n" +
            "\t AND position.job_id = job.job_id\n" +
            "\t AND position.ou_id = job.ou_id\n" +
            "\t AND position.created_by = users.user_id\n" +
            "\t AND position.channel = channel.CHANNEL_CODE \n" +
            "   AND department.ou_id = ouBlv.lookup_code\n" +
            "   AND ouBlv.lookup_type = 'BASE_OU'\n" +
            "   AND ouBlv.system_code = 'BASE'\n";*/

    public static final String QUERY_POSITION_SQL = "SELECT department.department_id   departmentId,\n" +
            "       department.department_code departmentCode,\n" +
            "       department.department_name departmentName,\n" +
            "       position.position_id       positionId,\n" +
            "       position.position_code     positionCode,\n" +
            "       position.position_name     positionName,\n" +
            "       position.job_id            jobId,\n" +
            "       job.job_name               jobName\n" +
            "  from base_position position \n" +
            "  left join base_department department\n" +
            "  on department.department_id = position.department_id\n" +
            "  left join base_job job\n" +
            "  on position.job_id = job.job_id where 1 = 1 ";


    public static final String QUERY_LAST_UPDATE_INFO_SQL = "SELECT position.position_id positionId\n" +
            "\t\t\t,position.position_code positionCode\n" +
            "\t\t\t,position.position_name positionName\n" +
            "\t\t\t,position.ou_id ouId\n" +
            "\t\t\t,position.department_id departmentId\n" +
            "\t\t\t,position.job_id jobId\n" +
            "\t\t\t,position.date_from dateFrom\n" +
            "\t\t\t,position.date_to dateTo\n" +
            "\t\t\t,position.enable_flag enableFlag\n" +
            "\t\t\t,position.biz_line_type bizLineType\n" +
            "\t\t\t,position.channel channelCode\n" +
            "\t\t\t,position.created_by createdBy\n" +
            "\t\t\t,position.last_updated_by lastUpdatedBy\n" +
            "\t\t\t,position.last_update_login lastUpdateLogin\n" +
            "\t\t\t,position.delete_flag deleteFlag\n" +
            "\t\t\t,position.version_num versionNum\n" +
            "  FROM base_position position\n" +
            " WHERE 1 = 1\n";

    public static final String INSERT_POSITION_SQL = "INSERT INTO base_position\n" +
            "  (position_id\n" +
            "  ,ou_id\n" +
            "  ,channel\n" +
            "  ,position_code\n" +
            "  ,position_name\n" +
            "  ,department_id\n" +
            "  ,job_id\n" +
            "  ,biz_line_type\n" +
            "  ,date_from\n" +
            "  ,date_to\n" +
            "  ,enable_flag\n" +
            "  ,delete_flag\n" +
            "  ,version_num\n" +
            "  ,creation_date\n" +
            "  ,created_by\n" +
            "  ,last_updated_by\n" +
            "  ,last_update_date\n" +
            "  ,last_update_login)\n" +
            "VALUES\n" +
            "  (:positionId\n" +
            "  ,:ouId\n" +
            "  ,':channelCode'\n" +
            "  ,':positionCode'\n" +
            "  ,':positionName'\n" +
            "  ,:departmentId\n" +
            "  ,:jobId\n" +
            "  ,':bizLineType'\n" +
            "  ,to_date(':dateFrom','yyyy-mm-dd')\n" +
            "  ,to_date(':dateTo','yyyy-mm-dd')\n" +
            "  ,':enableFlag'\n" +
            "  ,:deleteFlag\n" +
            "  ,:versionNum\n" +
            "  ,sysdate\n" +
            "  ,:createdBy\n" +
            "  ,:lastUpdatedBy\n" +
            "  ,sysdate\n" +
            "  ,:lastUpdateLogin)\n";

    public static final String UPDATE_POSITION_SQL = "UPDATE base_position\n" +
            "   SET ou_id = :ouId\n" +
            "      ,channel = :channelCode\n" +
            "      ,position_code = :positionCode\n" +
            "      ,position_name = :positionName\n" +
            "      ,department_id = :departmentId\n" +
            "      ,job_id = :jobId\n" +
            "      ,biz_line_type = :bizLineType\n" +
            "      ,date_from = to_date(:dateFrom,'yyyy-mm-dd')\n" +
            "      ,date_to = to_date(:dateTo,'yyyy-mm-dd')\n" +
            "      ,enable_flag = :enableFlag\n" +
            "      ,delete_flag = :deleteFlag\n" +
            "      ,version_num = :versionNum\n" +
            "      ,last_updated_by = :lastUpdatedBy\n" +
            "      ,last_update_date = sysdate\n" +
            " WHERE position_id = :positionId";

    private Integer positionId; //职位ID
    private Integer departmentId; //部门Id
    private String departmentCode;//部门编号
    private String departmentName;//部门名称
    private Integer jobId; //职务ID
    private String jobName;//职务名称
    private String positionName; //职位名称
    private String positionCode; //职位编码
    @JSONField(format = "yyyy-MM-dd")
    private Date dateFrom; //生效日期
    @JSONField(format = "yyyy-MM-dd")
    private Date dateTo; //失效日期
    private String enableFlag; //是否启用（Y：启用；N：禁用）
    private String bizLineType; //业务线类型
    private String channel; //渠道
    private String channelCode;
    private String channelName;
    private Integer ouId; //事业部
    private String ouName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String createdByName;//创建人

    private Integer parentPositionId;
    private Integer parentPersonId;
    private Integer personId;
    private String personName;
    private String allName;

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getBizLineType() {
        return bizLineType;
    }

    public void setBizLineType(String bizLineType) {
        this.bizLineType = bizLineType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getOuId() {
        return ouId;
    }

    public void setOuId(Integer ouId) {
        this.ouId = ouId;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Integer getParentPositionId() {
        return parentPositionId;
    }

    public void setParentPositionId(Integer parentPositionId) {
        this.parentPositionId = parentPositionId;
    }

    public Integer getParentPersonId() {
        return parentPersonId;
    }

    public void setParentPersonId(Integer parentPersonId) {
        this.parentPersonId = parentPersonId;
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

    public String getAllName() {
        return allName;
    }

    public void setAllName(String allName) {
        this.allName = allName;
    }
}
