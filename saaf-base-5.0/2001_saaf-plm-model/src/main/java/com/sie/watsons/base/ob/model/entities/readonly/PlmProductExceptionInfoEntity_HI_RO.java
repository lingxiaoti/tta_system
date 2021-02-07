package com.sie.watsons.base.ob.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmProductExceptionInfoEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:10 CST 2019  Auto Generate
 */

public class PlmProductExceptionInfoEntity_HI_RO {
    private Integer plmProductExceptionInfoId;
    private String productExceptionNum;
    @JSONField(format="yyyy-MM-dd")
    private Date creationDate;
    private String productExceptionBillStatus;
    private String productExceptionBillStatusName;
    private String creator;
    private String exceptionSource;
    private String exceptionSourceName;
    private String exceptionCategory;
    private String exceptionCategoryName;
    private String complaintShopNumber;
    private String cityOfShop;
    @JSONField(format="yyyy-MM-dd")
    private Date qaResponseTime;
    private String department;
    private String detail;
    private String indemnity;
	@JSONField(format="yyyy-MM-dd")
    private Date endDate;
    private String treatment;
    private String treatmentName;
    private String results;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;
	@JSONField(format="yyyy-MM-dd")
	private Date startDate;

    private String productNo;
    private String productNameCn;
    private String productName;
    private String batchNumber;
    private BigDecimal complaintSamplesNumber;
    private String supplierName;

    public static final String QUERY_SQL = "select ppei.PLM_PRODUCT_EXCEPTION_INFO_ID as plmProductExceptionInfoId,\n" +
			"       ppei.PRODUCT_EXCEPTION_NUM         as productExceptionNum,\n" +
			"       ppei.CREATION_DATE                 as creationDate,\n" +
			"       ppei.PRODUCT_EXCEPTION_BILL_STATUS as productExceptionBillStatus,\n" +
//			"       blv.MEANING                        as productExceptionBillStatusName,\n" +
			"		ppei.start_date					   as startDate,\n" +
			"       ppei.CREATOR                       as creator,\n" +
			"       ppei.EXCEPTION_SOURCE              as exceptionSource,\n" +
//			"       blv2.MEANING                       as exceptionSourceName,\n" +
			"       ppei.EXCEPTION_CATEGORY            as exceptionCategory,\n" +
//			"       blv3.MEANING                       as exceptionCategoryName,\n" +
			"       ppei.COMPLAINT_SHOP_NUMBER         as complaintShopNumber,\n" +
			"       ppei.CITY_OF_SHOP                  as cityOfShop,\n" +
			"       ppei.QA_RESPONSE_TIME              as qaResponseTime,\n" +
			"       ppei.DEPARTMENT                    as department,\n" +
			"       ppei.DETAIL                        as detail,\n" +
			"       ppei.INDEMNITY                     as indemnity,\n" +
			"       ppei.END_DATE                      as endDate,\n" +
			"       ppei.TREATMENT                     as treatment,\n" +
//			"       blv4.MEANING                       as treatmentName,\n" +
			"       ppei.RESULTS                       as results,\n" +
			"       ppei.CREATED_BY                    as createdBy,\n" +
			"       ppei.LAST_UPDATED_BY               as lastUpdatedBy,\n" +
			"       ppei.LAST_UPDATE_DATE              as lastUpdateDate,\n" +
			"       ppei.LAST_UPDATE_LOGIN             as lastUpdateLogin,\n" +
			"       ppei.VERSION_NUM                   as versionNum\n" +
			"FROM PLM_PRODUCT_EXCEPTION_INFO ppei\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv ON ppei.PRODUCT_EXCEPTION_BILL_STATUS = blv.LOOKUP_CODE AND blv.LOOKUP_TYPE = 'PLM_PRODUCT_EXEP_BILL_STATUS'\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv2 ON ppei.EXCEPTION_SOURCE = blv2.LOOKUP_CODE AND blv2.LOOKUP_TYPE = 'PLM_EXCEPTION_SOURCE'\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv3 ON ppei.EXCEPTION_CATEGORY = blv3.LOOKUP_CODE AND blv3.LOOKUP_TYPE = 'PLM_EXCEPTION_CATEGORY'\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv4 ON ppei.TREATMENT = blv4.LOOKUP_CODE AND blv4.LOOKUP_TYPE = 'PLM_TREATMENT'\n" +
			"WHERE 1=1  ";

