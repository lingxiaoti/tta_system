package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmBaseClassEntity_HI Entity Object Thu Nov 14 14:41:18 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_BASE_CLASS")
public class PlmBaseClassEntity_HI {
	private Integer classId;
	private String className;
	private String classDesc;
	private String classLevel1;
	private String classLevel2;
	private String classLevel3;
	private String classLevel4;
	private String classLevel5;

	private String classLevelid1;
	private String classLevelid2;
	private String classLevelid3;
	private String classLevelid4;
	private String classLevelid5;

	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_CLASS", sequenceName = "SEQ_PLM_CLASS", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "SEQ_PLM_CLASS", strategy = GenerationType.SEQUENCE)
	@Column(name = "class_id", nullable = false, length = 22)
	public Integer getClassId() {
		return classId;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "class_name", nullable = true, length = 255)
	public String getClassName() {
		return className;
	}

	public void setClassLevel1(String classLevel1) {
		this.classLevel1 = classLevel1;
	}

	@Column(name = "class_level1", nullable = true, length = 255)
	public String getClassLevel1() {
		return classLevel1;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Column(name = "class_level2", nullable = true, length = 255)
	public String getClassLevel2() {
		return classLevel2;
	}

	public void setClassLevel2(String classLevel2) {
		this.classLevel2 = classLevel2;
	}

	@Column(name = "class_level3", nullable = true, length = 255)
	public String getClassLevel3() {
		return classLevel3;
	}

	public void setClassLevel3(String classLevel3) {
		this.classLevel3 = classLevel3;
	}

	@Column(name = "class_level4", nullable = true, length = 255)
	public String getClassLevel4() {
		return classLevel4;
	}

	public void setClassLevel4(String classLevel4) {
		this.classLevel4 = classLevel4;
	}

	@Column(name = "class_level5", nullable = true, length = 255)
	public String getClassLevel5() {
		return classLevel5;
	}

	public void setClassLevel5(String classLevel5) {
		this.classLevel5 = classLevel5;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "class_desc", nullable = true, length = 255)
	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	@Column(name = "class_levelid1", nullable = true, length = 255)
	public String getClassLevelid1() {
		return classLevelid1;
	}

	public void setClassLevelid1(String classLevelid1) {
		this.classLevelid1 = classLevelid1;
	}

	@Column(name = "class_levelid2", nullable = true, length = 255)
	public String getClassLevelid2() {
		return classLevelid2;
	}

	public void setClassLevelid2(String classLevelid2) {
		this.classLevelid2 = classLevelid2;
	}

	@Column(name = "class_levelid3", nullable = true, length = 255)
	public String getClassLevelid3() {
		return classLevelid3;
	}

	public void setClassLevelid3(String classLevelid3) {
		this.classLevelid3 = classLevelid3;
	}

	@Column(name = "class_levelid4", nullable = true, length = 255)
	public String getClassLevelid4() {
		return classLevelid4;
	}

	public void setClassLevelid4(String classLevelid4) {
		this.classLevelid4 = classLevelid4;
	}

	@Column(name = "class_levelid5", nullable = true, length = 255)
	public String getClassLevelid5() {
		return classLevelid5;
	}

	public void setClassLevelid5(String classLevelid5) {
		this.classLevelid5 = classLevelid5;
	}

}
