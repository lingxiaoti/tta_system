package com.sie.watsons.base.supplement.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmSupplementLineEntity_HI Entity Object
 * Mon Sep 23 17:32:30 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_SUPPLEMENT_LINE")
public class PlmSupplementLineEntity_HI {
    private Integer plmSupplementLineId;
    private Integer headId;
    private String rmsCode;
    private String productName;
    private String region;
    private String warehouse;
    private String meter;
    private String pogCode;
    private String store;
    private String priorSupplier;
    private String supplementStatus;
    private String stopReason;
    private String storeType;
    private String sesionProduct;
	@JSONField(format="yyyy-MM-dd")
	private Date expireDate;
	@JSONField(format="yyyy-MM-dd")
    private Date exeDate;
    private String southItemList;
    private String westItemList;
    private String eastItemList;
    private String northItemList;
    private String remarks;
    private Integer createdBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
    private String exeStatus;
    private String expireStatus;
    private String consultProductno;
	@JSONField(format="yyyy-MM-dd")
    private String consultDate;
	@JSONField(format="yyyy-MM-dd")
    private String consultEnddate;

	public void setPlmSupplementLineId(Integer plmSupplementLineId) {
		this.plmSupplementLineId = plmSupplementLineId;
	}

	@Id
	@Column(name="plm_supplement_line_id", nullable=true, length=11)
	@SequenceGenerator(name = "SEQ_PLM_SUPPLEMENT_LINE", sequenceName = "SEQ_PLM_SUPPLEMENT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_SUPPLEMENT_LINE", strategy = GenerationType.SEQUENCE)
	public Integer getPlmSupplementLineId() {
		return plmSupplementLineId;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name="rms_code", nullable=true, length=10)	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="product_name", nullable=true, length=10)	
	public String getProductName() {
		return productName;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name="region", nullable=true, length=10)	
	public String getRegion() {
		return region;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name="warehouse", nullable=true, length=10)	
	public String getWarehouse() {
		return warehouse;
	}

	public void setMeter(String meter) {
		this.meter = meter;
	}

	@Column(name="meter", nullable=true, length=11)
	public String getMeter() {
		return meter;
	}

	public void setPogCode(String pogCode) {
		this.pogCode = pogCode;
	}

	@Column(name="pog_code", nullable=true, length=10)	
	public String getPogCode() {
		return pogCode;
	}

	public void setStore(String store) {
		this.store = store;
	}

	@Column(name="store", nullable=true, length=10)	
	public String getStore() {
		return store;
	}

	public void setPriorSupplier(String priorSupplier) {
		this.priorSupplier = priorSupplier;
	}

	@Column(name="prior_supplier", nullable=true, length=10)	
	public String getPriorSupplier() {
		return priorSupplier;
	}

	public void setSupplementStatus(String supplementStatus) {
		this.supplementStatus = supplementStatus;
	}

	@Column(name="supplement_status", nullable=true, length=11)	
	public String getSupplementStatus() {
		return supplementStatus;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	@Column(name="stop_reason", nullable=true, length=10)	
	public String getStopReason() {
		return stopReason;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name="store_type", nullable=true, length=10)	
	public String getStoreType() {
		return storeType;
	}

	public void setExeDate(Date exeDate) {
		this.exeDate = exeDate;
	}

	@Column(name="exe_date", nullable=true, length=7)
	public Date getExeDate() {
		return exeDate;
	}

	public void setSouthItemList(String southItemList) {
		this.southItemList = southItemList;
	}

	@Column(name="south_item_list", nullable=true, length=10)	
	public String getSouthItemList() {
		return southItemList;
	}

	public void setWestItemList(String westItemList) {
		this.westItemList = westItemList;
	}

	@Column(name="west_item_list", nullable=true, length=10)	
	public String getWestItemList() {
		return westItemList;
	}

	public void setEastItemList(String eastItemList) {
		this.eastItemList = eastItemList;
	}

	@Column(name="east_item_list", nullable=true, length=10)	
	public String getEastItemList() {
		return eastItemList;
	}

	public void setNorthItemList(String northItemList) {
		this.northItemList = northItemList;
	}

	@Column(name="north_item_list", nullable=true, length=10)	
	public String getNorthItemList() {
		return northItemList;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="remarks", nullable=true, length=255)
	public String getRemarks() {
		return remarks;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=11)	
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

	@Column(name="last_update_login", nullable=true, length=11)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=11)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="head_id", nullable=true, length=11)
	public Integer getHeadId() {
		return headId;
	}

	public void setHeadId(Integer headId) {
		this.headId = headId;
	}

	@Column(name="expire_date", nullable=true, length=7)
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Column(name="expire_status", nullable=true, length=50)
	public String getExpireStatus() {
		return expireStatus;
	}

	public void setExpireStatus(String expireStatus) {
		this.expireStatus = expireStatus;
	}

	@Column(name="exe_status", nullable=true, length=50)
	public String getExeStatus() {
		return exeStatus;
	}

	public void setExeStatus(String exeStatus) {
		this.exeStatus = exeStatus;
	}

	@Column(name="consult_productno", nullable=true, length=255)
	public String getConsultProductno() {
		return consultProductno;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	@Column(name="consult_date", nullable=true, length=255)
	public String getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(String consultDate) {
		this.consultDate = consultDate;
	}

	@Column(name="consult_enddate", nullable=true, length=255)
	public String getConsultEnddate() {
		return consultEnddate;
	}

	public void setConsultEnddate(String consultEnddate) {
		this.consultEnddate = consultEnddate;
	}

	@Column(name="sesion_product", nullable=true, length=50)
	public String getSesionProduct() {
		return sesionProduct;
	}

	public void setSesionProduct(String sesionProduct) {
		this.sesionProduct = sesionProduct;
	}
}
