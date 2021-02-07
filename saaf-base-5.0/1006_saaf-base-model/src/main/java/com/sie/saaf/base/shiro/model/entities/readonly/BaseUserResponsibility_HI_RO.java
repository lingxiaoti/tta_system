package com.sie.saaf.base.shiro.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author ZhangJun
 * @creteTime 2017-12-13
 */
public class BaseUserResponsibility_HI_RO {
	public static final String QUERY_RESPONSIBILITY_BY_USERID_SQL = "SELECT\n" +
			"\tbaseUserResponsibility.resp_user_id  respUserId ,\n" +
			"\tbaseResponsibility.responsibility_id  responsibilityId ,\n" +
			"\tbaseResponsibility.responsibility_code  responsibilityCode ,\n" +
			"\tbaseResponsibility.responsibility_name  responsibilityName ,\n" +
			"\tbaseResponsibility.responsibility_desc  responsibilityDesc ,\n" +
			"\tbaseResponsibility.system_code  systemCode ,\n" +
			"\tbaseResponsibility.start_date_active  startDateActive ,\n" +
			"\tbaseResponsibility.end_date_active  endDateActive ,\n" +
			"\tbaseResponsibility.version_num  versionNum ,\n" +
			"\tbaseResponsibility.creation_date  creationDate ,\n" +
			"\tbaseResponsibility.created_by  createdBy ,\n" +
			"\tbaseResponsibility.last_updated_by  lastUpdatedBy ,\n" +
			"\tbaseResponsibility.last_update_date  lastUpdateDate\n" +
			"FROM\n" +
			"\tbase_responsibility  baseResponsibility ,\n" +
			"\tbase_user_responsibility  baseUserResponsibility ,\n" +
			"\tbase_users  baseUsers\n" +
			"WHERE\n" +
			"\tbaseResponsibility.responsibility_id = baseUserResponsibility.responsibility_id\n" +
			"AND baseUserResponsibility.user_id = baseUsers.user_id\n\t";

	public static final String QUERY_USER_BY_RESPONSIBILITYID_SQL = "SELECT\n" +
			"\tbaseUserResponsibility.resp_user_id AS respUserId ,\n" +
			"\tbaseUsers.user_id AS userId ,\n" +
			"\tbaseUsers.phone_number AS phoneNumber ,\n" +
			"\tbaseUsers.name_pingyin AS namePingyin ,\n" +
			"\tbaseUsers.name_simple_pinyin AS nameSimplePinyin ,\n" +
			"\tbaseUsers.person_id AS personId ,\n" +
			"\tbaseUsers.source_id AS sourceId ,\n" +
			"\tbaseUsers.isadmin ,\n" +
			"\tbaseUsers.user_name AS userName ,\n" +
			"\tbaseUsers.user_desc AS userDesc ,\n" +
			"\tbaseUsers.user_type AS userType ,\n" +
			"\tbaseUsers.user_full_name AS userFullName ,\n" +
			"\tbaseUsers.encrypted_password AS encryptedPassword ,\n" +
			"\tbaseUsers.internal_user AS internalUser ,\n" +
			"\tbaseUsers.delete_flag AS deleteFlag ,\n" +
			"\tbaseUsers.start_date AS startDate ,\n" +
			"\tbaseUsers.end_date AS endDate ,\n" +
			"\tbaseUsers.order_no AS orderNo ,\n" +
			"\tbaseUsers.creation_date AS creationDate ,\n" +
			"\tbaseUsers.created_by AS createdBy ,\n" +
			"\tbaseUsers.last_updated_by AS lastUpdatedBy ,\n" +
			"\tbaseUsers.last_update_date AS lastUpdateDate ,\n" +
			"\tbaseUsers.version_num AS versionNum\n" +
			"FROM\n" +
			"\tbase_responsibility  baseResponsibility ,\n" +
			"\tbase_user_responsibility  baseUserResponsibility ,\n" +
			"\tbase_users  baseUsers\n" +
			"WHERE\n" +
			"\tbaseResponsibility.responsibility_id = baseUserResponsibility.responsibility_id\n" +
			"AND baseUserResponsibility.user_id = baseUsers.user_id\n";

	//职责信息
	private Integer responsibilityId; //职责标识
	private String responsibilityCode; //职责编号
	private String responsibilityName; //职责名称
	private String responsibilityDesc; //职责描述
	private String systemCode; //职责的系统编码
	@JSONField(format = "yyyy-MM-dd")
	private Date startDateActive; //生效时间
	@JSONField(format = "yyyy-MM-dd")
	private Date endDateActive; //失效时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建日期
	private Integer createdBy; //创建人
	private Integer lastUpdatedBy; //更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新日期
	private Integer versionNum; //版本号

	//用户信息
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
	private String deleteFlag; // 是否删除（0：未删除；1：已删除）
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate; // 生效日期
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endDate; // 失效日期
	private Integer orderNo; // 排序号

	//用户与职责
	private Integer respUserId;

	public Integer getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityId(Integer responsibilityId) {
		this.responsibilityId = responsibilityId;
	}

	public String getResponsibilityCode() {
		return responsibilityCode;
	}

	public void setResponsibilityCode(String responsibilityCode) {
		this.responsibilityCode = responsibilityCode;
	}

	public String getResponsibilityName() {
		return responsibilityName;
	}

	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}

	public String getResponsibilityDesc() {
		return responsibilityDesc;
	}

	public void setResponsibilityDesc(String responsibilityDesc) {
		this.responsibilityDesc = responsibilityDesc;
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

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
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

	public Integer getRespUserId() {
		return respUserId;
	}

	public void setRespUserId(Integer respUserId) {
		this.respUserId = respUserId;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
}
