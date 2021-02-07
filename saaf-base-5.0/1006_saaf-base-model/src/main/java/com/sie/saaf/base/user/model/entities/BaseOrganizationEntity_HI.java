package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseOrganizationEntity_HI Entity Object Wed Dec 06 10:57:11 CST 2017 Auto
 * Generate
 */
@Entity
@Table(name = "base_organization")
public class BaseOrganizationEntity_HI {
	private Integer orgId; // 组织机构Id
	private Integer parentOrgId; // 父机构Id
	private Integer organizationId;//关联org_id，表示库存组织所属的机构
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
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_BASE_ORGANIZATION", sequenceName = "SEQ_BASE_ORGANIZATION", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_ORGANIZATION", strategy = GenerationType.SEQUENCE)
	@Column(name = "org_id", nullable = false, length = 11)
	public Integer getOrgId() {
		return orgId;
	}

	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	@Column(name = "parent_org_id", nullable = true, length = 11)
	public Integer getParentOrgId() {
		return parentOrgId;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "organization_id", nullable = true, length = 11)
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "org_code", nullable = true, length = 20)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "org_name", nullable = true, length = 200)
	public String getOrgName() {
		return orgName;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	@Column(name = "tree_type", nullable = true, length = 20)
	public String getTreeType() {
		return treeType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name = "channel_type", nullable = true, length = 20)
	public String getChannelType() {
		return channelType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "business_type", nullable = true, length = 20)
	public String getBusinessType() {
		return businessType;
	}

	public void setIsDep(String isDep) {
		this.isDep = isDep;
	}

	@Column(name = "is_dep", nullable = true, length = 1)
	public String getIsDep() {
		return isDep;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "org_type", nullable = true, length = 20)
	public String getOrgType() {
		return orgType;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	@Column(name = "org_level", nullable = true, length = 11)
	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name = "is_leaf", nullable = true, length = 11)
	public Integer getIsLeaf() {
		return isLeaf;
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

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Column(name = "enabled", nullable = true, length = 2)
	public String getEnabled() {
		return enabled;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "remark", nullable = true, length = 2000)
	public String getRemark() {
		return remark;
	}

	public void setOrgPinyinName(String orgPinyinName) {
		this.orgPinyinName = orgPinyinName;
	}

	@Column(name = "org_pinyin_name", nullable = true, length = 100)
	public String getOrgPinyinName() {
		return orgPinyinName;
	}

	public void setOrgSimplePinyinName(String orgSimplePinyinName) {
		this.orgSimplePinyinName = orgSimplePinyinName;
	}

	@Column(name = "org_simple_pinyin_name", nullable = true, length = 50)
	public String getOrgSimplePinyinName() {
		return orgSimplePinyinName;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "order_no", nullable = true, length = 11)
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "delete_flag", nullable = true, length = 11)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setOrgHierarchyId(String orgHierarchyId) {
		this.orgHierarchyId = orgHierarchyId;
	}

	@Column(name = "org_hierarchy_id", nullable = true, length = 200)
	public String getOrgHierarchyId() {
		return orgHierarchyId;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	@Column(name = "org_email", nullable = true, length = 50)
	public String getOrgEmail() {
		return orgEmail;
	}

	public void setSourceSystemId(String sourceSystemId) {
		this.sourceSystemId = sourceSystemId;
	}

	@Column(name = "source_system_id", nullable = true, length = 20)
	public String getSourceSystemId() {
		return sourceSystemId;
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
	@Column(name = "version_num", nullable = true, length = 11)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	@Column(name = "leader_id", nullable = true, length = 11)
	public Integer getLeaderId() {
		return leaderId;
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
}
