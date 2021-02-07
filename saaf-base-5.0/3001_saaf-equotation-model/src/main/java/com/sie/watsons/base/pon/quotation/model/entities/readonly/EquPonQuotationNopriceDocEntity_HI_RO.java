package com.sie.watsons.base.pon.quotation.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * EquPonQuotationNopriceDocEntity_HI_RO Entity Object
 * Sun Oct 06 15:41:48 CST 2019  Auto Generate
 */

public class EquPonQuotationNopriceDocEntity_HI_RO {

    public static String SELECT_NOPRICE_SQL = "select nd.quotation_noprice_id     quotationNopriceId,\n" +
            "       nd.noprice_file_id          nopriceFileId,\n" +
            "       nd.noprice_file_path               nopriceFilePath,\n" +
            "       nd.noprice_file_name        nopriceFileName,\n" +
            "       nd.update_noprice_file_id   updateNopriceFileId,\n" +
            "       nd.update_noprice_file_path               updateNopriceFilePath,\n" +
            "       nd.update_noprice_file_name updateNopriceFileName,\n" +
            "       pa.file_id                  projectFileId,\n" +
            "       pa.attachment_name          attachmentName,\n" +
            "       pa.file_path                projectFilePath,\n" +
            "       pa.description              description,\n" +
            "       pa.file_name                projectFileName\n" +
            "  from equ_pon_quotation_noprice_doc nd\n" +
            "  inner join equ_pon_project_attachment pa\n" +
            "    on nd.project_noprice_id = pa.attachment_id\n" +
            "   and pa.file_type = 'NonPriceFile'\n" +
            " where 1 = 1\n";

    public static String SELECT_NOPRICE_SELECT_SQL = "select nd.quotation_noprice_id     quotationNopriceId,\n" +
            "       nd.noprice_file_id          nopriceSelectFileId,\n" +
            "       nd.noprice_file_path               nopriceSelectFilePath,\n" +
            "       nd.noprice_file_name        nopriceSelectFileName,\n" +
            "       nd.update_noprice_file_id   updateNopriceSelectFileId,\n" +
            "       nd.update_noprice_file_path               updateNopriceSelectFilePath,\n" +
            "       nd.update_noprice_file_name updateNopriceSelectFileName,\n" +
            "       pa.is_must_reply                  isMustReply,\n" +
            "       pa.file_id                  projectFileId,\n" +
            "       pa.attachment_name          attachmentName,\n" +
            "       pa.file_path                projectFilePath,\n" +
            "       pa.description              description,\n" +
            "       pa.file_name                projectFileName\n" +
            "  from equ_pon_quotation_noprice_doc nd\n" +
            "  inner join equ_pon_project_attachment pa\n" +
            "    on nd.project_noprice_id = pa.attachment_id\n" +
            "   and pa.file_type = 'NonPriceSelectFile'\n" +
            " where 1 = 1\n";

    private String nopriceFilePath;
    private Integer projectFileId;
    private String description;
    private String projectFileName;
    private String attachmentName;
    private String projectFilePath;
    private String nopriceSelectFilePath;
    private String nopriceSelectFileName;
    private Integer updateNopriceSelectFileId;
    private String updateNopriceSelectFilePath;
    private String updateNopriceSelectFileName;
    private Integer nopriceSelectFileId;
    private String isMustReply;

    private Integer quotationNopriceId;
    private Integer quotationId;
    private Integer projectNopriceId;
    private Integer nopriceFileId;
    private Integer updateNopriceFileId;
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
    private String updateNopriceFileName;
    private String fileType;
    private String nopriceFileName;


    public void setQuotationNopriceId(Integer quotationNopriceId) {
        this.quotationNopriceId = quotationNopriceId;
    }


    public Integer getQuotationNopriceId() {
        return quotationNopriceId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }


    public Integer getQuotationId() {
        return quotationId;
    }

    public void setProjectNopriceId(Integer projectNopriceId) {
        this.projectNopriceId = projectNopriceId;
    }


    public Integer getProjectNopriceId() {
        return projectNopriceId;
    }

    public void setNopriceFileId(Integer nopriceFileId) {
        this.nopriceFileId = nopriceFileId;
    }


    public Integer getNopriceFileId() {
        return nopriceFileId;
    }

    public void setUpdateNopriceFileId(Integer updateNopriceFileId) {
        this.updateNopriceFileId = updateNopriceFileId;
    }


    public Integer getUpdateNopriceFileId() {
        return updateNopriceFileId;
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

    public String getUpdateNopriceFileName() {
        return updateNopriceFileName;
    }

    public void setUpdateNopriceFileName(String updateNopriceFileName) {
        this.updateNopriceFileName = updateNopriceFileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getNopriceFileName() {
        return nopriceFileName;
    }

    public void setNopriceFileName(String nopriceFileName) {
        this.nopriceFileName = nopriceFileName;
    }

    public String getNopriceFilePath() {
        return nopriceFilePath;
    }

    public void setNopriceFilePath(String nopriceFilePath) {
        this.nopriceFilePath = nopriceFilePath;
    }

    public Integer getProjectFileId() {
        return projectFileId;
    }

    public void setProjectFileId(Integer projectFileId) {
        this.projectFileId = projectFileId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectFileName() {
        return projectFileName;
    }

    public void setProjectFileName(String projectFileName) {
        this.projectFileName = projectFileName;
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

    public String getNopriceSelectFilePath() {
        return nopriceSelectFilePath;
    }

    public void setNopriceSelectFilePath(String nopriceSelectFilePath) {
        this.nopriceSelectFilePath = nopriceSelectFilePath;
    }

    public String getNopriceSelectFileName() {
        return nopriceSelectFileName;
    }

    public void setNopriceSelectFileName(String nopriceSelectFileName) {
        this.nopriceSelectFileName = nopriceSelectFileName;
    }

    public Integer getUpdateNopriceSelectFileId() {
        return updateNopriceSelectFileId;
    }

    public void setUpdateNopriceSelectFileId(Integer updateNopriceSelectFileId) {
        this.updateNopriceSelectFileId = updateNopriceSelectFileId;
    }

    public String getUpdateNopriceSelectFilePath() {
        return updateNopriceSelectFilePath;
    }

    public void setUpdateNopriceSelectFilePath(String updateNopriceSelectFilePath) {
        this.updateNopriceSelectFilePath = updateNopriceSelectFilePath;
    }

    public String getUpdateNopriceSelectFileName() {
        return updateNopriceSelectFileName;
    }

    public void setUpdateNopriceSelectFileName(String updateNopriceSelectFileName) {
        this.updateNopriceSelectFileName = updateNopriceSelectFileName;
    }

    public String getIsMustReply() {
        return isMustReply;
    }

    public void setIsMustReply(String isMustReply) {
        this.isMustReply = isMustReply;
    }

    public Integer getNopriceSelectFileId() {
        return nopriceSelectFileId;
    }

    public void setNopriceSelectFileId(Integer nopriceSelectFileId) {
        this.nopriceSelectFileId = nopriceSelectFileId;
    }
}
