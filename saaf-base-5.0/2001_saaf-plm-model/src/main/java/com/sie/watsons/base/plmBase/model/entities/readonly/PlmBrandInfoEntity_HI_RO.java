package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmBrandInfoEntity_HI_RO Entity Object Thu Oct 31 15:07:24 CST 2019 Auto
 * Generate
 */

public class PlmBrandInfoEntity_HI_RO {
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
	private Integer brandCnUdaId;
	private Integer brandCnUdaValue;
	private Integer brandEnUdaId;
	private Integer brandEnUdaValue;
	private Integer motherCompanyUdaId;
	private Integer mothercompanyUdaValue;
	private Integer groupbrandId;
	private String rejectText; // 驳回原因
	private String processReject;
	private String processUser;
	private String processIncident;

	public String getProcessReject() {
		return processReject;
	}

	public void setProcessReject(String processReject) {
		this.processReject = processReject;
	}

	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	public String getProcessIncident() {
		return processIncident;
	}

	public void setProcessIncident(String processIncident) {
		this.processIncident = processIncident;
	}

	public static String QUERY_SQL = "  SELECT pbi.plm_brand_info_id       AS plmBrandInfoId\n"
			+ "       ,pbi.plm_mother_company      AS plmMotherCompany\n"
			+ "       ,pbi.plm_group_brand         AS plmGroupBrand\n"
			+ "       ,pbi.plm_brand_code          AS plmBrandCode\n"
			+ "       ,pbi.plm_brand_cn            AS plmBrandCn\n"
			+ "       ,pbi.plm_brand_en            AS plmBrandEn\n"
			+ "       ,pbi.remarks                 AS remarks\n"
			+ "       ,pbi.plm_local_brand_code    AS plmLocalBrandCode\n"
			+ "       ,pbi.creator                 AS creator\n"
			+ "       ,pbi.bill_status             AS billStatus\n"
			+ "       ,pbi.bill_status_name        AS billStatusName\n"
			+ "       ,pbi.bic                     AS bic\n"
			+ "       ,pbi.bic_name                AS bicName\n"
			+ "       ,pbi.ba                      AS ba\n"
			+ "       ,pbi.ba_name                 AS baName\n"
			+ "       ,pbi.ta                      AS ta\n"
			+ "       ,pbi.ta_name                 AS taName\n"
			+ "       ,pbi.created_by              AS createdBy\n"
			+ "       ,pbi.last_updated_by         AS lastUpdatedBy\n"
			+ "       ,pbi.last_update_date        AS lastUpdateDate\n"
			+ "       ,pbi.last_update_login       AS lastUpdateLogin\n"
			+ "       ,pbi.version_num             AS versionNum\n"
			+ "       ,pbi.creation_date           AS creationDate\n"
			+ "       ,pbi.brand_cn_uda_id         AS brandCnUdaId\n"
			+ "       ,pbi.brand_cn_uda_value      AS brandCnUdaValue\n"
			+ "       ,pbi.brand_en_uda_id         AS brandEnUdaId\n"
			+ "       ,pbi.brand_en_uda_value      AS brandEnUdaValue\n"
			+ "       ,pbi.mother_company_uda_id   AS motherCompanyUdaId\n"
			+ "       ,pbi.mothercompany_uda_value AS mothercompanyUdaValue\n"
			+ "       ,pbi.groupbrand_id           AS groupbrandId\n"
			+ "       ,pbi.reject_text             AS rejectText\n"
			+ "       ,pbi.process_reject           AS processReject\n"
			+ "       ,pbi.process_user             AS processUser\n"
			+ "       ,pbi.process_incident         AS processIncident\n"
			+ "   FROM plm_brand_info pbi\n" + "  WHERE 1 = 1 ";

	public static String BrandcodeSql = "select 'B'||to_char(sysdate,'YYYY')||lpad(SEQ_PLM_BRAND_INFO_NO.nextval,5,'0') plmLocalBrandCode "
			+ " from dual";

