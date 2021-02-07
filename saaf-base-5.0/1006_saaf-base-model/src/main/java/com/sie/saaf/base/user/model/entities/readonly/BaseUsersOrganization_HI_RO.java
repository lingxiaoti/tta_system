package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author ZhangJun
 * @createTime 2017-12-25 10:30
 * @description
 */
public class BaseUsersOrganization_HI_RO {

	public static final String QUERY_USER_SQL = "SELECT\r\n" +
			"	baseUsers.user_id AS userId ,\r\n" +
			"	baseUsers.phone_number as phoneNumber,\r\n" +
			"	baseUsers.name_pingyin as namePingyin,\r\n" +
			"	baseUsers.name_simple_pinyin as nameSimplePinyin,\r\n" +
			"	baseUsers.person_id as personId,\r\n" +
			"	baseUsers.order_no as orderNo ,\r\n" +
			"	baseUsers.end_date as endDate,\r\n" +
			"	baseUsers.start_date as startDate,\r\n" +
			"	baseUsers.delete_flag as deleteFlag,\r\n" +
			"	baseUsers.internal_user as internalUser,\r\n" +
			"	baseUsers.encrypted_password as encryptedPassword,\r\n" +
			"	baseUsers.user_full_name as userFullName,\r\n" +
			"	baseUsers.user_type as userType,\r\n" +
			"	baseUsers.user_desc as userDesc,\r\n" +
			"	baseUsers.user_name as userName ,\r\n" +
			"	baseUsers.isadmin as isadmin,\r\n" +
			"	baseUsers.source_id as sourceId,\r\n" +
			"   basePersonOrganization.position_id as positionId,\r\n" +
			"   basePersonOrganization.org_id as orgId\r\n" +
			" FROM base_users  baseUsers,\r\n" +
			"   base_person_organization  basePersonOrganization \r\n" +
			" WHERE baseUsers.person_id=basePersonOrganization.person_id \r\n" +
			" AND basePersonOrganization.org_id=:orgId\r\n";


	private Integer userId; // 用户Id
	private String phoneNumber; // 电话号码
	private String namePingyin; // 用户姓名（拼音）
	private String nameSimplePinyin; // 用户姓名（拼音首字母）
	private String personId; // 对应经销商、门店、员工的外围系统ID
	private String sourceId; // 关联人员ID、关联经销商ID、关联门店编码
	private String isadmin; // 是否是系统管理员
	private String userName; // 用户名/登录帐号
	private String userDesc; // 用户描述
	private String userType; // 用户类型：IN:内部员工，OUT：经销商、门店、导购
	private String userFullName; // 姓名
	private String encryptedPassword; // 加密后密码（MD5）
	private String internalUser; // 是否是EBS用户，如果是，需要将用户、密码回写EBS系统
	private Integer deleteFlag; // 是否删除（0：未删除；1：已删除）
	@JSONField(format = "yyyy-MM-dd")
	private Date startDate; // 生效日期
	@JSONField(format = "yyyy-MM-dd")
	private Date endDate; // 失效日期
	private Integer orderNo; // 排序号
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; // 创建日期
	private Integer createdBy; // 创建人
	private Integer lastUpdatedBy; // 更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; // 更新日期
	private Integer versionNum; // 版本号
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String password;//密码，明文，不在数据库保存

	private Integer positionId;//职位Id
	private Integer orgId;//组织Id

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNamePingyin() {
		return namePingyin;
	}

	public void setNamePingyin(String namePingyin) {
		this.namePingyin = namePingyin;
	}

	public String getNameSimplePinyin() {
		return nameSimplePinyin;
	}

	public void setNameSimplePinyin(String nameSimplePinyin) {
		this.nameSimplePinyin = nameSimplePinyin;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getInternalUser() {
		return internalUser;
	}

	public void setInternalUser(String internalUser) {
		this.internalUser = internalUser;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
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

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
}
