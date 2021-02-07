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
 * PlmSupplierQaNonObInfoEntity_HI Entity Object Wed Oct 09 10:07:17 CST 2019
 * Auto Generate
 */
@Entity
@Table(name = "PLM_SUPPLIER_QA_NON_OB_INFO")
public class PlmSupplierQaNonObInfoEntity_HI {
	private Integer plmSupplierQaNonObInfoId;
	private String qaGroupCode;
	private String specialProductType;
	private String specialProductTypeName;
	private String brandCnName;
	private String brandEnName;
	private Integer plmBrandId;
	private String purchaseApprovalRole;
	private Integer purchaseApprovalUser;
	private String qaApprovalRole;
	private Integer qaApprovalUser;
	private String dealerName;
	private String producerName;
	private String billStatus;
	private String approvalRemarks;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer operatorUserId;
	private String qaGroupName;
	private String creatorName;
	private String billStatusName;
	private String supplierCode; // 供应商编码

	private String submitPerson; // 提交人姓名

	private String curentId; // 保存流程id

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

	@Column(name = "curent_id", nullable = true, length = 255)
	public String getCurentId() {
		return curentId;
	}

	public void setCurentId(String curentId) {
		this.curentId = curentId;
	}

	@Column(name = "submit_person", nullable = true, length = 255)
	public String getSubmitPerson() {
		return submitPerson;
	}

	public void setSubmitPerson(String submitPerson) {
		this.submitPerson = submitPerson;
	}

	public void setPlmSupplierQaNonObInfoId(Integer plmSupplierQaNonObInfoId) {
		this.plmSupplierQaNonObInfoId = plmSupplierQaNonObInfoId;
	}

	@Column(name = "supplier_code", nullable = true, length = 100)
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	@Id
	@SequenceGenerator(name = "plmSupplierQaNonObInfoEntity_HISeqGener", sequenceName = "SEQ_PLM_SUPPLIER_QA_NON_OB", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "plmSupplierQaNonObInfoEntity_HISeqGener", strategy = GenerationType.SEQUENCE)
	@Column(name = "plm_supplier_qa_non_ob_info_id", nullable = false, length = 22)
	public Integer getPlmSupplierQaNonObInfoId() {
		return plmSupplierQaNonObInfoId;
	}

	public void setQaGroupCode(String qaGroupCode) {
		this.qaGroupCode = qaGroupCode;
	}

	@Column(name = "qa_group_code", nullable = true, length = 100)
	public String getQaGroupCode() {
		return qaGroupCode;
	}

	public void setSpecialProductType(String specialProductType) {
		this.specialProductType = specialProductType;
	}

	@Column(name = "special_product_type", nullable = true, length = 20)
	public String getSpecialProductType() {
		return specialProductType;
	}

	public void setSpecialProductTypeName(String specialProductTypeName) {
		this.specialProductTypeName = specialProductTypeName;
	}

	@Column(name = "special_product_type_name", nullable = true, length = 50)
	public String getSpecialProductTypeName() {
		return specialProductTypeName;
	}

	public void setBrandCnName(String brandCnName) {
		this.brandCnName = brandCnName;
	}

	@Column(name = "brand_cn_name", nullable = true, length = 200)
	public String getBrandCnName() {
		return brandCnName;
	}

	public void setBrandEnName(String brandEnName) {
		this.brandEnName = brandEnName;
	}

	@Column(name = "brand_en_name", nullable = true, length = 200)
	public String getBrandEnName() {
		return brandEnName;
	}

	public void setPlmBrandId(Integer plmBrandId) {
		this.plmBrandId = plmBrandId;
	}

	@Column(name = "plm_brand_id", nullable = true, length = 22)
	public Integer getPlmBrandId() {
		return plmBrandId;
	}

	public void setPurchaseApprovalRole(String purchaseApprovalRole) {
		this.purchaseApprovalRole = purchaseApprovalRole;
	}

	@Column(name = "purchase_approval_role", nullable = true, length = 200)
	public String getPurchaseApprovalRole() {
		return purchaseApprovalRole;
	}

	public void setPurchaseApprovalUser(Integer purchaseApprovalUser) {
		this.purchaseApprovalUser = purchaseApprovalUser;
	}

	@Column(name = "purchase_approval_user", nullable = true, length = 22)
	public Integer getPurchaseApprovalUser() {
		return purchaseApprovalUser;
	}

	public void setQaApprovalRole(String qaApprovalRole) {
		this.qaApprovalRole = qaApprovalRole;
	}

	@Column(name = "qa_approval_role", nullable = true, length = 200)
	public String getQaApprovalRole() {
		return qaApprovalRole;
	}

	public void setQaApprovalUser(Integer qaApprovalUser) {
		this.qaApprovalUser = qaApprovalUser;
	}

	@Column(name = "qa_approval_user", nullable = true, length = 22)
	public Integer getQaApprovalUser() {
		return qaApprovalUser;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	@Column(name = "dealer_name", nullable = true, length = 800)
	public String getDealerName() {
		return dealerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	@Column(name = "producer_name", nullable = true, length = 800)
	public String getProducerName() {
		return producerName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	@Column(name = "bill_status", nullable = true, length = 20)
	public String getBillStatus() {
		return billStatus;
	}

	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	@Column(name = "approval_remarks", nullable = true, length = 800)
	public String getApprovalRemarks() {
		return approvalRemarks;
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

	@Column(name = "creator_name", nullable = true, length = 50)
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "bill_status_name", nullable = true, length = 50)
	public String getBillStatusName() {
		return billStatusName;
	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	@Column(name = "qa_group_name", nullable = true, length = 100)
	public String getQaGroupName() {
		return qaGroupName;
	}

	public void setQaGroupName(String qaGroupName) {
		this.qaGroupName = qaGroupName;
	}
}
