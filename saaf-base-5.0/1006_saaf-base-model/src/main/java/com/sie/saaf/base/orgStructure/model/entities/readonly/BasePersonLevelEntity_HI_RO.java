package com.sie.saaf.base.orgStructure.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BasePersonLevelEntity_HI_RO Entity Object
 * Sat Jul 21 09:15:30 CST 2018  Auto Generate
 */

public class BasePersonLevelEntity_HI_RO {

    //新增Oracle的职位层级信息
    public static final String INSERT_BASE_PERSON_LEVEL_SQL="INSERT INTO BASE_PERSON_LEVEL (\n" +
            "     LEVEL_ID,\n" +
            "     PERSON_ID,\n" +
            "     POSITION_ID,\n" +
            "     MGR_PERSON_ID,\n" +
            "     MGR_POSITION_ID,\n" +
            "     DATE_FROM,\n" +
            "     DATE_TO,\n" +
            "     OU_ID,\n" +
            "     ENABLE_FLAG,\n" +
            "     CREATION_DATE,\n" +
            "     CREATED_BY,\n" +
            "     LAST_UPDATE_DATE,\n" +
            "     LAST_UPDATED_BY,\n" +
            "     LAST_UPDATE_LOGIN,\n" +
            "     DELETE_FLAG,\n" +
            "     VERSION_NUM \n" +
            ")VALUES(\n" +
            "          ':levelId',\n" +
            "          ':personId',\n" +
            "          ':positionId',\n" +
            "          ':mgrPersonId',\n" +
            "          ':mgrPositionId',\n" +
            "          TO_DATE( ':dateFrom', 'yyyy-MM-dd' ),\n" +
            "          TO_DATE( ':dateTo', 'yyyy-MM-dd' ),\n" +
            "          ':ouId',\n" +
            "          ':enableFlag',\n" +
            "          sysdate,\n" +
            "          ':createdBy',\n" +
            "          sysdate,\n" +
            "          ':lastUpdateBy',\n" +
            "          ':lastUpdateLogin',\n" +
            "          ':deleteFlag',\n" +
            "          ':versionNum' \n" +
            ")  ";

    public static final String UPDATE_BASE_PERSON_LEVEL_SQL="UPDATE BASE_PERSON_LEVEL \n" +
            "   SET\n" +
            "       PERSON_ID = ':personId',\n" +
            "       POSITION_ID = ':positionId',\n" +
            "       MGR_PERSON_ID = ':mgrPersonId',\n" +
            "       MGR_POSITION_ID = ':mgrPositionId',\n" +
            "       DATE_FROM = TO_DATE( ':dateFrom', 'yyyy-MM-dd' ),\n" +
            "       DATE_TO = TO_DATE( ':dateTo', 'yyyy-MM-dd' ),\n" +
            "       OU_ID = ':ouId',\n" +
            "       ENABLE_FLAG = ':enableFlag',\n" +
            "       CREATION_DATE =sysdate,\n" +
            "       CREATED_BY = ':createdBy',\n" +
            "       LAST_UPDATE_DATE =sysdate,\n" +
            "       LAST_UPDATED_BY = ':lastUpdateBy',\n" +
            "       LAST_UPDATE_LOGIN = ':lastUpdateLogin',\n" +
            "       DELETE_FLAG = ':deleteFlag',\n" +
            "       VERSION_NUM = ':versionNum' \n" +
            "   WHERE\n" +
            "     LEVEL_ID = :levelId  ";

    //获取最新的可同步到ESS的职位层级信息
    public static final String FIND_LAST_UPDATE_LIST_SQL="select \n" +
            "  bpl.level_id,\n" +
            "  bpl.person_id,\n" +
            "  bpl.position_id,\n" +
            "  bpl.mgr_person_id,\n" +
            "  bpl.mgr_position_id,\n" +
            "  bpl.date_from,\n" +
            "  bpl.date_to,\n" +
            "  bpl.ou_id,\n" +
            "  bpl.enable_flag,\n" +
            "  bpl.delete_flag,\n" +
            "  bpl.version_num,\n" +
            "  bpl.creation_date,\n" +
            "  bpl.created_by,\n" +
            "  bpl.last_update_date,\n" +
            "  bpl.last_updated_by,\n" +
            "  bpl.last_update_login\n" +
            "from \n" +
            "base_person_level bpl where 1=1 ";

    //查询oracle中有没有存在某一个id值
    public static final String FIND_ORACLE_PERSON_LEVEL_BY_ID="SELECT bpl.LEVEL_ID FROM base_person_level bpl where 1=1 ";

