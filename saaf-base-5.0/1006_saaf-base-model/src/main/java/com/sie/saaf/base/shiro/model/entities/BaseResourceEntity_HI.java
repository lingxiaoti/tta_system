package com.sie.saaf.base.shiro.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseResourceEntity_HI Entity Object
 * Tue Dec 12 19:08:04 CST 2017  Auto Generate
 */
@Entity
@Table(name = "base_resource")
public class BaseResourceEntity_HI {
    private Integer resourceId; //资源标识
    private Integer menuId; //菜单Id，节点标识 对应到功能
    private String resourceCode; //资源编号
    private String buttonUrl; //按钮对应的执行方法地址
    private Integer orderNo; //排序号
    private String resourceType; //类型标识(按钮、方法、字段名、代码片段)
    private String resourceName; //资源名称
    private String resourceDesc; //资源描述
	private String resIcon; // 资源图标
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer lastUpdateLogin; //last_update_login
    private Integer operatorUserId;

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_RESOURCE", sequenceName = "SEQ_BASE_RESOURCE", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_RESOURCE", strategy = GenerationType.SEQUENCE)
	@Column(name = "resource_id", nullable = false, length = 11)	
	public Integer getResourceId() {
		return resourceId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "menu_id", nullable = true, length = 11)	
	public Integer getMenuId() {
		return menuId;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	@Column(name = "resource_code", nullable = true, length = 200)	
	public String getResourceCode() {
		return resourceCode;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	@Column(name = "button_url", nullable = true, length = 200)	
	public String getButtonUrl() {
		return buttonUrl;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "order_no", nullable = true, length = 11)	
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	@Column(name = "resource_type", nullable = true, length = 30)	
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "resource_name", nullable = true, length = 100)	
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	@Column(name = "resource_desc", nullable = true, length = 250)	
	public String getResourceDesc() {
		return resourceDesc;
	}

	@Column(name = "res_icon", nullable = true, length = 50)
	public String getResIcon() {
		return resIcon;
	}

	public void setResIcon(String resIcon) {
		this.resIcon = resIcon;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 11)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