    public static final String REPORT_SQL = "select ppei.PLM_PRODUCT_EXCEPTION_INFO_ID as plmProductExceptionInfoId,\n" +
			"       ppei.PRODUCT_EXCEPTION_NUM         as productExceptionNum,\n" +
			"       ppei.CREATION_DATE                 as creationDate,\n" +
			"		ppei.start_date					   as startDate,\n" +
			"       ppei.PRODUCT_EXCEPTION_BILL_STATUS as productExceptionBillStatus,\n" +
//			"       blv.MEANING                        as productExceptionBillStatusName,\n" +
			"       ppei.CREATOR                       as creator,\n" +
			"       ppei.EXCEPTION_SOURCE              as exceptionSource,\n" +
//			"       blv2.MEANING                       as exceptionSourceName,\n" +
			"       ppei.EXCEPTION_CATEGORY            as exceptionCategory,\n" +
//			"       blv3.MEANING                       as exceptionCategoryName,\n" +
			"       ppei.COMPLAINT_SHOP_NUMBER         as complaintShopNumber,\n" +
			"       ppei.CITY_OF_SHOP                  as cityOfShop,\n" +
			"       ppei.QA_RESPONSE_TIME              as qaResponseTime,\n" +
			"       ppei.DEPARTMENT                    as department,\n" +
			"       ppei.DETAIL                        as detail,\n" +
			"       ppei.INDEMNITY                     as indemnity,\n" +
			"       ppei.END_DATE                      as endDate,\n" +
			"       ppei.TREATMENT                     as treatment,\n" +
//			"       blv4.MEANING                       as treatmentName,\n" +
			"       ppei.RESULTS                       as results,\n" +
			"       pred.PRODUCT_NO                    as productNo,\n" +
			"       pred.PRODUCT_NAME_CN               as productNameCn,\n" +
			"       pred.COMPLAINT_SAMPLES_NUMBER      as complaintSamplesNumber,\n" +
			"       pred.BATCH_NUMBER                  as batchNumber,\n" +
			"       pred.SUPPLIER_NAME                 as supplierName,\n" +
			"       pred.PRODUCT_NAME                  as productName,\n" +
			"       ppei.CREATED_BY                    as createdBy,\n" +
			"       ppei.LAST_UPDATED_BY               as lastUpdatedBy,\n" +
			"       ppei.LAST_UPDATE_DATE              as lastUpdateDate,\n" +
			"       ppei.LAST_UPDATE_LOGIN             as lastUpdateLogin,\n" +
			"       ppei.VERSION_NUM                   as versionNum\n" +
			"FROM PLM_PRODUCT_EXCEPTION_INFO ppei\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv ON ppei.PRODUCT_EXCEPTION_BILL_STATUS = blv.LOOKUP_CODE AND blv.LOOKUP_TYPE = 'PLM_PRODUCT_EXEP_BILL_STATUS'\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv2 ON ppei.EXCEPTION_SOURCE = blv2.LOOKUP_CODE AND blv2.LOOKUP_TYPE = 'PLM_EXCEPTION_SOURCE'\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv3 ON ppei.EXCEPTION_CATEGORY = blv3.LOOKUP_CODE AND blv3.LOOKUP_TYPE = 'PLM_EXCEPTION_CATEGORY'\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv4 ON ppei.TREATMENT = blv4.LOOKUP_CODE AND blv4.LOOKUP_TYPE = 'PLM_TREATMENT'\n" +
			"LEFT JOIN PLM_PRODUCT_EXCEPTION_DETAIL pred ON ppei.PLM_PRODUCT_EXCEPTION_INFO_ID = pred.PLM_PRODUCT_EXCEPTION_INFO_ID\n" +
			"WHERE 1=1  ";


