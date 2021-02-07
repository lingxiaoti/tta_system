package com.sie.watsons.base.pos.qualificationreview.model.entities;

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
 * EquPosZzscCredentialsEntity_HI Entity Object
 * Sun Nov 10 18:55:45 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pos_zzsc_credentials")
public class EquPosZzscCredentialsEntity_HI {
    private Integer credentialsId;
    private Integer supplierId;
    private String longBusinessIndate;
    private String isThreeInOne;
    private String licenseNum;
    @JSONField(format="yyyy-MM-dd")
    private Date licenseIndate;
    private String longLicenseIndate;
    private String taxCode;
    private String tissueCode;
    @JSONField(format="yyyy-MM-dd")
    private Date tissueIndate;
    private String longTissueIndate;
    private String businessScope;
    private String representativeName;
    private String bankPermitNumber;
    private String enrollCapital;
    private String registeredAddress;
    @JSONField(format="yyyy-MM-dd")
    private Date establishmentDate;
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
    private String businessYears;
    private String shareholderInfo;
    private String relatedParty;
    private String projectExperience;
    private String intentionWatsonsProject;
    private Integer licenseFileId;
    private Integer tissueFileId;
    private Integer taxFileId;
    private Integer bankFileId;
    private Integer taxpayerFileId;
    private Integer projectExperienceFileId;
    private Integer relatedFileId;
    private String deptCode;
    private String licenseFileName;
    private String licenseFilePath;
    private String tissueFileName;
    private String tissueFilePath;
    private String taxFileName;
    private String taxFilePath;
    private String bankFileName;
    private String bankFilePath;
    private String taxpayerFileName;
    private String taxpayerFilePath;
    private String projectExperienceFileName;
    private String projectExperienceFilePath;
    private String relatedFileName;
    private String relatedFilePath;
	private Integer companyCreditFileId;
	private String companyCreditFileName;
	private String companyCreditFilePath;
	private String representativeContactWay;
	private String companyRegisteredAddress;
	private Integer qualificationId;
    private Integer operatorUserId;

	public void setCredentialsId(Integer credentialsId) {
		this.credentialsId = credentialsId;
	}

