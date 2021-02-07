package com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * EquPosBgCapacityEntity_HI_RO Entity Object
 * Tue Sep 24 14:10:15 CST 2019  Auto Generate
 */

public class EquPosBgCapacityEntity_HI_RO {
	//查询供应商产能信息
	public  static  final String  QUERY_SQL =

			"SELECT pci.capacity_id capacityId\n" +
					"      ,pci.supplier_id supplierId\n" +
					"      ,pci.supplier_address_id supplierAddressId\n" +
					"      ,pci.product_scope  productScope\n" +
					"      ,pci.main_raw_materials mainRawMaterials\n" +
					"      ,pci.production_equipment productionEquipment\n" +
					"      ,pci.production_capacity productionCapacity\n" +
					"      ,pci.remark\n" +
					"      ,pci.production_capacity_file_id productionCapacityFileId\n" +
					"      ,pci.change_id changeId\n" +
					"      ,pci.source_id sourceId\n" +
					"      ,pci.version_num versionNum\n" +
					"      ,pci.creation_date creationDate\n" +
					"      ,pci.created_by createdBy\n" +
					"      ,pci.last_updated_by lastUpdatedBy\n" +
					"      ,pci.last_update_date lastUpdateDate\n" +
					"      ,pci.last_update_login lastUpdateLogin\n" +
					"      ,pci.attribute_category attributeCategory\n" +
					"      ,pci.attribute1\n" +
					"      ,pci.attribute2\n" +
					"      ,pci.attribute3\n" +
					"      ,pci.attribute4\n" +
					"      ,pci.attribute5\n" +
					"      ,pci.attribute6\n" +
					"      ,pci.attribute7\n" +
					"      ,pci.attribute8\n" +
					"      ,pci.attribute9\n" +
					"      ,pci.attribute10\n" +
					"      ,pci.production_capacity_file_name productionCapacityFileName\n" +
					"      ,pci.production_capacity_file_path productionCapacityFilePath\n" +
					"      ,pci.capacity_id \"index\"\n" +
					"FROM   equ_pos_Bg_capacity pci\n" +
					"where 1 = 1 ";

	private Integer capacityId;
	private Integer supplierId;
	private Integer supplierAddressId;
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
	private String productScope;
	private String mainRawMaterials;
	private String productionEquipment;
	private String productionCapacity;
	private String remark;
	private Integer productionCapacityFileId;
	private String productionCapacityFileName;
	private String productionCapacityFilePath;
	private Integer changeId;
	private Integer sourceId;
	private Integer operatorUserId;
	private Integer index;

	public void setCapacityId(Integer capacityId) {
		this.capacityId = capacityId;
	}


	public Integer getCapacityId() {
		return capacityId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}


	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}


	public Integer getSupplierAddressId() {
		return supplierAddressId;
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

	public void setProductScope(String productScope) {
		this.productScope = productScope;
	}


	public String getProductScope() {
		return productScope;
	}

	public void setMainRawMaterials(String mainRawMaterials) {
		this.mainRawMaterials = mainRawMaterials;
	}


	public String getMainRawMaterials() {
		return mainRawMaterials;
	}

	public void setProductionEquipment(String productionEquipment) {
		this.productionEquipment = productionEquipment;
	}


	public String getProductionEquipment() {
		return productionEquipment;
	}

	public void setProductionCapacity(String productionCapacity) {
		this.productionCapacity = productionCapacity;
	}


	public String getProductionCapacity() {
		return productionCapacity;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getRemark() {
		return remark;
	}

	public void setProductionCapacityFileId(Integer productionCapacityFileId) {
		this.productionCapacityFileId = productionCapacityFileId;
	}


	public Integer getProductionCapacityFileId() {
		return productionCapacityFileId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getProductionCapacityFileName() {
		return productionCapacityFileName;
	}

	public void setProductionCapacityFileName(String productionCapacityFileName) {
		this.productionCapacityFileName = productionCapacityFileName;
	}

	public String getProductionCapacityFilePath() {
		return productionCapacityFilePath;
	}

	public void setProductionCapacityFilePath(String productionCapacityFilePath) {
		this.productionCapacityFilePath = productionCapacityFilePath;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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
