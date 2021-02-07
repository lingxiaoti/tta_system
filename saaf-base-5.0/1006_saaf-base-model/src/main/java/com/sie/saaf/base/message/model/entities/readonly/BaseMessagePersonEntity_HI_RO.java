package com.sie.saaf.base.message.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseMessagePersonEntity_HI_RO Entity Object
 * Tue Jul 10 16:14:51 CST 2018  Auto Generate
 */

public class BaseMessagePersonEntity_HI_RO {
    public static final String QUERY_MESSAGE_PERSON_COUNT_SQL = "SELECT COUNT(1) countNum\n" +
            "  FROM (\n" +
            "\t\t\t\tSELECT person.employee_number personCode\n" +
            "\t\t\t\t\t\t\t,person.person_name personName\n" +
            "\t\t\t\t\t\t\t,dept.department_name departmentName\n" +
            "\t\t\t\t\t\t\t,if(messageInstation.mess_status = 0, '未读', '已读') messStatus\n" +
            "\t\t\t\t\tFROM base_message_person messagePerson\n" +
            "\t\t\t\t\t\t\t,base_users users\n" +
            "\t\t\t\t\t\t\t,base_person person\n" +
            "\t\t\t\t\t\t\t,base_department dept\n" +
            "\t\t\t\t\t\t\t,base_message_instation messageInstation\n" +
            "\t\t\t\t WHERE 1 = 1\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = :conMessId\n" +
            "\t\t\t\t\t AND messagePerson.dep_mess_id = :depMessId\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = :deptId\n" +
            "\t\t\t\t\t AND messagePerson.delete_flag = 0\n" +
            "\t\t\t\t\t AND messagePerson.user_type = '20'\n" +
            "\t\t\t\t\t AND messagePerson.user_id = users.user_id\n" +
            "\t\t\t\t\t AND messagePerson.user_type = users.user_type\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = dept.department_id\n" +
            "\t\t\t\t\t AND users.person_id = person.person_id\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = messageInstation.con_mess_id\n" +
            "\t\t\t\t\t AND messagePerson.user_id = messageInstation.mess_receiver_id\n" +
            "\t\t\t\t\t AND messageInstation.delete_flag = 0 \n" +
            "\t\t\t\tUNION ALL\n" +
            "\t\t\t\tSELECT customer.customer_number personCode\n" +
            "\t\t\t\t\t\t\t,customer.customer_name personName\n" +
            "\t\t\t\t\t\t\t,dept.department_name departmentName\n" +
            "\t\t\t\t\t\t\t,if(messageInstation.mess_status = 0, '未读', '已读') messStatus\n" +
            "\t\t\t\t\tFROM base_message_person messagePerson\n" +
            "\t\t\t\t\t\t\t,base_users users\n" +
            "\t\t\t\t\t\t\t,base_customer customer\n" +
            "\t\t\t\t\t\t\t,base_department dept\n" +
            "\t\t\t\t\t\t\t,base_message_instation messageInstation\n" +
            "\t\t\t\t WHERE 1 = 1\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = :conMessId\n" +
            "\t\t\t\t\t AND messagePerson.dep_mess_id = :depMessId\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = :deptId\n" +
            "\t\t\t\t\t AND messagePerson.delete_flag = 0\n" +
            "\t\t\t\t\t AND messagePerson.user_type = '30'\n" +
            "\t\t\t\t\t AND messagePerson.user_id = users.user_id\n" +
            "\t\t\t\t\t AND messagePerson.user_type = users.user_type\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = dept.department_id\n" +
            "\t\t\t\t\t AND users.cust_account_id = customer.customer_id\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = messageInstation.con_mess_id\n" +
            "\t\t\t\t\t AND messagePerson.user_id = messageInstation.mess_receiver_id\n" +
            "\t\t\t\t\t AND messageInstation.delete_flag = 0 \n" +
            "\t\t\t\tUNION ALL\n" +
            "\t\t\t\tSELECT invStoreT.store_code personCode\n" +
            "\t\t\t\t\t\t\t,invStoreT.store_name personName\n" +
            "\t\t\t\t\t\t\t,dept.department_name departmentName\n" +
            "\t\t\t\t\t\t\t,if(messageInstation.mess_status = 0, '未读', '已读') messStatus\n" +
            "\t\t\t\t\tFROM base_message_person messagePerson\n" +
            "\t\t\t\t\t\t\t,base_users users\n" +
            "\t\t\t\t\t\t\t,base_inv_store_t invStoreT\n" +
            "\t\t\t\t\t\t\t,base_department dept\n" +
            "\t\t\t\t\t\t\t,base_message_instation messageInstation\n" +
            "\t\t\t\t WHERE 1 = 1\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = :conMessId\n" +
            "\t\t\t\t\t AND messagePerson.dep_mess_id = :depMessId\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = :deptId\n" +
            "\t\t\t\t\t AND messagePerson.delete_flag = 0\n" +
            "\t\t\t\t\t AND messagePerson.user_type = '40'\n" +
            "\t\t\t\t\t AND messagePerson.user_id = users.user_id\n" +
            "\t\t\t\t\t AND messagePerson.user_type = users.user_type\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = dept.department_id\n" +
            "\t\t\t\t\t AND users.user_id = invStoreT.store_user_id\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = messageInstation.con_mess_id\n" +
            "\t\t\t\t\t AND messagePerson.user_id = messageInstation.mess_receiver_id\n" +
            "\t\t\t\t\t AND messageInstation.delete_flag = 0) s\n";

