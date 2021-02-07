package com.sie.watsons.base.supplier.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TtaRelSupplierEntity_HI_RO Entity Object
 * Wed May 22 12:51:37 CST 2019  Auto Generate
 */

public class TtaRelSupplierEntity_HI_RO {

	public static final String TTA_REL_SUPPLIER_V = "select * from tta_rel_supplier_V V where 1=1";

	public static  final String SELECT_ITEM_SUPPLIER = "SELECT t.rel_supplier_id as relSupplierId,\n" +
			"       t.rel_supplier_code as relSupplierCode,\n" +
			"       t.rel_supplier_name as relSupplierName,\n" +
			"       t.rel_id relId,\n" +
			"       t.creation_date as creationDate,\n" +
			"       t.created_by as createdBy,\n" +
			"       t.last_updated_by as lastUpdatedBy,\n" +
			"       t.last_update_date as lastUpdateDate,\n" +
			"       t.version_num as versionNum\n" +
			"  from tta_rel_supplier t\n" +
			" where 1 = 1 ";

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

	
	public Integer getRelSupplierId() {
		return relSupplierId;
	}

	public void setRelSupplierCode(String relSupplierCode) {
		this.relSupplierCode = relSupplierCode;
	}

	
	public String getRelSupplierCode() {
		return relSupplierCode;
	}

	public void setRelSupplierName(String relSupplierName) {
		this.relSupplierName = relSupplierName;
	}

	
	public String getRelSupplierName() {
		return relSupplierName;
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

	public String getRelContractOutput() {
		return relContractOutput;
	}

	public void setRelContractOutput(String relContractOutput) {
		this.relContractOutput = relContractOutput;
	}
}
