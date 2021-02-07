package com.sie.watsons.base.pos.archivesChange.afterChange.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * EquPosBgCredentialsEntity_HI_RO Entity Object
 * Tue Sep 24 14:10:21 CST 2019  Auto Generate
 */

public class EquPosBgCredentialsEntity_HI_RO {
	//查询供应商资质信息
	public  static  final String  QUERY_SQL =

			"SELECT sc.credentials_id credentialsId\n" +
					"      ,sc.supplier_id supplierId\n" +
					"      ,sc.long_business_indate longBusinessIndate\n" +
					"      ,sc.is_three_in_one isThreeInOne\n" +
					"      ,sc.license_num           licenseNum\n" +
					"      ,sc.license_indate        licenseIndate\n" +
					"      ,sc.long_license_indate   longLicenseIndate\n" +
					"      ,sc.tax_code              taxCode\n" +
					"      ,sc.tissue_code           tissueCode\n" +
					"      ,sc.tissue_indate         tissueIndate\n" +
					"      ,sc.long_tissue_indate    longTissueIndate\n" +
					"      ,sc.business_scope        businessScope\n" +
					"      ,sc.representative_name   representativeName\n" +
					"      ,sc.bank_permit_number    bankPermitNumber\n" +
					"      ,sc.enroll_capital        enrollCapital\n" +
					"      ,sc.registered_address    registeredAddress\n" +
					"      ,sc.establishment_date    establishmentDate\n" +
					"      ,sc.business_years        businessYears\n" +
					"      ,sc.shareholder_info      shareholderInfo\n" +
					"      ,sc.related_party         relatedParty\n" +
					"      ,sc.project_experience    projectExperience\n" +
					"      ,sc.intention_watsons_project intentionWatsonsProject\n" +
					"      ,sc.license_file_id licenseFileId\n" +
					"      ,sc.tissue_file_id tissueFileId\n" +
					"      ,sc.tax_file_id taxFileId\n" +
					"      ,sc.bank_file_id bankFileId\n" +
					"      ,sc.taxpayer_file_id taxpayerFileId\n" +
					"      ,sc.project_experience_file_id projectExperienceFileId\n" +
					"      ,sc.related_file_id relatedFileId\n" +
					"      ,sc.dept_code deptCode\n" +
					"      ,sc.change_id changeId\n" +
					"      ,sc.source_id sourceId\n" +
					"      ,sc.version_num         versionNum\n" +
					"     ,sc.creation_date       creationDate\n" +
					"      ,sc.created_by          createdBy\n" +
					"      ,sc.last_updated_by     lastUpdatedBy\n" +
					"      ,sc.last_update_date    lastUpdateDate\n" +
					"      ,sc.last_update_login   lastUpdateLogin\n" +
					"      ,sc.attribute_category  attributeCategory\n" +
					"      ,sc.attribute1\n" +
					"      ,sc.attribute2\n" +
					"      ,sc.attribute3\n" +
					"      ,sc.attribute4\n" +
					"      ,sc.attribute5\n" +
					"      ,sc.attribute6\n" +
					"      ,sc.attribute7\n" +
					"      ,sc.attribute8\n" +
					"      ,sc.attribute9\n" +
					"      ,sc.attribute10\n" +
					"      ,sc.license_file_name licenseFileName\n" +
					"      ,sc.license_file_path licenseFilePath\n" +
					"      ,sc.tissue_file_name tissueFileName\n" +
					"      ,sc.tissue_file_path tissueFilePath\n" +
					"      ,sc.tax_file_name taxFileName\n" +
					"      ,sc.tax_file_path taxFilePath\n" +
					"      ,sc.bank_file_name bankFileName\n" +
					"      ,sc.bank_file_path bankFilePath\n" +
					"      ,sc.taxpayer_file_name taxpayerFileName\n" +
					"      ,sc.taxpayer_file_path taxpayerFilePath\n" +
					"      ,sc.project_experience_file_name projectExperienceFileName\n" +
					"      ,sc.project_experience_file_path projectExperienceFilePath\n" +
					"      ,sc.related_file_name relatedFileName\n" +
					"      ,sc.related_file_path relatedFilePath\n" +
					"      ,sc.company_credit_file_id companyCreditFileId\n" +
					"      ,sc.company_credit_file_name companyCreditFileName\n" +
					"      ,sc.company_credit_file_path companyCreditFilePath\n" +
					"      ,sc.representative_contact_way representativeContactWay\n" +
					"      ,sc.company_registered_address companyRegisteredAddress\n" +
					"FROM   equ_pos_bg_credentials sc\n" +
					"where 1 = 1";


