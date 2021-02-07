package com.sie.watsons.base.pos.qualificationreview.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPosZzscOperationStatusEntity_HI Entity Object
 * Fri Sep 20 18:01:33 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pos_zzsc_operation_status")
public class EquPosZzscOperationStatusEntity_HI {
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
    private BigDecimal companyArea;
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
	private Integer qualificationId;
    private Integer operatorUserId;

	public void setOperationalId(Integer operationalId) {
		this.operationalId = operationalId;
	}

	@Id
	@SequenceGenerator(name = "equ_pos_zzsc_oper_status_s", sequenceName = "equ_pos_zzsc_oper_status_s", allocationSize = 1)
	@GeneratedValue(generator = "equ_pos_zzsc_oper_status_s", strategy = GenerationType.SEQUENCE)
	@Column(name="operational_id", nullable=false, length=22)	
	public Integer getOperationalId() {
		return operationalId;
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

	public void setSurroundingEnvironment(String surroundingEnvironment) {
		this.surroundingEnvironment = surroundingEnvironment;
	}

	@Column(name="surrounding_environment", nullable=true, length=200)	
	public String getSurroundingEnvironment() {
		return surroundingEnvironment;
	}

	public void setDoorPlate(String doorPlate) {
		this.doorPlate = doorPlate;
	}

	@Column(name="door_plate", nullable=true, length=200)	
	public String getDoorPlate() {
		return doorPlate;
	}

	public void setReception(String reception) {
		this.reception = reception;
	}

	@Column(name="reception", nullable=true, length=200)	
	public String getReception() {
		return reception;
	}

	public void setCompanyArea(BigDecimal companyArea) {
		this.companyArea = companyArea;
	}

	@Column(name="company_area", nullable=true, length=22)	
	public BigDecimal getCompanyArea() {
		return companyArea;
	}

	public void setOfficeSpace(String officeSpace) {
		this.officeSpace = officeSpace;
	}

	@Column(name="office_space", nullable=true, length=200)	
	public String getOfficeSpace() {
		return officeSpace;
	}

	public void setEmployeeProfile(String employeeProfile) {
		this.employeeProfile = employeeProfile;
	}

	@Column(name="employee_profile", nullable=true, length=200)	
	public String getEmployeeProfile() {
		return employeeProfile;
	}

	public void setMajorCustomers(String majorCustomers) {
		this.majorCustomers = majorCustomers;
	}

	@Column(name="major_customers", nullable=true, length=200)	
	public String getMajorCustomers() {
		return majorCustomers;
	}

	public void setSaleChannel(String saleChannel) {
		this.saleChannel = saleChannel;
	}

	@Column(name="sale_channel", nullable=true, length=200)	
	public String getSaleChannel() {
		return saleChannel;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=20)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setSurEnvironmentFileId(Integer surEnvironmentFileId) {
		this.surEnvironmentFileId = surEnvironmentFileId;
	}

	@Column(name="sur_environment_file_id", nullable=true, length=22)	
	public Integer getSurEnvironmentFileId() {
		return surEnvironmentFileId;
	}

	public void setSurEnvironmentFilePath(String surEnvironmentFilePath) {
		this.surEnvironmentFilePath = surEnvironmentFilePath;
	}

	@Column(name="sur_environment_file_path", nullable=true, length=200)	
	public String getSurEnvironmentFilePath() {
		return surEnvironmentFilePath;
	}

	public void setSurEnvironmentFileName(String surEnvironmentFileName) {
		this.surEnvironmentFileName = surEnvironmentFileName;
	}

	@Column(name="sur_environment_file_name", nullable=true, length=200)	
	public String getSurEnvironmentFileName() {
		return surEnvironmentFileName;
	}

	public void setDoorPlateFileId(Integer doorPlateFileId) {
		this.doorPlateFileId = doorPlateFileId;
	}

	@Column(name="door_plate_file_id", nullable=true, length=22)	
	public Integer getDoorPlateFileId() {
		return doorPlateFileId;
	}

	public void setDoorPlateFilePath(String doorPlateFilePath) {
		this.doorPlateFilePath = doorPlateFilePath;
	}

	@Column(name="door_plate_file_path", nullable=true, length=200)	
	public String getDoorPlateFilePath() {
		return doorPlateFilePath;
	}

	public void setDoorPlateFileName(String doorPlateFileName) {
		this.doorPlateFileName = doorPlateFileName;
	}

	@Column(name="door_plate_file_name", nullable=true, length=200)	
	public String getDoorPlateFileName() {
		return doorPlateFileName;
	}

	public void setReceptionFileId(Integer receptionFileId) {
		this.receptionFileId = receptionFileId;
	}

	@Column(name="reception_file_id", nullable=true, length=22)	
	public Integer getReceptionFileId() {
		return receptionFileId;
	}

	public void setReceptionFilePath(String receptionFilePath) {
		this.receptionFilePath = receptionFilePath;
	}

	@Column(name="reception_file_path", nullable=true, length=200)	
	public String getReceptionFilePath() {
		return receptionFilePath;
	}

	public void setReceptionFileName(String receptionFileName) {
		this.receptionFileName = receptionFileName;
	}

	@Column(name="reception_file_name", nullable=true, length=200)	
	public String getReceptionFileName() {
		return receptionFileName;
	}

	public void setCompanyAreaFileId(Integer companyAreaFileId) {
		this.companyAreaFileId = companyAreaFileId;
	}

	@Column(name="company_area_file_id", nullable=true, length=22)	
	public Integer getCompanyAreaFileId() {
		return companyAreaFileId;
	}

	public void setCompanyAreaFilePath(String companyAreaFilePath) {
		this.companyAreaFilePath = companyAreaFilePath;
	}

	@Column(name="company_area_file_path", nullable=true, length=200)	
	public String getCompanyAreaFilePath() {
		return companyAreaFilePath;
	}

	public void setCompanyAreaFileName(String companyAreaFileName) {
		this.companyAreaFileName = companyAreaFileName;
	}

	@Column(name="company_area_file_name", nullable=true, length=200)	
	public String getCompanyAreaFileName() {
		return companyAreaFileName;
	}

	public void setOfficeSpaceFileId(Integer officeSpaceFileId) {
		this.officeSpaceFileId = officeSpaceFileId;
	}

	@Column(name="office_space_file_id", nullable=true, length=22)	
	public Integer getOfficeSpaceFileId() {
		return officeSpaceFileId;
	}

	public void setOfficeSpaceFilePath(String officeSpaceFilePath) {
		this.officeSpaceFilePath = officeSpaceFilePath;
	}

	@Column(name="office_space_file_path", nullable=true, length=200)	
	public String getOfficeSpaceFilePath() {
		return officeSpaceFilePath;
	}

	public void setOfficeSpaceFileName(String officeSpaceFileName) {
		this.officeSpaceFileName = officeSpaceFileName;
	}

	@Column(name="office_space_file_name", nullable=true, length=200)	
	public String getOfficeSpaceFileName() {
		return officeSpaceFileName;
	}

	public void setEmployeeProfileFileId(Integer employeeProfileFileId) {
		this.employeeProfileFileId = employeeProfileFileId;
	}

	@Column(name="employee_profile_file_id", nullable=true, length=22)	
	public Integer getEmployeeProfileFileId() {
		return employeeProfileFileId;
	}

	public void setEmployeeProfileFilePath(String employeeProfileFilePath) {
		this.employeeProfileFilePath = employeeProfileFilePath;
	}

	@Column(name="employee_profile_file_path", nullable=true, length=200)	
	public String getEmployeeProfileFilePath() {
		return employeeProfileFilePath;
	}

	public void setEmployeeProfileFileName(String employeeProfileFileName) {
		this.employeeProfileFileName = employeeProfileFileName;
	}

	@Column(name="employee_profile_file_name", nullable=true, length=200)	
	public String getEmployeeProfileFileName() {
		return employeeProfileFileName;
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
