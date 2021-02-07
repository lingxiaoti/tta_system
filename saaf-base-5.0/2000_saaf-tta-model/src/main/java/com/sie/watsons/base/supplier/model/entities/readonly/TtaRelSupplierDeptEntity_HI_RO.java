package com.sie.watsons.base.supplier.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaRelSupplierDeptEntity_HI_RO Entity Object
 * Wed May 22 12:51:38 CST 2019  Auto Generate
 */

public class TtaRelSupplierDeptEntity_HI_RO {
    private Integer relSupplierId;
    private String relDeptCode;
    private String relDeptName;
    private Integer relId;
    private String status;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

	public static final String TTA_REL_SUPPLIER_DEPT_V = "select * from tta_rel_supplier_DEPT_V V where 1=1";

	public void setRelSupplierId(Integer relSupplierId) {
		this.relSupplierId = relSupplierId;
	}

	
	public Integer getRelSupplierId() {
		return relSupplierId;
	}

	public void setRelDeptCode(String relDeptCode) {
		this.relDeptCode = relDeptCode;
	}

	
	public String getRelDeptCode() {
		return relDeptCode;
	}

	public void setRelDeptName(String relDeptName) {
		this.relDeptName = relDeptName;
	}

	
	public String getRelDeptName() {
		return relDeptName;
	}

	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	
	public Integer getRelId() {
		return relId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
