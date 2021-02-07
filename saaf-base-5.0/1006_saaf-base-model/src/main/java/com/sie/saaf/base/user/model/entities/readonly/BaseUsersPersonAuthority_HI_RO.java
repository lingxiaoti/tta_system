package com.sie.saaf.base.user.model.entities.readonly;

/**
 * Created by WORK on 2019/7/11.
 */
public class BaseUsersPersonAuthority_HI_RO {


    public static final String SQL_USER_AUTHORITY ="select \n" +
            "        bu.user_id        userId\n" +
            "       ,max(bu.user_name)      userName\n" +
            "       ,max(bu.user_full_name)  userFullName\n" +
            "       ,max(bp.employee_number)  employeeNumber\n" +
            "       ,max(bu.data_type)  dataType\n" +
            "       ,max(bd.department_name) departmentName\n" +
            "       ,max(bu.version_num) versionNum\n" +
            "       from  \n" +
            "       base_users  bu\n" +
            "       left join  base_person bp on   bu.person_id = bp.person_id              --  寻找用户表\n" +
            "       left join  base_person_assign bpa  on   bp.person_id = bpa.person_id     -- 寻找职位分配表\n" +
            "       left join  base_position  bpn  on   bpa.position_id = bpn.position_id   --  寻找职位表\n" +
            "       left join   base_department bd on  bpn.department_id = bd.department_id  -- 寻找部门表\n" +
            "       \n" +
            "       where  1=1" ;

    public static final String SQL_USER_MAIL ="select \n" +
            "bp.email email\n" +
            "from  \n" +
            "base_users   bu,\n" +
            "base_person  bp\n" +
            "\n" +
            "where bu.person_id = bp.person_id\n";

    public static final String SQL_USER_DATA_TYPE_CHECK =" select count(1) counts from  \n" +
            "tta_user_group_dept_brand tu\n" +
            "where tu.data_type !=:dataType  and  nvl(tu.end_time,sysdate)>=sysdate and  nvl(tu.start_time,sysdate)<sysdate";

    private Integer userId; //用户Id
    private String userName; //用户名/登录帐号
    private String userFullName; //姓名
    private String employeeNumber; //员工号
    private String departmentName; //部门
    private String dataType; //数据类型
    private String dataTypeFlag; //数据类型
    private String email; //邮箱
    private Integer counts; //计数
    private Integer versionNum; //版本号

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeFlag() {
        return dataTypeFlag;
    }

    public void setDataTypeFlag(String dataTypeFlag) {
        this.dataTypeFlag = dataTypeFlag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }
}
