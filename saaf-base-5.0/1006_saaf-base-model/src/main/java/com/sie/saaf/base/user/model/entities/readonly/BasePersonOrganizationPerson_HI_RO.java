package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BasePersonEntity_HI_RO Entity Object
 * Wed Dec 06 10:57:20 CST 2017  Auto Generate
 */

public class BasePersonOrganizationPerson_HI_RO {
	/*查询人员字段*/
	public static final String QUERY_PERSON_FIELD = " basePerson.person_id AS personId ,\r\n" + 
    		"	basePerson.employee_number AS employeeNumber ,\r\n" + 
    		"	basePerson.person_name AS personName ,\r\n" + 
    		"	basePerson.person_type AS personType ,\r\n" + 
    		"	basePerson.sex ,\r\n" + 
    		"	basePerson.birth_day AS birthDay ,\r\n" + 
    		"	basePerson.card_no AS cardNo ,\r\n" + 
    		"	basePerson.enabled AS enabled ,\r\n" + 
    		"	basePerson.tel_phone AS telPhone ,\r\n" + 
    		"	basePerson.mobile_phone AS mobilePhone ,\r\n" + 
    		"	basePerson.email AS email,\r\n" + 
    		"	basePerson.postal_address AS postalAddress ,\r\n" + 
    		"	basePerson.version_num AS versionNum ,\r\n" + 
    		"	basePerson.last_update_date AS lastUpdateDate ,\r\n" + 
    		"	basePerson.last_updated_by AS lastUpdatedBy ,\r\n" + 
    		"	basePerson.created_by AS createdBy ,\r\n" + 
    		"	basePerson.creation_date AS creationDate ,\r\n" + 
    		"	basePerson.postcode AS postcode, \r\n" +
    		"	basePersonOrganization.person_org_id AS personOrgId,\r\n" +
			"	basePerson.person_name_en  AS nameEn,\r\n" +
			"	basePerson.dept_no AS deptNo,\r\n" +
			"	basePerson.post_name AS postName,\r\n" +
			"	basePerson.grand,\r\n" +
			"	basePerson.area, \r\n" +
			"	basePerson.market, \r\n" +
			"	basePerson.org_code AS orgCode\r\n";

    /*关联base_person_organization查询*/
    public static final String QUERY_JOIN_PERSON_ORGANIZATION_SQL = "SELECT distinct \r\n" +
    		QUERY_PERSON_FIELD +
    		" ,lookup.meaning as personTypeDesc \n" +
			" ,basePerson.person_name_cn\n" +
			"FROM \r\n" +
    		"    base_person  basePerson \r\n" +
			// 需 关联base_person_organization, 否则 basePersonService/findBasePersonsByOrgId 将报错
			"LEFT JOIN " +
    		"	 base_person_organization  basePersonOrganization \r\n"+
    		" on basePerson.person_id=basePersonOrganization.person_id \r\n" +
			"LEFT JOIN (select lookup_type,lookup_code,meaning\n" +
			"from base_lookup_values where lookup_type='PERSON_TYPE' and enabled_flag='Y' \n" +
			"and delete_flag=0 and start_date_active<sysdate and (end_date_active>=sysdate or end_date_active is null)) lookup\n" +
			"on lookup.lookup_code=basePerson.person_type\n" +
			"WHERE 1=1 ";
    
	private Integer personId; //人员Id
    private Integer personOrgId;  //人员组织关系表ID
    private String employeeNumber; //员工号
    private String personName; //人员姓名
    private String personType; //IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购
    private String sex; //性别
    @JSONField(format = "yyyy-MM-dd")
    private Date birthDay; //生日
    private String cardNo; //身份证号
    private String enabled; //启用标识
    private String telPhone; //电话号码
    private String mobilePhone; //手机号码
    private String email; //邮箱地址
    private String postalAddress; //通信地址
    private String postcode; //邮编
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建日期
    private Integer createdBy; //创建人
    private Integer lastUpdatedBy; //更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新日期
    private Integer versionNum; //版本号
    private Integer operatorUserId;

    private String personTypeDesc;//员工类型

	private String nameEn; //姓名(英文)
	private String deptNo; //部门编码
	private String postName; //职位名称
	private Integer grand; //级别
	private String area; //区域
	private String market; //市场
	private String orgCode;//组织
	private String personNameCn; //中文名称

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	
	public Integer getPersonId() {
		return personId;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	
	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	
	public String getPersonName() {
		return personName;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	
	public String getPersonType() {
		return personType;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	
	public String getSex() {
		return sex;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	
	public Date getBirthDay() {
		return birthDay;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	
	public String getCardNo() {
		return cardNo;
	}


	public String getEnabled() {
		return enabled;
	}


	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}


	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	
	public String getTelPhone() {
		return telPhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getEmail() {
		return email;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	
	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	
	public String getPostcode() {
		return postcode;
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

	public String getPersonTypeDesc() {
		return personTypeDesc;
	}

	public void setPersonTypeDesc(String personTypeDesc) {
		this.personTypeDesc = personTypeDesc;
	}

    public Integer getPersonOrgId() {
        return personOrgId;
    }

    public void setPersonOrgId(Integer personOrgId) {
        this.personOrgId = personOrgId;
    }

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Integer getGrand() {
		return grand;
	}

	public void setGrand(Integer grand) {
		this.grand = grand;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setPersonNameCn(String personNameCn) {
		this.personNameCn = personNameCn;
	}

	public String getPersonNameCn() {
		return personNameCn;
	}
}
