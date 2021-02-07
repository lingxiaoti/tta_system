package com.sie.watsons.base.supplier.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaRelSupplierEntity_HI Entity Object
 * Wed May 22 12:51:37 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_rel_supplier")
public class TtaRelSupplierEntity_HI {
    private Integer relSupplierId;
    private String relSupplierCode;
    private String relSupplierName;
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
	private String relContractOutput;
	public void setRelSupplierId(Integer relSupplierId) {
		this.relSupplierId = relSupplierId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_REL_SUPPLIER", sequenceName = "SEQ_TTA_REL_SUPPLIER", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REL_SUPPLIER", strategy = GenerationType.SEQUENCE)
	@Column(name="rel_supplier_id", nullable=false, length=22)	
	public Integer getRelSupplierId() {
		return relSupplierId;
	}

	public void setRelSupplierCode(String relSupplierCode) {
		this.relSupplierCode = relSupplierCode;
	}

	@Column(name="rel_supplier_code", nullable=false, length=50)	
	public String getRelSupplierCode() {
		return relSupplierCode;
	}

	public void setRelSupplierName(String relSupplierName) {
		this.relSupplierName = relSupplierName;
	}

	@Column(name="rel_supplier_name", nullable=false, length=230)
	public String getRelSupplierName() {
		return relSupplierName;
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

	@Column(name="status", nullable=false, length=1)	
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

	@Column(name="rel_contract_output", nullable=true, length=30)
	public String getRelContractOutput() {
		return relContractOutput;
	}

	public void setRelContractOutput(String relContractOutput) {
		this.relContractOutput = relContractOutput;
	}
}
