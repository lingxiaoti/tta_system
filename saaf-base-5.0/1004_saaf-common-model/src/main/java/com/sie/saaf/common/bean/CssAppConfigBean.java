package com.sie.saaf.common.bean;

/**
 * @auther: huqitao 2018/11/6
 */
public class CssAppConfigBean {
    private Integer configId; //主键ID
    private Integer orgId; //事业部ID
    private Integer menuId; //菜单功能ID
    private String menuCode;
    private String menuName;
    private String isCheckNow; //是否实时校验（Y：实时校验，N：非实时校验）
    private String isNotOwnInvOut; //是否允许非自有子库出库（Y：允许，N：不允许）

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getIsCheckNow() {
        return isCheckNow;
    }

    public void setIsCheckNow(String isCheckNow) {
        this.isCheckNow = isCheckNow;
    }

    public String getIsNotOwnInvOut() {
        return isNotOwnInvOut;
    }

    public void setIsNotOwnInvOut(String isNotOwnInvOut) {
        this.isNotOwnInvOut = isNotOwnInvOut;
    }
}