	private Integer credentialsId; //资质信息ID
	private Integer supplierId; //供应商信息ID
	private String longBusinessIndate; //营业期限是否长期有效
	private String isThreeInOne; //是否三证合一
	private String licenseNum; //营业执照号
	@JSONField(format="yyyy-MM-dd")
	private Date licenseIndate; //营业期限
	private String longLicenseIndate; //营业执照号是否长期有效
	private String taxCode; //税务登记证号
	private String tissueCode; //组织机构代码
	@JSONField(format="yyyy-MM-dd")
	private Date tissueIndate; //组织机构代码到期日
	private String longTissueIndate; //组织机构代码是否长期有效
	private String businessScope; //营业范围
	private String representativeName; //法人代表姓名
	private String bankPermitNumber; //开户银行许可证号
	private String enrollCapital; //注册资金（万元）
	private String registeredAddress; //注册地址
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date establishmentDate; //成立日期
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
	private String businessYears; //经营年限
	private String shareholderInfo; //股东信息
	private String relatedParty; //关联方信息
	private String projectExperience; //行业内类似项目经验
	private String intentionWatsonsProject; //以往有合作意向的屈臣氏项目
	private Integer operatorUserId;
	private Integer licenseFileId;
	private Integer tissueFileId;
	private Integer taxFileId;
	private Integer bankFileId;
	private Integer taxpayerFileId;
	private Integer projectExperienceFileId;
	private Integer relatedFileId;
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
	private String deptCode;
	private Integer changeId;
	private Integer sourceId;

	public void setCredentialsId(Integer credentialsId) {
		this.credentialsId = credentialsId;
	}


	public Integer getCredentialsId() {
		return credentialsId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}


	public Integer getSupplierId() {
		return supplierId;
	}

	public void setLongBusinessIndate(String longBusinessIndate) {
		this.longBusinessIndate = longBusinessIndate;
	}


	public String getLongBusinessIndate() {
		return longBusinessIndate;
	}

	public void setIsThreeInOne(String isThreeInOne) {
		this.isThreeInOne = isThreeInOne;
	}


	public String getIsThreeInOne() {
		return isThreeInOne;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}


	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseIndate(Date licenseIndate) {
		this.licenseIndate = licenseIndate;
	}


	public Date getLicenseIndate() {
		return licenseIndate;
	}

	public void setLongLicenseIndate(String longLicenseIndate) {
		this.longLicenseIndate = longLicenseIndate;
	}


	public String getLongLicenseIndate() {
		return longLicenseIndate;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}


	public String getTaxCode() {
		return taxCode;
	}

	public void setTissueCode(String tissueCode) {
		this.tissueCode = tissueCode;
	}


	public String getTissueCode() {
		return tissueCode;
	}

	public void setTissueIndate(Date tissueIndate) {
		this.tissueIndate = tissueIndate;
	}


	public Date getTissueIndate() {
		return tissueIndate;
	}

	public void setLongTissueIndate(String longTissueIndate) {
		this.longTissueIndate = longTissueIndate;
	}


	public String getLongTissueIndate() {
		return longTissueIndate;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}


	public String getBusinessScope() {
		return businessScope;
	}

	public void setRepresentativeName(String representativeName) {
		this.representativeName = representativeName;
	}


	public String getRepresentativeName() {
		return representativeName;
	}

	public void setBankPermitNumber(String bankPermitNumber) {
		this.bankPermitNumber = bankPermitNumber;
	}


	public String getBankPermitNumber() {
		return bankPermitNumber;
	}

	public void setEnrollCapital(String enrollCapital) {
		this.enrollCapital = enrollCapital;
	}