    public static final String QUERY_MESSAGE_PERSON_SQL = "SELECT s.personCode personCode\n" +
            "       ,s.personName personName \n" +
            "       ,s.departmentName departmentName\n" +
            "       ,s.messStatus messStatus \n" +
            "  FROM (\n" +
            "\t\t\t\tSELECT person.employee_number personCode\n" +
            "\t\t\t\t\t\t\t,person.person_name personName\n" +
            "\t\t\t\t\t\t\t,dept.department_name departmentName\n" +
            "\t\t\t\t\t\t\t,if(messageInstation.mess_status = 0, '未读', '已读') messStatus\n" +
            "\t\t\t\t\tFROM base_message_person messagePerson\n" +
            "\t\t\t\t\t\t\t,base_users users\n" +
            "\t\t\t\t\t\t\t,base_person person\n" +
            "\t\t\t\t\t\t\t,base_department dept\n" +
            "\t\t\t\t\t\t\t,base_message_instation messageInstation\n" +
            "\t\t\t\t WHERE 1 = 1\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = :conMessId\n" +
            "\t\t\t\t\t AND messagePerson.dep_mess_id = :depMessId\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = :deptId\n" +
            "\t\t\t\t\t AND messagePerson.delete_flag = 0\n" +
            "\t\t\t\t\t AND messagePerson.user_type = '20'\n" +
            "\t\t\t\t\t AND messagePerson.user_id = users.user_id\n" +
            "\t\t\t\t\t AND messagePerson.user_type = users.user_type\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = dept.department_id\n" +
            "\t\t\t\t\t AND users.person_id = person.person_id\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = messageInstation.con_mess_id\n" +
            "\t\t\t\t\t AND messagePerson.user_id = messageInstation.mess_receiver_id\n" +
            "\t\t\t\t\t AND messageInstation.delete_flag = 0 \n" +
            "\t\t\t\tUNION ALL\n" +
            "\t\t\t\tSELECT customer.customer_number personCode\n" +
            "\t\t\t\t\t\t\t,customer.customer_name personName\n" +
            "\t\t\t\t\t\t\t,dept.department_name departmentName\n" +
            "\t\t\t\t\t\t\t,if(messageInstation.mess_status = 0, '未读', '已读') messStatus\n" +
            "\t\t\t\t\tFROM base_message_person messagePerson\n" +
            "\t\t\t\t\t\t\t,base_users users\n" +
            "\t\t\t\t\t\t\t,base_customer customer\n" +
            "\t\t\t\t\t\t\t,base_department dept\n" +
            "\t\t\t\t\t\t\t,base_message_instation messageInstation\n" +
            "\t\t\t\t WHERE 1 = 1\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = :conMessId\n" +
            "\t\t\t\t\t AND messagePerson.dep_mess_id = :depMessId\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = :deptId\n" +
            "\t\t\t\t\t AND messagePerson.delete_flag = 0\n" +
            "\t\t\t\t\t AND messagePerson.user_type = '30'\n" +
            "\t\t\t\t\t AND messagePerson.user_id = users.user_id\n" +
            "\t\t\t\t\t AND messagePerson.user_type = users.user_type\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = dept.department_id\n" +
            "\t\t\t\t\t AND users.cust_account_id = customer.customer_id\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = messageInstation.con_mess_id\n" +
            "\t\t\t\t\t AND messagePerson.user_id = messageInstation.mess_receiver_id\n" +
            "\t\t\t\t\t AND messageInstation.delete_flag = 0 \n" +
            "\t\t\t\tUNION ALL\n" +
            "\t\t\t\tSELECT invStoreT.store_code personCode\n" +
            "\t\t\t\t\t\t\t,invStoreT.store_name personName\n" +
            "\t\t\t\t\t\t\t,dept.department_name departmentName\n" +
            "\t\t\t\t\t\t\t,if(messageInstation.mess_status = 0, '未读', '已读') messStatus\n" +
            "\t\t\t\t\tFROM base_message_person messagePerson\n" +
            "\t\t\t\t\t\t\t,base_users users\n" +
            "\t\t\t\t\t\t\t,base_inv_store_t invStoreT\n" +
            "\t\t\t\t\t\t\t,base_department dept\n" +
            "\t\t\t\t\t\t\t,base_message_instation messageInstation\n" +
            "\t\t\t\t WHERE 1 = 1\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = :conMessId\n" +
            "\t\t\t\t\t AND messagePerson.dep_mess_id = :depMessId\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = :deptId\n" +
            "\t\t\t\t\t AND messagePerson.delete_flag = 0\n" +
            "\t\t\t\t\t AND messagePerson.user_type = '40'\n" +
            "\t\t\t\t\t AND messagePerson.user_id = users.user_id\n" +
            "\t\t\t\t\t AND messagePerson.user_type = users.user_type\n" +
            "\t\t\t\t\t AND messagePerson.dept_id = dept.department_id\n" +
            "\t\t\t\t\t AND users.user_id = invStoreT.store_user_id\n" +
            "\t\t\t\t\t AND messagePerson.con_mess_id = messageInstation.con_mess_id\n" +
            "\t\t\t\t\t AND messagePerson.user_id = messageInstation.mess_receiver_id\n" +
            "\t\t\t\t\t AND messageInstation.delete_flag = 0) s" +
            " ORDER BY s.personCode \n";

