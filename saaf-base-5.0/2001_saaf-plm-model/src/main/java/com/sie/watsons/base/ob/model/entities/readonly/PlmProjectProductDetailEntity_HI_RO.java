package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmProjectProductDetailEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:06 CST 2019  Auto Generate
 */

public class PlmProjectProductDetailEntity_HI_RO {
    private Integer plmProjectProductDetailId;
    private Integer plmProjectId;
    private Integer plmDevelopmentInfoId;
    private String obId;
    private String barcode;
    private String productName;
    private String productCategory;
    private String producerName;
    private String productBillStatus;
    private String productStatus;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer operatorUserId;

    private String productCategoryName;
    private String productBillStatusName;
    private String productCreator;

    public static final String QUERY_SQL = "SELECT\n" +
			"prpd.PLM_PROJECT_ID as plmProjectId,\n" +
			"       prpd.PLM_PROJECT_PRODUCT_DETAIL_ID as plmProjectProductDetailId,\n" +
			"       prpd.PLM_DEVELOPMENT_INFO_ID as plmDevelopmentInfoId,\n" +
			"       prpd.OB_ID as obId,\n" +
			"       prpd.BARCODE as barcode,\n" +
			"       prpd.PRODUCT_NAME as productName,\n" +
			"       prpd.PRODUCT_CATEGORY as productCategory,\n" +
			"		pdi.product_creator	as productCreator,\n" +
//			"       blv2.MEANING as productCategoryName,\n" +
			"       prpd.PRODUCER_NAME as producerName,\n" +
			"       prpd.PRODUCT_BILL_STATUS as productBillStatus,\n" +
			"		prpd.PRODUCT_STATUS as productStatus,\n" +
//			"       blv.MEANING as productBillStatusName,\n" +
			"       prpd.CREATED_BY as createdBy,\n" +
			"       prpd.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       prpd.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       prpd.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       prpd.VERSION_NUM as versionNum,\n" +
			"       prpd.CREATION_DATE as creationDate\n" +
			"FROM PLM_PROJECT_PRODUCT_DETAIL prpd\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv ON prpd.PRODUCT_BILL_STATUS = blv.LOOKUP_CODE AND blv.LOOKUP_TYPE = 'PLM_OB_PRODUCT_STATUS'\n" +
//			"LEFT JOIN BASE_LOOKUP_VALUES blv2 ON prpd.PRODUCT_CATEGORY = blv2.LOOKUP_CODE AND blv2.LOOKUP_TYPE = 'PLM_PROJECT_PRODUCT_CATEGORY'\n" +
			"LEFT JOIN PLM_DEVELOPMENT_INFO pdi ON prpd.PLM_DEVELOPMENT_INFO_ID = pdi.PLM_DEVELOPMENT_INFO_ID\n" +
			" WHERE 1=1 ";

	public void setPlmProjectProductDetailId(Integer plmProjectProductDetailId) {
		this.plmProjectProductDetailId = plmProjectProductDetailId;
	}

	
	public Integer getPlmProjectProductDetailId() {
		return plmProjectProductDetailId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

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

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	
	public String getBarcode() {
		return barcode;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	
	public String getProductCategory() {
		return productCategory;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	
	public String getProducerName() {
		return producerName;
	}

	public void setProductBillStatus(String productBillStatus) {
		this.productBillStatus = productBillStatus;
	}

	
	public String getProductBillStatus() {
		return productBillStatus;
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

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductCategoryName() {
//		return productCategoryName;
		if(this.productCategory != null && !"".equals(this.productCategory)){
			this.productCategoryName = ResultConstant.PLM_PROJECT_PRODUCT_CATEGORY.getString(productCategory);
		}
		return this.productCategoryName;

	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public String getProductBillStatusName() {
//		return productBillStatusName;
		if(this.productBillStatus != null && !"".equals(this.productBillStatus)){
			this.productBillStatusName = ResultConstant.PLM_OB_PRODUCT_BILL_STATUS.getString(productBillStatus);
		}
		return this.productBillStatusName;

	}

	public void setProductBillStatusName(String productBillStatusName) {
		this.productBillStatusName = productBillStatusName;
	}

	public String getProductCreator() {
		return productCreator;
	}

	public void setProductCreator(String productCreator) {
		this.productCreator = productCreator;
	}
}
