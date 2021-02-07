package com.sie.saaf.base.orgStructure.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BasePersonAssignEntity_HI_RO Entity Object
 * Sat Jul 21 09:15:24 CST 2018  Auto Generate
 */

public class BasePersonAssignEntity_HI_RO {
    public static final String QUERY_PERSON_ASSIGN_SQL = "select distinct " +
         //   "             bpa.assign_id assignId    " +
            "            bpa.ou_id ouId    " +
            "            ,ouBlv.meaning ouName\n" +
            "            ,bpa.person_id personId    " +
            "            ,basePerson.person_name personName    " +
            "            ,basePosition.position_id positionId    " +
            "            ,basePosition.position_name positionName    " +
            "            ,department.department_id departmentId    " +
            "            ,department.department_name departmentName    " +
            "            ,bpa.mgr_flag mgrFlag    " +
            "            ,bpa.primary_flag primaryFlag    " +
            "            ,bpa.enable_flag enableFlag    " +
            "            ,bpa.budget_view budgetView    " +
            "            ,blv.meaning budgetViewName    " +
            "            ,bpa.budget_maintain budgetMaintain    " +
            "            ,(case when  bpa.budget_maintain = 'Y' then '是' else '否' end ) budgetMaintainName    " +
            "            ,bpa.job_id jobId    " +
            "            ,job.job_name jobName    " +
            // "         ,bpa.date_from dateFrom    " +
           // "          ,bpa.date_to dateTo    " +
            "            ,bu.user_name createdByName    " +
           // "          ,bpa.creation_date creationDate    " +
            "  from base_person_assign bpa    " +
            "       LEFT JOIN base_lookup_values blv ON blv.lookup_code = bpa.budget_view AND blv.lookup_type = 'BUDGET_VIEW' AND blv.system_code = 'BASE'     " +
            "            ,base_position basePosition    " +
            "            ,base_department department    " +
            "            ,base_job job    " +
            "            ,base_person basePerson    " +
            "            ,base_users bu    " +
            "            ,base_lookup_values ouBlv\n" +
            " where 1 = 1    " +
            "     and bpa.delete_flag = 0    " +
            "     and bpa.position_id = basePosition.position_id    " +
            "     and basePosition.department_id = department.department_id    " +
            "     and bpa.job_id = job.job_id    " +
            "     and bpa.person_id = basePerson.person_id    " +
          //  "     and bpa.created_by = bu.user_id    " +
            "     AND bpa.person_id = bu.person_id " +
            "     AND department.ou_id = ouBlv.lookup_code\n" +
            "     AND ouBlv.lookup_type = 'BASE_OU'\n" +
            "     AND ouBlv.system_code = 'BASE'\n";

    public static final String QUERY_EFFECTIVE_PERSON_ASSIGN_SQL = "select bpa.assign_id assignId    " +
            "            ,bpa.ou_id ouId    " +
            "            ,bpa.person_id personId    " +
            "            ,bpa.position_id positionId    " +
            "  from base_person_assign bpa    " +
            " where 1 = 1    " +
            "     and bpa.delete_flag = 0    " +
            "     and bpa.date_from <= trunc(sysdate)    " +
            "     and bpa.date_to >= trunc(sysdate)    " +
            "     and bpa.ou_id = :ouId    " +
            "     and bpa.person_id = :personId    ";

    public static final String FIND_HAVE_BUDGET_MAINTAIN="SELECT    " +
            "    bpa.assign_id assignId,    " +
            "    bpa.ou_id ouId,    " +
            "    bpa.person_id personId,    " +
            "    bpa.position_id positionId,    " +
            "    bpa.budget_maintain budgetMaintain,    " +
            "    bpa.budget_view budgetView    " +
            "FROM    " +
            "    base_person_assign bpa     " +
            "WHERE    " +
            "    1 = 1     " +
            "    AND bpa.delete_flag = 0     " +
            "    AND bpa.enable_flag='Y'    " +
            "    AND bpa.budget_maintain='Y'  " +
            "    AND bpa.date_from <= trunc(sysdate) AND bpa.date_to >= trunc(sysdate)     ";

    public static final String QUERY_LAST_UPDATE_INFO_SQL = "SELECT t1.assign_id assignId\n" +
            "\t\t\t,t1.ou_id ouId\n" +
            "\t\t\t,t2.department_id departmentId\n" +
            "\t\t\t,t1.person_id personId\n" +
            "\t\t\t,t1.date_from dateFrom\n" +
            "\t\t\t,t1.date_to dateTo\n" +
            "\t\t\t,t1.budget_view budgetView\n" +
            "\t\t\t,t1.budget_maintain budgetMaintain\n" +
            "\t\t\t,t1.job_id jobId\n" +
            "\t\t\t,t1.last_update_login lastUpdateLogin\n" +
            "\t\t\t,t1.last_updated_by lastUpdatedBy\n" +
            "\t\t\t,t1.last_update_date lastUpdateDate\n" +
            "\t\t\t,t1.created_by createdBy\n" +
            "\t\t\t,t1.creation_date creationDate\n" +
            "\t\t\t,t1.mgr_flag mgrFlag\n" +
            "\t\t\t,t1.position_id positionId\n" +
            "\t\t\t,t1.primary_flag primaryFlag\n" +
            "\t\t\t,t1.enable_flag enableFlag\n" +
            "  FROM base_person_assign t1\n" +
            "\t\t\t,base_position t2\n" +
            " WHERE 1 = 1\n" +
            "\t AND t1.position_id = t2.position_id\n";

