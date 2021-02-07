package com.sie.watsons.base.supplier.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * TtaRelSupplierBrandEntity_HI Entity Object
 * Wed May 22 12:51:41 CST 2019  Auto Generate
 */
@Entity
@Table(name="tta_rel_supplier_brand")
public class TtaRelSupplierBrandEntity_HI {
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
    private String groupName;
    private String groupCode;
    private String deptCode;
    private String deptName;
    private String buScorecard;
    private String winTopSupplier;
    private String kbScorecard;
    private Integer operatorUserId;
	private String relBrandNameEn;

	public void setRelSupplierId(Integer relSupplierId) {
		this.relSupplierId = relSupplierId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_TTA_REL_SUPPLIER_BRAND", sequenceName = "SEQ_TTA_REL_SUPPLIER_BRAND", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_TTA_REL_SUPPLIER_BRAND", strategy = GenerationType.SEQUENCE)
	@Column(name="rel_supplier_id", nullable=false, length=22)	
	public Integer getRelSupplierId() {
		return relSupplierId;
	}

	public void setRelBrandCode(String relBrandCode) {
		this.relBrandCode = relBrandCode;
	}

	@Column(name="rel_brand_code")
	public String getRelBrandCode() {
		return relBrandCode;
	}

	public void setRelBrandName(String relBrandName) {
		this.relBrandName = relBrandName;
	}

	@Column(name="rel_brand_name")
	public String getRelBrandName() {
		return relBrandName;
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

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="group_name", nullable=true, length=120)	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code")
	public String getGroupCode() {
		return groupCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=20)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="dept_name", nullable=true, length=220)
	public String getDeptName() {
		return deptName;
	}

	public void setBuScorecard(String buScorecard) {
		this.buScorecard = buScorecard;
	}

	@Column(name="bu_scorecard", nullable=true, length=120)	
	public String getBuScorecard() {
		return buScorecard;
	}

	public void setWinTopSupplier(String winTopSupplier) {
		this.winTopSupplier = winTopSupplier;
	}

	@Column(name="win_top_supplier", nullable=true, length=120)	
	public String getWinTopSupplier() {
		return winTopSupplier;
	}

	public void setKbScorecard(String kbScorecard) {
		this.kbScorecard = kbScorecard;
	}

	@Column(name="kb_scorecard", nullable=true, length=20)	
	public String getKbScorecard() {
		return kbScorecard;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="rel_brand_name_en", nullable=true, length=230)
	public String getRelBrandNameEn() {
		return relBrandNameEn;
	}

	public void setRelBrandNameEn(String relBrandNameEn) {
		this.relBrandNameEn = relBrandNameEn;
	}
}
