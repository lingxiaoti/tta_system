package com.sie.saaf.common.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Administrator on 2017/12/13.
 */
public class UserSessionBean {
	private Integer userId; // 用户Id
	private Integer personId; // 人员id
	private String email;
	private String employeeNumber; // 员工编号
	private String phoneNumber; // 用户电话号码
	private String userName; // 用户名
	private String name; // name_pinyin
	private String namePingyin; // 用户名拼音
	private String nameEnglish;// 英文名
	private String nameChinese;
	private String storeCode; // 库存编码
	private String storeName; // 库存名
	private Integer orgId; // 组织Id
	private String orgName; // 组织名
	private Integer supplierId; // 组织Id
	private String subinvType; // 字库类型
	private String orgCode; // 组织编码
	private Integer leaderId; // 部门领导Id
	private String systemCode; // 系统编码
	private String userFullName; // 姓名
	private String certificate;// cookie信息
	private JSONArray roles; // 用户角色[{roleId:'',roleName:''}]
	private JSONArray ssoSystems;// 能访问的系统
	private String isadmin; // Y：超级管理员
	private String userHeadImgUrl; // 用户头像路径
	private List<ResponsibilityBean> userRespList = new ArrayList<>();// 职责
	private List<MenuResponsibility> menuResponsibilityList = new ArrayList<>();// 对应菜单下职责
	private List<PositionBean> positionList = new ArrayList<>();// 职位
	private Integer operationRespId;// 当前操作的职责ID
	private List<Integer> operationOrgIds = new ArrayList<>();// 当前操作OU ID
	private String domain;// 当前访问域名
	private boolean fromApp = false;// 是否app登录
	private CustomerInfoBean customerInfo;
	private List<CssAppConfigBean> configBeanList;
	private Integer serviceStoreId;
	private String serviceStoreNumber;
	private String serviceStoreName;
	private String userType;
	private Integer deptId;// 部门id
	private String deptCode;// 部门编号
	private String deptName;// 部门编码
	private Integer loadResource;// 系统来源
	private List<String> vendorCodes;// 供应商profile
	private List<String> vendorGroupCodes;

	private String groupCode;
	private String groupName;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginDate;// 最后登录日期

	private String dataType;

	public String getNamePingyin() {
		return namePingyin;
	}

	public void setNamePingyin(String namePingyin) {
		this.namePingyin = namePingyin;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSubinvType() {
		return subinvType;
	}

	public void setSubinvType(String subinvType) {
		this.subinvType = subinvType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public JSONArray getRoles() {
		return roles;
	}

	public void setRoles(JSONArray roles) {
		this.roles = roles;
	}

	public JSONArray getSsoSystems() {
		return ssoSystems;
	}

	public void setSsoSystems(JSONArray ssoSystems) {
		this.ssoSystems = ssoSystems;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public String getUserHeadImgUrl() {
		return userHeadImgUrl;
	}

	public void setUserHeadImgUrl(String userHeadImgUrl) {
		this.userHeadImgUrl = userHeadImgUrl;
	}

	public List<ResponsibilityBean> getUserRespList() {
		return userRespList;
	}

	public void setUserRespList(List<ResponsibilityBean> userRespList) {
		this.userRespList = userRespList;
	}

	public Integer getOperationRespId() {
		return operationRespId;
	}

	public void setOperationRespId(Integer operationRespId) {
		this.operationRespId = operationRespId;
	}

	public List<Integer> getOperationOrgIds() {
		return operationOrgIds;
	}

	public void setOperationOrgIds(List<Integer> operationOrgIds) {
		this.operationOrgIds = operationOrgIds;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PositionBean> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<PositionBean> positionList) {
		this.positionList = positionList;
	}

	public List<MenuResponsibility> getMenuResponsibilityList() {
		return menuResponsibilityList;
	}

	public void setMenuResponsibilityList(
			List<MenuResponsibility> menuResponsibilityList) {
		this.menuResponsibilityList = menuResponsibilityList;
	}

	public boolean isFromApp() {
		return fromApp;
	}

	public void setFromApp(boolean fromApp) {
		this.fromApp = fromApp;
	}

	public CustomerInfoBean getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfoBean customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<CssAppConfigBean> getConfigBeanList() {
		return configBeanList;
	}

	public void setConfigBeanList(List<CssAppConfigBean> configBeanList) {
		this.configBeanList = configBeanList;
	}

	public Integer getServiceStoreId() {
		return serviceStoreId;
	}

	public void setServiceStoreId(Integer serviceStoreId) {
		this.serviceStoreId = serviceStoreId;
	}

	public String getServiceStoreName() {
		return serviceStoreName;
	}

	public void setServiceStoreName(String serviceStoreName) {
		this.serviceStoreName = serviceStoreName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getServiceStoreNumber() {
		return serviceStoreNumber;
	}

	public void setServiceStoreNumber(String serviceStoreNumber) {
		this.serviceStoreNumber = serviceStoreNumber;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getLoadResource() {
		return loadResource;
	}

	public void setLoadResource(Integer loadResource) {
		this.loadResource = loadResource;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public List<String> getVendorCodes() {
		return vendorCodes;
	}

	public void setVendorCodes(List<String> vendorCodes) {
		this.vendorCodes = vendorCodes;
	}

	public List<String> getVendorGroupCodes() {
		return vendorGroupCodes;
	}

	public void setVendorGroupCodes(List<String> vendorGroupCodes) {
		this.vendorGroupCodes = vendorGroupCodes;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	public String getNameChinese() {
		return nameChinese;
	}

	public void setNameChinese(String nameChinese) {
		this.nameChinese = nameChinese;
	}
}