	public String getEnrollCapital() {
		return enrollCapital;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}


	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}


	public Date getEstablishmentDate() {
		return establishmentDate;
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

	public void setBusinessYears(String businessYears) {
		this.businessYears = businessYears;
	}


	public String getBusinessYears() {
		return businessYears;
	}

	public void setShareholderInfo(String shareholderInfo) {
		this.shareholderInfo = shareholderInfo;
	}


	public String getShareholderInfo() {
		return shareholderInfo;
	}

	public void setRelatedParty(String relatedParty) {
		this.relatedParty = relatedParty;
	}


	public String getRelatedParty() {
		return relatedParty;
	}

	public void setProjectExperience(String projectExperience) {
		this.projectExperience = projectExperience;
	}


	public String getProjectExperience() {
		return projectExperience;
	}

	public void setIntentionWatsonsProject(String intentionWatsonsProject) {
		this.intentionWatsonsProject = intentionWatsonsProject;
	}


	public String getIntentionWatsonsProject() {
		return intentionWatsonsProject;
	}

	public void setLicenseFileId(Integer licenseFileId) {
		this.licenseFileId = licenseFileId;
	}


	public Integer getLicenseFileId() {
		return licenseFileId;
	}

	public void setTissueFileId(Integer tissueFileId) {
		this.tissueFileId = tissueFileId;
	}


	public Integer getTissueFileId() {
		return tissueFileId;
	}

	public void setTaxFileId(Integer taxFileId) {
		this.taxFileId = taxFileId;
	}


	public Integer getTaxFileId() {
		return taxFileId;
	}

	public void setBankFileId(Integer bankFileId) {
		this.bankFileId = bankFileId;
	}


	public Integer getBankFileId() {
		return bankFileId;
	}

	public void setTaxpayerFileId(Integer taxpayerFileId) {
		this.taxpayerFileId = taxpayerFileId;
	}


	public Integer getTaxpayerFileId() {
		return taxpayerFileId;
	}

	public void setProjectExperienceFileId(Integer projectExperienceFileId) {
		this.projectExperienceFileId = projectExperienceFileId;
	}


	public Integer getProjectExperienceFileId() {
		return projectExperienceFileId;
	}

	public void setRelatedFileId(Integer relatedFileId) {
		this.relatedFileId = relatedFileId;
	}


	public Integer getRelatedFileId() {
		return relatedFileId;
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

	public String getLicenseFileName() {
		return licenseFileName;
	}

	public void setLicenseFileName(String licenseFileName) {
		this.licenseFileName = licenseFileName;
	}

	public String getLicenseFilePath() {
		return licenseFilePath;
	}

	public void setLicenseFilePath(String licenseFilePath) {
		this.licenseFilePath = licenseFilePath;
	}

	public String getTissueFileName() {
		return tissueFileName;
	}

	public void setTissueFileName(String tissueFileName) {
		this.tissueFileName = tissueFileName;
	}

	public String getTissueFilePath() {
		return tissueFilePath;
	}

	public void setTissueFilePath(String tissueFilePath) {
		this.tissueFilePath = tissueFilePath;
	}

	public String getTaxFileName() {
		return taxFileName;
	}

	public void setTaxFileName(String taxFileName) {
		this.taxFileName = taxFileName;
	}

	public String getTaxFilePath() {
		return taxFilePath;
	}

	public void setTaxFilePath(String taxFilePath) {
		this.taxFilePath = taxFilePath;
	}

	public String getBankFileName() {
		return bankFileName;
	}

	public void setBankFileName(String bankFileName) {
		this.bankFileName = bankFileName;
	}

	public String getBankFilePath() {
		return bankFilePath;
	}

	public void setBankFilePath(String bankFilePath) {
		this.bankFilePath = bankFilePath;
	}

	public String getTaxpayerFileName() {
		return taxpayerFileName;
	}

	public void setTaxpayerFileName(String taxpayerFileName) {
		this.taxpayerFileName = taxpayerFileName;
	}

	public String getTaxpayerFilePath() {
		return taxpayerFilePath;
	}

	public void setTaxpayerFilePath(String taxpayerFilePath) {
		this.taxpayerFilePath = taxpayerFilePath;
	}

	public String getProjectExperienceFileName() {
		return projectExperienceFileName;
	}

	public void setProjectExperienceFileName(String projectExperienceFileName) {
		this.projectExperienceFileName = projectExperienceFileName;
	}

	public String getProjectExperienceFilePath() {
		return projectExperienceFilePath;
	}

	public void setProjectExperienceFilePath(String projectExperienceFilePath) {
		this.projectExperienceFilePath = projectExperienceFilePath;
	}

	public String getRelatedFileName() {
		return relatedFileName;
	}

	public void setRelatedFileName(String relatedFileName) {
		this.relatedFileName = relatedFileName;
	}

	public String getRelatedFilePath() {
		return relatedFilePath;
	}

	public void setRelatedFilePath(String relatedFilePath) {
		this.relatedFilePath = relatedFilePath;
	}

	public Integer getCompanyCreditFileId() {
		return companyCreditFileId;
	}

	public void setCompanyCreditFileId(Integer companyCreditFileId) {
		this.companyCreditFileId = companyCreditFileId;
	}

	public String getCompanyCreditFileName() {
		return companyCreditFileName;
	}

	public void setCompanyCreditFileName(String companyCreditFileName) {
		this.companyCreditFileName = companyCreditFileName;
	}

	public String getCompanyCreditFilePath() {
		return companyCreditFilePath;
	}

	public void setCompanyCreditFilePath(String companyCreditFilePath) {
		this.companyCreditFilePath = companyCreditFilePath;
	}

	public String getRepresentativeContactWay() {
		return representativeContactWay;
	}

	public void setRepresentativeContactWay(String representativeContactWay) {
		this.representativeContactWay = representativeContactWay;
	}

	public String getCompanyRegisteredAddress() {
		return companyRegisteredAddress;
	}

	public void setCompanyRegisteredAddress(String companyRegisteredAddress) {
		this.companyRegisteredAddress = companyRegisteredAddress;
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
