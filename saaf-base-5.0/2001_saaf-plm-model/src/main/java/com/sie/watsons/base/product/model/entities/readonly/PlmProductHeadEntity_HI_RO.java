package com.sie.watsons.base.product.model.entities.readonly;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.redisUtil.ResultConstant;

/**
 * PlmProductGoodsHeadEntity_HI_RO Entity Object Thu Aug 29 10:51:49 CST 2019
 * Auto Generate
 */

public class PlmProductHeadEntity_HI_RO {
	public static final String QUERY_HEAD = "SELECT\n" +
			"  PRODUCT.PRODUCT_HEAD_ID AS productHeadId,\n" +
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
			"  uda3.uda_value_desc AS motherCompanyName,\n " +
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
			"   PRODUCT.SUPPLIER_COUNT AS supplierCount,\n" +
			"    PRODUCT.PRODUCT_RETURN AS productReturn,\n" +
			"     PRODUCT.QA_FILE_ID AS qaFileId,\n" +
			"     PRODUCT.QA_FILENAME AS qaFilename, \n" +
			"     PRODUCT.CONDITION AS condition, \n" +
			"     PRODUCT.ALL_TIER As allTier, \n" +
			"     PRODUCT.TIER1 AS tier1, \n" +
			"     PRODUCT.TIER2 AS tier2, \n" +
			"     PRODUCT.TIER345 AS tier345, \n" +
			"     PRODUCT.STORE_TYPE AS storeType, \n" +
			"     PRODUCT.TRADE_ZONE AS tradeZone, \n" +
			"     PRODUCT.MAIN_PRODUCTNAME AS mainProductname, \n" +
			"     PRODUCT.MAIN_PRODUCTID AS mainProductid, \n" +
			"     PRODUCT.PROCESS_INSTANCEID AS processInstanceid, \n" +
			"     PRODUCT.UPDATE_INSTANCEID AS updateInstanceid,  " +
			"    og.area_cn as originCountryName,\n"+
			"    og.area_en as originCountryEname,\n"+
			" PRODUCT.VALID_DAYS AS validDays, " +
			" PRODUCT.SX_DAYS AS sxDays,  PRODUCT.SERIAL_ID AS serialId, " +
			" PRODUCT.SERIAL_NAME AS serialName,  PRODUCT.CLASS_ID AS classId, " +
			" PRODUCT.CLASS_NAME AS className,  PRODUCT.ADD_PROCESSNAME AS addProcessname,  " +
			" PRODUCT.UPDATE_PROCEKEY AS updateProcekey,  PRODUCT.CREATEDSTR AS createdstr, " +
			" nvl(PRODUCT.ISRETURN_SALE,'0') AS isreturnSale, " +
			" nvl(PRODUCT.ISUPDATE_PRICE,'0') AS isupdatePrice, nvl(PRODUCT.ISITERATE_SALES,'0') AS isiterateSales, " +
			" PRODUCT.ORIGIN_COUNTRYID AS originCountryid, PRODUCT.SUG_RETAILPRICE AS sugRetailprice, PRODUCT.RATE_CLASS AS rateClass, " +
			" PRODUCT.CREATED_ENAME AS createdEname, PRODUCT.CREATED_EMP AS createdEmp,  PRODUCT.RMS_STATUS AS rmsStatus, " +
			" PRODUCT.RMS_REMAKE AS rmsRemake,  PRODUCT.USER_DEPT AS userDept, " +
			" PRODUCT.USER_GROUPCODE AS userGroupcode,  PRODUCT.POG_WAYS AS pogWays, " +
			" PRODUCT.POG_DEPARMENT AS pogDeparment,  PRODUCT.RMS_UPDATE AS rmsUpdate,  " +
			" PRODUCT.CONDITIONS as  conditions, PRODUCT.special_requier_name AS specialRequierName, " +
			" PRODUCT.transport_storage_name AS transportStorageName, PRODUCT.product_license_name AS productLicenseName,  " +
			" PRODUCT.standard_of_unit as standardOfUnit, PRODUCT.is_rms_rever AS isRmsRever,  " +
			" PRODUCT.rms_rever_businesskey AS rmsReverBusinesskey,  PRODUCT.brand_cn_uda_id AS BrandCnUdaId, " +
			" PRODUCT.brand_cn_uda_value AS BrandCnUdaValue, " +
			" PRODUCT.brand_en_uda_id AS BrandEnUdaId, PRODUCT.brand_en_uda_value AS BrandEnUdaValue  FROM\n" +
			"  PLM_PRODUCT_HEAD  PRODUCT  \n" +
			"  left join plm_uda_attribute  uda1 on PRODUCT.Power_Ob = uda1.uda_value  and uda1.uda_id=48\n" +
			"  left join plm_uda_attribute  uda2 on PRODUCT.Rang_Ob = uda2.uda_value  and uda2.uda_id=47\n" +
			"  LEFT JOIN plm_uda_attribute uda3   ON product.mother_company = uda3.uda_value AND uda3.uda_id = 49 \n" +
			"  LEFT JOIN plm_group_brand pgb   ON product.group_brand = pgb.plm_group_brand_id \n" +
			"  left join PLM_COUNTRY_OF_ORIGIN og on product.origin_country=og.name_abbreviation \n"+
			"  left join PLM_COUNTRY_OF_ORIGIN og on product.origin_country=og.name_abbreviation \n"+
			"  LEFT JOIN PLM_USER_BRAND_MAP userBrandMap ON product.BRANDNAME_CN=userBrandMap.BRAND_CN \n" +
			"  AND product.BRANDNAME_EN=userBrandMap.BRAND_EN AND userBrandMap.DELETE_FLAG != 1 AND userBrandMap.STATUS>9  \n"+
			"  where PRODUCT.IS_UPDATECHECK='0' ";

