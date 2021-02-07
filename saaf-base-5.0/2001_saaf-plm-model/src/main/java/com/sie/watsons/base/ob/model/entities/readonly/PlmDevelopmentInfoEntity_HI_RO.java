package com.sie.watsons.base.ob.model.entities.readonly;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

/**
 * PlmDevelopmentInfoEntity_HI_RO Entity Object Thu Aug 29 14:13:07 CST 2019
 * Auto Generate
 */

public class PlmDevelopmentInfoEntity_HI_RO {
	private Integer plmDevelopmentInfoId;
	private String obId;
	private Integer plmProjectId;
	private Integer plmProjectProductDetailId;
	private String barcode;
	private String projectRange;
	private String projectName;
	private String biddingCode;
	private String brandCn;
	private String productName;
	private String productNo;
	private String productNameCn;
	private String productNameEn;
	private Integer supplierId;
	private String supplierCode;
	private String supplierType;
	private String supplierName;
	private String producer;
	private String developmentBillStatus;
	private String productStatus;
	private String specialApprovalFlag;
	private String productCategory;
	private String projectCreator;
	private String productCreator;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	private String remarks;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	private Integer versionNum;
	private Integer operatorUserId;

	private String developmentBillStatusName;
	private String productStatusName;
	private String productionBatch;
	private BigDecimal productionCount;
	private String batchRemarks;
	private String productCategoryName;
	private String barcodeType;

	public String getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(String barcodeType) {
		this.barcodeType = barcodeType;
	}

	public static final String QUERY_SQL = "select pdi.PLM_DEVELOPMENT_INFO_ID       as plmDevelopmentInfoId,\n"
			+ "       pdi.OB_ID                         as obId,\n"
			+ "       pdi.PLM_PROJECT_ID                as plmProjectId,\n"
			+ "       pdi.PLM_PROJECT_PRODUCT_DETAIL_ID as plmProjectProductDetailId,\n"
			+ "       pdi.BARCODE                       as barcode,\n"
			+ "       pdi.PROJECT_RANGE                 as projectRange,\n"
			+ "       pdi.PROJECT_NAME                  as projectName,\n"
			+ "       pdi.BIDDING_CODE                  as biddingCode,\n"
			+ "       pdi.BRAND_CN                      as brandCn,\n"
			+ "       pdi.PRODUCT_NAME                  as productName,\n"
			+ "       pdi.PRODUCT_NO                    as productNo,\n"
			+ "       pdi.PRODUCT_NAME_CN               as productNameCn,\n"
			+ "       pdi.PRODUCT_NAME_EN               as productNameEn,\n"
			+ "       pdi.SUPPLIER_ID                   as supplierId,\n"
			+ "       pdi.supplier_code                 as supplierCode,\n"
			+ "       pdi.SUPPLIER_TYPE                 as supplierType,\n"
			+ "       pdi.SUPPLIER_NAME                 as supplierName,\n"
			+ "       pdi.PRODUCER                      as producer,\n"
			+ "       pdi.DEVELOPMENT_BILL_STATUS       as developmentBillStatus,\n"
			+
			// "       blv.MEANING                       as developmentBillStatusName,\n"
			// +
			"       pdi.PRODUCT_STATUS                as productStatus,\n"
			+
			// "       blv2.MEANING                      as productStatusName,\n"
			// +
			"       pdi.SPECIAL_APPROVAL_FLAG         as specialApprovalFlag,\n"
			+ "       pdi.PRODUCT_CATEGORY              as productCategory,\n"
			+
			// "       blv3.MEANING                      as productCategoryName,\n"
			// +
			"       pdi.PROJECT_CREATOR               as projectCreator,\n"
			+ "       pdi.PRODUCT_CREATOR               as productCreator,\n"
			+ "       pdi.CREATION_DATE                 as creationDate,\n"
			+ "       pdi.START_DATE                    as startDate,\n"
			+ "       pdi.END_DATE                      as endDate,\n"
			+ "       pdi.REMARKS                       as remarks,\n"
			+ "       pdi.CREATED_BY                    as createdBy,\n"
			+ "       pdi.LAST_UPDATED_BY               as lastUpdatedBy,\n"
			+ "       pdi.LAST_UPDATE_DATE              as lastUpdateDate,\n"
			+ "       pdi.LAST_UPDATE_LOGIN             as lastUpdateLogin,\n"
			+ "       pdi.VERSION_NUM                   as versionNum,\n"
			+ "       pdi.BARCODE_TYPE                  as  barcodeType "
			+ " from PLM_DEVELOPMENT_INFO pdi\n" +
			// "LEFT JOIN BASE_LOOKUP_VALUES blv ON blv.LOOKUP_CODE = pdi.DEVELOPMENT_BILL_STATUS AND blv.LOOKUP_TYPE = 'PLM_OB_PRODUCT_BILL_STATUS'\n"
			// +
			// "LEFT JOIN BASE_LOOKUP_VALUES blv2 ON blv2.LOOKUP_CODE = pdi.PRODUCT_STATUS AND blv2.LOOKUP_TYPE = 'PLM_DEVE_PRODUCT_STATUS'\n"
			// +
			// "LEFT JOIN BASE_LOOKUP_VALUES blv3 ON blv3.LOOKUP_CODE = pdi.PRODUCT_CATEGORY AND blv3.LOOKUP_TYPE = 'PLM_PROJECT_PRODUCT_CATEGORY'\n"
			// +
			"WHERE 1 = 1   ";