    public static final String INSERT_DEPARTMENT_PERSON_SQL = "INSERT INTO base_department_person\n" +
            "  (department_person_id\n" +
            "  ,org_id\n" +
            "  ,department_structure_id\n" +
            "  ,department_id\n" +
            "  ,person_id\n" +
            "  ,effective_beg_date\n" +
            "  ,effective_end_date\n" +
            "  ,budget_view\n" +
            "  ,budget_maintain\n" +
            "  ,job_id\n" +
            "  ,parent_person_id\n" +
            "  ,parent_person_department_id\n" +
            "  ,last_update_login\n" +
            "  ,last_updated_by\n" +
            "  ,last_update_date\n" +
            "  ,created_by\n" +
            "  ,creation_date\n" +
            "  ,attribute_category\n" +
            "  ,attribute1\n" +
            "  ,attribute2\n" +
            "  ,attribute3\n" +
            "  ,attribute4\n" +
            "  ,attribute5\n" +
            "  ,attribute6\n" +
            "  ,attribute7\n" +
            "  ,attribute8\n" +
            "  ,attribute9\n" +
            "  ,attribute10\n" +
            "  ,attribute11\n" +
            "  ,attribute12\n" +
            "  ,attribute13\n" +
            "  ,attribute14\n" +
            "  ,attribute15\n" +
            "  ,supervisor_flag\n" +
            "  ,position_id\n" +
            "  ,primary_flag\n" +
            "  ,enable_flag)\n" +
            "VALUES\n" +
            "  (:departmentPersonId\n" +
            "  ,:orgId\n" +
            "  ,NULL\n" +
            "  ,:departmentId\n" +
            "  ,:personId\n" +
            "  ,to_date(':dateFrom','yyyy-mm-dd')\n" +
            "  ,to_date(':dateTo','yyyy-mm-dd')\n" +
            "  ,':budgetView'\n" +
            "  ,':budgetMaintain'\n" +
            "  ,:jobId\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,:lastUpdateLogin\n" +
            "  ,:lastUpdatedBy\n" +
            "  ,sysdate\n" +
            "  ,:createdBy\n" +
            "  ,sysdate\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,NULL\n" +
            "  ,':supervisorFlag'\n" +
            "  ,:positionId\n" +
            "  ,':primaryFlag'\n" +
            "  ,':enableFlag')\n";

    public static final String UPDATE_DEPARTMENT_PERSON_SQL = "UPDATE base_department_person\n" +
            "   SET org_id = :orgId\n" +
            "      ,department_id = :departmentId\n" +
            "      ,person_id = :personId\n" +
            "      ,effective_beg_date = to_date(:dateFrom,'yyyy-mm-dd')\n" +
            "      ,effective_end_date = to_date(:dateTo,'yyyy-mm-dd')\n" +
            "      ,budget_view = :budgetView\n" +
            "      ,budget_maintain = :budgetMaintain\n" +
            "      ,job_id = :jobId\n" +
            "      ,last_update_login = :lastUpdateLogin\n" +
            "      ,last_updated_by = :lastUpdatedBy\n" +
            "      ,last_update_date = sysdate\n" +
            "      ,supervisor_flag = :supervisorFlag\n" +
            "      ,position_id = :positionId\n" +
            "      ,primary_flag = :primaryFlag\n" +
            "      ,enable_flag = :enableFlag\n" +
            " WHERE department_person_id = :departmentPersonId";

    private Integer assignId; //主键ID
    private Integer personId; //人员ID
    private String personName;
    private Integer positionId; //职位ID
    private String positionName;
    private Integer departmentId;
    private String departmentName;
    private Integer jobId; //职务ID
    private String jobName;
    @JSONField(format = "yyyy-MM-dd")
    private Date dateFrom; //生效日期
    @JSONField(format = "yyyy-MM-dd")
    private Date dateTo; //失效日期
    private Integer ouId; //事业部
    private String ouName;
    private String mgrFlag; //部门负责人标识(Y/N)
    private String primaryFlag; //主职位标识(Y/N)
    private String enableFlag; //生效标识(Y/N)
    private String budgetView; //查看预算：1--无，2—查看本级部门预算，3-查看本级及下级部门预算
    private String budgetMaintain; //修改预算：Y/N
    private String budgetViewName; //查看预算：1--无，2—查看本级部门预算，3-查看本级及下级部门预算
    private String budgetMaintainName; //修改预算：Y/N
    /*private Integer attribute1;
    private Integer attribute2;
    private Integer attribute3;*/
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdatedBy;
    private Integer lastUpdateLogin;
    private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
    private Integer versionNum; //版本号
    private Integer operatorUserId;
    private String createdByName;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
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

    public String getMgrFlag() {
        return mgrFlag;
    }

    public void setMgrFlag(String mgrFlag) {
        this.mgrFlag = mgrFlag;
    }

    public String getPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(String primaryFlag) {
        this.primaryFlag = primaryFlag;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getBudgetView() {
        return budgetView;
    }

    public void setBudgetView(String budgetView) {
        this.budgetView = budgetView;
    }

    public String getBudgetMaintain() {
        return budgetMaintain;
    }

    public void setBudgetMaintain(String budgetMaintain) {
        this.budgetMaintain = budgetMaintain;
    }

    public String getBudgetViewName() {
        return budgetViewName;
    }

    public void setBudgetViewName(String budgetViewName) {
        this.budgetViewName = budgetViewName;
    }

    public String getBudgetMaintainName() {
        return budgetMaintainName;
    }

    public void setBudgetMaintainName(String budgetMaintainName) {
        this.budgetMaintainName = budgetMaintainName;
    }

    /*public Integer getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(Integer attribute1) {
        this.attribute1 = attribute1;
    }

    public Integer getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(Integer attribute2) {
        this.attribute2 = attribute2;
    }

    public Integer getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(Integer attribute3) {
        this.attribute3 = attribute3;
    }*/

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

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }
}
