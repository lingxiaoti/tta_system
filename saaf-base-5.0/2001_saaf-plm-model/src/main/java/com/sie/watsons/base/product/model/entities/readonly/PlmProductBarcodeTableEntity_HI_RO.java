package com.sie.watsons.base.product.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductBarcodeTableEntity_HI_RO Entity Object
 * Thu Aug 29 10:51:48 CST 2019  Auto Generate
 */

public class PlmProductBarcodeTableEntity_HI_RO {
    private Integer barcodeId;
    private String obId;
    private String barcode;
    private String isMain;
    private Integer productHeadId;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateLogin;
    private Integer operatorUserId;

	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}

	
	public Integer getBarcodeId() {
		return barcodeId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	
	public String getObId() {
		return obId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	
	public String getBarcode() {
		return barcode;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	
	public String getIsMain() {
		return isMain;
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

	public void setLastUpdateLogin(Date lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Date getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
