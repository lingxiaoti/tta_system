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
 * PlmProductQaFileEntity_HI Entity Object Thu Aug 29 10:51:52 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_QA_FILE")
public class PlmProductQaFileEntity_HI {
	private Integer qaId;
	@columnNames(name = "资质文件id")
	private Integer qaFileId;
	private String qaUrl;
	@columnNames(name = "资质文件名称")
	private String qaFileName;
	@columnNames(name = "描述")
	private String description;
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
	@columnNames(name = "资质文件类型")
	private String qaFiletype;
	@columnNames(name = "证书编号类型")
	private String qaCodetype;
	@columnNames(name = "证书编号")
	private String qaCode;
	@columnNames(name = "日期类型")
	private String dateType;
	@columnNames(name = "日期")
	@JSONField(format = "yyyy-MM-dd")
	private Date datecurent;
	private String supplierId;
	private String supplierName;
	private String flags;
	@columnNames(name = "资质组Id")
	private String groupId; // 资质组id
	@columnNames(name = "资质组名称")
	private String groupName; // 资质组名称
	@columnNames(name = "退回原因")
	private String returnReson; // 退回原因

	private String isSupplierid;
	private String isSpa;
	private String isUpdate;
	private Integer listId;

	@Column(name = "is_update", nullable = true, length = 50)
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Column(name = "is_Spa", nullable = true, length = 200)
	public String getIsSpa() {
		return isSpa;
	}

	public void setIsSpa(String isSpa) {
		this.isSpa = isSpa;
	}

	public void setQaId(Integer qaId) {
		this.qaId = qaId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_QA_FILE", sequenceName = "SEQ_PLM_PRODUCT_QA_FILE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_QA_FILE", strategy = GenerationType.SEQUENCE)
	@Column(name = "qa_id", nullable = false, length = 22)
	public Integer getQaId() {
		return qaId;
	}

	public void setQaFileId(Integer qaFileId) {
		this.qaFileId = qaFileId;
	}

	@Column(name = "supplier_name", nullable = true, length = 255)
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name = "supplier_id", nullable = true, length = 255)
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "qa_file_id", nullable = true, length = 22)
	public Integer getQaFileId() {
		return qaFileId;
	}

	public void setQaUrl(String qaUrl) {
		this.qaUrl = qaUrl;
	}

	@Column(name = "qa_url", nullable = true, length = 255)
	public String getQaUrl() {
		return qaUrl;
	}

	public void setQaFileName(String qaFileName) {
		this.qaFileName = qaFileName;
	}

	@Column(name = "qa_file_name", nullable = true, length = 255)
	public String getQaFileName() {
		return qaFileName;
	}

	@Column(name = "description", nullable = true, length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "qa_filetype", nullable = true, length = 255)
	public String getQaFiletype() {
		return qaFiletype;
	}

	public void setQaFiletype(String qaFiletype) {
		this.qaFiletype = qaFiletype;
	}

	@Column(name = "qa_codetype", nullable = true, length = 255)
	public String getQaCodetype() {
		return qaCodetype;
	}

	public void setQaCodetype(String qaCodetype) {
		this.qaCodetype = qaCodetype;
	}

	@Column(name = "qa_code", nullable = true, length = 255)
	public String getQaCode() {
		return qaCode;
	}

	public void setQaCode(String qaCode) {
		this.qaCode = qaCode;
	}

	@Column(name = "date_type", nullable = true, length = 255)
	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	@Column(name = "datecurent", nullable = true, length = 7)
	public Date getDatecurent() {
		return datecurent;
	}

	public void setDatecurent(Date datecurent) {
		this.datecurent = datecurent;
	}

	@Column(name = "flags", nullable = true, length = 255)
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	@Column(name = "group_id", nullable = true, length = 255)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "group_name", nullable = true, length = 255)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "return_reson", nullable = true, length = 500)
	public String getReturnReson() {
		return returnReson;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	@Transient
	public String getIsSupplierid() {
		return isSupplierid;
	}

	public void setIsSupplierid(String isSupplierid) {
		this.isSupplierid = isSupplierid;
	}

	@Column(name = "list_id", nullable = true, length = 11)
	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}
}
