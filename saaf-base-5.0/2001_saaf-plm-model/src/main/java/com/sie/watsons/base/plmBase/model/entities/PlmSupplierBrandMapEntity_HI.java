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
 * PlmSupplierBrandMapEntity_HI Entity Object
 * Fri Jul 10 15:57:06 CST 2020  Auto Generate
 */
@Entity
@Table(name="plm_supplier_brand_map")
public class PlmSupplierBrandMapEntity_HI {
    private Integer mapId;
    private String supplierCode;
    private String supplierName;
    private Integer brandCnUdaId;
    private Integer brandCnUdaValue;
    private String plmBrandCn;
    private Integer brandEnUdaId;
    private Integer brandEnUdaValue;
    private String plmBrandEn;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer deleteFlag;
    private Integer versionNum;
    private Integer operatorUserId;

	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_SUPPLIER_BRANDMAP", sequenceName = "SEQ_SUPPLIER_BRANDMAP", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "SEQ_SUPPLIER_BRANDMAP", strategy = GenerationType.SEQUENCE)
	@Column(name="map_id", nullable=true, length=22)	
	public Integer getMapId() {
		return mapId;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Column(name="supplier_code", nullable=true, length=50)	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=230)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setBrandCnUdaId(Integer brandCnUdaId) {
		this.brandCnUdaId = brandCnUdaId;
	}

	@Column(name="brand_cn_uda_id", nullable=true, length=22)	
	public Integer getBrandCnUdaId() {
		return brandCnUdaId;
	}

	public void setBrandCnUdaValue(Integer brandCnUdaValue) {
		this.brandCnUdaValue = brandCnUdaValue;
	}

	@Column(name="brand_cn_uda_value", nullable=true, length=22)	
	public Integer getBrandCnUdaValue() {
		return brandCnUdaValue;
	}

	public void setPlmBrandCn(String plmBrandCn) {
		this.plmBrandCn = plmBrandCn;
	}

	@Column(name="plm_brand_cn", nullable=true, length=200)	
	public String getPlmBrandCn() {
		return plmBrandCn;
	}

	public void setBrandEnUdaId(Integer brandEnUdaId) {
		this.brandEnUdaId = brandEnUdaId;
	}

	@Column(name="brand_en_uda_id", nullable=true, length=22)	
	public Integer getBrandEnUdaId() {
		return brandEnUdaId;
	}

	public void setBrandEnUdaValue(Integer brandEnUdaValue) {
		this.brandEnUdaValue = brandEnUdaValue;
	}

	@Column(name="brand_en_uda_value", nullable=true, length=22)	
	public Integer getBrandEnUdaValue() {
		return brandEnUdaValue;
	}

	public void setPlmBrandEn(String plmBrandEn) {
		this.plmBrandEn = plmBrandEn;
	}

	@Column(name="plm_brand_en", nullable=true, length=200)	
	public String getPlmBrandEn() {
		return plmBrandEn;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
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

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name="delete_flag", nullable=true, length=22)	
	public Integer getDeleteFlag() {
		return deleteFlag;
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
