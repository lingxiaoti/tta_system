package com.sie.watsons.base.product.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * PlmProductGoodsHeadEntity_HI Entity Object Thu Aug 29 10:51:49 CST 2019 Auto
 * Generate
 */
@Entity
@Table(name = "PLM_PRODUCT_HEAD")
public class PlmProductHeadEntity_HI {
	@columnNames(name = "头表Id")
	private Integer productHeadId;
	@columnNames(name = "OBId")
	private String obId;
	@columnNames(name = "groupBrand")
	private String groupBrand;
	private String plmCode;
	private String rmsCode;
	@columnNames(name = "部门")
	private String department;
	@columnNames(name = "部门描述")
	private String departmentDescript;
	@columnNames(name = "分类编码")
	private String classes;
	@columnNames(name = "分类描述")
	private String classDesc;
	@columnNames(name = "子分类编码")
	private String subClass;
	@columnNames(name = "子分类描述")
	private String subclassDesc;
	@columnNames(name = "采购类型")
	private String purchaseType;
	@columnNames(name = "商品名称")
	private String productName;
	@columnNames(name = "商品英文名")
	private String productEname;
	@columnNames(name = "商品类型")
	private String productType;
	@columnNames(name = "品牌名称(中文)")
	private String brandnameCn;
	@columnNames(name = "品牌名称(英文)")
	private String brandnameEn;
	@columnNames(name = "计价单位")
	private String countUnit;
	@columnNames(name = "特殊商品类型")
	private String otherInfo;
	@columnNames(name = "销售渠道")
	private String markerChannel;
	@columnNames(name = "货品形态")
	private String productShape;
	private Integer versionNum;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	private Integer createdBy;
	private Integer lastUpdatedBy;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate;
	private Integer lastUpdateLogin;
	@columnNames(name = "毛利（GP %）")
	private String grossEarnings;
	@columnNames(name = "税收分类编码")
	private String rateClassCode;
	@columnNames(name = "日损耗率(%)")
	private String dayDamage;
	@columnNames(name = "特别牌照")
	private String specialLicence;
	@columnNames(name = "商品来源")
	private String productResource;
	@columnNames(name = "商品种类")
	private String productCategeery;
	@columnNames(name = "独有商品")
	private String uniqueCommodities;
	@columnNames(name = "专柜商品")
	private String specialtyProduct;
	@columnNames(name = "商品性质")
	private String productProperties;
	@columnNames(name = "buyingLevel")
	private String buyingLevel;
	@columnNames(name = "危险品及易燃易爆")
	private String dangerousProduct;
	@columnNames(name = "POS弹出信息商品")
	private String posInfo;
	@columnNames(name = "国际采购商品")
	private String internationProduct;
	@columnNames(name = "季节性商品")
	private String sesionProduct;
	@columnNames(name = "TOP商品")
	private String topProduct;
	@columnNames(name = "mother company")
	private String motherCompany;
	@columnNames(name = "蓝帽商品")
	private String bluecapProduct;
	@columnNames(name = "跨境商品")
	private String crossborderProduct;
	@columnNames(name = "VC商品")
	private String vcProduct;
	@columnNames(name = "仓库来源")
	private String warehouseResource;
	@columnNames(name = "COMPANY DELETION ")
	private String companyDeletion;
	@columnNames(name = "原产国")
	private String originCountry;
	@columnNames(name = "单位规格")
	private String unit;
	@columnNames(name = "仓库可收货天数")
	private Integer warehouseGetDay;
	@columnNames(name = "仓库可出货天数")
	private Integer warehousePostDay;
	@columnNames(name = "rang ob 专用")
	private String rangOb;
	@columnNames(name = "power brand ob 专用")
	private String powerOb;
	@columnNames(name = "tier")
	private String tier;
	private String specialRequier;
	@columnNames(name = "特殊要求")
	private String specialRequierName;
	private String transportStorage;
	@columnNames(name = "运输存储")
	private String transportStorageName;
	private String productLicense;
	@columnNames(name = "商品证照")
	private String productLicenseName;
	private String orderStatus;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	private Integer operatorUserId;
	private Integer supplierCount;
	private String isUpdatecheck;
	@columnNames(name = "价格竞争商品合")
	private String pricewarProduct;
	@columnNames(name = "代销类型")
	private String consignmentType;
	@columnNames(name = "代销比率")
	private String consignmentRate;
	@columnNames(name = "Sales QTY Forecast/Store/Week  ")
	private String salesQty;
	@columnNames(name = "参考货号 ")
	private String consultProductno;
	@columnNames(name = "主商品货号")
	private String imProductno;
	@columnNames(name = "主商品名")
	private String imProductname;
	@columnNames(name = "参考货号名称")
	private String consultProductname;
	@columnNames(name = "参考开始时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date consultDate;
	@columnNames(name = "是否乳制品")
	private String isDiaryproduct;
	@columnNames(name = "有条件审批")
	private String provCondition;
	@columnNames(name = "商品可退货属性")
	private String productReturn;
	@columnNames(name = "退货条件")
	private String condition;
	@columnNames(name = "参考结束时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date consultEnddate;
	@columnNames(name = "资质组id")
	private String qaFileId; // 资质组id
	@columnNames(name = "资质组名称")
	private String qaFilename; // 资质组名称
	@columnNames(name = "alltier")
	private String allTier;
	@columnNames(name = "tier1")
	private String tier1; // 区域
	@columnNames(name = "tier2")
	private String tier2; // Tier
	@columnNames(name = "tier345")
	private String tier345;
	@columnNames(name = "存储类型")
	private String storeType;
	@columnNames(name = "交易中心")
	private String tradeZone;

	@columnNames(name = "主商品名称")
	private String mainProductname;
	@columnNames(name = "主商品编号")
	private String mainProductid;

	private String processInstanceid; // 新增流程实例
	private String updateInstanceid; // 修改流程实例
	private Integer checkPerson; // 当前审批人
	// NEXT_CHECKPERSON
	private String nextCheckperson; // 下一个审批人节点
	@columnNames(name = "建议零售价")
	private String sugRetailprice; // 建议零售价

	@columnNames(name = "有效期限")
	private String validDays;
	@columnNames(name = "保质期天数")
	private Integer sxDays;
	@columnNames(name = "系列id")
	private String serialId; // 系列id
	@columnNames(name = "系列名称")
	private String serialName;// 系列名称
	@columnNames(name = "分类Id")
	private String classId;
	@columnNames(name = "分类名称")
	private String className;

	private String addProcessname; // 新增流程名称
	private String updateProcekey; // 修改流程businesskey

	private String createdstr;
	private String createdEname; // 创建人英文名
	private String createdEmp; // 创建人工号

	@columnNames(name = "是否做退货销售")
	private String isreturnSale;
	@columnNames(name = "不可改价")
	private String isupdatePrice;
	@columnNames(name = " 不可重复销售")
	private String isiterateSales;

	@columnNames(name = "原产国Id")
	private String originCountryid;
	@columnNames(name = "税收分类名称")
	private String rateClass;

	private String rmsStatus;
	private String rmsRemake;
	private String rmsUpdate;
	private String userDept;
	private String userGroupcode;

	@columnNames(name = "pog生效方式")
	private String pogWays;
	@columnNames(name = "POG部门编码")
	private String pogDeparment;

	private String conditions;
	@columnNames(name = "单件规格")
	private String standardOfUnit;

	private String isRmsRever; // 是否rms驳回重新提交单据

	private String rmsReverBusinesskey;

	private Integer BrandCnUdaId;
	private Integer BrandCnUdaValue;
	private Integer BrandEnUdaId;
	private Integer BrandEnUdaValue;
	private Integer motherCompanyId;
	private Integer brandInfoId;

	@Column(name = "brand_cn_uda_id", nullable = false, length = 22)
	public Integer getBrandCnUdaId() {
		return BrandCnUdaId;
	}

	public void setBrandCnUdaId(Integer brandCnUdaId) {
		BrandCnUdaId = brandCnUdaId;
	}

	@Column(name = "brand_cn_uda_value", nullable = false, length = 22)
	public Integer getBrandCnUdaValue() {
		return BrandCnUdaValue;
	}

	public void setBrandCnUdaValue(Integer brandCnUdaValue) {
		BrandCnUdaValue = brandCnUdaValue;
	}

	@Column(name = "brand_en_uda_id", nullable = false, length = 22)
	public Integer getBrandEnUdaId() {
		return BrandEnUdaId;
	}

	public void setBrandEnUdaId(Integer brandEnUdaId) {
		BrandEnUdaId = brandEnUdaId;
	}

	@Column(name = "brand_en_uda_value", nullable = false, length = 22)
	public Integer getBrandEnUdaValue() {
		return BrandEnUdaValue;
	}

	public void setBrandEnUdaValue(Integer brandEnUdaValue) {
		BrandEnUdaValue = brandEnUdaValue;
	}

	@Column(name = "rms_rever_businesskey", nullable = true, length = 50)
	public String getRmsReverBusinesskey() {
		return rmsReverBusinesskey;
	}

	public void setRmsReverBusinesskey(String rmsReverBusinesskey) {
		this.rmsReverBusinesskey = rmsReverBusinesskey;
	}

	@Column(name = "is_rms_rever", nullable = true, length = 50)
	public String getIsRmsRever() {
		return isRmsRever;
	}

	public void setIsRmsRever(String isRmsRever) {
		this.isRmsRever = isRmsRever;
	}

	@Column(name = "standard_of_unit", nullable = true, length = 500)
	public String getStandardOfUnit() {
		return standardOfUnit;
	}

	public void setStandardOfUnit(String standardOfUnit) {
		this.standardOfUnit = standardOfUnit;
	}

	@Column(name = "transport_storage_name", nullable = true, length = 500)
	public String getTransportStorageName() {
		return transportStorageName;
	}

	public void setTransportStorageName(String transportStorageName) {
		this.transportStorageName = transportStorageName;
	}

	@Column(name = "product_license_name", nullable = true, length = 500)
	public String getProductLicenseName() {
		return productLicenseName;
	}

	public void setProductLicenseName(String productLicenseName) {
		this.productLicenseName = productLicenseName;
	}

	@Column(name = "special_requier_name", nullable = true, length = 500)
	public String getSpecialRequierName() {
		return specialRequierName;
	}

	public void setSpecialRequierName(String specialRequierName) {
		this.specialRequierName = specialRequierName;
	}

	@Column(name = "conditions", nullable = true, length = 255)
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	@Column(name = "rms_update", nullable = true, length = 255)
	public String getRmsUpdate() {
		return rmsUpdate;
	}

	public void setRmsUpdate(String rmsUpdate) {
		this.rmsUpdate = rmsUpdate;
	}

	@Column(name = "pog_ways", nullable = true, length = 255)
	public String getPogWays() {
		return pogWays;
	}

	public void setPogWays(String pogWays) {
		this.pogWays = pogWays;
	}

	@Column(name = "pog_deparment", nullable = true, length = 255)
	public String getPogDeparment() {
		return pogDeparment;
	}

	public void setPogDeparment(String pogDeparment) {
		this.pogDeparment = pogDeparment;
	}

	@Column(name = "user_dept", nullable = true, length = 255)
	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	@Column(name = "user_groupcode", nullable = true, length = 255)
	public String getUserGroupcode() {
		return userGroupcode;
	}

	public void setUserGroupcode(String userGroupcode) {
		this.userGroupcode = userGroupcode;
	}

	@Column(name = "rms_status", nullable = true, length = 255)
	public String getRmsStatus() {
		return rmsStatus;
	}

	public void setRmsStatus(String rmsStatus) {
		this.rmsStatus = rmsStatus;
	}

	@Column(name = "rms_remake", nullable = true, length = 255)
	public String getRmsRemake() {
		return rmsRemake;
	}

	public void setRmsRemake(String rmsRemake) {
		this.rmsRemake = rmsRemake;
	}

	public void setProductHeadId(Integer productHeadId) {
		this.productHeadId = productHeadId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_PLM_PRODUCT_HEAD", sequenceName = "SEQ_PLM_PRODUCT_HEAD", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_PLM_PRODUCT_HEAD", strategy = GenerationType.SEQUENCE)
	@Column(name = "product_head_id", nullable = false, length = 22)
	public Integer getProductHeadId() {
		return productHeadId;
	}

	public void setPlmCode(String plmCode) {
		this.plmCode = plmCode;
	}

	@Column(name = "plm_code", nullable = true, length = 50)
	public String getPlmCode() {
		return plmCode;
	}

	@Column(name = "created_ename", nullable = true, length = 255)
	public String getCreatedEname() {
		return createdEname;
	}

	public void setCreatedEname(String createdEname) {
		this.createdEname = createdEname;
	}

	@Column(name = "created_emp", nullable = true, length = 255)
	public String getCreatedEmp() {
		return createdEmp;
	}

	public void setCreatedEmp(String createdEmp) {
		this.createdEmp = createdEmp;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "supplier_count", nullable = false, length = 22)
	public Integer getSupplierCount() {
		return supplierCount;
	}

	public void setSupplierCount(Integer supplierCount) {
		this.supplierCount = supplierCount;
	}

	@Column(name = "department", nullable = true, length = 200)
	public String getDepartment() {
		return department;
	}

	public void setDepartmentDescript(String departmentDescript) {
		this.departmentDescript = departmentDescript;
	}

	@Column(name = "department_descript", nullable = true, length = 200)
	public String getDepartmentDescript() {
		return departmentDescript;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	@Column(name = "classes", nullable = true, length = 100)
	public String getClasses() {
		return classes;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	@Column(name = "class_desc", nullable = true, length = 200)
	public String getClassDesc() {
		return classDesc;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	@Column(name = "sub_class", nullable = true, length = 100)
	public String getSubClass() {
		return subClass;
	}

	public void setSubclassDesc(String subclassDesc) {
		this.subclassDesc = subclassDesc;
	}

	@Column(name = "subclass_desc", nullable = true, length = 100)
	public String getSubclassDesc() {
		return subclassDesc;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	@Column(name = "purchase_type", nullable = true, length = 100)
	public String getPurchaseType() {
		return purchaseType;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_name", nullable = true, length = 200)
	public String getProductName() {
		return productName;
	}

	public void setProductEname(String productEname) {
		this.productEname = productEname;
	}

	@Column(name = "product_ename", nullable = true, length = 150)
	public String getProductEname() {
		return productEname;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Column(name = "product_type", nullable = true, length = 150)
	public String getProductType() {
		return productType;
	}

	public void setBrandnameCn(String brandnameCn) {
		this.brandnameCn = brandnameCn;
	}

	@Column(name = "brandname_cn", nullable = true, length = 200)
	public String getBrandnameCn() {
		return brandnameCn;
	}

	public void setBrandnameEn(String brandnameEn) {
		this.brandnameEn = brandnameEn;
	}

	@Column(name = "brandname_en", nullable = true, length = 200)
	public String getBrandnameEn() {
		return brandnameEn;
	}

	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}

	@Column(name = "count_unit", nullable = true, length = 200)
	public String getCountUnit() {
		return countUnit;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	@Column(name = "other_info", nullable = true, length = 150)
	public String getOtherInfo() {
		return otherInfo;
	}

	public void setMarkerChannel(String markerChannel) {
		this.markerChannel = markerChannel;
	}

	@Column(name = "marker_channel", nullable = true, length = 200)
	public String getMarkerChannel() {
		return markerChannel;
	}

	public void setProductShape(String productShape) {
		this.productShape = productShape;
	}

	@Column(name = "product_shape", nullable = true, length = 150)
	public String getProductShape() {
		return productShape;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 22)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = false, length = 7)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = false, length = 22)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = false, length = 22)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = false, length = 7)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 22)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setGrossEarnings(String grossEarnings) {
		this.grossEarnings = grossEarnings;
	}

	@Column(name = "gross_earnings", nullable = true, length = 255)
	public String getGrossEarnings() {
		return grossEarnings;
	}

	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
	}

	@Column(name = "rate_class_code", nullable = true, length = 150)
	public String getRateClassCode() {
		return rateClassCode;
	}

	public void setDayDamage(String dayDamage) {
		this.dayDamage = dayDamage;
	}

	@Column(name = "day_damage", nullable = true, length = 150)
	public String getDayDamage() {
		return dayDamage;
	}

	public void setSpecialLicence(String specialLicence) {
		this.specialLicence = specialLicence;
	}

	@Column(name = "special_licence", nullable = true, length = 50)
	public String getSpecialLicence() {
		return specialLicence;
	}

	public void setProductResource(String productResource) {
		this.productResource = productResource;
	}

	@Column(name = "product_resource", nullable = true, length = 150)
	public String getProductResource() {
		return productResource;
	}

	public void setProductCategeery(String productCategeery) {
		this.productCategeery = productCategeery;
	}

	@Column(name = "product_categeery", nullable = true, length = 100)
	public String getProductCategeery() {
		return productCategeery;
	}

	public void setUniqueCommodities(String uniqueCommodities) {
		this.uniqueCommodities = uniqueCommodities;
	}

	@Column(name = "unique_commodities", nullable = true, length = 100)
	public String getUniqueCommodities() {
		return uniqueCommodities;
	}

	public void setSpecialtyProduct(String specialtyProduct) {
		this.specialtyProduct = specialtyProduct;
	}

	@Column(name = "specialty_product", nullable = true, length = 50)
	public String getSpecialtyProduct() {
		return specialtyProduct;
	}

	public void setProductProperties(String productProperties) {
		this.productProperties = productProperties;
	}

	@Column(name = "product_properties", nullable = true, length = 100)
	public String getProductProperties() {
		return productProperties;
	}

	public void setBuyingLevel(String buyingLevel) {
		this.buyingLevel = buyingLevel;
	}

	@Column(name = "buying_level", nullable = true, length = 150)
	public String getBuyingLevel() {
		return buyingLevel;
	}

	public void setDangerousProduct(String dangerousProduct) {
		this.dangerousProduct = dangerousProduct;
	}

	@Column(name = "dangerous_product", nullable = true, length = 50)
	public String getDangerousProduct() {
		return dangerousProduct;
	}

	public void setPosInfo(String posInfo) {
		this.posInfo = posInfo;
	}

	@Column(name = "pos_info", nullable = true, length = 50)
	public String getPosInfo() {
		return posInfo;
	}

	public void setInternationProduct(String internationProduct) {
		this.internationProduct = internationProduct;
	}

	@Column(name = "internation_product", nullable = true, length = 50)
	public String getInternationProduct() {
		return internationProduct;
	}

	public void setSesionProduct(String sesionProduct) {
		this.sesionProduct = sesionProduct;
	}

	@Column(name = "sesion_product", nullable = true, length = 50)
	public String getSesionProduct() {
		return sesionProduct;
	}

	public void setTopProduct(String topProduct) {
		this.topProduct = topProduct;
	}

	@Column(name = "top_product", nullable = true, length = 50)
	public String getTopProduct() {
		return topProduct;
	}

	public void setMotherCompany(String motherCompany) {
		this.motherCompany = motherCompany;
	}

	@Column(name = "mother_company", nullable = true, length = 100)
	public String getMotherCompany() {
		return motherCompany;
	}

	public void setBluecapProduct(String bluecapProduct) {
		this.bluecapProduct = bluecapProduct;
	}

	@Column(name = "bluecap_product", nullable = true, length = 50)
	public String getBluecapProduct() {
		return bluecapProduct;
	}

	public void setCrossborderProduct(String crossborderProduct) {
		this.crossborderProduct = crossborderProduct;
	}

	@Column(name = "crossborder_product", nullable = true, length = 50)
	public String getCrossborderProduct() {
		return crossborderProduct;
	}

	public void setVcProduct(String vcProduct) {
		this.vcProduct = vcProduct;
	}

	@Column(name = "vc_product", nullable = true, length = 50)
	public String getVcProduct() {
		return vcProduct;
	}

	public void setWarehouseResource(String warehouseResource) {
		this.warehouseResource = warehouseResource;
	}

	@Column(name = "warehouse_resource", nullable = true, length = 50)
	public String getWarehouseResource() {
		return warehouseResource;
	}

	public void setCompanyDeletion(String companyDeletion) {
		this.companyDeletion = companyDeletion;
	}

	@Column(name = "company_deletion", nullable = true, length = 50)
	public String getCompanyDeletion() {
		return companyDeletion;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	@Column(name = "origin_country", nullable = true, length = 200)
	public String getOriginCountry() {
		return originCountry;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "unit", nullable = true, length = 100)
	public String getUnit() {
		return unit;
	}

	public void setWarehouseGetDay(Integer warehouseGetDay) {
		this.warehouseGetDay = warehouseGetDay;
	}

	@Column(name = "warehouse_get_day", nullable = true, length = 22)
	public Integer getWarehouseGetDay() {
		return warehouseGetDay;
	}

	public void setWarehousePostDay(Integer warehousePostDay) {
		this.warehousePostDay = warehousePostDay;
	}

	@Column(name = "warehouse_post_day", nullable = true, length = 22)
	public Integer getWarehousePostDay() {
		return warehousePostDay;
	}

	public void setRangOb(String rangOb) {
		this.rangOb = rangOb;
	}

	@Column(name = "rang_ob", nullable = true, length = 100)
	public String getRangOb() {
		return rangOb;
	}

	public void setPowerOb(String powerOb) {
		this.powerOb = powerOb;
	}

	@Column(name = "power_ob", nullable = true, length = 100)
	public String getPowerOb() {
		return powerOb;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	@Column(name = "tier", nullable = true, length = 100)
	public String getTier() {
		return tier;
	}

	public void setSpecialRequier(String specialRequier) {
		this.specialRequier = specialRequier;
	}

	@Column(name = "special_requier", nullable = true, length = 50)
	public String getSpecialRequier() {
		return specialRequier;
	}

	public void setTransportStorage(String transportStorage) {
		this.transportStorage = transportStorage;
	}

	@Column(name = "transport_storage", nullable = true, length = 50)
	public String getTransportStorage() {
		return transportStorage;
	}

	public void setProductLicense(String productLicense) {
		this.productLicense = productLicense;
	}

	@Column(name = "product_license", nullable = true, length = 100)
	public String getProductLicense() {
		return productLicense;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "order_status", nullable = true, length = 50)
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "start_date", nullable = true, length = 7)
	public Date getStartDate() {
		return startDate;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	@Column(name = "ob_id", nullable = true, length = 100)
	public String getObId() {
		return obId;
	}

	public void setObId(String obId) {
		this.obId = obId;
	}

	@Column(name = "group_brand", nullable = true, length = 100)
	public String getGroupBrand() {
		return groupBrand;
	}

	public void setGroupBrand(String groupBrand) {
		this.groupBrand = groupBrand;
	}

	@Column(name = "is_updatecheck", nullable = true, length = 100)
	public String getIsUpdatecheck() {
		return isUpdatecheck;
	}

	public void setIsUpdatecheck(String isUpdatecheck) {
		this.isUpdatecheck = isUpdatecheck;
	}

	@Column(name = "pricewar_product", nullable = true, length = 255)
	public String getPricewarProduct() {
		return pricewarProduct;
	}

	public void setPricewarProduct(String pricewarProduct) {
		this.pricewarProduct = pricewarProduct;
	}

	@Column(name = "consignment_type", nullable = true, length = 255)
	public String getConsignmentType() {
		return consignmentType;
	}

	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	@Column(name = "consignment_rate", nullable = true, length = 255)
	public String getConsignmentRate() {
		return consignmentRate;
	}

	public void setConsignmentRate(String consignmentRate) {
		this.consignmentRate = consignmentRate;
	}

	@Column(name = "sales_qty", nullable = true, length = 255)
	public String getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
	}

	@Column(name = "consult_productno", nullable = true, length = 255)
	public String getConsultProductno() {
		return consultProductno;
	}

	public void setConsultProductno(String consultProductno) {
		this.consultProductno = consultProductno;
	}

	@Column(name = "im_productno", nullable = true, length = 255)
	public String getImProductno() {
		return imProductno;
	}

	public void setImProductno(String imProductno) {
		this.imProductno = imProductno;
	}

	@Column(name = "im_productname", nullable = true, length = 255)
	public String getImProductname() {
		return imProductname;
	}

	public void setImProductname(String imProductname) {
		this.imProductname = imProductname;
	}

	@Column(name = "consult_productname", nullable = true, length = 255)
	public String getConsultProductname() {
		return consultProductname;
	}

	public void setConsultProductname(String consultProductname) {
		this.consultProductname = consultProductname;
	}

	@Column(name = "consult_date", nullable = true, length = 7)
	public Date getConsultDate() {
		return consultDate;
	}

	public void setConsultDate(Date consultDate) {
		this.consultDate = consultDate;
	}

	@Column(name = "is_diaryproduct", nullable = true, length = 255)
	public String getIsDiaryproduct() {
		return isDiaryproduct;
	}

	public void setIsDiaryproduct(String isDiaryproduct) {
		this.isDiaryproduct = isDiaryproduct;
	}

	@Column(name = "prov_condition", nullable = true, length = 255)
	public String getProvCondition() {
		return provCondition;
	}

	public void setProvCondition(String provCondition) {
		this.provCondition = provCondition;
	}

	@Column(name = "product_return", nullable = true, length = 255)
	public String getProductReturn() {
		return productReturn;
	}

	public void setProductReturn(String productReturn) {
		this.productReturn = productReturn;
	}

	@Column(name = "condition", nullable = true, length = 255)
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Column(name = "consult_enddate", nullable = true, length = 7)
	public Date getConsultEnddate() {
		return consultEnddate;
	}

	public void setConsultEnddate(Date consultEnddate) {
		this.consultEnddate = consultEnddate;
	}

	@Column(name = "qa_file_id", nullable = true, length = 255)
	public String getQaFileId() {
		return qaFileId;
	}

	public void setQaFileId(String qaFileId) {
		this.qaFileId = qaFileId;
	}

	@Column(name = "qa_filename", nullable = true, length = 255)
	public String getQaFilename() {
		return qaFilename;
	}

	public void setQaFilename(String qaFilename) {
		this.qaFilename = qaFilename;
	}

	@Column(name = "all_tier", nullable = true, length = 255)
	public String getAllTier() {
		return allTier;
	}

	public void setAllTier(String allTier) {
		this.allTier = allTier;
	}

	@Column(name = "tier1", nullable = true, length = 255)
	public String getTier1() {
		return tier1;
	}

	public void setTier1(String tier1) {
		this.tier1 = tier1;
	}

	@Column(name = "tier2", nullable = true, length = 255)
	public String getTier2() {
		return tier2;
	}

	public void setTier2(String tier2) {
		this.tier2 = tier2;
	}

	@Column(name = "tier345", nullable = true, length = 255)
	public String getTier345() {
		return tier345;
	}

	public void setTier345(String tier345) {
		this.tier345 = tier345;
	}

	@Column(name = "store_type", nullable = true, length = 255)
	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	@Column(name = "trade_zone", nullable = true, length = 255)
	public String getTradeZone() {
		return tradeZone;
	}

	public void setTradeZone(String tradeZone) {
		this.tradeZone = tradeZone;
	}

	@Column(name = "main_productname", nullable = true, length = 255)
	public String getMainProductname() {
		return mainProductname;
	}

	public void setMainProductname(String mainProductname) {
		this.mainProductname = mainProductname;
	}

	@Column(name = "main_productid", nullable = true, length = 255)
	public String getMainProductid() {
		return mainProductid;
	}

	public void setMainProductid(String mainProductid) {
		this.mainProductid = mainProductid;
	}

	@Column(name = "process_instanceid", nullable = true, length = 255)
	public String getProcessInstanceid() {
		return processInstanceid;
	}

	public void setProcessInstanceid(String processInstanceid) {
		this.processInstanceid = processInstanceid;
	}

	@Column(name = "update_instanceid", nullable = true, length = 255)
	public String getUpdateInstanceid() {
		return updateInstanceid;
	}

	public void setUpdateInstanceid(String updateInstanceid) {
		this.updateInstanceid = updateInstanceid;
	}

	@Column(name = "check_person", nullable = true, length = 11)
	public Integer getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(Integer checkPerson) {
		this.checkPerson = checkPerson;
	}

	@Column(name = "rms_code", nullable = true, length = 11)
	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}

	@Column(name = "next_checkperson", nullable = true, length = 255)
	public String getNextCheckperson() {
		return nextCheckperson;
	}

	public void setNextCheckperson(String nextCheckperson) {
		this.nextCheckperson = nextCheckperson;
	}

	@Column(name = "sug_retailprice", nullable = true, length = 255)
	public String getSugRetailprice() {
		return sugRetailprice;
	}

	public void setSugRetailprice(String sugRetailprice) {
		this.sugRetailprice = sugRetailprice;
	}

	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}

	@Column(name = "valid_days", nullable = true, length = 22)
	public String getValidDays() {
		return validDays;
	}

	public void setSxDays(Integer sxDays) {
		this.sxDays = sxDays;
	}

	@Column(name = "sx_days", nullable = true, length = 22)
	public Integer getSxDays() {
		return sxDays;
	}

	@Column(name = "serial_id", nullable = true, length = 255)
	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	@Column(name = "serial_name", nullable = true, length = 255)
	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	@Column(name = "class_id", nullable = true, length = 255)
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Column(name = "class_name", nullable = true, length = 255)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "add_processname", nullable = true, length = 255)
	public String getAddProcessname() {
		return addProcessname;
	}

