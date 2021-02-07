package com.sie.watsons.base.pos.qualificationreview.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Date;

/**
 * EquPosZzscSuppDeptInfoEntity_HI Entity Object
 * Fri Sep 20 18:01:39 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pos_zzsc_supp_dept_info")
public class EquPosZzscSuppDeptInfoEntity_HI {
    private Integer id;
    private Integer supplierId;
    private String deptCode;
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
    private String supplierType;
    private String supplierStatus;
	private String majorCustomer;
	private Clob companyDescription;
	private String cooperativeProject;
	private String agentLevel;
	private Integer supplierFileId;
	private String fileName;
	private String filePath;
	private String supplierInChargeNumber;
	private String supplierInChargeName;
	private Integer qualificationId;
    private Integer operatorUserId;

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@SequenceGenerator(name = "equ_pos_zzsc_supp_dept_info_s", sequenceName = "equ_pos_zzsc_supp_dept_info_s", allocationSize = 1)
	@GeneratedValue(generator = "equ_pos_zzsc_supp_dept_info_s", strategy = GenerationType.SEQUENCE)
	@Column(name="id", nullable=false, length=22)	
	public Integer getId() {
		return id;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=false, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=false, length=20)	
	public String getDeptCode() {
		return deptCode;
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

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	@Column(name="supplier_type", nullable=true, length=50)	
	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	@Column(name="supplier_status", nullable=true, length=20)	
	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public void setMajorCustomer(String majorCustomer) {
		this.majorCustomer = majorCustomer;
	}

	@Column(name="major_customer", nullable=true, length=200)
	public String getMajorCustomer() {
		return majorCustomer;
	}

	public void setCompanyDescription(Clob companyDescription) {
		this.companyDescription = companyDescription;
	}

	@Column(name="company_description", nullable=true)
	public Clob getCompanyDescription() {
		return companyDescription;
	}

	public void setCooperativeProject(String cooperativeProject) {
		this.cooperativeProject = cooperativeProject;
	}

	@Column(name="cooperative_project", nullable=true, length=240)
	public String getCooperativeProject() {
		return cooperativeProject;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}

	@Column(name="agent_level", nullable=true, length=20)
	public String getAgentLevel() {
		return agentLevel;
	}

	public void setSupplierFileId(Integer supplierFileId) {
		this.supplierFileId = supplierFileId;
	}

	@Column(name="supplier_file_id", nullable=true, length=22)
	public Integer getSupplierFileId() {
		return supplierFileId;
	}

	@Column(name="file_name", nullable=true, length=100)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_path", nullable=true, length=240)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="supplier_in_charge_number", nullable=true, length=10)
	public String getSupplierInChargeNumber() {
		return supplierInChargeNumber;
	}

	public void setSupplierInChargeNumber(String supplierInChargeNumber) {
		this.supplierInChargeNumber = supplierInChargeNumber;
	}

	@Column(name="supplier_in_charge_name", nullable=true, length=30)
	public String getSupplierInChargeName() {
		return supplierInChargeName;
	}

	public void setSupplierInChargeName(String supplierInChargeName) {
		this.supplierInChargeName = supplierInChargeName;
	}

	@Column(name="qualification_id", nullable=false, length=22)
	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}
}
