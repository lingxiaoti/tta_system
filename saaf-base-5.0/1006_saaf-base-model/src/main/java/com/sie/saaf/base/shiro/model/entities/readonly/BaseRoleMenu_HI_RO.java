package com.sie.saaf.base.shiro.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;


public class BaseRoleMenu_HI_RO {

	public static String QUERY_MENU_ROLE_SQL = "SELECT\n" +
			"\tbaseMenu.menu_id AS menuId ,\n" +
			"\tbaseMenu.menu_parent_id AS menuParentId ,\n" +
			"\tbaseMenu.menu_code AS menuCode ,\n" +
			"\tbaseMenu.order_no AS orderNo ,\n" +
			"\tbaseMenu.menu_name AS menuName ,\n" +
			"\tbaseMenu.menu_desc AS menuDesc ,\n" +
			"\tbaseMenu.level_id AS levelId ,\n" +
			"\tbaseMenu.menu_type AS menuType ,\n" +
			"\tbaseMenu.parameter AS parameter ,\n" +
			"\tbaseMenu.system_code AS systemCode ,\n" +
			"\tbaseMenu.image_link AS imageLink ,\n" +
			"\tbaseMenu.image_color AS imageColor ,\n" +
			"\tbaseMenu.html_url AS htmlUrl ,\n" +
			"\tbaseMenu.from_type AS formType ,\n" +
			"\tbaseMenu.enabled AS enabled ,\n" +
			"\tbaseMenu.start_date_active AS startDateActive ,\n" +
			"\tbaseMenu.end_date_active AS endDateActive ,\n" +
			"\tbaseMenu.version_num AS versionNum ,\n" +
			"\tbaseRole.role_id AS roleId ,\n" +
			"\tbaseRole.role_name AS roleName ,\n" +
			"\tbaseRole.role_code AS roleCode ,\n" +
			"\tbaseRole.role_desc AS roleDesc ,\n" +
			"\tbaseRole.start_date_active AS roleStartDateActive ,\n" +
			"\tbaseRole.end_date_active AS roleEndDateActive ,\n" +
			"\tbaseRole.version_num AS roleVersionNum\n" +
			"FROM\n" +
			"\tbase_menu  baseMenu ,\n" +
			"\tbase_role_menu  baseRoleMenu ,\n" +
			"\tbase_role  baseRole\n" +
			"WHERE\n" +
			"\tbaseMenu.menu_id = baseRoleMenu.menu_id\n" +
			"\tAND baseRole.role_id = baseRoleMenu.role_id\n";


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
	private String imageLink; //PC端图片样式
	private String imageColor; //PC端图标颜色
	private String htmlUrl; //PC端HTML路由链接
	private String fromType; //访问来源
	private String enabled; //是否启用
	@JSONField(format = "yyyy-MM-dd")
	private Date startDateActive; //启用时间
	@JSONField(format = "yyyy-MM-dd")
	private Date endDateActive; //失效时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建日期
	private Integer createdBy; //创建人
	private Integer lastUpdatedBy; //更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新日期
	private Integer versionNum; //版本号
	private Integer lastUpdateLogin; //last_update_login

	//角色
	private Integer roleId; //角色Id
	private String roleName; //角色名称
	private String roleCode; //角色编号
	private String roleDesc; //角色描述
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date roleStartDateActive; //角色生效时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date roleEndDateActive; //角色失效时间
	private Integer roleVersionNum;//角色版本号

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

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
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

	public Date getRoleStartDateActive() {
		return roleStartDateActive;
	}

	public void setRoleStartDateActive(Date roleStartDateActive) {
		this.roleStartDateActive = roleStartDateActive;
	}

	public Date getRoleEndDateActive() {
		return roleEndDateActive;
	}

	public void setRoleEndDateActive(Date roleEndDateActive) {
		this.roleEndDateActive = roleEndDateActive;
	}

	public Integer getRoleVersionNum() {
		return roleVersionNum;
	}

	public void setRoleVersionNum(Integer roleVersionNum) {
		this.roleVersionNum = roleVersionNum;
	}

}
