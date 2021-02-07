package com.sie.watsons.base.productEco.model.entities.readonly;

import javax.persistence.Version;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

import javax.persistence.Transient;

/**
 * PlmProductHeadEcoEntity_HI_RO Entity Object
 * Fri May 22 14:29:18 CST 2020  Auto Generate
 */

public class PlmProductHeadEcoEntity_HI_RO {

	public static final String QUERY_HEAD = "SELECT\n" +
			"  PRODUCT.PRODUCT_HEAD_ID AS productHeadId,\n" +
			"  PRODUCT.eco_Id AS ecoId,\n" +
			"  PRODUCT.eco_No AS ecoNo,\n" +
			"  PRODUCT.eco_Status AS ecoStatus,\n" +
			"  PRODUCT.eco_dept_id AS ecoDeptId,\n" +
			"  PRODUCT.PLM_CODE AS plmCode,\n" +
			"  PRODUCT.RMS_CODE AS rmsCode,\n" +
			"  PRODUCT.OB_ID AS obId,\n" +
			"  PRODUCT.GROUP_BRAND AS groupBrand,\n" +
			"  pgb.plm_group_brand_name AS  plmGroupBrandName,\n" +
			"  PRODUCT.DEPARTMENT AS department,\n" +
			"  PRODUCT.DEPARTMENT_DESCRIPT AS departmentDescript,\n" +
			"  PRODUCT.CLASSES AS classes,\n" +
			"  PRODUCT.CLASS_DESC AS classDesc,\n" +
			"  PRODUCT.SUB_CLASS AS subClass,\n" +
			"  PRODUCT.SUBCLASS_DESC AS subclassDesc,\n" +
			"  PRODUCT.PURCHASE_TYPE AS purchaseType,\n" +
			"  PRODUCT.PRODUCT_NAME AS productName,\n" +
			"  PRODUCT.PRODUCT_ENAME AS productEname,\n" +
			"  PRODUCT.PRODUCT_TYPE AS productType,\n" +
			"  PRODUCT.BRANDNAME_CN AS brandnameCn,\n" +
			"  PRODUCT.BRANDNAME_EN AS brandnameEn,\n" +
			"  PRODUCT.COUNT_UNIT AS countUnit,\n" +
			"  PRODUCT.OTHER_INFO AS otherInfo,\n" +
			"  PRODUCT.MARKER_CHANNEL AS markerChannel,\n" +
			"  PRODUCT.PRODUCT_SHAPE AS productShape,\n" +
			"  PRODUCT.VERSION_NUM AS versionNum,\n" +
			"  PRODUCT.CREATION_DATE AS creationDate,\n" +
			"  PRODUCT.CREATED_BY AS createdBy,\n" +
			"  PRODUCT.LAST_UPDATED_BY AS lastUpdatedBy,\n" +
			"  PRODUCT.LAST_UPDATE_DATE AS lastUpdateDate,\n" +
			"  PRODUCT.LAST_UPDATE_LOGIN AS lastUpdateLogin,\n" +
			"  PRODUCT.GROSS_EARNINGS AS grossEarnings,\n" +
			"  PRODUCT.RATE_CLASS_CODE AS rateClassCode,\n" +
			"  PRODUCT.DAY_DAMAGE AS dayDamage,\n" +
			"  PRODUCT.SPECIAL_LICENCE AS specialLicence,\n" +
			"  PRODUCT.PRODUCT_RESOURCE AS productResource,\n" +
			"  PRODUCT.PRODUCT_CATEGEERY AS productCategeery,\n" +
			"  PRODUCT.UNIQUE_COMMODITIES AS uniqueCommodities,\n" +
			"  PRODUCT.SPECIALTY_PRODUCT AS specialtyProduct,\n" +
			"  PRODUCT.PRODUCT_PROPERTIES AS productProperties,\n" +
			"  PRODUCT.BUYING_LEVEL AS buyingLevel,\n" +
			"  PRODUCT.DANGEROUS_PRODUCT AS dangerousProduct,\n" +
			"  PRODUCT.POS_INFO AS posInfo,\n" +
			"  PRODUCT.INTERNATION_PRODUCT AS internationProduct,\n" +
			"  PRODUCT.SESION_PRODUCT AS sesionProduct,\n" +
			"  PRODUCT.TOP_PRODUCT AS topProduct,\n" +
			"  PRODUCT.MOTHER_COMPANY AS motherCompany,\n" +
			"  uda3.uda_value_desc AS motherCompanyName,\n" +
			"  PRODUCT.BLUECAP_PRODUCT AS bluecapProduct,\n" +
			"  PRODUCT.CROSSBORDER_PRODUCT AS crossborderProduct,\n" +
			"  PRODUCT.VC_PRODUCT AS vcProduct,\n" +
			"  PRODUCT.WAREHOUSE_RESOURCE AS warehouseResource,\n" +
			"  PRODUCT.COMPANY_DELETION AS companyDeletion,\n" +
			"  PRODUCT.ORIGIN_COUNTRY AS originCountry,\n" +
			"  PRODUCT.UNIT AS unit,\n" +
			"  PRODUCT.WAREHOUSE_GET_DAY AS warehouseGetDay,\n" +
			"  PRODUCT.WAREHOUSE_POST_DAY AS warehousePostDay,\n" +
			"  PRODUCT.RANG_OB AS rangOb,\n" +
			"  uda2.uda_value_desc as rangObName,\n" +
			"  PRODUCT.POWER_OB AS powerOb,\n" +
			"  uda1.uda_value_desc as powerObName,\n" +
			"  PRODUCT.TIER AS tier,\n" +
			"  PRODUCT.SPECIAL_REQUIER AS specialRequier,\n" +
			"  PRODUCT.TRANSPORT_STORAGE AS transportStorage,\n" +
			"  PRODUCT.PRODUCT_LICENSE AS productLicense,\n" +
			"  PRODUCT.ORDER_STATUS AS orderStatus,\n" +
			"  PRODUCT.START_DATE AS startDate,\n" +
			"  PRODUCT.IS_UPDATECHECK AS isUpdatecheck,\n" +
			" PRODUCT.PRICEWAR_PRODUCT AS pricewarProduct,\n" +
			" PRODUCT.CONSIGNMENT_TYPE AS consignmentType,\n" +
			" PRODUCT.CONSIGNMENT_RATE AS consignmentRate,\n" +
			" PRODUCT.SALES_QTY AS salesQty,\n" +
			" PRODUCT.CONSULT_PRODUCTNO AS consultProductno,\n" +
			" PRODUCT.IM_PRODUCTNO AS imProductno,\n" +
			" PRODUCT.IM_PRODUCTNAME AS imProductname,\n" +
			" PRODUCT.CONSULT_PRODUCTNAME AS consultProductname,\n" +
			" PRODUCT.PROV_CONDITION AS provCondition,\n" +
			" PRODUCT.CONSULT_DATE AS consultDate,\n" +
			" PRODUCT.CONSULT_ENDDATE AS consultEnddate,\n" +
			" PRODUCT.IS_DIARYPRODUCT AS isDiaryproduct,\n" +
			"   og.area_cn as originCountryName,\n"+
			"   og.area_en as originCountryEname,\n"+
			"   PRODUCT.SUPPLIER_COUNT AS supplierCount,\n" +
			"  PRODUCT.PRODUCT_RETURN AS productReturn,\n" +
			"   PRODUCT.QA_FILE_ID AS qaFileId,PRODUCT.QA_FILENAME AS qaFilename,\n" +
			"    PRODUCT.CONDITION AS condition, PRODUCT.ALL_TIER As allTier, \n" +
			"    PRODUCT.TIER1 AS tier1, PRODUCT.TIER2 AS tier2, PRODUCT.TIER345 AS tier345,\n" +
			"     PRODUCT.STORE_TYPE AS storeType, PRODUCT.TRADE_ZONE AS tradeZone,\n" +
			"      PRODUCT.MAIN_PRODUCTNAME AS mainProductname, PRODUCT.MAIN_PRODUCTID AS mainProductid, \n" +
			"      PRODUCT.PROCESS_INSTANCEID AS processInstanceid, PRODUCT.UPDATE_INSTANCEID AS updateInstanceid, \n" +
			"       PRODUCT.VALID_DAYS AS validDays, PRODUCT.SX_DAYS AS sxDays,  PRODUCT.SERIAL_ID AS serialId, \n" +
			"       PRODUCT.SERIAL_NAME AS serialName,  PRODUCT.CLASS_ID AS classId, PRODUCT.CLASS_NAME AS className, \n" +
			"        PRODUCT.ADD_PROCESSNAME AS addProcessname,  PRODUCT.UPDATE_PROCEKEY AS updateProcekey,  \n" +
			"        PRODUCT.CREATEDSTR AS createdstr, nvl(PRODUCT.ISRETURN_SALE,'0') AS isreturnSale, \n" +
			"        nvl(PRODUCT.ISUPDATE_PRICE,'0') AS isupdatePrice, nvl(PRODUCT.ISITERATE_SALES,'0') AS isiterateSales,\n" +
			"         PRODUCT.ORIGIN_COUNTRYID AS originCountryid, PRODUCT.SUG_RETAILPRICE AS sugRetailprice,\n" +
			"          PRODUCT.RATE_CLASS AS rateClass, PRODUCT.CREATED_ENAME AS createdEname, \n" +
			"          PRODUCT.CREATED_EMP AS createdEmp,  PRODUCT.RMS_STATUS AS rmsStatus, \n" +
			"          PRODUCT.RMS_REMAKE AS rmsRemake,  PRODUCT.USER_DEPT AS userDept, PRODUCT.USER_GROUPCODE AS userGroupcode, \n" +
			"           PRODUCT.POG_WAYS AS pogWays, PRODUCT.POG_DEPARMENT AS pogDeparment,  PRODUCT.RMS_UPDATE AS rmsUpdate, \n" +
			"            PRODUCT.CONDITIONS as  conditions, PRODUCT.special_requier_name AS specialRequierName,\n" +
			"             PRODUCT.transport_storage_name AS transportStorageName,\n" +
			"              PRODUCT.product_license_name AS productLicenseName,\n" +
			"               PRODUCT.standard_of_unit as standardOfUnit, \n" +
			"               PRODUCT.is_rms_rever AS isRmsRever, \n" +
			"                PRODUCT.rms_rever_businesskey AS rmsReverBusinesskey,\n" +
			"                  PRODUCT.brand_cn_uda_id AS BrandCnUdaId, \n" +
			"                  PRODUCT.brand_cn_uda_value AS BrandCnUdaValue, \n" +
			"                  PRODUCT.brand_en_uda_id AS BrandEnUdaId, \n" +
			"                  PRODUCT.brand_en_uda_value AS BrandEnUdaValue  FROM\n" +
			"  PLM_PRODUCT_HEAD_ECO  PRODUCT  \n" +
			"  left join plm_uda_attribute  uda1 on PRODUCT.Power_Ob = uda1.uda_value  and uda1.uda_id=48\n" +
			"  left join plm_uda_attribute  uda2 on PRODUCT.Rang_Ob = uda2.uda_value  and uda2.uda_id=47\n" +
			"  LEFT JOIN plm_uda_attribute uda3   ON product.mother_company = uda3.uda_value AND uda3.uda_id = 49 \n" +
			"  LEFT JOIN plm_group_brand pgb   ON product.group_brand = pgb.plm_group_brand_id \n" +
			"  left join PLM_COUNTRY_OF_ORIGIN og on product.origin_country=og.name_abbreviation \n"+
			"  where 1=1 and PRODUCT.IS_UPDATECHECK='0' ";



