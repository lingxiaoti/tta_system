package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.product.model.entities.PlmProductPicfileTableEntity_HI;

/**
 * PlmProductPicfileTableEntity_HI_RO Entity Object Thu Aug 29 10:51:51 CST 2019
 * Auto Generate
 */

public class PlmProductPicfileTableEntity_HI_RO implements Comparable {
	private Integer picId;
	private Integer picfileId;
	private String picType;
	private String picName;
	private String picUrl;
	private Integer productHeadId;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer operatorUserId;
	private String supplierId;
	private String isUpdate;
	private String isRequire;

	public String getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public Integer getPicId() {
		return picId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof PlmProductPicfileTableEntity_HI) {
			PlmProductPicfileTableEntity_HI_RO s = (PlmProductPicfileTableEntity_HI_RO) o;
			if (this.picId > s.picId) {
				return 1;
			} else if (this.picId == s.picId) {
				return 0;
			} else {
				return -1;
			}
		}
		return 0;

	}

}
