package com.sie.watsons.base.pos.tempspecial.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**  EquPosTempSpecialEntity_HI_RO.QUERY_SQL
 * EquPosTempSpecialEntity_HI_RO Entity Object
 * Mon Sep 09 17:48:33 CST 2019  Auto Generate
 */
public class EquPosTempSpecialEntity_HI_RO {
    public static final String QUERY_SQL = "select ts.temp_special_id      tempSpecialId,\n" +
            "       ts.temp_special_code    tempSpecialCode,\n" +
            "       ts.supplier_id          supplierId,\n" +
            "       si.supplier_number      supplierNumber,\n" +
            "       si.supplier_name        supplierName,\n" +
            "       si.supplier_status      supplierStatus,\n" +
            "       ts.business_type        businessType,\n" +
            "       ts.doc_status           docStatus,\n" +
            "       ts.access_dept_id       accessDeptId,\n" +
            "       ts.dept_code       deptCode,\n" +
            "       ts.creation_date        creationDate,\n" +
            "       ts.created_by           createdBy,\n" +
            "       qi.qualification_number qualificationNumber,\n" +
            "       qi.qualification_status qualificationStatus,\n" +
            "       ts.special_reason       specialReason,\n" +
            "       ts.reject_reason        rejectReason,\n" +
            "       ts.file_id              fileId,\n" +
            "       ts.file_path            filePath,\n" +
            "       ts.file_name     fileName,\n" +
            "       ts.version_num          versionNum\n" +
            "  from equ_pos_temp_special ts\n" +
            "  left join equ_pos_qualification_info qi\n" +
            "    on qi.qualification_id = ts.qualification_id\n" +
            "  left join EQU_POS_SUPPLIER_INFO si\n" +
            "    on ts.supplier_id = si.supplier_id\n" +
            " where 1 = 1";

    public static final String QUERY_FOR_QUALIFICATION = "select ts.temp_special_id   tempSpecialId,\n" +
            "       ts.temp_special_code tempSpecialCode,\n" +
            "       ts.doc_status        docStatus,\n" +
            "       ts.supplier_id       supplierId,\n" +
            "       si.supplier_number   supplierNumber,\n" +
            "       si.supplier_name     supplierName\n" +
            "  from equ_pos_temp_special ts\n" +
            "  left join EQU_POS_SUPPLIER_INFO si\n" +
            "    on ts.supplier_id = si.supplier_id\n" +
            " where 1 = 1\n" +
            "   and ts.doc_status != 'CANCEL'";

    private String supplierStatus;
    private String supplierName;
    private String businessTypeMeaning;
    private String docStatusMeaning;
    private String departmentName;
    private String userName;
    private String supplierNumber;
    private String qualificationStatus;
    private String qualificationNumber;
    private String qualificationStatusMeaning;
    private String rejectReason;
    private String specialReason;
    private Integer fileId;
    private String fileName;
    private String filePath;
    private String procInstId;
    private String deptCode;

    private Integer tempSpecialId;
    private String tempSpecialCode;
    private Integer supplierId;
    private String businessType;
    private String docStatus;
    private Integer accessDeptId;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
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
    private Integer operatorUserId;
    private Integer qualificationId;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getSpecialReason() {
        return specialReason;
    }

    public void setSpecialReason(String specialReason) {
        this.specialReason = specialReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Integer getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(Integer qualificationId) {
        this.qualificationId = qualificationId;
    }

    public void setTempSpecialId(Integer tempSpecialId) {
        this.tempSpecialId = tempSpecialId;
    }


    public Integer getTempSpecialId() {
        return tempSpecialId;
    }

    public String getTempSpecialCode() {
        return tempSpecialCode;
    }

    public void setTempSpecialCode(String tempSpecialCode) {
        this.tempSpecialCode = tempSpecialCode;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }


    public Integer getSupplierId() {
        return supplierId;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }


    public String getBusinessType() {
        return businessType;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }


    public String getDocStatus() {
        return docStatus;
    }

    public void setAccessDeptId(Integer accessDeptId) {
        this.accessDeptId = accessDeptId;
    }


    public Integer getAccessDeptId() {
        return accessDeptId;
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

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public static String getQuerySql() {
        return QUERY_SQL;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBusinessTypeMeaning() {
        return businessTypeMeaning;
    }

    public void setBusinessTypeMeaning(String businessTypeMeaning) {
        this.businessTypeMeaning = businessTypeMeaning;
    }

    public String getDocStatusMeaning() {
        return docStatusMeaning;
    }

    public void setDocStatusMeaning(String docStatusMeaning) {
        this.docStatusMeaning = docStatusMeaning;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public String getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(String qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    public String getQualificationNumber() {
        return qualificationNumber;
    }

    public void setQualificationNumber(String qualificationNumber) {
        this.qualificationNumber = qualificationNumber;
    }

    public String getQualificationStatusMeaning() {
        return qualificationStatusMeaning;
    }

    public void setQualificationStatusMeaning(String qualificationStatusMeaning) {
        this.qualificationStatusMeaning = qualificationStatusMeaning;
    }

    public String getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(String supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
}
