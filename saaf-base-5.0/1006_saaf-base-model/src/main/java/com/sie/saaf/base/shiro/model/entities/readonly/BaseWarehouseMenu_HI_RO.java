package com.sie.saaf.base.shiro.model.entities.readonly;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/2.
 */
public class BaseWarehouseMenu_HI_RO {

    //pda 菜单同步
    public static final String SQL_WAREHOUSE_MENU = "SELECT DISTINCT\n" +
            "  user.USER_ID,\n" +
            "  inv.SUB_INV_CODE,\n" +
            "  mapping.WAREHOUSE_NAME INV_NAME,\n" +
            "  mapping.ORGANIZATION_ID,\n" +
            "  '库存组织'                 ORGANIZATION_NAME,\n" +
            "  menu.menu_id           FUNCTION_ID,\n" +
            "  menu.menu_name         FUNCTION_NAME,\n" +
            "  '1'                    FUNCTION_TYPE,\n" +
            "  menu.menu_parent_id    MENU_ID,\n" +
            "  menu.order_no          SORT_NUM\n" +
            "FROM\n" +
            "  base_pda_inv_cfg inv,\n" +
            "  base_warehouse_mapping mapping,\n" +
            "  base_users user,\n" +
            "  base_role role,\n" +
            "  base_user_responsibility responsibility,\n" +
            "  base_responsibility_role bro,\n" +
            "  base_role_menu brm,\n" +
            "  base_menu menu\n" +
            "WHERE inv.SUB_INV_CODE = mapping.WAREHOUSE_CODE\n" +
            "      AND inv.USER_ID = user.user_id\n" +
            "      AND user.user_id = responsibility.user_id\n" +
            "      AND responsibility.responsibility_id = bro.responsibility_id\n" +
            "      AND bro.role_id = role.role_id\n" +
            "      AND role.role_id = brm.role_id\n" +
            "      AND brm.menu_id = menu.menu_id\n" +
            "      AND menu.enabled = 'Y'\n" +
            "      AND inv.DELETE_FLAG = '0'\n" +
            "      AND responsibility.end_date_active > SYSDATE ";


    private Integer userId;
    private String subInvCode;
    private String invName;
    private Integer organizationId;
    private String organizationName;
    private Integer functionId;
    private String functionName;
    private String functionType;
    private Integer menuId;
    private Integer sortNum;
    private Date timeStamp;//pda同步用户时间

    public Date getTimeStamp() { return timeStamp; }

    public void setTimeStamp(Date timeStamp) {this.timeStamp = timeStamp;}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubInvCode() {
        return subInvCode;
    }

    public void setSubInvCode(String subInvCode) {
        this.subInvCode = subInvCode;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

}
