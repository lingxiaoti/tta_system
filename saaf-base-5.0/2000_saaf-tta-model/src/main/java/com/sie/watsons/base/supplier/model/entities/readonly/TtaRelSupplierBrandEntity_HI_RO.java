package com.sie.watsons.base.supplier.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * TtaRelSupplierBrandEntity_HI_RO Entity Object
 * Wed May 22 12:51:41 CST 2019  Auto Generate
 */

public class TtaRelSupplierBrandEntity_HI_RO {
    private Integer relSupplierId;
    private String relBrandCode;
    private String relBrandName;
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
	private String groupCode;
    private String groupName;
    private String deptCode;
    private String deptName;
    private String buScorecard;
    private String winTopSupplier;
    private String kbScorecard;
    private Integer operatorUserId;
	private String relBrandNameEn;


	public static final String TTA_REL_SUPPLIER_BRAND_V = "select * from tta_rel_supplier_brand_v V where 1=1";

	public void setRelSupplierId(Integer relSupplierId) {
		this.relSupplierId = relSupplierId;
	}

	
	public Integer getRelSupplierId() {
		return relSupplierId;
	}

	public void setRelBrandCode(String relBrandCode) {
		this.relBrandCode = relBrandCode;
	}

	
	public String getRelBrandCode() {
		return relBrandCode;
	}

	public void setRelBrandName(String relBrandName) {
		this.relBrandName = relBrandName;
	}

	
	public String getRelBrandName() {
		return relBrandName;
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

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	public String getGroupName() {
		return groupName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	
	public String getDeptName() {
		return deptName;
	}

	public void setBuScorecard(String buScorecard) {
		this.buScorecard = buScorecard;
	}

	
	public String getBuScorecard() {
		return buScorecard;
	}

	public void setWinTopSupplier(String winTopSupplier) {
		this.winTopSupplier = winTopSupplier;
	}

	
	public String getWinTopSupplier() {
		return winTopSupplier;
	}

	public void setKbScorecard(String kbScorecard) {
		this.kbScorecard = kbScorecard;
	}

	
	public String getKbScorecard() {
		return kbScorecard;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getRelBrandNameEn() {
		return relBrandNameEn;
	}

	public void setRelBrandNameEn(String relBrandNameEn) {
		this.relBrandNameEn = relBrandNameEn;
	}
}
