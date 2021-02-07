package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmDeptClassEntity_HI_RO Entity Object
 * Wed Oct 23 15:32:20 CST 2019  Auto Generate
 */

public class PlmDeptClassEntity_HI_RO {
    private Integer plmDeptClassId;
    private Integer plmDeptListId;
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

    public static final String QUERY_SQL = "SELECT\n" +
			"pdc.PLM_DEPT_CLASS_ID as plmDeptClassId,\n" +
			"       pdc.PLM_DEPT_LIST_ID as plmDeptListId,\n" +
			"       pdc.PLM_CLASS_CODE as plmClassCode,\n" +
			"       pdc.PLM_CLASS_NAME as plmClassName,\n" +
			"       pdc.PLM_DEPT_CODE as plmDeptCode,\n" +
			"       pdc.PLM_DEPT_NAME as plmDeptName,\n" +
			"       pdc.CREATED_BY as createdBy,\n" +
			"       pdc.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       pdc.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       pdc.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       pdc.VERSION_NUM as versionNum,\n" +
			"       pdc.CREATION_DATE as creationDate\n" +
			"FROM PLM_DEPT_CLASS pdc\n" +
			"WHERE 1=1 ";

	public void setPlmDeptClassId(Integer plmDeptClassId) {
		this.plmDeptClassId = plmDeptClassId;
	}

	
	public Integer getPlmDeptClassId() {
		return plmDeptClassId;
	}

	public void setPlmDeptListId(Integer plmDeptListId) {
		this.plmDeptListId = plmDeptListId;
	}

	
	public Integer getPlmDeptListId() {
		return plmDeptListId;
	}

	public void setPlmClassCode(String plmClassCode) {
		this.plmClassCode = plmClassCode;
	}

	
	public String getPlmClassCode() {
		return plmClassCode;
	}

	public void setPlmClassName(String plmClassName) {
		this.plmClassName = plmClassName;
	}

	
	public String getPlmClassName() {
		return plmClassName;
	}

	public void setPlmDeptCode(String plmDeptCode) {
		this.plmDeptCode = plmDeptCode;
	}

	
	public String getPlmDeptCode() {
		return plmDeptCode;
	}

	public void setPlmDeptName(String plmDeptName) {
		this.plmDeptName = plmDeptName;
	}

	
	public String getPlmDeptName() {
		return plmDeptName;
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

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
