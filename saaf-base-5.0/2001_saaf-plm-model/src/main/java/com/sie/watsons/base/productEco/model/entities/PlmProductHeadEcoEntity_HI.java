package com.sie.watsons.base.productEco.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import jodd.vtor.constraint.NotBlank;
import jodd.vtor.constraint.NotNull;

import javax.persistence.Transient;
/**
 * PlmProductHeadEcoEntity_HI Entity Object
 * Fri May 22 14:29:18 CST 2020  Auto Generate
 */
@Entity
@Table(name="PLM_PRODUCT_HEAD_ECO")
public class PlmProductHeadEcoEntity_HI {

    private Integer ecoId;
    private String ecoNo;
    private String ecoStatus;
    private Integer ecoDeptId;
	@NotBlank( message = "货品ID:productHeadId不能为空",profiles = {"insert"})
	@NotNull( message = "货品ID:productHeadId不能为空",profiles = {"insert"})
    private Integer productHeadId;
	@NotBlank( message = "货品编码:plmCode不能为空.",profiles = {"insert"})
	@NotNull( message = "货品ID:productHeadId不能为空",profiles = {"insert"})
    private String plmCode;
    private String department;
    private String departmentDescript;
    private String classes;
    private String classDesc;
    private String subClass;
    private String subclassDesc;
    private String purchaseType;
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
	@NotNull( message = "dangerousProduct不能为空",profiles = {"insert"})
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
	@NotBlank( message = "originCountry不能为空",profiles = {"insert"})
    private String originCountry;
	@NotBlank( message = "unit不能为空",profiles = {"insert"})
    private String unit;
	@NotBlank( message = "warehouseGetDay不能为空",profiles = {"insert"})
    private Integer warehouseGetDay;
	@NotBlank( message = "warehousePostDay不能为空",profiles = {"insert"})
    private Integer warehousePostDay;
    private String rangOb;
    private String powerOb;
    private String tier;
    private String specialRequier;
    private String transportStorage;
    private String productLicense;
    private String orderStatus;
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
	@NotBlank( message = "productReturn不能为空",profiles = {"insert"})
    private String productReturn;
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
	@NotBlank( message = "validDays不能为空",profiles = {"insert"})
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

	@Column(name="brand_cn_uda_id", nullable=true, length=22)
	public Integer getBrandCnUdaId() {		return brandCnUdaId;	}
	public void setBrandCnUdaId(Integer brandCnUdaId) {		this.brandCnUdaId = brandCnUdaId;	}

	@Column(name="brand_cn_uda_value", nullable=true, length=22)
	public Integer getBrandCnUdaValue() {		return brandCnUdaValue;	}
	public void setBrandCnUdaValue(Integer brandCnUdaValue) {		this.brandCnUdaValue = brandCnUdaValue;	}

	@Column(name="brand_en_uda_id", nullable=true, length=22)
	public Integer getBrandEnUdaId() {		return brandEnUdaId;	}
	public void setBrandEnUdaId(Integer brandEnUdaId) {		this.brandEnUdaId = brandEnUdaId;	}

	@Column(name="brand_en_uda_value", nullable=true, length=22)
	public Integer getBrandEnUdaValue() {		return brandEnUdaValue;	}
	public void setBrandEnUdaValue(Integer brandEnUdaValue) {		this.brandEnUdaValue = brandEnUdaValue;	}



	public void setEcoId(Integer ecoId) {
		this.ecoId = ecoId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_HEAD_ECO", sequenceName = "SEQ_PLM_PRODUCT_HEAD_ECO", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_HEAD_ECO", strategy = GenerationType.SEQUENCE)
	@Column(name="eco_id", nullable=true, length=22)	
	public Integer getEcoId() {
		return ecoId;
	}

	public void setEcoNo(String ecoNo) {
		this.ecoNo = ecoNo;
	}

