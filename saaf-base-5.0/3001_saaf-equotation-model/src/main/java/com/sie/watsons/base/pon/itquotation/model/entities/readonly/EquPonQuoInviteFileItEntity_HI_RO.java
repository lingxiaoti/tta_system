package com.sie.watsons.base.pon.itquotation.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * EquPonQuoInviteFileItEntity_HI_RO Entity Object
 * Tue Dec 17 18:21:57 CST 2019  Auto Generate
 */

public class EquPonQuoInviteFileItEntity_HI_RO {
    public static void main(String[] args) {
        System.out.println(EquPonQuoInviteFileItEntity_HI_RO.QUERY_SELECT_SQL);
    }
//    public static final String QUERY_NONSELECT_SQL = "select fi.quotation_attachment_id    quotationAttachmentId,\n" +
//            "       fi.quotation_id               quotationId,\n" +
//            "       pa.attachment_name            attachmentName,\n" +
//            "       pa.description                description,\n" +
//            "       fi.project_file_id            projectFileId,\n" +
//            "       fi.project_file_name          projectFileName,\n" +
//            "       fi.project_file_path          projectFilePath,\n" +
//            "       fi.quotation_file_id          quotationFileId,\n" +
//            "       fi.quotation_file_name        quotationFileName,\n" +
//            "       fi.quotation_file_path        quotationFilePath,\n" +
//            "       fi.update_quotation_file_id   updateQuotationFileId,\n" +
//            "       fi.update_quotation_file_name updateQuotationFileName,\n" +
//            "       fi.update_quotation_file_path updateQuotationFilePath,\n" +
//            "       fi.file_type                  fileType,\n" +
//            "       fi.version_num                versionNum\n" +
//            "  from equ_pon_quo_invite_file_it fi left　join EQU_PON_IT_PROJECT_ATTACHMENT pa\n" +
//            "    on fi.attachment_id = pa.attachment_id\n" +
//            " where fi.file_type = 'nonSelectFile'";

    public static final String QUERY_SELECT_SQL = "select fi.quotation_attachment_id quotationAttachmentId,\n" +
            "       fi.quotation_id quotationId,\n" +
            "       pa.attachment_name            attachmentName,\n" +
            "       pa.description                description,\n" +
            "       pa.is_must_reply              isMustReply, \n" +
            "       fi.project_file_id projectFileId,\n" +
            "       fi.project_file_name projectFileName,\n" +
            "       fi.project_file_path projectFilePath,\n" +
            "       fi.quotation_file_id quotationFileId,\n" +
            "       fi.quotation_file_name quotationFileName,\n" +
            "       fi.quotation_file_path quotationFilePath,\n" +
            "       fi.update_quotation_file_id updateQuotationFileId,\n" +
            "       fi.update_quotation_file_name updateQuotationFileName,\n" +
            "       fi.update_quotation_file_path updateQuotationFilePath,\n" +
            "       fi.file_type fileType,\n" +
            "       fi.version_num versionNum\n" +
            "  from equ_pon_quo_invite_file_it fi left　join EQU_PON_IT_PROJECT_ATTACHMENT pa\n" +
            "    on fi.attachment_id = pa.attachment_id\n" +
            "  where 1 = 1 ";

    private Integer quotationAttachmentId;
    private Integer quotationId;
    private Integer projectFileId;
    private String projectFileName;
    private String projectFilePath;
    private Integer quotationFileId;
    private String quotationFileName;
    private String quotationFilePath;
    private Integer updateQuotationFileId;
    private String updateQuotationFileName;
    private String updateQuotationFilePath;
    private String fileType;
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
    private Integer versionNum;
    private Integer operatorUserId;
    private Integer attachmentId;
    private String attachmentName;
    private String description;
    private String isMustReply;

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setQuotationAttachmentId(Integer quotationAttachmentId) {
		this.quotationAttachmentId = quotationAttachmentId;
	}

	
	public Integer getQuotationAttachmentId() {
		return quotationAttachmentId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	
	public Integer getQuotationId() {
		return quotationId;
	}

	public void setProjectFileId(Integer projectFileId) {
		this.projectFileId = projectFileId;
	}

	
	public Integer getProjectFileId() {
		return projectFileId;
	}

	public void setProjectFileName(String projectFileName) {
		this.projectFileName = projectFileName;
	}

	
	public String getProjectFileName() {
		return projectFileName;
	}

	public void setProjectFilePath(String projectFilePath) {
		this.projectFilePath = projectFilePath;
	}

	
	public String getProjectFilePath() {
		return projectFilePath;
	}

	public void setQuotationFileId(Integer quotationFileId) {
		this.quotationFileId = quotationFileId;
	}

	
	public Integer getQuotationFileId() {
		return quotationFileId;
	}

	public void setQuotationFileName(String quotationFileName) {
		this.quotationFileName = quotationFileName;
	}

	
	public String getQuotationFileName() {
		return quotationFileName;
	}

	public void setQuotationFilePath(String quotationFilePath) {
		this.quotationFilePath = quotationFilePath;
	}

	
	public String getQuotationFilePath() {
		return quotationFilePath;
	}

	public void setUpdateQuotationFileId(Integer updateQuotationFileId) {
		this.updateQuotationFileId = updateQuotationFileId;
	}

	
	public Integer getUpdateQuotationFileId() {
		return updateQuotationFileId;
	}

	public void setUpdateQuotationFileName(String updateQuotationFileName) {
		this.updateQuotationFileName = updateQuotationFileName;
	}

	
	public String getUpdateQuotationFileName() {
		return updateQuotationFileName;
	}

	public void setUpdateQuotationFilePath(String updateQuotationFilePath) {
		this.updateQuotationFilePath = updateQuotationFilePath;
	}

	
	public String getUpdateQuotationFilePath() {
		return updateQuotationFilePath;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getFileType() {
		return fileType;
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

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getIsMustReply() {
		return isMustReply;
	}

	public void setIsMustReply(String isMustReply) {
		this.isMustReply = isMustReply;
	}
}
