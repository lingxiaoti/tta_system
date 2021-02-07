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
 * PlmBrandMapEntity_HI Entity Object Fri May 22 16:25:08 CST 2020 Auto Generate
 */
@Entity
@Table(name = "PLM_BRAND_MAP")
public class PlmBrandMapEntity_HI {
	private Integer brandMapId;
	private Integer brandCnUdaId;
	private Integer brandCnUdaValue;
	private String brandCnUdaValueDesc;
	private Integer brandEnUdaId;
	private Integer brandEnUdaValue;
	private String brandEnUdaValueDesc;
	private Integer versionNum;
	private Integer createdBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private String billStatus;
	private String billStatusName;
	private String creator;
	private Integer operatorUserId;
	private Integer motherCompanyUdaId;
	private Integer mothercompanyUdaValue;
	private String plmMotherCompany;
	private Integer groupbrandId;
	private String plmGroupBrand;
	private Integer motherCompanyId;

	@Column(name = "mother_company_uda_id", nullable = true, length = 22)
	public Integer getMotherCompanyUdaId() {		return motherCompanyUdaId;	}
	public void setMotherCompanyUdaId(Integer motherCompanyUdaId) {		this.motherCompanyUdaId = motherCompanyUdaId;	}

	@Column(name = "mothercompany_uda_value", nullable = true, length = 22)
	public Integer getMothercompanyUdaValue() {		return mothercompanyUdaValue;	}
	public void setMothercompanyUdaValue(Integer mothercompanyUdaValue) {		this.mothercompanyUdaValue = mothercompanyUdaValue;	}

	@Column(name = "plm_mother_company", nullable = true, length = 255)
	public String getPlmMotherCompany() {		return plmMotherCompany;	}
	public void setPlmMotherCompany(String plmMotherCompany) {		this.plmMotherCompany = plmMotherCompany;	}

	@Column(name = "groupbrand_id", nullable = true, length = 22)
	public Integer getGroupbrandId() {		return groupbrandId;	}
	public void setGroupbrandId(Integer groupbrandId) {		this.groupbrandId = groupbrandId;	}

	@Column(name = "motherCompany_id", nullable = true, length = 22)
	public Integer getMotherCompanyId() {
		return motherCompanyId;
	}

	public void setMotherCompanyId(Integer motherCompanyId) {
		this.motherCompanyId = motherCompanyId;
	}

	@Column(name = "plm_group_brand", nullable = true, length = 255)
	public String getPlmGroupBrand() {		return plmGroupBrand;	}
	public void setPlmGroupBrand(String plmGroupBrand) {		this.plmGroupBrand = plmGroupBrand;	}



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

	public void setBrandMapId(Integer brandMapId) {
		this.brandMapId = brandMapId;
	}
	@Id
	@SequenceGenerator(name = "SEQ_PLM_BRAND_MAP", sequenceName = "SEQ_PLM_BRAND_MAP", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_BRAND_MAP", strategy = GenerationType.SEQUENCE)
	@Column(name = "brand_map_id", nullable = true, length = 22)
	public Integer getBrandMapId() {
		return brandMapId;
	}

	public void setBrandCnUdaId(Integer brandCnUdaId) {
		this.brandCnUdaId = brandCnUdaId;
	}

	@Column(name = "brand_cn_uda_id", nullable = true, length = 22)
	public Integer getBrandCnUdaId() {
		return brandCnUdaId;
	}

	public void setBrandCnUdaValue(Integer brandCnUdaValue) {
		this.brandCnUdaValue = brandCnUdaValue;
	}

	@Column(name = "brand_cn_uda_value", nullable = true, length = 22)
	public Integer getBrandCnUdaValue() {
		return brandCnUdaValue;
	}

	public void setBrandCnUdaValueDesc(String brandCnUdaValueDesc) {
		this.brandCnUdaValueDesc = brandCnUdaValueDesc;
	}

	@Column(name = "brand_cn_uda_value_desc", nullable = true, length = 250)
	public String getBrandCnUdaValueDesc() {
		return brandCnUdaValueDesc;
	}

	public void setBrandEnUdaId(Integer brandEnUdaId) {
		this.brandEnUdaId = brandEnUdaId;
	}

	@Column(name = "brand_en_uda_id", nullable = true, length = 22)
	public Integer getBrandEnUdaId() {
		return brandEnUdaId;
	}

	public void setBrandEnUdaValue(Integer brandEnUdaValue) {
		this.brandEnUdaValue = brandEnUdaValue;
	}

	@Column(name = "brand_en_uda_value", nullable = true, length = 22)
	public Integer getBrandEnUdaValue() {
		return brandEnUdaValue;
	}

	public void setBrandEnUdaValueDesc(String brandEnUdaValueDesc) {
		this.brandEnUdaValueDesc = brandEnUdaValueDesc;
	}

	@Column(name = "brand_en_uda_value_desc", nullable = true, length = 250)
	public String getBrandEnUdaValueDesc() {
		return brandEnUdaValueDesc;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 7)
	public Date getCreationDate() {
		return creationDate;
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

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "creator", nullable = true, length = 100)
	public String getCreator() {
		return creator;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
