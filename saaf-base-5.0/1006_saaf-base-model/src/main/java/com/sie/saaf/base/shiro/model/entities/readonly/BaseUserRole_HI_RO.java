package com.sie.saaf.base.shiro.model.entities.readonly;

/**
 *
 * @author huangtao
 *
 */
public class BaseUserRole_HI_RO {


    public static final String QUERY = "SELECT\n" +
            "  max(bu.user_id)   AS userId,\n" +
            "  br.role_id   AS roleId,\n" +
            "  max(br.role_code) AS roleCode,\n" +
            "  max(br.role_name) AS roleName," +
            "  max(br.system_code) AS systemCode\n" +
            "  FROM base_users bu JOIN base_user_responsibility bur ON bur.user_id = bu.user_id\n" +
            "  JOIN base_responsibility_role brr ON brr.responsibility_id = bur.responsibility_id\n" +
            "  JOIN base_role br ON br.role_id = brr.role_id \n" +
            // "  AND SYSDATE BETWEEN ifnull(br.start_date_active, SUBDATE(SYSDATE, INTERVAL 1 DAY)) " +
            // "  AND ifnull(br.end_date_active, SUBDATE(SYSDATE, INTERVAL -1 DAY)) where 1=1 ";
            " AND SYSDATE BETWEEN nvl(br.start_date_active, (SYSDATE + 1)) \n" +
            " AND nvl(br.end_date_active, (SYSDATE -1)) and  bu.user_id=:userId \n";


    private Integer userId;
    private Integer roleId;
    private String roleCode;
    private String roleName;
    private String systemCode;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
