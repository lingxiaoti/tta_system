package com.sie.saaf.base.user.model.entities.readonly;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseProfileValue_HI_RO;

/**
 * 用户视图关联查询
 *
 * @author ZhangJun
 * @creteTime 2017-12-11
 */
public class BaseUsersPerson_HI_RO {

	/**
	 * 功能描述： 通过userId查询父级人员Id
	 *
	 * @author xiaoga
	 * @date 2019/7/20
	 * @param
	 * @return
	 */
	public static final String QUERY_PARENT_PERSON_SQL = "select "
			+ "       a.user_type,\n"
			+ "       a.user_name,\n"
			+ "       to_char(d.person_id) as person_id\n"
			+ // 上级父级
			"  from base_users a\n" + " inner join base_person b\n"
			+ "    on a.person_id = b.person_id\n"
			+ " inner join tta_report_relationship_in c\n"
			+ "    on c.employee_no = b.employee_number\n"
			+ "  left join base_person d\n"
			+ "    on d.employee_number = c.report_to\n"
			+ " where a.user_id =:userId ";

	/*
	 * 功能描述： 通过用户id查询需要BIC节点需要审批的人
	 *
	 * @author xiaoga
	 *
	 * @date 2019/8/14
	 *
	 * @param
	 *
	 * @return
	 */
	public static final String QUERY_BIC_USER = "select u.user_name\n"
			+ "  from base_users u\n" + " where u.delete_flag = '0'\n"
			+ "   and exists (select 1\n" + "          from base_users a\n"
			+ "         inner join BASE_LOOKUP_VALUES b\n"
			+ "            on a.group_code = b.lookup_code\n"
			+ "           and b.enabled_flag = 'Y'\n"
			+ "           AND b.delete_flag = '0'\n"
			+ "           and b.LOOKUP_TYPE = 'BIC_DEPT'\n"
			+ "           and b.meaning = u.user_name\n"
			+ "         where a.user_id =:userId) ";

	/**
	 * 功能描述： 通过personId 查询用户信息
	 *
	 * @author xiaoga
	 * @date 2019/7/20
	 * @param
	 * @return
	 */
	public static final String QUERY_USER_BY_PERSON_ID_SQL = "select "
			+ "	a.user_id, "
			+ "	a.user_name, "
			+ "	a.user_type\n"
			+ " from base_users a\n"
			+ " inner join base_person b\n"
			+ " on a.person_id = b.person_id where a.person_id=:personId and a.user_type=:userType ";

	public static final String QUERY_USER_BY_PROCCESS_START_USER_ID = "select  "
			+ "   t1.user_name,"
			+ "   t1.user_id, "
			+ "	t1.user_type from base_users t1\n"
			+ // --上级
			"inner join \n"
			+ "(\n"
			+ "select a.user_id, a.user_type, a.user_name, to_char(d.person_id) as person_id \n"
			+ // -- 上级父级
			"  from base_users a\n"
			+ " inner join base_person b\n"
			+ "    on a.person_id = b.person_id\n"
			+ " inner join tta_report_relationship_in c\n"
			+ "    on c.employee_no = b.employee_number\n"
			+ "  left join base_person d\n"
			+ "    on d.employee_number = c.report_to\n"
			+ " where a.user_id =:userId\n"
			+ " ) t2 on t1.person_id = t2.person_id";

	public static final String QUERY_USER_BY_PROCCESS_START_USER_ID2 = "select  "
			+ "   t1.user_name,"
			+ "   t1.user_id, "
			+ "	t1.user_type,t2.post_name from base_users t1\n"
			+ // --上级
			"inner join \n"
			+ "(\n"
			+ "select a.user_id, a.user_type, a.user_name, to_char(d.person_id) as person_id,d.post_name \n"
			+ // -- 上级父级
			"  from base_users a\n"
			+ " inner join base_person b\n"
			+ "    on a.person_id = b.person_id\n"
			+ " inner join tta_report_relationship_in c\n"
			+ "    on c.employee_no = b.employee_number\n"
			+ "  left join base_person d\n"
			+ "    on d.employee_number = c.report_to \n"
			+ " where a.user_id =:userId and (d.post_name not like 'General Manager%' and d.post_name not like 'Chief Executive Officer%' ) \n"
			+ " ) t2 on t1.person_id = t2.person_id";