    private Integer ecoId;
    private String ecoNo;
    private String ecoStatus;
	private String ecoStatusName;
    private Integer ecoDeptId;
    private Integer productHeadId;
    private String plmCode;
    private String department;
    private String departmentDescript;
    private String classes;
    private String classDesc;
    private String subClass;
    private String subclassDesc;
    private String purchaseType;
	private String purchaseTypestr;
    private String productName;
    private String productEname;
    private String productType;
    private String brandnameCn;
    private String brandnameEn;
    private String countUnit;
    private String otherInfo;
    private String markerChannel;
    private String productShape;
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private String grossEarnings;
    private String rateClassCode;
    private String dayDamage;
    private String specialLicence;
    private String productResource;
    private String productCategeery;
    private String uniqueCommodities;
    private String specialtyProduct;
    private String productProperties;
    private String buyingLevel;
    private String dangerousProduct;
    private String posInfo;
    private String internationProduct;
    private String sesionProduct;
    private String topProduct;
    private String motherCompany;
    private String bluecapProduct;
    private String crossborderProduct;
    private String vcProduct;
    private String warehouseResource;
    private String companyDeletion;
    private String originCountry;
    private String unit;
    private Integer warehouseGetDay;
    private Integer warehousePostDay;
    private String rangOb;
    private String powerOb;
    private String tier;
    private String specialRequier;
    private String transportStorage;
    private String productLicense;
    private String orderStatus;
	private String orderstatusName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    private Integer supplierCount;
    private String obId;
    private String groupBrand;
    private String isUpdatecheck;
    private String pricewarProduct;
    private String consignmentType;
    private String consignmentRate;
    private String salesQty;
    private String consultProductno;
    private String imProductno;
    private String imProductname;
    private String consultProductname;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date consultDate;
    private String isDiaryproduct;
    private String provCondition;
    private String productReturn;
	private String productReturnName;
    private String condition;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date consultEnddate;
    private String qaFileId;
    private String qaFilename;
    private String allTier;
    private String tier1;
    private String tier2;
    private String tier345;
    private String storeType;
    private String tradeZone;
    private String mainProductname;
    private String mainProductid;
    private String processInstanceid;
    private String updateInstanceid;
    private Integer checkPerson;
    private String rmsCode;
    private String nextCheckperson;
    private String sugRetailprice;
    private String validDays;
    private Integer sxDays;
    private String serialId;
    private String serialName;
    private String classId;
    private String className;
    private String addProcessname;
    private String updateProcekey;
    private String createdstr;
    private String isreturnSale;
    private String isupdatePrice;
    private String isiterateSales;
    private String originCountryid;
    private String rateClass;
    private String createdEname;
    private String createdEmp;
    private String rmsStatus;
    private String rmsRemake;
    private String userDept;
    private String userGroupcode;
    private String pogWays;
    private String pogDeparment;
    private String rmsUpdate;
    private String conditions;
    private String specialRequierName;
    private String transportStorageName;
    private String productLicenseName;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date rmsSynchrDate;
    private String standardOfUnit;
    private String isRmsRever;
    private String rmsReverBusinesskey;
    private Integer operatorUserId;

