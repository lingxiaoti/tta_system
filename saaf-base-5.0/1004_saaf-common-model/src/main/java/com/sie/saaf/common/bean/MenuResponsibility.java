package com.sie.saaf.common.bean;

import java.util.List;

public class MenuResponsibility {

    /**
     *  菜单id
     */
    private  Integer menuId;

    /**
     * 菜单编码
     */
    private String menuCode;
    /**
     *  当前菜单下的所有职责
     */
    private List<ResponsibilityBean> responsibilityList;

    /**
     * 当前菜单下的操作职责
     */
    private ResponsibilityBean oprResponsibility;


    public MenuResponsibility() {
    }

    public MenuResponsibility(Integer menuId, List<ResponsibilityBean> responsibilityList, ResponsibilityBean oprResponsibility) {
        this.menuId = menuId;
        this.responsibilityList = responsibilityList;
        this.oprResponsibility = oprResponsibility;
    }

    public MenuResponsibility(Integer menuId, String menuCode, List<ResponsibilityBean> responsibilityList, ResponsibilityBean oprResponsibility) {
        this.menuId = menuId;
        this.menuCode = menuCode;
        this.responsibilityList = responsibilityList;
        this.oprResponsibility = oprResponsibility;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public List<ResponsibilityBean> getResponsibilityList() {
        return responsibilityList;
    }

    public void setResponsibilityList(List<ResponsibilityBean> responsibilityList) {
        this.responsibilityList = responsibilityList;
    }

    public ResponsibilityBean getOprResponsibility() {
        return oprResponsibility;
    }

    public void setOprResponsibility(ResponsibilityBean oprResponsibility) {
        this.oprResponsibility = oprResponsibility;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
}
