package com.sie.watsons.base.elecsign.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * TtaElecConAttrLineEntity_HI_RO Entity Object
 * Mon Mar 30 17:14:25 CST 2020  Auto Generate
 */

public class TtaElecConAttrLineEntity_HI_RO {
    private Integer elecConAttrLineId;
    private Integer elecConHeaderId;
    private Integer conAttrLineId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer fileId; //添加水印后的附件id
	private String fileUrl; //添加水印后的附件url
	private String fileName;


	public void setElecConAttrLineId(Integer elecConAttrLineId) {
		this.elecConAttrLineId = elecConAttrLineId;
	}

	
	public Integer getElecConAttrLineId() {
		return elecConAttrLineId;
	}

	public void setElecConHeaderId(Integer elecConHeaderId) {
		this.elecConHeaderId = elecConHeaderId;
	}

	
	public Integer getElecConHeaderId() {
		return elecConHeaderId;
	}

	public void setConAttrLineId(Integer conAttrLineId) {
		this.conAttrLineId = conAttrLineId;
	}

	
	public Integer getConAttrLineId() {
		return conAttrLineId;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