	private Integer brandCnUdaId;
	private Integer brandCnUdaValue;
	private Integer brandEnUdaId;
	private Integer brandEnUdaValue;
	private String otherinfoname;// 特殊商品类型字符串
	private String originCountryName;
	private String originCountryEname;
	private String plmGroupBrandName;
	private String motherCompanyName;
	private String rangObName;
	private String powerObName;

	//	private List<PlmProductPicfileTableEcoEntity_HI_RO> picfileTableEcos;
//	private List<PlmProductPriceEcoEntity_HI_RO> priceEcos;
//	private List<PlmProductQaFileEcoEntity_HI_RO> qaFileEcos;
//	private List<PlmProductSupplierInfoEcoEntity_HI_RO> supplierEcos;
//	private List<PlmProductDrugEcoEntity_HI_RO> drugEcos;

//	System.out.println("--->SQL ::" + QUERY_HEAD);
//		System.out.println(param.toJSONString());
public static void main(String[] args) {
	System.out.println(" --->>>::: "+ QUERY_HEAD);
}

	public String getOriginCountryName() {
		return originCountryName;
	}

	public void setOriginCountryName(String originCountryName) {
		this.originCountryName = originCountryName;
	}

	public String getOriginCountryEname() {
		return originCountryEname;
	}

	public void setOriginCountryEname(String originCountryEname) {
		this.originCountryEname = originCountryEname;
	}

