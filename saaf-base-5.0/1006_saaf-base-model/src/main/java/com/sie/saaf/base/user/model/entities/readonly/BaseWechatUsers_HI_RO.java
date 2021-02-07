package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 微信与用户表关联视图
 * @author ZhangJun
 * @createTime 2017-12-27 15:14
 * @description 微信与用户表关联视图
 */
public class BaseWechatUsers_HI_RO {

	public static final String QUERY_SQL = "SELECT\n" +
			"\tbaseWechatMng.wx_id AS wxId ,\n" +
			"\tbaseWechatMng.user_id AS userId ,\n" +
			"\tbaseWechatMng.wx_open_id AS wxOpenId ,\n" +
			"\tbaseWechatMng.union_id AS unionId ,\n" +
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
			"\tbaseUsers.start_date AS startDate ,\n" +
			"\tbaseUsers.end_date AS endDate ,\n" +
			"\tbaseUsers.order_no AS orderNo ,\n" +
			"\tbaseUsers.delete_flag AS deleteFlag ,\n" +
			"\tbaseUsers.internal_user AS internalUser ,\n" +
			"\tbaseUsers.encrypted_password AS encryptedPassword\n" +
			"FROM\n" +
			"\tbase_wechat_mng  baseWechatMng ,\n" +
			"\tbase_users baseUsers\n" +
			"WHERE\n" +
			"\tbaseWechatMng.user_id = baseUsers.user_id\n\t" +
			"\tAND baseWechatMng.wx_open_id=:wxOpenId";

	private Integer wxId; //主键
	private Integer userId; //用户Id
	private String wxOpenId; //微信公众号OpenId
	private String unionId;

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

	public Integer getWxId() {
		return wxId;
	}

	public void setWxId(Integer wxId) {
		this.wxId = wxId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
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
}
