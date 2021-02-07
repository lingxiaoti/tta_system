package com.sie.watsons.base.pos.csrAudit.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**  EquPosSupplierCsrAuditEntity_HI_RO.QUERY_SQL
 * EquPosSupplierCsrAuditEntity_HI_RO Entity Object
 * Tue Sep 17 16:35:50 CST 2019  Auto Generate
 */

public class EquPosSupplierCsrAuditEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	//查询供应商CSR审核单据
	public  static  final String  QUERY_SQL =
			"SELECT csr.csr_audit_id csrAuditId\n" +
					"      ,csr.supplier_id supplierId\n" +
					"      ,csr.scene_type sceneType\n" +
					"      ,csr.csr_audit_number csrAuditNumber\n" +
					"      ,csr.csr_audit_status csrAuditStatus\n" +
					"      ,csr.qualification_audit_number qualificationAuditNumber\n" +
					"      ,csr.credit_audit_number creditAuditNumber\n" +
					"      ,csr.is_exemption isExemption\n" +
					"      ,csr.csr_audit_score csrAuditScore\n" +
					"      ,csr.csr_audit_result csrAuditResult\n" +
					"      ,csr.effective_date effectiveDate\n" +
					"      ,csr.csr_report_id csrReportId\n" +
					"      ,csr.csr_report_name csrReportName\n" +
					"      ,csr.csr_report_path csrCapReportPath\n" +
					"      ,csr.remark\n" +
					"      ,csr.dept_code deptCode\n" +
					"      ,csr.cap_rectification_summary_id capRectificationSummaryId\n" +
					"      ,csr.cap_rectification_summary_name capRectificationSummaryName\n" +
					"      ,csr.cap_rectification_summary_path capRectificationSummaryPath\n" +
					"      ,csr.version_num versionNum\n" +
					"      ,csr.creation_date creationDate\n" +
					"      ,csr.created_by createdBy\n" +
					"      ,csr.last_updated_by lastUpdatedBy\n" +
					"      ,csr.last_update_date lastUpdateDate\n" +
					"      ,csr.last_update_login lastUpdateLogin\n" +
					"      ,csr.attribute1\n" +
					"      ,csr.attribute2\n" +
					"      ,csr.attribute3\n" +
					"      ,csr.attribute4\n" +
					"      ,csr.attribute5\n" +
					"      ,csr.attribute6\n" +
					"      ,csr.attribute7\n" +
					"      ,csr.attribute8\n" +
					"      ,csr.attribute9\n" +
					"      ,csr.attribute10\n" +
					"      ,psi.supplier_number supplierNumber\n" +
					"      ,psi.supplier_name supplierName\n" +
					"      ,psi.supplier_status supplierStatus\n" +
					"      ,pqi.qualification_status qualificationStatus\n" +
					"      ,pqi.qualification_id qualificationId\n" +
					"      ,sca.credit_audit_score creditAuditScore\n" +
					"      ,sca.credit_audit_resule creditAuditResule\n" +
					"      ,sca.special_results specialResults\n" +
					"      ,(CASE\n" +
					"         WHEN sca.credit_audit_resule = 'Y' THEN\n" +
					"          '合格'\n" +
					"         WHEN sca.credit_audit_resule = 'N'\n" +
					"              AND sca.special_results = 'Y' THEN\n" +
					"          '特批合格'\n" +
					"         WHEN sca.credit_audit_resule = 'N'\n" +
					"              AND sca.special_results = 'N' THEN\n" +
					"          '特批不合格'\n" +
					"         ELSE\n" +
					"          ''\n" +
					"       END) creditAuditResuleMeaning\n" +
					"FROM   equ_pos_supplier_csr_audit    csr\n" +
					"      ,equ_pos_supplier_info         psi\n" +
					"      ,equ_pos_qualification_info    pqi\n" +
					"      ,equ_pos_supplier_credit_audit sca\n" +
					"WHERE  csr.supplier_id = psi.supplier_id(+)\n" +
					"AND    csr.qualification_audit_number = pqi.qualification_number(+)\n" +
					"      \n" +
					"AND    csr.credit_audit_number = sca.sup_credit_audit_code(+)\n";

	private Integer csrAuditId;
    private Integer supplierId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
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
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private String sceneType;					//业务类型
    private String csrAuditNumber;				//CSR审查单据编号
    private String csrAuditStatus;				//CSR审查状态
    private String qualificationAuditNumber;	//资质审查单据编号
    private String creditAuditNumber;			//信用审查单据编号
    private String isExemption;					//是否豁免
    private String csrAuditScore;				//CSR审核分数
    private String csrAuditResult;				//CSR审核结果
    @JSONField(format="yyyy-MM-dd")
    private Date effectiveDate;					//有效期限
    private Integer csrReportId;				//CSR报告id
    private String csrReportName;				//CSR报告名称
    private String csrReportPath;				//CSR报告路径
    private String remark;						//备注
    private String deptCode;					//部门
	private Integer capRectificationSummaryId;		//CAP整改汇总文件ID
	private String capRectificationSummaryName;		//CAP整改汇总文件名称
	private String capRectificationSummaryPath;		//CAP整改汇总文件路径
    private Integer operatorUserId;

	private String sceneTypeMeaning;			//业务类型中文
	private String csrAuditStatusMeaning;	//CSR审核单据状态中文
	private String supplierNumber;				//供应商档案号
	private String supplierName;				//供应商名称
	private String supplierStatus;				//供应商状态
	private Integer qualificationId;
	private String qualificationStatus;			//资质审核单据状态
	private String qualificationStatusMeaning;  //资质审核单据状态中文
	private String creditAuditScore;			//信用审核分数
	private String creditAuditResule;			//信用审核结果
	private String specialResults;				//特批结果
	private String creditAuditScoreMeaning;		//信用审核分数中文
	private String creditAuditResuleMeaning;	//信用审核结果中文
	private String csrAuditResultMeaning;	    //CSR审核结果中文
	private String userFullName;				//创建人

	public void setCsrAuditId(Integer csrAuditId) {
		this.csrAuditId = csrAuditId;
	}

	
	public Integer getCsrAuditId() {
		return csrAuditId;
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

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	
	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	
	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	
	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	
	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	
	public String getAttribute15() {
		return attribute15;
	}

	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}

	
	public String getSceneType() {
		return sceneType;
	}

	public void setCsrAuditNumber(String csrAuditNumber) {
		this.csrAuditNumber = csrAuditNumber;
	}

	
	public String getCsrAuditNumber() {
		return csrAuditNumber;
	}

	public void setCsrAuditStatus(String csrAuditStatus) {
		this.csrAuditStatus = csrAuditStatus;
	}

	
	public String getCsrAuditStatus() {
		return csrAuditStatus;
	}

	public void setQualificationAuditNumber(String qualificationAuditNumber) {
		this.qualificationAuditNumber = qualificationAuditNumber;
	}

	
	public String getQualificationAuditNumber() {
		return qualificationAuditNumber;
	}

	public void setCreditAuditNumber(String creditAuditNumber) {
		this.creditAuditNumber = creditAuditNumber;
	}

	
	public String getCreditAuditNumber() {
		return creditAuditNumber;
	}

	public void setIsExemption(String isExemption) {
		this.isExemption = isExemption;
	}

	
	public String getIsExemption() {
		return isExemption;
	}

	public void setCsrAuditScore(String csrAuditScore) {
		this.csrAuditScore = csrAuditScore;
	}

	
	public String getCsrAuditScore() {
		return csrAuditScore;
	}

	public void setCsrAuditResult(String csrAuditResult) {
		this.csrAuditResult = csrAuditResult;
	}

	
	public String getCsrAuditResult() {
		return csrAuditResult;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setCsrReportId(Integer csrReportId) {
		this.csrReportId = csrReportId;
	}

	
	public Integer getCsrReportId() {
		return csrReportId;
	}

	public void setCsrReportName(String csrReportName) {
		this.csrReportName = csrReportName;
	}

	
	public String getCsrReportName() {
		return csrReportName;
	}

	public void setCsrReportPath(String csrReportPath) {
		this.csrReportPath = csrReportPath;
	}

	
	public String getCsrReportPath() {
		return csrReportPath;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public String getRemark() {
		return remark;
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

	public String getSceneTypeMeaning() {
		return sceneTypeMeaning;
	}

	public void setSceneTypeMeaning(String sceneTypeMeaning) {
		this.sceneTypeMeaning = sceneTypeMeaning;
	}

	public String getCsrAuditStatusMeaning() {
		return csrAuditStatusMeaning;
	}

	public void setCsrAuditStatusMeaning(String csrAuditStatusMeaning) {
		this.csrAuditStatusMeaning = csrAuditStatusMeaning;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getQualificationStatus() {
		return qualificationStatus;
	}

	public void setQualificationStatus(String qualificationStatus) {
		this.qualificationStatus = qualificationStatus;
	}

	public String getQualificationStatusMeaning() {
		return qualificationStatusMeaning;
	}

	public void setQualificationStatusMeaning(String qualificationStatusMeaning) {
		this.qualificationStatusMeaning = qualificationStatusMeaning;
	}

	public String getCreditAuditScore() {
		return creditAuditScore;
	}

	public void setCreditAuditScore(String creditAuditScore) {
		this.creditAuditScore = creditAuditScore;
	}

	public String getCreditAuditResule() {
		return creditAuditResule;
	}

	public void setCreditAuditResule(String creditAuditResule) {
		this.creditAuditResule = creditAuditResule;
	}

	public String getCreditAuditScoreMeaning() {
		return creditAuditScoreMeaning;
	}

	public void setCreditAuditScoreMeaning(String creditAuditScoreMeaning) {
		this.creditAuditScoreMeaning = creditAuditScoreMeaning;
	}

	public String getCreditAuditResuleMeaning() {
		return creditAuditResuleMeaning;
	}

	public void setCreditAuditResuleMeaning(String creditAuditResuleMeaning) {
		this.creditAuditResuleMeaning = creditAuditResuleMeaning;
	}

	public String getCsrAuditResultMeaning() {
		return csrAuditResultMeaning;
	}

	public void setCsrAuditResultMeaning(String csrAuditResultMeaning) {
		this.csrAuditResultMeaning = csrAuditResultMeaning;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getSpecialResults() {
		return specialResults;
	}

	public void setSpecialResults(String specialResults) {
		this.specialResults = specialResults;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public Integer getCapRectificationSummaryId() {
		return capRectificationSummaryId;
	}

	public void setCapRectificationSummaryId(Integer capRectificationSummaryId) {
		this.capRectificationSummaryId = capRectificationSummaryId;
	}

	public String getCapRectificationSummaryName() {
		return capRectificationSummaryName;
	}

	public void setCapRectificationSummaryName(String capRectificationSummaryName) {
		this.capRectificationSummaryName = capRectificationSummaryName;
	}

	public String getCapRectificationSummaryPath() {
		return capRectificationSummaryPath;
	}

	public void setCapRectificationSummaryPath(String capRectificationSummaryPath) {
		this.capRectificationSummaryPath = capRectificationSummaryPath;
	}

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}
}
