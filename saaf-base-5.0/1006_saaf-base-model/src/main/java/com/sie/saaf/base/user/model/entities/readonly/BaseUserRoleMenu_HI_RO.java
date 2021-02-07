package com.sie.saaf.base.user.model.entities.readonly;

/**
 * Created by Administrator on 2019/11/29/029.
 */
public class BaseUserRoleMenu_HI_RO {
//    public static final String SQL_USER_ROLE_MENU ="SELECT br.RESPONSIBILITY_NAME responsibilityName,r.role_name roleName \n" +
//            ",bm.menuName1,bm.menuName2,bm.menuName3, \n" +
//            "(SELECT \n" +
//            "listagg(res.RESOURCE_NAME,',')  \n" +
//            "within group(order by res.menu_id) list \n" +
//            " FROM BASE_RESOURCE res  \n" +
//            "where  res.menu_id = bm.menuId3 or res.menu_id = bm.menuId2) functionNames \n" +
//            "from BASE_ROLE\tr \n" +
//            "left join BASE_RESPONSIBILITY_ROLE rr \n" +
//            "on rr.role_id = r.role_id  \n" +
//            "left join BASE_RESPONSIBILITY br \n" +
//            "on BR.RESPONSIBILITY_ID = rr.RESPONSIBILITY_ID \n" +
//            "left join BASE_ROLE_MENU rm  \n" +
//            "on rm.role_id = r.role_id  \n" +
//            "LEFT JOIN ( \n" +
//            "select b1.menu_id menuId1,b2.menu_id menuId2,b3.menu_id menuId3, \n" +
//            "b1.menu_name menuName1,b2.menu_name menuName2,b3.menu_name menuName3 \n" +
//            " from BASE_MENU b1 \n" +
//            "left join BASE_MENU b2 on b1.menu_id = b2.menu_parent_id  \n" +
//            "left join BASE_MENU b3 on b2.menu_id = b3.menu_parent_id  \n" +
//            "where b1.level_id = 1  \n" +
//            "and b2.level_id = 2  \n" +
//            "and (b3.level_id = 3 or b3.level_id is null ) ) bm   \n" +
//            "on bm.menuId1 =  rm.MENU_ID  \n" +
//            "where bm.menuName1 is not null  \n" +
//            " \n" +
//            "";

