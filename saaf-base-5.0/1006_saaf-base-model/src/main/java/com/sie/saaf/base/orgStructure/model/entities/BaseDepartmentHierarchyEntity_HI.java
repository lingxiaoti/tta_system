package com.sie.saaf.base.orgStructure.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * BaseDepartmentHierarchyEntity_HI Entity Object
 * Mon Aug 13 09:43:58 CST 2018  Auto Generate
 */
@Entity
@Table(name="base_department_hierarchy")
public class BaseDepartmentHierarchyEntity_HI {
    private Integer seqId; //主键ID
    private Integer ouId; //事业部
    private Integer departmentId; //当前部门ID
    private Integer subordinateDepartmentId; //下级部门ID
    private Integer levelDifference; //部门层级差异
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

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_BASE_DEPARTMENT_HIERARCHY", sequenceName = "SEQ_BASE_DEPARTMENT_HIERARCHY", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_DEPARTMENT_HIERARCHY", strategy = GenerationType.SEQUENCE)
	@Column(name="seq_id", nullable=false, length=11)	
	public Integer getSeqId() {
		return seqId;
	}

	public void setOuId(Integer ouId) {
		this.ouId = ouId;
	}

	@Column(name="ou_id", nullable=true, length=11)	
	public Integer getOuId() {
		return ouId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name="department_id", nullable=true, length=11)	
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setSubordinateDepartmentId(Integer subordinateDepartmentId) {
		this.subordinateDepartmentId = subordinateDepartmentId;
	}

	@Column(name="subordinate_department_id", nullable=true, length=11)	
	public Integer getSubordinateDepartmentId() {
		return subordinateDepartmentId;
	}

	public void setLevelDifference(Integer levelDifference) {
		this.levelDifference = levelDifference;
	}

	@Column(name="level_difference", nullable=true, length=11)	
	public Integer getLevelDifference() {
		return levelDifference;
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
}