    public static final String QUERY_MESSAGE_TARGET_PERSON_SQL = "select\n" +
            "\t messPerson.per_mess_id perMessId,\n" +
            "\t messPerson.con_mess_id conMessId,\n" +
            "\t messPerson.bu_mess_id buMessId,\n" +
            "\t messPerson.dep_mess_id depMessId,\n" +
            "\t messPerson.user_id userId,\n" +
            "\t s.personName personName,\n" +
            "\t messPerson.user_type userType,\n" +
            "\t messPerson.dept_id departmentId,\n" +
            "\t s.departmentName departmentName,\n" +
            "\t s.departmentCode departmentCode\n" +
            "from \n" +
            "\t base_message_person messPerson,\n" +
            "\t base_message_bu bu,\n" +
            "\t (:userTypeTable) s\n" +
            "where \n" +
            "\t bu.bu_mess_id = :buMessId\n" +
            "\t and messPerson.delete_flag = 0\n" +
            "\t and bu.bu_mess_id = messPerson.bu_mess_id\n" +
            "\t and messPerson.user_id = s.userId\n" +
            "\t and messPerson.dept_id = s.departmentId";

    private Integer perMessId; //主键Id
    private Integer conMessId; //消息内容ID
    private Integer buMessId; //接收对象组合表主键ID
    private Integer depMessId; //接收部门表主键ID
    private Integer deptId; //接收部门ID
    private String userType; //接收用户类型
    private Integer userId; //接收用户ID
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer lastUpdatedBy;
    private Integer versionNum;
    private Integer deleteFlag;
    private Integer operatorUserId;

    private String personCode;
    private String personName;
    private String messStatus; //消息状态

    private String departmentName;
    private Integer departmentId;
    private String departmentCode;

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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPerMessId() {
        return perMessId;
    }

    public void setPerMessId(Integer perMessId) {
        this.perMessId = perMessId;
    }

    public Integer getConMessId() {
        return conMessId;
    }

    public void setConMessId(Integer conMessId) {
        this.conMessId = conMessId;
    }

    public Integer getBuMessId() {
        return buMessId;
    }

    public void setBuMessId(Integer buMessId) {
        this.buMessId = buMessId;
    }

    public Integer getDepMessId() {
        return depMessId;
    }

    public void setDepMessId(Integer depMessId) {
        this.depMessId = depMessId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getMessStatus() {
        return messStatus;
    }

    public void setMessStatus(String messStatus) {
        this.messStatus = messStatus;
    }
}