	public void setPlmProductExceptionInfoId(Integer plmProductExceptionInfoId) {
		this.plmProductExceptionInfoId = plmProductExceptionInfoId;
	}

	
	public Integer getPlmProductExceptionInfoId() {
		return plmProductExceptionInfoId;
	}

	public void setProductExceptionNum(String productExceptionNum) {
		this.productExceptionNum = productExceptionNum;
	}

	
	public String getProductExceptionNum() {
		return productExceptionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setProductExceptionBillStatus(String productExceptionBillStatus) {
		this.productExceptionBillStatus = productExceptionBillStatus;
	}

	
	public String getProductExceptionBillStatus() {
		return productExceptionBillStatus;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	public String getCreator() {
		return creator;
	}

	public void setExceptionSource(String exceptionSource) {
		this.exceptionSource = exceptionSource;
	}

	
	public String getExceptionSource() {
		return exceptionSource;
	}

	public void setExceptionCategory(String exceptionCategory) {
		this.exceptionCategory = exceptionCategory;
	}

	
	public String getExceptionCategory() {
		return exceptionCategory;
	}

	public void setComplaintShopNumber(String complaintShopNumber) {
		this.complaintShopNumber = complaintShopNumber;
	}

	
	public String getComplaintShopNumber() {
		return complaintShopNumber;
	}

	public void setCityOfShop(String cityOfShop) {
		this.cityOfShop = cityOfShop;
	}

	
	public String getCityOfShop() {
		return cityOfShop;
	}

	public Date getQaResponseTime() {
		return qaResponseTime;
	}

	public void setQaResponseTime(Date qaResponseTime) {
		this.qaResponseTime = qaResponseTime;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	
	public String getDepartment() {
		return department;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
	public String getDetail() {
		return detail;
	}

	public void setIndemnity(String indemnity) {
		this.indemnity = indemnity;
	}

	
	public String getIndemnity() {
		return indemnity;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getEndDate() {
		return endDate;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	
	public String getTreatment() {
		return treatment;
	}

	public void setResults(String results) {
		this.results = results;
	}

	
	public String getResults() {
		return results;
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getProductExceptionBillStatusName() {
//		return productExceptionBillStatusName;
		if(this.productExceptionBillStatus != null && !"".equals(this.productExceptionBillStatus)){
			this.productExceptionBillStatusName = ResultConstant.PLM_PRODUCT_EXEP_BILL_STATUS.getString(productExceptionBillStatus);
		}
		return this.productExceptionBillStatusName;


	}

	public void setProductExceptionBillStatusName(String productExceptionBillStatusName) {
		this.productExceptionBillStatusName = productExceptionBillStatusName;
	}

	public String getExceptionSourceName() {
//		return exceptionSourceName;
		if(this.exceptionSource != null && !"".equals(this.exceptionSource)){
			this.exceptionSourceName = ResultConstant.PLM_EXCEPTION_SOURCE.getString(exceptionSource);
		}
		return this.exceptionSourceName;

	}

	public void setExceptionSourceName(String exceptionSourceName) {
		this.exceptionSourceName = exceptionSourceName;
	}

	public String getExceptionCategoryName() {
//		return exceptionCategoryName;
		if(this.exceptionCategory != null && !"".equals(this.exceptionCategory)){
			this.exceptionCategoryName = ResultConstant.PLM_EXCEPTION_CATEGORY.getString(exceptionCategory);
		}
		return this.exceptionCategoryName;

	}

	public void setExceptionCategoryName(String exceptionCategoryName) {
		this.exceptionCategoryName = exceptionCategoryName;
	}

	public String getTreatmentName() {
//		return treatmentName;
		if(this.treatment != null && !"".equals(this.treatment)){
			this.treatmentName = ResultConstant.PLM_TREATMENT.getString(treatment);
		}
		return this.treatmentName;
	}

	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductNameCn() {
		return productNameCn;
	}

	public void setProductNameCn(String productNameCn) {
		this.productNameCn = productNameCn;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public BigDecimal getComplaintSamplesNumber() {
		return complaintSamplesNumber;
	}

	public void setComplaintSamplesNumber(BigDecimal complaintSamplesNumber) {
		this.complaintSamplesNumber = complaintSamplesNumber;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
