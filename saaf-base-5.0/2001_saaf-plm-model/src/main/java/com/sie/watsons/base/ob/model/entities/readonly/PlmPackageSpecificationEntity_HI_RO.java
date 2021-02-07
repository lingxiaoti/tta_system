package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmPackageSpecificationEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:09 CST 2019  Auto Generate
 */

public class PlmPackageSpecificationEntity_HI_RO {
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

    public static final String QUERY_SQL = "SELECT\n" +
			"       pps.PLM_PACKAGE_SPECIFICATION_ID as plmPackageSpecificationId,\n" +
			"       pps.PLM_DEVELOPMENT_INFO_ID as plmDevelopmentInfoId,\n" +
			"       pps.PLM_PROJECT_ID as plmProjectId,\n" +
			"       pps.PACKAGE_NUMBER as packageNumber,\n" +
			"       pps.ITEM_LOCALIZATION_OR_NAME as itemLocalizationOrName,\n" +
			"       pps.MATERIAL as material,\n" +
			"       pps.NET_WEIGHT_PER_GRAM as netWeightPerGram,\n" +
			"       pps.RECYCLABLE as recyclable,\n" +
			"       pps.REMARKS as remarks,\n" +
			"       pps.CREATED_BY as createdBy,\n" +
			"       pps.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       pps.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       pps.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       pps.VERSION_NUM as versionNum,\n" +
			"       pps.CREATION_DATE as creationDate\n" +
			"FROM PLM_PACKAGE_SPECIFICATION pps\n" +
			"WHERE 1=1  ";

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	private String supplierName;
	private String producer;
	private String productNo;
	private String barcode;
	private String productNameCn;
	private String productNameEn;
	private String productName;

    public static final String REPORT_SQL = "SELECT pdi.START_DATE as startDate,\n" +
			"       pdi.SUPPLIER_NAME  as supplierName,\n" +
			"       pdi.PRODUCER   as producer,\n" +
			"       pdi.PRODUCT_NO as productNo,\n" +
			"       pdi.BARCODE  as barcode,\n" +
			"       pdi.PRODUCT_NAME_CN  as productNameCn,\n" +
			"       pdi.PRODUCT_NAME_EN as productNameEn,\n" +
			"       pdi.PRODUCT_NAME as productName,\n" +
			"       pps.ITEM_LOCALIZATION_OR_NAME  as itemLocalizationOrName,\n" +
			"       pps.MATERIAL as material,\n" +
			"       pps.NET_WEIGHT_PER_GRAM  as netWeightPerGram,\n" +
			"       pps.RECYCLABLE as recyclable,\n" +
			"       pdi.PLM_DEVELOPMENT_INFO_ID as plmDevelopmentInfoId\n" +
			"FROM PLM_PACKAGE_SPECIFICATION pps\n" +
			"LEFT JOIN PLM_DEVELOPMENT_INFO pdi ON pps.PLM_DEVELOPMENT_INFO_ID = pdi.PLM_DEVELOPMENT_INFO_ID\n" +
			"WHERE 1=1 ";

	public void setPlmPackageSpecificationId(Integer plmPackageSpecificationId) {
		this.plmPackageSpecificationId = plmPackageSpecificationId;
	}

	
	public Integer getPlmPackageSpecificationId() {
		return plmPackageSpecificationId;
	}

	public void setPlmDevelopmentInfoId(Integer plmDevelopmentInfoId) {
		this.plmDevelopmentInfoId = plmDevelopmentInfoId;
	}

	
	public Integer getPlmDevelopmentInfoId() {
		return plmDevelopmentInfoId;
	}

	public void setPlmProjectId(Integer plmProjectId) {
		this.plmProjectId = plmProjectId;
	}

	
	public Integer getPlmProjectId() {
		return plmProjectId;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}

	
	public String getPackageNumber() {
		return packageNumber;
	}

	public void setItemLocalizationOrName(String itemLocalizationOrName) {
		this.itemLocalizationOrName = itemLocalizationOrName;
	}

	
	public String getItemLocalizationOrName() {
		return itemLocalizationOrName;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	
	public String getMaterial() {
		return material;
	}

	public void setNetWeightPerGram(Double netWeightPerGram) {
		this.netWeightPerGram = netWeightPerGram;
	}

	
	public Double getNetWeightPerGram() {
		return netWeightPerGram;
	}

	public void setRecyclable(String recyclable) {
		this.recyclable = recyclable;
	}

	
	public String getRecyclable() {
		return recyclable;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getProductNameCn() {
		return productNameCn;
	}

	public void setProductNameCn(String productNameCn) {
		this.productNameCn = productNameCn;
	}

	public String getProductNameEn() {
		return productNameEn;
	}

	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
