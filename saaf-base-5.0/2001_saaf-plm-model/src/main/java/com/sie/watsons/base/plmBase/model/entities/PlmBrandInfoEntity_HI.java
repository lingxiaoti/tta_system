package com.sie.watsons.base.plmBase.model.entities;

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
 * PlmBrandInfoEntity_HI Entity Object Thu Oct 31 15:07:23 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_BRAND_INFO")
public class PlmBrandInfoEntity_HI {
	private Integer plmBrandInfoId;
	private String plmMotherCompany;
	private String plmGroupBrand;
	private String plmBrandCode;
	private String plmBrandCn;
	private String plmBrandEn;
	private String remarks;
	private String creator;
	private String billStatus;
	private String billStatusName;

	private Integer brandCnUdaId;
	private Integer brandCnUdaValue;
	private Integer brandEnUdaId;
	private Integer brandEnUdaValue;

	private Integer motherCompanyUdaId;
	private Integer mothercompanyUdaValue;
	private Integer groupbrandId;

	private Integer bic;
	private String bicName;
	private Integer ba;
	private String baName;
	private Integer ta;
	private String taName;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;
	private String plmLocalBrandCode;
	private String rejectText; // 驳回原因
	private Integer motherCompanyId;

	private String processReject;
	private String processUser;
	private String processIncident;

	@Column(name = "process_incident", nullable = true, length = 255)
	public String getProcessIncident() {
		return processIncident;
	}

	public void setProcessIncident(String processIncident) {
		this.processIncident = processIncident;
	}

	@Column(name = "process_user", nullable = true, length = 255)
	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	@Column(name = "process_reject", nullable = true, length = 255)
	public String getProcessReject() {
		return processReject;
	}

	public void setProcessReject(String processReject) {
		this.processReject = processReject;
	}

	public void setPlmBrandInfoId(Integer plmBrandInfoId) {
		this.plmBrandInfoId = plmBrandInfoId;
	}

	@Column(name = "reject_text", nullable = true, length = 255)
	public String getRejectText() {
		return rejectText;
	}

	public void setRejectText(String rejectText) {
		this.rejectText = rejectText;
	}

	@Id
	@SequenceGenerator(name = "plmBrandInfoEntity_HISeqGener", sequenceName = "SEQ_PLM_BRAND_INFO", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "plmBrandInfoEntity_HISeqGener", strategy = GenerationType.SEQUENCE)
	@Column(name = "plm_brand_info_id", nullable = false, length = 22)
	public Integer getPlmBrandInfoId() {
		return plmBrandInfoId;
	}

	@Column(name = "brand_cn_uda_id", nullable = false, length = 22)
	public Integer getBrandCnUdaId() {
		return brandCnUdaId;
	}

	public void setBrandCnUdaId(Integer brandCnUdaId) {
		this.brandCnUdaId = brandCnUdaId;
	}

	@Column(name = "brand_cn_uda_value", nullable = false, length = 220)
	public Integer getBrandCnUdaValue() {
		return brandCnUdaValue;
	}

	public void setBrandCnUdaValue(Integer brandCnUdaValue) {
		this.brandCnUdaValue = brandCnUdaValue;
	}

	@Column(name = "brand_en_uda_id", nullable = false, length = 22)
	public Integer getBrandEnUdaId() {
		return brandEnUdaId;
	}

	public void setBrandEnUdaId(Integer brandEnUdaId) {
		this.brandEnUdaId = brandEnUdaId;
	}

	@Column(name = "brand_en_uda_value", nullable = false, length = 220)
	public Integer getBrandEnUdaValue() {
		return brandEnUdaValue;
	}

	public void setBrandEnUdaValue(Integer brandEnUdaValue) {
		this.brandEnUdaValue = brandEnUdaValue;
	}

	@Column(name = "mother_company_uda_id", nullable = false, length = 22)
	public Integer getMotherCompanyUdaId() {
		return motherCompanyUdaId;
	}

