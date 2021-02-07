package com.sie.watsons.base.productEco.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductQaFileEcoEntity_HI_RO Entity Object
 * Fri May 22 14:29:30 CST 2020  Auto Generate
 */

public class PlmProductQaFileEcoEntity_HI_RO {
    private Integer ecoId;
    private Integer lineId;
    private String acdType;
    private Integer qaId;
    private Integer qaFileId;
    private String qaUrl;
    private String qaFileName;
    private String description;
    private Integer productHeadId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String qaFiletype;
    private String qaCodetype;
    private String qaCode;
    private String dateType;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date datecurent;
    private String supplierId;
    private String flags;
    private String groupId;
    private String groupName;
    private String returnReson;
    private String supplierName;
    private String isSpa;
    private String isUpdate;
    private Integer operatorUserId;

	public void setEcoId(Integer ecoId) {
		this.ecoId = ecoId;
	}

	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	
	public Integer getLineId() {
		return lineId;
	}

	public void setAcdType(String acdType) {
		this.acdType = acdType;
	}

	
	public String getAcdType() {
		return acdType;
	}

	public void setQaId(Integer qaId) {
		this.qaId = qaId;
	}

	
	public Integer getQaId() {
		return qaId;
	}

	public void setQaFileId(Integer qaFileId) {
		this.qaFileId = qaFileId;
	}

	
	public Integer getQaFileId() {
		return qaFileId;
	}

	public void setQaUrl(String qaUrl) {
		this.qaUrl = qaUrl;
	}

	
	public String getQaUrl() {
		return qaUrl;
	}

	public void setQaFileName(String qaFileName) {
		this.qaFileName = qaFileName;
	}

	
	public String getQaFileName() {
		return qaFileName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	
	public Integer getProductHeadId() {
		return productHeadId;
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

	public void setQaFiletype(String qaFiletype) {
		this.qaFiletype = qaFiletype;
	}

	
	public String getQaFiletype() {
		return qaFiletype;
	}

	public void setQaCodetype(String qaCodetype) {
		this.qaCodetype = qaCodetype;
	}

	
	public String getQaCodetype() {
		return qaCodetype;
	}

	public void setQaCode(String qaCode) {
		this.qaCode = qaCode;
	}

	
	public String getQaCode() {
		return qaCode;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	
	public String getDateType() {
		return dateType;
	}

	public void setDatecurent(Date datecurent) {
		this.datecurent = datecurent;
	}

	
	public Date getDatecurent() {
		return datecurent;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	
	public String getSupplierId() {
		return supplierId;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	
	public String getFlags() {
		return flags;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
	public String getGroupName() {
		return groupName;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	
	public String getReturnReson() {
		return returnReson;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setIsSpa(String isSpa) {
		this.isSpa = isSpa;
	}

	
	public String getIsSpa() {
		return isSpa;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
