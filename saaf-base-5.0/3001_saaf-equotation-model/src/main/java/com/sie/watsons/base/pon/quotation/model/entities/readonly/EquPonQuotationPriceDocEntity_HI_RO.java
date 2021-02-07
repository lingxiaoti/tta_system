package com.sie.watsons.base.pon.quotation.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * EquPonQuotationPriceDocEntity_HI_RO Entity Object
 * Sun Oct 06 15:41:49 CST 2019  Auto Generate
 */

public class EquPonQuotationPriceDocEntity_HI_RO {
    public static String SELECT_PRICE_SQL = "select pd.quotation_price_id     quotationPriceId,\n" +
            "       pd.price_file_id          priceFileId,\n" +
            "       pd.quotation_id          quotationId,\n" +
            "       pd.PRICE_FILE_PATH             priceFilePath,\n" +
            "       pd.price_file_name        priceFileName,\n" +
            "       pd.update_price_file_id   updatePriceFileId,\n" +
            "       pd.UPDATE_PRICE_FILE_PATH             updatePriceFilePath,\n" +
            "       pd.update_price_file_name updatePriceFileName,\n" +
            "       pa.file_id                projectFileId,\n" +
            "       pa.attachment_name          attachmentName,\n" +
            "       pa.file_path                projectFilePath,\n" +
            "       pa.description            description,\n" +
            "       pa.file_name              projectFileName\n" +
            "  from EQU_PON_QUOTATION_PRICE_DOC pd\n" +
            "  inner join equ_pon_project_attachment pa\n" +
            "    on pd.project_price_id = pa.attachment_id\n" +
            "   and pa.file_type = 'PriceFile'\n" +
            " where 1 = 1";
    private Integer quotationPriceId;
    private Integer quotationId;
    private Integer projectPriceId;
    private Integer priceFileId;
    private Integer updatePriceFileId;
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
    private String updatePriceFileName;
    private String priceFileName;
    private String attachmentName;
    private String projectFilePath;
    private Integer projectFileId;
    private String priceFilePath;
    private String updatePriceFilePath;
    private String projectFileName;
    private String description;

    public void setQuotationPriceId(Integer quotationPriceId) {
        this.quotationPriceId = quotationPriceId;
    }


    public Integer getQuotationPriceId() {
        return quotationPriceId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }


    public Integer getQuotationId() {
        return quotationId;
    }

    public void setProjectPriceId(Integer projectPriceId) {
        this.projectPriceId = projectPriceId;
    }


    public Integer getProjectPriceId() {
        return projectPriceId;
    }

    public void setPriceFileId(Integer priceFileId) {
        this.priceFileId = priceFileId;
    }


    public Integer getPriceFileId() {
        return priceFileId;
    }

    public void setUpdatePriceFileId(Integer updatePriceFileId) {
        this.updatePriceFileId = updatePriceFileId;
    }


    public Integer getUpdatePriceFileId() {
        return updatePriceFileId;
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

    public String getUpdatePriceFileName() {
        return updatePriceFileName;
    }

    public void setUpdatePriceFileName(String updatePriceFileName) {
        this.updatePriceFileName = updatePriceFileName;
    }

    public String getPriceFileName() {
        return priceFileName;
    }

    public void setPriceFileName(String priceFileName) {
        this.priceFileName = priceFileName;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getProjectFilePath() {
        return projectFilePath;
    }

    public void setProjectFilePath(String projectFilePath) {
        this.projectFilePath = projectFilePath;
    }

    public Integer getProjectFileId() {
        return projectFileId;
    }

    public void setProjectFileId(Integer projectFileId) {
        this.projectFileId = projectFileId;
    }

    public String getPriceFilePath() {
        return priceFilePath;
    }

    public void setPriceFilePath(String priceFilePath) {
        this.priceFilePath = priceFilePath;
    }

    public String getUpdatePriceFilePath() {
        return updatePriceFilePath;
    }

    public void setUpdatePriceFilePath(String updatePriceFilePath) {
        this.updatePriceFilePath = updatePriceFilePath;
    }

    public String getProjectFileName() {
        return projectFileName;
    }

    public void setProjectFileName(String projectFileName) {
        this.projectFileName = projectFileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
