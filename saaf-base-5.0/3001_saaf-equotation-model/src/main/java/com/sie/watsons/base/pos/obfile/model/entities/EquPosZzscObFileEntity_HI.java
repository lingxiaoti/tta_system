package com.sie.watsons.base.pos.obfile.model.entities;

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
 * EquPosZzscObFileEntity_HI Entity Object
 * Mon Dec 02 17:24:58 CST 2019  Auto Generate
 */
@Entity
@Table(name="EQU_POS_ZZSC_OB_FILE")
public class EquPosZzscObFileEntity_HI {
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
	@Id
	@SequenceGenerator(name = "EQU_POS_ZZSC_OB_FILE_S", sequenceName = "EQU_POS_ZZSC_OB_FILE_S", allocationSize = 1)
	@GeneratedValue(generator = "EQU_POS_ZZSC_OB_FILE_S", strategy = GenerationType.SEQUENCE)
	@Column(name="ob_file_id", nullable=false, length=22)	
	public Integer getObFileId() {
		return obFileId;
	}

	public void setSuppIdnt(String suppIdnt) {
		this.suppIdnt = suppIdnt;
	}

	@Column(name="supp_idnt", nullable=true, length=50)	
	public String getSuppIdnt() {
		return suppIdnt;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=200)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=22)	
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="file_type", nullable=true, length=50)	
	public String getFileType() {
		return fileType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_name", nullable=true, length=200)	
	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="file_path", nullable=true, length=200)	
	public String getFilePath() {
		return filePath;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="file_id", nullable=true, length=22)	
	public Integer getFileId() {
		return fileId;
	}

	public void setFrDate(Date frDate) {
		this.frDate = frDate;
	}

	@Column(name="fr_date", nullable=true, length=7)	
	public Date getFrDate() {
		return frDate;
	}

	public void setEndData(Date endData) {
		this.endData = endData;
	}

	@Column(name="end_data", nullable=true, length=7)	
	public Date getEndData() {
		return endData;
	}

	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	@Column(name="mongo_id", nullable=true, length=100)	
	public String getMongoId() {
		return mongoId;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name="attribute1", nullable=true, length=200)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name="attribute2", nullable=true, length=200)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name="attribute3", nullable=true, length=200)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name="attribute4", nullable=true, length=200)	
	public String getAttribute4() {
		return attribute4;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name="attribute_category", nullable=true, length=30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
