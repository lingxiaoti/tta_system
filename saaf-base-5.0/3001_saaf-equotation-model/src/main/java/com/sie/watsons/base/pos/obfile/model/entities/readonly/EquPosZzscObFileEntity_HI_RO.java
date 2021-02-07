package com.sie.watsons.base.pos.obfile.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * EquPosZzscObFileEntity_HI_RO Entity Object
 * Mon Dec 02 17:24:58 CST 2019  Auto Generate
 */

public class EquPosZzscObFileEntity_HI_RO {
    private Integer obFileId;
    private String suppIdnt;
    private String supplierName;
    private Integer supplierId;
    private String fileType;
    private String fileName;
    private String filePath;
    private Integer fileId;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date frDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endData;
    private String mongoId;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String attributeCategory;
    private Integer operatorUserId;

	public void setObFileId(Integer obFileId) {
		this.obFileId = obFileId;
	}

	
	public Integer getObFileId() {
		return obFileId;
	}

	public void setSuppIdnt(String suppIdnt) {
		this.suppIdnt = suppIdnt;
	}

	
	public String getSuppIdnt() {
		return suppIdnt;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
	public String getFileType() {
		return fileType;
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

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	
	public Integer getFileId() {
		return fileId;
	}

	public void setFrDate(Date frDate) {
		this.frDate = frDate;
	}

	
	public Date getFrDate() {
		return frDate;
	}

	public void setEndData(Date endData) {
		this.endData = endData;
	}

	
	public Date getEndData() {
		return endData;
	}

	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	
	public String getMongoId() {
		return mongoId;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
