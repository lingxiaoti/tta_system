package com.sie.watsons.base.pos.qualityUpdate.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

/**
 * EquPosQualityUpdateHeadEntity_HI_RO Entity Object
 * Wed Sep 18 11:18:05 CST 2019  Auto Generate
 */

public class EquPosQualityUpdateHeadEntity_HI_RO {
    //	EquPosQualityUpdateHeadEntity_HI_RO.Query_HEAD_ONLY
    public static final String Query_HEAD_ONLY = "SELECT t.UPDATE_HEAD_ID    updateHeadId\n" +
            "      ,t.update_code       updateCode\n" +
            "      ,t.doc_status        docStatus\n" +
            "      ,t.file_id           fileId\n" +
            "      ,t.file_name fileName\n" +
            "      ,t.file_path        filePath\n" +
            "      ,t.creation_date     creationDate\n" +
            "      ,t.created_by        createdBy\n" +
            "      ,t.dept_id           deptId\n" +
            "      ,t.remark            remark\n" +
            "FROM   equ_pos_quality_update_head t\n" +
            "WHERE  1 = 1";
    //      EquPosQualityUpdateHeadEntity_HI_RO.QUERY_HEAD_SQL
    public static final String QUERY_HEAD_SQL = "SELECT t.UPDATE_HEAD_ID    updateHeadId\n" +
            "      ,l.update_line_id    updateLineId\n" +
            "      ,t.update_code       updateCode\n" +
            "      ,si.supplier_name    supplierName\n" +
            "      ,t.doc_status        docStatus\n" +
            "      ,t.file_id           fileId\n" +
            "      ,t.file_name fileName\n" +
            "      ,t.file_path        filePath\n" +
            "      ,t.creation_date     creationDate\n" +
            "      ,t.created_by        createdBy\n" +
            "      ,t.dept_id           deptId\n" +
            "      ,t.remark            remark\n" +
            "FROM   equ_pos_quality_update_line l\n" +
            "INNER  JOIN equ_pos_quality_update_head t\n" +
            "ON     l.update_head_id = t.update_head_id\n" +
            "INNER  JOIN equ_pos_supplier_info si\n" +
            "ON     si.supplier_number = l.supplier_number\n" +
            "WHERE  1 = 1";

    private List<EquPosQualityUpdateLineEntity_HI_RO> lineRoList;
    private String supplierName;
    private String docStatusMeaning;
    private String userName;
    private String deptName;
    private String fileName;
    private String filePath;
    private Integer updateHeadId;
    private String updateCode;
    private Integer supplierId;
    private String docStatus;
    private Integer fileId;
    private String remark;
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
    private Integer deptId;
    private Integer operatorUserId;
    private String deptCode;
    private Integer updateLineId;

    public Integer getUpdateLineId() {
        return updateLineId;
    }

    public void setUpdateLineId(Integer updateLineId) {
        this.updateLineId = updateLineId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public void setUpdateHeadId(Integer updateHeadId) {
        this.updateHeadId = updateHeadId;
    }


    public Integer getUpdateHeadId() {
        return updateHeadId;
    }

    public void setUpdateCode(String updateCode) {
        this.updateCode = updateCode;
    }


    public String getUpdateCode() {
        return updateCode;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }


    public Integer getSupplierId() {
        return supplierId;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }


    public String getDocStatus() {
        return docStatus;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }


    public Integer getFileId() {
        return fileId;
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

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }


    public Integer getDeptId() {
        return deptId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public List<EquPosQualityUpdateLineEntity_HI_RO> getLineRoList() {
        return lineRoList;
    }

    public void setLineRoList(List<EquPosQualityUpdateLineEntity_HI_RO> lineRoList) {
        this.lineRoList = lineRoList;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDocStatusMeaning() {
        return docStatusMeaning;
    }

    public void setDocStatusMeaning(String docStatusMeaning) {
        this.docStatusMeaning = docStatusMeaning;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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
}
