package com.sie.saaf.base.user.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * BaseUsersEntity_HI Entity Object Wed Dec 06 11:00:12 CST 2017 Auto Generate
 */
@Entity
@Table(name = "base_users")
public class BaseUsersEntity_HI {
	private Integer userId; // 用户Id
	private String phoneNumber; // 电话号码
	private String namePingyin; // 用户姓名（拼音）
	private String nameSimplePinyin; // 用户姓名（拼音首字母）
	private Integer personId; // 对应经销商、门店、员工的外围系统ID
	private String sourceId; // 关联人员ID、关联经销商ID、关联门店编码
	private String sourceCode; // 用户来源
	private String isadmin; // 是否是系统管理员
	private String userName; // 用户名/登录帐号
	private String userDesc; // 用户描述
	private String userType; // 用户类型：IN:内部员工，OUT：经销商、门店、导购
	private String userFullName; // 姓名
	private String userHeadImgUrl;// 用户头像地址
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
	private String password;// 密码，明文，不在数据库保存
	private Date pwdUpdateDate;
	private String dataType;// 数据类型
	private String groupCode;
	private String groupName;
	private String emailAddress;
	// 2019/12/4
	private String deptType; // 部门类型

	// private Integer supplierId; // 供应商ID
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_BASE_USERS", sequenceName = "SEQ_BASE_USERS", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_USERS", strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id", nullable = false, length = 11)
	public Integer getUserId() {
		return userId;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "phone_number", nullable = true, length = 30)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setNamePingyin(String namePingyin) {
		this.namePingyin = namePingyin;
	}

	@Column(name = "name_pingyin", nullable = true, length = 100)
	public String getNamePingyin() {
		return namePingyin;
	}

	public void setNameSimplePinyin(String nameSimplePinyin) {
		this.nameSimplePinyin = nameSimplePinyin;
	}

	@Column(name = "name_simple_pinyin", nullable = true, length = 50)
	public String getNameSimplePinyin() {
		return nameSimplePinyin;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	@Column(name = "person_id", nullable = true, length = 11)
	public Integer getPersonId() {
		return personId;
	}

	@Column(name = "source_code", nullable = true, length = 50)
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "source_id", nullable = true, length = 36)
	public String getSourceId() {
		return sourceId;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	@Column(name = "isadmin", nullable = true, length = 20)
	public String getIsadmin() {
		return isadmin;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_name", nullable = true, length = 100)
	public String getUserName() {
		return userName;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	@Column(name = "user_desc", nullable = true, length = 256)
	public String getUserDesc() {
		return userDesc;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	// @Column(name = "supplier_Id", nullable = true, length = 256)
	// public Integer getSupplierId() {
	// return supplierId;
	// }

	// public void setSupplierId(Integer supplierId) {
	// this.supplierId = supplierId;
	// }

	@Column(name = "user_type", nullable = true, length = 10)
	public String getUserType() {
		return userType;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	@Column(name = "user_full_name", nullable = true, length = 100)
	public String getUserFullName() {
		return userFullName;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	@Column(name = "encrypted_password", nullable = true, length = 100)
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setInternalUser(String internalUser) {
		this.internalUser = internalUser;
	}

	@Column(name = "internal_user", nullable = true, length = 11)
	public String getInternalUser() {
		return internalUser;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "delete_flag", nullable = true, length = 11)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "start_date", nullable = true, length = 0)
	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "end_date", nullable = true, length = 0)
	public Date getEndDate() {
		return endDate;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "order_no", nullable = true, length = 11)
	public Integer getOrderNo() {
		return orderNo;
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
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Transient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "user_head_img_url", nullable = true, length = 255)
	public String getUserHeadImgUrl() {
		return userHeadImgUrl;
	}

	public void setUserHeadImgUrl(String userHeadImgUrl) {
		this.userHeadImgUrl = userHeadImgUrl;
	}

	@Column(name = "pwd_update_date", nullable = true, length = 0)
	public Date getPwdUpdateDate() {
		return pwdUpdateDate;
	}

	public void setPwdUpdateDate(Date pwdUpdateDate) {
		this.pwdUpdateDate = pwdUpdateDate;
	}

	@Column(name = "data_type", nullable = true, length = 2)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "group_code", nullable = false, length = 50)
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name = "group_name", nullable = false, length = 230)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "email_address", nullable = false, length = 255)
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "dept_type", nullable = false, length = 255)
	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

}
