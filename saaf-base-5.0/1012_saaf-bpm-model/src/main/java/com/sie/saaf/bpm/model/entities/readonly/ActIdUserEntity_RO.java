package com.sie.saaf.bpm.model.entities.readonly;


import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import java.util.List;

/**
 * @author laoqunzhao
 * @createTime 2018-05-07
 * @description 对Activiti UserEntity扩展增加属性与系统用户对接
 */
public class ActIdUserEntity_RO extends UserEntity{

	private static final long serialVersionUID = 1L;
	private Object userId;//系统用户ID
	private String userName;//用户名
	private String userFullName;//姓名
	private String telPhone; // 电话号码
	private String mobilePhone; // 手机号码
	private String email; // 邮箱地址
	private Object orgId; //组织ID
	private String orgName;//组织名称
	private String orgCode;//组织代码
	private Object leaderId;//领导主键
	private String employeeNumber; //员工号
	private String cardNo; //身份证号
	private String wechat;
	private String token;
	private String personId; //员工ID
	private String personName;//员工姓名
	private Integer departmentId;//部门Id
	private String departmentCode; //部门编码
	private String departmentName; //部门名称
	private Integer jobId; //职务ID
	private String jobCode; //职务编码
	private String jobName; //职务名称
	private Integer positionId; //职位ID
	private String positionName; //职位名称
	private String userType;//用户类型 50.导购
	private List<BaseDepartmentEntity_HI_RO> departments;//部门层级
	
	public Object getUserId() {
		return userId;
	}
	
	public void setUserId(Object userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserFullName() {
		return userFullName;
	}
	
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	
	public String getTelPhone() {
		return telPhone;
	}
	
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Object getOrgId() {
		return orgId;
	}

	public void setOrgId(Object orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Object getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Object leaderId) {
		this.leaderId = leaderId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public List<BaseDepartmentEntity_HI_RO> getDepartments() {
		return departments;
	}

	public void setDepartments(List<BaseDepartmentEntity_HI_RO> departments) {
		this.departments = departments;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
