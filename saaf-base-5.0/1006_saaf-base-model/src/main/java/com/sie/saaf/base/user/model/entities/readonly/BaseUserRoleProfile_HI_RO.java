package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

/**
 * Created by Administrator on 2019/11/27/027.
 */
public class BaseUserRoleProfile_HI_RO {
//    public static final String SQL_USER_ROLE_PROFILE ="select u.user_id,u.user_name userName,lc.meaning userType, \n" +
//            "u.USER_FULL_NAME userFullName,bd.DEPARTMENT_NAME dept, \n" +
//            "u.PHONE_NUMBER phoneNumber, \n" +
//            "p.email,bres.responsibility_name responsibilityName,br.role_name roleName, \n" +
//
//            "(select \n" +
//            "listagg(SUPPLIER_NAME,'；') within group(order by PROFILE_ID) profile from  \n" +
//            " (select DISTINCT ts.supplier_code||ts.SUPPLIER_NAME SUPPLIER_NAME ,bpv.PROFILE_ID  " +
//            " from  base_profile bp \n" +
//            "join BASE_PROFILE_VALUE bpv on bp.PROFILE_ID = bpv.PROFILE_ID  \n" +
//            "left join TTA_SUPPLIER ts on ts.supplier_code = bpv.PROFILE_VALUE  \n" +
//            "where bpv.key_table_name = 'base_users'  \n" +
//            "and bp.PROFILE_CODE = 'VENDOR'  \n" +
//            "and business_key = u.user_id ) abc  \n" +
//
//            ") profile,u.CREATION_DATE creationDate,bur.end_date_active endDate  \n" +
//            " from BASE_USERS u  \n" +
//            "left JOIN  \n" +
//            "BASE_PERSON p on u.PERSON_ID = p.PERSON_ID  \n" +
//            "left JOIN base_user_responsibility bur ON bur.user_id = u.user_id  \n" +
//            "left JOIN base_responsibility_role brr ON brr.responsibility_id = bur.responsibility_id  \n" +
//            "left JOIN base_responsibility bres ON brr.responsibility_id = bres.responsibility_id  \n" +
//            "left JOIN base_role br ON br.role_id = brr.role_id  \n" +
//            "left join BASE_DEPARTMENT bd on bd.DEPARTMENT_CODE = p.DEPT_NO \n" +
//            "left join (select lookup_code,meaning from base_lookup_values b  \n" +
//            "where b.lookup_type = 'USER_TYPE' and system_code = 'BASE' \n" +
//            "group by lookup_code,meaning ) lc  \n" +
//            "on u.user_type = lc.lookup_code  \n" +
//            "where 1=1   \n" +
//            "";
    public static final String SQL_USER_ROLE_PROFILE ="select *\n" +
        "  from (select u.user_id userId,\n" +
        "               u.user_name userName,\n" +
        "               u.user_type userType,\n" +
        "               lc.meaning userTypeName,\n" +
        "               u.USER_FULL_NAME userFullName,\n" +
        "               bd.DEPARTMENT_NAME dept,\n" +
        "               u.PHONE_NUMBER phoneNumber,\n" +
        "               nvl(p.email,u.email_address),\n" +
        "               bres.system_code,\n" +
        "               lookup1.meaning systemName,\n" +
        "               bres.responsibility_name responsibilityName,\n" +
        "               br.role_name roleName,\n" +
        "               (select listagg(PROFILE_VALUE || SUPPLIER_NAME, '；') within group(order by PROFILE_ID) profile\n" +
        "                  from (select DISTINCT bpv.PROFILE_VALUE,\n" +
        "                                        ts.SUPPLIER_NAME,\n" +
        "                                        bpv.PROFILE_ID\n" +
        "                          from base_profile bp\n" +
        "                          join BASE_PROFILE_VALUE bpv\n" +
        "                            on bp.PROFILE_ID = bpv.PROFILE_ID\n" +
        "                          left join TTA_SUPPLIER ts\n" +
        "                            on ts.supplier_code = bpv.PROFILE_VALUE\n" +
        "                         where bpv.key_table_name = 'base_users'\n" +
        "                           and BP.DELETE_FLAG = 0\n" +
        "                           and bpv.DELETE_FLAG = 0\n" +
        "                           and (bp.PROFILE_CODE = 'VENDOR' OR\n" +
        "                               bp.PROFILE_CODE = 'VENDOR_GROUP' OR\n" +
        "                               bp.PROFILE_CODE = 'VENDOR_EXTERAL')\n" +
        "                           and business_key = u.user_id) abc) profile,\n" +
        "               to_char(u.CREATION_DATE, 'yyyy-mm-dd hh24:mi:ss') creationDate,\n" +
        "               to_char(u.END_DATE, 'yyyy-mm-dd hh24:mi:ss') endDate\n" +
        "          from BASE_USERS u\n" +
        "          left JOIN BASE_PERSON p\n" +
        "            on u.PERSON_ID = p.PERSON_ID\n" +
        "          left JOIN base_user_responsibility bur\n" +
        "            ON bur.user_id = u.user_id\n" +
        "           AND (bur.END_DATE_ACTIVE > SYSDATE OR bur.END_DATE_ACTIVE IS NULL)\n" +
        "          left JOIN base_responsibility_role brr\n" +
        "            ON brr.responsibility_id = bur.responsibility_id\n" +
        "          left JOIN base_responsibility bres\n" +
        "            ON bur.responsibility_id = bres.responsibility_id\n" +
        "          left JOIN base_role br\n" +
        "            ON br.role_id = brr.role_id\n" +
        "          left join BASE_DEPARTMENT bd\n" +
        "            on bd.DEPARTMENT_CODE = p.DEPT_NO\n" +
        "          left join (select lookup_code, meaning\n" +
        "                      from base_lookup_values b\n" +
        "                     where b.lookup_type = 'USER_TYPE'\n" +
        "                       and system_code = 'BASE'\n" +
        "                     group by lookup_code, meaning) lc\n" +
        "            on u.user_type = lc.lookup_code\n" +
        "          left join (\n" +
        "             select lookup_type,lookup_code,meaning\n" +
        "               from base_lookup_values where lookup_type='SYSTEM_CODE' and enabled_flag='Y'\n" +
        "               and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null) and system_code='PUBLIC'\n" +
        "          ) lookup1 on  bres.system_code =  lookup1.lookup_code\n" +
        "                   ) T1\n" +
        " WHERE 1 = 1";
//"order by u.user_id  \n" +
    private Integer userId;
    private String userName; //用户账号
    private String userType; //用户类型
    private String userTypeName; // 用户类型转义
    private String userFullName;
    private String phoneNumber;
    private String email;
    private String responsibilityName;
    private String roleName;
    private String profile;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String dept;
    private String systemName;


    public Integer getUserId() {
    return userId;
  }

    public void setUserId(Integer userId) {
    this.userId = userId;
  }


    public String getUserTypeName() {
      return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
      this.userTypeName = userTypeName;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsibilityName() {
        return responsibilityName;
    }

    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
