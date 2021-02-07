package com.sie.watsons.base.pos.qualityUpdate.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EquPosQualityUpdateLindEntity_HI_RO Entity Object
 * Wed Sep 18 11:40:32 CST 2019  Auto Generate
 */

public class EquPosQualityUpdateLineEntity_HI_RO {

    public static final String QUERY_LINE_SQL = "select t.update_line_id   updateLineId,\n" +
            "       t.update_head_id   updateHeadId,\n" +
            "       t.supplier_number  supplierNumber,\n" +
            "       si.supplier_name   supplierName,\n" +
            "       t.category         category,\n" +
            "       t.score            score,\n" +
            "       t.result           result,\n" +
            "       t.file_id          fileId,\n" +
            "       t.file_name fileName,\n" +
            "       t.file_path        filePath,\n" +
            "       t.begin_valid_date beginValidDate,\n" +
            "       t.end_valid_date   endValidDate,\n" +
            "       t.created_by        createdBy,\n" +
            "       t.last_updated_by  lastUpdatedBy,\n" +
            "       t.category_maintain_id  categoryMaintainId,\n" +
            "       t.last_update_date lastUpdateDate\n" +
            "  from equ_pos_quality_update_line t\n" +
            "  left join equ_pos_supplier_info si\n" +
            "    on si.supplier_number = t.supplier_number\n" +
            " where 1 = 1";
    private String userName;
    private String supplierName;
    private String fileName;
    private String filePath;
    private Integer updateLineId;
    private String supplierNumber;
    private String category;
    private BigDecimal score;
    private String result;
    private Integer fileId;
    @JSONField(format = "yyyy-MM-dd")
    private Date beginValidDate;
    @JSONField(format = "yyyy-MM-dd")
    private Date endValidDate;
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
    private Integer updateHeadId;
    private Integer operatorUserId;
    private Integer categoryMaintainId;

    public void setUpdateLineId(Integer updateLineId) {
        this.updateLineId = updateLineId;
    }


    public Integer getUpdateLineId() {
        return updateLineId;
    }

    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }


    public String getSupplierNumber() {
        return supplierNumber;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getCategory() {
        return category;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }


    public BigDecimal getScore() {
        return score;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getResult() {
        return result;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }


    public Integer getFileId() {
        return fileId;
    }

    public void setBeginValidDate(Date beginValidDate) {
        this.beginValidDate = beginValidDate;
    }


    public Date getBeginValidDate() {
        return beginValidDate;
    }

    public void setEndValidDate(Date endValidDate) {
        this.endValidDate = endValidDate;
    }


    public Date getEndValidDate() {
        return endValidDate;
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

    public void setUpdateHeadId(Integer updateHeadId) {
        this.updateHeadId = updateHeadId;
    }


    public Integer getUpdateHeadId() {
        return updateHeadId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Integer getCategoryMaintainId() {
        return categoryMaintainId;
    }

    public void setCategoryMaintainId(Integer categoryMaintainId) {
        this.categoryMaintainId = categoryMaintainId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
