package com.sie.saaf.common.bean;

public class MenuInfoBean implements Comparable<MenuInfoBean> {
    private Integer menuId; //菜单Id
    private Integer menuParentId; //上级菜单Id
    private String menuCode; //菜单编码
    private Integer orderNo; //排序号
    private String menuName; //菜单名称
    private String menuDesc; //菜单提示信息
    private Integer levelId; //层级ID
    private String menuType; //菜单类型：10-菜单节点；20-功能节点
    private String parameter; //参数
    private String systemCode; //系统编码
    private String imageLink; //图片样式
    private String imageColor; //图标颜色
    private String htmlUrl; //HTML路由链接
    private String fromType; //访问来源


    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(Integer menuParentId) {
        this.menuParentId = menuParentId;
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

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageColor() {
        return imageColor;
    }

    public void setImageColor(String imageColor) {
        this.imageColor = imageColor;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }


    @Override
    public int compareTo(MenuInfoBean o) {
        if (o == null)
            return 1;
        else if (o.getOrderNo() == null && this.getOrderNo() == null)
            return 0;
        else if (this.getOrderNo()==null)
            return -1;
        else if (o.getOrderNo()==null)
            return 1;
        else
            return this.getOrderNo().compareTo(o.getOrderNo());
    }
}
