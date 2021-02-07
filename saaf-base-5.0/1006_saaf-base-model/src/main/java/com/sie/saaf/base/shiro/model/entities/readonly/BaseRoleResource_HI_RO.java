package com.sie.saaf.base.shiro.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 资源与角色视图
 * @author ZhangJun
 * @creteTime 2017-12-14
 */
public class BaseRoleResource_HI_RO {

	public static final String QUERY_RESOURCE_SQL = "SELECT " +
			"    baseResource.resource_id AS resourceId , " +
			"    max(baseResource.menu_id) AS menuId , " +
			"    max(baseResource.resource_code) AS resourceCode , " +
			"    max(baseResource.button_url) AS buttonUrl , " +
			"    max(baseResource.order_no) AS orderNo , " +
			"    max(baseResource.resource_type) AS resourceType , " +
			"    max(baseResource.resource_name) AS resourceName , " +
			"    max(baseResource.resource_desc) AS resourceDesc , " +
			"    max(baseResource.res_icon) AS resIcon , " +
			"    max(baseResource.creation_date) AS creationDate , " +
			"    max(baseResource.last_updated_by) AS lastUpdatedBy , " +
			"    max(baseResource.created_by) AS createdBy , " +
			"    max(baseResource.last_update_date) AS lastUpdateDate , " +
			"    max(baseResource.version_num) AS versionNum , " +
			"    max(baseRoleResource.resource_value) AS resourceValue  " +
			"FROM " +
			"    base_resource  baseResource , " +
			"    base_role_resource  baseRoleResource  " +
			"WHERE " +
			"    baseResource.resource_id = baseRoleResource.resource_id ";

	public static final String QUERY_ROLE_SQL = "SELECT " +
			"    baseRole.role_id AS roleId , " +
			"    baseRole.role_name AS roleName , " +
			"    baseRole.role_code AS roleCode , " +
			"    baseRole.role_desc AS roleDesc , " +
			"    baseRole.system_code AS systemCode , " +
			"    baseRole.start_date_active AS startDateActive , " +
			"    baseRole.end_date_active AS endDateActive , " +
			"    baseRole.creation_date AS creationDate , " +
			"    baseRole.created_by AS createdBy , " +
			"    baseRole.last_updated_by AS lastUpdatedBy , " +
			"    baseRole.last_update_date AS lastUpdateDate , " +
			"    baseRole.version_num AS versionNum " +
			"FROM " +
			"    base_role_resource  baseRoleResource , " +
			"    base_role  baseRole " +
			"WHERE " +
			"    baseRoleResource.role_id = baseRole.role_id     ";

	private Integer resourceId; //资源标识
	private Integer menuId; //菜单Id，节点标识 对应到功能
	private String resourceCode; //资源编号
	private String buttonUrl; //按钮对应的执行方法地址
	private Integer orderNo; //排序号
	private String resourceType; //类型标识(按钮、方法、字段名、代码片段)
	private String resourceName; //资源名称
	private String resourceDesc; //资源描述
	private String resIcon; //图标
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建日期
	private Integer createdBy; //创建人
	private Integer lastUpdatedBy; //更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新日期
	private Integer versionNum; //版本号
	private Integer lastUpdateLogin; //last_update_login

	private String resourceValue; //值

	private Integer roleId; //角色Id
	private String roleName; //角色名称
	private String roleCode; //角色编号
	private String roleDesc; //角色描述
	private String systemCode; //系统编码
	@JSONField(format = "yyyy-MM-dd")
	private Date startDateActive; //生效时间
	@JSONField(format = "yyyy-MM-dd")
	private Date endDateActive; //失效时间

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getResourceValue() {
		return resourceValue;
	}

	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	public String getResIcon() {
		return resIcon;
	}

	public void setResIcon(String resIcon) {
		this.resIcon = resIcon;
	}
}
