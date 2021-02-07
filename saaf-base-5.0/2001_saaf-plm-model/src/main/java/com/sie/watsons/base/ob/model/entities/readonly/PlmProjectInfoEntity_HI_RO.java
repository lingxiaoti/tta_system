package com.sie.watsons.base.ob.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmProjectInfoEntity_HI_RO Entity Object
 * Thu Aug 29 11:31:03 CST 2019  Auto Generate
 */

public class PlmProjectInfoEntity_HI_RO {
    private Integer plmProjectId;
    private String projectNumber;
    private String biddingCode;
    private String projectRange;
    private String originalRange;
    private String projectName;
    private Integer supplierId;
    private String supplierType;
    private String supplierCode;
    private String supplierName;
    private String billStatus;
    private String billStatusName;
    private String multiProducer;
    private String creatorName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private String remarks;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    private Integer operatorUserId;

    private String productName;
	@JSONField(format="yyyy-MM-dd")
	private Date exceptionOccurrenceTime;
	private String exceptionOccurrenceStage;
	private String occurrenceStageMeaning;
	private String exceptionDetails;
	private String results;
	private String supplierTypeName;
	private String productCategory;
	private Integer plmProjectProductDetailId;
	private Integer plmDevelopmentInfoId;
	private String exceptionProductName;
	private BigDecimal productCount;


	public static final String QUERY_SQL = "SELECT ppi.PLM_PROJECT_ID    as plmProjectId,\n" +
			"       ppi.PROJECT_NUMBER    as projectNumber,\n" +
			"       ppi.BIDDING_CODE      as biddingCode,\n" +
			"       ppi.PROJECT_RANGE     as projectRange,\n" +
			"       ppi.PROJECT_RANGE     as originalRange,\n" +
			"       ppi.PROJECT_NAME      as projectName,\n" +
			"       ppi.SUPPLIER_ID       as supplierId,\n" +
			"       ppi.SUPPLIER_TYPE     as supplierType,\n" +
//			"       blv3.MEANING          as supplierTypeName,\n" +
			"       ppi.SUPPLIER_NAME     as supplierName,\n" +
			"       ppi.SUPPLIER_CODE     as supplierCode,\n" +
			"       ppi.BILL_STATUS       as billStatus,\n" +
//			"       blv.MEANING           as billStatusName,\n" +
			"       ppi.CREATOR_NAME      as creatorName,\n" +
			"       ppi.CREATION_DATE     as creationDate,\n" +
			"       ppi.REMARKS           as remarks,\n" +
			"		ppi.MULTI_PRODUCER	  as multiProducer,\n" +
			"       ppi.CREATED_BY        as createdBy,\n" +
			"       ppi.LAST_UPDATED_BY   as lastUpdatedBy,\n" +
			"       ppi.LAST_UPDATE_DATE  as lastUpdateDate,\n" +
			"       ppi.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       prpd2.count           as productCount,\n" +
			"       ppi.VERSION_NUM       as versionNum\n" +
			"FROM PLM_PROJECT_INFO ppi\n" +
//			"            LEFT JOIN BASE_LOOKUP_VALUES blv\n" +
//			"         ON ppi.BILL_STATUS = blv.LOOKUP_CODE AND blv.LOOKUP_TYPE = 'PLM_PROJECT_STATUS'\n" +
//			"            LEFT JOIN BASE_LOOKUP_VALUES blv3\n" +
//			"         ON ppi.SUPPLIER_TYPE = blv3.LOOKUP_CODE AND blv3.LOOKUP_TYPE = 'PLM_SUPPLIER_TYPE'\n" +
			"LEFT JOIN (SELECT count(*) as count,prpd.PLM_PROJECT_ID FROM PLM_PROJECT_PRODUCT_DETAIL prpd GROUP BY prpd.PLM_PROJECT_ID) prpd2 ON ppi.PLM_PROJECT_ID = prpd2.PLM_PROJECT_ID\n" +
			"where 1 = 1  ";