	public static final String QUERY_JOIN_PERSON_SQL = "SELECT baseUsers.user_id            AS userId\n"
			+ "      ,baseUsers.group_code         AS groupCode\n"
			+ "      ,baseUsers.group_name         AS groupName\n"
			+ "      ,baseUsers.phone_number       AS phoneNumber\n"
			+ "      ,baseUsers.name_pingyin       AS namePingyin\n"
			+ "      ,baseUsers.name_simple_pinyin AS nameSimplePinyin\n"
			+ "      ,baseUsers.person_id          AS personId\n"
			+ "      ,baseUsers.order_no           AS orderNo\n"
			+ "      ,basePerson.Department_full_Name   AS departmentName\n"
			+ "      ,baseUsers.end_date           AS endDate\n"
			+ "      ,baseUsers.start_date         AS startDate\n"
			+ "      ,baseUsers.delete_flag        AS deleteFlag\n"
			+ "      ,baseUsers.internal_user      AS internalUser\n"
			+ "      ,baseUsers.user_full_name     AS userFullName\n"
			+ "      ,baseUsers.user_type          AS userType\n"
			+ "      ,baseUsers.user_head_img_url  AS userHeadImgUrl\n"
			+ "      ,baseUsers.version_num        AS versionNum\n"
			+ "      ,baseUsers.email_address      AS emailAddress\n"
			+ "      ,baseUsers.dept_type          AS deptType\n"
			+ "      ,baseUsers.data_type          AS dataType\n"
			+ "      ,lookup.meaning               AS userTypeDesc\n"
			+ "      ,baseUsers.user_desc          AS userDesc\n"
			+ "      ,baseUsers.user_name          AS userName\n"
			+ "      ,baseUsers.isadmin            AS isadmin\n"
			+ "      ,baseUsers.source_id          AS sourceId\n"
			+ "      ,basePerson.employee_number   AS employeeNumber\n"
			+ "      ,basePerson.person_name       AS personName\n"
			+ "      ,basePerson.person_type       AS personType\n"
			+ "      ,basePerson.sex               AS sex\n"
			+ "      ,basePerson.birth_day         AS birthDay\n"
			+ "      ,basePerson.card_no           AS cardNo\n"
			+ "      ,basePerson.enabled           AS enabled\n"
			+ "      ,basePerson.tel_phone         AS telPhone\n"
			+ "      ,basePerson.mobile_phone      AS mobilePhone\n"
			+ "      ,basePerson.email             AS email\n"
			+ "      ,basePerson.postal_address    AS postalAddress\n"
			+ "      ,basePerson.postcode          AS postcode\n"
			+ "      ,basePerson.person_name_en    AS personNameEn\n"
			+ " FROM   base_users baseUsers\n"
			+ "LEFT   JOIN (SELECT B.*\n"
			+ "                   ,bd.department_name\n"
			+ "                   ,bd.department_full_name\n"
			+ "             FROM   base_person     B\n"
			+ "                   ,Base_Department bd\n"
			+ "             WHERE  b.DEPT_NO = BD.DEPARTMENT_CODE) basePerson\n"
			+ "ON     baseUsers.person_id = basePerson.person_id\n"
			+ "LEFT   JOIN (SELECT lookup_type\n"
			+ "                   ,lookup_code\n"
			+ "                   ,meaning\n"
			+ "             FROM   base_lookup_values\n"
			+ "             WHERE  lookup_type = 'USER_TYPE'\n"
			+ "             AND    enabled_flag = 'Y'\n"
			+ "             AND    delete_flag = 0\n"
			+ "             AND    start_date_active < SYSDATE\n"
			+ "             AND    (end_date_active >= SYSDATE OR end_date_active IS NULL)\n"
			+ "             AND    system_code = 'BASE') lookup\n"
			+ "ON     lookup.lookup_code = baseUsers.user_Type\n"
			+ "WHERE  1 = 1 ";

