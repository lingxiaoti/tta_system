package com.sie.watsons.base.pon.itquotation.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * EquPonQuoInviteFileItEntity_HI Entity Object
 * Tue Dec 17 18:21:57 CST 2019  Auto Generate
 */
@Entity
@Table(name="equ_pon_quo_invite_file_it")
public class EquPonQuoInviteFileItEntity_HI {
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

	public void setQuotationAttachmentId(Integer quotationAttachmentId) {
		this.quotationAttachmentId = quotationAttachmentId;
	}

	@Id	
	@SequenceGenerator(name="EQU_PON_QUO_INVITE_FILE_IT_SEQ", sequenceName="EQU_PON_QUO_INVITE_FILE_IT_SEQ", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="EQU_PON_QUO_INVITE_FILE_IT_SEQ",strategy=GenerationType.SEQUENCE)
	@Column(name="quotation_attachment_id", nullable=false, length=22)	
	public Integer getQuotationAttachmentId() {
		return quotationAttachmentId;
	}

	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}

	@Column(name="quotation_id", nullable=true, length=22)	
	public Integer getQuotationId() {
		return quotationId;
	}

	public void setProjectFileId(Integer projectFileId) {
		this.projectFileId = projectFileId;
	}

	@Column(name="project_file_id", nullable=true, length=22)	
	public Integer getProjectFileId() {
		return projectFileId;
	}

	public void setProjectFileName(String projectFileName) {
		this.projectFileName = projectFileName;
	}

	@Column(name="project_file_name", nullable=true, length=100)	
	public String getProjectFileName() {
		return projectFileName;
	}

	public void setProjectFilePath(String projectFilePath) {
		this.projectFilePath = projectFilePath;
	}

	@Column(name="project_file_path", nullable=true, length=240)	
	public String getProjectFilePath() {
		return projectFilePath;
	}

	public void setQuotationFileId(Integer quotationFileId) {
		this.quotationFileId = quotationFileId;
	}

	@Column(name="quotation_file_id", nullable=true, length=22)	
	public Integer getQuotationFileId() {
		return quotationFileId;
	}

	public void setQuotationFileName(String quotationFileName) {
		this.quotationFileName = quotationFileName;
	}

	@Column(name="quotation_file_name", nullable=true, length=100)	
	public String getQuotationFileName() {
		return quotationFileName;
	}

	public void setQuotationFilePath(String quotationFilePath) {
		this.quotationFilePath = quotationFilePath;
	}

	@Column(name="quotation_file_path", nullable=true, length=240)	
	public String getQuotationFilePath() {
		return quotationFilePath;
	}

	public void setUpdateQuotationFileId(Integer updateQuotationFileId) {
		this.updateQuotationFileId = updateQuotationFileId;
	}

	@Column(name="update_quotation_file_id", nullable=true, length=22)	
	public Integer getUpdateQuotationFileId() {
		return updateQuotationFileId;
	}

	public void setUpdateQuotationFileName(String updateQuotationFileName) {
		this.updateQuotationFileName = updateQuotationFileName;
	}

	@Column(name="update_quotation_file_name", nullable=true, length=100)	
	public String getUpdateQuotationFileName() {
		return updateQuotationFileName;
	}

	public void setUpdateQuotationFilePath(String updateQuotationFilePath) {
		this.updateQuotationFilePath = updateQuotationFilePath;
	}

	@Column(name="update_quotation_file_path", nullable=true, length=240)	
	public String getUpdateQuotationFilePath() {
		return updateQuotationFilePath;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=true, length=30)	
	public String getFileType() {
		return fileType;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=false, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=false, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=false, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=240)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=240)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=240)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=240)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name="attribute5", nullable=true, length=240)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name="attribute6", nullable=true, length=240)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name="attribute7", nullable=true, length=240)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name="attribute8", nullable=true, length=240)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name="attribute9", nullable=true, length=240)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name="attribute10", nullable=true, length=240)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
    @Column(name="attachment_id", nullable=true, length=22)
    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