    public static String REPORT_SQL = "SELECT ppi.PLM_PROJECT_ID             as plmProjectId,\n" +
			"       ppi.PROJECT_NUMBER             as projectNumber,\n" +
			"       ppi.BIDDING_CODE               as biddingCode,\n" +
			"       ppi.PROJECT_RANGE              as projectRange,\n" +
			"		ppi.PROJECT_RANGE			   as originalRange,\n" +
			"       ppi.PROJECT_NAME               as projectName,\n" +
			"	    ppi.MULTI_PRODUCER			   as mutliProducer,\n" +
			"		ppi.SUPPLIER_ID				   as supplierId,\n" +
			"       ppi.SUPPLIER_TYPE              as supplierType,\n" +
//			"       blv3.MEANING                   as supplierTypeName,\n" +
			"       ppi.SUPPLIER_NAME              as supplierName,\n" +
			"       ppi.SUPPLIER_CODE              as supplierCode,\n" +
			"       ppi.BILL_STATUS                as billStatus,\n" +
//			"       blv.MEANING                    as billStatusName,\n" +
			"       ppi.CREATOR_NAME               as creatorName,\n" +
			"       ppi.CREATION_DATE              as creationDate,\n" +
			"       ppi.REMARKS                    as remarks,\n" +
			"       ppi.CREATED_BY                 as createdBy,\n" +
			"       ppi.LAST_UPDATED_BY            as lastUpdatedBy,\n" +
			"       ppi.LAST_UPDATE_DATE           as lastUpdateDate,\n" +
			"       ppi.LAST_UPDATE_LOGIN          as lastUpdateLogin,\n" +
			"       ppi.VERSION_NUM                as versionNum,\n" +
			"       prpd.PRODUCT_NAME              as productName,\n" +
			"		ppe.EXCEPTION_PRODUCT_NAME	   as exceptionProductName,\n" +
			"       ppe.EXCEPTION_OCCURRENCE_TIME  as exceptionOccurrenceTime,\n" +
			"       ppe.EXCEPTION_OCCURRENCE_STAGE as exceptionOccurrenceStage,\n" +
//			"       blv2.MEANING                   as occurrenceStageMeaning,\n" +
			"       ppe.EXCEPTION_DETAILS          as exceptionDetails,\n" +
			"       ppe.RESULTS                    as results\n" +
			"FROM PLM_PROJECT_INFO ppi\n" +
//			"       LEFT JOIN BASE_LOOKUP_VALUES blv ON ppi.BILL_STATUS = blv.LOOKUP_CODE AND blv.LOOKUP_TYPE = 'PLM_PROJECT_STATUS'\n" +
			"       LEFT JOIN PLM_PROJECT_PRODUCT_DETAIL prpd ON ppi.PLM_PROJECT_ID = prpd.PLM_PROJECT_ID\n" +
			"       JOIN PLM_PROJECT_EXCEPTION ppe ON prpd.PLM_PROJECT_PRODUCT_DETAIL_ID = ppe.PLM_PROJECT_PRODUCT_DETAIL_ID\n" +
//			"       LEFT JOIN BASE_LOOKUP_VALUES blv2\n" +
//			"         ON ppe.EXCEPTION_OCCURRENCE_STAGE = blv2.LOOKUP_CODE AND blv2.LOOKUP_TYPE = 'PLM_EXCEPTION_OCCURRENCE_STAGE'\n" +
//			"       LEFT JOIN BASE_LOOKUP_VALUES blv3\n" +
//			"         ON ppi.SUPPLIER_TYPE = blv3.LOOKUP_CODE AND blv3.LOOKUP_TYPE = 'PLM_SUPPLIER_TYPE'\n" +
			"where 1 = 1 ";

