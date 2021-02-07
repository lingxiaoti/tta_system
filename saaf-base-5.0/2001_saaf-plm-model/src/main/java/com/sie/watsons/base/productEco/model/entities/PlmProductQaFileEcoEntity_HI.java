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
 * PlmProductQaFileEcoEntity_HI Entity Object
 * Fri May 22 14:29:30 CST 2020  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_QA_FILE_ECO")
public class PlmProductQaFileEcoEntity_HI {
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

	public void setQaId(Integer qaId) {
		this.qaId = qaId;
	}

	@Column(name="qa_id", nullable=false, length=22)	
	public Integer getQaId() {
		return qaId;
	}

	public void setQaFileId(Integer qaFileId) {
		this.qaFileId = qaFileId;
	}

	@Column(name="qa_file_id", nullable=true, length=22)	
	public Integer getQaFileId() {
		return qaFileId;
	}

	public void setQaUrl(String qaUrl) {
		this.qaUrl = qaUrl;
	}

	@Column(name="qa_url", nullable=true, length=255)	
	public String getQaUrl() {
		return qaUrl;
	}

	public void setQaFileName(String qaFileName) {
		this.qaFileName = qaFileName;
	}

	@Column(name="qa_file_name", nullable=true, length=255)	
	public String getQaFileName() {
		return qaFileName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="description", nullable=true, length=255)	
	public String getDescription() {
		return description;
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

	public void setQaFiletype(String qaFiletype) {
		this.qaFiletype = qaFiletype;
	}

	@Column(name="qa_filetype", nullable=true, length=255)	
	public String getQaFiletype() {
		return qaFiletype;
	}

	public void setQaCodetype(String qaCodetype) {
		this.qaCodetype = qaCodetype;
	}

	@Column(name="qa_codetype", nullable=true, length=255)	
	public String getQaCodetype() {
		return qaCodetype;
	}

	public void setQaCode(String qaCode) {
		this.qaCode = qaCode;
	}

	@Column(name="qa_code", nullable=true, length=255)	
	public String getQaCode() {
		return qaCode;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	@Column(name="date_type", nullable=true, length=255)	
	public String getDateType() {
		return dateType;
	}

	public void setDatecurent(Date datecurent) {
		this.datecurent = datecurent;
	}

	@Column(name="datecurent", nullable=true, length=7)	
	public Date getDatecurent() {
		return datecurent;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name="supplier_id", nullable=true, length=255)	
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

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name="group_id", nullable=true, length=255)	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name="group_name", nullable=true, length=255)	
	public String getGroupName() {
		return groupName;
	}

	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}

	@Column(name="return_reson", nullable=true, length=500)	
	public String getReturnReson() {
		return returnReson;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Column(name="supplier_name", nullable=true, length=255)	
	public String getSupplierName() {
		return supplierName;
	}

	public void setIsSpa(String isSpa) {
		this.isSpa = isSpa;
	}

	@Column(name="is_spa", nullable=true, length=50)	
	public String getIsSpa() {
		return isSpa;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Column(name="is_update", nullable=true, length=2050)	
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
