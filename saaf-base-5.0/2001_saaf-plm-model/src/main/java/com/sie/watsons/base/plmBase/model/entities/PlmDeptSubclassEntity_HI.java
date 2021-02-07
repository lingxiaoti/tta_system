package com.sie.watsons.base.plmBase.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmDeptSubclassEntity_HI Entity Object
 * Wed Oct 23 15:32:21 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_DEPT_SUBCLASS")
public class PlmDeptSubclassEntity_HI {
    private Integer plmDeptSubclassId;
    private Integer plmDeptListId;
    private Integer plmDeptClassId;
    private String plmSubclassCode;
    private String plmSubclassName;
    private String plmClassCode;
    private String plmClassName;
    private String plmDeptCode;
    private String plmDeptName;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

	public void setPlmDeptSubclassId(Integer plmDeptSubclassId) {
		this.plmDeptSubclassId = plmDeptSubclassId;
	}

	@Id	
	@SequenceGenerator(name="plmDeptSubclassEntity_HISeqGener", sequenceName="SEQ_PLM_DEPT_SUBCLASS", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmDeptSubclassEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_dept_subclass_id", nullable=false, length=22)	
	public Integer getPlmDeptSubclassId() {
		return plmDeptSubclassId;
	}

	public void setPlmDeptListId(Integer plmDeptListId) {
		this.plmDeptListId = plmDeptListId;
	}

	@Column(name="plm_dept_list_id", nullable=true, length=22)	
	public Integer getPlmDeptListId() {
		return plmDeptListId;
	}

	public void setPlmDeptClassId(Integer plmDeptClassId) {
		this.plmDeptClassId = plmDeptClassId;
	}

	@Column(name="plm_dept_class_id", nullable=true, length=22)	
	public Integer getPlmDeptClassId() {
		return plmDeptClassId;
	}

	public void setPlmSubclassCode(String plmSubclassCode) {
		this.plmSubclassCode = plmSubclassCode;
	}

	@Column(name="plm_subclass_code", nullable=true, length=50)	
	public String getPlmSubclassCode() {
		return plmSubclassCode;
	}

	public void setPlmSubclassName(String plmSubclassName) {
		this.plmSubclassName = plmSubclassName;
	}

	@Column(name="plm_subclass_name", nullable=true, length=100)	
	public String getPlmSubclassName() {
		return plmSubclassName;
	}

	public void setPlmClassCode(String plmClassCode) {
		this.plmClassCode = plmClassCode;
	}

	@Column(name="plm_class_code", nullable=true, length=50)	
	public String getPlmClassCode() {
		return plmClassCode;
	}

	public void setPlmClassName(String plmClassName) {
		this.plmClassName = plmClassName;
	}

	@Column(name="plm_class_name", nullable=true, length=100)	
	public String getPlmClassName() {
		return plmClassName;
	}

	public void setPlmDeptCode(String plmDeptCode) {
		this.plmDeptCode = plmDeptCode;
	}

	@Column(name="plm_dept_code", nullable=true, length=50)	
	public String getPlmDeptCode() {
		return plmDeptCode;
	}

	public void setPlmDeptName(String plmDeptName) {
		this.plmDeptName = plmDeptName;
	}

	@Column(name="plm_dept_name", nullable=true, length=100)	
	public String getPlmDeptName() {
		return plmDeptName;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
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

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
