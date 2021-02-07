package com.sie.watsons.base.usergroupdeptbrand.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaUserGroupDeptBrandEntity_HI Entity Object
 * Fri Jul 12 17:22:33 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_user_group_dept_brand")
public class TtaUserGroupDeptBrandEntity_HI {
    private Integer userGroupDeptBrandId;
    private Integer userId;
    private String group;
    private String groupDesc;
    private String dept;
    private String deptDesc;
    private String brandCode;
    private String brandCn;
    private String brandEn;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
	private String dataType;
    private Integer operatorUserId;

	public void setUserGroupDeptBrandId(Integer userGroupDeptBrandId) {
		this.userGroupDeptBrandId = userGroupDeptBrandId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_USER_GROUP_DEPT_BRAND", sequenceName = "SEQ_TTA_USER_GROUP_DEPT_BRAND", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_USER_GROUP_DEPT_BRAND", strategy = GenerationType.SEQUENCE)
	@Column(name="user_group_dept_brand_id", nullable=false, length=22)	
	public Integer getUserGroupDeptBrandId() {
		return userGroupDeptBrandId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name="user_id", nullable=true, length=22)	
	public Integer getUserId() {
		return userId;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Column(name="[GROUP]", nullable=true, length=200)
	public String getGroup() {
		return group;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name="group_desc", nullable=true, length=500)	
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name="dept", nullable=true, length=200)	
	public String getDept() {
		return dept;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="dept_desc", nullable=true, length=500)	
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	@Column(name="brand_code", nullable=true, length=200)	
	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	@Column(name="brand_cn", nullable=true, length=500)	
	public String getBrandCn() {
		return brandCn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

	@Column(name="brand_en", nullable=true, length=500)	
	public String getBrandEn() {
		return brandEn;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name="start_time", nullable=true, length=7)	
	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="end_time", nullable=true, length=7)	
	public Date getEndTime() {
		return endTime;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="data_type", nullable=false, length=2)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