	public static final String QUERY_SQL_NEW = "select " +
			"pph.rms_code as productNo,\n" +
			"pbt.barcode as barcode,\n" +
			"pph.product_name as productName,\n" +
			"pdi.supplier_name as supplierName,\n" +
			"pdi.producer as producer\n" +
			"from plm_product_head pph \n" +
			"left join plm_product_barcode_table pbt\n" +
			"on pph.product_head_id=pbt.product_head_id\n" +
			"left join PLM_DEVELOPMENT_INFO pdi\n" +
			"on pbt.ob_id=pdi.ob_id\n" +
			"where pph.rms_status='Y'\n" +
			"and (pph.product_type=1 or pph.product_type=4)\n" +
			"and pbt.barcode is not null";

	public static final String REPORT_SQL = "select pdi.PLM_DEVELOPMENT_INFO_ID       as plmDevelopmentInfoId,\n"
			+ "       pdi.OB_ID                         as obId,\n"
			+ "       pdi.PLM_PROJECT_ID                as plmProjectId,\n"
			+ "       pdi.PLM_PROJECT_PRODUCT_DETAIL_ID as plmProjectProductDetailId,\n"
			+ "       pdi.BARCODE                       as barcode,\n"
			+ "       pdi.PROJECT_RANGE                 as projectRange,\n"
			+ "       pdi.PROJECT_NAME                  as projectName,\n"
			+ "       pdi.BIDDING_CODE                  as biddingCode,\n"
			+ "       pdi.BRAND_CN                      as brandCn,\n"
			+ "       pdi.PRODUCT_NAME                  as productName,\n"
			+ "       pdi.PRODUCT_NO                    as productNo,\n"
			+ "       pdi.PRODUCT_NAME_CN               as productNameCn,\n"
			+ "       pdi.PRODUCT_NAME_EN               as productNameEn,\n"
			+ "       pdi.SUPPLIER_ID                   as supplierId,\n"
			+ "       pdi.supplier_code                 as supplierCode,\n"
			+ "       pdi.SUPPLIER_TYPE                 as supplierType,\n"
			+ "       pdi.SUPPLIER_NAME                 as supplierName,\n"
			+ "       pdi.PRODUCER                      as producer,\n"
			+ "       pdi.DEVELOPMENT_BILL_STATUS       as developmentBillStatus,\n"
			+
			// "       blv.MEANING                       as developmentBillStatusName,\n"
			// +
			"       pdi.PRODUCT_STATUS                as productStatus,\n"
			+
			// "       blv2.MEANING                      as productStatusName,\n"
			// +
			"       pdi.SPECIAL_APPROVAL_FLAG         as specialApprovalFlag,\n"
			+ "       pdi.PRODUCT_CATEGORY              as productCategory,\n"
			+ "       pdi.PROJECT_CREATOR               as projectCreator,\n"
			+ "       pdi.PRODUCT_CREATOR               as productCreator,\n"
			+ "       pdi.CREATION_DATE                 as creationDate,\n"
			+ "       pdi.START_DATE                    as startDate,\n"
			+ "       pdi.END_DATE                      as endDate,\n"
			+ "       pdi.REMARKS                       as remarks,\n"
			+ "       pdi.CREATED_BY                    as createdBy,\n"
			+ "       pdi.LAST_UPDATED_BY               as lastUpdatedBy,\n"
			+ "       pdi.LAST_UPDATE_DATE              as lastUpdateDate,\n"
			+ "       pdi.LAST_UPDATE_LOGIN             as lastUpdateLogin,\n"
			+ "       pdi.VERSION_NUM                   as versionNum,\n"
			+ "       pdbi.PRODUCTION_BATCH             as productionBatch,\n"
			+ "       pdbi.PRODUCTION_COUNT             as productionCount,\n"
			+ "       pdbi.REMARKS                      as batchRemarks\n"
			+ "from PLM_DEVELOPMENT_INFO pdi\n"
			+
			// "LEFT JOIN BASE_LOOKUP_VALUES blv ON blv.LOOKUP_CODE = pdi.DEVELOPMENT_BILL_STATUS AND blv.LOOKUP_TYPE = 'PLM_OB_PRODUCT_BILL_STATUS'\n"
			// +
			// "LEFT JOIN BASE_LOOKUP_VALUES blv2 ON blv2.LOOKUP_CODE = pdi.PRODUCT_STATUS AND blv2.LOOKUP_TYPE = 'PLM_DEVE_PRODUCT_STATUS'\n"
			// +
			"LEFT JOIN PLM_DEVELOPMENT_BATCH_INFO pdbi ON pdi.PLM_DEVELOPMENT_INFO_ID = pdbi.PLM_DEVELOPMENT_INFO_ID\n"
			+ "WHERE 1 = 1 ";

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	public String getObId() {
		return obId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcode() {
		return barcode;
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

	public void setBrandCn(String brandCn) {
		this.brandCn = brandCn;
	}

	public String getBrandCn() {
		return brandCn;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNameCn(String productNameCn) {
		this.productNameCn = productNameCn;
	}

	public String getProductNameCn() {
		return productNameCn;
	}

	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}

	public String getProductNameEn() {
		return productNameEn;
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

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getProducer() {
		return producer;
	}

	public void setDevelopmentBillStatus(String developmentBillStatus) {
		this.developmentBillStatus = developmentBillStatus;
	}

	public String getDevelopmentBillStatus() {
		return developmentBillStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setSpecialApprovalFlag(String specialApprovalFlag) {
		this.specialApprovalFlag = specialApprovalFlag;
	}

	public String getSpecialApprovalFlag() {
		return specialApprovalFlag;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProjectCreator(String projectCreator) {
		this.projectCreator = projectCreator;
	}

	public String getProjectCreator() {
		return projectCreator;
	}

	public void setProductCreator(String productCreator) {
		this.productCreator = productCreator;
	}

	public String getProductCreator() {
		return productCreator;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
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

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getBiddingCode() {
		return biddingCode;
	}

	public void setBiddingCode(String biddingCode) {
		this.biddingCode = biddingCode;
	}

	public Integer getPlmProjectProductDetailId() {
		return plmProjectProductDetailId;
	}

	public void setPlmProjectProductDetailId(Integer plmProjectProductDetailId) {
		this.plmProjectProductDetailId = plmProjectProductDetailId;
	}

	public String getDevelopmentBillStatusName() {
		// return developmentBillStatusName;
		if (this.developmentBillStatus != null
				&& !"".equals(this.developmentBillStatus)) {
			if (ResultConstant.PLM_OB_PRODUCT_BILL_STATUS != null) {
				this.developmentBillStatusName = ResultConstant.PLM_OB_PRODUCT_BILL_STATUS
						.getString(developmentBillStatus);
			}
		}
		return this.developmentBillStatusName;
	}

	public void setDevelopmentBillStatusName(String developmentBillStatusName) {
		this.developmentBillStatusName = developmentBillStatusName;
	}

	public String getProductStatusName() {
		// return productStatusName;
		if (this.productStatus != null && !"".equals(this.productStatus)) {
			if (ResultConstant.PLM_DEVE_PRODUCT_STATUS != null) {
				this.productStatusName = ResultConstant.PLM_DEVE_PRODUCT_STATUS
						.getString(productStatus);
			}
		}
		return this.productStatusName;
	}

	public void setProductStatusName(String productStatusName) {
		this.productStatusName = productStatusName;
	}

	public String getProductionBatch() {
		return productionBatch;
	}

	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}

	public BigDecimal getProductionCount() {
		return productionCount;
	}

	public void setProductionCount(BigDecimal productionCount) {
		this.productionCount = productionCount;
	}

	public String getBatchRemarks() {
		return batchRemarks;
	}

	public void setBatchRemarks(String batchRemarks) {
		this.batchRemarks = batchRemarks;
	}

	public String getProductCategoryName() {
		// return productCategoryName;
		if (this.productCategory != null && !"".equals(this.productCategory)) {
			if (ResultConstant.PLM_PROJECT_PRODUCT_CATEGORY != null) {
				this.productCategoryName = ResultConstant.PLM_PROJECT_PRODUCT_CATEGORY
						.getString(productCategory);
			}
		}
		return this.productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
}
