package com.sie.saaf.base.user.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseOrganizationEntity_HI_RO Entity Object
 * Wed Dec 06 10:57:11 CST 2017  Auto Generate
 */

public class BaseOrganizationPerson_HI_RO {
	/*查询的组织字段*/
	public static final String QUERY_ORG_FIELD = " baseOrganization.org_id AS orgId ,\r\n" + 
    		"	baseOrganization.parent_org_id AS parentOrgId ,\r\n" + 
    		"	parentOrg.org_name AS parentOrgName ,\r\n" +
    		"	baseOrganization.org_code AS orgCode ,\r\n" +
    		"	baseOrganization.org_name AS orgName ,\r\n" + 
    		"	baseOrganization.tree_type AS treeType ,\r\n" + 
    		"	baseOrganization.channel_type AS channelType ,\r\n" + 
    		"	baseOrganization.business_type AS businessType ,\r\n" + 
    		"	baseOrganization.is_dep AS isDep ,\r\n" + 
    		"	baseOrganization.org_type AS orgType ,\r\n" + 
    		"	baseOrganization.org_level AS orgLevel ,\r\n" + 
    		"	baseOrganization.is_leaf AS isLeaf ,\r\n" + 
    		"	baseOrganization.start_date AS startDate ,\r\n" + 
    		"	baseOrganization.end_date AS endDate ,\r\n" + 
    		"	baseOrganization.enabled AS enabled ,\r\n" + 
    		"	baseOrganization.remark AS remark ,\r\n" + 
    		"	baseOrganization.org_pinyin_name AS orgPinyinName ,\r\n" + 
    		"	baseOrganization.org_simple_pinyin_name AS orgSimplePinyinName ,\r\n" + 
    		"	baseOrganization.order_no AS orderNo ,\r\n" + 
    		"	baseOrganization.delete_flag AS deleteFlag ,\r\n" + 
    		"	baseOrganization.org_hierarchy_id AS orgHierarchyId ,\r\n" + 
    		"	baseOrganization.org_email AS orgEmail ,\r\n" + 
    		"	baseOrganization.source_system_id AS sourceSystemId ,\r\n" + 
    		"	baseOrganization.creation_date AS creationDate ,\r\n" + 
    		"	baseOrganization.created_by AS createdBy ,\r\n" + 
    		"	baseOrganization.last_updated_by AS lastUpdatedBy ,\r\n" + 
    		"	baseOrganization.last_update_date AS lastUpdateDate ,\r\n" + 
    		"	baseOrganization.version_num AS versionNum ,\r\n" + 
    		"	baseOrganization.leader_id AS leaderId \r\n";
	/*查询的组织领导字段*/
	public static final String QUERY_PERSON_FIELD = "	leader.employee_number AS leaderEmployeeNumber ,\r\n" + 
    		"	leader.person_name AS leaderPersonName ,\r\n" + 
    		"	leader.person_type AS leaderPersonType ,\r\n" + 
    		"	leader.sex AS leaderSex ,\r\n" + 
    		"	leader.birth_day AS leaderBirthDay ,\r\n" + 
    		"	leader.card_no AS leaderCardNo ,\r\n" + 
    		"	leader.enabled AS leaderEnableFlag ,\r\n" +
    		"	leader.tel_phone AS leaderTelPhone ,\r\n" + 
    		"	leader.mobile_phone AS leaderMobilePhone ,\r\n" + 
    		"	leader.email AS leaderEmail ,\r\n" + 
    		"	leader.postal_address AS leaderPostalAddress ,\r\n" + 
    		"	leader.postcode AS leaderPostcode\r\n";
	
	/*查询组织及组织领导*/
    public static final String QUERY_SQL = "SELECT\r\n" + 
    		QUERY_ORG_FIELD + "," +
    		QUERY_PERSON_FIELD + 
    		"FROM\r\n" + 
    		"	base_organization  baseOrganization\n" +
			"left join base_organization  parentOrg on parentOrg.org_id=baseOrganization.parent_org_id\r\n" +
    		"left join base_person  leader\r\n" +
    		"on baseOrganization.leader_id=leader.person_id\r\n" + 
    		"WHERE\r\n" + 
    		"	1 = 1 ";