	@Id
	@SequenceGenerator(name = "equ_pos_zzsc_credentials_s", sequenceName = "equ_pos_zzsc_credentials_s", allocationSize = 1)
	@GeneratedValue(generator = "equ_pos_zzsc_credentials_s", strategy = GenerationType.SEQUENCE)
	@Column(name="credentials_id", nullable=false, length=22)
	public Integer getCredentialsId() {
		return credentialsId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=false, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setLongBusinessIndate(String longBusinessIndate) {
		this.longBusinessIndate = longBusinessIndate;
	}

	@Column(name="long_business_indate", nullable=true, length=30)	
	public String getLongBusinessIndate() {
		return longBusinessIndate;
	}

	public void setIsThreeInOne(String isThreeInOne) {
		this.isThreeInOne = isThreeInOne;
	}

	@Column(name="is_three_in_one", nullable=true, length=10)	
	public String getIsThreeInOne() {
		return isThreeInOne;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	@Column(name="license_num", nullable=true, length=100)	
	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseIndate(Date licenseIndate) {
		this.licenseIndate = licenseIndate;
	}

	@Column(name="license_indate", nullable=true, length=7)	
	public Date getLicenseIndate() {
		return licenseIndate;
	}

	public void setLongLicenseIndate(String longLicenseIndate) {
		this.longLicenseIndate = longLicenseIndate;
	}

	@Column(name="long_license_indate", nullable=true, length=30)	
	public String getLongLicenseIndate() {
		return longLicenseIndate;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	@Column(name="tax_code", nullable=true, length=100)	
	public String getTaxCode() {
		return taxCode;
	}

	public void setTissueCode(String tissueCode) {
		this.tissueCode = tissueCode;
	}

	@Column(name="tissue_code", nullable=true, length=100)	
	public String getTissueCode() {
		return tissueCode;
	}

	public void setTissueIndate(Date tissueIndate) {
		this.tissueIndate = tissueIndate;
	}

	@Column(name="tissue_indate", nullable=true, length=7)	
	public Date getTissueIndate() {
		return tissueIndate;
	}

	public void setLongTissueIndate(String longTissueIndate) {
		this.longTissueIndate = longTissueIndate;
	}

	@Column(name="long_tissue_indate", nullable=true, length=10)	
	public String getLongTissueIndate() {
		return longTissueIndate;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@Column(name="business_scope", nullable=true, length=4000)
	public String getBusinessScope() {
		return businessScope;
	}

	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}

	@Column(name="representative_name", nullable=true, length=200)	
	public String getRepresentativeName() {
		return representativeName;
	}

	public void setBankPermitNumber(String bankPermitNumber) {
		this.bankPermitNumber = bankPermitNumber;
	}

	@Column(name="bank_permit_number", nullable=true, length=100)	
	public String getBankPermitNumber() {
		return bankPermitNumber;
	}

	public void setEnrollCapital(String enrollCapital) {
		this.enrollCapital = enrollCapital;
	}

	@Column(name="enroll_capital", nullable=true, length=50)	
	public String getEnrollCapital() {
		return enrollCapital;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	@Column(name="registered_address", nullable=true, length=240)	
	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@Column(name="establishment_date", nullable=true, length=7)	
	public Date getEstablishmentDate() {
		return establishmentDate;
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

	public void setBusinessYears(String businessYears) {
		this.businessYears = businessYears;
	}

	@Column(name="business_years", nullable=true, length=10)	
	public String getBusinessYears() {
		return businessYears;
	}

	public void setShareholderInfo(String shareholderInfo) {
		this.shareholderInfo = shareholderInfo;
	}

	@Column(name="shareholder_info", nullable=true, length=240)	
	public String getShareholderInfo() {
		return shareholderInfo;
	}

	public void setRelatedParty(String relatedParty) {
		this.relatedParty = relatedParty;
	}

	@Column(name="related_party", nullable=true, length=240)	
	public String getRelatedParty() {
		return relatedParty;
	}

	public void setProjectExperience(String projectExperience) {
		this.projectExperience = projectExperience;
	}

	@Column(name="project_experience", nullable=true, length=240)	
	public String getProjectExperience() {
		return projectExperience;
	}

	public void setIntentionWatsonsProject(String intentionWatsonsProject) {
		this.intentionWatsonsProject = intentionWatsonsProject;
	}

	@Column(name="intention_watsons_project", nullable=true, length=240)	
	public String getIntentionWatsonsProject() {
		return intentionWatsonsProject;
	}

	public void setLicenseFileId(Integer licenseFileId) {
		this.licenseFileId = licenseFileId;
	}

	@Column(name="license_file_id", nullable=true, length=22)	
	public Integer getLicenseFileId() {
		return licenseFileId;
	}

	public void setTissueFileId(Integer tissueFileId) {
		this.tissueFileId = tissueFileId;
	}

	@Column(name="tissue_file_id", nullable=true, length=22)	
	public Integer getTissueFileId() {
		return tissueFileId;
	}

	public void setTaxFileId(Integer taxFileId) {
		this.taxFileId = taxFileId;
	}

	@Column(name="tax_file_id", nullable=true, length=22)	
	public Integer getTaxFileId() {
		return taxFileId;
	}

	public void setBankFileId(Integer bankFileId) {
		this.bankFileId = bankFileId;
	}

	@Column(name="bank_file_id", nullable=true, length=22)	
	public Integer getBankFileId() {
		return bankFileId;
	}

	public void setTaxpayerFileId(Integer taxpayerFileId) {
		this.taxpayerFileId = taxpayerFileId;
	}

	@Column(name="taxpayer_file_id", nullable=true, length=22)	
	public Integer getTaxpayerFileId() {
		return taxpayerFileId;
	}

	public void setProjectExperienceFileId(Integer projectExperienceFileId) {
		this.projectExperienceFileId = projectExperienceFileId;
	}

	@Column(name="project_experience_file_id", nullable=true, length=22)	
	public Integer getProjectExperienceFileId() {
		return projectExperienceFileId;
	}

	public void setRelatedFileId(Integer relatedFileId) {
		this.relatedFileId = relatedFileId;
	}

	@Column(name="related_file_id", nullable=true, length=22)	
	public Integer getRelatedFileId() {
		return relatedFileId;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="dept_code", nullable=true, length=20)	
	public String getDeptCode() {
		return deptCode;
	}

	public void setLicenseFileName(String licenseFileName) {
		this.licenseFileName = licenseFileName;
	}

	@Column(name="license_file_name", nullable=true, length=100)	
	public String getLicenseFileName() {
		return licenseFileName;
	}

	public void setLicenseFilePath(String licenseFilePath) {
		this.licenseFilePath = licenseFilePath;
	}

	@Column(name="license_file_path", nullable=true, length=240)	
	public String getLicenseFilePath() {
		return licenseFilePath;
	}

	public void setTissueFileName(String tissueFileName) {
		this.tissueFileName = tissueFileName;
	}

	@Column(name="tissue_file_name", nullable=true, length=100)	
	public String getTissueFileName() {
		return tissueFileName;
	}

	public void setTissueFilePath(String tissueFilePath) {
		this.tissueFilePath = tissueFilePath;
	}

	@Column(name="tissue_file_path", nullable=true, length=240)	
	public String getTissueFilePath() {
		return tissueFilePath;
	}

	public void setTaxFileName(String taxFileName) {
		this.taxFileName = taxFileName;
	}

	@Column(name="tax_file_name", nullable=true, length=100)	
	public String getTaxFileName() {
		return taxFileName;
	}

	public void setTaxFilePath(String taxFilePath) {
		this.taxFilePath = taxFilePath;
	}

	@Column(name="tax_file_path", nullable=true, length=240)	
	public String getTaxFilePath() {
		return taxFilePath;
	}

	public void setBankFileName(String bankFileName) {
		this.bankFileName = bankFileName;
	}

	@Column(name="bank_file_name", nullable=true, length=100)	
	public String getBankFileName() {
		return bankFileName;
	}

	public void setBankFilePath(String bankFilePath) {
		this.bankFilePath = bankFilePath;
	}

	@Column(name="bank_file_path", nullable=true, length=240)	
	public String getBankFilePath() {
		return bankFilePath;
	}

	public void setTaxpayerFileName(String taxpayerFileName) {
		this.taxpayerFileName = taxpayerFileName;
	}

	@Column(name="taxpayer_file_name", nullable=true, length=100)	
	public String getTaxpayerFileName() {
		return taxpayerFileName;
	}

	public void setTaxpayerFilePath(String taxpayerFilePath) {
		this.taxpayerFilePath = taxpayerFilePath;
	}

	@Column(name="taxpayer_file_path", nullable=true, length=240)	
	public String getTaxpayerFilePath() {
		return taxpayerFilePath;
	}

	public void setProjectExperienceFileName(String projectExperienceFileName) {
		this.projectExperienceFileName = projectExperienceFileName;
	}

	@Column(name="project_experience_file_name", nullable=true, length=100)	
	public String getProjectExperienceFileName() {
		return projectExperienceFileName;
	}

	public void setProjectExperienceFilePath(String projectExperienceFilePath) {
		this.projectExperienceFilePath = projectExperienceFilePath;
	}

	@Column(name="project_experience_file_path", nullable=true, length=240)	
	public String getProjectExperienceFilePath() {
		return projectExperienceFilePath;
	}

	public void setRelatedFileName(String relatedFileName) {
		this.relatedFileName = relatedFileName;
	}

	@Column(name="related_file_name", nullable=true, length=100)	
	public String getRelatedFileName() {
		return relatedFileName;
	}

	public void setRelatedFilePath(String relatedFilePath) {
		this.relatedFilePath = relatedFilePath;
	}

	@Column(name="related_file_path", nullable=true, length=240)	
	public String getRelatedFilePath() {
		return relatedFilePath;
	}

	@Column(name="company_credit_file_id", nullable=true, length=22)
	public Integer getCompanyCreditFileId() {
		return companyCreditFileId;
	}

	public void setCompanyCreditFileId(Integer companyCreditFileId) {
		this.companyCreditFileId = companyCreditFileId;
	}

	@Column(name="company_credit_file_name", nullable=true, length=240)
	public String getCompanyCreditFileName() {
		return companyCreditFileName;
	}

	public void setCompanyCreditFileName(String companyCreditFileName) {
		this.companyCreditFileName = companyCreditFileName;
	}

	@Column(name="company_credit_file_path", nullable=true, length=240)
	public String getCompanyCreditFilePath() {
		return companyCreditFilePath;
	}

	public void setCompanyCreditFilePath(String companyCreditFilePath) {
		this.companyCreditFilePath = companyCreditFilePath;
	}

	@Column(name="representative_contact_way", nullable=true, length=100)
	public String getRepresentativeContactWay() {
		return representativeContactWay;
	}

	public void setRepresentativeContactWay(String representativeContactWay) {
		this.representativeContactWay = representativeContactWay;
	}

	@Column(name="company_registered_address", nullable=true, length=1000)
	public String getCompanyRegisteredAddress() {
		return companyRegisteredAddress;
	}

	public void setCompanyRegisteredAddress(String companyRegisteredAddress) {
		this.companyRegisteredAddress = companyRegisteredAddress;
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