	public void setMotherCompanyUdaId(Integer motherCompanyUdaId) {
		this.motherCompanyUdaId = motherCompanyUdaId;
	}

	@Column(name = "mothercompany_uda_value ", nullable = false, length = 220)
	public Integer getMothercompanyUdaValue() {
		return mothercompanyUdaValue;
	}

	public void setMothercompanyUdaValue(Integer mothercompanyUdaValue) {
		this.mothercompanyUdaValue = mothercompanyUdaValue;
	}

	@Column(name = "groupbrand_id ", nullable = false, length = 22)
	public Integer getGroupbrandId() {
		return groupbrandId;
	}

	public void setGroupbrandId(Integer groupbrandId) {
		this.groupbrandId = groupbrandId;
	}

	public void setPlmMotherCompany(String plmMotherCompany) {
		this.plmMotherCompany = plmMotherCompany;
	}

	@Column(name = "plm_mother_company", nullable = true, length = 200)
	public String getPlmMotherCompany() {
		return plmMotherCompany;
	}

	public void setPlmGroupBrand(String plmGroupBrand) {
		this.plmGroupBrand = plmGroupBrand;
	}

	@Column(name = "plm_group_brand", nullable = true, length = 200)
	public String getPlmGroupBrand() {
		return plmGroupBrand;
	}

	public void setPlmBrandCode(String plmBrandCode) {
		this.plmBrandCode = plmBrandCode;
	}

	@Column(name = "plm_brand_code", nullable = true, length = 50)
	public String getPlmBrandCode() {
		return plmBrandCode;
	}

	public void setPlmBrandCn(String plmBrandCn) {
		this.plmBrandCn = plmBrandCn;
	}

	@Column(name = "plm_brand_cn", nullable = true, length = 200)
	public String getPlmBrandCn() {
		return plmBrandCn;
	}

	public void setPlmBrandEn(String plmBrandEn) {
		this.plmBrandEn = plmBrandEn;
	}

	@Column(name = "plm_brand_en", nullable = true, length = 100)
	public String getPlmBrandEn() {
		return plmBrandEn;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "remarks", nullable = true, length = 400)
	public String getRemarks() {
		return remarks;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "creator", nullable = true, length = 100)
	public String getCreator() {
		return creator;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name = "bill_status", nullable = true, length = 20)
	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	@Column(name = "bill_status_name", nullable = true, length = 50)
	public String getBillStatusName() {
		return billStatusName;
	}

	public void setBic(Integer bic) {
		this.bic = bic;
	}

	@Column(name = "bic", nullable = true, length = 22)
	public Integer getBic() {
		return bic;
	}

	public void setBicName(String bicName) {
		this.bicName = bicName;
	}

	@Column(name = "bic_name", nullable = true, length = 100)
	public String getBicName() {
		return bicName;
	}

	public void setBa(Integer ba) {
		this.ba = ba;
	}

	@Column(name = "ba", nullable = true, length = 22)
	public Integer getBa() {
		return ba;
	}

	public void setBaName(String baName) {
		this.baName = baName;
	}

	@Column(name = "ba_name", nullable = true, length = 100)
	public String getBaName() {
		return baName;
	}

	public void setTa(Integer ta) {
		this.ta = ta;
	}

	@Column(name = "ta", nullable = true, length = 22)
	public Integer getTa() {
		return ta;
	}

	public void setTaName(String taName) {
		this.taName = taName;
	}

	@Column(name = "ta_name", nullable = true, length = 100)
	public String getTaName() {
		return taName;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "plm_local_brand_code", nullable = true, length = 50)
	public String getPlmLocalBrandCode() {
		return plmLocalBrandCode;
	}

	@Column(name = "motherCompany_id", nullable = true, length = 22)
	public Integer getMotherCompanyId() {
		return motherCompanyId;
	}

	public void setMotherCompanyId(Integer motherCompanyId) {
		this.motherCompanyId = motherCompanyId;
	}
	public void setPlmLocalBrandCode(String plmLocalBrandCode) {
		this.plmLocalBrandCode = plmLocalBrandCode;
	}
}
