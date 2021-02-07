package com.sie.watsons.base.pon.information.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

/**
 * EquPonQuotationApprovalEntity_HI_RO Entity Object
 * Mon Oct 21 11:34:42 CST 2019  Auto Generate
 */

public class EquPonQuotationApprovalEntity_HI_RO {
    private Integer approvalId;
	private Integer brandTeamUserId;
    private String approvalNumber;
    private String approvalName;
	private String docStatus;
	private String country;
	private Integer fileId;
	private String filePath;
	private String fileName;
	private Clob companyDescription;
	private String majorCustomer;
	private String licenseNum;
	@JSONField(format="yyyy-MM-dd")
	private Date licenseIndate;
	private String tissueCode;
	@JSONField(format="yyyy-MM-dd")
	private Date tissueIndate;
	private String taxCode;
	private String bankPermitNumber;
	private String businessScope;
	@JSONField(format="yyyy-MM-dd")
	private Date establishmentDate;
	private String enrollCapital;
    private Integer count;
    private Integer projectId;
	private Integer standardsId;
	private String scoringStandardName;
    private String projectName;
    private String projectNumber;
    private String approvalStatus;
	private String isUnderway;
    private String approvalType;
    private String orderType;
    private String deptCode;
    private String relevantCatetory;
    private String nodePath;
	private Integer supplierId;
	private BigDecimal ascending;
	private String supplierNumber;
    private String projectCategory;
    private String seriesName;
    private BigDecimal projectPurchaseAmount;
    @JSONField(format="yyyy-MM-dd")
    private Date projectCycleFrom;
    @JSONField(format="yyyy-MM-dd")
    private Date projectCycleTo;
	private String projectCycle;
	@JSONField(format="yyyy-MM-dd")
    private Date quotationDeadline;
    private String scoringStandard;
    private String presentCooperationSupplier;
    private String rejectReason;
    private String remark;
	private String supplierStatus;
	private String supplierType;
	private String supplierShortName;
    private Integer informationid;
    private String informationcode;
	private Integer approvalRele;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date projectCycleTos;

    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
	private BigDecimal firstProductPrice;
	private BigDecimal bargainFirstPrice;
	private BigDecimal score;
	private BigDecimal weighting;
	private String supplierName;
	private String productName;
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
	private Date secondQuotationDeadline;
    private Integer operatorUserId;
	private Integer scoringId;
	private String scoringNumber;
	private String quotationNumber;
	private Integer quotationId;
	private BigDecimal annualSalesQuantity;
	private BigDecimal totalFirstProductPrice;
	private BigDecimal totalBargainFirstPrice;
	private BigDecimal noPriceScore;
	private Integer processInstanceId;

	private String qaUserNumber;
	private String 	iaUserNumber;
	private String securityUserNumber;

	private Integer qaUserId;
	private Integer iaUserId;
	private Integer securityUserId;
	private String terminateFlag;


	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date sendQuotationInvitationDate;
	private String isInvited;
	private String isReplyOnTime;
	private String tenderResult;
	private String quotationFlag;

	private String purchaseCompany;
	private String unionDeptmentName;
	private String unionDeptmentFlag;

	private Integer inspectionListFileId;
	private String inspectionListFileName;
	private String inspectionListFilePath;
	private String sendQuotationResultFlag;

	public void setApprovalId(Integer approvalId) {
		this.approvalId = approvalId;
	}


	public Date getSecondQuotationDeadline() {
		return secondQuotationDeadline;
	}

