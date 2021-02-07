package com.sie.watsons.base.productEco.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * PlmProductPicfileTableEcoEntity_HI Entity Object
 * Fri May 22 14:29:40 CST 2020  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_PICFILE_TABLE_ECO")
public class PlmProductPicfileTableEcoEntity_HI {
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

	@Column(name="eco_id", nullable=false, length=22)	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_LINE", sequenceName = "SEQ_PLM_PRODUCT_LINE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_LINE", strategy = GenerationType.SEQUENCE)
	@Column(name="line_id", nullable=false, length=22)	
	public Integer getLineId() {
		return lineId;
	}

	public void setAcdType(String acdType) {
		this.acdType = acdType;
	}

	@Column(name="acd_type", nullable=true, length=20)	
	public String getAcdType() {
		return acdType;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	@Column(name="pic_id", nullable=false, length=22)	
	public Integer getPicId() {
		return picId;
	}

	public void setPicfileId(Integer picfileId) {
		this.picfileId = picfileId;
	}

	@Column(name="picfile_id", nullable=true, length=22)	
	public Integer getPicfileId() {
		return picfileId;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	@Column(name="pic_type", nullable=true, length=100)	
	public String getPicType() {
		return picType;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name="pic_name", nullable=true, length=150)	
	public String getPicName() {
		return picName;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(name="pic_url", nullable=true, length=255)	
	public String getPicUrl() {
		return picUrl;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name="product_head_id", nullable=true, length=22)	
	public Integer getProductHeadId() {
		return productHeadId;
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

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
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

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=100)	
	public String getSupplierId() {
		return supplierId;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	@Column(name="flags", nullable=true, length=255)	
	public String getFlags() {
		return flags;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	@Column(name="return_reson", nullable=true, length=500)	
	public String getReturnReson() {
		return returnReson;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Column(name="is_update", nullable=true, length=50)	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}

	@Column(name="is_require", nullable=true, length=100)	
	public String getIsRequire() {
		return isRequire;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