    public static final String getDeptSql() {
    	String sql = "SELECT\n" +
				" baseOrganization.org_id AS orgId ,\n" +
				" baseOrganization.parent_org_id AS parentOrgId ,\n" +
				" parentOrg.org_name AS parentOrgName ,\n" +
				" baseOrganization.org_code AS orgCode ,\n" +
				" baseOrganization.org_name AS orgName\n" +
				"FROM\n" +
				"  base_organization  baseOrganization\n" +
				"left join base_organization  parentOrg on parentOrg.org_id=baseOrganization.parent_org_id\n" +
				"left join base_person  leader\n" +
				"on baseOrganization.leader_id=leader.person_id\n" +
				"WHERE 1 =1 \n";
    	return sql;
	}
    /*查询组织与员工关系*/
    public static final String QUERY_JOIN_PERSON_ORGANIZATION_SQL = "SELECT\r\n" + 
    		QUERY_ORG_FIELD + "," +
    		"	basePersonOrganization.person_org_id AS personOrgId ,\r\n" + 
    		"	basePersonOrganization.position_id AS positionId ,\r\n" + 
    		"	basePersonOrganization.person_id AS personId ,\r\n" + 
    		"	basePersonOrganization.start_date AS personOrgStartDate ,\r\n" + 
    		"	basePersonOrganization.end_date AS personOrgEndDate \r\n" + 
    		" FROM base_organization  baseOrganization ,\r\n"+
    		" base_person_organization  basePersonOrganization \r\n" + 
    		" WHERE baseOrganization.org_id = basePersonOrganization.org_id ";
    
	private Integer orgId; // 组织机构Id
	private Integer parentOrgId; // 父机构Id
	private String parentOrgName;// 父机构名称
	private String orgCode; // 组织机构编码
	private String orgName; // 组织机构名称
	private String treeType; // 组织树类型（行政、预算、核算）
	private String channelType; // 渠道类型(商务、电商、OTC、医务、内部等
	private String businessType; // 业务类型（业务、推广）
	private String isDep; // 部门/渠道标志
	private String orgType; // 类型（ORG：机构；DEPT：部门）
	private Integer orgLevel; // 组织机构层级
	private Integer isLeaf; // 是否是叶子节点，(0：叶子节点，1：非叶子节点)
	@JSONField(format = "yyyy-MM-dd")
	private Date startDate; // 启用日期
	@JSONField(format = "yyyy-MM-dd")
	private Date endDate; // 失效日期
	private String enabled; // 是否启用（Y：启用；N：禁用）
	private String remark; // 备注
	private String orgPinyinName; // 机构名称(拼音)
	private String orgSimplePinyinName; // 机构名称(拼音首字母)
	private Integer orderNo; // 排序号
	private Integer deleteFlag; // 是否删除（0：未删除；1：已删除）
	private String orgHierarchyId; // 层级结构
	private String orgEmail; // 邮件地址
	private String sourceSystemId; // 源系统ID
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; // 创建日期
	private Integer createdBy; // 创建人
	private Integer lastUpdatedBy; // 更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; // 更新日期
	private Integer versionNum; // 版本号
	private Integer leaderId; // 组织领导
	private Integer operatorUserId;
	private Integer organizationId;

	/* 部门领导 */
	private String leaderEmployeeNumber; // 员工号
	private String leaderPersonName; // 人员姓名
	private String leaderPersonType; // IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购
	private String leaderSex; // 性别
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date leaderBirthDay; // 生日
	private String leaderCardNo; // 身份证号
	private String leaderEnableFlag; // 启用标识
	private String leaderTelPhone; // 电话号码
	private String leaderMobilePhone; // 手机号码
	private String leaderEmail; // 邮箱地址
	private String leaderPostalAddress; // 通信地址
	private String leaderPostcode; // 邮编
	