    public static final String QUERY_PERSON_LEVEL_SQL = "SELECT\n" +
            "     bpl.level_id levelId,\n" +
            "     bpl.ou_id ouId,\n" +
            "     ouBlv.meaning ouName,\n" +
            "     department.department_id mgrDepartmentId,\n" +
            "     department.department_name mgrDepartmentName,\n" +
            "     bpl.mgr_position_id mgrPositionId,\n" +
            "     mgrBasePosition.position_name mgrPositionName,\n" +
            "     mgrBasePerson.person_id mgrPersonId,\n" +
            "     mgrBasePerson.person_name mgrPersonName,\n" +
            "     bpl.position_id positionId,\n" +
            "     basePosition.position_name positionName,\n" +
            "     basePerson.person_id personId,\n" +
            "     basePerson.person_name personName,\n" +
            "     bpl.enable_flag enableFlag,\n" +
            "     bpl.date_from dateFrom,\n" +
            "     bpl.date_to dateTo,\n" +
            "     bu.user_name createdByName,\n" +
            "     bpl.creation_date creationDate \n" +
            "FROM\n" +
            "     base_person_level bpl\n" +
            "     LEFT JOIN base_person mgrBasePerson ON mgrBasePerson.person_id = bpl.mgr_person_id\n" +
            "     LEFT JOIN base_person basePerson ON basePerson.person_id = bpl.person_id\n" +
            "     LEFT JOIN base_position mgrBasePosition ON mgrBasePosition.position_id = bpl.mgr_position_id\n" +
            "     LEFT JOIN base_department department ON department.department_id = mgrBasePosition.department_id,\n" +
            "     base_position basePosition,\n" +
            "     base_users bu,\n" +
            "     base_lookup_values ouBlv \n" +
            "WHERE\n" +
            "     1 = 1 \n" +
            "     AND bpl.position_id = basePosition.position_id \n" +
            "     AND bpl.created_by = bu.user_id \n" +
            "     AND bpl.ou_id = ouBlv.lookup_code \n" +
            "     AND ouBlv.lookup_type = 'BASE_OU' \n" +
            "     AND ouBlv.system_code = 'BASE'   ";

    private Integer mgrDepartmentId;
    private String mgrDepartmentName;
    private Integer levelId; //主键ID
    private Integer personId; //人员ID
    private String personName;//人员名称
    private Integer positionId; //职位ID
    private String positionName;//职位名称
    private Integer mgrPersonId; //上级人员ID
    private String mgrPersonName;//上级人员名称
    private Integer mgrPositionId; //上级职位ID
    private String mgrPositionName;//上级职位名称
    @JSONField(format = "yyyy-MM-dd")
    private Date dateFrom; //生效日期
    @JSONField(format = "yyyy-MM-dd")
    private Date dateTo; //失效日期
    private Integer ouId; //事业部
    private String ouName;
    private String enableFlag; //生效标识(Y/N)
    /*private Integer attribute1;
    private Integer attribute2;
    private Integer sourceSystemId;*/
    private Integer deleteFlag; //是否删除（0：未删除；1：已删除）
    private Integer versionNum; //版本号
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdatedBy;
    private Integer operatorUserId;
    private String createdByName; //创建人
    private Integer lastUpdateLogin;

    public Integer getMgrDepartmentId() {
        return mgrDepartmentId;
    }

    public void setMgrDepartmentId(Integer mgrDepartmentId) {
        this.mgrDepartmentId = mgrDepartmentId;
    }

    public String getMgrDepartmentName() {
        return mgrDepartmentName;
    }

    public void setMgrDepartmentName(String mgrDepartmentName) {
        this.mgrDepartmentName = mgrDepartmentName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
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

    public Integer getMgrPersonId() {
        return mgrPersonId;
    }

    public void setMgrPersonId(Integer mgrPersonId) {
        this.mgrPersonId = mgrPersonId;
    }

    public String getMgrPersonName() {
        return mgrPersonName;
    }

    public void setMgrPersonName(String mgrPersonName) {
        this.mgrPersonName = mgrPersonName;
    }

    public Integer getMgrPositionId() {
        return mgrPositionId;
    }

    public void setMgrPositionId(Integer mgrPositionId) {
        this.mgrPositionId = mgrPositionId;
    }

    public String getMgrPositionName() {
        return mgrPositionName;
    }

    public void setMgrPositionName(String mgrPositionName) {
        this.mgrPositionName = mgrPositionName;
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

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
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

    public Integer getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(Integer sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }*/

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

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }
}
