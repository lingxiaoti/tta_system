package com.sie.watsons.base.ob.model.entities;

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
 * PlmPackageSpecificationEntity_HI Entity Object
 * Thu Aug 29 14:13:09 CST 2019  Auto Generate
 */
@Entity
@Table(name="PLM_PACKAGE_SPECIFICATION")
public class PlmPackageSpecificationEntity_HI {
    private Integer plmPackageSpecificationId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String packageNumber;
    private String itemLocalizationOrName;
    private String material;
    private Double netWeightPerGram;
    private String recyclable;
    private String remarks;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

	public void setPlmPackageSpecificationId(Integer plmPackageSpecificationId) {
		this.plmPackageSpecificationId = plmPackageSpecificationId;
	}

	@Id	
	@SequenceGenerator(name="plmPackageSpecificationEntity_HISeqGener", sequenceName="SEQ_PLM_PACKAGE_SPECIFICATION", allocationSize=1, initialValue=1)
	@GeneratedValue(generator="plmPackageSpecificationEntity_HISeqGener",strategy=GenerationType.SEQUENCE)	
	@Column(name="plm_package_specification_id", nullable=false, length=22)	
	public Integer getPlmPackageSpecificationId() {
		return plmPackageSpecificationId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	@Column(name="plm_development_info_id", nullable=true, length=22)	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	@Column(name="plm_project_id", nullable=true, length=22)	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}

	@Column(name="package_number", nullable=true, length=100)	
	public String getPackageNumber() {
		return packageNumber;
	}

	public void setItemLocalizationOrName(String itemLocalizationOrName) {
		this.itemLocalizationOrName = itemLocalizationOrName;
	}

	@Column(name="item_localization_or_name", nullable=true, length=200)	
	public String getItemLocalizationOrName() {
		return itemLocalizationOrName;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Column(name="material", nullable=true, length=200)	
	public String getMaterial() {
		return material;
	}

	public void setNetWeightPerGram(Double netWeightPerGram) {
		this.netWeightPerGram = netWeightPerGram;
	}

	@Column(name="net_weight_per_gram", nullable=true, length=22)	
	public Double getNetWeightPerGram() {
		return netWeightPerGram;
	}

	public void setRecyclable(String recyclable) {
		this.recyclable = recyclable;
	}

	@Column(name="recyclable", nullable=true, length=200)	
	public String getRecyclable() {
		return recyclable;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="remarks", nullable=true, length=800)	
	public String getRemarks() {
		return remarks;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