	public static void main(String[] args) {
		System.out.println("SQL---->>>>:::" + QUERY_SQL);
	}

	public Integer getBrandCnUdaId() {
		return brandCnUdaId;
	}

	public void setBrandCnUdaId(Integer brandCnUdaId) {
		this.brandCnUdaId = brandCnUdaId;
	}

	public Integer getBrandCnUdaValue() {
		return brandCnUdaValue;
	}

	public void setBrandCnUdaValue(Integer brandCnUdaValue) {
		this.brandCnUdaValue = brandCnUdaValue;
	}

	public Integer getBrandEnUdaId() {
		return brandEnUdaId;
	}

	public void setBrandEnUdaId(Integer brandEnUdaId) {
		this.brandEnUdaId = brandEnUdaId;
	}

	public Integer getBrandEnUdaValue() {
		return brandEnUdaValue;
	}

	public void setBrandEnUdaValue(Integer brandEnUdaValue) {
		this.brandEnUdaValue = brandEnUdaValue;
	}

	public Integer getMotherCompanyUdaId() {
		return motherCompanyUdaId;
	}

	public void setMotherCompanyUdaId(Integer motherCompanyUdaId) {
		this.motherCompanyUdaId = motherCompanyUdaId;
	}

	public Integer getMothercompanyUdaValue() {
		return mothercompanyUdaValue;
	}

	public void setMothercompanyUdaValue(Integer mothercompanyUdaValue) {
		this.mothercompanyUdaValue = mothercompanyUdaValue;
	}

	public Integer getGroupbrandId() {
		return groupbrandId;
	}

	public void setGroupbrandId(Integer groupbrandId) {
		this.groupbrandId = groupbrandId;
	}

	public void setPlmBrandInfoId(Integer plmBrandInfoId) {
		this.plmBrandInfoId = plmBrandInfoId;
	}

	public Integer getPlmBrandInfoId() {
		return plmBrandInfoId;
	}

	public String getRejectText() {
		return rejectText;
	}

	public void setRejectText(String rejectText) {
		this.rejectText = rejectText;
	}

	public void setPlmMotherCompany(String plmMotherCompany) {
		this.plmMotherCompany = plmMotherCompany;
	}

	public String getPlmMotherCompany() {
		return plmMotherCompany;
	}

	public void setPlmGroupBrand(String plmGroupBrand) {
		this.plmGroupBrand = plmGroupBrand;
	}

	public String getPlmGroupBrand() {
		return plmGroupBrand;
	}

	public void setPlmBrandCode(String plmBrandCode) {
		this.plmBrandCode = plmBrandCode;
	}

	public String getPlmBrandCode() {
		return plmBrandCode;
	}

	public void setPlmBrandCn(String plmBrandCn) {
		this.plmBrandCn = plmBrandCn;
	}

	public String getPlmBrandCn() {
		return plmBrandCn;
	}

	public void setPlmBrandEn(String plmBrandEn) {
		this.plmBrandEn = plmBrandEn;
	}

	public String getPlmBrandEn() {
		return plmBrandEn;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	public String getBillStatusName() {
		return billStatusName;
	}

	public void setBic(Integer bic) {
		this.bic = bic;
	}

	public Integer getBic() {
		return bic;
	}

	public void setBicName(String bicName) {
		this.bicName = bicName;
	}

	public String getBicName() {
		return bicName;
	}

	public void setBa(Integer ba) {
		this.ba = ba;
	}

	public Integer getBa() {
		return ba;
	}

	public void setBaName(String baName) {
		this.baName = baName;
	}

	public String getBaName() {
		return baName;
	}

	public void setTa(Integer ta) {
		this.ta = ta;
	}

	public Integer getTa() {
		return ta;
	}

	public void setTaName(String taName) {
		this.taName = taName;
	}

	public String getTaName() {
		return taName;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getPlmLocalBrandCode() {
		return plmLocalBrandCode;
	}

	public void setPlmLocalBrandCode(String plmLocalBrandCode) {
		this.plmLocalBrandCode = plmLocalBrandCode;
	}
}
