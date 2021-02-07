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
 * PlmDeptListEntity_HI Entity Object
 * Wed Oct 23 15:32:20 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_DEPT_LIST")
public class PlmDeptListEntity_HI {
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer plmDeptListId;
    private String plmGroupCode;
    private String plmGroupDesc;
    private String plmDeptCode;
    private String plmDeptName;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    private Integer operatorUserId;

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

	public void setPlmDeptListId(Integer plmDeptListId) {
		this.plmDeptListId = plmDeptListId;
	}

	@Id	
	@SequenceGenerator(name="plmDeptListEntity_HISeqGener", sequenceName="SEQ_PLM_DEPT_LIST", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmDeptListEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_dept_list_id", nullable=false, length=22)	
	public Integer getPlmDeptListId() {
		return plmDeptListId;
	}

	public void setPlmGroupCode(String plmGroupCode) {
		this.plmGroupCode = plmGroupCode;
	}

	@Column(name="plm_group_code", nullable=true, length=50)	
	public String getPlmGroupCode() {
		return plmGroupCode;
	}

	public void setPlmGroupDesc(String plmGroupDesc) {
		this.plmGroupDesc = plmGroupDesc;
	}

	@Column(name="plm_group_desc", nullable=true, length=100)	
	public String getPlmGroupDesc() {
		return plmGroupDesc;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
