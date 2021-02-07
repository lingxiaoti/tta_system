package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

/** 根据用户id 查询用户信息、用户子库、组织、用户子库、组织
 * @author xiangyipo
 * @date 2018/2/5
 */
public class BasePDAUserMenuEntity_HI_RO {

    public static final String QUERY_USER_INFO_SQL = "SELECT \n" +
            "\tbu.user_id AS userId,\n" +
            "\tbu.user_name AS userName,\n" +
            "\tbu.user_full_name AS userFullName,\n" +
            "\tbu.encrypted_password AS password," +
            " '0' AS subInvCount \n" +
            "FROM\n" +
            "\tbase_users  bu\n" +
            "where bu.delete_flag = 0 \n" +
            "\t and 1=1 \n";

    //查询用户子库、组织
    public static final String QUERY_USER_WAREHOUSE_ORGANIZATION_SQL = "SELECT\n" +
            "\tinv.SUB_INV_CODE AS subInvCode,\n" +
            "\tinv.ROLE_ID AS roleId,\n" +
            "\thouse.WAREHOUSE_NAME AS subInvName,\n" +
            "\tinv.ORGANIZATION_ID AS organizationId,\n" +
            "\torg.ORGANIZATION_ID AS orgId,\n" +
            "\torg.org_name AS organizationName,\n" +
            "\tinv.CHANNEL_CODE AS channelCode \n" +
            "FROM\n" +
            "\tbase_pda_inv_cfg  inv\n" +
            "\tINNER JOIN base_users  bu ON bu.user_id = inv.USER_ID \n" +
            "\tAND bu.delete_flag = 0 \n" +
            "\tAND ( bu.start_date IS NULL or bu.start_date <=sysdate ) \n" +
            "\tAND ( bu.end_date IS NULL or bu.end_date >sysdate )\n" +
            "\tLEFT JOIN base_warehouse_mapping AS house ON house.delete_flag = 0 \n" +
            "\tAND house.WAREHOUSE_CODE = inv.SUB_INV_CODE \n" +
            "\tAND ( house.START_DATE_ACTIVE IS NULL or house.START_DATE_ACTIVE <=sysdate ) \n" +
            "\tAND ( house.END_DATE_ACTIVE IS NULL or house.END_DATE_ACTIVE >sysdate )\n" +
            "\tLEFT JOIN base_organization AS org ON org.org_id = house.ORGANIZATION_ID \n" +
            "\tAND org.enabled = 'Y' \n" +
            "\tAND ( org.start_date IS NULL or org.start_date <=sysdate ) \n" +
            "\tAND ( org.end_date IS NULL or org.end_date >sysdate ) \n" +
            "WHERE\n" +
            "\t1 = 1 and inv.DELETE_FLAG = '0'\n" ;

    //查询用户菜单
    public static final String QUERY_USER_MENU_SQL = "SELECT\n" +
            "\tmenu.menu_name AS menuName,\n" +
            "\tmenu.menu_code AS menuCode,\n" +
            "\tmenu.menu_type AS menuType,\n" +
            "\tmenu.order_no AS orderNo,\n" +
            "\tmenu.parameter AS functionId,\n" +
            "\tmenu.menu_parent_id AS parenMenuCode \n" +
            "FROM\n" +
            "\tbase_menu AS menu\n" +
            "\tJOIN base_pda_role_cfg role on role.MENU_ID = menu.menu_id\n" +
            "\tAND ( menu.start_date_active IS NULL or menu.start_date_active <= NOW( ) ) \n" +
            "\tAND ( menu.end_date_active IS NULL or menu.end_date_active > NOW( ) ) \n" +
            "\tAND menu.enabled = 'Y' \n" +
            "\tAND role.enabled = 'Y' \n" +
            "\tAND menu.system_code = 'PDA_MENU'\n" +
            "WHERE\n" +
            "\t1 = 1" ;

    private String parenMenuCode;
    private String subInvCode;
    private String channelCode;

    private String subInvName;
    private String subInvCount;
    private String organizationName;
    private String functionId;
    private String roleId;    //角色Id(菜单权限Id)

    private Integer userId; // 用户Id
    private String userName; // 用户名/登录帐号
    private String userFullName; // 姓名
    private String password; // 加密后密码（MD5）
    private String warehouseName; //仓库名称
    private Integer organizationId; //库存组织ID
    private Integer orgId;     //组织ID
    private String orgName; // 组织机构名称
    private String menuName; //菜单名称
    private String menuCode; //菜单编码
    private Integer orderNo; //排序号
    private String menuType; //菜单类型：10-菜单节点；20-功能节点
    private List userOrg;
    private JSONArray menus;



    public String getSubInvCode() {
        return subInvCode;
    }

    public void setSubInvCode(String subInvCode) {
        this.subInvCode = subInvCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getSubInvName() {
        return subInvName;
    }

    public void setSubInvName(String subInvName) {
        this.subInvName = subInvName;
    }

    public String getSubInvCount() {
        return subInvCount;
    }

    public void setSubInvCount(String subInvCount) {
        this.subInvCount = subInvCount;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getParenMenuCode() {
        return parenMenuCode;
    }

    public void setParenMenuCode(String parenMenuCode) {
        this.parenMenuCode = parenMenuCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public List getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(List userOrg) {
        this.userOrg = userOrg;
    }

    public List getMenus() {
        return menus;
    }

    public void setMenus(JSONArray menus) {
        this.menus = menus;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
