package com.sie.saaf.base.user.model.entities.readonly;

public class BaseMenuRoleEntity_HI_RO {
    public static final String SQL="SELECT \n" +
            "\tmenu.menu_id AS menuId,\n" +
            "\tmenu.menu_name AS menuName,\n" +
            "\tmenu.menu_desc as menuDesc,\n" +
            "\tmenu.system_code AS systemCode,\n" +
            "\tmenu.html_url AS htmlUrl,\n" +
            "\tmenu.menu_code AS menuCode,\n" +
            "\tdic.meaning AS systemName,\n" +
            "\trole.ROLE_ID AS roleId\n" +
            "FROM base_menu menu \n" +
            "\tJOIN base_role_menu role ON role.menu_id=menu.menu_id AND menu_type='20' and level_id in (2,3) AND menu.from_type='pc' AND menu.enabled='Y' \n" +
            "\tand (menu.start_date_active is null or menu.start_date_active<=SYSDATE) \n" +
            "\tAND (menu.end_date_active is null or menu.end_date_active >= SYSDATE)\n" +
            "\tLEFT JOIN base_lookup_values dic ON dic.lookup_type='SYSTEM_CODE' AND dic.lookup_code=menu.system_code and dic.system_code='PUBLIC'  WHERE 1=1 AND menu.system_code <> 'PDA_MENU'";




    private Integer menuId;
    private String menuName;
    private String menuDesc;
    private String systemCode;
    private String systemName;
    private Integer roleId;
    private String htmlUrl;
    private String menuCode;


    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
}
