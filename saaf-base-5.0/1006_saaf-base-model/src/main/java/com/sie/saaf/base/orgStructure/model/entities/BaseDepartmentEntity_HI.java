package com.sie.saaf.base.orgStructure.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BaseDepartmentEntity_HI Entity Object
 * Mon Aug 20 19:28:19 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_department")
public class BaseDepartmentEntity_HI {
	private Integer departmentId; //部门ID
	private String departmentCode; //部门代码
	private String departmentName; //部门名称
	private String suffix; //部门名称后缀
	private Integer departmentLevel; //部门层级
	private Integer ouId; //事业部
	private Integer parentDepartmentId; //父部门
	private String costCenter; //成本中心
	private Integer departmentSeq; //部门序号
	@JSONField(format="yyyy-MM-dd")
	private Date dateFrom; //生效日期
	@JSONField(format="yyyy-MM-dd")
	private Date dateTo; //失效日期
	private String enableFlag; //生效标识
	private String bizLineType; //业务线
	private String channel; //渠道
	private String departmentType;//部门类型，10-总部部门，20-渠道
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建时间
	private Integer createdBy; //创建人
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //更新时间
	private Integer lastUpdatedBy; //更新人
	private Integer lastUpdateLogin; //最后登录ID
	private Integer deleteFlag; //删除标识
	private Integer versionNum; //版本号
	private Integer operatorUserId;
	private String inventoryEnable;

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Id
    @SequenceGenerator(name = "SEQ_BASE_DEPARTMENT", sequenceName = "SEQ_BASE_DEPARTMENT", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_DEPARTMENT", strategy = GenerationType.SEQUENCE)
	@Column(name="department_id", nullable=false, length=11)
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	@Column(name="department_code", nullable=true, length=20)
	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name="department_name", nullable=true, length=100)
	public String getDepartmentName() {
		return departmentName;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Column(name="suffix", nullable=true, length=30)
	public String getSuffix() {
		return suffix;
	}

	public void setDepartmentLevel(Integer departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	@Column(name="department_level", nullable=true, length=11)
	public Integer getDepartmentLevel() {
		return departmentLevel;
	}

	public void setOuId(Integer ouId) {
		this.ouId = ouId;
	}

	@Column(name="ou_id", nullable=true, length=11)
	public Integer getOuId() {
		return ouId;
	}

	public void setParentDepartmentId(Integer parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	@Column(name="parent_department_id", nullable=true, length=11)
	public Integer getParentDepartmentId() {
		return parentDepartmentId;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	@Column(name="cost_center", nullable=true, length=30)
	public String getCostCenter() {
		return costCenter;
	}

	public void setDepartmentSeq(Integer departmentSeq) {
		this.departmentSeq = departmentSeq;
	}

	@Column(name="department_seq", nullable=true, length=11)
	public Integer getDepartmentSeq() {
		return departmentSeq;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	@Column(name="date_from", nullable=true, length=0)
	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Column(name="date_to", nullable=true, length=0)
	public Date getDateTo() {
		return dateTo;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	@Column(name="enable_flag", nullable=true, length=6)
	public String getEnableFlag() {
		return enableFlag;
	}

	public void setBizLineType(String bizLineType) {
		this.bizLineType = bizLineType;
	}

	@Column(name="biz_line_type", nullable=true, length=50)
	public String getBizLineType() {
		return bizLineType;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name="channel", nullable=true, length=50)
	public String getChannel() {
		return channel;
	}

	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}

	@Column(name="department_type", nullable=true, length=30)
	public String getDepartmentType() {
		return departmentType;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=0)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=11)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)
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

	@Column(name="inventory_enable", nullable=true, length=10)
	public String getInventoryEnable() {
		return inventoryEnable;
	}

	public void setInventoryEnable(String inventoryEnable) {
		this.inventoryEnable = inventoryEnable;
	}
}