	public static final String QUERY = " SELECT ppd.drug_ins drugIns,ppbt.barCode, ppd.drug_id drugId, p.* from PLM_PRODUCT_HEAD p "
			+ "   left join PLM_PRODUCT_DRUG ppd on ppd.product_head_id= p.product_head_id\n "
			+ "   left join plm_product_barcode_table ppbt on ppd.product_head_id = ppbt.product_head_id and ppbt.is_main ='1'\n "
			+ " where 1=1 \n"
			+ "  and p.order_status = '6' \n"
			+ "  and p.rms_code is null \n"
			+ "  and nvl(p.rms_status,'N') != 'Y'  \n"
	   // todo: 过滤已经上传RMS 待处理的货品
	 + " AND p.plm_code not IN\n" +
			"            (SELECT DISTINCT pal.item\n" +
			"               FROM plm_api_log pal\n" +
			"              WHERE 1 = 1\n" +
			"                AND pal.status IS NULL\n" +
			"                AND length(pal.request_id) = 10\n" +
			"                and pal.return_flag IS NULL )"
	;

	public static final String QUERY2 = "SELECT * from PLM_PRODUCT_HEAD p "
			+ " where 1 = 1 \n"
			+ " and p.order_status = '6' \n "
			+ " and nvl( p.rms_status ,'N') != 'Y' \n"
			+ "  and p.rms_code is null \n"
			//todo: 过滤已经上传RMS 待处理的货品
	 + " AND p.plm_code not IN\n" +
			"            (SELECT DISTINCT pal.item\n" +
			"               FROM plm_api_log pal\n" +
			"              WHERE 1 = 1\n" +
			"                AND pal.status IS NULL\n" +
			"                AND length(pal.request_id) = 10\n" +
			"                and pal.return_flag IS NULL )"
	;

	public static final String ORIGIN_COUNTRY_QUERY = "select v.name_abbreviation originCountry from PLM_COUNTRY_OF_ORIGIN v  "
			+ "left join PLM_PRODUCT_HEAD h on h.origin_country = v.area_cn  "
			+ "where h.PRODUCT_HEAD_ID = ':headId' ";

