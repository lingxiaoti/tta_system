package com.sie.watsons.base.ob.model.entities.readonly;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * PlmDevelopmentIngredientsEntity_HI_RO Entity Object
 * Thu Aug 29 14:13:08 CST 2019  Auto Generate
 */

public class PlmDevelopmentIngredientsEntity_HI_RO {
    private String inciOrCiSource;
    private String casNumber;
    private String einecsNumber;
    private String function;
    private java.math.BigDecimal w1;
    private java.math.BigDecimal w2;
    private java.math.BigDecimal w3;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer plmDevelopmentIngredientsId;
    private Integer plmDevelopmentInfoId;
    private Integer plmProjectId;
    private String rawMaterialName;
    private String rawMaterialProducer;
    private String rawMaterialSourceArea;
    private String standardChineseName;
    private String inciName;
    private Integer operatorUserId;

    public static final String QUERY_SQL = "SELECT\n" +
			"       pdi.PLM_DEVELOPMENT_INGREDIENTS_ID as plmDevelopmentIngredientsId,\n" +
			"       pdi.PLM_DEVELOPMENT_INFO_ID as plmDevelopmentInfoId,\n" +
			"       pdi.PLM_PROJECT_ID as plmProjectId,\n" +
			"       pdi.RAW_MATERIAL_NAME as rawMaterialName,\n" +
			"       pdi.RAW_MATERIAL_PRODUCER as rawMaterialProducer,\n" +
			"       pdi.RAW_MATERIAL_SOURCE_AREA as rawMaterialSourceArea,\n" +
			"       pdi.STANDARD_CHINESE_NAME as standardChineseName,\n" +
			"       pdi.INCI_NAME as inciName,\n" +
			"       pdi.INCI_OR_CI_SOURCE as inciOrCiSource,\n" +
			"       pdi.CAS_NUMBER as casNumber,\n" +
			"       pdi.EINECS_NUMBER as einecsNumber,\n" +
			"       pdi.FUNCTION as function,\n" +
			"       pdi.W1 as w1,\n" +
			"       pdi.W2 as w2,\n" +
			"       pdi.W3 as w3,\n" +
			"       pdi.CREATED_BY as createdBy,\n" +
			"       pdi.LAST_UPDATED_BY as lastUpdatedBy,\n" +
			"       pdi.LAST_UPDATE_DATE as lastUpdateDate,\n" +
			"       pdi.LAST_UPDATE_LOGIN as lastUpdateLogin,\n" +
			"       pdi.VERSION_NUM as versionNum,\n" +
			"       pdi.CREATION_DATE as creationDate\n" +
			"FROM PLM_DEVELOPMENT_INGREDIENTS pdi\n" +
			"WHERE 1=1 ";

    private String barcode;
    private String productName;
    private String productNameCn;
    private String productNameEn;
    private String supplierName;
	private String producer;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date startDate;

    public static final String REPORT_SQL = "SELECT \n" +
			"       pdi2.BARCODE  as barcode,\n" +
			"       pdi2.PRODUCT_NAME    as productName,\n" +
			"       pdi2.PRODUCT_NAME_CN as productNameCn,\n" +
			"       pdi2.PRODUCT_NAME_EN as productNameEn,\n" +
			"       pdi2.SUPPLIER_NAME   as supplierName,\n" +
			"       pdi2.PRODUCER as producer,\n" +
			"       pdi2.START_DATE      as startDate,\n" +
			"       pdi.RAW_MATERIAL_NAME as rawMaterialName,\n" +
			"       pdi.RAW_MATERIAL_PRODUCER as rawMaterialProducer,\n" +
			"       pdi.RAW_MATERIAL_SOURCE_AREA as rawMaterialSourceArea,\n" +
			"       pdi.STANDARD_CHINESE_NAME   as standardChineseName,\n" +
			"       pdi.INCI_NAME as inciName,\n" +
			"       pdi.INCI_OR_CI_SOURCE       as inciOrCiSource,\n" +
			"       pdi.CAS_NUMBER       as casNumber,\n" +
			"       pdi.EINECS_NUMBER    as einecsNumber,\n" +
			"       pdi.FUNCTION  as function,\n" +
			"       pdi.W1 as w1,\n" +
			"       pdi.W2 as w2,\n" +
			"       pdi.W3 as w3\n" +
			"FROM PLM_DEVELOPMENT_INGREDIENTS pdi \n" +
			"LEFT JOIN PLM_DEVELOPMENT_INFO pdi2 ON pdi.PLM_DEVELOPMENT_INFO_ID = pdi2.PLM_DEVELOPMENT_INFO_ID\n" +
			"WHERE 1=1 ";

	public void setInciOrCiSource(String inciOrCiSource) {
		this.inciOrCiSource = inciOrCiSource;
	}

	
	public String getInciOrCiSource() {
		return inciOrCiSource;
	}

	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}

	
	public String getCasNumber() {
		return casNumber;
	}

	public void setEinecsNumber(String einecsNumber) {
		this.einecsNumber = einecsNumber;
	}

	
	public String getEinecsNumber() {
		return einecsNumber;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	
	public String getFunction() {
		return function;
	}

	public void setW1(java.math.BigDecimal w1) {
		this.w1 = w1;
	}

	
	public java.math.BigDecimal getW1() {
		return w1;
	}

	public void setW2(java.math.BigDecimal w2) {
		this.w2 = w2;
	}

	
	public java.math.BigDecimal getW2() {
		return w2;
	}

	public void setW3(java.math.BigDecimal w3) {
		this.w3 = w3;
	}

	
	public java.math.BigDecimal getW3() {
		return w3;
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

	public void setPlmDevelopmentIngredientsId(Integer plmDevelopmentIngredientsId) {
		this.plmDevelopmentIngredientsId = plmDevelopmentIngredientsId;
	}

	
	public Integer getPlmDevelopmentIngredientsId() {
		return plmDevelopmentIngredientsId;
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

	public void setRawMaterialName(String rawMaterialName) {
		this.rawMaterialName = rawMaterialName;
	}

	
	public String getRawMaterialName() {
		return rawMaterialName;
	}

	public void setRawMaterialProducer(String rawMaterialProducer) {
		this.rawMaterialProducer = rawMaterialProducer;
	}

	
	public String getRawMaterialProducer() {
		return rawMaterialProducer;
	}

	public void setRawMaterialSourceArea(String rawMaterialSourceArea) {
		this.rawMaterialSourceArea = rawMaterialSourceArea;
	}

	
	public String getRawMaterialSourceArea() {
		return rawMaterialSourceArea;
	}

	public void setStandardChineseName(String standardChineseName) {
		this.standardChineseName = standardChineseName;
	}

	
	public String getStandardChineseName() {
		return standardChineseName;
	}

	public void setInciName(String inciName) {
		this.inciName = inciName;
	}

	
	public String getInciName() {
		return inciName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
