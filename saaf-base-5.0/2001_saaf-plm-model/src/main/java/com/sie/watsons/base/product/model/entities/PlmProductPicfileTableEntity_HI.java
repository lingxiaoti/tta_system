package com.sie.watsons.base.product.model.entities;

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
 * PlmProductPicfileTableEntity_HI Entity Object Thu Aug 29 10:51:51 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_PICFILE_TABLE")
public class PlmProductPicfileTableEntity_HI implements Comparable {
	private Integer picId;
	@columnNames(name = "图片文件id")
	private Integer picfileId;
	@columnNames(name = "图片文件类型")
	private String picType;
	@columnNames(name = "图片文件名称")
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
	private String flags;
	@columnNames(name = "退回原因")
	private String returnReson;

	private String isSupplierid;

	private String isUpdate;

	private String isRequire;

	private Integer picSeq;

	@Column(name = "is_require", nullable = true, length = 100)
	public String getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}

	@Column(name = "is_update", nullable = true, length = 100)
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_PICFILE_TABLE", sequenceName = "SEQ_PLM_PRODUCT_PICFILE_TABLE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_PICFILE_TABLE", strategy = GenerationType.SEQUENCE)
	@Column(name = "pic_id", nullable = false, length = 22)
	public Integer getPicId() {
		return picId;
	}

	public void setPicfileId(Integer picfileId) {
		this.picfileId = picfileId;
	}

	@Column(name = "supplier_id", nullable = true, length = 100)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "picfile_id", nullable = true, length = 22)
	public Integer getPicfileId() {
		return picfileId;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	@Column(name = "pic_type", nullable = true, length = 100)
	public String getPicType() {
		return picType;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Column(name = "pic_name", nullable = true, length = 150)
	public String getPicName() {
		return picName;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(name = "pic_url", nullable = true, length = 255)
	public String getPicUrl() {
		return picUrl;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name = "product_head_id", nullable = true, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
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

	@Column(name = "flags", nullable = true, length = 255)
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	@Column(name = "return_reson", nullable = true, length = 500)
	public String getReturnReson() {
		return returnReson;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	@Column(name = "PIC_SEQ", nullable = true, length = 22)
	public Integer getPicSeq() {
		return picSeq;
	}

	public void setPicSeq(Integer picSeq) {
		this.picSeq = picSeq;
	}

	@Transient
	public String getIsSupplierid() {
		return isSupplierid;
	}

	public void setIsSupplierid(String isSupplierid) {
		this.isSupplierid = isSupplierid;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof PlmProductPicfileTableEntity_HI) {
			PlmProductPicfileTableEntity_HI s = (PlmProductPicfileTableEntity_HI) o;
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