	public void setProductReturnName(String productReturnName) {
		this.productReturnName = productReturnName;
	}
	public Integer getBrandCnUdaId() {		return brandCnUdaId;	}
	public void setBrandCnUdaId(Integer brandCnUdaId) {		this.brandCnUdaId = brandCnUdaId;	}

	public Integer getBrandCnUdaValue() {		return brandCnUdaValue;	}
	public void setBrandCnUdaValue(Integer brandCnUdaValue) {		this.brandCnUdaValue = brandCnUdaValue;	}

	public Integer getBrandEnUdaId() {		return brandEnUdaId;	}
	public void setBrandEnUdaId(Integer brandEnUdaId) {		this.brandEnUdaId = brandEnUdaId;	}

	public Integer getBrandEnUdaValue() {		return brandEnUdaValue;	}
	public void setBrandEnUdaValue(Integer brandEnUdaValue) {		this.brandEnUdaValue = brandEnUdaValue;	}

//	public List<PlmProductDrugEcoEntity_HI_RO> getDrugEcos() {return drugEcos;}
//	public void setDrugEcos(List<PlmProductDrugEcoEntity_HI_RO> drugEcos) {this.drugEcos = drugEcos;}
//
//	public List<PlmProductPicfileTableEcoEntity_HI_RO> getPicfileTableEcos() {return picfileTableEcos;}
//	public void setPicfileTableEcos(List<PlmProductPicfileTableEcoEntity_HI_RO> picfileTableEcos) {this.picfileTableEcos = picfileTableEcos;}
//
//	public List<PlmProductPriceEcoEntity_HI_RO> getPriceEcos() {return priceEcos;}
//	public void setPriceEcos(List<PlmProductPriceEcoEntity_HI_RO> priceEcos) {this.priceEcos = priceEcos;}
//
//	public List<PlmProductQaFileEcoEntity_HI_RO> getQaFileEcos() {return qaFileEcos;}
//	public void setQaFileEcos(List<PlmProductQaFileEcoEntity_HI_RO> qaFileEcos) {this.qaFileEcos = qaFileEcos;}
//
//	public List<PlmProductSupplierInfoEcoEntity_HI_RO> getSupplierEcos() {return supplierEcos;}
//	public void setSupplierEcos(List<PlmProductSupplierInfoEcoEntity_HI_RO> supplierEcos) {this.supplierEcos = supplierEcos;}


	public void setPurchaseTypestr(String purchaseTypestr) {
		this.purchaseTypestr = purchaseTypestr;
	}

	public void setOrderstatusName(String orderstatusName) {
		this.orderstatusName = orderstatusName;
	}

	public void setOtherinfoname(String otherinfoname) {
		this.otherinfoname = otherinfoname;
	}

