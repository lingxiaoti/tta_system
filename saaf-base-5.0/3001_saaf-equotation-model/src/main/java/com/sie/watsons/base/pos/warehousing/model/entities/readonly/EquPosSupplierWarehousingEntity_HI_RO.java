package com.sie.watsons.base.pos.warehousing.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPosSupplierWarehousingEntity_HI_RO Entity Object
 * Mon Sep 16 16:45:35 CST 2019  Auto Generate
 */

public class EquPosSupplierWarehousingEntity_HI_RO {
    private Integer supWarehousingId;
    private String supWarehousingCode;
    private String sceneType;
    private String supplierName;
    private String sceneTypeName;
    private String supWarehousingStatusName;
    private String qualityAuditNumber;
    private String qualityAuditStatus;
    private String processInstanceId;
    private String qualityAuditResult;
    private String creditAuditScore;
    private String csrAuditScore;
    private BigDecimal qualityAuditScore;
    private String procInstId;
    private String csrAuditNumber;
    private String csrAuditStatus;
    private String csrAuditResult;
    private String isExemption;
    private Integer supplierId;
    private String supplierNumber;
    private String qualificationCode;
    private String qualificationStatus;
    private String supCreditAuditCode;
    private String supCreditAuditStatus;
    private String creditAuditResule;
    private String supWarehousingStatus;
    private String userName;
    private String deptCode;
    private Integer qualificationId;
    private Integer supCreditAuditId;
    private Integer supCsrAuditId;
    private Integer supQualityAuditId;
    private String rejectReason;
    private String remark;
    private String supplierType;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date approveDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
    private Integer operatorUserId;

    public void setSupWarehousingId(Integer supWarehousingId) {
        this.supWarehousingId = supWarehousingId;
    }


    public Integer getSupWarehousingId() {
        return supWarehousingId;
    }

    public void setSupWarehousingCode(String supWarehousingCode) {
        this.supWarehousingCode = supWarehousingCode;
    }