	public static final String QUERY3 = "select prh.product_head_id productHeadId, prh.rms_code rmsCode, PRH.PRODUCT_NAME productName,PRH.DEPARTMENT department FROM PLM_PRODUCT_HEAD prh where 1=1 ";

	public static final String QUERY4 = "SELECT *\n" +
			"  FROM plm_product_head pph\n" +
			" WHERE 1 = 1\n" +
			"   AND pph.rms_synchr_date > SYSDATE - 60\n" +
			"   AND pph.rms_synchr_date IS NOT NULL\n" +
			"   AND pph.rms_code IS NOT NULL\n" +
			" ORDER BY 1 DESC ";

	public static final String QUERYP_RODUCT_HEAD_RMS = "SELECT DISTINCT product.PRODUCT_HEAD_ID productHeadId,product.MOTHER_COMPANY motherCompany,product.MOTHER_COMPANY_ID motherCompanyId,product.GROUP_BRAND groupBrand, \n"
			+" product.BRANDNAME_CN brandCn,product.BRANDNAME_EN brandEn,vd.ACCOUNT_ID accountId,vd.SUPPLIER_CODE supplierCode\n"
			+" FROM PLM_PRODUCT_HEAD product LEFT JOIN VMI_DATA_PRIVILEGE_LINE vd \n"
			+" ON product.RMS_CODE = vd.ITEM_CODE WHERE vd.ACCOUNT_ID IS NOT NULL ";

	public static void main(String[] args) {
		System.out.println("  SQL:::  " + QUERY4);
	}
    private String originCountryEname;
	private String originCountryName;
	private String plmGroupBrandName;
	private String motherCompanyName;
	private String rangObName;
	private String powerObName;
	private String billNo;
	private String isRmsRever;
	private String standardOfUnit;
	private String specialRequierName;
	private String barCode;
	private Integer drugId;
	private String drugIns;
	// private Integer sxDays;
	private Integer productHeadId;
	private String plmCode;
	private String rmsCode;
	private String obId;
	private String groupBrand;
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
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
	private String transportStorageName;
	private String productLicense;
	private String productLicenseName;
	private String orderStatus;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	private Integer operatorUserId;
	private String createdstr;
	private Integer supplierCount;

	private String isUpdatecheck;
	private String orderstatusName;

	private String pricewarProduct;
	private String consignmentType;
	private String consignmentRate;
	private String salesQty;
	private String consultProductno;
	private String imProductno;
	private String imProductname;
	private String consultProductname;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date consultDate;
	private String isDiaryproduct;
	private String provCondition;

	private String productReturn;
	private String productReturnName;
	private String sesionProductName;
	private String condition;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date consultEnddate;
	private String qaFileId; // 资质组id

	private String qaFilename; // 资质组名称
	private String allTier;
	private String tier1;
	private String tier2;
	private String tier345;
	private String storeType;
	private String tradeZone;
	private String mainProductname;
	private String mainProductid;

	private String processInstanceid; // 新增流程实例
	private String updateInstanceid; // 修改流程实例
	private String lastUpdatePerson; // 得到名称

	private String validDays;

	private Integer sxDays;

	private String serialId; // 系列id

	private String serialName;// 系列名称

	private String classId; // 分类id
	private String className; // 分类名称
	private String sugRetailprice; // 建议零售价

	private String addProcessname; // 新增流程名称
	private String updateProcekey; // 修改流程businesskey

	private String otherinfoname;// 特殊商品类型字符串

	private String isreturnSale;
	private String isupdatePrice;
	private String isiterateSales;

	private String originCountryid;

	private String rateClass;

	private String createdEname; // 创建人英文名
	private String createdEmp; // 创建人工号
	private String rmsStatus;
	private String rmsRemake;
	private String rmsUpdate;
	private String userDept;
	private String userGroupcode;

	private String pogWays;
	private String pogDeparment;

	private String submitStatus;

	private String conditions;