	/*
	 * public static final String SQL_USER_SESSION = "SELECT\n" +
	 * "	bu.user_full_name AS userFullName,\n" + "	bu.user_id AS userId,\n" +
	 * "	bu.isadmin AS isadmin,\n" + // "	bu.supplier_id AS supplierId,\n" +
	 * "	bu.person_id AS personId,\n" +
	 * "	bp.employee_number AS employeeNumber,\n" +
	 * "	bp.mobile_phone AS mobilePhone,\n" + "	bu.user_name AS userName,\n" +
	 * "	bu.data_type AS dataType,\n" + "	bu.name_pingyin AS namePingyin,\n" +
	 * "	bu.user_head_img_url AS userHeadImgUrl,\n" +
	 * "	bu.group_code AS groupCode,\n" + "	bu.group_name AS groupName,\n" +
	 * "	bo.org_id AS orgId,\n" + "	bo.org_name AS orgName,\n" +
	 * "	bo.org_code AS orgCode,\n" + "	bo.leader_id AS leaderId,\n" +
	 * "	bp.email AS email,\n" + "	bp.person_name_en as nameEnglish,\n" +
	 * "	bu.user_type as userType\n" + "FROM\n" + "	base_users bu\n" +
	 * "	LEFT JOIN base_person bp ON bp.person_id = bu.person_id\n" +
	 * "	LEFT JOIN base_person_organization bpo ON bu.person_id = bpo.person_id \n"
	 * + "	AND bpo.position_id\n" +
	 * "	IS NULL LEFT JOIN base_organization bo ON bpo.org_id = bo.org_id\n" +
	 * "WHERE\n" + "	1 = 1";
	 */

	// 以上sql郭占山需求更改如下sql 20200324
	public static final String SQL_USER_SESSION = "SELECT\n"
			+ " bu.user_full_name AS userFullName,\n"
			+ " bu.user_id AS userId,\n"
			+ " bu.isadmin AS isadmin,\n"
			+ " bu.person_id AS personId,\n"
			+ " bp.employee_number AS employeeNumber,\n"
			+ " bp.mobile_phone AS mobilePhone,\n"
			+ " bu.user_name AS userName,\n"
			+ " bu.data_type AS dataType,\n"
			+ " bu.name_pingyin AS namePingyin,\n"
			+ " bu.user_head_img_url AS userHeadImgUrl,\n"
			+ " bu.group_code AS groupCode,\n"
			+ " bu.group_name AS groupName,\n"
			+ " bo.org_id AS orgId,\n"
			+ " bo.org_name AS orgName,\n"
			+ " bo.org_code AS orgCode,\n"
			+ " bo.leader_id AS leaderId,\n"
			+ " nvl(bp.email,bu.email_address) AS email,\n"
			+ " bp.person_name_en as nameEnglish,\n"
			+ " bp.person_name_cn as nameChinese,\n"
			+ " bu.user_type as userType\n"
			+ " FROM base_users bu\n"
			+ " LEFT JOIN base_person bp ON bp.person_id = bu.person_id\n"
			+ " LEFT JOIN base_organization bo on bp.org_code = bo.org_code where 1 = 1 \n";;

	public static final String SQL_USER_BY_USERNAME = "SELECT baseUsers.user_id AS userId , baseUsers.user_name as userName FROM base_users  baseUsers WHERE 1=1 ";

	public static final String SQL_USER_BY_SOURCEID = "select user_id from base_users where user_type = '60' and source_id = :sourceId";

	/**
	 * 功能描述:通过employee_no 员工编号查询用户信息
	 */
	// 开发环境使用
	/*
	 * public static final String SQL_USER_EMPLOYEE =
	 * "select bu.user_id as userId,\n" +
	 * "       bu.employee_number as employeeNumber,\n" +
	 * "       bu.pwd_update_date as pwdUpdateDate,\n" +
	 * "       bu.user_full_name as userFullName,\n" +
	 * "       bu.user_name as userName,\n" +
	 * "       bu.version_num as versionNum\n" +
	 * "  from base_users bu, base_person bp\n" +
	 * " where bu.employee_number = bp.employee_number\n" +
	 * " and bu.employee_number = :employeeNumber and 1 = 1";
	 */