    public static final String IMPORT_PROJECT_SQL = "select ppi.PLM_PROJECT_ID                 as plmProjectId,\n" +
			"		ppi.PROJECT_NAME				   as projectName,\n" +
			"       ppi.SUPPLIER_ID                    as supplierId,\n" +
			"		ppi.PROJECT_NUMBER				   as projectNumber,\n" +
			"       ppi.SUPPLIER_TYPE                  as supplierType,\n" +
			"		ppi.MULTI_PRODUCER				   as multiProducer,\n" +
			"       ppi.SUPPLIER_NAME                  as supplierName,\n" +
			"       ppi.SUPPLIER_CODE                  as supplierCode,\n" +
			"       ppi.CREATOR_NAME                   as creatorName,\n" +
			"		ppi.bill_status					   as billStatus,\n" +
			"		ppi.creation_date				   as creationDate,\n" +
			"       ppi.PROJECT_RANGE                  as projectRange\n" +
			"from PLM_PROJECT_INFO ppi\n" +
			"WHERE 1 = 1 ";

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	
	public String getProjectNumber() {
		return projectNumber;
	}

	public void setBiddingCode(String biddingCode) {
		this.biddingCode = biddingCode;
	}

	
	public String getBiddingCode() {
		return biddingCode;
	}

	public void setProjectRange(String projectRange) {
		this.projectRange = projectRange;
	}

	
	public String getProjectRange() {
		return projectRange;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
	public String getProjectName() {
		return projectName;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	
	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	
	public String getSupplierName() {
		return supplierName;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	
	public String getBillStatus() {
		return billStatus;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public String getRemarks() {
		return remarks;
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

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getBillStatusName() {
//		return billStatusName;
		if(this.billStatus != null && !"".equals(this.billStatus)){
			this.billStatusName = ResultConstant.PLM_PROJECT_STATUS.getString(billStatus);
		}
		return this.billStatusName;

	}

	public void setBillStatusName(String billStatusName) {
		this.billStatusName = billStatusName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getExceptionOccurrenceTime() {
		return exceptionOccurrenceTime;
	}

	public void setExceptionOccurrenceTime(Date exceptionOccurrenceTime) {
		this.exceptionOccurrenceTime = exceptionOccurrenceTime;
	}

	public String getExceptionOccurrenceStage() {
		return exceptionOccurrenceStage;
	}

	public void setExceptionOccurrenceStage(String exceptionOccurrenceStage) {
		this.exceptionOccurrenceStage = exceptionOccurrenceStage;
	}

	public String getOccurrenceStageMeaning() {
//		return occurrenceStageMeaning;
		if(this.exceptionOccurrenceStage != null && !"".equals(this.exceptionOccurrenceStage)){
			this.occurrenceStageMeaning = ResultConstant.PLM_EXCEPTION_OCCURRENCE_STAGE.getString(exceptionOccurrenceStage);
		}
		return this.occurrenceStageMeaning;

	}

	public void setOccurrenceStageMeaning(String occurrenceStageMeaning) {
		this.occurrenceStageMeaning = occurrenceStageMeaning;
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getSupplierTypeName() {
//		return supplierTypeName;
		if(this.supplierType != null && !"".equals(this.supplierType)){
			this.supplierTypeName = ResultConstant.PLM_SUPPLIER_TYPE.getString(supplierType);
		}
		return this.supplierTypeName;

	}

	public void setSupplierTypeName(String supplierTypeName) {
		this.supplierTypeName = supplierTypeName;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public Integer getPlmProjectProductDetailId() {
		return plmProjectProductDetailId;
	}

	public void setPlmProjectProductDetailId(Integer plmProjectProductDetailId) {
		this.plmProjectProductDetailId = plmProjectProductDetailId;
	}

	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	public String getOriginalRange() {
		return originalRange;
	}

	public void setOriginalRange(String originalRange) {
		this.originalRange = originalRange;
	}

	public String getExceptionProductName() {
		return exceptionProductName;
	}

	public void setExceptionProductName(String exceptionProductName) {
		this.exceptionProductName = exceptionProductName;
	}

	public BigDecimal getProductCount() {
		return productCount;
	}

	public void setProductCount(BigDecimal productCount) {
		this.productCount = productCount;
	}

	public String getMultiProducer() {
		return multiProducer;
	}

	public void setMultiProducer(String multiProducer) {
		this.multiProducer = multiProducer;
	}
}