	@Column(name="eco_no", nullable=true, length=20)
	public String getEcoNo() {
		return ecoNo;
	}

	public void setEcoStatus(String ecoStatus) {
		this.ecoStatus = ecoStatus;
	}

	@Column(name="eco_status", nullable=true, length=20)	
	public String getEcoStatus() {
		return ecoStatus;
	}

	public void setEcoDeptId(Integer ecoDeptId) {
		this.ecoDeptId = ecoDeptId;
	}

	@Column(name="eco_dept_id", nullable=true, length=22)	
	public Integer getEcoDeptId() {
		return ecoDeptId;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Column(name="product_head_id", nullable=false, length=22)	
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
	}

	@Column(name="plm_code", nullable=true, length=50)	
	public String getPlmCode() {
		return plmCode;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name="department", nullable=true, length=200)	
	public String getDepartment() {
		return department;
	}

	public void setDepartmentDescript(String departmentDescript) {
		this.departmentDescript = departmentDescript;
	}

	@Column(name="department_descript", nullable=true, length=200)	
	public String getDepartmentDescript() {
		return departmentDescript;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	@Column(name="classes", nullable=true, length=100)	
	public String getClasses() {
		return classes;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	@Column(name="class_desc", nullable=true, length=200)	
	public String getClassDesc() {
		return classDesc;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	@Column(name="sub_class", nullable=true, length=100)	
	public String getSubClass() {
		return subClass;
	}

	public void setSubclassDesc(String subclassDesc) {
		this.subclassDesc = subclassDesc;
	}

	@Column(name="subclass_desc", nullable=true, length=100)	
	public String getSubclassDesc() {
		return subclassDesc;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Column(name="purchase_type", nullable=true, length=100)	
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="product_name", nullable=true, length=200)	
	public String getProductName() {
		return productName;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	@Column(name="product_ename", nullable=true, length=200)	
	public String getProductEname() {
		return productEname;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Column(name="product_type", nullable=true, length=150)	
	public String getProductType() {
		return productType;
	}

	public void setBrandnameCn(String brandnameCn) {
		this.brandnameCn = brandnameCn;
	}

	@Column(name="brandname_cn", nullable=true, length=200)	
	public String getBrandnameCn() {
		return brandnameCn;
	}

	public void setBrandnameEn(String brandnameEn) {
		this.brandnameEn = brandnameEn;
	}

	@Column(name="brandname_en", nullable=true, length=200)	
	public String getBrandnameEn() {
		return brandnameEn;
	}

	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}

	@Column(name="count_unit", nullable=true, length=200)	
	public String getCountUnit() {
		return countUnit;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	@Column(name="other_info", nullable=true, length=150)	
	public String getOtherInfo() {
		return otherInfo;
	}

	public void setMarkerChannel(String markerChannel) {
		this.markerChannel = markerChannel;
	}

	@Column(name="marker_channel", nullable=true, length=200)	
	public String getMarkerChannel() {
		return markerChannel;
	}

	public void setProductShape(String productShape) {
		this.productShape = productShape;
	}

	@Column(name="product_shape", nullable=true, length=150)	
	public String getProductShape() {
		return productShape;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name="version_num", nullable=true, length=22)	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name="creation_date", nullable=true, length=7)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_by", nullable=true, length=22)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name="last_updated_by", nullable=true, length=22)	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="last_update_date", nullable=true, length=7)	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name="last_update_login", nullable=true, length=22)	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setGrossEarnings(String grossEarnings) {
		this.grossEarnings = grossEarnings;
	}

	@Column(name="gross_earnings", nullable=true, length=255)	
	public String getGrossEarnings() {
		return grossEarnings;
	}

	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
	}

	@Column(name="rate_class_code", nullable=true, length=150)	
	public String getRateClassCode() {
		return rateClassCode;
	}

	public void setDayDamage(String dayDamage) {
		this.dayDamage = dayDamage;
	}

	@Column(name="day_damage", nullable=true, length=150)	
	public String getDayDamage() {
		return dayDamage;
	}

	public void setSpecialLicence(String specialLicence) {
		this.specialLicence = specialLicence;
	}

	@Column(name="special_licence", nullable=true, length=50)	
	public String getSpecialLicence() {
		return specialLicence;
	}

	public void setProductResource(String productResource) {
		this.productResource = productResource;
	}

	@Column(name="product_resource", nullable=true, length=150)	
	public String getProductResource() {
		return productResource;
	}

	public void setProductCategeery(String productCategeery) {
		this.productCategeery = productCategeery;
	}

	@Column(name="product_categeery", nullable=true, length=100)	
	public String getProductCategeery() {
		return productCategeery;
	}

	public void setUniqueCommodities(String uniqueCommodities) {
		this.uniqueCommodities = uniqueCommodities;
	}

	@Column(name="unique_commodities", nullable=true, length=100)	
	public String getUniqueCommodities() {
		return uniqueCommodities;
	}

	public void setSpecialtyProduct(String specialtyProduct) {
		this.specialtyProduct = specialtyProduct;
	}

	@Column(name="specialty_product", nullable=true, length=50)	
	public String getSpecialtyProduct() {
		return specialtyProduct;
	}

	public void setProductProperties(String productProperties) {
		this.productProperties = productProperties;
	}

	@Column(name="product_properties", nullable=true, length=100)	
	public String getProductProperties() {
		return productProperties;
	}

	public void setBuyingLevel(String buyingLevel) {
		this.buyingLevel = buyingLevel;
	}

	@Column(name="buying_level", nullable=true, length=150)	
	public String getBuyingLevel() {
		return buyingLevel;
	}

	public void setDangerousProduct(String dangerousProduct) {
		this.dangerousProduct = dangerousProduct;
	}

	@Column(name="dangerous_product", nullable=true, length=50)	
	public String getDangerousProduct() {
		return dangerousProduct;
	}

	public void setPosInfo(String posInfo) {
		this.posInfo = posInfo;
	}

	@Column(name="pos_info", nullable=true, length=50)	
	public String getPosInfo() {
		return posInfo;
	}

	public void setInternationProduct(String internationProduct) {
		this.internationProduct = internationProduct;
	}

	@Column(name="internation_product", nullable=true, length=50)	
	public String getInternationProduct() {
		return internationProduct;
	}

	public void setSesionProduct(String sesionProduct) {
		this.sesionProduct = sesionProduct;
	}

	@Column(name="sesion_product", nullable=true, length=50)	
	public String getSesionProduct() {
		return sesionProduct;
	}

	public void setTopProduct(String topProduct) {
		this.topProduct = topProduct;
	}

	@Column(name="top_product", nullable=true, length=50)	
	public String getTopProduct() {
		return topProduct;
	}

	public void setMotherCompany(String motherCompany) {
		this.motherCompany = motherCompany;
	}

	@Column(name="mother_company", nullable=true, length=100)	
	public String getMotherCompany() {
		return motherCompany;
	}

	public void setBluecapProduct(String bluecapProduct) {
		this.bluecapProduct = bluecapProduct;
	}

	@Column(name="bluecap_product", nullable=true, length=50)	
	public String getBluecapProduct() {
		return bluecapProduct;
	}

	public void setCrossborderProduct(String crossborderProduct) {
		this.crossborderProduct = crossborderProduct;
	}

	@Column(name="crossborder_product", nullable=true, length=50)	
	public String getCrossborderProduct() {
		return crossborderProduct;
	}

	public void setVcProduct(String vcProduct) {
		this.vcProduct = vcProduct;
	}

	@Column(name="vc_product", nullable=true, length=50)	
	public String getVcProduct() {
		return vcProduct;
	}

	public void setWarehouseResource(String warehouseResource) {
		this.warehouseResource = warehouseResource;
	}

	@Column(name="warehouse_resource", nullable=true, length=50)	
	public String getWarehouseResource() {
		return warehouseResource;
	}

	public void setCompanyDeletion(String companyDeletion) {
		this.companyDeletion = companyDeletion;
	}

	@Column(name="company_deletion", nullable=true, length=50)	
	public String getCompanyDeletion() {
		return companyDeletion;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	@Column(name="origin_country", nullable=true, length=200)	
	public String getOriginCountry() {
		return originCountry;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name="unit", nullable=true, length=100)	
	public String getUnit() {
		return unit;
	}

	public void setWarehouseGetDay(Integer warehouseGetDay) {
		this.warehouseGetDay = warehouseGetDay;
	}

	@Column(name="warehouse_get_day", nullable=true, length=22)	
	public Integer getWarehouseGetDay() {
		return warehouseGetDay;
	}

	public void setWarehousePostDay(Integer warehousePostDay) {
		this.warehousePostDay = warehousePostDay;
	}

	@Column(name="warehouse_post_day", nullable=true, length=22)	
	public Integer getWarehousePostDay() {
		return warehousePostDay;
	}

	public void setRangOb(String rangOb) {
		this.rangOb = rangOb;
	}

	@Column(name="rang_ob", nullable=true, length=100)	
	public String getRangOb() {
		return rangOb;
	}

	public void setPowerOb(String powerOb) {
		this.powerOb = powerOb;
	}

	@Column(name="power_ob", nullable=true, length=100)	
	public String getPowerOb() {
		return powerOb;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	@Column(name="tier", nullable=true, length=100)	
	public String getTier() {
		return tier;
	}

	public void setSpecialRequier(String specialRequier) {
		this.specialRequier = specialRequier;
	}

	@Column(name="special_requier", nullable=true, length=50)	
	public String getSpecialRequier() {
		return specialRequier;
	}

	public void setTransportStorage(String transportStorage) {
		this.transportStorage = transportStorage;
	}

	@Column(name="transport_storage", nullable=true, length=50)	
	public String getTransportStorage() {
		return transportStorage;
	}

	public void setProductLicense(String productLicense) {
		this.productLicense = productLicense;
	}

	@Column(name="product_license", nullable=true, length=100)	
	public String getProductLicense() {
		return productLicense;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name="order_status", nullable=true, length=50)	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="start_date", nullable=true, length=7)	
	public Date getStartDate() {
		return startDate;
	}

	public void setSupplierCount(Integer supplierCount) {
		this.supplierCount = supplierCount;
	}

	@Column(name="supplier_count", nullable=true, length=22)	
	public Integer getSupplierCount() {
		return supplierCount;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	@Column(name="ob_id", nullable=true, length=100)	
	public String getObId() {
		return obId;
	}

	public void setGroupBrand(String groupBrand) {
		this.groupBrand = groupBrand;
	}

	@Column(name="group_brand", nullable=true, length=100)	
	public String getGroupBrand() {
		return groupBrand;
	}

	public void setIsUpdatecheck(String isUpdatecheck) {
		this.isUpdatecheck = isUpdatecheck;
	}

	@Column(name="is_updatecheck", nullable=true, length=255)	
	public String getIsUpdatecheck() {
		return isUpdatecheck;
	}

	public void setPricewarProduct(String pricewarProduct) {
		this.pricewarProduct = pricewarProduct;
	}

	@Column(name="pricewar_product", nullable=true, length=255)	
	public String getPricewarProduct() {
		return pricewarProduct;
	}

	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	@Column(name="consignment_type", nullable=true, length=255)	
	public String getConsignmentType() {
		return consignmentType;
	}

	public void setConsignmentRate(String consignmentRate) {
		this.consignmentRate = consignmentRate;
	}

	@Column(name="consignment_rate", nullable=true, length=255)	
	public String getConsignmentRate() {
		return consignmentRate;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
	}

	@Column(name="sales_qty", nullable=true, length=255)	
	public String getSalesQty() {
		return salesQty;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	@Column(name="consult_productno", nullable=true, length=255)	
	public String getConsultProductno() {
		return consultProductno;
	}

	public void setImProductno(String imProductno) {
		this.imProductno = imProductno;
	}

	@Column(name="im_productno", nullable=true, length=255)	
	public String getImProductno() {
		return imProductno;
	}

	public void setImProductname(String imProductname) {
		this.imProductname = imProductname;
	}

	@Column(name="im_productname", nullable=true, length=255)	
	public String getImProductname() {
		return imProductname;
	}

	public void setConsultProductname(String consultProductname) {
		this.consultProductname = consultProductname;
	}

	@Column(name="consult_productname", nullable=true, length=255)	
	public String getConsultProductname() {
		return consultProductname;
	}

	public void setConsultDate(Date consultDate) {
		this.consultDate = consultDate;
	}

	@Column(name="consult_date", nullable=true, length=7)	
	public Date getConsultDate() {
		return consultDate;
	}

	public void setIsDiaryproduct(String isDiaryproduct) {
		this.isDiaryproduct = isDiaryproduct;
	}

	@Column(name="is_diaryproduct", nullable=true, length=255)	
	public String getIsDiaryproduct() {
		return isDiaryproduct;
	}

	public void setProvCondition(String provCondition) {
		this.provCondition = provCondition;
	}

	@Column(name="prov_condition", nullable=true, length=255)	
	public String getProvCondition() {
		return provCondition;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	@Column(name="product_return", nullable=true, length=255)	
	public String getProductReturn() {
		return productReturn;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name="condition", nullable=true, length=255)	
	public String getCondition() {
		return condition;
	}

	public void setConsultEnddate(Date consultEnddate) {
		this.consultEnddate = consultEnddate;
	}

	@Column(name="consult_enddate", nullable=true, length=7)	
	public Date getConsultEnddate() {
		return consultEnddate;
	}

	public void setQaFileId(String qaFileId) {
		this.qaFileId = qaFileId;
	}

	@Column(name="qa_file_id", nullable=true, length=255)	
	public String getQaFileId() {
		return qaFileId;
	}

	public void setQaFilename(String qaFilename) {
		this.qaFilename = qaFilename;
	}

	@Column(name="qa_filename", nullable=true, length=255)	
	public String getQaFilename() {
		return qaFilename;
	}

	public void setAllTier(String allTier) {
		this.allTier = allTier;
	}

	@Column(name="all_tier", nullable=true, length=255)	
	public String getAllTier() {
		return allTier;
	}

	public void setTier1(String tier1) {
		this.tier1 = tier1;
	}

	@Column(name="tier1", nullable=true, length=255)	
	public String getTier1() {
		return tier1;
	}

	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}

	@Column(name="tier2", nullable=true, length=255)	
	public String getTier2() {
		return tier2;
	}

	public void setTier345(String tier345) {
		this.tier345 = tier345;
	}

	@Column(name="tier345", nullable=true, length=255)	
	public String getTier345() {
		return tier345;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name="store_type", nullable=true, length=255)	
	public String getStoreType() {
		return storeType;
	}

	public void setTradeZone(String tradeZone) {
		this.tradeZone = tradeZone;
	}

	@Column(name="trade_zone", nullable=true, length=255)	
	public String getTradeZone() {
		return tradeZone;
	}

	public void setMainProductname(String mainProductname) {
		this.mainProductname = mainProductname;
	}

	@Column(name="main_productname", nullable=true, length=255)	
	public String getMainProductname() {
		return mainProductname;
	}

	public void setMainProductid(String mainProductid) {
		this.mainProductid = mainProductid;
	}

	@Column(name="main_productid", nullable=true, length=255)	
	public String getMainProductid() {
		return mainProductid;
	}

	public void setProcessInstanceid(String processInstanceid) {
		this.processInstanceid = processInstanceid;
	}

	@Column(name="process_instanceid", nullable=true, length=255)	
	public String getProcessInstanceid() {
		return processInstanceid;
	}

	public void setUpdateInstanceid(String updateInstanceid) {
		this.updateInstanceid = updateInstanceid;
	}

	@Column(name="update_instanceid", nullable=true, length=2500)	
	public String getUpdateInstanceid() {
		return updateInstanceid;
	}

	public void setCheckPerson(Integer checkPerson) {
		this.checkPerson = checkPerson;
	}

	@Column(name="check_person", nullable=true, length=22)	
	public Integer getCheckPerson() {
		return checkPerson;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name="rms_code", nullable=true, length=255)	
	public String getRmsCode() {
		return rmsCode;
	}

	public void setNextCheckperson(String nextCheckperson) {
		this.nextCheckperson = nextCheckperson;
	}

	@Column(name="next_checkperson", nullable=true, length=255)	
	public String getNextCheckperson() {
		return nextCheckperson;
	}

	public void setSugRetailprice(String sugRetailprice) {
		this.sugRetailprice = sugRetailprice;
	}

	@Column(name="sug_retailprice", nullable=true, length=255)	
	public String getSugRetailprice() {
		return sugRetailprice;
	}

	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}

	@Column(name="valid_days", nullable=true, length=255)	
	public String getValidDays() {
		return validDays;
	}

	public void setSxDays(Integer sxDays) {
		this.sxDays = sxDays;
	}

	@Column(name="sx_days", nullable=true, length=22)	
	public Integer getSxDays() {
		return sxDays;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	@Column(name="serial_id", nullable=true, length=255)	
	public String getSerialId() {
		return serialId;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	@Column(name="serial_name", nullable=true, length=255)	
	public String getSerialName() {
		return serialName;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Column(name="class_id", nullable=true, length=255)	
	public String getClassId() {
		return classId;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name="class_name", nullable=true, length=255)	
	public String getClassName() {
		return className;
	}

	public void setAddProcessname(String addProcessname) {
		this.addProcessname = addProcessname;
	}

	@Column(name="add_processname", nullable=true, length=255)	
	public String getAddProcessname() {
		return addProcessname;
	}

	public void setUpdateProcekey(String updateProcekey) {
		this.updateProcekey = updateProcekey;
	}

	@Column(name="update_procekey", nullable=true, length=255)	
	public String getUpdateProcekey() {
		return updateProcekey;
	}

	public void setCreatedstr(String createdstr) {
		this.createdstr = createdstr;
	}

	@Column(name="createdstr", nullable=true, length=255)	
	public String getCreatedstr() {
		return createdstr;
	}

	public void setIsreturnSale(String isreturnSale) {
		this.isreturnSale = isreturnSale;
	}

	@Column(name="isreturn_sale", nullable=true, length=255)	
	public String getIsreturnSale() {
		return isreturnSale;
	}

	public void setIsupdatePrice(String isupdatePrice) {
		this.isupdatePrice = isupdatePrice;
	}

	@Column(name="isupdate_price", nullable=true, length=255)	
	public String getIsupdatePrice() {
		return isupdatePrice;
	}

	public void setIsiterateSales(String isiterateSales) {
		this.isiterateSales = isiterateSales;
	}

	@Column(name="isiterate_sales", nullable=true, length=255)	
	public String getIsiterateSales() {
		return isiterateSales;
	}

	public void setOriginCountryid(String originCountryid) {
		this.originCountryid = originCountryid;
	}

	@Column(name="origin_countryid", nullable=true, length=255)	
	public String getOriginCountryid() {
		return originCountryid;
	}

	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}

	@Column(name="rate_class", nullable=true, length=255)	
	public String getRateClass() {
		return rateClass;
	}

	public void setCreatedEname(String createdEname) {
		this.createdEname = createdEname;
	}

	@Column(name="created_ename", nullable=true, length=255)	
	public String getCreatedEname() {
		return createdEname;
	}

	public void setCreatedEmp(String createdEmp) {
		this.createdEmp = createdEmp;
	}

	@Column(name="created_emp", nullable=true, length=255)	
	public String getCreatedEmp() {
		return createdEmp;
	}

	public void setRmsStatus(String rmsStatus) {
		this.rmsStatus = rmsStatus;
	}

	@Column(name="rms_status", nullable=true, length=255)	
	public String getRmsStatus() {
		return rmsStatus;
	}

	public void setRmsRemake(String rmsRemake) {
		this.rmsRemake = rmsRemake;
	}

	@Column(name="rms_remake", nullable=true, length=255)	
	public String getRmsRemake() {
		return rmsRemake;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	@Column(name="user_dept", nullable=true, length=255)	
	public String getUserDept() {
		return userDept;
	}

	public void setUserGroupcode(String userGroupcode) {
		this.userGroupcode = userGroupcode;
	}

	@Column(name="user_groupcode", nullable=true, length=255)	
	public String getUserGroupcode() {
		return userGroupcode;
	}

	public void setPogWays(String pogWays) {
		this.pogWays = pogWays;
	}

	@Column(name="pog_ways", nullable=true, length=255)	
	public String getPogWays() {
		return pogWays;
	}

	public void setPogDeparment(String pogDeparment) {
		this.pogDeparment = pogDeparment;
	}

	@Column(name="pog_deparment", nullable=true, length=255)	
	public String getPogDeparment() {
		return pogDeparment;
	}

	public void setRmsUpdate(String rmsUpdate) {
		this.rmsUpdate = rmsUpdate;
	}

	@Column(name="rms_update", nullable=true, length=255)	
	public String getRmsUpdate() {
		return rmsUpdate;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	@Column(name="conditions", nullable=true, length=500)	
	public String getConditions() {
		return conditions;
	}

	public void setSpecialRequierName(String specialRequierName) {
		this.specialRequierName = specialRequierName;
	}

	@Column(name="special_requier_name", nullable=true, length=500)	
	public String getSpecialRequierName() {
		return specialRequierName;
	}

	public void setTransportStorageName(String transportStorageName) {
		this.transportStorageName = transportStorageName;
	}

	@Column(name="transport_storage_name", nullable=true, length=500)	
	public String getTransportStorageName() {
		return transportStorageName;
	}

	public void setProductLicenseName(String productLicenseName) {
		this.productLicenseName = productLicenseName;
	}

	@Column(name="product_license_name", nullable=true, length=500)	
	public String getProductLicenseName() {
		return productLicenseName;
	}

	public void setRmsSynchrDate(Date rmsSynchrDate) {
		this.rmsSynchrDate = rmsSynchrDate;
	}

	@Column(name="rms_synchr_date", nullable=true, length=7)	
	public Date getRmsSynchrDate() {
		return rmsSynchrDate;
	}

	public void setStandardOfUnit(String standardOfUnit) {
		this.standardOfUnit = standardOfUnit;
	}

	@Column(name="standard_of_unit", nullable=true, length=255)	
	public String getStandardOfUnit() {
		return standardOfUnit;
	}

	public void setIsRmsRever(String isRmsRever) {
		this.isRmsRever = isRmsRever;
	}

	@Column(name="is_rms_rever", nullable=true, length=50)	
	public String getIsRmsRever() {
		return isRmsRever;
	}

	public void setRmsReverBusinesskey(String rmsReverBusinesskey) {
		this.rmsReverBusinesskey = rmsReverBusinesskey;
	}

	@Column(name="rms_rever_businesskey", nullable=true, length=50)	
	public String getRmsReverBusinesskey() {
		return rmsReverBusinesskey;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
