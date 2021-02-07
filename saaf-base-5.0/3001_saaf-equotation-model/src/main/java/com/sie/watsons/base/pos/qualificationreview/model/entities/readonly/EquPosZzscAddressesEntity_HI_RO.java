package com.sie.watsons.base.pos.qualificationreview.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosZzscAddressesEntity_HI_RO Entity Object
 * Fri Sep 20 18:01:34 CST 2019  Auto Generate
 */

public class EquPosZzscAddressesEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	//供应商地址信息查询
	public  static  final String  QUERY_SQL =
			"SELECT sa.supplier_address_id supplierAddressId\n" +
					"      ,sa.supplier_id supplierId\n" +
					"      ,sa.address_name addressName\n" +
					"      ,sa.country\n" +
					"      ,sa.province\n" +
					"      ,sa.city\n" +
					"      ,sa.county\n" +
					"      ,sa.dept_code deptCode\n" +
					"      ,sa.detail_address detailAddress\n" +
					"      ,sa.qualification_id qualificationId\n" +
					"      ,sa.version_num versionNum\n" +
					"      ,sa.creation_date creationDate\n" +
					"      ,sa.created_by createdBy\n" +
					"      ,sa.last_updated_by lastUpdatedBy\n" +
					"      ,sa.last_update_date lastUpdateDate\n" +
					"      ,sa.last_update_login lastUpdateLogin\n" +
					"      ,sa.attribute_category attributeCategory\n" +
					"      ,sa.attribute1\n" +
					"      ,sa.attribute2\n" +
					"      ,sa.attribute3\n" +
					"      ,sa.attribute4\n" +
					"      ,sa.attribute5\n" +
					"      ,sa.attribute6\n" +
					"      ,sa.attribute7\n" +
					"      ,sa.attribute8\n" +
					"      ,sa.attribute9\n" +
					"      ,sa.attribute10\n" +
					"FROM   equ_pos_zzsc_addresses sa where 1 = 1 ";

	private Integer supplierAddressId;
	private Integer supplierId;
	private String addressName;
	private String country;
	private String province;
	private String city;
	private String county;
	private String detailAddress;
	private String deptCode;
	private Integer qualificationId;
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
	private Integer operatorUserId;
	private EquPosZzscProductionInfoEntity_HI_RO oemProductionInfoParams;
	private List<EquPosZzscCapacityEntity_HI_RO> oemCapacityInfoParams;

	private EquPosZzscOperationStatusEntity_HI_RO itOperationalInfoParams;
	private List<EquPosZzscCapacityEntity_HI_RO> itCapacityInfoParams;

	private List<EquPosZzscCredentialAttachEntity_HI_RO> surEnvironmentDataTable;
	private List<EquPosZzscCredentialAttachEntity_HI_RO> doorPlateDataTable;
	private List<EquPosZzscCredentialAttachEntity_HI_RO> receptionDataTable;
	private List<EquPosZzscCredentialAttachEntity_HI_RO> companyAreaDataTable;
	private List<EquPosZzscCredentialAttachEntity_HI_RO> officeSpaceDataTable;
	private List<EquPosZzscCredentialAttachEntity_HI_RO> employeeProfileDataTable;

	public void setSupplierAddressId(Integer supplierAddressId) {
		this.supplierAddressId = supplierAddressId;
	}

	
	public Integer getSupplierAddressId() {
		return supplierAddressId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	
	public String getAddressName() {
		return addressName;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	public String getCountry() {
		return country;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	
	public String getProvince() {
		return province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	public String getCity() {
		return city;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	
	public String getCounty() {
		return county;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	
	public String getDetailAddress() {
		return detailAddress;
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

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public EquPosZzscProductionInfoEntity_HI_RO getOemProductionInfoParams() {
		return oemProductionInfoParams;
	}

	public void setOemProductionInfoParams(EquPosZzscProductionInfoEntity_HI_RO oemProductionInfoParams) {
		this.oemProductionInfoParams = oemProductionInfoParams;
	}

	public List<EquPosZzscCapacityEntity_HI_RO> getOemCapacityInfoParams() {
		return oemCapacityInfoParams;
	}

	public void setOemCapacityInfoParams(List<EquPosZzscCapacityEntity_HI_RO> oemCapacityInfoParams) {
		this.oemCapacityInfoParams = oemCapacityInfoParams;
	}

	public EquPosZzscOperationStatusEntity_HI_RO getItOperationalInfoParams() {
		return itOperationalInfoParams;
	}

	public void setItOperationalInfoParams(EquPosZzscOperationStatusEntity_HI_RO itOperationalInfoParams) {
		this.itOperationalInfoParams = itOperationalInfoParams;
	}

	public List<EquPosZzscCapacityEntity_HI_RO> getItCapacityInfoParams() {
		return itCapacityInfoParams;
	}

	public void setItCapacityInfoParams(List<EquPosZzscCapacityEntity_HI_RO> itCapacityInfoParams) {
		this.itCapacityInfoParams = itCapacityInfoParams;
	}

	public List<EquPosZzscCredentialAttachEntity_HI_RO> getSurEnvironmentDataTable() {
		return surEnvironmentDataTable;
	}

	public void setSurEnvironmentDataTable(List<EquPosZzscCredentialAttachEntity_HI_RO> surEnvironmentDataTable) {
		this.surEnvironmentDataTable = surEnvironmentDataTable;
	}

	public List<EquPosZzscCredentialAttachEntity_HI_RO> getDoorPlateDataTable() {
		return doorPlateDataTable;
	}

	public void setDoorPlateDataTable(List<EquPosZzscCredentialAttachEntity_HI_RO> doorPlateDataTable) {
		this.doorPlateDataTable = doorPlateDataTable;
	}

	public List<EquPosZzscCredentialAttachEntity_HI_RO> getReceptionDataTable() {
		return receptionDataTable;
	}

	public void setReceptionDataTable(List<EquPosZzscCredentialAttachEntity_HI_RO> receptionDataTable) {
		this.receptionDataTable = receptionDataTable;
	}

	public List<EquPosZzscCredentialAttachEntity_HI_RO> getCompanyAreaDataTable() {
		return companyAreaDataTable;
	}

	public void setCompanyAreaDataTable(List<EquPosZzscCredentialAttachEntity_HI_RO> companyAreaDataTable) {
		this.companyAreaDataTable = companyAreaDataTable;
	}

	public List<EquPosZzscCredentialAttachEntity_HI_RO> getOfficeSpaceDataTable() {
		return officeSpaceDataTable;
	}

	public void setOfficeSpaceDataTable(List<EquPosZzscCredentialAttachEntity_HI_RO> officeSpaceDataTable) {
		this.officeSpaceDataTable = officeSpaceDataTable;
	}

	public List<EquPosZzscCredentialAttachEntity_HI_RO> getEmployeeProfileDataTable() {
		return employeeProfileDataTable;
	}

	public void setEmployeeProfileDataTable(List<EquPosZzscCredentialAttachEntity_HI_RO> employeeProfileDataTable) {
		this.employeeProfileDataTable = employeeProfileDataTable;
	}

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}
}