	private String headid;
	private String orderName;

	private String rmsReverBusinesskey;

	private Integer BrandCnUdaId;
	private Integer BrandCnUdaValue;
	private Integer BrandEnUdaId;
	private Integer BrandEnUdaValue;
	private Integer motherCompanyId;

	public Integer getMotherCompanyId() {
		return motherCompanyId;
	}

	public void setMotherCompanyId(Integer motherCompanyId) {
		this.motherCompanyId = motherCompanyId;
	}

	public String getOriginCountryEname() {
		return originCountryEname;
	}

	public void setOriginCountryEname(String originCountryEname) {
		this.originCountryEname = originCountryEname;
	}

	public String getOriginCountryName() {
		return originCountryName;
	}

	public void setOriginCountryName(String originCountryName) {
		this.originCountryName = originCountryName;
	}

	public String getPlmGroupBrandName() {		return plmGroupBrandName;	}

	public void setPlmGroupBrandName(String plmGroupBrandName) {this.plmGroupBrandName = plmGroupBrandName;}

	public String getMotherCompanyName() {return motherCompanyName;}

	public void setMotherCompanyName(String motherCompanyName) {this.motherCompanyName = motherCompanyName;}

	public String getRangObName() {return rangObName;}

	public void setRangObName(String rangObName) {this.rangObName = rangObName;}

	public String getPowerObName() {return powerObName;}

	public void setPowerObName(String powerObName) {		this.powerObName = powerObName;	}

	public Integer getBrandCnUdaId() {
		return BrandCnUdaId;
	}

	public void setBrandCnUdaId(Integer brandCnUdaId) {
		BrandCnUdaId = brandCnUdaId;
	}

	public Integer getBrandCnUdaValue() {
		return BrandCnUdaValue;
	}

	public void setBrandCnUdaValue(Integer brandCnUdaValue) {
		BrandCnUdaValue = brandCnUdaValue;
	}

	public Integer getBrandEnUdaId() {
		return BrandEnUdaId;
	}

	public void setBrandEnUdaId(Integer brandEnUdaId) {
		BrandEnUdaId = brandEnUdaId;
	}

	public Integer getBrandEnUdaValue() {
		return BrandEnUdaValue;
	}

