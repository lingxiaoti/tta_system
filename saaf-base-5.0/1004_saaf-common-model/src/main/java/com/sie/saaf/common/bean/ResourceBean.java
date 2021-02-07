package com.sie.saaf.common.bean;

public class ResourceBean implements Comparable<ResourceBean> {
    private Integer resourceId; //资源标识
    private Integer menuId; //菜单Id，节点标识 对应到功能
    private String resourceCode; //资源编号
    private String buttonUrl; //按钮对应的执行方法地址
    private Integer orderNo; //排序号
    private String resourceType; //类型标识(按钮、方法、字段名、代码片段)
    private String resourceName; //资源名称
    private String resourceDesc; //资源描述
    private String resIcon; // 资源图标

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public String getResIcon() {
        return resIcon;
    }

    public void setResIcon(String resIcon) {
        this.resIcon = resIcon;
    }


    @Override
    public int compareTo(ResourceBean o) {
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
