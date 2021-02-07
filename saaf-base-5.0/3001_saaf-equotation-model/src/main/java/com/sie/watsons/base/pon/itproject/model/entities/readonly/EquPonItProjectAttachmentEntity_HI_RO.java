package com.sie.watsons.base.pon.itproject.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * EquPonItProjectAttachmentEntity_HI_RO Entity Object
 * Mon Dec 16 23:18:04 CST 2019  Auto Generate
 */

public class EquPonItProjectAttachmentEntity_HI_RO {
	public static void main(String[] args) {
		System.out.println(QUERY_SQL);
	}
	public  static  final String  QUERY_SQL = "select t.attachment_id      attachmentId\n" +
			"      ,t.project_id         projectId\n" +
			"      ,t.attachment_name    attachmentName\n" +
			"      ,t.description\n" +
			"      ,t.file_id            fileId\n" +
			"      ,t.file_name          fileName\n" +
			"      ,t.file_path          filePath\n" +
			"      ,t.file_type          fileType\n" +
			"      ,t.version_num        versionNum\n" +
			"      ,t.creation_date      creationDate\n" +
			"      ,t.created_by         createdBy\n" +
			"      ,t.last_updated_by    lastUpdatedBy\n" +
			"      ,t.last_update_date   lastUpdateDate\n" +
			"      ,t.last_update_login  lastUpdateLogin\n" +
			"      ,t.attribute_category attributeCategory\n" +
			"      ,t.attribute1\n" +
			"      ,t.attribute2\n" +
			"      ,t.attribute3\n" +
			"      ,t.attribute4\n" +
			"      ,t.attribute5\n" +
			"      ,t.attribute6\n" +
			"      ,t.attribute7\n" +
			"      ,t.attribute8\n" +
			"      ,t.attribute9\n" +
			"      ,t.attribute10\n" +
			"      ,t.selected_flag      selectedFlag\n" +
			"      ,t.is_must_reply isMustReply\n" +
			"      ,t.attachment_id \"index\"\n" +
			"      ,t.source_id          sourceId\n" +
			"from   equ_pon_it_project_attachment t\n" +
			"where  1 = 1\n";
	private Integer attachmentId;
    private Integer projectId;
    private String attachmentName;
    private String description;
    private Integer fileId;
    private String fileName;
    private String filePath;
    private String fileType;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
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
    private String selectedFlag;
    private Integer sourceId;
	private String isMustReply;
	private Integer index;
    private Integer operatorUserId;

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	
	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	public Integer getProjectId() {
		return projectId;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	
	public String getAttachmentName() {
		return attachmentName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	public String getFilePath() {
		return filePath;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getFileType() {
		return fileType;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	
	public String getAttributeCategory() {
		return attributeCategory;
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

	public void setSelectedFlag(String selectedFlag) {
		this.selectedFlag = selectedFlag;
	}

	
	public String getSelectedFlag() {
		return selectedFlag;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	
	public Integer getSourceId() {
		return sourceId;
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

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