	/*员工与组织关系*/
	private Integer positionId; //职位ID
    private Integer personId; //人员Id
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date personOrgStartDate; //生效日期
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date personOrgEndDate; //失效日期

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public Integer getParentOrgId() {
		return parentOrgId;
	}

	public String getParentOrgName() {
		return parentOrgName;
	}

	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setIsDep(String isDep) {
		this.isDep = isDep;
	}

	public String getIsDep() {
		return isDep;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setOrgPinyinName(String orgPinyinName) {
		this.orgPinyinName = orgPinyinName;
	}

	public String getOrgPinyinName() {
		return orgPinyinName;
	}

	public void setOrgSimplePinyinName(String orgSimplePinyinName) {
		this.orgSimplePinyinName = orgSimplePinyinName;
	}

	public String getOrgSimplePinyinName() {
		return orgSimplePinyinName;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOrgHierarchyId(String orgHierarchyId) {
		this.orgHierarchyId = orgHierarchyId;
	}

	public String getOrgHierarchyId() {
		return orgHierarchyId;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setSourceSystemId(String sourceSystemId) {
		this.sourceSystemId = sourceSystemId;
	}

	public String getSourceSystemId() {
		return sourceSystemId;
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

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getLeaderEmployeeNumber() {
		return leaderEmployeeNumber;
	}

	public void setLeaderEmployeeNumber(String leaderEmployeeNumber) {
		this.leaderEmployeeNumber = leaderEmployeeNumber;
	}

	public String getLeaderPersonName() {
		return leaderPersonName;
	}

	public void setLeaderPersonName(String leaderPersonName) {
		this.leaderPersonName = leaderPersonName;
	}

	public String getLeaderPersonType() {
		return leaderPersonType;
	}

	public void setLeaderPersonType(String leaderPersonType) {
		this.leaderPersonType = leaderPersonType;
	}

	public String getLeaderSex() {
		return leaderSex;
	}

	public void setLeaderSex(String leaderSex) {
		this.leaderSex = leaderSex;
	}

	public Date getLeaderBirthDay() {
		return leaderBirthDay;
	}

	public void setLeaderBirthDay(Date leaderBirthDay) {
		this.leaderBirthDay = leaderBirthDay;
	}

	public String getLeaderCardNo() {
		return leaderCardNo;
	}

	public void setLeaderCardNo(String leaderCardNo) {
		this.leaderCardNo = leaderCardNo;
	}

	public String getLeaderEnableFlag() {
		return leaderEnableFlag;
	}

	public void setLeaderEnableFlag(String leaderEnableFlag) {
		this.leaderEnableFlag = leaderEnableFlag;
	}

	public String getLeaderTelPhone() {
		return leaderTelPhone;
	}

	public void setLeaderTelPhone(String leaderTelPhone) {
		this.leaderTelPhone = leaderTelPhone;
	}

	public String getLeaderMobilePhone() {
		return leaderMobilePhone;
	}

	public void setLeaderMobilePhone(String leaderMobilePhone) {
		this.leaderMobilePhone = leaderMobilePhone;
	}

	public String getLeaderEmail() {
		return leaderEmail;
	}

	public void setLeaderEmail(String leaderEmail) {
		this.leaderEmail = leaderEmail;
	}

	public String getLeaderPostalAddress() {
		return leaderPostalAddress;
	}

	public void setLeaderPostalAddress(String leaderPostalAddress) {
		this.leaderPostalAddress = leaderPostalAddress;
	}

	public String getLeaderPostcode() {
		return leaderPostcode;
	}

	public void setLeaderPostcode(String leaderPostcode) {
		this.leaderPostcode = leaderPostcode;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Date getPersonOrgStartDate() {
		return personOrgStartDate;
	}

	public void setPersonOrgStartDate(Date personOrgStartDate) {
		this.personOrgStartDate = personOrgStartDate;
	}

	public Date getPersonOrgEndDate() {
		return personOrgEndDate;
	}

	public void setPersonOrgEndDate(Date personOrgEndDate) {
		this.personOrgEndDate = personOrgEndDate;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
}
