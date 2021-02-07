package com.sie.saaf.base.shiro.model.entities.readonly;

/**
 * @author ZhangJun
 * @createTime 2018-01-05 10:33
 * @description
 */
public class BaseMenuResource_HI_RO {
	public static final String QUERY_SQL = "select distinct \n" +
			"    to_char(menuButton.menuId) as menuId, \n" +
			"    to_char(menuButton.menuParentId) as menuParentId,\n" +
			"    menuButton.menuCode,\n" +
			"    menuButton.menuName,\n" +
			"    menuButton.menuDesc,\n" +
			"    menuButton.menuType,\n" +
			"    menuButton.systemCode\n" +
			"from (\n" +
			"select \n" +
			"    baseMenu.menu_id as menuId,\n" +
			"    baseMenu.menu_parent_id as menuParentId,\n" +
			"    baseMenu.menu_code as menuCode,\n" +
			"    baseMenu.menu_name as menuName,\n" +
			"    baseMenu.menu_desc as menuDesc,\n" +
			"    baseMenu.menu_type as menuType,\n" +
			"    baseMenu.system_code as systemCode,\n" +
			"    baseRoleMenu.role_id as roleId\n" +
			"from base_menu baseMenu \n" +
			"left join base_role_menu baseRoleMenu\n" +
			"on baseMenu.menu_id=baseRoleMenu.menu_id \n" +
			"where baseMenu.start_date_active < SYSDATE \n" +
			"    and baseMenu.enabled = 'Y'\n" +
			"    and (baseMenu.end_date_active >= SYSDATE or baseMenu.end_date_active is null)\n" +
			"union ALL\n" +
			"select\n" +
			"    baseResource.resource_id as menuId,\n" +
			"    baseResource.menu_id as menuParentId,\n" +
			"    baseResource.resource_code as menu_code,\n" +
			"    baseResource.resource_name as menuName,\n" +
			"    baseResource.resource_desc as menuDesc,\n" +
			"    '30' as menuType,\n" +
			"    baseMenu.system_code as systemCode,\n" +
			"    baseRoleResource.role_id as roleId\n" +
			"from base_resource baseResource \n" +
			"left join base_role_resource baseRoleResource\n" +
			"on baseResource.resource_id=baseRoleResource.resource_id \n" +
			"left join base_menu baseMenu\n" +
			"on baseMenu.menu_id=baseResource.menu_id\n" +
			"where baseMenu.start_date_active < sysdate \n" +
			"    and baseMenu.enabled = 'Y'\n" +
			"    and (baseMenu.end_date_active >= sysdate or baseMenu.end_date_active is null)\n" +
			") menuButton where 1=1 ";
	public static final String QUERY_RESOURCE_MENU_SQL = "select \n" +
			"    baseResource.resource_id as resourceId, \n" +
			"    to_char(baseResource.menu_id) as menuId,\n" +
			"    baseResource.resource_code as resourceCode,\n" +
			"    baseResource.button_url as buttonUrl,\n" +
			"    baseResource.order_no as orderNo,\n" +
			"    baseResource.resource_type as resourceType,\n" +
			"    baseResource.resource_name as resourceName,\n" +
			"    baseResource.resource_desc as resourceDesc,\n" +
			"	 baseResource.res_icon as resIcon,\n"+
			"    baseResource.version_num as versionNum,\n" +
			"    baseMenu.menu_name as menuName,\n" +
			"    baseMenu.system_code as systemCode,\n" +
			"    lookupValues.meaning as resourceTypeName\n" +
			"from base_resource baseResource \n" +
			"left join base_menu baseMenu on baseResource.menu_id=baseMenu.menu_id\n" +
			"left join (select LOOKUP_CODE,MEANING from base_lookup_values where LOOKUP_TYPE='RESOURCE_TYPE' and enabled_flag='Y' and start_date_active<sysdate and (end_date_active is null or end_date_active >= sysdate) and delete_flag=0) lookupValues\n" +
			"on lookupValues.lookup_code=baseResource.resource_Type\n" +
			"where 1=1 ";

	/*菜单*/
	private String menuId;//Id
	private String menuParentId;//上级Id
	private String menuCode;//编码
	private String menuName;//名称
	private String menuDesc;//描述
	private String menuType;//类型
	private String systemCode;//系统编码
	private String roleId;//角色Id

	/*资源*/
	private Integer resourceId;//资源Id
	private String resourceCode;//资源编码
	private String buttonUrl;//按钮对应的执行方法地址
	private String resourceType;//资源类型
	private String resourceName;//资源名称
	private String resourceDesc;//资源描述
	private String resIcon;//图标
	private String resourceTypeName;//资源类型名称
	private Integer orderNo;//排序号
	private Integer versionNum;//版本号

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(String menuParentId) {
		this.menuParentId = menuParentId;
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

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
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

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public String getResIcon() {
		return resIcon;
	}

	public void setResIcon(String resIcon) {
		this.resIcon = resIcon;
	}
}
