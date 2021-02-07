package com.sie.watsons.base.supplier.model.entities;

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
 * TtaRelSupplierDeptEntity_HI Entity Object
 * Wed May 22 12:51:38 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_rel_supplier_dept")
public class TtaRelSupplierDeptEntity_HI {
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

	public void setRelSupplierId(Integer relSupplierId) {
		this.relSupplierId = relSupplierId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_REL_SUPPLIER_DEPT", sequenceName = "SEQ_TTA_REL_SUPPLIER_DEPT", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REL_SUPPLIER_DEPT", strategy = GenerationType.SEQUENCE)
	@Column(name="rel_supplier_id", nullable=false, length=22)	
	public Integer getRelSupplierId() {
		return relSupplierId;
	}

	public void setRelDeptCode(String relDeptCode) {
		this.relDeptCode = relDeptCode;
	}

	@Column(name="rel_dept_code", nullable=false, length=50)	
	public String getRelDeptCode() {
		return relDeptCode;
	}

	public void setRelDeptName(String relDeptName) {
		this.relDeptName = relDeptName;
	}

	@Column(name="rel_dept_name", nullable=false, length=230)
	public String getRelDeptName() {
		return relDeptName;
	}

	public void setRelId(Integer relId) {
		this.relId = relId;
	}

	@Column(name="rel_id", nullable=false, length=22)	
	public Integer getRelId() {
		return relId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="status")
	public String getStatus() {
		return status;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
