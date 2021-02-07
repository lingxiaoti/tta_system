package com.sie.watsons.base.pos.qualityAudit.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**  EquPosSupplierQualityAuditEntity_HI_RO.QUERY_SQL
 * EquPosSupplierQualityAuditEntity_HI_RO Entity Object
 * Mon Sep 16 21:43:33 CST 2019  Auto Generate
 */

public class EquPosSupplierQualityAuditEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	//查询供应商质量审核单据
	public  static  final String  QUERY_SQL =
			"SELECT qa.quality_audit_id qualityAuditId\n" +
					"      ,qa.supplier_id supplierId\n" +
					"      ,qa.scene_type sceneType\n" +
					"      ,qa.quality_audit_number qualityAuditNumber\n" +
					"      ,qa.quality_audit_status qualityAuditStatus\n" +
					"      ,qa.qualification_audit_number qualificationAuditNumber\n" +
					"      ,qa.credit_audit_number creditAuditNumber\n" +
					"      ,qa.quality_audit_score qualityAuditScore\n" +
					"      ,qa.quality_audit_result qualityAuditResult\n" +
					"      ,qa.effective_date effectiveDate\n" +
					"      ,qa.quality_audit_date qualityAuditDate\n" +
					"      ,qa.audit_cap_report_id auditCapReportId\n" +
					"      ,qa.audit_cap_report_name auditCapReportName\n" +
					"      ,qa.audit_cap_report_path auditCapReportPath\n" +
					"      ,qa.cap_rectification_summary_id capRectificationSummaryId\n" +
					"      ,qa.cap_rectification_summary_name capRectificationSummaryName\n" +
					"      ,qa.cap_rectification_summary_path capRectificationSummaryPath\n" +
					"      ,qa.remark\n" +
					"      ,qa.dept_code deptCode\n" +
					"      ,qa.version_num versionNum\n" +
					"      ,qa.creation_date creationDate\n" +
					"      ,qa.created_by createdBy\n" +
					"      ,qa.last_updated_by lastUpdatedBy\n" +
					"      ,qa.last_update_date lastUpdateDate\n" +
					"      ,qa.last_update_login lastUpdateLogin\n" +
					"      ,qa.attribute1\n" +
					"      ,qa.attribute2\n" +
					"      ,qa.attribute3\n" +
					"      ,qa.attribute4\n" +
					"      ,qa.attribute5\n" +
					"      ,qa.attribute6\n" +
					"      ,qa.attribute7\n" +
					"      ,qa.attribute8\n" +
					"      ,qa.attribute9\n" +
					"      ,qa.attribute10 \n" +
					"      ,psi.supplier_number supplierNumber\n" +
					"      ,psi.supplier_name supplierName\n" +
					"      ,d.supplier_status supplierStatus\n" +
					"      ,pqi.qualification_status qualificationStatus \n" +
					"      ,pqi.qualification_id qualificationId \n" +
					"      ,sca.credit_audit_score creditAuditScore\n" +
					"      ,sca.credit_audit_resule creditAuditResule\n" +
					"      ,sca.special_results specialResults \n" +
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
					"       END) creditAuditResuleMeaning \n" +
					"FROM   equ_pos_supplier_quality_audit qa\n" +
					"      ,equ_pos_supplier_info          psi\n" +
					"      ,equ_pos_supp_info_with_dept    d\n" +
					"      ,equ_pos_qualification_info     pqi\n" +
					"      ,equ_pos_supplier_credit_audit  sca \n" +
					"WHERE  qa.supplier_id = psi.supplier_id(+)\n" +
					"AND    qa.supplier_id = d.supplier_id(+)\n" +
					"AND    qa.dept_code = d.dept_code(+)\n" +
					"AND    qa.qualification_audit_number = pqi.qualification_number(+) \n" +
					"AND    qa.credit_audit_number = sca.sup_credit_audit_code(+)";

	private Integer qualityAuditId;				//质量审核单据id
    private Integer supplierId;					//供应商id
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
    private String sceneType;						//业务类型
    private String qualityAuditNumber;				//质量审核单据号
    private String qualityAuditStatus;				//质量审核单据状态
    private String qualificationAuditNumber;		//资质审查单号
    private String creditAuditNumber;				//信用审查单号
    private BigDecimal qualityAuditScore;				//质量审核分数
    private String qualityAuditResult;				//质量审核结果
    @JSONField(format="yyyy-MM-dd")
    private Date effectiveDate;						//有效期限
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date qualityAuditDate;						//质量审核日期
    private Integer auditCapReportId;				//审核CAP报告id
    private String auditCapReportName;				//审核CAP报告名称
    private String auditCapReportPath;				//审核CAP报告路径
    private Integer capRectificationSummaryId;		//CAP整改汇总文件ID
	private String capRectificationSummaryName;		//CAP整改汇总文件名称
	private String capRectificationSummaryPath;		//CAP整改汇总文件路径
    private String remark;							//备注
    private String deptCode;						//部门编号
    private Integer operatorUserId;

    private String sceneTypeMeaning;				//业务类型中文
	private String qualityAuditStatusMeaning;		//质量审核单据状态中文
	private String supplierNumber;					//供应商档案号
	private String supplierName;					//供应商名称
	private Integer qualificationId;
	private String qualificationStatus;				//资质审核单据状态
	private String qualificationStatusMeaning;		//资质审核单据状态中文
	private String specialResults;					//信用审核特批结果
	private String creditAuditScore;				//信用审核单据分数
	private String creditAuditResule;				//信用审核单据结果
	private String creditAuditScoreMeaning;			//信用审核单据分数中文
	private String creditAuditResuleMeaning;		//信用审核单据结果中文
	private String qualityAuditResultMeaning;		//质量审核单据结果中文
	private String userFullName;					//创建人
	private String supplierStatus;					//供应商状态

	public void setQualityAuditId(Integer qualityAuditId) {
		this.qualityAuditId = qualityAuditId;
	}

	
	public Integer getQualityAuditId() {
		return qualityAuditId;
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

	public void setQualityAuditNumber(String qualityAuditNumber) {
		this.qualityAuditNumber = qualityAuditNumber;
	}

	
	public String getQualityAuditNumber() {
		return qualityAuditNumber;
	}

	public void setQualityAuditStatus(String qualityAuditStatus) {
		this.qualityAuditStatus = qualityAuditStatus;
	}


	public String getQualityAuditStatus() {
		return qualityAuditStatus;
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

	public void setQualityAuditScore(BigDecimal qualityAuditScore) {
		this.qualityAuditScore = qualityAuditScore;
	}

	
	public BigDecimal getQualityAuditScore() {
		return qualityAuditScore;
	}

	public void setQualityAuditResult(String qualityAuditResult) {
		this.qualityAuditResult = qualityAuditResult;
	}

	
	public String getQualityAuditResult() {
		return qualityAuditResult;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setAuditCapReportId(Integer auditCapReportId) {
		this.auditCapReportId = auditCapReportId;
	}

	
	public Integer getAuditCapReportId() {
		return auditCapReportId;
	}

	public void setAuditCapReportName(String auditCapReportName) {
		this.auditCapReportName = auditCapReportName;
	}

	
	public String getAuditCapReportName() {
		return auditCapReportName;
	}

	public void setAuditCapReportPath(String auditCapReportPath) {
		this.auditCapReportPath = auditCapReportPath;
	}

	
	public String getAuditCapReportPath() {
		return auditCapReportPath;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getRemark() {
		return remark;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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

	public String getQualityAuditStatusMeaning() {
		return qualityAuditStatusMeaning;
	}

	public void setQualityAuditStatusMeaning(String qualityAuditStatusMeaning) {
		this.qualityAuditStatusMeaning = qualityAuditStatusMeaning;
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

	public String getQualityAuditResultMeaning() {
		return qualityAuditResultMeaning;
	}

	public void setQualityAuditResultMeaning(String qualityAuditResultMeaning) {
		this.qualityAuditResultMeaning = qualityAuditResultMeaning;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public Date getQualityAuditDate() {
		return qualityAuditDate;
	}

	public void setQualityAuditDate(Date qualityAuditDate) {
		this.qualityAuditDate = qualityAuditDate;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public Integer getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}
}
