package com.sie.watsons.base.pos.archivesChange.afterChange.model.entities;

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
 * EquPosBgProductionInfoEntity_HI Entity Object
 * Tue Sep 24 14:10:18 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pos_bg_production_info")
public class EquPosBgProductionInfoEntity_HI {
    private Integer productionId;
    private Integer supplierId;
    private Integer versionNum;
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
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date productionStartDate;
    private Float factoryArea;
    private Float productionArea;
    private Float finishedProductArea;
    private Float rawMaterialArea;
    private Float packagingArea;
    private Float laboratoryArea;
    private Integer employeesAmount;
    private Integer qualityPersonnelAmount;
    private Integer salesmanAmount;
    private Integer producersAmount;
    private Integer designerAmount;
    private Integer administrativeStaffAmount;
    private Integer other;
    private Integer supplierAddressId;
    private Float officeArea;
	private Integer changeId;
	private Integer sourceId;
    private Integer operatorUserId;

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	@Id
	@SequenceGenerator(name = "equ_pos_bg_production_info_s", sequenceName = "equ_pos_bg_production_info_s", allocationSize = 1)
	@GeneratedValue(generator = "equ_pos_bg_production_info_s", strategy = GenerationType.SEQUENCE)
	@Column(name="source_id", nullable=false, length=22)
	public Integer getSourceId() {
		return sourceId;
	}

	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}

	@Column(name="production_id", nullable=false, length=22)	
	public Integer getProductionId() {
		return productionId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=false, length=22)	
	public Integer getSupplierId() {
		return supplierId;
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

	public void setProductionStartDate(Date productionStartDate) {
		this.productionStartDate = productionStartDate;
	}

	@Column(name="production_start_date", nullable=true, length=7)	
	public Date getProductionStartDate() {
		return productionStartDate;
	}

	public void setFactoryArea(Float factoryArea) {
		this.factoryArea = factoryArea;
	}

	@Column(name="factory_area", nullable=true, length=22)	
	public Float getFactoryArea() {
		return factoryArea;
	}

	public void setProductionArea(Float productionArea) {
		this.productionArea = productionArea;
	}

	@Column(name="production_area", nullable=true, length=22)	
	public Float getProductionArea() {
		return productionArea;
	}

	public void setFinishedProductArea(Float finishedProductArea) {
		this.finishedProductArea = finishedProductArea;
	}

	@Column(name="finished_product_area", nullable=true, length=22)	
	public Float getFinishedProductArea() {
		return finishedProductArea;
	}

	public void setRawMaterialArea(Float rawMaterialArea) {
		this.rawMaterialArea = rawMaterialArea;
	}

	@Column(name="raw_material_area", nullable=true, length=22)	
	public Float getRawMaterialArea() {
		return rawMaterialArea;
	}

	public void setPackagingArea(Float packagingArea) {
		this.packagingArea = packagingArea;
	}

	@Column(name="packaging_area", nullable=true, length=22)	
	public Float getPackagingArea() {
		return packagingArea;
	}

	public void setLaboratoryArea(Float laboratoryArea) {
		this.laboratoryArea = laboratoryArea;
	}

	@Column(name="laboratory_area", nullable=true, length=22)	
	public Float getLaboratoryArea() {
		return laboratoryArea;
	}

	public void setEmployeesAmount(Integer employeesAmount) {
		this.employeesAmount = employeesAmount;
	}

	@Column(name="employees_amount", nullable=true, length=22)	
	public Integer getEmployeesAmount() {
		return employeesAmount;
	}

	public void setQualityPersonnelAmount(Integer qualityPersonnelAmount) {
		this.qualityPersonnelAmount = qualityPersonnelAmount;
	}

	@Column(name="quality_personnel_amount", nullable=true, length=22)	
	public Integer getQualityPersonnelAmount() {
		return qualityPersonnelAmount;
	}

	public void setSalesmanAmount(Integer salesmanAmount) {
		this.salesmanAmount = salesmanAmount;
	}

	@Column(name="salesman_amount", nullable=true, length=22)	
	public Integer getSalesmanAmount() {
		return salesmanAmount;
	}

	public void setProducersAmount(Integer producersAmount) {
		this.producersAmount = producersAmount;
	}

	@Column(name="producers_amount", nullable=true, length=22)	
	public Integer getProducersAmount() {
		return producersAmount;
	}

	public void setDesignerAmount(Integer designerAmount) {
		this.designerAmount = designerAmount;
	}

	@Column(name="designer_amount", nullable=true, length=22)	
	public Integer getDesignerAmount() {
		return designerAmount;
	}

	public void setAdministrativeStaffAmount(Integer administrativeStaffAmount) {
		this.administrativeStaffAmount = administrativeStaffAmount;
	}

	@Column(name="administrative_staff_amount", nullable=true, length=22)	
	public Integer getAdministrativeStaffAmount() {
		return administrativeStaffAmount;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	@Column(name="other", nullable=true, length=22)	
	public Integer getOther() {
		return other;
	}

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

	@Column(name="supplier_address_id", nullable=false, length=22)	
	public Integer getSupplierAddressId() {
		return supplierAddressId;
	}

	public void setOfficeArea(Float officeArea) {
		this.officeArea = officeArea;
	}

	@Column(name="office_area", nullable=true, length=22)	
	public Float getOfficeArea() {
		return officeArea;
	}

	public void setChangeId(Integer changeId) {
		this.changeId = changeId;
	}

	@Column(name="change_id", nullable=true, length=22)
	public Integer getChangeId() {
		return changeId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
