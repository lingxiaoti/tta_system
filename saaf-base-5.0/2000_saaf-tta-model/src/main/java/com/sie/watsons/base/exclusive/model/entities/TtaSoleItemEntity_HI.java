package com.sie.watsons.base.exclusive.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * TtaSoleItemEntity_HI Entity Object
 * Tue Jun 25 10:44:39 CST 2019  Auto Generate
 */
@Entity
@Table(name="TTA_SOLE_ITEM")
public class TtaSoleItemEntity_HI {
    private Integer soleItemId;//主键
    private Integer soleAgrtInfoId;//外键
    private String groupCode;//GROUP
	private String groupName;
    private String brandCn;//品牌
    private String barCode;//货品条码
    private String itemCode;//item编码
    private String itemName;//item名称
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date effectiveDate;//生效时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;//失效时间
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private Integer soleAgrtHId;//表tta_sole_agrt的外鍵

	private String vendorNbr;
	private String vendorName;
	private Integer deptCode;
	private String deptDesc;
	private String brandEn;

	public void setSoleItemId(Integer soleItemId) {
		this.soleItemId = soleItemId;
	}

	@Id
	@SequenceGenerator(name="SEQ_TTA_SOLE_ITEM", sequenceName="SEQ_TTA_SOLE_ITEM", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="SEQ_TTA_SOLE_ITEM",strategy=GenerationType.SEQUENCE)
	@Column(name="sole_item_id", nullable=false, length=22)	
	public Integer getSoleItemId() {
		return soleItemId;
	}

	public void setSoleAgrtInfoId(Integer soleAgrtInfoId) {
		this.soleAgrtInfoId = soleAgrtInfoId;
	}

	@Column(name="sole_agrt_info_id", nullable=true, length=22)	
	public Integer getSoleAgrtInfoId() {
		return soleAgrtInfoId;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(name="group_code", nullable=true, length=50)	
	public String getGroupCode() {
		return groupCode;
	}

	@Column(name="brand_cn", nullable=true, length=50)
	public String getBrandCn() {
		return brandCn;
	}


	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name="bar_code", nullable=true, length=100)	
	public String getBarCode() {
		return barCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name="item_code", nullable=true, length=50)	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name="item_name", nullable=true, length=100)	
	public String getItemName() {
		return itemName;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Column(name="effective_date", nullable=true, length=7)	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Column(name="expiration_date", nullable=true, length=7)	
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
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

	@Column(name="last_update_date", nullable=false, length=7)	
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name="sole_agrt_h_id", nullable=true, length=22)
	public Integer getSoleAgrtHId() {
		return soleAgrtHId;
	}

	public void setSoleAgrtHId(Integer soleAgrtHId) {
		this.soleAgrtHId = soleAgrtHId;
	}

	@Column(name="group_name", nullable=true, length=100)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="VENDOR_NBR", nullable=true, length=30)
	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name="VENDOR_NAME", nullable=true, length=150)
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Column(name="DEPT_CODE", nullable=true, length=150)
	public Integer getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(Integer deptCode) {
		this.deptCode = deptCode;
	}

	@Column(name="DEPT_DESC", nullable=true, length=100)
	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	@Column(name="brand_en", nullable=true, length=500)
	public String getBrandEn() {
		return brandEn;
	}

	public void setBrandEn(String brandEn) {
		this.brandEn = brandEn;
	}

}