    public String getSupWarehousingCode() {
        return supWarehousingCode;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }


    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupWarehousingStatus(String supWarehousingStatus) {
        this.supWarehousingStatus = supWarehousingStatus;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getCreditAuditScore() {
        return creditAuditScore;
    }

    public void setCreditAuditScore(String creditAuditScore) {
        this.creditAuditScore = creditAuditScore;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSupWarehousingStatus() {
        return supWarehousingStatus;
    }

    public void setQualificationId(Integer qualificationId) {
        this.qualificationId = qualificationId;
    }


    public Integer getQualificationId() {
        return qualificationId;
    }

    public void setSupCreditAuditId(Integer supCreditAuditId) {
        this.supCreditAuditId = supCreditAuditId;
    }


    public Integer getSupCreditAuditId() {
        return supCreditAuditId;
    }

    public void setSupCsrAuditId(Integer supCsrAuditId) {
        this.supCsrAuditId = supCsrAuditId;
    }

    public String getQualityAuditNumber() {
        return qualityAuditNumber;
    }

    public void setQualityAuditNumber(String qualityAuditNumber) {
        this.qualityAuditNumber = qualityAuditNumber;
    }

    public String getQualityAuditStatus() {
        return qualityAuditStatus;
    }

    public String getSceneTypeName() {
        return sceneTypeName;
    }

    public void setSceneTypeName(String sceneTypeName) {
        this.sceneTypeName = sceneTypeName;
    }

    public String getSupWarehousingStatusName() {
        return supWarehousingStatusName;
    }

    public void setSupWarehousingStatusName(String supWarehousingStatusName) {
        this.supWarehousingStatusName = supWarehousingStatusName;
    }

    public void setQualityAuditStatus(String qualityAuditStatus) {
        this.qualityAuditStatus = qualityAuditStatus;
    }

    public String getQualityAuditResult() {
        return qualityAuditResult;
    }

    public void setQualityAuditResult(String qualityAuditResult) {
        this.qualityAuditResult = qualityAuditResult;
    }

    public String getCsrAuditNumber() {
        return csrAuditNumber;
    }

    public void setCsrAuditNumber(String csrAuditNumber) {
        this.csrAuditNumber = csrAuditNumber;
    }

    public String getCsrAuditStatus() {
        return csrAuditStatus;
    }

    public void setCsrAuditStatus(String csrAuditStatus) {
        this.csrAuditStatus = csrAuditStatus;
    }

    public String getIsExemption() {
        return isExemption;
    }

    public void setIsExemption(String isExemption) {
        this.isExemption = isExemption;
    }

    public String getCsrAuditResult() {
        return csrAuditResult;
    }

    public void setCsrAuditResult(String csrAuditResult) {
        this.csrAuditResult = csrAuditResult;
    }

    public Integer getSupCsrAuditId() {
        return supCsrAuditId;
    }

    public void setSupQualityAuditId(Integer supQualityAuditId) {
        this.supQualityAuditId = supQualityAuditId;
    }


    public Integer getSupQualityAuditId() {
        return supQualityAuditId;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getQualificationCode() {
        return qualificationCode;
    }

    public void setQualificationCode(String qualificationCode) {
        this.qualificationCode = qualificationCode;
    }

    public String getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(String qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    public String getSupCreditAuditCode() {
        return supCreditAuditCode;
    }

    public void setSupCreditAuditCode(String supCreditAuditCode) {
        this.supCreditAuditCode = supCreditAuditCode;
    }

    public String getSupCreditAuditStatus() {
        return supCreditAuditStatus;
    }

    public void setSupCreditAuditStatus(String supCreditAuditStatus) {
        this.supCreditAuditStatus = supCreditAuditStatus;
    }

    public String getCreditAuditResule() {
        return creditAuditResule;
    }

    public void setCreditAuditResule(String creditAuditResule) {
        this.creditAuditResule = creditAuditResule;
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

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getCsrAuditScore() {
        return csrAuditScore;
    }

    public void setCsrAuditScore(String csrAuditScore) {
        this.csrAuditScore = csrAuditScore;
    }

    public BigDecimal getQualityAuditScore() {
        return qualityAuditScore;
    }

    public void setQualityAuditScore(BigDecimal qualityAuditScore) {
        this.qualityAuditScore = qualityAuditScore;
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

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public static void main(String[] args) {
        System.out.println(QUEERY_WARE_SQL);
    }

//    EquPosSupplierWarehousingEntity_HI_RO.QUEERY_WARE_SQL
    public static String QUEERY_WARE_SQL = "SELECT t.sup_warehousing_id supWarehousingId\n" +
        "      ,t.sup_warehousing_code supWarehousingCode\n" +
        "      ,pi.supplier_name supplierName\n" +
        "      ,t.scene_type sceneType\n" +
        "      ,t.supplier_id supplierId\n" +
        "      ,t.approve_Date approveDate\n" +
        "      ,t.supplier_number supplierNumber\n" +
        "      ,t.sup_warehousing_status supWarehousingStatus\n" +
        "      ,t.qualification_id qualificationId\n" +
        "      ,epq.qualification_number qualificationCode\n" +
        "      ,t.qualification_status qualificationStatus\n" +
        "      ,t.sup_credit_audit_id supCreditAuditId\n" +
        "      ,ca.sup_credit_audit_code supCreditAuditCode\n" +
        "      ,t.sup_credit_audit_status supCreditAuditStatus\n" +
        "      ,t.quality_audit_score qualityAuditScore\n" +
        "      ,t.version_Num versionNum\n" +
        "      ,t.csr_audit_score csrAuditScore\n" +
        "      ,t.credit_Audit_Score creditAuditScore\n" +
        "      ,t.credit_audit_resule AS creditAuditResule\n" +
        "      ,t.sup_csr_audit_id supCsrAuditId\n" +
        "      ,t.sup_quality_audit_id supQualityAuditId\n" +
        "      ,t.reject_reason rejectReason\n" +
        "      ,t.dept_code deptCode\n" +
        "      ,t.remark\n" +
        "      ,t.creation_date creationDate\n" +
        "      ,psq.quality_audit_number qualityAuditNumber\n" +
        "      ,t.quality_audit_status qualityAuditStatus\n" +
        "      ,t.quality_audit_result qualityAuditResult\n" +
        "      ,eps.CSR_AUDIT_NUMBER csrAuditNumber\n" +
        "      ,eps.is_exemption isExemption\n" +
        "      ,t.csr_audit_status csrAuditStatus\n" +
        "      ,t.csr_audit_result csrAuditResult\n" +
        "      ,t.created_by createdBy\n" +
        "      ,d.supplier_type supplierType\n" +
        "FROM   EQU_POS_SUPPLIER_Warehousing   t\n" +
        "      ,equ_pos_supplier_info          pi\n" +
        "      ,EQU_POS_QUALIFICATION_INFO     epq\n" +
        "      ,EQU_POS_SUPPLIER_QUALITY_AUDIT psq\n" +
        "      ,EQU_POS_SUPPLIER_CSR_AUDIT     EPS\n" +
        "      ,EQU_POS_SUPPLIER_CREDIT_AUDIT  CA\n" +
        "      ,equ_pos_supp_info_with_dept D\n" +
        "WHERE  t.supplier_id = pi.supplier_id(+)\n" +
        "AND    epq.qualification_id(+) = t.qualification_id\n" +
        "AND    CA.SUP_CREDIT_AUDIT_ID(+) = T.SUP_CREDIT_AUDIT_ID\n" +
        "AND    T.SUP_CSR_AUDIT_ID = EPS.CSR_AUDIT_ID(+)\n" +
        "AND    T.SUP_QUALITY_AUDIT_ID = psq.quality_audit_id(+)\n" +
        "AND    T.Supplier_Id = d.supplier_id(+)\n" +
        "and    t.dept_code = d.dept_code(+) \n";

//    public static String QUEERY_WARE_SQL = "SELECT t.sup_warehousing_id supWarehousingId\n" +
//            "      ,t.sup_warehousing_code supWarehousingCode\n" +
//            "      ,pi.supplier_name supplierName\n" +
//            "      ,t.scene_type sceneType\n" +
//            "      ,t.supplier_id supplierId\n" +
//            "      ,t.approve_Date approveDate\n" +
//            "      ,t.supplier_number supplierNumber\n" +
//            "      ,t.sup_warehousing_status supWarehousingStatus\n" +
//            "      ,t.qualification_id qualificationId\n" +
//            "      ,epq.qualification_number qualificationCode\n" +
//            "      ,t.qualification_status qualificationStatus\n" +
//            "      ,t.sup_credit_audit_id supCreditAuditId\n" +
//            "      ,ca.sup_credit_audit_code supCreditAuditCode\n" +
//            "      ,t.sup_credit_audit_status supCreditAuditStatus\n" +
//            "      ,t.quality_audit_score qualityAuditScore\n" +
//            "      ,t.version_Num versionNum\n" +
//            "      ,t.csr_audit_score csrAuditScore\n" +
//            "      ,t.credit_Audit_Score creditAuditScore\n" +
//            "      ,t.credit_audit_resule AS creditAuditResule\n" +
//            "      ,t.sup_csr_audit_id supCsrAuditId\n" +
//            "      ,t.sup_quality_audit_id supQualityAuditId\n" +
//            "      ,t.reject_reason rejectReason\n" +
//            "      ,t.dept_code deptCode\n" +
//            "      ,t.remark\n" +
//            "      ,t.creation_date creationDate\n" +
//            "      ,psq.quality_audit_number qualityAuditNumber\n" +
//            "      ,t.quality_audit_status qualityAuditStatus\n" +
//            "      ,t.quality_audit_result qualityAuditResult\n" +
//            "      ,eps.CSR_AUDIT_NUMBER csrAuditNumber\n" +
//            "      ,t.csr_audit_status csrAuditStatus\n" +
//            "      ,t.csr_audit_result csrAuditResult\n" +
//            "      ,t.created_by createdBy\n" +
//            "FROM   EQU_POS_SUPPLIER_Warehousing   t\n" +
//            "      ,equ_pos_supplier_info          pi\n" +
//            "      ,EQU_POS_QUALIFICATION_INFO     epq\n" +
//            "      ,EQU_POS_SUPPLIER_QUALITY_AUDIT psq\n" +
//            "      ,EQU_POS_SUPPLIER_CSR_AUDIT     EPS\n" +
//            "      ,EQU_POS_SUPPLIER_CREDIT_AUDIT  CA\n" +
//            "WHERE  t.supplier_id = pi.supplier_id(+)\n" +
//            "AND    epq.qualification_id(+) = t.qualification_id\n" +
//            "AND    CA.SUP_CREDIT_AUDIT_ID(+) = T.SUP_CREDIT_AUDIT_ID\n" +
//            "AND    T.SUP_CSR_AUDIT_ID = EPS.CSR_AUDIT_ID(+)\n" +
//            "AND    T.SUP_QUALITY_AUDIT_ID = psq.quality_audit_id(+)";
}