	// 测试环境使用 等上传到测试环境再放开
	public static final String SQL_USER_EMPLOYEE = "select bu.user_id as userId,\n"
			+ "       bu.pwd_update_date as pwdUpdateDate,\n"
			+ "       bu.user_full_name as userFullName,\n"
			+ "       bu.user_name as userName,\n"
			+ "       bu.version_num as versionNum\n"
			+ "  from base_users bu, base_person bp\n"
			+ " where bu.user_name = bp.employee_number\n"
			+ " and bu.user_name = :employeeNumber and 1 = 1";
	private String personNameEn;
	private String departmentFullName;
	private Integer userId; // 用户Id
	private String phoneNumber; // 电话号码
	private String namePingyin; // 用户姓名（拼音）
	private String nameEnglish;// 英文名
	private String nameChinese;
	private String nameSimplePinyin; // 用户姓名（拼音首字母）
	private String personId; // 对应经销商、门店、员工的外围系统ID
	private String sourceId; // 关联人员ID、关联经销商ID、关联门店编码
	private String isadmin; // 是否是系统管理员
	private String userName; // 用户名/登录帐号
	private String userDesc; // 用户描述
	private String userType; // 用户类型：IN:内部员工，OUT：经销商、门店、导购
	private String userFullName; // 姓名
	private String departmentName;
	private String userHeadImgUrl;
	private String encryptedPassword; // 加密后密码（MD5）
	private String internalUser; // 是否是EBS用户，如果是，需要将用户、密码回写EBS系统
	private Integer deleteFlag; // 是否删除（0：未删除；1：已删除）
	private Integer supplierId;
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
	private Integer operatorUserId;
	private Date pwdUpdateDate;

	private String dataType;

	private Integer orgId;
	private String orgName;
	private String orgCode;
	private Integer leaderId;

	/* 员工信息 */
	private String employeeNumber; // 员工号
	private String personName; // 人员姓名
	private String personType; // IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购
	private String sex; // 性别
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date birthDay; // 生日
	private String cardNo; // 身份证号
	private String enabled; // 启用标识
	private String telPhone; // 电话号码
	private String mobilePhone; // 手机号码
	private String email; // 邮箱地址
	private String postalAddress; // 通信地址
	private String postcode; // 邮编

	private String userTypeDesc;// 用户类型
	private String groupCode;
	private String groupName;

	private String emailAddress;

	// 2019/12/04
	private String deptType;

	private List<BaseProfileValue_HI_RO> profileValues;

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setNamePingyin(String namePingyin) {
		this.namePingyin = namePingyin;
	}

	public String getNamePingyin() {
		return namePingyin;
	}

	public void setNameSimplePinyin(String nameSimplePinyin) {
		this.nameSimplePinyin = nameSimplePinyin;
	}

	public String getNameSimplePinyin() {
		return nameSimplePinyin;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonId() {
		return personId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setInternalUser(String internalUser) {
		this.internalUser = internalUser;
	}

	public String getInternalUser() {
		return internalUser;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
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

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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

	public String getUserTypeDesc() {
		return userTypeDesc;
	}

	public void setUserTypeDesc(String userTypeDesc) {
		this.userTypeDesc = userTypeDesc;
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

	public String getUserHeadImgUrl() {
		return userHeadImgUrl;
	}

	public void setUserHeadImgUrl(String userHeadImgUrl) {
		this.userHeadImgUrl = userHeadImgUrl;
	}

	public List<BaseProfileValue_HI_RO> getProfileValues() {
		return profileValues;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setProfileValues(List<BaseProfileValue_HI_RO> profileValues) {
		this.profileValues = profileValues;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getPwdUpdateDate() {
		return pwdUpdateDate;
	}

	public void setPwdUpdateDate(Date pwdUpdateDate) {
		this.pwdUpdateDate = pwdUpdateDate;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getPersonNameEn() {
		return personNameEn;
	}

	public void setPersonNameEn(String personNameEn) {
		this.personNameEn = personNameEn;
	}

	public String getDepartmentFullName() {
		return departmentFullName;
	}

	public void setDepartmentFullName(String departmentFullName) {
		this.departmentFullName = departmentFullName;
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
