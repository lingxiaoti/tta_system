package com.sie.watsons.base.pos.tempspecial.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * EquPosTempSpecialEntity_HI Entity Object
 * Mon Sep 09 17:48:33 CST 2019  Auto Generate
 */
@Entity
@Table(name = "equ_pos_temp_special")
public class EquPosTempSpecialEntity_HI {
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
    private String fileName;
    private String filePath;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private Integer operatorUserId;
    private Integer fileId;
    private String specialReason;
    private String rejectReason;
    private Integer qualificationId;
    private String deptCode;

    public void setTempSpecialId(Integer tempSpecialId) {
        this.tempSpecialId = tempSpecialId;
    }

    @Id
    @SequenceGenerator(name = "EQU_POS_TEMP_SPECIAL_SEQ", sequenceName = "EQU_POS_TEMP_SPECIAL_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "EQU_POS_TEMP_SPECIAL_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "temp_special_id", nullable = false, length = 22)
    public Integer getTempSpecialId() {
        return tempSpecialId;
    }

    @Column(name = "temp_special_code", nullable = false, length = 30)
    public String getTempSpecialCode() {
        return tempSpecialCode;
    }

    public void setTempSpecialCode(String tempSpecialCode) {
        this.tempSpecialCode = tempSpecialCode;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @Column(name = "supplier_id", nullable = true, length = 22)
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Column(name = "file_Name", nullable = true, length = 240)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    @Column(name = "file_Path", nullable = true, length = 240)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Column(name = "business_type", nullable = true, length = 30)
    public String getBusinessType() {
        return businessType;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    @Column(name = "doc_status", nullable = false, length = 30)
    public String getDocStatus() {
        return docStatus;
    }

    public void setAccessDeptId(Integer accessDeptId) {
        this.accessDeptId = accessDeptId;
    }

    @Column(name = "access_dept_id", nullable = true, length = 22)
    public Integer getAccessDeptId() {
        return accessDeptId;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Version
    @Column(name = "version_num", nullable = false, length = 22)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "creation_date", nullable = false, length = 7)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_by", nullable = false, length = 22)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_updated_by", nullable = false, length = 22)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_date", nullable = false, length = 7)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name = "last_update_login", nullable = true, length = 22)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    @Column(name = "attribute1", nullable = true, length = 240)
    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    @Column(name = "attribute2", nullable = true, length = 240)
    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    @Column(name = "attribute3", nullable = true, length = 240)
    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    @Column(name = "attribute4", nullable = true, length = 240)
    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    @Column(name = "attribute5", nullable = true, length = 240)
    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    @Column(name = "attribute6", nullable = true, length = 240)
    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    @Column(name = "attribute7", nullable = true, length = 240)
    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    @Column(name = "attribute8", nullable = true, length = 240)
    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    @Column(name = "attribute9", nullable = true, length = 240)
    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }

    @Column(name = "attribute10", nullable = true, length = 240)
    public String getAttribute10() {
        return attribute10;
    }

    @Column(name = "special_reason", nullable = true, length = 240)
    public String getSpecialReason() {
        return specialReason;
    }

    public void setSpecialReason(String specialReason) {
        this.specialReason = specialReason;
    }

    @Column(name = "reject_reason", nullable = true, length = 240)
    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Column(name = "qualification_id", nullable = false, length = 22)
    public Integer getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(Integer qualificationId) {
        this.qualificationId = qualificationId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Column(name = "file_id", nullable = true, length = 22)
    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }
    @Column(name="dept_code", nullable=false, length=20)
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }
}