	public String getPlmGroupBrandName() {
		return plmGroupBrandName;
	}

	public void setPlmGroupBrandName(String plmGroupBrandName) {
		this.plmGroupBrandName = plmGroupBrandName;
	}

	public String getMotherCompanyName() {
		return motherCompanyName;
	}

	public void setMotherCompanyName(String motherCompanyName) {
		this.motherCompanyName = motherCompanyName;
	}

	public String getRangObName() {
		return rangObName;
	}

	public void setRangObName(String rangObName) {
		this.rangObName = rangObName;
	}

	public String getPowerObName() {
		return powerObName;
	}

	public void setPowerObName(String powerObName) {
		this.powerObName = powerObName;
	}

	public void setEcoId(Integer ecoId) {
		this.ecoId = ecoId;
	}

	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setEcoNo(String ecoNo) {
		this.ecoNo = ecoNo;
	}

	
	public String getEcoNo() {
		return ecoNo;
	}

	public void setEcoStatus(String ecoStatus) {
		this.ecoStatus = ecoStatus;
	}

	
	public String getEcoStatus() {
		return ecoStatus;
	}




	public void setEcoDeptId(Integer ecoDeptId) {
		this.ecoDeptId = ecoDeptId;
	}

	
	public Integer getEcoDeptId() {
		return ecoDeptId;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
	}

	
	public String getPlmCode() {
		return plmCode;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	
	public String getDepartment() {
		return department;
	}

	public void setDepartmentDescript(String departmentDescript) {
		this.departmentDescript = departmentDescript;
	}

	
	public String getDepartmentDescript() {
		return departmentDescript;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	
	public String getClasses() {
		return classes;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	
	public String getClassDesc() {
		return classDesc;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	
	public String getSubClass() {
		return subClass;
	}

	public void setSubclassDesc(String subclassDesc) {
		this.subclassDesc = subclassDesc;
	}

	
	public String getSubclassDesc() {
		return subclassDesc;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	
	public String getProductEname() {
		return productEname;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	
	public String getProductType() {
		return productType;
	}

	public void setBrandnameCn(String brandnameCn) {
		this.brandnameCn = brandnameCn;
	}

	
	public String getBrandnameCn() {
		return brandnameCn;
	}

	public void setBrandnameEn(String brandnameEn) {
		this.brandnameEn = brandnameEn;
	}

	
	public String getBrandnameEn() {
		return brandnameEn;
	}

	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}

	
	public String getCountUnit() {
		return countUnit;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	
	public String getOtherInfo() {
		return otherInfo;
	}

	public void setMarkerChannel(String markerChannel) {
		this.markerChannel = markerChannel;
	}

	
	public String getMarkerChannel() {
		return markerChannel;
	}

	public void setProductShape(String productShape) {
		this.productShape = productShape;
	}

	
	public String getProductShape() {
		return productShape;
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

	public void setGrossEarnings(String grossEarnings) {
		this.grossEarnings = grossEarnings;
	}

	
	public String getGrossEarnings() {
		return grossEarnings;
	}

	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
	}

	
	public String getRateClassCode() {
		return rateClassCode;
	}

	public void setDayDamage(String dayDamage) {
		this.dayDamage = dayDamage;
	}

	
	public String getDayDamage() {
		return dayDamage;
	}

	public void setSpecialLicence(String specialLicence) {
		this.specialLicence = specialLicence;
	}

	
	public String getSpecialLicence() {
		return specialLicence;
	}

	public void setProductResource(String productResource) {
		this.productResource = productResource;
	}

	
	public String getProductResource() {
		return productResource;
	}

	public void setProductCategeery(String productCategeery) {
		this.productCategeery = productCategeery;
	}

	
	public String getProductCategeery() {
		return productCategeery;
	}

	public void setUniqueCommodities(String uniqueCommodities) {
		this.uniqueCommodities = uniqueCommodities;
	}

	
	public String getUniqueCommodities() {
		return uniqueCommodities;
	}

	public void setSpecialtyProduct(String specialtyProduct) {
		this.specialtyProduct = specialtyProduct;
	}

	
	public String getSpecialtyProduct() {
		return specialtyProduct;
	}

	public void setProductProperties(String productProperties) {
		this.productProperties = productProperties;
	}

	
	public String getProductProperties() {
		return productProperties;
	}

	public void setBuyingLevel(String buyingLevel) {
		this.buyingLevel = buyingLevel;
	}

	
	public String getBuyingLevel() {
		return buyingLevel;
	}

	public void setDangerousProduct(String dangerousProduct) {
		this.dangerousProduct = dangerousProduct;
	}

	
	public String getDangerousProduct() {
		return dangerousProduct;
	}

	public void setPosInfo(String posInfo) {
		this.posInfo = posInfo;
	}

	
	public String getPosInfo() {
		return posInfo;
	}

	public void setInternationProduct(String internationProduct) {
		this.internationProduct = internationProduct;
	}

	
	public String getInternationProduct() {
		return internationProduct;
	}

	public void setSesionProduct(String sesionProduct) {
		this.sesionProduct = sesionProduct;
	}

	
	public String getSesionProduct() {
		return sesionProduct;
	}

	public void setTopProduct(String topProduct) {
		this.topProduct = topProduct;
	}

	
	public String getTopProduct() {
		return topProduct;
	}

	public void setMotherCompany(String motherCompany) {
		this.motherCompany = motherCompany;
	}

	
	public String getMotherCompany() {
		return motherCompany;
	}

	public void setBluecapProduct(String bluecapProduct) {
		this.bluecapProduct = bluecapProduct;
	}

	
	public String getBluecapProduct() {
		return bluecapProduct;
	}

	public void setCrossborderProduct(String crossborderProduct) {
		this.crossborderProduct = crossborderProduct;
	}

	
	public String getCrossborderProduct() {
		return crossborderProduct;
	}

	public void setVcProduct(String vcProduct) {
		this.vcProduct = vcProduct;
	}

	
	public String getVcProduct() {
		return vcProduct;
	}

	public void setWarehouseResource(String warehouseResource) {
		this.warehouseResource = warehouseResource;
	}

	
	public String getWarehouseResource() {
		return warehouseResource;
	}

	public void setCompanyDeletion(String companyDeletion) {
		this.companyDeletion = companyDeletion;
	}

	
	public String getCompanyDeletion() {
		return companyDeletion;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	
	public String getOriginCountry() {
		return originCountry;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	
	public String getUnit() {
		return unit;
	}

	public void setWarehouseGetDay(Integer warehouseGetDay) {
		this.warehouseGetDay = warehouseGetDay;
	}

	
	public Integer getWarehouseGetDay() {
		return warehouseGetDay;
	}

	public void setWarehousePostDay(Integer warehousePostDay) {
		this.warehousePostDay = warehousePostDay;
	}

	
	public Integer getWarehousePostDay() {
		return warehousePostDay;
	}

	public void setRangOb(String rangOb) {
		this.rangOb = rangOb;
	}

	
	public String getRangOb() {
		return rangOb;
	}

	public void setPowerOb(String powerOb) {
		this.powerOb = powerOb;
	}

	
	public String getPowerOb() {
		return powerOb;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	
	public String getTier() {
		return tier;
	}

	public void setSpecialRequier(String specialRequier) {
		this.specialRequier = specialRequier;
	}

	
	public String getSpecialRequier() {
		return specialRequier;
	}

	public void setTransportStorage(String transportStorage) {
		this.transportStorage = transportStorage;
	}

	
	public String getTransportStorage() {
		return transportStorage;
	}

	public void setProductLicense(String productLicense) {
		this.productLicense = productLicense;
	}

	
	public String getProductLicense() {
		return productLicense;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	public Date getStartDate() {
		return startDate;
	}

	public void setSupplierCount(Integer supplierCount) {
		this.supplierCount = supplierCount;
	}

	
	public Integer getSupplierCount() {
		return supplierCount;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	
	public String getObId() {
		return obId;
	}

	public void setGroupBrand(String groupBrand) {
		this.groupBrand = groupBrand;
	}

	
	public String getGroupBrand() {
		return groupBrand;
	}

	public void setIsUpdatecheck(String isUpdatecheck) {
		this.isUpdatecheck = isUpdatecheck;
	}

	
	public String getIsUpdatecheck() {
		return isUpdatecheck;
	}

	public void setPricewarProduct(String pricewarProduct) {
		this.pricewarProduct = pricewarProduct;
	}

	
	public String getPricewarProduct() {
		return pricewarProduct;
	}

	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	
	public String getConsignmentType() {
		return consignmentType;
	}

	public void setConsignmentRate(String consignmentRate) {
		this.consignmentRate = consignmentRate;
	}

	
	public String getConsignmentRate() {
		return consignmentRate;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
	}

	
	public String getSalesQty() {
		return salesQty;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	
	public String getConsultProductno() {
		return consultProductno;
	}

	public void setImProductno(String imProductno) {
		this.imProductno = imProductno;
	}

	
	public String getImProductno() {
		return imProductno;
	}

	public void setImProductname(String imProductname) {
		this.imProductname = imProductname;
	}

	
	public String getImProductname() {
		return imProductname;
	}

	public void setConsultProductname(String consultProductname) {
		this.consultProductname = consultProductname;
	}

	
	public String getConsultProductname() {
		return consultProductname;
	}

	public void setConsultDate(Date consultDate) {
		this.consultDate = consultDate;
	}

	
	public Date getConsultDate() {
		return consultDate;
	}

	public void setIsDiaryproduct(String isDiaryproduct) {
		this.isDiaryproduct = isDiaryproduct;
	}

	
	public String getIsDiaryproduct() {
		return isDiaryproduct;
	}

	public void setProvCondition(String provCondition) {
		this.provCondition = provCondition;
	}

	
	public String getProvCondition() {
		return provCondition;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	
	public String getProductReturn() {
		return productReturn;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	
	public String getCondition() {
		return condition;
	}

	public void setConsultEnddate(Date consultEnddate) {
		this.consultEnddate = consultEnddate;
	}

	
	public Date getConsultEnddate() {
		return consultEnddate;
	}

	public void setQaFileId(String qaFileId) {
		this.qaFileId = qaFileId;
	}

	
	public String getQaFileId() {
		return qaFileId;
	}

	public void setQaFilename(String qaFilename) {
		this.qaFilename = qaFilename;
	}

	
	public String getQaFilename() {
		return qaFilename;
	}

	public void setAllTier(String allTier) {
		this.allTier = allTier;
	}

	
	public String getAllTier() {
		return allTier;
	}

	public void setTier1(String tier1) {
		this.tier1 = tier1;
	}

	
	public String getTier1() {
		return tier1;
	}

	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}

	
	public String getTier2() {
		return tier2;
	}

	public void setTier345(String tier345) {
		this.tier345 = tier345;
	}

	
	public String getTier345() {
		return tier345;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	
	public String getStoreType() {
		return storeType;
	}

	public void setTradeZone(String tradeZone) {
		this.tradeZone = tradeZone;
	}

	
	public String getTradeZone() {
		return tradeZone;
	}

	public void setMainProductname(String mainProductname) {
		this.mainProductname = mainProductname;
	}

	
	public String getMainProductname() {
		return mainProductname;
	}

	public void setMainProductid(String mainProductid) {
		this.mainProductid = mainProductid;
	}

	
	public String getMainProductid() {
		return mainProductid;
	}

	public void setProcessInstanceid(String processInstanceid) {
		this.processInstanceid = processInstanceid;
	}

	
	public String getProcessInstanceid() {
		return processInstanceid;
	}

	public void setUpdateInstanceid(String updateInstanceid) {
		this.updateInstanceid = updateInstanceid;
	}

	
	public String getUpdateInstanceid() {
		return updateInstanceid;
	}

	public void setCheckPerson(Integer checkPerson) {
		this.checkPerson = checkPerson;
	}

	
	public Integer getCheckPerson() {
		return checkPerson;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setNextCheckperson(String nextCheckperson) {
		this.nextCheckperson = nextCheckperson;
	}

	
	public String getNextCheckperson() {
		return nextCheckperson;
	}

	public void setSugRetailprice(String sugRetailprice) {
		this.sugRetailprice = sugRetailprice;
	}

	
	public String getSugRetailprice() {
		return sugRetailprice;
	}

	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}

	
	public String getValidDays() {
		return validDays;
	}

	public void setSxDays(Integer sxDays) {
		this.sxDays = sxDays;
	}

	
	public Integer getSxDays() {
		return sxDays;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	
	public String getSerialId() {
		return serialId;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	
	public String getSerialName() {
		return serialName;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	
	public String getClassId() {
		return classId;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
	public String getClassName() {
		return className;
	}

	public void setAddProcessname(String addProcessname) {
		this.addProcessname = addProcessname;
	}

	
	public String getAddProcessname() {
		return addProcessname;
	}

	public void setUpdateProcekey(String updateProcekey) {
		this.updateProcekey = updateProcekey;
	}

	
	public String getUpdateProcekey() {
		return updateProcekey;
	}

	public void setCreatedstr(String createdstr) {
		this.createdstr = createdstr;
	}

	
	public String getCreatedstr() {
		return createdstr;
	}

	public void setIsreturnSale(String isreturnSale) {
		this.isreturnSale = isreturnSale;
	}

	
	public String getIsreturnSale() {
		return isreturnSale;
	}

	public void setIsupdatePrice(String isupdatePrice) {
		this.isupdatePrice = isupdatePrice;
	}

	
	public String getIsupdatePrice() {
		return isupdatePrice;
	}

	public void setIsiterateSales(String isiterateSales) {
		this.isiterateSales = isiterateSales;
	}

	
	public String getIsiterateSales() {
		return isiterateSales;
	}

	public void setOriginCountryid(String originCountryid) {
		this.originCountryid = originCountryid;
	}

	
	public String getOriginCountryid() {
		return originCountryid;
	}

	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}

	
	public String getRateClass() {
		return rateClass;
	}

	public void setCreatedEname(String createdEname) {
		this.createdEname = createdEname;
	}

	
	public String getCreatedEname() {
		return createdEname;
	}

	public void setCreatedEmp(String createdEmp) {
		this.createdEmp = createdEmp;
	}

	
	public String getCreatedEmp() {
		return createdEmp;
	}

	public void setRmsStatus(String rmsStatus) {
		this.rmsStatus = rmsStatus;
	}

	
	public String getRmsStatus() {
		return rmsStatus;
	}

	public void setRmsRemake(String rmsRemake) {
		this.rmsRemake = rmsRemake;
	}

	
	public String getRmsRemake() {
		return rmsRemake;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	
	public String getUserDept() {
		return userDept;
	}

	public void setUserGroupcode(String userGroupcode) {
		this.userGroupcode = userGroupcode;
	}

	
	public String getUserGroupcode() {
		return userGroupcode;
	}

	public void setPogWays(String pogWays) {
		this.pogWays = pogWays;
	}

	
	public String getPogWays() {
		return pogWays;
	}

	public void setPogDeparment(String pogDeparment) {
		this.pogDeparment = pogDeparment;
	}

	
	public String getPogDeparment() {
		return pogDeparment;
	}

	public void setRmsUpdate(String rmsUpdate) {
		this.rmsUpdate = rmsUpdate;
	}

	
	public String getRmsUpdate() {
		return rmsUpdate;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	
	public String getConditions() {
		return conditions;
	}

	public void setSpecialRequierName(String specialRequierName) {
		this.specialRequierName = specialRequierName;
	}

	
	public String getSpecialRequierName() {
		return specialRequierName;
	}

	public void setTransportStorageName(String transportStorageName) {
		this.transportStorageName = transportStorageName;
	}

	
	public String getTransportStorageName() {
		return transportStorageName;
	}

	public void setProductLicenseName(String productLicenseName) {
		this.productLicenseName = productLicenseName;
	}

	
	public String getProductLicenseName() {
		return productLicenseName;
	}

	public void setRmsSynchrDate(Date rmsSynchrDate) {
		this.rmsSynchrDate = rmsSynchrDate;
	}

	
	public Date getRmsSynchrDate() {
		return rmsSynchrDate;
	}

	public void setStandardOfUnit(String standardOfUnit) {
		this.standardOfUnit = standardOfUnit;
	}

	
	public String getStandardOfUnit() {
		return standardOfUnit;
	}

	public void setIsRmsRever(String isRmsRever) {
		this.isRmsRever = isRmsRever;
	}

	
	public String getIsRmsRever() {
		return isRmsRever;
	}

	public void setRmsReverBusinesskey(String rmsReverBusinesskey) {
		this.rmsReverBusinesskey = rmsReverBusinesskey;
	}

	
	public String getRmsReverBusinesskey() {
		return rmsReverBusinesskey;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
	public String getPurchaseTypestr() {
		if (this.purchaseType != null) {
			if (ResultConstant.PLM_PRODUCT_PURCHASE != null) {
				purchaseTypestr = ResultConstant.PLM_PRODUCT_PURCHASE
						.getString(purchaseType);
			}
		}
		return purchaseTypestr;
	}
	public String getOtherinfoname() {
		if (this.otherInfo != null) {
			if (ResultConstant.PLM_PRODUCT_ORTHERINFO != null) {
				otherinfoname = ResultConstant.PLM_PRODUCT_ORTHERINFO
						.getString(otherInfo);
			}
		}
		return otherinfoname;
	}
	public String getOrderstatusName() {
		if (this.orderStatus != null) {
			if (!this.orderStatus.equals("8")) {
				if (ResultConstant.PLM_PRODUT_ORDERSTATUS != null) {
					orderstatusName = ResultConstant.PLM_PRODUT_ORDERSTATUS
							.getString(orderStatus);
				}
			} else {
				if (ResultConstant.PLM_PRODUCT_STATUS != null) {
					orderstatusName = ResultConstant.PLM_PRODUCT_STATUS
							.getString(orderStatus);
				}
			}
		}
		return orderstatusName;
	}
	public String getProductReturnName() {
		if (this.productReturn != null) {
			if (ResultConstant.PLM_PRODUCT_RETURNPRO != null) {
				productReturnName = ResultConstant.PLM_PRODUCT_RETURNPRO
						.getString(productReturn);
			}
		}
		return productReturnName;
	}

	public String getEcoStatusName() {
		if (this.ecoStatus != null) {
			if (ResultConstant.PLM_PRODUCT_UPD_STATUS != null) {
				ecoStatusName = ResultConstant.PLM_PRODUCT_UPD_STATUS
						.getString(ecoStatus);
			}
		}
		return ecoStatusName;
	}

	public void setEcoStatusName(String ecoStatusName) {
		this.ecoStatusName = ecoStatusName;
	}
}