    public static final String SQL_USER_ROLE_MENU = "SELECT\n" +
      "\tT1.RESPONSIBILITY_NAME responsibilityName,\n" +
      "\tT2.ROLE_ID,\n" +
      "\tT3.ROLE_NAME roleName,\n" +
      "\tT4.MENUNAME1,\n" +
      "\tT4.MENUNAME2,\n" +
      "\tT4.MENUNAME3,\n" +
      "  (\n" +
      "    SELECT\n" +
      "\t\t\tLISTAGG (B1.RESOURCE_NAME, ',') WITHIN GROUP (ORDER BY B1.MENU_ID) LIST\n" +
      "\t\tFROM\n" +
      "\t\t\tBASE_RESOURCE B1\n" +
      "\t\tJOIN BASE_ROLE_RESOURCE B2 ON B1.RESOURCE_ID = B2.RESOURCE_ID\n" +
      "\t\tWHERE (B1.MENU_ID = T4.MENUID3 OR B1.MENU_ID = T4.MENUID2) \n" +
      "    AND T3.ROLE_ID = B2.ROLE_ID\n" +
      "  ) FUNCTIONNAMES\n" +
      "FROM\n" +
      "\tBASE_RESPONSIBILITY T1\n" +
      "LEFT JOIN BASE_RESPONSIBILITY_ROLE T2 ON T1.RESPONSIBILITY_ID = T2.RESPONSIBILITY_ID\n" +
      "LEFT JOIN BASE_ROLE T3 ON T2.ROLE_ID = T3.ROLE_ID\n" +
      "LEFT JOIN (\n" +
      "\tSELECT\n" +
      "\t\tB1.ROLE_ID,\n" +
      "\t\tB1.MENU_ID MENUID1,\n" +
      "\t\tB1.MENU_NAME MENUNAME1,\n" +
      "\t\tB2.MENU_ID MENUID2,\n" +
      "\t\tB2.MENU_NAME MENUNAME2,\n" +
      "\t\tB3.MENU_ID MENUID3,\n" +
      "\t\tB3.MENU_NAME MENUNAME3\n" +
      "\tFROM\n" +
      "\t\t(\n" +
      "\t\t\tSELECT\n" +
      "\t\t\t\tB2.ROLE_ID,\n" +
      "\t\t\t\tB1.MENU_ID,\n" +
      "\t\t\t\tB1.MENU_PARENT_ID,\n" +
      "\t\t\t\tB1.MENU_NAME\n" +
      "\t\t\tFROM\n" +
      "\t\t\t\tBASE_MENU B1\n" +
      "\t\t\tJOIN BASE_ROLE_MENU B2 ON B1.MENU_ID = B2.MENU_ID\n" +
      "\t\t\tWHERE\n" +
      "\t\t\t\t1 = 1\n" +
      "\t\t\tAND B1.LEVEL_ID = 1\n" +
      "\t\t\tAND B1.ENABLED = 'Y'\n" +
      "\t\t\tAND B1.START_DATE_ACTIVE < SYSDATE\n" +
      "\t\t\tAND (\n" +
      "\t\t\t\tB1.END_DATE_ACTIVE >= SYSDATE\n" +
      "\t\t\t\tOR B1.END_DATE_ACTIVE IS NULL\n" +
      "\t\t\t)\n" +
      "\t\t) B1\n" +
      "\tLEFT JOIN (\n" +
      "\t\tSELECT\n" +
      "\t\t\tB2.ROLE_ID,\n" +
      "\t\t\tB1.MENU_ID,\n" +
      "\t\t\tB1.MENU_PARENT_ID,\n" +
      "\t\t\tB1.MENU_NAME\n" +
      "\t\tFROM\n" +
      "\t\t\tBASE_MENU B1\n" +
      "\t\tJOIN BASE_ROLE_MENU B2 ON B1.MENU_ID = B2.MENU_ID\n" +
      "\t\tWHERE\n" +
      "\t\t\t1 = 1\n" +
      "\t\tAND B1.LEVEL_ID = 2\n" +
      "\t\tAND B1.ENABLED = 'Y'\n" +
      "\t\tAND B1.START_DATE_ACTIVE < SYSDATE\n" +
      "\t\tAND (\n" +
      "\t\t\tB1.END_DATE_ACTIVE >= SYSDATE\n" +
      "\t\t\tOR B1.END_DATE_ACTIVE IS NULL\n" +
      "\t\t)\n" +
      "\t) B2 ON B1.MENU_ID = B2.MENU_PARENT_ID AND B1.ROLE_ID = B2.ROLE_ID\n" +
      "\tLEFT JOIN (\n" +
      "\t\tSELECT\n" +
      "\t\t\tB2.ROLE_ID,\n" +
      "\t\t\tB1.MENU_ID,\n" +
      "\t\t\tB1.MENU_PARENT_ID,\n" +
      "\t\t\tB1.MENU_NAME\n" +
      "\t\tFROM\n" +
      "\t\t\tBASE_MENU B1\n" +
      "\t\tJOIN BASE_ROLE_MENU B2 ON B1.MENU_ID = B2.MENU_ID\n" +
      "\t\tWHERE\n" +
      "\t\t\t1 = 1\n" +
      "\t\tAND B1.LEVEL_ID = 3\n" +
      "\t\tAND B1.ENABLED = 'Y'\n" +
      "\t\tAND B1.START_DATE_ACTIVE < SYSDATE\n" +
      "\t\tAND (\n" +
      "\t\t\tB1.END_DATE_ACTIVE >= SYSDATE\n" +
      "\t\t\tOR B1.END_DATE_ACTIVE IS NULL\n" +
      "\t\t)\n" +
      "\t) B3 ON B2.MENU_ID = B3.MENU_PARENT_ID AND B2.ROLE_ID = B3.ROLE_ID\n" +
      ") T4 ON T3.ROLE_ID = T4.ROLE_ID\n" +
      "WHERE T4.MENUNAME1 IS NOT NULL \t";

    private String responsibilityName;
    private String roleName;
    private String menuName1;
    private String menuName2;
    private String menuName3;
    private String functionNames;

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

    public String getMenuName1() {
        return menuName1;
    }

    public void setMenuName1(String menuName1) {
        this.menuName1 = menuName1;
    }

    public String getMenuName2() {
        return menuName2;
    }

    public void setMenuName2(String menuName2) {
        this.menuName2 = menuName2;
    }

    public String getMenuName3() {
        return menuName3;
    }

    public void setMenuName3(String menuName3) {
        this.menuName3 = menuName3;
    }

    public String getFunctionNames() {
        return functionNames;
    }

    public void setFunctionNames(String functionNames) {
        this.functionNames = functionNames;
    }
}
