package com.sie.watsons.base.newbreed.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaNewbreedSetHeaderEntity_HI Entity Object
 * Wed Jun 05 09:59:11 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_NEWBREED_SET_HEADER")
public class TtaNewbreedSetHeaderEntity_HI {
    private Integer newbreedSetId;
    private String breedName;
    private String isEffective;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdatedBy;
    private Integer lastUpdateLogin;
    private Integer deleteFlag;
    private Integer versionNum;
    private Integer operatorUserId;
	private String deptCode;
	private String deptName;
	private String deptId;
	private Integer newbreedYear;
	public void setNewbreedSetId(Integer newbreedSetId) {
		this.newbreedSetId = newbreedSetId;
	}
	@Id
	@SequenceGenerator(name="SEQ_TTA_NEWBREED_SET_HEADER", sequenceName="SEQ_TTA_NEWBREED_SET_HEADER", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_NEWBREED_SET_HEADER",strategy=GenerationType.SEQUENCE)
	@Column(name="newbreed_set_id", nullable=false, length=22)	
	public Integer getNewbreedSetId() {
		return newbreedSetId;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	@Column(name="breed_name", nullable=true, length=100)	
	public String getBreedName() {
		return breedName;
	}

	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective;
	}

	@Column(name="is_effective", nullable=true, length=2)	
	public String getIsEffective() {
		return isEffective;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=22)	
	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
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

	@Column(name="dept_code", nullable=false, length=50)
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_name", nullable=false, length=200)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_id", nullable=false, length=22)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name="newbreed_year", nullable=true, length=7)
	public Integer getNewbreedYear() {
		return newbreedYear;
	}

	public void setNewbreedYear(Integer newbreedYear) {
		this.newbreedYear = newbreedYear;
	}
}
