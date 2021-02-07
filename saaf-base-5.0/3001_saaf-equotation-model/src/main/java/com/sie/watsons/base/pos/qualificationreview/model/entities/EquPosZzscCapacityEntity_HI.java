package com.sie.watsons.base.pos.qualificationreview.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosZzscCapacityEntity_HI Entity Object
 * Fri Sep 20 18:01:31 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pos_zzsc_capacity")
public class EquPosZzscCapacityEntity_HI {
    private Integer capacityId;
    private Integer supplierId;
    private Integer supplierAddressId;
    private Integer versionNum;
	private String productionCapacityFileName;
	private String productionCapacityFilePath;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String productScope;
    private String mainRawMaterials;
    private String productionEquipment;
    private String productionCapacity;
    private String remark;
    private Integer productionCapacityFileId;
	private Integer qualificationId;
    private Integer operatorUserId;

	public void setCapacityId(Integer capacityId) {
		this.capacityId = capacityId;
	}

	@Id
	@SequenceGenerator(name = "equ_pos_zzsc_capacity_s", sequenceName = "equ_pos_zzsc_capacity_s", allocationSize = 1)
	@GeneratedValue(generator = "equ_pos_zzsc_capacity_s", strategy = GenerationType.SEQUENCE)
	@Column(name="capacity_id", nullable=false, length=22)	
	public Integer getCapacityId() {
		return capacityId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=false, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

	@Column(name="supplier_address_id", nullable=false, length=22)	
	public Integer getSupplierAddressId() {
		return supplierAddressId;
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

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	@Column(name="production_Capacity_File_Name", nullable=true, length=240)
	public String getProductionCapacityFileName() {
		return productionCapacityFileName;
	}

	public void setProductionCapacityFileName(String productionCapacityFileName) {
		this.productionCapacityFileName = productionCapacityFileName;
	}

	@Column(name="production_Capacity_File_Path", nullable=true, length=240)
	public String getProductionCapacityFilePath() {
		return productionCapacityFilePath;
	}

	public void setProductionCapacityFilePath(String productionCapacityFilePath) {
		this.productionCapacityFilePath = productionCapacityFilePath;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=false, length=7)	
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name="attribute_category", nullable=true, length=30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setProductScope(String productScope) {
		this.productScope = productScope;
	}

	@Column(name="product_scope", nullable=true, length=2000)
	public String getProductScope() {
		return productScope;
	}

	public void setMainRawMaterials(String mainRawMaterials) {
		this.mainRawMaterials = mainRawMaterials;
	}

	@Column(name="main_raw_materials", nullable=true, length=2000)
	public String getMainRawMaterials() {
		return mainRawMaterials;
	}

	public void setProductionEquipment(String productionEquipment) {
		this.productionEquipment = productionEquipment;
	}

	@Column(name="production_equipment", nullable=true, length=2000)
	public String getProductionEquipment() {
		return productionEquipment;
	}

	public void setProductionCapacity(String productionCapacity) {
		this.productionCapacity = productionCapacity;
	}

	@Column(name="production_capacity", nullable=true, length=2000)
	public String getProductionCapacity() {
		return productionCapacity;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="remark", nullable=true, length=2000)
	public String getRemark() {
		return remark;
	}

	public void setProductionCapacityFileId(Integer productionCapacityFileId) {
		this.productionCapacityFileId = productionCapacityFileId;
	}

	@Column(name="production_capacity_file_id", nullable=true, length=22)	
	public Integer getProductionCapacityFileId() {
		return productionCapacityFileId;
	}

	@Column(name="qualification_id", nullable=false, length=22)
	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
