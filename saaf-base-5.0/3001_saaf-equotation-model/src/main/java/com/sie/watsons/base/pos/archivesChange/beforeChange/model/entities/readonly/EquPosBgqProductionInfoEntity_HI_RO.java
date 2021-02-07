package com.sie.watsons.base.pos.archivesChange.beforeChange.model.entities.readonly;

import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosBgqProductionInfoEntity_HI_RO Entity Object
 * Wed Sep 25 01:18:36 CST 2019  Auto Generate
 */

public class EquPosBgqProductionInfoEntity_HI_RO {
	//查询供应商生产信息
	public  static  final String  QUERY_SQL =
			"SELECT ppi.production_id productionId\n" +
					"      ,ppi.supplier_id supplierId\n" +
					"      ,ppi.production_start_date productionStartDate\n" +
					"      ,ppi.factory_area factoryArea\n" +
					"      ,ppi.production_area productionArea\n" +
					"      ,ppi.finished_product_area finishedProductArea\n" +
					"      ,ppi.raw_material_area rawMaterialArea\n" +
					"      ,ppi.packaging_area packagingArea\n" +
					"      ,ppi.laboratory_area laboratoryArea\n" +
					"      ,ppi.office_area officeArea\n" +
					"      ,ppi.employees_amount employeesAmount\n" +
					"      ,ppi.quality_personnel_amount qualityPersonnelAmount\n" +
					"      ,ppi.salesman_amount salesmanAmount\n" +
					"      ,ppi.producers_amount producersAmount\n" +
					"      ,ppi.designer_amount designerAmount\n" +
					"      ,ppi.administrative_staff_amount administrativeStaffAmount\n" +
					"      ,ppi.other\n" +
					"      ,ppi.supplier_address_id supplierAddressId\n" +
					"      ,ppi.change_id changeId\n" +
					"      ,ppi.source_id sourceId\n" +
					"      ,ppi.version_num versionNum\n" +
					"      ,ppi.creation_date creationDate\n" +
					"      ,ppi.created_by createdBy\n" +
					"      ,ppi.last_updated_by lastUpdatedBy\n" +
					"      ,ppi.last_update_date lastUpdateDate\n" +
					"      ,ppi.last_update_login lastUpdateLogin\n" +
					"      ,ppi.attribute_category attributeCategory\n" +
					"      ,ppi.attribute1\n" +
					"      ,ppi.attribute2\n" +
					"      ,ppi.attribute3\n" +
					"      ,ppi.attribute4\n" +
					"      ,ppi.attribute5\n" +
					"      ,ppi.attribute6\n" +
					"      ,ppi.attribute7\n" +
					"      ,ppi.attribute8\n" +
					"      ,ppi.attribute9\n" +
					"      ,ppi.attribute10\n" +
					"FROM   equ_pos_bgq_production_info ppi where 1 = 1 ";

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
	@JSONField(format="yyyy-MM-dd")
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

	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}


	public Integer getProductionId() {
		return productionId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}


	public Integer getSupplierId() {
		return supplierId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}


	public Integer getVersionNum() {
		return versionNum;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}


	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}


	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}


	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}


	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}


	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}


	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}


	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}


	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}


	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}


	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}


	public String getAttribute10() {
		return attribute10;
	}

	public void setProductionStartDate(Date productionStartDate) {
		this.productionStartDate = productionStartDate;
	}


	public Date getProductionStartDate() {
		return productionStartDate;
	}

	public Float getFactoryArea() {
		return factoryArea;
	}

	public void setFactoryArea(Float factoryArea) {
		this.factoryArea = factoryArea;
	}

	public Float getProductionArea() {
		return productionArea;
	}

	public void setProductionArea(Float productionArea) {
		this.productionArea = productionArea;
	}

	public Float getFinishedProductArea() {
		return finishedProductArea;
	}

	public void setFinishedProductArea(Float finishedProductArea) {
		this.finishedProductArea = finishedProductArea;
	}

	public Float getRawMaterialArea() {
		return rawMaterialArea;
	}

	public void setRawMaterialArea(Float rawMaterialArea) {
		this.rawMaterialArea = rawMaterialArea;
	}

	public Float getPackagingArea() {
		return packagingArea;
	}

	public void setPackagingArea(Float packagingArea) {
		this.packagingArea = packagingArea;
	}

	public Float getLaboratoryArea() {
		return laboratoryArea;
	}

	public void setLaboratoryArea(Float laboratoryArea) {
		this.laboratoryArea = laboratoryArea;
	}

	public Integer getEmployeesAmount() {
		return employeesAmount;
	}

	public void setEmployeesAmount(Integer employeesAmount) {
		this.employeesAmount = employeesAmount;
	}

	public Integer getQualityPersonnelAmount() {
		return qualityPersonnelAmount;
	}

	public void setQualityPersonnelAmount(Integer qualityPersonnelAmount) {
		this.qualityPersonnelAmount = qualityPersonnelAmount;
	}

	public Integer getSalesmanAmount() {
		return salesmanAmount;
	}

	public void setSalesmanAmount(Integer salesmanAmount) {
		this.salesmanAmount = salesmanAmount;
	}

	public Integer getProducersAmount() {
		return producersAmount;
	}

	public void setProducersAmount(Integer producersAmount) {
		this.producersAmount = producersAmount;
	}

	public Integer getDesignerAmount() {
		return designerAmount;
	}

	public void setDesignerAmount(Integer designerAmount) {
		this.designerAmount = designerAmount;
	}

	public Integer getAdministrativeStaffAmount() {
		return administrativeStaffAmount;
	}

	public void setAdministrativeStaffAmount(Integer administrativeStaffAmount) {
		this.administrativeStaffAmount = administrativeStaffAmount;
	}

	public Integer getOther() {
		return other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	public Integer getSupplierAddressId() {
		return supplierAddressId;
	}

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

	public Float getOfficeArea() {
		return officeArea;
	}

	public void setOfficeArea(Float officeArea) {
		this.officeArea = officeArea;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getChangeId() {
		return changeId;
	}

	public void setChangeId(Integer changeId) {
		this.changeId = changeId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
}
