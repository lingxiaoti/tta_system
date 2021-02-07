package com.sie.watsons.base.pos.supplierinfo.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosOperationalStatusEntity_HI_RO Entity Object
 * Sat Sep 14 14:50:37 CST 2019  Auto Generate
 */

public class EquPosOperationalStatusEntity_HI_RO {
	public  static  final String  QUERY_SQL =
			"SELECT os.operational_id operationalId\n" +
					"      ,os.supplier_id supplierId\n" +
					"      ,os.supplier_address_id supplierAddressId\n" +
					"      ,os.surrounding_environment surroundingEnvironment\n" +
					"      ,os.door_plate              doorPlate\n" +
					"      ,os.reception\n" +
					"      ,os.company_area            companyArea\n" +
					"      ,os.office_space            officeSpace\n" +
					"      ,os.employee_profile        employeeProfile\n" +
					"      ,os.major_customers         majorCustomers\n" +
					"      ,os.sale_channel            saleChannel\n" +
					"      ,os.dept_code deptCode\n" +
					"      ,os.sur_environment_file_id surEnvironmentFileId\n" +
					"      ,os.sur_environment_file_path surEnvironmentFilePath\n" +
					"      ,os.sur_environment_file_name surEnvironmentFileName\n" +
					"      ,os.door_plate_file_id doorPlateFileId\n" +
					"      ,os.door_plate_file_path doorPlateFilePath\n" +
					"      ,os.door_plate_file_name doorPlateFileName\n" +
					"      ,os.reception_file_id receptionFileId\n" +
					"      ,os.reception_file_path receptionFilePath\n" +
					"      ,os.reception_file_name receptionFileName\n" +
					"      ,os.company_area_file_id companyAreaFileId\n" +
					"      ,os.company_area_file_path companyAreaFilePath\n" +
					"      ,os.company_area_file_name companyAreaFileName\n" +
					"      ,os.office_space_file_id officeSpaceFileId\n" +
					"      ,os.office_space_file_path officeSpaceFilePath\n" +
					"      ,os.office_space_file_name officeSpaceFileName\n" +
					"      ,os.employee_profile_file_id employeeProfileFileId\n" +
					"      ,os.employee_profile_file_path employeeProfileFilePath\n" +
					"      ,os.employee_profile_file_name employeeProfileFileName\n" +
					"      ,os.version_num versionNum\n" +
					"      ,os.creation_date creationDate\n" +
					"      ,os.created_by createdBy\n" +
					"      ,os.last_updated_by lastUpdatedBy\n" +
					"      ,os.last_update_date lastUpdateDate\n" +
					"      ,os.last_update_login lastUpdateLogin\n" +
					"      ,os.attribute_category attributeCategory\n" +
					"      ,os.attribute1\n" +
					"      ,os.attribute2\n" +
					"      ,os.attribute3\n" +
					"      ,os.attribute4\n" +
					"      ,os.attribute5\n" +
					"      ,os.attribute6\n" +
					"      ,os.attribute7\n" +
					"      ,os.attribute8\n" +
					"      ,os.attribute9\n" +
					"      ,os.attribute10\n" +
					"FROM   equ_pos_operational_status os where 1 = 1";

	private Integer operationalId;
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
	private String surroundingEnvironment;
	private String doorPlate;
	private String reception;
	private Integer companyArea;
	private String officeSpace;
	private String employeeProfile;
	private String majorCustomers;
	private String saleChannel;
	private String deptCode;
	private Integer surEnvironmentFileId;
	private String surEnvironmentFilePath;
	private String surEnvironmentFileName;
	private Integer doorPlateFileId;
	private String doorPlateFilePath;
	private String doorPlateFileName;
	private Integer receptionFileId;
	private String receptionFilePath;
	private String receptionFileName;
	private Integer companyAreaFileId;
	private String companyAreaFilePath;
	private String companyAreaFileName;
	private Integer officeSpaceFileId;
	private String officeSpaceFilePath;
	private String officeSpaceFileName;
	private Integer employeeProfileFileId;
	private String employeeProfileFilePath;
	private String employeeProfileFileName;
	private Integer operatorUserId;

	public void setOperationalId(Integer operationalId) {
		this.operationalId = operationalId;
	}


