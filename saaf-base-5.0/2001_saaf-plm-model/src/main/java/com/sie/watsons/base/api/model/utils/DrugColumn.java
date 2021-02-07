package com.sie.watsons.base.api.model.utils;


/**
 * K8 药品对接字段
 */
public class DrugColumn {

    private String drugCode = "";//药品代码
    private String productName = "";//药品名称
    private String commonName = "";//通用名称
    private String standard = "";// 基本单位(规格)
    private String backageUnit = "";//包装规格
    private String drugUnit = "";//药监包装规格
    private String drugForm = "";// 剂型
    private String unit = "";// 单位(规格)
    private String barCode = "";// 条形码
    private String producer = "";// 生产厂家
    private String drugPlace = "";// 产地
    private String marketPerson = "";// 上市可持有人
    private String symbol = "";// 批准文号
    private String storeCondition = "";// 存储条件
    private String symbolDate = "";// 批文有效期
    private String symbolDays = "";// 批文预警天数
    private String protectPeriod = "";// 保质期
    private String displayCycle = "";// 陈列周期
    private String businessCategory = "";// 经营类别
    private String typeCode = "";// 类别代码
    private String categoryName = "";// 类别名称
    private String isProtect = "";// 是否重点养护
    private String displayDays = "";// 陈列预警天数
    private String sxDays = "";// 效期预警天数
    private String supPrice = "";// 进价一 --供应商成本价
    private String supPrice2 = "";// 进价二 --供应商成本价
    private String price = "";// 售价一 --价格表其中一条就好
    private String price2 = "";// 售价四 --供应商成本价
    private String isPull = "";// 是否拆零
    private String isBatchnumber = "";// 是否属批号管理(默认1)
    private String presType = "";// 处方药类别
    private String isRx = "";// 是否处方药品(1是，0否)
    private String isCold=""; //是否冷藏
    private String isEphedrine="";//是否含麻黄碱
    private String supplierCode = "";// 供应商编号
    private String supplierName = "";// 供商名称
    private String gspManage = "";// GSP管理级别
    private String drugStandcode = "";// 药监统一编号
    private String drugStandard = "";// 药品本位码
    private String rang = "";// 所属经营范围
    private String qaStandard = "";// 质量标准
    private String fileInfo = "";// 批准证明文件及其附件
    private String opk = "2";// OPK(1.新建、2.更新、3.停用)

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getBackageUnit() {
        return backageUnit;
    }

    public void setBackageUnit(String backageUnit) {
        this.backageUnit = backageUnit;
    }

    public String getDrugUnit() {
        return drugUnit;
    }

    public void setDrugUnit(String drugUnit) {
        this.drugUnit = drugUnit;
    }

    public String getDrugForm() {
        return drugForm;
    }

    public void setDrugForm(String drugForm) {
        this.drugForm = drugForm;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDrugPlace() {
        return drugPlace;
    }

    public void setDrugPlace(String drugPlace) {
        this.drugPlace = drugPlace;
    }

    public String getMarketPerson() {
        return marketPerson;
    }

    public void setMarketPerson(String marketPerson) {
        this.marketPerson = marketPerson;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getStoreCondition() {
        return storeCondition;
    }

    public void setStoreCondition(String storeCondition) {
        this.storeCondition = storeCondition;
    }

    public String getSymbolDate() {
        return symbolDate;
    }

    public void setSymbolDate(String symbolDate) {
        this.symbolDate = symbolDate;
    }

    public String getSymbolDays() {
        return symbolDays;
    }

    public void setSymbolDays(String symbolDays) {
        this.symbolDays = symbolDays;
    }

    public String getProtectPeriod() {
        return protectPeriod;
    }

    public void setProtectPeriod(String protectPeriod) {
        this.protectPeriod = protectPeriod;
    }

    public String getDisplayCycle() {
        return displayCycle;
    }

    public void setDisplayCycle(String displayCycle) {
        this.displayCycle = displayCycle;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIsProtect() {
        return isProtect;
    }

    public void setIsProtect(String isProtect) {
        this.isProtect = isProtect;
    }

    public String getDisplayDays() {
        return displayDays;
    }

    public void setDisplayDays(String displayDays) {
        this.displayDays = displayDays;
    }

    public String getSxDays() {
        return sxDays;
    }

    public void setSxDays(String sxDays) {
        this.sxDays = sxDays;
    }

    public String getSupPrice() {
        return supPrice;
    }

    public void setSupPrice(String supPrice) {
        this.supPrice = supPrice;
    }

    public String getSupPrice2() {
        return supPrice2;
    }

    public void setSupPrice2(String supPrice2) {
        this.supPrice2 = supPrice2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getIsPull() {
        return isPull;
    }

    public void setIsPull(String isPull) {
        this.isPull = isPull;
    }

    public String getIsBatchnumber() {
        return isBatchnumber;
    }

    public void setIsBatchnumber(String isBatchnumber) {
        this.isBatchnumber = isBatchnumber;
    }

    public String getPresType() {
        return presType;
    }

    public void setPresType(String presType) {
        this.presType = presType;
    }

    public String getIsRx() {
        return isRx;
    }

    public void setIsRx(String isRx) {
        this.isRx = isRx;
    }

    public String getIsCold() {
        return isCold;
    }

    public void setIsCold(String isCold) {
        this.isCold = isCold;
    }

    public String getIsEphedrine() {
        return isEphedrine;
    }

    public void setIsEphedrine(String isEphedrine) {
        this.isEphedrine = isEphedrine;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getGspManage() {
        return gspManage;
    }

    public void setGspManage(String gspManage) {
        this.gspManage = gspManage;
    }

    public String getDrugStandcode() {
        return drugStandcode;
    }

    public void setDrugStandcode(String drugStandcode) {
        this.drugStandcode = drugStandcode;
    }

    public String getDrugStandard() {
        return drugStandard;
    }

    public void setDrugStandard(String drugStandard) {
        this.drugStandard = drugStandard;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getQaStandard() {
        return qaStandard;
    }

    public void setQaStandard(String qaStandard) {
        this.qaStandard = qaStandard;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getOpk() {
        return opk;
    }

    public void setOpk(String opk) {
        this.opk = opk;
    }
}