	public void setSecondQuotationDeadline(Date secondQuotationDeadline) {
		this.secondQuotationDeadline = secondQuotationDeadline;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Clob getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(Clob companyDescription) {
		this.companyDescription = companyDescription;
	}

	public String getMajorCustomer() {
		return majorCustomer;
	}

	public void setMajorCustomer(String majorCustomer) {
		this.majorCustomer = majorCustomer;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public Date getLicenseIndate() {
		return licenseIndate;
	}

	public void setLicenseIndate(Date licenseIndate) {
		this.licenseIndate = licenseIndate;
	}

	public String getTissueCode() {
		return tissueCode;
	}

	public void setTissueCode(String tissueCode) {
		this.tissueCode = tissueCode;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getTissueIndate() {
		return tissueIndate;
	}

	public void setTissueIndate(Date tissueIndate) {
		this.tissueIndate = tissueIndate;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getBankPermitNumber() {
		return bankPermitNumber;
	}

	public void setBankPermitNumber(String bankPermitNumber) {
		this.bankPermitNumber = bankPermitNumber;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public Date getEstablishmentDate() {
		return establishmentDate;
	}

	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public void setEstablishmentDate(Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public String getEnrollCapital() {
		return enrollCapital;
	}

	public void setEnrollCapital(String enrollCapital) {
		this.enrollCapital = enrollCapital;
	}

	public Integer getApprovalId() {
		return approvalId;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public BigDecimal getNoPriceScore() {
		return noPriceScore;
	}

	public void setNoPriceScore(BigDecimal noPriceScore) {
		this.noPriceScore = noPriceScore;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	public Integer getApprovalRele() {
		return approvalRele;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getSupplierShortName() {
		return supplierShortName;
	}

	public void setSupplierShortName(String supplierShortName) {
		this.supplierShortName = supplierShortName;
	}

	public void setApprovalRele(Integer approvalRele) {
		this.approvalRele = approvalRele;
	}

	public Date getSendQuotationInvitationDate() {
		return sendQuotationInvitationDate;
	}

	public void setSendQuotationInvitationDate(Date sendQuotationInvitationDate) {
		this.sendQuotationInvitationDate = sendQuotationInvitationDate;
	}

	public String getIsUnderway() {
		return isUnderway;
	}

	public void setIsUnderway(String isUnderway) {
		this.isUnderway = isUnderway;
	}

	public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIsInvited() {
		return isInvited;
	}

	public void setIsInvited(String isInvited) {
		this.isInvited = isInvited;
	}

	public String getIsReplyOnTime() {
		return isReplyOnTime;
	}

	public void setIsReplyOnTime(String isReplyOnTime) {
		this.isReplyOnTime = isReplyOnTime;
	}

	public String getTenderResult() {
		return tenderResult;
	}

	public void setTenderResult(String tenderResult) {
		this.tenderResult = tenderResult;
	}

	public String getQuotationFlag() {
		return quotationFlag;
	}

	public void setQuotationFlag(String quotationFlag) {
		this.quotationFlag = quotationFlag;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getWeighting() {
		return weighting;
	}

	public void setWeighting(BigDecimal weighting) {
		this.weighting = weighting;
	}

	public String getProjectCycle() {
		return projectCycle;
	}

	public void setProjectCycle(String projectCycle) {
		this.projectCycle = projectCycle;
	}

	public String getApprovalName() {
		return approvalName;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public BigDecimal getAscending() {
		return ascending;
	}

	public void setAscending(BigDecimal ascending) {
		this.ascending = ascending;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getBrandTeamUserId() {
		return brandTeamUserId;
	}

	public void setBrandTeamUserId(Integer brandTeamUserId) {
		this.brandTeamUserId = brandTeamUserId;
	}

	public Integer getStandardsId() {
		return standardsId;
	}

	public void setStandardsId(Integer standardsId) {
		this.standardsId = standardsId;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public BigDecimal getFirstProductPrice() {
		return firstProductPrice;
	}

	public String getQaUserNumber() {
		return qaUserNumber;
	}

	public void setQaUserNumber(String qaUserNumber) {
		this.qaUserNumber = qaUserNumber;
	}

	public String getIaUserNumber() {
		return iaUserNumber;
	}

	public void setIaUserNumber(String iaUserNumber) {
		this.iaUserNumber = iaUserNumber;
	}

	public String getSecurityUserNumber() {
		return securityUserNumber;
	}

	public void setSecurityUserNumber(String securityUserNumber) {
		this.securityUserNumber = securityUserNumber;
	}

	public Integer getQaUserId() {
		return qaUserId;
	}

	public void setQaUserId(Integer qaUserId) {
		this.qaUserId = qaUserId;
	}

	public Integer getIaUserId() {
		return iaUserId;
	}

	public void setIaUserId(Integer iaUserId) {
		this.iaUserId = iaUserId;
	}

	public Integer getSecurityUserId() {
		return securityUserId;
	}

	public void setSecurityUserId(Integer securityUserId) {
		this.securityUserId = securityUserId;
	}

	public void setFirstProductPrice(BigDecimal firstProductPrice) {
		this.firstProductPrice = firstProductPrice;
	}

	public BigDecimal getBargainFirstPrice() {
		return bargainFirstPrice;
	}

	public void setBargainFirstPrice(BigDecimal bargainFirstPrice) {
		this.bargainFirstPrice = bargainFirstPrice;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	
	public String getApprovalType() {
		return approvalType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getQuotationNumber() {
		return quotationNumber;
	}

	public void setQuotationNumber(String quotationNumber) {
		this.quotationNumber = quotationNumber;
	}

	public Integer getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	public BigDecimal getAnnualSalesQuantity() {
		return annualSalesQuantity;
	}

	public void setAnnualSalesQuantity(BigDecimal annualSalesQuantity) {
		this.annualSalesQuantity = annualSalesQuantity;
	}

	public BigDecimal getTotalFirstProductPrice() {
		return totalFirstProductPrice;
	}

	public void setTotalFirstProductPrice(BigDecimal totalFirstProductPrice) {
		this.totalFirstProductPrice = totalFirstProductPrice;
	}

	public BigDecimal getTotalBargainFirstPrice() {
		return totalBargainFirstPrice;
	}

	public void setTotalBargainFirstPrice(BigDecimal totalBargainFirstPrice) {
		this.totalBargainFirstPrice = totalBargainFirstPrice;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	
	public String getDeptCode() {
		return deptCode;
	}

	public void setRelevantCatetory(String relevantCatetory) {
		this.relevantCatetory = relevantCatetory;
	}

	
	public String getRelevantCatetory() {
		return relevantCatetory;
	}

	public void setProjectCategory(String projectCategory) {
		this.projectCategory = projectCategory;
	}

	
	public String getProjectCategory() {
		return projectCategory;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public Date getProjectCycleTos() {
		return projectCycleTos;
	}

	public void setProjectCycleTos(Date projectCycleTos) {
		this.projectCycleTos = projectCycleTos;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setProjectPurchaseAmount(BigDecimal projectPurchaseAmount) {
		this.projectPurchaseAmount = projectPurchaseAmount;
	}

	
	public BigDecimal getProjectPurchaseAmount() {
		return projectPurchaseAmount;
	}

	public void setProjectCycleFrom(Date projectCycleFrom) {
		this.projectCycleFrom = projectCycleFrom;
	}

	
	public Date getProjectCycleFrom() {
		return projectCycleFrom;
	}

	public void setProjectCycleTo(Date projectCycleTo) {
		this.projectCycleTo = projectCycleTo;
	}

	
	public Date getProjectCycleTo() {
		return projectCycleTo;
	}

	public void setQuotationDeadline(Date quotationDeadline) {
		this.quotationDeadline = quotationDeadline;
	}

	
	public Date getQuotationDeadline() {
		return quotationDeadline;
	}

	public void setScoringStandard(String scoringStandard) {
		this.scoringStandard = scoringStandard;
	}

	
	public String getScoringStandard() {
		return scoringStandard;
	}

	public void setPresentCooperationSupplier(String presentCooperationSupplier) {
		this.presentCooperationSupplier = presentCooperationSupplier;
	}

	public Integer getScoringId() {
		return scoringId;
	}

	public void setScoringId(Integer scoringId) {
		this.scoringId = scoringId;
	}

	public String getScoringNumber() {
		return scoringNumber;
	}

	public void setScoringNumber(String scoringNumber) {
		this.scoringNumber = scoringNumber;
	}

	public String getPresentCooperationSupplier() {
		return presentCooperationSupplier;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setInformationid(Integer informationid) {
		this.informationid = informationid;
	}

	
	public Integer getInformationid() {
		return informationid;
	}

	public void setInformationcode(String informationcode) {
		this.informationcode = informationcode;
	}

	
	public String getInformationcode() {
		return informationcode;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

    public Integer getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Integer processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

	public String getTerminateFlag() {
		return terminateFlag;
	}

	public void setTerminateFlag(String terminateFlag) {
		this.terminateFlag = terminateFlag;
	}

	public String getScoringStandardName() {
		return scoringStandardName;
	}

	public void setScoringStandardName(String scoringStandardName) {
		this.scoringStandardName = scoringStandardName;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getPurchaseCompany() {
		return purchaseCompany;
	}

	public void setPurchaseCompany(String purchaseCompany) {
		this.purchaseCompany = purchaseCompany;
	}

	public String getUnionDeptmentName() {
		return unionDeptmentName;
	}

	public void setUnionDeptmentName(String unionDeptmentName) {
		this.unionDeptmentName = unionDeptmentName;
	}

	public String getUnionDeptmentFlag() {
		return unionDeptmentFlag;
	}

	public void setUnionDeptmentFlag(String unionDeptmentFlag) {
		this.unionDeptmentFlag = unionDeptmentFlag;
	}

	public Integer getInspectionListFileId() {
		return inspectionListFileId;
	}

	public void setInspectionListFileId(Integer inspectionListFileId) {
		this.inspectionListFileId = inspectionListFileId;
	}

	public String getInspectionListFileName() {
		return inspectionListFileName;
	}

	public void setInspectionListFileName(String inspectionListFileName) {
		this.inspectionListFileName = inspectionListFileName;
	}

	public String getInspectionListFilePath() {
		return inspectionListFilePath;
	}

	public void setInspectionListFilePath(String inspectionListFilePath) {
		this.inspectionListFilePath = inspectionListFilePath;
	}

	public String getSendQuotationResultFlag() {
		return sendQuotationResultFlag;
	}

	public void setSendQuotationResultFlag(String sendQuotationResultFlag) {
		this.sendQuotationResultFlag = sendQuotationResultFlag;
	}

	public static String FIND_PARTICIPATINGCOUNT_SQL = "SELECT count(1) COUNT\n" +
			"FROM   (SELECT pqa.approval_status\n" +
			"              ,PSI.SUPPLIER_ID\n" +
			"              ,EPP.SOURCE_PROJECT_NUMBER\n" +
			"              ,EPP.PROJECT_ID\n" +
			"        FROM   EQU_PON_QUOTATION_INFO      EPQI\n" +
			"              ,equ_pon_project_info        EPP\n" +
			"              ,EQU_PON_SUPPLIER_INVITATION PSI\n" +
			"              ,EQU_PON_QUOTATION_APPROVAL  pqa\n" +
			"        WHERE  NVL(EPQI.ORDER_TYPE, 10) <> '20'\n" +
			"        AND    EPQI.PROJECT_ID = EPP.PROJECT_ID\n" +
			"        AND    EPP.project_status <> '50'\n" +
			"        AND    PSI.Is_Quit = 'N'\n" +
			"        AND    PSI.Quotation_Flag = 'Y'\n" +
			"        AND    EPP.PROJECT_ID = PSI.PROJECT_ID\n" +
			"        AND    PSI.supplier_id = EPQI.supplier_id\n" +
			"        AND    pqa.Project_Id(+) = EPP.PROJECT_ID) P\n" +
			"WHERE  (P.approval_status NOT IN ('30', '50') OR P.approval_status IS NULL)";

	public static String FIND_IT_PARTICIPATINGCOUNT_SQL = "SELECT count(1) COUNT\n" +
			"FROM   (SELECT pqa.approval_status\n" +
			"              ,PSI.SUPPLIER_ID\n" +
			"              ,EPP.SOURCE_PROJECT_NUMBER\n" +
			"              ,EPP.PROJECT_ID\n" +
			"        FROM   EQU_PON_QUOTATION_INFO_IT      EPQI\n" +
			"              ,equ_pon_IT_project_info        EPP\n" +
			"              ,EQU_PON_IT_SUPPLIER_INVITATION PSI\n" +
			"              ,EQU_PON_QUOTATION_APPROVAL  pqa\n" +
			"        WHERE  NVL(EPQI.ORDER_TYPE, 10) <> '20'\n" +
			"        AND    EPQI.PROJECT_ID = EPP.PROJECT_ID\n" +
			"        AND    EPP.project_status <> '50'\n" +
			"        AND    PSI.Is_Quit = 'N'\n" +
			"        AND    PSI.Quotation_Flag = 'Y'\n" +
			"        AND    EPP.PROJECT_ID = PSI.PROJECT_ID\n" +
			"        AND    PSI.supplier_id = EPQI.supplier_id\n" +
			"        AND    pqa.Project_Id(+) = EPP.PROJECT_ID\n" +
			"        AND    pqa.dept_code <> '0E') P\n" +
			"WHERE  (P.approval_status NOT IN ('30', '50') OR P.approval_status IS NULL)";

    public static String FIND_SELECT10_SQL = "SELECT COUNT(1) count\n" +
            "FROM   EQU_PON_QUOTATION_APPROVAL pqa\n" +
            "      ,equ_pon_project_info       PRI\n" +
            "      ,EQU_PON_QUOTATION_APP_RELE epq\n" +
            "WHERE  pqa.project_id = PRI.Project_Id\n" +
            "AND    pqa.order_type = '10'\n" +
            "and    PRI.project_status <> '50'\n" +
            "and    pqa.approval_status = '30'\n" +
            "and    epq.approval_id = pqa.approval_id";

	public static String FIND_IT_SELECT10_SQL = "SELECT COUNT(1) count\n" +
			"FROM   EQU_PON_QUOTATION_APPROVAL pqa\n" +
			"      ,equ_pon_it_project_info       PRI\n" +
			"      ,EQU_PON_QUOTATION_APP_RELE epq\n" +
			"WHERE  pqa.project_id = PRI.Project_Id\n" +
			"and    pqa.dept_code <> '0E'\n" +
			"AND    pqa.order_type = '10'\n" +
			"and    PRI.project_status <> '50'\n" +
			"and    pqa.approval_status = '30'\n" +
			"and    epq.approval_id = pqa.approval_id";

    public static String FIND_SELECT20_SQL = "SELECT COUNT(1) COUNT\n" +
			"      FROM   EQU_PON_QUOTATION_APPROVAL pqa\n" +
			"            ,equ_pon_project_info       PRI\n" +
			"            ,EQU_PON_QUO_SECOND_RESULT  EQE \n" +
			"WHERE  pqa.project_id = PRI.Project_Id\n" +
			"AND    pqa.order_type = '20'\n" +
			"AND    PQA.approval_status = '30'\n" +
			"AND    PRI.project_status <> '50'\n" +
			"AND    EQE.IS_SELECT = 'Y'\n" +
			"AND    EQE.approval_id = PQA.approval_id";

	public static String FIND_IT_SELECT20_SQL = "SELECT COUNT(1) COUNT\n" +
			"      FROM   EQU_PON_QUOTATION_APPROVAL pqa\n" +
			"            ,equ_pon_it_project_info       PRI\n" +
			"            ,EQU_PON_QUO_SECOND_RESULT  EQE \n" +
			"WHERE  pqa.project_id = PRI.Project_Id\n" +
			"and    pqa.dept_code <> '0E'\n" +
			"AND    pqa.order_type = '20'\n" +
			"AND    PQA.approval_status = '30'\n" +
			"AND    PRI.project_status <> '50'\n" +
			"AND    EQE.IS_SELECT = 'Y'\n" +
			"AND    EQE.approval_id = PQA.approval_id";

    public static String FIND_RESPONSESCOUNT_SQL = "SELECT COUNT(1) COUNT\n" +
			"FROM   EQU_PON_SUPPLIER_INVITATION PSI\n" +
			"      ,equ_pon_project_info        PRI\n" +
			"      ,(SELECT MAX(b.project_version) project_version\n" +
			"              ,b.source_project_number\n" +
			"        FROM   EQU_PON_PROJECT_INFO b\n" +
			"        GROUP  BY b.source_project_number) p\n" +
			"WHERE  PSI.Project_Id = PRI.Project_Id\n" +
			"AND    PSI.QUOTATION_FLAG = 'Y'\n" +
			"AND    PRI.project_version = p.project_version\n" +
			"AND    PRI.source_project_number = p.source_project_number";

	public static String FIND_IT_RESPONSESCOUNT_SQL = "SELECT COUNT(1) COUNT\n" +
			"FROM   EQU_PON_IT_SUPPLIER_INVITATION PSI\n" +
			"      ,equ_pon_it_project_info        PRI\n" +
			"      ,(SELECT MAX(b.project_version) project_version\n" +
			"              ,b.source_project_number\n" +
			"        FROM   EQU_PON_IT_PROJECT_INFO b\n" +
			"        WHERE  B.SEND_QUOTATION_INVITATION_DATE IS NOT NULL\n" +
			"        GROUP  BY b.source_project_number\n" +
			"        UNION\n" +
			"        SELECT MAX(bb.project_version) project_version\n" +
			"              ,bb.source_project_number\n" +
			"        FROM   EQU_PON_IT_PROJECT_INFO bb\n" +
			"        WHERE  bb.source_project_number NOT IN\n" +
			"               (SELECT bc.source_project_number\n" +
			"                FROM   EQU_PON_IT_PROJECT_INFO bc\n" +
			"                WHERE  Bc.SEND_QUOTATION_INVITATION_DATE IS NOT NULL)\n" +
			"        GROUP  BY bb.source_project_number) p\n" +
			"WHERE  PSI.Project_Id = PRI.Project_Id\n" +
			"AND    PSI.QUOTATION_FLAG = 'Y'\n" +
			"AND    PRI.project_version = p.project_version\n" +
			"AND    PRI.source_project_number = p.source_project_number";

    public static String FIND_INVITEDQUOTESCOUNT_SQL = "SELECT COUNT(1) count\n" +
            "FROM   equ_pon_project_info PRI\n" +
            "      ,EQU_PON_SUPPLIER_INVITATION PSI\n" +
            "      ,(SELECT MAX(b.project_version) project_version\n" +
            "              ,b.source_project_number\n" +
            "        FROM   EQU_PON_PROJECT_INFO b\n" +
            "        GROUP  BY b.source_project_number) p\n" +
            "WHERE  PRI.PROJECT_ID = PSI.PROJECT_ID\n" +
            "AND    PSI.INVITE_FLAG = 'Y'\n" +
            "AND    PRI.project_version = p.project_version\n" +
            "AND    PRI.source_project_number = p.source_project_number";

	public static String FIND_IT_INVITEDQUOTESCOUNT_SQL = "SELECT COUNT(1) count\n" +
			"FROM   equ_pon_it_project_info PRI\n" +
			"      ,EQU_PON_it_SUPPLIER_INVITATION PSI\n" +
			"      ,(SELECT MAX(b.project_version) project_version\n" +
			"              ,b.source_project_number\n" +
			"        FROM   EQU_PON_it_PROJECT_INFO b\n" +
			"        GROUP  BY b.source_project_number) p\n" +
			"WHERE  PRI.PROJECT_ID = PSI.PROJECT_ID\n" +
			"AND    PSI.INVITE_FLAG = 'Y'\n" +
			"AND    PRI.project_version = p.project_version\n" +
			"AND    PRI.source_project_number = p.source_project_number";

	public static String FIND_SUPPLIER_SQL ="SELECT psi.supplier_id         supplierId\n" +
			"      ,psi.supplier_number     supplierNumber\n" +
			"      ,psi.supplier_name       supplierName\n" +
			"      ,psi.supplier_short_name supplierShortName\n" +
			"      ,psi.supplier_status     supplierStatus \n" +
			"      ,psi.supplier_type       supplierType\n" +
			"FROM   equ_pos_supplier_info psi WHERE 1 = 1  \n";

	public static String QUERY_BID_STATUS_SQL = "SELECT PRI.PROJECT_ID projectId\n" +
			"      ,PSI.SUPPLIER_ID supplierId\n" +
			"      ,ppi.Order_Type orderType\n" +
			"      ,ppi.QUOTATION_ID\n" +
			"      ,ppi.QUOTATION_NUMBER quotationNumber\n" +
			"      ,PRI.PROJECT_NAME projectName\n" +
			"      ,ppi.supplier_id\n" +
			"      ,ppi.score_project_number\n" +
			"      ,PRI.PROJECT_NUMBER projectNumber\n" +
			"      ,PRI.CREATED_BY createdBy\n" +
			"      ,PRI.PROJECT_CATEGORY projectCategory\n" +
			"      ,PRI.SERIES_NAME seriesName\n" +
			"      ,(SELECT SPRI.CREATION_DATE\n" +
			"        FROM   EQU_PON_PROJECT_INFO SPRI\n" +
			"        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER AND ROWNUM = 1) creationDate\n" +
			"      ,(select spri2.SEND_QUOTATION_INVITATION_DATE\n" +
			"       from   (SELECT SPRI.SEND_QUOTATION_INVITATION_DATE\n" +
			"          FROM   EQU_PON_PROJECT_INFO SPRI\n" +
			"          WHERE  SPRI.Source_Project_Number =\n" +
			"                 PRI.SOURCE_PROJECT_NUMBER\n" +
			"          and    spri.send_quotation_invitation_date is not null\n" +
			"          order  by spri.send_quotation_invitation_date desc) SPRI2\n" +
			"       where  rownum = 1) sendQuotationInvitationDate\n" +
			"       -- PRI.QUOTATION_DEADLINE > SYSDATE\n" +
			"      ,(CASE --供应商拒绝就算未应邀\n" +
			"         WHEN PSI.QUOTATION_FLAG = 'R' THEN\n" +
			"          '否'\n" +
			"         WHEN PSI.Invite_Flag IS NULL\n" +
			"              OR PSI.Invite_Flag = 'N' THEN\n" +
			"          '未发送邀请'\n" +
			"         WHEN PRI.QUOTATION_DEADLINE < SYSDATE\n" +
			"              AND PSI.QUOTATION_FLAG = 'N' --超时并且没有应当就算未应邀\n" +
			"          THEN\n" +
			"          '否'\n" +
			"         WHEN PSI.QUOTATION_FLAG = 'Y' THEN --创建了报价算应邀\n" +
			"          '是'\n" +
			"         ELSE\n" +
			"          ''\n" +
			"       END) AS isInvited\n" +
			"       --是否准时回标\n" +
			"      ,(CASE --供应商拒绝或者退出就算空白\n" +
			"         WHEN PSI.Is_Quit = 'Y' THEN\n" +
			"          ''\n" +
			"       --供应商报价时间比项目截止时间早就算准时回标\n" +
			"         WHEN PRI.QUOTATION_DEADLINE >=\n" +
			"              nvl(ppi.COMMIT_TIME, TO_date('2222-12-22', 'YYYY-MM-DD')) THEN\n" +
			"          '是'\n" +
			"       --还未到报价截止时间,也没有提交时间的显示为空\n" +
			"         WHEN PRI.QUOTATION_DEADLINE >= SYSDATE\n" +
			"              AND ppi.COMMIT_TIME IS NULL THEN\n" +
			"          ''\n" +
			"         WHEN ppi.QUOTATION_ID IS NULL THEN\n" +
			"          ''\n" +
			"       --供应商报价时间比项目截止时间晚就算准未及时应标    \n" +
			"         WHEN PRI.QUOTATION_DEADLINE <\n" +
			"              nvl(ppi.COMMIT_TIME, TO_date('2222-12-22', 'YYYY-MM-DD')) THEN\n" +
			"          '否'\n" +
			"       END) isReplyOnTime\n" +
			"      ,get_Supplier_Bid_Status(PSI.SUPPLIER_ID, PSI.PROJECT_ID,ppi.QUOTATION_ID,nvl(PSI.Is_Quit,'N')) tenderResult\n" +
			"      ,get_Underway_Supplier_Bid(PSI.SUPPLIER_ID,\n" +
			"                                 PSI.PROJECT_ID,\n" +
			"                                 ppi.quotation_id,\n" +
			"                                 PRI.SEND_QUOTATION_INVITATION_DATE) isUnderway\n" +
			"      ,PRI.QUOTATION_DEADLINE quotationDeadline\n" +
			"      ,PSI.QUOTATION_FLAG quotationFlag\n" +
			"FROM   EQU_PON_SUPPLIER_INVITATION PSI\n" +
			"      ,EQU_POS_SUPPLIER_INFO PSSI\n" +
			"      ,EQU_PON_PROJECT_INFO PRI\n" +
			"      ,equ_pon_quotation_info_V ppi\n" +
			"      ,(SELECT MAX(b.project_version) project_version\n" +
			"              ,b.source_project_number\n" +
			"        FROM   EQU_PON_PROJECT_INFO b\n" +
			"        WHERE  B.SEND_QUOTATION_INVITATION_DATE IS NOT NULL\n" +
			"        GROUP  BY b.source_project_number\n" +
			"        UNION\n" +
			"        SELECT MAX(bb.project_version) project_version\n" +
			"              ,bb.source_project_number\n" +
			"        FROM   EQU_PON_PROJECT_INFO bb\n" +
			"        WHERE  bb.source_project_number NOT IN\n" +
			"               (SELECT bc.source_project_number\n" +
			"                FROM   EQU_PON_PROJECT_INFO bc\n" +
			"                WHERE  Bc.SEND_QUOTATION_INVITATION_DATE IS NOT NULL)\n" +
			"        GROUP  BY bb.source_project_number) p\n" +
			"WHERE  PSI.SUPPLIER_ID = PSSI.SUPPLIER_ID\n" +
			"AND    PRI.PROJECT_ID = PSI.PROJECT_ID\n" +
			"AND    PRI.PROJECT_Status <> '50'\n" +
			"AND    ppi.supplier_id(+) = PSI.SUPPLIER_ID\n" +
			"AND    PPI.score_project_number(+) = PRI.SOURCE_PROJECT_NUMBER\n" +
			"AND    PRI.project_version = p.project_version\n" +
			"AND    PRI.source_project_number = p.source_project_number";

	public static String QUERY_IT_BID_STATUS_SQL = "SELECT PRI.PROJECT_ID projectId\n" +
			"      ,PSI.SUPPLIER_ID supplierId\n" +
			"      ,ppi.Order_Type orderType\n" +
			"      ,ppi.QUOTATION_ID\n" +
			"      ,ppi.QUOTATION_NUMBER quotationNumber\n" +
			"      ,PRI.PROJECT_NAME projectName\n" +
			"      ,ppi.supplier_id\n" +
			"      ,ppi.score_project_number\n" +
			"      ,PRI.PROJECT_NUMBER projectNumber\n" +
			"      ,PRI.CREATED_BY createdBy\n" +
			"      ,(SELECT SPRI.CREATION_DATE\n" +
			"        FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
			"        WHERE  SPRI.PROJECT_NUMBER = PRI.SOURCE_PROJECT_NUMBER AND ROWNUM = 1) creationDate\n" +
			"      ,(select spri2.SEND_QUOTATION_INVITATION_DATE\n" +
			"       from   (SELECT SPRI.SEND_QUOTATION_INVITATION_DATE\n" +
			"          FROM   EQU_PON_IT_PROJECT_INFO SPRI\n" +
			"          WHERE  SPRI.Source_Project_Number =\n" +
			"                 PRI.SOURCE_PROJECT_NUMBER\n" +
			"          and    spri.send_quotation_invitation_date is not null\n" +
			"          order  by spri.send_quotation_invitation_date desc) SPRI2\n" +
			"       where  rownum = 1) sendQuotationInvitationDate\n" +
			"       -- PRI.QUOTATION_DEADLINE > SYSDATE\n" +
			"      ,(CASE --供应商拒绝就算未应邀\n" +
			"         WHEN PSI.QUOTATION_FLAG = 'R' THEN\n" +
			"          '否'\n" +
			"         WHEN PSI.Invite_Flag IS NULL\n" +
			"              OR PSI.Invite_Flag = 'N' THEN\n" +
			"          '未发送邀请'\n" +
			"         WHEN PRI.QUOTATION_DEADLINE < SYSDATE\n" +
			"              AND PSI.QUOTATION_FLAG = 'N' --超时并且没有应当就算未应邀\n" +
			"          THEN\n" +
			"          '否'\n" +
			"         WHEN PSI.QUOTATION_FLAG = 'Y' THEN --创建了报价算应邀\n" +
			"          '是'\n" +
			"         ELSE\n" +
			"          ''\n" +
			"       END) AS isInvited\n" +
			"       --是否准时回标\n" +
			"      ,(CASE --供应商拒绝或者退出就算空白\n" +
			"         WHEN PSI.Is_Quit = 'Y' THEN\n" +
			"          ''\n" +
			"       --供应商报价时间比项目截止时间早就算准时回标\n" +
			"         WHEN PRI.QUOTATION_DEADLINE >=\n" +
			"              nvl(ppi.COMMIT_TIME, TO_date('2222-12-22', 'YYYY-MM-DD')) THEN\n" +
			"          '是'\n" +
			"       --还未到报价截止时间,也没有提交时间的显示为空\n" +
			"         WHEN PRI.QUOTATION_DEADLINE >= SYSDATE\n" +
			"              AND ppi.COMMIT_TIME IS NULL THEN\n" +
			"          ''\n" +
			"         WHEN ppi.QUOTATION_ID IS NULL THEN\n" +
			"          ''\n" +
			"       --供应商报价时间比项目截止时间晚就算准未及时应标    \n" +
			"         WHEN PRI.QUOTATION_DEADLINE <\n" +
			"              nvl(ppi.COMMIT_TIME, TO_date('2222-12-22', 'YYYY-MM-DD')) THEN\n" +
			"          '否'\n" +
			"       END) isReplyOnTime\n" +
			"      ,get_It_Supplier_Bid_Status(PSI.SUPPLIER_ID, PSI.PROJECT_ID,ppi.QUOTATION_ID,nvl(PSI.Is_Quit,'N')) tenderResult\n" +
			"      ,get_It_Underway_Supplier_Bid(PSI.SUPPLIER_ID,\n" +
			"                                 PSI.PROJECT_ID,\n" +
			"                                 ppi.quotation_id,\n" +
			"                                 PRI.SEND_QUOTATION_INVITATION_DATE) isUnderway\n" +
			"      ,PRI.QUOTATION_DEADLINE quotationDeadline\n" +
			"      ,PSI.QUOTATION_FLAG quotationFlag\n" +
			"FROM   EQU_PON_IT_SUPPLIER_INVITATION PSI\n" +
			"      ,EQU_POS_SUPPLIER_INFO PSSI\n" +
			"      ,EQU_PON_IT_PROJECT_INFO PRI\n" +
			"      ,equ_pon_quotation_info_V2 ppi\n" +
			"      ,(SELECT MAX(b.project_version) project_version\n" +
			"              ,b.source_project_number\n" +
			"        FROM   EQU_PON_IT_PROJECT_INFO b\n" +
			"        WHERE  B.SEND_QUOTATION_INVITATION_DATE IS NOT NULL\n" +
			"        GROUP  BY b.source_project_number\n" +
			"        UNION\n" +
			"        SELECT MAX(bb.project_version) project_version\n" +
			"              ,bb.source_project_number\n" +
			"        FROM   EQU_PON_IT_PROJECT_INFO bb\n" +
			"        WHERE  bb.source_project_number NOT IN\n" +
			"               (SELECT bc.source_project_number\n" +
			"                FROM   EQU_PON_IT_PROJECT_INFO bc\n" +
			"                WHERE  Bc.SEND_QUOTATION_INVITATION_DATE IS NOT NULL)\n" +
			"        GROUP  BY bb.source_project_number) p\n" +
			"WHERE  PSI.SUPPLIER_ID = PSSI.SUPPLIER_ID\n" +
			"AND    PRI.PROJECT_ID = PSI.PROJECT_ID\n" +
			"AND    ppi.supplier_id(+) = PSI.SUPPLIER_ID\n" +
			"AND    PPI.score_project_number(+) = PRI.SOURCE_PROJECT_NUMBER\n" +
			"AND    PRI.project_version = p.project_version\n" +
			"AND    PRI.source_project_number = p.source_project_number";

	public static void main(String[] args) {
		System.out.println(QUERY_BID_STATUS_SQL);
	}
	public static String QUERY_LIST_SQL = "SELECT t.approval_id                  approvalId\n" +
			"      ,t.BRAND_TEAM_USER_ID           brandTeamUserId\n" +
			"      ,t.approval_number              approvalNumber\n" +
			"      ,t.approval_name                approvalName\n" +
			"      ,t.project_id                   projectId\n" +
			"      ,t.File_id                     fileId\n" +
			"      ,t.file_name                   fileName\n" +
			"      ,t.file_path                   filePath\n" +
			"      ,t.inspection_list_file_id                     inspectionListFileId\n" +
			"      ,t.inspection_list_file_name                   inspectionListFileName\n" +
			"      ,t.inspection_list_file_path                   inspectionListFilePath\n" +
			"      ,t.send_quotation_result_flag                   sendQuotationResultFlag\n" +
			"      ,t.version_Num versionNum\n" +
			"      ,t.project_name                 projectName\n" +
			"      ,t.project_number               projectNumber\n" +
			"      ,t.approval_status              approvalStatus\n" +
			"      ,t.second_Quotation_Deadline secondQuotationDeadline\n" +
			"      ,t.QA_USER_ID qaUserId\n" +
			"      ,t.IA_USER_ID iaUserId\n" +
			"      ,t.SECURITY_USER_ID securityUserId\n" +
			"      ,t.terminate_Flag terminateFlag\n" +
			"      ,t.QA_USER_Number qaUserNumber\n" +
			"      ,t.IA_USER_Number iaUserNumber\n" +
			"      ,t.SECURITY_USER_Number securityUserNumber\n" +
			"      ,t.approval_type                approvalType\n" +
			"      ,t.order_type                   orderType\n" +
			"      ,t.dept_code                    deptCode\n" +
			"      ,t.relevant_catetory            relevantCatetory\n" +
			"      ,t.node_path            nodePath\n" +
			"      ,t.project_category             projectCategory\n" +
			"      ,t.series_name                  seriesName\n" +
			"      ,t.project_purchase_amount      projectPurchaseAmount\n" +
			"      ,t.project_cycle_from           projectCycleFrom\n" +
			"      ,t.project_cycle_to             projectCycleTo\n" +
			"      ,t.quotation_deadline           quotationDeadline\n" +
			"      ,t.scoring_standard             scoringStandard\n" +
			"      ,s.standards_id                 standardsId\n" +
			"      ,s.standards_name                 scoringStandardName\n" +
			"      ,t.present_cooperation_supplier presentCooperationSupplier\n" +
			"      ,t.reject_reason                rejectReason\n" +
			"      ,t.remark\n" +
			"      ,t.scoring_id                   scoringId\n" +
			"      ,t.scoring_Number               scoringNumber\n" +
			"      ,t.information_id               informationId\n" +
			"      ,t.information_code             informationCode\n" +
			"      ,t.creation_date                creationDate\n" +
			"      ,t.created_by                   createdBy\n" +
			"      ,t.purchase_company                   purchaseCompany\n" +
			"      ,t.union_deptment_name                   unionDeptmentName\n" +
			"      ,decode(t.union_deptment_name,'','N','Y')       unionDeptmentFlag\n" +
			"FROM   EQU_PON_QUOTATION_APPROVAL t\n" +
			"      ,EQU_PON_standards_H        S\n" +
			"WHERE  T.SCORING_STANDARD = S.STANDARDS_CODE(+)";

	public static String QUERY_DOUBLE_SQL = "SELECT PQP.FIRST_PRODUCT_PRICE firstProductPrice\n" +
			"      ,PQP.BARGAIN_FIRST_PRICE bargainFirstPrice\n" +
			"      ,round((PQP.BARGAIN_FIRST_PRICE - P.FIRST_PRODUCT_PRICE) /\n" +
			"             P.FIRST_PRODUCT_PRICE,\n" +
			"             4) * 100 ascending\n" +
			"      ,psi.supplier_name supplierName\n" +
			"      ,psi.supplier_id supplierId\n" +
			"      ,PQI.ORDER_TYPE orderType\n" +
			"      ,PQP.Product_Name productName\n" +
			"      ,QA.PROJECT_NUMBER projectNumber\n" +
			"FROM   EQU_PON_QUOTATION_APPROVAL     QA\n" +
			"      ,EQU_PON_QUOTATION_info         PQI\n" +
			"      ,equ_pon_quotation_product_info PQP\n" +
			"      ,equ_pon_quotation_product_info P\n" +
			"      ,equ_pos_supplier_info          psi\n" +
			"WHERE  QA.PROJECT_NUMBER = PQI.PROJECT_NUMBER\n" +
			"AND    PQI.QUOTATION_ID = PQP.QUOTATION_ID\n" +
			"AND    pqi.supplier_id = psi.supplier_id\n" +
			"AND    P.PROJECT_NUMBER = PQP.PROJECT_NUMBER\n" +
			"AND    P.FIRST_PRODUCT_PRICE IS NOT NULL\n" +
			"AND    P.PRODUCT_NAME = PQP.PRODUCT_NAME\n" +
			"AND    P.supplier_id = PQP.supplier_id ";


	public static String QUERY_SCHEDULE_SQL = "SELECT t.information_id informationId\n" +
			"      ,ppi.project_cycle_to projectCycleTo " +
			"FROM   EQU_PON_QUOTATION_INFORMATION T\n" +
			"      ,Equ_Pon_Project_Info          ppi\n" +
			"WHERE  T.PROJECT_ID = ppi.project_id\n" +
			"AND    t.information_status = '20'\n" +
			"AND    t.dept_code = '0E'\n" +
			"AND    NOT EXISTS (SELECT 1\n" +
			"        FROM   equ_pon_witness_team a\n" +
			"        WHERE  a.project_id = ppi.project_id\n" +
			"        AND    nvl(a.ATTRIBUTE1, 'N') <> 'Y')\n";

//	public static String QUERY_IT_SCHEDULE_SQL = "SELECT t.information_id informationId\n" +
//			"FROM   EQU_PON_QUOTATION_INFORMATION T\n" +
//			"WHERE  t.information_status = '20'\n" +
//			"AND    t.dept_code = '03'\n";

//	public static String QUERY_DOU_QUO_SQL = "SELECT psi.supplier_name supplierName\n" +
//			"      ,NVL(de.noPriceScore, 0) noPriceScore\n" +
//			"      ,psi.supplier_id supplierId\n" +
//			"      ,psi.supplier_number supplierNumber\n" +
//			"      ,qp.quotation_number quotationNumber\n" +
//			"      ,qp.quotation_id quotationId\n" +
//			"      ,qp.order_type orderType\n" +
//			"      ,pps.ANNUAL_SALES_QUANTITY annualSalesQuantity\n" +
//			"      ,qpi.first_product_price firstProductPrice\n" +
//			"      ,qpi.BARGAIN_FIRST_PRICE bargainFirstPrice\n" +
//			"      ,(qpi.first_product_price * pps.ANNUAL_SALES_QUANTITY) totalFirstProductPrice\n" +
//			"      ,(qpi.BARGAIN_FIRST_PRICE * pps.ANNUAL_SALES_QUANTITY) totalBargainFirstPrice\n" +
//			"\n" +
//			"FROM   equ_pon_product_specs pps\n" +
//			"      ,equ_pon_project_info ppi\n" +
//			"      ,equ_pon_quotation_product_info qpi\n" +
//			"      ,equ_pon_quotation_info qp\n" +
//			"      ,equ_pos_supplier_info psi\n" +
//			"      ,equ_pon_quotation_approval epa\n" +
//			"      ,(SELECT CD.SUPPLIER_ID supplierId\n" +
//			"              ,CD.SCORING_ID scoringId\n" +
//			"              ,nvl(cd.score, 0) noPriceScore\n" +
//			"              ,qi.project_number projectNumber\n" +
//			"        FROM   EQU_PON_SCORING_INFO          CI\n" +
//			"              ,EQU_PON_QUOTATION_INFORMATION QI\n" +
//			"              ,EQU_PON_SCORING_DETAIL        CD\n" +
//			"        WHERE  CI.INFORMATION_ID = QI.INFORMATION_ID\n" +
//			"        AND    CD.SCORING_ID = CI.SCORING_ID\n" +
//			"        AND    CD.SCORING_TYPE = 'nonPriceCalTotalScore') de\n" +
//			"WHERE  pps.project_id = ppi.project_id\n" +
//			"AND    qpi.project_number = ppi.project_number\n" +
//			"AND    pps.product_name = qpi.product_name\n" +
//			"AND    qpi.supplier_id = psi.supplier_id\n" +
//			"AND    qpi.quotation_id = qp.quotation_id\n" +
//			"AND    qp.doc_status IN ('COMMIT', 'COMPLETE')\n" +
//			"AND    de.projectNumber(+) = qpi.project_number\n" +
//			"AND    de.supplierId(+) = qpi.supplier_Id\n" +
//			"AND    de.scoringId(+) = epa.scoring_Id\n" +
//			"AND    epa.project_id = ppi.project_id\n";

	public static String QUERY_DOU_QUO_SQL = "SELECT psi.supplier_name supplierName\n" +
			"      ,NVL(de.noPriceScore, 0) noPriceScore\n" +
			"      ,psi.supplier_id supplierId\n" +
			"      ,psi.supplier_number supplierNumber\n" +
			"      ,qp.quotation_number quotationNumber\n" +
			"      ,qp.quotation_id quotationId\n" +
			"      ,qp.order_type orderType\n" +
			"      ,pps.ANNUAL_SALES_QUANTITY annualSalesQuantity\n" +
			"      ,qpi.first_product_price firstProductPrice\n" +
			"      ,qpi.BARGAIN_FIRST_PRICE bargainFirstPrice\n" +
			"      ,(qpi.first_product_price * pps.ANNUAL_SALES_QUANTITY) totalFirstProductPrice\n" +
			"      ,(qpi.BARGAIN_FIRST_PRICE * pps.ANNUAL_SALES_QUANTITY) totalBargainFirstPrice\n" +
			"FROM   equ_pon_product_specs pps\n" +
			"      ,equ_pon_project_info ppi\n" +
			"      ,equ_pon_quotation_product_info qpi\n" +
			"      ,equ_pon_quotation_info qp\n" +
			"      ,equ_pos_supplier_info psi\n" +
			"      ,equ_pon_quotation_approval epa\n" +
			"      ,(SELECT CD.SUPPLIER_ID supplierId\n" +
			"              ,CD.SCORING_ID scoringId\n" +
			"              ,nvl(cd.score, 0) noPriceScore \n" +
			"        FROM   EQU_PON_SCORING_INFO          CI \n" +
			"              ,EQU_PON_SCORING_DETAIL        CD\n" +
			"        WHERE  CD.SCORING_ID = CI.SCORING_ID\n" +
			"        AND    CD.SCORING_TYPE = 'nonPriceCalTotalScore') de\n" +
			"WHERE  pps.project_id = ppi.project_id\n" +
			"AND    substr(qp.project_Number, 0, 16) = substr(ppi.project_Number, 0, 16)\n" +
			"AND    pps.product_name = qpi.product_name\n" +
			"AND    qpi.supplier_id = psi.supplier_id\n" +
			"AND    qpi.quotation_id = qp.quotation_id\n" +
			"AND    qp.doc_status IN ('COMMIT', 'COMPLETE') \n" +
			"AND    de.supplierId(+) = qpi.supplier_Id\n" +
			"AND    de.scoringId(+) = epa.scoring_Id\n" +
			"AND    epa.project_id = ppi.project_id\n";

	public static String QUERY_IT_DOU_QUO_SQL = "SELECT psi.supplier_name supplierName\n" +
			"      ,psi.supplier_id supplierId\n" +
			"      ,psi.supplier_number supplierNumber\n" +
			"      ,qii.quotation_number quotationNumber\n" +
			"      ,qii.quotation_id quotationId\n" +
			"      ,qii.order_type orderType\n" +
			"FROM   equ_pon_quotation_info_it qii\n" +
			"      ,equ_pos_supplier_info psi\n" +
			"      ,equ_pon_it_project_info ppi\n" +
			"WHERE  ppi.project_id = qii.project_id\n" +
			"and    qii.supplier_id = psi.supplier_id\n" +
			"AND    qii.doc_status IN ('COMMIT', 'COMPLETE')";

	public static String QUERY_WEIGHT_SQL = "SELECT d.supplier_id supplierId\n" +
			"      ,d.score score\n" +
			"      ,(SELECT T.WEIGHTING\n" +
			"       FROM   equ_pon_scoring_detail T\n" +
			"       WHERE  T.scoring_id = D.SCORING_ID\n" +
			"       AND    T.scoring_type = 'nonPriceCal'\n" +
			"       AND    T.scoring_item = 'Cost'\n" +
			"       AND    T.line_lv = '2'\n" +
			"       AND    ROWNUM = 1) weighting\n" +
			"FROM   equ_pon_scoring_detail d\n" +
			"WHERE  d.scoring_type = 'nonPriceCal'\n" +
			"AND    d.scoring_item = 'Panel test'";

	public static String QUERY_DOU_ABLE_SQL = "SELECT t.supplier_id supplierId , t.supplier_name supplierName\n" +
			"FROM   EQU_PON_QUOTATION_APP_RELE t\n" +
			"WHERE  t.attribute1 = '20'\n" +
			"AND    NOT EXISTS (SELECT 1\n" +
			"        FROM   EQU_PON_QUOTATION_info a\n" +
			"        WHERE  substr(a.project_number, 0, 16) = substr(t.project_number, 0, 16)\n" +
			"        AND    a.supplier_id = t.supplier_id\n" +
			"        AND    a.order_type = '20' and a.doc_status in ('COMMIT','COMPLETE'))";

	public static String QUERY_IT_DOU_ABLE_SQL = "SELECT t.supplier_id supplierId , t.supplier_name supplierName\n" +
			"FROM   EQU_PON_QUOTATION_APP_RELE t\n" +
			"WHERE  t.attribute1 = '20'\n" +
			"AND    NOT EXISTS (SELECT 1\n" +
			"        FROM   equ_pon_quotation_info_it a\n" +
			"        WHERE  substr(a.project_number, 0, 16) = substr(t.project_number, 0, 16)\n" +
			"        AND    a.supplier_id = t.supplier_id\n" +
			"        AND    a.order_type = '20' and a.doc_status in ('COMMIT','COMPLETE'))";

	public static String QUERY_NO_PRICE_SCORE_SQL = "SELECT CD.SUPPLIER_ID supplierId\n"+
			"      ,nvl(cd.score, 0) * 100 noPriceScore\n"+
			"      ,qi.project_number projectNumber\n"+
			"FROM   EQU_PON_SCORING_INFO          CI\n"+
			"      ,EQU_PON_QUOTATION_INFORMATION QI\n"+
			"      ,EQU_PON_SCORING_DETAIL        CD\n"+
			"WHERE  CI.INFORMATION_ID = QI.INFORMATION_ID\n"+
			"AND    CD.SCORING_ID = CI.SCORING_ID\n"+
			"AND    CD.SCORING_TYPE = 'nonPriceCalTotalScore'";

	public static String QUERY_DOU_SQL = "SELECT t.approval_rele approvalRele\n" +
			"      ,t.supplier_id supplierId\n" +
			"      ,t.supplier_number supplierNumber\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.approval_id approvalId\n" +
			"FROM   EQU_PON_QUOTATION_APP_RELE t where 1 = 1";

	public static String QUERY_DOU_SQL2 = "SELECT t.approval_rele approvalRele\n" +
			"      ,t.supplier_id supplierId\n" +
			"      ,t.supplier_number || 'S' supplierNumber\n" +
			"      ,t.created_by createdBy\n" +
			"      ,t.creation_date creationDate\n" +
			"      ,t.approval_id approvalId\n" +
			"FROM   EQU_PON_QUOTATION_APP_RELE t where 1 = 1";

	public static String QUERY_QUOTATION_SQL = "SELECT b.supplier_number supplierNumber\n" +
			"      ,a.doc_status docStatus\n" +
			"FROM   EQU_PON_QUOTATION_INFO a\n" +
			"      ,equ_pos_supplier_info  b\n" +
			"WHERE  a.supplier_id = b.supplier_id and a.order_type = '20'";

	public static String QUERY_QUOTATION_IT_SQL = "SELECT b.supplier_number || 'S' supplierNumber\n" +
			"      ,a.doc_status docStatus\n" +
			"FROM   EQU_PON_QUOTATION_INFO_IT a\n" +
			"      ,equ_pos_supplier_info  b\n" +
			"WHERE  a.supplier_id = b.supplier_id and a.order_type = '20'";

	public static String QUERY_SUPPLIER_SQL = "SELECT A.SUPPLIER_ID         supplierId\n"+
			"      ,B.SUPPLIER_NAME       supplierName\n"+
			"      ,B.SUPPLIER_NUMBER     supplierNumber\n"+
			"      ,A.SUPPLIER_STATUS     supplierStatus\n"+
			"      ,B.COUNTRY             country\n"+
			"      ,A.COMPANY_DESCRIPTION companyDescription\n"+
			"      ,A.SUPPLIER_TYPE       supplierType\n"+
			"      ,A.DEPT_CODE deptCode\n"+
			"      ,A.CREATED_BY          createdBy\n"+
			"      ,A.CREATION_DATE       creationDate\n"+
			"      ,a.last_update_date    lastUpdateDate\n"+
			"      ,a.MAJOR_CUSTOMER      majorCustomer\n"+
			"      ,c.LICENSE_NUM         licenseNum\n"+
			"      ,c.LICENSE_INDATE      licenseIndate\n"+
			"      ,c.TISSUE_CODE         tissueCode\n"+
			"      ,c.TISSUE_INDATE       tissueIndate\n"+
			"      ,c.TAX_CODE            taxCode\n"+
			"      ,c.BANK_PERMIT_NUMBER  bankPermitNumber\n"+
			"      ,c.BUSINESS_SCOPE      businessScope\n"+
			"      ,c.ESTABLISHMENT_DATE  establishmentDate\n"+
			"      ,c.ENROLL_CAPITAL      enrollCapital\n"+
			"FROM   equ_pos_supp_info_with_dept  A\n"+
			"      ,EQU_POS_SUPPLIER_INFO        B\n"+
			"      ,equ_pos_supplier_credentials C\n"+
			"WHERE  a.supplier_id = b.supplier_id\n"+
			"AND    a.supplier_id = c.supplier_id(+) \n"+
			"AND    a.dept_code = c.dept_code(+) ";


	public static String QUERY_DOU_SUPPLIER_SQL = "SELECT t.quotation_id quotationId\n" +
			"      ,T.QUOTATION_NUMBER quotationNumber\n" +
			"      ,T.PROJECT_NUMBER projectNumber\n" +
			"      ,PSI.SUPPLIER_ID supplierId\n" +
			"      ,PSI.SUPPLIER_NUMBER supplierNumber\n" +
			"      ,PSI.SUPPLIER_NAME supplierName\n" +
			"FROM   EQU_PON_QUOTATION_INFO t\n" +
			"      ,EQU_POS_SUPPLIER_INFO  PSI\n" +
			"WHERE  T.order_type = '20'\n" +
			"AND    T.SUPPLIER_ID = PSI.SUPPLIER_ID";

	public static String QUERY_IT_DOU_SUPPLIER_SQL = "SELECT t.quotation_id quotationId\n" +
			"      ,T.QUOTATION_NUMBER quotationNumber\n" +
			"      ,T.PROJECT_NUMBER projectNumber\n" +
			"      ,PSI.SUPPLIER_ID supplierId\n" +
			"      ,PSI.SUPPLIER_NUMBER supplierNumber\n" +
			"      ,PSI.SUPPLIER_NAME supplierName\n" +
			"FROM   EQU_PON_QUOTATION_INFO_IT t\n" +
			"      ,EQU_POS_SUPPLIER_INFO  PSI\n" +
			"WHERE  T.order_type = '20'\n" +
			"AND    T.SUPPLIER_ID = PSI.SUPPLIER_ID";

	public static String QUERY_DOU_SUPPLIER_PRICE_SQL = "SELECT t.quotation_id quotationId\n" +
			"      ,T.QUOTATION_NUMBER quotationNumber\n" +
			"      ,epq.product_name productName\n" +
			"      ,T.PROJECT_NUMBER projectNumber\n" +
			"      ,PSI.SUPPLIER_ID supplierId\n" +
			"      ,epq.BARGAIN_FIRST_PRICE\n" +
			"      ,epq.FIRST_PRODUCT_PRICE\n" +
			"      ,round((epq.BARGAIN_FIRST_PRICE - FF.FIRST_PRODUCT_PRICE) /\n" +
			"             FF.FIRST_PRODUCT_PRICE,\n" +
			"             4) * 100 ascending\n" +
			"      ,PSI.SUPPLIER_NUMBER supplierNumber\n" +
			"      ,PSI.SUPPLIER_NAME supplierName\n" +
			"      ,epq.first_product_price firstProductPrice\n" +
			"      ,epq.bargain_first_price bargainFirstPrice\n" +
			"      ,T.order_type orderType\n" +
			"FROM   EQU_PON_QUOTATION_INFO t\n" +
			"      ,EQU_POS_SUPPLIER_INFO PSI\n" +
			"      ,equ_pon_quotation_product_info epq\n" +
			"      ,(SELECT fepq.FIRST_PRODUCT_PRICE\n" +
			"              ,FT.SUPPLIER_ID\n" +
			"              ,ft.Project_Number\n" +
			"               \n" +
			"              ,Ft.quotation_id\n" +
			"              ,fepq.product_name\n" +
			"        FROM   equ_pon_quotation_product_info fepq\n" +
			"              ,EQU_PON_QUOTATION_INFO         ft\n" +
			"        WHERE  nvl(ft.order_type, '10') = '10'\n" +
			"        AND    FT.QUOTATION_ID = fepq.QUOTATION_ID) FF\n" +
			"WHERE  T.SUPPLIER_ID = PSI.SUPPLIER_ID\n" +
			"AND    epq.quotation_id = t.quotation_id\n" +
			"AND    FF.product_name = epq.product_name\n" +
			"AND    FF.Project_Number = T.Project_Number\n" +
			"AND    FF.SUPPLIER_ID = t.SUPPLIER_ID\n" +
			"AND    FF.SUPPLIER_ID = t.SUPPLIER_ID\n" +
			"AND    EXISTS (SELECT 1\n" +
			"        FROM   EQU_PON_QUOTATION_APP_RELE a\n" +
			"              ,Equ_Pon_Quotation_Approval b\n" +
			"        WHERE  a.approval_id = b.approval_id\n" +
			"        AND    substr(b.PROJECT_NUMBER, 0, 16) = substr(T.PROJECT_NUMBER, 0, 16)\n" +
			"        AND    a.supplier_id = T.SUPPLIER_ID)\n" ;


	public static String QUERY_IT_DOU_SUPPLIER_PRICE_SQL = "SELECT t.quotation_id quotationId\n" +
			"      ,T.QUOTATION_NUMBER quotationNumber\n" +
			"      ,qc.content productName\n" +
			"      ,T.PROJECT_NUMBER projectNumber\n" +
			"      ,PSI.SUPPLIER_ID supplierId\n" +
			"      ,epq.bargain_unit_price * (1 - epq.bargain_tax_rate / 100) * epq.amount * epq.bargain_discount / 100 BARGAIN_FIRST_PRICE\n" +
			"      ,epq.unit_price * (1 - epq.tax_rate / 100) * epq.amount * epq.discount / 100 FIRST_PRODUCT_PRICE\n" +
			"      ,round((epq.bargain_unit_price * (1 - epq.bargain_tax_rate / 100) * epq.amount * epq.bargain_discount / 100 - epq.unit_price * (1 - epq.tax_rate / 100) * epq.amount * epq.discount / 100) / \n" +
			"             (epq.unit_price * (1 - epq.tax_rate / 100) * epq.amount * epq.discount / 100) ,\n" +
			"             4) * 100 ascending\n" +
			"      ,PSI.SUPPLIER_NUMBER supplierNumber\n" +
			"      ,PSI.SUPPLIER_NAME supplierName\n" +
			"      ,T.order_type orderType\n" +
			"FROM   EQU_PON_QUOTATION_INFO_IT t\n" +
			"      ,EQU_POS_SUPPLIER_INFO PSI\n" +
			"      ,equ_pon_quo_content_it epq\n" +
			"      ,equ_pon_it_quotation_content qc\n" +
			"WHERE  T.SUPPLIER_ID = PSI.SUPPLIER_ID\n" +
			"AND    epq.quotation_id = t.quotation_id\n" +
			"and    epq.relevance_id = qc.content_id\n" +
			"and    t.order_type = 20\n" +
			"AND    EXISTS (SELECT 1\n" +
			"        FROM   EQU_PON_QUOTATION_APP_RELE a\n" +
			"              ,Equ_Pon_Quotation_Approval b\n" +
			"        WHERE  a.approval_id = b.approval_id\n" +
			"        AND    substr(b.PROJECT_NUMBER, 0, 16) = substr(T.PROJECT_NUMBER, 0, 16)\n" +
			"        AND    a.supplier_id = T.SUPPLIER_ID)";


}
