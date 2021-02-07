package com.sie.watsons.base.plmBase.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

/**
 * PlmSupplierQaNonObInfoEntity_HI_RO Entity Object Wed Oct 09 10:07:17 CST 2019
 * Auto Generate
 */

public class PlmSupplierQaNonObInfoEntity_HI_RO {
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
	private String creatorName;
	private String billStatusName;
	private Integer operatorUserId;
	private String qaGroupName;
	private String supplierCode;

	private String submitPerson;

	private String curentId;

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

	public String getCurentId() {
		return curentId;
	}

	public void setCurentId(String curentId) {
		this.curentId = curentId;
	}

	public String getSubmitPerson() {
		return submitPerson;
	}

	public void setSubmitPerson(String submitPerson) {
		this.submitPerson = submitPerson;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getBillStatusName() {
		// return billStatusName;
		if (this.billStatus != null && !"".equals(this.billStatus)) {
			if (ResultConstant.PLM_SUPPLIER_QA_STATUS != null) {
				this.billStatusName = ResultConstant.PLM_SUPPLIER_QA_STATUS
						.getString(billStatus);
			}
		}
		return this.billStatusName;

	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	public static final String TTA_SUPPLIER = "select supplier_id as supplierId,supplier_code as supplierCode,"
			+ "supplier_name  as supplierName,status from tta_supplier  where 1=1 ";
	public static final String QUERY_SQL = "SELECT psqnoi.PLM_SUPPLIER_QA_NON_OB_INFO_ID as plmSupplierQaNonObInfoId,\n"
			+ "       psqnoi.QA_GROUP_CODE                  as qaGroupCode,\n"
			+ "		psqnoi.QA_GROUP_NAME				  as qaGroupName,\n"
			+ "       psqnoi.SPECIAL_PRODUCT_TYPE           as specialProductType,\n"
			+ "       psqnoi.SPECIAL_PRODUCT_TYPE_NAME      as specialProductTypeName,\n"
			+ "       psqnoi.BRAND_CN_NAME                  as brandCnName,\n"
			+ "       psqnoi.BRAND_EN_NAME                  as brandEnName,\n"
			+ "       psqnoi.PLM_BRAND_ID                   as plmBrandId,\n"
			+ "       psqnoi.PURCHASE_APPROVAL_ROLE         as purchaseApprovalRole,\n"
			+ "       psqnoi.PURCHASE_APPROVAL_USER         as purchaseApprovalUser,\n"
			+ "       psqnoi.QA_APPROVAL_ROLE               as qaApprovalRole,\n"
			+ "       psqnoi.QA_APPROVAL_USER               as qaApprovalUser,\n"
			+ "       psqnoi.DEALER_NAME                    as dealerName,\n"
			+ "       psqnoi.PRODUCER_NAME                  as producerName,\n"
			+ "       psqnoi.BILL_STATUS                    as billStatus,\n"
			+ "       psqnoi.APPROVAL_REMARKS               as approvalRemarks,\n"
			+
			// "       blv.MEANING                           as billStatusName,\n"
			// +
			"       psqnoi.CREATOR_NAME                   as creatorName,\n"
			+ "       psqnoi.CREATED_BY                     as createdBy,\n"
			+ "       psqnoi.LAST_UPDATED_BY                as lastUpdatedBy,\n"
			+ "       psqnoi.LAST_UPDATE_DATE               as lastUpdateDate,\n"
			+ "       psqnoi.LAST_UPDATE_LOGIN              as lastUpdateLogin,\n"
			+ "       psqnoi.VERSION_NUM                    as versionNum,\n"
			+ "       psqnoi.CREATION_DATE                  as creationDate,\n"
			+ "        psqnoi.SUPPLIER_CODE                  as supplierCode,\n "
			+ "        psqnoi.SUBMIT_PERSON                  as submitPerson, \n  "
			+ "        psqnoi.CURENT_ID                  as curentId, \n  "
			+ "        psqnoi.process_reject                  as processReject, \n  "
			+ "        psqnoi.process_user                  as processUser, \n  "
			+ "        psqnoi.process_incident                  as processIncident \n  "
			+ " FROM PLM_SUPPLIER_QA_NON_OB_INFO psqnoi\n" +
			// "       LEFT JOIN BASE_LOOKUP_VALUES blv\n" +
			// "         ON psqnoi.BILL_STATUS = blv.LOOKUP_CODE AND blv.LOOKUP_TYPE = 'PLM_SUPPLIER_QA_STATUS'\n"
			// +
			"WHERE 1 = 1  ";

	public static final String countsql = " select count(1) as plmSupplierQaNonObInfoId from plm_product_supplier_info info left join plm_product_head headinfo on (headinfo.product_head_id=info.product_head_id) where headinfo.order_status in\r\n"
			+ "('1','2','3','4','10','7') and length(info.supplier_code)>=10 ";

	public void setPlmSupplierQaNonObInfoId(Integer plmSupplierQaNonObInfoId) {
		this.plmSupplierQaNonObInfoId = plmSupplierQaNonObInfoId;
	}

	public Integer getPlmSupplierQaNonObInfoId() {
		return plmSupplierQaNonObInfoId;
	}

	public void setQaGroupCode(String qaGroupCode) {
		this.qaGroupCode = qaGroupCode;
	}

	public String getQaGroupCode() {
		return qaGroupCode;
	}

	public void setSpecialProductType(String specialProductType) {
		this.specialProductType = specialProductType;
	}

	public String getSpecialProductType() {
		return specialProductType;
	}

	public void setSpecialProductTypeName(String specialProductTypeName) {
		this.specialProductTypeName = specialProductTypeName;
	}

	public String getSpecialProductTypeName() {
		return specialProductTypeName;
	}

	public void setBrandCnName(String brandCnName) {
		this.brandCnName = brandCnName;
	}

	public String getBrandCnName() {
		return brandCnName;
	}

	public void setBrandEnName(String brandEnName) {
		this.brandEnName = brandEnName;
	}

	public String getBrandEnName() {
		return brandEnName;
	}

	public void setPlmBrandId(Integer plmBrandId) {
		this.plmBrandId = plmBrandId;
	}

	public Integer getPlmBrandId() {
		return plmBrandId;
	}

	public void setPurchaseApprovalRole(String purchaseApprovalRole) {
		this.purchaseApprovalRole = purchaseApprovalRole;
	}

	public String getPurchaseApprovalRole() {
		return purchaseApprovalRole;
	}

	public void setPurchaseApprovalUser(Integer purchaseApprovalUser) {
		this.purchaseApprovalUser = purchaseApprovalUser;
	}

	public Integer getPurchaseApprovalUser() {
		return purchaseApprovalUser;
	}

	public void setQaApprovalRole(String qaApprovalRole) {
		this.qaApprovalRole = qaApprovalRole;
	}

	public String getQaApprovalRole() {
		return qaApprovalRole;
	}

	public void setQaApprovalUser(Integer qaApprovalUser) {
		this.qaApprovalUser = qaApprovalUser;
	}

	public Integer getQaApprovalUser() {
		return qaApprovalUser;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	public String getApprovalRemarks() {
		return approvalRemarks;
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

	public String getQaGroupName() {
		return qaGroupName;
	}

	public void setQaGroupName(String qaGroupName) {
		this.qaGroupName = qaGroupName;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