	public Integer getOperationalId() {
		return operationalId;
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

	public void setSurroundingEnvironment(String surroundingEnvironment) {
		this.surroundingEnvironment = surroundingEnvironment;
	}


	public String getSurroundingEnvironment() {
		return surroundingEnvironment;
	}

	public void setDoorPlate(String doorPlate) {
		this.doorPlate = doorPlate;
	}


	public String getDoorPlate() {
		return doorPlate;
	}

	public void setReception(String reception) {
		this.reception = reception;
	}


	public String getReception() {
		return reception;
	}

	public void setCompanyArea(Integer companyArea) {
		this.companyArea = companyArea;
	}


	public Integer getCompanyArea() {
		return companyArea;
	}

	public void setOfficeSpace(String officeSpace) {
		this.officeSpace = officeSpace;
	}


	public String getOfficeSpace() {
		return officeSpace;
	}

	public void setEmployeeProfile(String employeeProfile) {
		this.employeeProfile = employeeProfile;
	}


	public String getEmployeeProfile() {
		return employeeProfile;
	}

	public void setMajorCustomers(String majorCustomers) {
		this.majorCustomers = majorCustomers;
	}


	public String getMajorCustomers() {
		return majorCustomers;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}


	public String getSaleChannel() {
		return saleChannel;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}


	public String getDeptCode() {
		return deptCode;
	}

	public void setSurEnvironmentFileId(Integer surEnvironmentFileId) {
		this.surEnvironmentFileId = surEnvironmentFileId;
	}


	public Integer getSurEnvironmentFileId() {
		return surEnvironmentFileId;
	}

	public void setSurEnvironmentFilePath(String surEnvironmentFilePath) {
		this.surEnvironmentFilePath = surEnvironmentFilePath;
	}


	public String getSurEnvironmentFilePath() {
		return surEnvironmentFilePath;
	}

	public void setSurEnvironmentFileName(String surEnvironmentFileName) {
		this.surEnvironmentFileName = surEnvironmentFileName;
	}


	public String getSurEnvironmentFileName() {
		return surEnvironmentFileName;
	}

	public void setDoorPlateFileId(Integer doorPlateFileId) {
		this.doorPlateFileId = doorPlateFileId;
	}


	public Integer getDoorPlateFileId() {
		return doorPlateFileId;
	}

	public void setDoorPlateFilePath(String doorPlateFilePath) {
		this.doorPlateFilePath = doorPlateFilePath;
	}


	public String getDoorPlateFilePath() {
		return doorPlateFilePath;
	}

	public void setDoorPlateFileName(String doorPlateFileName) {
		this.doorPlateFileName = doorPlateFileName;
	}


	public String getDoorPlateFileName() {
		return doorPlateFileName;
	}

	public void setReceptionFileId(Integer receptionFileId) {
		this.receptionFileId = receptionFileId;
	}


	public Integer getReceptionFileId() {
		return receptionFileId;
	}

	public void setReceptionFilePath(String receptionFilePath) {
		this.receptionFilePath = receptionFilePath;
	}


	public String getReceptionFilePath() {
		return receptionFilePath;
	}

	public void setReceptionFileName(String receptionFileName) {
		this.receptionFileName = receptionFileName;
	}


	public String getReceptionFileName() {
		return receptionFileName;
	}

	public void setCompanyAreaFileId(Integer companyAreaFileId) {
		this.companyAreaFileId = companyAreaFileId;
	}


	public Integer getCompanyAreaFileId() {
		return companyAreaFileId;
	}

	public void setCompanyAreaFilePath(String companyAreaFilePath) {
		this.companyAreaFilePath = companyAreaFilePath;
	}


	public String getCompanyAreaFilePath() {
		return companyAreaFilePath;
	}

	public void setCompanyAreaFileName(String companyAreaFileName) {
		this.companyAreaFileName = companyAreaFileName;
	}


	public String getCompanyAreaFileName() {
		return companyAreaFileName;
	}

	public void setOfficeSpaceFileId(Integer officeSpaceFileId) {
		this.officeSpaceFileId = officeSpaceFileId;
	}


	public Integer getOfficeSpaceFileId() {
		return officeSpaceFileId;
	}

	public void setOfficeSpaceFilePath(String officeSpaceFilePath) {
		this.officeSpaceFilePath = officeSpaceFilePath;
	}


	public String getOfficeSpaceFilePath() {
		return officeSpaceFilePath;
	}

	public void setOfficeSpaceFileName(String officeSpaceFileName) {
		this.officeSpaceFileName = officeSpaceFileName;
	}


	public String getOfficeSpaceFileName() {
		return officeSpaceFileName;
	}

	public void setEmployeeProfileFileId(Integer employeeProfileFileId) {
		this.employeeProfileFileId = employeeProfileFileId;
	}


	public Integer getEmployeeProfileFileId() {
		return employeeProfileFileId;
	}

	public void setEmployeeProfileFilePath(String employeeProfileFilePath) {
		this.employeeProfileFilePath = employeeProfileFilePath;
	}


	public String getEmployeeProfileFilePath() {
		return employeeProfileFilePath;
	}

	public void setEmployeeProfileFileName(String employeeProfileFileName) {
		this.employeeProfileFileName = employeeProfileFileName;
	}


	public String getEmployeeProfileFileName() {
		return employeeProfileFileName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}


	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