	public void setBrandEnUdaValue(Integer brandEnUdaValue) {
		BrandEnUdaValue = brandEnUdaValue;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getRmsReverBusinesskey() {
		return rmsReverBusinesskey;
	}

	public void setRmsReverBusinesskey(String rmsReverBusinesskey) {
		this.rmsReverBusinesskey = rmsReverBusinesskey;
	}

	public String getIsRmsRever() {
		return isRmsRever;
	}

	public void setIsRmsRever(String isRmsRever) {
		this.isRmsRever = isRmsRever;
	}

	public String getStandardOfUnit() {
		return standardOfUnit;
	}

	public void setStandardOfUnit(String standardOfUnit) {
		this.standardOfUnit = standardOfUnit;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getTransportStorageName() {
		return transportStorageName;
	}

	public void setTransportStorageName(String transportStorageName) {
		this.transportStorageName = transportStorageName;
	}

	public String getProductLicenseName() {
		return productLicenseName;
	}

	public void setProductLicenseName(String productLicenseName) {
		this.productLicenseName = productLicenseName;
	}

	public String getSpecialRequierName() {
		return specialRequierName;
	}

	public void setSpecialRequierName(String specialRequierName) {
		this.specialRequierName = specialRequierName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getHeadid() {
		return headid;
	}

	public void setHeadid(String headid) {
		this.headid = headid;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getRmsUpdate() {
		return rmsUpdate;
	}

	public void setRmsUpdate(String rmsUpdate) {
		this.rmsUpdate = rmsUpdate;
	}

	public Integer getDrugId() {
		return drugId;
	}

	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
	}

	public String getDrugIns() {
		return drugIns;
	}

	public void setDrugIns(String drugIns) {
		this.drugIns = drugIns;
	}

	public String getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}

	public String getPogWays() {
		return pogWays;
	}

	public void setPogWays(String pogWays) {
		this.pogWays = pogWays;
	}

	public String getPogDeparment() {
		return pogDeparment;
	}

	public void setPogDeparment(String pogDeparment) {
		this.pogDeparment = pogDeparment;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getUserGroupcode() {
		return userGroupcode;
	}

	public void setUserGroupcode(String userGroupcode) {
		this.userGroupcode = userGroupcode;
	}

	public String getRmsStatus() {
		return rmsStatus;
	}

	public void setRmsStatus(String rmsStatus) {
		this.rmsStatus = rmsStatus;
	}

	public String getRmsRemake() {
		return rmsRemake;
	}

	public void setRmsRemake(String rmsRemake) {
		this.rmsRemake = rmsRemake;
	}

	public String getCreatedEname() {
		return createdEname;
	}

	public void setCreatedEname(String createdEname) {
		this.createdEname = createdEname;
	}

	public String getCreatedEmp() {
		return createdEmp;
	}

	public void setCreatedEmp(String createdEmp) {
		this.createdEmp = createdEmp;
	}

	public String getRateClass() {
		return rateClass;
	}

	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}

	public String getLastUpdatePerson() {

		return lastUpdatePerson;
	}

	public void setLastUpdatePerson(String lastUpdatePerson) {
		this.lastUpdatePerson = lastUpdatePerson;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	public Integer getProductHeadId() {
		return productHeadId;
	}

	public String getPlmCode() {
		return plmCode;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
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

	public String getSubclassDesc() {
		// if (subClass != null) {
		// subclassDesc = ResultConstant.PLM_SUP_WAREHOUSE.getString(subClass);
		// }
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

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public String getObId() {
		return obId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	public String getGroupBrand() {
		return groupBrand;
	}

	public void setGroupBrand(String groupBrand) {
		this.groupBrand = groupBrand;
	}

	public String getCreatedstr() {
		return createdstr;
	}

	public void setCreatedstr(String createdstr) {
		this.createdstr = createdstr;
	}

	public String getIsUpdatecheck() {
		return isUpdatecheck;
	}

	public void setIsUpdatecheck(String isUpdatecheck) {
		this.isUpdatecheck = isUpdatecheck;
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

	public void setProductReturnName(String productReturnName) {
		this.productReturnName = productReturnName;
	}

	public String getSesionProductName() {
		if (this.sesionProduct != null) {
			if (ResultConstant.PLM_PRODUCT_SESION != null) {
				sesionProductName = ResultConstant.PLM_PRODUCT_SESION
						.getString(sesionProduct);
			}
		}
		return sesionProductName;
	}

	public void setSesionProductName(String sesionProductName) {
		this.sesionProductName = sesionProductName;
	}

	public void setOrderstatusName(String orderstatusName) {
		this.orderstatusName = orderstatusName;
	}

	public String getPricewarProduct() {
		return pricewarProduct;
	}

	public void setPricewarProduct(String pricewarProduct) {
		this.pricewarProduct = pricewarProduct;
	}

	public String getConsignmentType() {
		return consignmentType;
	}

	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	public String getConsignmentRate() {
		return consignmentRate;
	}

	public void setConsignmentRate(String consignmentRate) {
		this.consignmentRate = consignmentRate;
	}

	public String getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
	}

	public String getConsultProductno() {
		return consultProductno;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	public String getImProductno() {
		return imProductno;
	}

	public void setImProductno(String imProductno) {
		this.imProductno = imProductno;
	}

	public String getImProductname() {
		return imProductname;
	}

	public void setImProductname(String imProductname) {
		this.imProductname = imProductname;
	}

	public String getConsultProductname() {
		return consultProductname;
	}

	public void setConsultProductname(String consultProductname) {
		this.consultProductname = consultProductname;
	}

	public Date getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(Date consultDate) {
		this.consultDate = consultDate;
	}

	public String getIsDiaryproduct() {
		return isDiaryproduct;
	}

	public void setIsDiaryproduct(String isDiaryproduct) {
		this.isDiaryproduct = isDiaryproduct;
	}

	public String getProvCondition() {
		return provCondition;
	}

	public void setProvCondition(String provCondition) {
		this.provCondition = provCondition;
	}

	public static String getQueryHead() {
		return QUERY_HEAD;
	}

	public Integer getSupplierCount() {
		return supplierCount;
	}

	public void setSupplierCount(Integer supplierCount) {
		this.supplierCount = supplierCount;
	}

	public String getProductReturn() {
		return productReturn;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Date getConsultEnddate() {
		return consultEnddate;
	}

	public void setConsultEnddate(Date consultEnddate) {
		this.consultEnddate = consultEnddate;
	}

	public String getQaFileId() {
		return qaFileId;
	}

	public void setQaFileId(String qaFileId) {
		this.qaFileId = qaFileId;
	}

	public String getQaFilename() {
		return qaFilename;
	}

	public void setQaFilename(String qaFilename) {
		this.qaFilename = qaFilename;
	}

	public String getAllTier() {
		return allTier;
	}

	public void setAllTier(String allTier) {
		this.allTier = allTier;
	}

	public String getTier1() {
		return tier1;
	}

	public void setTier1(String tier1) {
		this.tier1 = tier1;
	}

	public String getTier2() {
		return tier2;
	}

	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}

	public String getTier345() {
		return tier345;
	}

	public void setTier345(String tier345) {
		this.tier345 = tier345;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getTradeZone() {
		return tradeZone;
	}

	public void setTradeZone(String tradeZone) {
		this.tradeZone = tradeZone;
	}

	public String getMainProductname() {
		return mainProductname;
	}

	public void setMainProductname(String mainProductname) {
		this.mainProductname = mainProductname;
	}

	public String getMainProductid() {
		return mainProductid;
	}

	public void setMainProductid(String mainProductid) {
		this.mainProductid = mainProductid;
	}

	public String getProcessInstanceid() {
		return processInstanceid;
	}

	public void setProcessInstanceid(String processInstanceid) {
		this.processInstanceid = processInstanceid;
	}

	public String getUpdateInstanceid() {
		return updateInstanceid;
	}

	public void setUpdateInstanceid(String updateInstanceid) {
		this.updateInstanceid = updateInstanceid;
	}

	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
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

	public void setSubclassDesc(String subclassDesc) {
		this.subclassDesc = subclassDesc;
	}

	public String getValidDays() {
		return validDays;
	}

	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}

	public Integer getSxDays() {
		return sxDays;
	}

	public void setSxDays(Integer sxDays) {
		this.sxDays = sxDays;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSugRetailprice() {
		return sugRetailprice;
	}

	public void setSugRetailprice(String sugRetailprice) {
		this.sugRetailprice = sugRetailprice;
	}

	public String getAddProcessname() {
		return addProcessname;
	}

	public void setAddProcessname(String addProcessname) {
		this.addProcessname = addProcessname;
	}

	public String getUpdateProcekey() {
		return updateProcekey;
	}

	public void setUpdateProcekey(String updateProcekey) {
		this.updateProcekey = updateProcekey;
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

	public String getIsreturnSale() {
		return isreturnSale;
	}

	public void setIsreturnSale(String isreturnSale) {
		this.isreturnSale = isreturnSale;
	}

	public String getIsupdatePrice() {
		return isupdatePrice;
	}

	public void setIsupdatePrice(String isupdatePrice) {
		this.isupdatePrice = isupdatePrice;
	}

	public String getIsiterateSales() {
		return isiterateSales;
	}

	public void setIsiterateSales(String isiterateSales) {
		this.isiterateSales = isiterateSales;
	}

	public String getOriginCountryid() {
		return originCountryid;
	}

	public void setOriginCountryid(String originCountryid) {
		this.originCountryid = originCountryid;
	}

}
