package com.sie.watsons.base.withdrawal.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaSupplierItemRelSupplierEntity_HI_RO Entity Object
 * Fri Aug 09 00:28:34 CST 2019  Auto Generate
 */

public class TtaSupplierItemRelSupplierEntity_HI_RO {
	public static final String SELECT_REL_SUPPLIER = "select sup_item_rel_s_id as supItemRelSId,\n" +
			"       rel_supplier_code as relSupplierCode,\n" +
			"       rel_supplier_name as relSupplierName,\n" +
			"       supplier_item_h_id as supplierItemHId,\n" +
			"       marjor_supplier_code as marjorSupplierCode,\n" +
			"       marjor_supplier_name as marjorSupplierName,\n" +
			"       status as \tstatus,\n" +
			"       creation_date as creationDate,\n" +
			"       created_by as createdBy,\n" +
			"       last_updated_by as lastUpdatedBy,\n" +
			"       last_update_date as lastUpdateDate,\n" +
			"       last_update_login lastUpdateLogin,\n" +
			"       version_num as versionNum\n" +
			"  from tta_supplier_item_rel_supplier sirs where 1=1 and sirs.supplier_item_h_id =:supplierItemHId ";


    private Integer supItemRelSId;
    private String relSupplierCode;
    private String relSupplierName;
    private Integer supplierItemHId;
    private String marjorSupplierCode;
    private String marjorSupplierName;
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

	public void setSupItemRelSId(Integer supItemRelSId) {
		this.supItemRelSId = supItemRelSId;
	}

	
	public Integer getSupItemRelSId() {
		return supItemRelSId;
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

	public void setSupplierItemHId(Integer supplierItemHId) {
		this.supplierItemHId = supplierItemHId;
	}

	
	public Integer getSupplierItemHId() {
		return supplierItemHId;
	}

	public void setMarjorSupplierCode(String marjorSupplierCode) {
		this.marjorSupplierCode = marjorSupplierCode;
	}

	
	public String getMarjorSupplierCode() {
		return marjorSupplierCode;
	}

	public void setMarjorSupplierName(String marjorSupplierName) {
		this.marjorSupplierName = marjorSupplierName;
	}

	
	public String getMarjorSupplierName() {
		return marjorSupplierName;
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