	public void setAddProcessname(String addProcessname) {
		this.addProcessname = addProcessname;
	}

	@Column(name = "update_procekey", nullable = true, length = 255)
	public String getUpdateProcekey() {
		return updateProcekey;
	}

	public void setUpdateProcekey(String updateProcekey) {
		this.updateProcekey = updateProcekey;
	}

	@Column(name = "createdstr", nullable = true, length = 255)
	public String getCreatedstr() {
		return createdstr;
	}

	public void setCreatedstr(String createdstr) {
		this.createdstr = createdstr;
	}

	@Column(name = "isreturn_sale", nullable = true, length = 255)
	public String getIsreturnSale() {
		return isreturnSale;
	}

	public void setIsreturnSale(String isreturnSale) {
		this.isreturnSale = isreturnSale;
	}

	@Column(name = "isupdate_price", nullable = true, length = 255)
	public String getIsupdatePrice() {
		return isupdatePrice;
	}

	public void setIsupdatePrice(String isupdatePrice) {
		this.isupdatePrice = isupdatePrice;
	}

	@Column(name = "isiterate_sales", nullable = true, length = 255)
	public String getIsiterateSales() {
		return isiterateSales;
	}

	public void setIsiterateSales(String isiterateSales) {
		this.isiterateSales = isiterateSales;
	}

	@Column(name = "origin_countryid", nullable = true, length = 255)
	public String getOriginCountryid() {
		return originCountryid;
	}

	public void setOriginCountryid(String originCountryid) {
		this.originCountryid = originCountryid;
	}

	@Column(name = "rate_class", nullable = true, length = 255)
	public String getRateClass() {
		return rateClass;
	}

	public void setRateClass(String rateClass) {
		this.rateClass = rateClass;
	}

	@Column(name = "MOTHER_COMPANY_ID", nullable = true, length = 255)
	public Integer getMotherCompanyId() {
		return motherCompanyId;
	}

	public void setMotherCompanyId(Integer motherCompanyId) {
		this.motherCompanyId = motherCompanyId;
	}

	@Column(name = "PLM_BRANDINFO_ID", nullable = true, length = 255)
	public Integer getBrandInfoId() {
		return brandInfoId;
	}

	public void setBrandInfoId(Integer brandInfoId) {
		this.brandInfoId = brandInfoId;
	}
}
