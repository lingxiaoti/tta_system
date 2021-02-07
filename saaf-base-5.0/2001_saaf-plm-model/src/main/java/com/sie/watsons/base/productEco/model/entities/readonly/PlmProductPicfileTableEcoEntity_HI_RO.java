package com.sie.watsons.base.productEco.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductPicfileTableEcoEntity_HI_RO Entity Object
 * Fri May 22 14:29:40 CST 2020  Auto Generate
 */

public class PlmProductPicfileTableEcoEntity_HI_RO {
    private Integer ecoId;
    private Integer lineId;
    private String acdType;
    private Integer picId;
    private Integer picfileId;
    private String picType;
    private String picName;
    private String picUrl;
    private Integer productHeadId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String supplierId;
    private String flags;
    private String returnReson;
    private String isUpdate;
    private String isRequire;
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

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	
	public Integer getPicId() {
		return picId;
	}

	public void setPicfileId(Integer picfileId) {
		this.picfileId = picfileId;
	}

	
	public Integer getPicfileId() {
		return picfileId;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	
	public String getPicType() {
		return picType;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	
	public String getPicName() {
		return picName;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	
	public String getPicUrl() {
		return picUrl;
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

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	
	public String getReturnReson() {
		return returnReson;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}

	
	public String getIsRequire() {
		return isRequire;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
