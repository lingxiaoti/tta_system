package com.sie.watsons.base.report.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liujiajie on 2019/7/11/011.
 */
@Entity
@Table(name="TTA_PROMOTION_GUIDELINE")
public class TtaPromotionGuidelineEntity_HI {

    private Integer osdId;
    private String osd;
    private String offerCode;
    private String step;
    private String effectArea;
    private String EffectCityTier;
    private String EffectStoreType;
    private String StoreLicenseStatus;
    private String Dept;
    private String ItemCode;
    private String ChineseDescription;
    private String EnglishDescription;
    private String Display;
    private String BuyerNote;
    private String POG;
    private String POGStoreCount;
    private String StockWkCoverage13;
    private String PromPack;
    private String RetailPrice;
    private String PromoPrice;
    private String PromoMech;
    private String MemberOffer;
    private String UnitCost;
    private String NewCost;
    private String OrderDiscount;
    private String CostChangePeriod;
    private String GPPer;
    private String RTV;
    private String ReturnDate;
    private String PromoType;
    private String MDQ;
    private String MaxDQ;
    private String Iner;
    private String StoreCount;
    private String ForctDiscrepancy;
    private String TradingForctQty;
    private String TradingForctSales;
    private String percentage;
    private String Reason;
    private String SupplyForctQty;
    private String WH998;
    private String WH998FreeGoods;
    private String WH997;
    private String WH997FreeGoods;
    private String WH999;
    private String WH999FreeGoods;
    private String WH992;
    private String WH992FreeGoods;
    private String WH995;
    private String WH995FreeGoods;
    private String WH991;
    private String WH991FreeGoods;
    private String WH996;
    private String WH996FreeGoods;
    private String WH9901;
    private String WH9901FreeGoods;
    private String WH9902;
    private String WH9902FreeGoods;
    private String WH9903;
    private String WH9903FreeGoods;
    private String WH9906;
    private String WH9906FreeGoods;
    private String WH9907;
    private String WH9907FreeGoods;
    private String WH998Per;
    private String WH997Per;
    private String WH999Per;
    private String WH992Per;
    private String WH995Per;
    private String WH991Per;
    private String WH996Per;
    private String WH9901Per;
    private String WH9902Per;
    private String WH9903Per;
    private String WH9906Per;
    private String WH9907Per;
    private String SupplierCode998;
    private String SupplierName998;
    private String SupplierCode997;
    private String SupplierName997;
    private String SupplierCode999;
    private String SupplierName999;
    private String SupplierCode992;
    private String SupplierName992;
    private String SupplierCode995;
    private String SupplierName995;
    private String SupplierCode991;
    private String SupplierName991;
    private String SupplierCode996;
    private String SupplierName996;
    private String SupplierCode9901;
    private String SupplierName9901;
    private String SupplierCode9902;
    private String SupplierName9902;
    private String SupplierCode9903;
    private String SupplierName9903;
    private String SupplierCode9906;
    private String SupplierName9906;
    private String SupplierCode9907;
    private String SupplierName9907;
    private String LocationList998;
    private String LocationList997;
    private String LocationList999;
    private String LocationList992;
    private String LocationList995;
    private String LocationList991;
    private String LocationList996;
    private String LocationList9901;
    private String LocationList9902;
    private String LocationList9903;
    private String LocationList9906;
    private String LocationList9907;
    private String FreeSupplierCode998;
    private String FreeSupplierName998;
    private String FreeSupplierCode997;
    private String FreeSupplierName997;
    private String FreeSupplierCode999;
    private String FreeSupplierName999;
    private String FreeSupplierCode992;
    private String FreeSupplierName992;
    private String FreeSupplierCode995;
    private String FreeSupplierName995;
    private String FreeSupplierCode991;
    private String FreeSupplierName991;
    private String FreeSupplierCode996;
    private String FreeSupplierName996;
    private String FreeSupplierCode9901;
    private String FreeSupplierName9901;
    private String FreeSupplierCode9902;
    private String FreeSupplierName9902;
    private String FreeSupplierCode9903;
    private String FreeSupplierName9903;
    private String FreeSupplierCode9906;
    private String FreeSupplierName9906;
    private String FreeSupplierCode9907;
    private String FreeSupplierName9907;
    private String RefSalesPeriod;
    private String RefArea;
    private String RefOSDType;
    private String RefItem;
    private String RefDesc;
    private String HistoricSalesQty;
    private String HistoricSalesAMT;
    private String HistoricAvgUnitPrice;
    private String DisplayonDM;
    private String Matchtheme;
    private String Consignment;
    private String NewItem;
    private String OwnBrand;
    private String LimitedItem;
    private String ExclusiveItem;
    private String OnShelfPromItem;
    private String ClearanceItem;
    private String FreeGoods;
    private String Promocompensation;
    private String Compensationdetail;

    private String promNumber;
    private Date promPeriodFrom;
    private Date promPeriodTo;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;

    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_TTA_PROMOTION_GUIDELINE", sequenceName = "SEQ_TTA_PROMOTION_GUIDELINE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_TTA_PROMOTION_GUIDELINE", strategy = GenerationType.SEQUENCE)
    @Column(name="OSD_ID", nullable=false, length=22)
    public Integer getOsdId() {
        return osdId;
    }

    public void setOsdId(Integer osdId) {
        this.osdId = osdId;
    }

    @Column(name="OSD")
    public String getOsd() {
        return osd;
    }

    public void setOsd(String osd) {
        this.osd = osd;
    }
    @Column(name="OFFERCODE")
    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }
    @Column(name="STEP")
    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
    @Column(name="EFFECTAREA")
    public String getEffectArea() {
        return effectArea;
    }

    public void setEffectArea(String effectArea) {
        this.effectArea = effectArea;
    }
    @Column(name="EFFECTCITYTIER")
    public String getEffectCityTier() {
        return EffectCityTier;
    }

    public void setEffectCityTier(String effectCityTier) {
        EffectCityTier = effectCityTier;
    }
    @Column(name="EFFECTSTORETYPE")
    public String getEffectStoreType() {
        return EffectStoreType;
    }

    public void setEffectStoreType(String effectStoreType) {
        EffectStoreType = effectStoreType;
    }
    @Column(name="STORELICENSESTATUS")
    public String getStoreLicenseStatus() {
        return StoreLicenseStatus;
    }

    public void setStoreLicenseStatus(String storeLicenseStatus) {
        StoreLicenseStatus = storeLicenseStatus;
    }
    @Column(name="DEPT")
    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }
    @Column(name="ITEMCODE")
    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }
    @Column(name="CHINESEDESCRIPTION")
    public String getChineseDescription() {
        return ChineseDescription;
    }

    public void setChineseDescription(String chineseDescription) {
        ChineseDescription = chineseDescription;
    }
    @Column(name="ENGLISHDESCRIPTION")
    public String getEnglishDescription() {
        return EnglishDescription;
    }

    public void setEnglishDescription(String englishDescription) {
        EnglishDescription = englishDescription;
    }
    @Column(name="DISPLAY")
    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }
    @Column(name="BUYERNOTE")
    public String getBuyerNote() {
        return BuyerNote;
    }

    public void setBuyerNote(String buyerNote) {
        BuyerNote = buyerNote;
    }
    @Column(name="POG")
    public String getPOG() {
        return POG;
    }

    public void setPOG(String POG) {
        this.POG = POG;
    }
    @Column(name="POGSTORECOUNT")
    public String getPOGStoreCount() {
        return POGStoreCount;
    }

    public void setPOGStoreCount(String POGStoreCount) {
        this.POGStoreCount = POGStoreCount;
    }
    @Column(name="STOCKWKCOVERAGE13")
    public String getStockWkCoverage13() {
        return StockWkCoverage13;
    }

    public void setStockWkCoverage13(String stockWkCoverage13) {
        StockWkCoverage13 = stockWkCoverage13;
    }
    @Column(name="PROMPACK")
    public String getPromPack() {
        return PromPack;
    }

    public void setPromPack(String promPack) {
        PromPack = promPack;
    }
    @Column(name="RETAILPRICE")
    public String getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        RetailPrice = retailPrice;
    }
    @Column(name="PROMOPRICE")
    public String getPromoPrice() {
        return PromoPrice;
    }

    public void setPromoPrice(String promoPrice) {
        PromoPrice = promoPrice;
    }
    @Column(name="PROMOMECH")
    public String getPromoMech() {
        return PromoMech;
    }

    public void setPromoMech(String promoMech) {
        PromoMech = promoMech;
    }
    @Column(name="MEMBEROFFER")
    public String getMemberOffer() {
        return MemberOffer;
    }

    public void setMemberOffer(String memberOffer) {
        MemberOffer = memberOffer;
    }
    @Column(name="UNITCOST")
    public String getUnitCost() {
        return UnitCost;
    }

    public void setUnitCost(String unitCost) {
        UnitCost = unitCost;
    }
    @Column(name="NEWCOST")
    public String getNewCost() {
        return NewCost;
    }

    public void setNewCost(String newCost) {
        NewCost = newCost;
    }
    @Column(name="ORDERDISCOUNT")
    public String getOrderDiscount() {
        return OrderDiscount;
    }

    public void setOrderDiscount(String orderDiscount) {
        OrderDiscount = orderDiscount;
    }
    @Column(name="COSTCHANGEPERIOD")
    public String getCostChangePeriod() {
        return CostChangePeriod;
    }

    public void setCostChangePeriod(String costChangePeriod) {
        CostChangePeriod = costChangePeriod;
    }
    @Column(name="GPPER")
    public String getGPPer() {
        return GPPer;
    }

    public void setGPPer(String GPPer) {
        this.GPPer = GPPer;
    }
    @Column(name="RTV")
    public String getRTV() {
        return RTV;
    }

    public void setRTV(String RTV) {
        this.RTV = RTV;
    }
    @Column(name="RETURNDATE")
    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }
    @Column(name="PROMOTYPE")
    public String getPromoType() {
        return PromoType;
    }

    public void setPromoType(String promoType) {
        PromoType = promoType;
    }
    @Column(name="MDQ")
    public String getMDQ() {
        return MDQ;
    }

    public void setMDQ(String MDQ) {
        this.MDQ = MDQ;
    }
    @Column(name="MAXDQ")
    public String getMaxDQ() {
        return MaxDQ;
    }

    public void setMaxDQ(String maxDQ) {
        MaxDQ = maxDQ;
    }
    @Column(name="INER")
    public String getIner() {
        return Iner;
    }

    public void setIner(String iner) {
        Iner = iner;
    }
    @Column(name="STORECOUNT")
    public String getStoreCount() {
        return StoreCount;
    }

    public void setStoreCount(String storeCount) {
        StoreCount = storeCount;
    }
    @Column(name="FORCTDISCREPANCY")
    public String getForctDiscrepancy() {
        return ForctDiscrepancy;
    }

    public void setForctDiscrepancy(String forctDiscrepancy) {
        ForctDiscrepancy = forctDiscrepancy;
    }
    @Column(name="TRADINGFORCTQTY")
    public String getTradingForctQty() {
        return TradingForctQty;
    }

    public void setTradingForctQty(String tradingForctQty) {
        TradingForctQty = tradingForctQty;
    }
    @Column(name="TRADINGFORCTSALES")
    public String getTradingForctSales() {
        return TradingForctSales;
    }

    public void setTradingForctSales(String tradingForctSales) {
        TradingForctSales = tradingForctSales;
    }
    @Column(name="PERCENTAGE")
    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
    @Column(name="REASON")
    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }
    @Column(name="SUPPLYFORCTQTY")
    public String getSupplyForctQty() {
        return SupplyForctQty;
    }

    public void setSupplyForctQty(String supplyForctQty) {
        SupplyForctQty = supplyForctQty;
    }
    @Column(name="WH998")
    public String getWH998() {
        return WH998;
    }

    public void setWH998(String WH998) {
        this.WH998 = WH998;
    }
    @Column(name="WH998FREEGOODS")
    public String getWH998FreeGoods() {
        return WH998FreeGoods;
    }

    public void setWH998FreeGoods(String WH998FreeGoods) {
        this.WH998FreeGoods = WH998FreeGoods;
    }
    @Column(name="WH997")
    public String getWH997() {
        return WH997;
    }

    public void setWH997(String WH997) {
        this.WH997 = WH997;
    }
    @Column(name="WH997FREEGOODS")
    public String getWH997FreeGoods() {
        return WH997FreeGoods;
    }

    public void setWH997FreeGoods(String WH997FreeGoods) {
        this.WH997FreeGoods = WH997FreeGoods;
    }
    @Column(name="WH999")
    public String getWH999() {
        return WH999;
    }

    public void setWH999(String WH999) {
        this.WH999 = WH999;
    }
    @Column(name="WH999FREEGOODS")
    public String getWH999FreeGoods() {
        return WH999FreeGoods;
    }

    public void setWH999FreeGoods(String WH999FreeGoods) {
        this.WH999FreeGoods = WH999FreeGoods;
    }
    @Column(name="WH992")
    public String getWH992() {
        return WH992;
    }

    public void setWH992(String WH992) {
        this.WH992 = WH992;
    }
    @Column(name="WH992FREEGOODS")
    public String getWH992FreeGoods() {
        return WH992FreeGoods;
    }

    public void setWH992FreeGoods(String WH992FreeGoods) {
        this.WH992FreeGoods = WH992FreeGoods;
    }
    @Column(name="WH995")
    public String getWH995() {
        return WH995;
    }

    public void setWH995(String WH995) {
        this.WH995 = WH995;
    }
    @Column(name="WH995FREEGOODS")
    public String getWH995FreeGoods() {
        return WH995FreeGoods;
    }

    public void setWH995FreeGoods(String WH995FreeGoods) {
        this.WH995FreeGoods = WH995FreeGoods;
    }
    @Column(name="WH991")
    public String getWH991() {
        return WH991;
    }

    public void setWH991(String WH991) {
        this.WH991 = WH991;
    }
    @Column(name="WH991FREEGOODS")
    public String getWH991FreeGoods() {
        return WH991FreeGoods;
    }

    public void setWH991FreeGoods(String WH991FreeGoods) {
        this.WH991FreeGoods = WH991FreeGoods;
    }
    @Column(name="WH996")
    public String getWH996() {
        return WH996;
    }

    public void setWH996(String WH996) {
        this.WH996 = WH996;
    }
    @Column(name="WH996FREEGOODS")
    public String getWH996FreeGoods() {
        return WH996FreeGoods;
    }

    public void setWH996FreeGoods(String WH996FreeGoods) {
        this.WH996FreeGoods = WH996FreeGoods;
    }
    @Column(name="WH9901")
    public String getWH9901() {
        return WH9901;
    }

    public void setWH9901(String WH9901) {
        this.WH9901 = WH9901;
    }
    @Column(name="WH9901FREEGOODS")
    public String getWH9901FreeGoods() {
        return WH9901FreeGoods;
    }

    public void setWH9901FreeGoods(String WH9901FreeGoods) {
        this.WH9901FreeGoods = WH9901FreeGoods;
    }
    @Column(name="WH9902")
    public String getWH9902() {
        return WH9902;
    }

    public void setWH9902(String WH9902) {
        this.WH9902 = WH9902;
    }
    @Column(name="WH9902FREEGOODS")
    public String getWH9902FreeGoods() {
        return WH9902FreeGoods;
    }

    public void setWH9902FreeGoods(String WH9902FreeGoods) {
        this.WH9902FreeGoods = WH9902FreeGoods;
    }
    @Column(name="WH9903")
    public String getWH9903() {
        return WH9903;
    }

    public void setWH9903(String WH9903) {
        this.WH9903 = WH9903;
    }
    @Column(name="WH9903FREEGOODS")
    public String getWH9903FreeGoods() {
        return WH9903FreeGoods;
    }

    public void setWH9903FreeGoods(String WH9903FreeGoods) {
        this.WH9903FreeGoods = WH9903FreeGoods;
    }
    @Column(name="WH9906")
    public String getWH9906() {
        return WH9906;
    }

    public void setWH9906(String WH9906) {
        this.WH9906 = WH9906;
    }
    @Column(name="WH9906FREEGOODS")
    public String getWH9906FreeGoods() {
        return WH9906FreeGoods;
    }

    public void setWH9906FreeGoods(String WH9906FreeGoods) {
        this.WH9906FreeGoods = WH9906FreeGoods;
    }
    @Column(name="WH9907")
    public String getWH9907() {
        return WH9907;
    }

    public void setWH9907(String WH9907) {
        this.WH9907 = WH9907;
    }
    @Column(name="WH9907FREEGOODS")
    public String getWH9907FreeGoods() {
        return WH9907FreeGoods;
    }

    public void setWH9907FreeGoods(String WH9907FreeGoods) {
        this.WH9907FreeGoods = WH9907FreeGoods;
    }
    @Column(name="WH998PER")
    public String getWH998Per() {
        return WH998Per;
    }

    public void setWH998Per(String WH998Per) {
        this.WH998Per = WH998Per;
    }
    @Column(name="WH997PER")
    public String getWH997Per() {
        return WH997Per;
    }

    public void setWH997Per(String WH997Per) {
        this.WH997Per = WH997Per;
    }
    @Column(name="WH999PER")
    public String getWH999Per() {
        return WH999Per;
    }

    public void setWH999Per(String WH999Per) {
        this.WH999Per = WH999Per;
    }
    @Column(name="WH992PER")
    public String getWH992Per() {
        return WH992Per;
    }

    public void setWH992Per(String WH992Per) {
        this.WH992Per = WH992Per;
    }
    @Column(name="WH995PER")
    public String getWH995Per() {
        return WH995Per;
    }

    public void setWH995Per(String WH995Per) {
        this.WH995Per = WH995Per;
    }
    @Column(name="WH991PER")
    public String getWH991Per() {
        return WH991Per;
    }

    public void setWH991Per(String WH991Per) {
        this.WH991Per = WH991Per;
    }
    @Column(name="WH996PER")
    public String getWH996Per() {
        return WH996Per;
    }

    public void setWH996Per(String WH996Per) {
        this.WH996Per = WH996Per;
    }
    @Column(name="WH9901PER")
    public String getWH9901Per() {
        return WH9901Per;
    }

    public void setWH9901Per(String WH9901Per) {
        this.WH9901Per = WH9901Per;
    }
    @Column(name="WH9902PER")
    public String getWH9902Per() {
        return WH9902Per;
    }

    public void setWH9902Per(String WH9902Per) {
        this.WH9902Per = WH9902Per;
    }
    @Column(name="WH9903PER")
    public String getWH9903Per() {
        return WH9903Per;
    }

    public void setWH9903Per(String WH9903Per) {
        this.WH9903Per = WH9903Per;
    }
    @Column(name="WH9906PER")
    public String getWH9906Per() {
        return WH9906Per;
    }

    public void setWH9906Per(String WH9906Per) {
        this.WH9906Per = WH9906Per;
    }
    @Column(name="WH9907PER")
    public String getWH9907Per() {
        return WH9907Per;
    }

    public void setWH9907Per(String WH9907Per) {
        this.WH9907Per = WH9907Per;
    }
    @Column(name="SUPPLIERCODE998")
    public String getSupplierCode998() {
        return SupplierCode998;
    }

    public void setSupplierCode998(String supplierCode998) {
        SupplierCode998 = supplierCode998;
    }
    @Column(name="SUPPLIERNAME998")
    public String getSupplierName998() {
        return SupplierName998;
    }

    public void setSupplierName998(String supplierName998) {
        SupplierName998 = supplierName998;
    }
    @Column(name="SUPPLIERCODE997")
    public String getSupplierCode997() {
        return SupplierCode997;
    }

    public void setSupplierCode997(String supplierCode997) {
        SupplierCode997 = supplierCode997;
    }
    @Column(name="SUPPLIERNAME997")
    public String getSupplierName997() {
        return SupplierName997;
    }

    public void setSupplierName997(String supplierName997) {
        SupplierName997 = supplierName997;
    }
    @Column(name="SUPPLIERCODE999")
    public String getSupplierCode999() {
        return SupplierCode999;
    }

    public void setSupplierCode999(String supplierCode999) {
        SupplierCode999 = supplierCode999;
    }
    @Column(name="SUPPLIERNAME999")
    public String getSupplierName999() {
        return SupplierName999;
    }

    public void setSupplierName999(String supplierName999) {
        SupplierName999 = supplierName999;
    }
    @Column(name="SUPPLIERCODE992")
    public String getSupplierCode992() {
        return SupplierCode992;
    }

    public void setSupplierCode992(String supplierCode992) {
        SupplierCode992 = supplierCode992;
    }
    @Column(name="SUPPLIERNAME992")
    public String getSupplierName992() {
        return SupplierName992;
    }

    public void setSupplierName992(String supplierName992) {
        SupplierName992 = supplierName992;
    }
    @Column(name="SUPPLIERCODE995")
    public String getSupplierCode995() {
        return SupplierCode995;
    }

    public void setSupplierCode995(String supplierCode995) {
        SupplierCode995 = supplierCode995;
    }
    @Column(name="SUPPLIERNAME995")
    public String getSupplierName995() {
        return SupplierName995;
    }

    public void setSupplierName995(String supplierName995) {
        SupplierName995 = supplierName995;
    }
    @Column(name="SUPPLIERCODE991")
    public String getSupplierCode991() {
        return SupplierCode991;
    }

    public void setSupplierCode991(String supplierCode991) {
        SupplierCode991 = supplierCode991;
    }
    @Column(name="SUPPLIERNAME991")
    public String getSupplierName991() {
        return SupplierName991;
    }

    public void setSupplierName991(String supplierName991) {
        SupplierName991 = supplierName991;
    }
    @Column(name="SUPPLIERCODE996")
    public String getSupplierCode996() {
        return SupplierCode996;
    }

    public void setSupplierCode996(String supplierCode996) {
        SupplierCode996 = supplierCode996;
    }
    @Column(name="SUPPLIERNAME996")
    public String getSupplierName996() {
        return SupplierName996;
    }

    public void setSupplierName996(String supplierName996) {
        SupplierName996 = supplierName996;
    }
    @Column(name="SUPPLIERCODE9901")
    public String getSupplierCode9901() {
        return SupplierCode9901;
    }

    public void setSupplierCode9901(String supplierCode9901) {
        SupplierCode9901 = supplierCode9901;
    }
    @Column(name="SUPPLIERNAME9901")
    public String getSupplierName9901() {
        return SupplierName9901;
    }

    public void setSupplierName9901(String supplierName9901) {
        SupplierName9901 = supplierName9901;
    }
    @Column(name="SUPPLIERCODE9902")
    public String getSupplierCode9902() {
        return SupplierCode9902;
    }

    public void setSupplierCode9902(String supplierCode9902) {
        SupplierCode9902 = supplierCode9902;
    }
    @Column(name="SUPPLIERNAME9902")
    public String getSupplierName9902() {
        return SupplierName9902;
    }

    public void setSupplierName9902(String supplierName9902) {
        SupplierName9902 = supplierName9902;
    }
    @Column(name="SUPPLIERCODE9903")
    public String getSupplierCode9903() {
        return SupplierCode9903;
    }

    public void setSupplierCode9903(String supplierCode9903) {
        SupplierCode9903 = supplierCode9903;
    }
    @Column(name="SUPPLIERNAME9903")
    public String getSupplierName9903() {
        return SupplierName9903;
    }

    public void setSupplierName9903(String supplierName9903) {
        SupplierName9903 = supplierName9903;
    }
    @Column(name="SUPPLIERCODE9906")
    public String getSupplierCode9906() {
        return SupplierCode9906;
    }

    public void setSupplierCode9906(String supplierCode9906) {
        SupplierCode9906 = supplierCode9906;
    }
    @Column(name="SUPPLIERNAME9906")
    public String getSupplierName9906() {
        return SupplierName9906;
    }

    public void setSupplierName9906(String supplierName9906) {
        SupplierName9906 = supplierName9906;
    }
    @Column(name="SUPPLIERCODE9907")
    public String getSupplierCode9907() {
        return SupplierCode9907;
    }

    public void setSupplierCode9907(String supplierCode9907) {
        SupplierCode9907 = supplierCode9907;
    }
    @Column(name="SUPPLIERNAME9907")
    public String getSupplierName9907() {
        return SupplierName9907;
    }

    public void setSupplierName9907(String supplierName9907) {
        SupplierName9907 = supplierName9907;
    }
    @Column(name="LOCATIONLIST998")
    public String getLocationList998() {
        return LocationList998;
    }

    public void setLocationList998(String locationList998) {
        LocationList998 = locationList998;
    }
    @Column(name="LOCATIONLIST997")
    public String getLocationList997() {
        return LocationList997;
    }

    public void setLocationList997(String locationList997) {
        LocationList997 = locationList997;
    }
    @Column(name="LOCATIONLIST999")
    public String getLocationList999() {
        return LocationList999;
    }

    public void setLocationList999(String locationList999) {
        LocationList999 = locationList999;
    }
    @Column(name="LOCATIONLIST992")
    public String getLocationList992() {
        return LocationList992;
    }

    public void setLocationList992(String locationList992) {
        LocationList992 = locationList992;
    }
    @Column(name="LOCATIONLIST995")
    public String getLocationList995() {
        return LocationList995;
    }

    public void setLocationList995(String locationList995) {
        LocationList995 = locationList995;
    }
    @Column(name="LOCATIONLIST991")
    public String getLocationList991() {
        return LocationList991;
    }

    public void setLocationList991(String locationList991) {
        LocationList991 = locationList991;
    }
    @Column(name="LOCATIONLIST996")
    public String getLocationList996() {
        return LocationList996;
    }

    public void setLocationList996(String locationList996) {
        LocationList996 = locationList996;
    }
    @Column(name="LOCATIONLIST9901")
    public String getLocationList9901() {
        return LocationList9901;
    }

    public void setLocationList9901(String locationList9901) {
        LocationList9901 = locationList9901;
    }
    @Column(name="LOCATIONLIST9902")
    public String getLocationList9902() {
        return LocationList9902;
    }

    public void setLocationList9902(String locationList9902) {
        LocationList9902 = locationList9902;
    }
    @Column(name="LOCATIONLIST9903")
    public String getLocationList9903() {
        return LocationList9903;
    }

    public void setLocationList9903(String locationList9903) {
        LocationList9903 = locationList9903;
    }
    @Column(name="LOCATIONLIST9906")
    public String getLocationList9906() {
        return LocationList9906;
    }

    public void setLocationList9906(String locationList9906) {
        LocationList9906 = locationList9906;
    }
    @Column(name="LOCATIONLIST9907")
    public String getLocationList9907() {
        return LocationList9907;
    }

    public void setLocationList9907(String locationList9907) {
        LocationList9907 = locationList9907;
    }
    @Column(name="FREESUPPLIERCODE998")
    public String getFreeSupplierCode998() {
        return FreeSupplierCode998;
    }

    public void setFreeSupplierCode998(String freeSupplierCode998) {
        FreeSupplierCode998 = freeSupplierCode998;
    }
    @Column(name="FREESUPPLIERNAME998")
    public String getFreeSupplierName998() {
        return FreeSupplierName998;
    }

    public void setFreeSupplierName998(String freeSupplierName998) {
        FreeSupplierName998 = freeSupplierName998;
    }
    @Column(name="FREESUPPLIERCODE997")
    public String getFreeSupplierCode997() {
        return FreeSupplierCode997;
    }

    public void setFreeSupplierCode997(String freeSupplierCode997) {
        FreeSupplierCode997 = freeSupplierCode997;
    }
    @Column(name="FREESUPPLIERNAME997")
    public String getFreeSupplierName997() {
        return FreeSupplierName997;
    }

    public void setFreeSupplierName997(String freeSupplierName997) {
        FreeSupplierName997 = freeSupplierName997;
    }
    @Column(name="FREESUPPLIERCODE999")
    public String getFreeSupplierCode999() {
        return FreeSupplierCode999;
    }

    public void setFreeSupplierCode999(String freeSupplierCode999) {
        FreeSupplierCode999 = freeSupplierCode999;
    }
    @Column(name="FREESUPPLIERNAME999")
    public String getFreeSupplierName999() {
        return FreeSupplierName999;
    }

    public void setFreeSupplierName999(String freeSupplierName999) {
        FreeSupplierName999 = freeSupplierName999;
    }
    @Column(name="FREESUPPLIERCODE992")
    public String getFreeSupplierCode992() {
        return FreeSupplierCode992;
    }

    public void setFreeSupplierCode992(String freeSupplierCode992) {
        FreeSupplierCode992 = freeSupplierCode992;
    }
    @Column(name="FREESUPPLIERNAME992")
    public String getFreeSupplierName992() {
        return FreeSupplierName992;
    }

    public void setFreeSupplierName992(String freeSupplierName992) {
        FreeSupplierName992 = freeSupplierName992;
    }
    @Column(name="FREESUPPLIERCODE995")
    public String getFreeSupplierCode995() {
        return FreeSupplierCode995;
    }

    public void setFreeSupplierCode995(String freeSupplierCode995) {
        FreeSupplierCode995 = freeSupplierCode995;
    }
    @Column(name="FREESUPPLIERNAME995")
    public String getFreeSupplierName995() {
        return FreeSupplierName995;
    }

    public void setFreeSupplierName995(String freeSupplierName995) {
        FreeSupplierName995 = freeSupplierName995;
    }
    @Column(name="FREESUPPLIERCODE991")
    public String getFreeSupplierCode991() {
        return FreeSupplierCode991;
    }

    public void setFreeSupplierCode991(String freeSupplierCode991) {
        FreeSupplierCode991 = freeSupplierCode991;
    }
    @Column(name="FREESUPPLIERNAME991")
    public String getFreeSupplierName991() {
        return FreeSupplierName991;
    }

    public void setFreeSupplierName991(String freeSupplierName991) {
        FreeSupplierName991 = freeSupplierName991;
    }
    @Column(name="FREESUPPLIERCODE996")
    public String getFreeSupplierCode996() {
        return FreeSupplierCode996;
    }

    public void setFreeSupplierCode996(String freeSupplierCode996) {
        FreeSupplierCode996 = freeSupplierCode996;
    }
    @Column(name="FREESUPPLIERNAME996")
    public String getFreeSupplierName996() {
        return FreeSupplierName996;
    }

    public void setFreeSupplierName996(String freeSupplierName996) {
        FreeSupplierName996 = freeSupplierName996;
    }
    @Column(name="FREESUPPLIERCODE9901")
    public String getFreeSupplierCode9901() {
        return FreeSupplierCode9901;
    }

    public void setFreeSupplierCode9901(String freeSupplierCode9901) {
        FreeSupplierCode9901 = freeSupplierCode9901;
    }
    @Column(name="FREESUPPLIERNAME9901")
    public String getFreeSupplierName9901() {
        return FreeSupplierName9901;
    }

    public void setFreeSupplierName9901(String freeSupplierName9901) {
        FreeSupplierName9901 = freeSupplierName9901;
    }
    @Column(name="FREESUPPLIERCODE9902")
    public String getFreeSupplierCode9902() {
        return FreeSupplierCode9902;
    }

    public void setFreeSupplierCode9902(String freeSupplierCode9902) {
        FreeSupplierCode9902 = freeSupplierCode9902;
    }
    @Column(name="FREESUPPLIERNAME9902")
    public String getFreeSupplierName9902() {
        return FreeSupplierName9902;
    }

    public void setFreeSupplierName9902(String freeSupplierName9902) {
        FreeSupplierName9902 = freeSupplierName9902;
    }
    @Column(name="FREESUPPLIERCODE9903")
    public String getFreeSupplierCode9903() {
        return FreeSupplierCode9903;
    }

    public void setFreeSupplierCode9903(String freeSupplierCode9903) {
        FreeSupplierCode9903 = freeSupplierCode9903;
    }
    @Column(name="FREESUPPLIERNAME9903")
    public String getFreeSupplierName9903() {
        return FreeSupplierName9903;
    }

    public void setFreeSupplierName9903(String freeSupplierName9903) {
        FreeSupplierName9903 = freeSupplierName9903;
    }
    @Column(name="FREESUPPLIERCODE9906")
    public String getFreeSupplierCode9906() {
        return FreeSupplierCode9906;
    }

    public void setFreeSupplierCode9906(String freeSupplierCode9906) {
        FreeSupplierCode9906 = freeSupplierCode9906;
    }
    @Column(name="FREESUPPLIERNAME9906")
    public String getFreeSupplierName9906() {
        return FreeSupplierName9906;
    }

    public void setFreeSupplierName9906(String freeSupplierName9906) {
        FreeSupplierName9906 = freeSupplierName9906;
    }
    @Column(name="FREESUPPLIERCODE9907")
    public String getFreeSupplierCode9907() {
        return FreeSupplierCode9907;
    }

    public void setFreeSupplierCode9907(String freeSupplierCode9907) {
        FreeSupplierCode9907 = freeSupplierCode9907;
    }
    @Column(name="FREESUPPLIERNAME9907")
    public String getFreeSupplierName9907() {
        return FreeSupplierName9907;
    }

    public void setFreeSupplierName9907(String freeSupplierName9907) {
        FreeSupplierName9907 = freeSupplierName9907;
    }
    @Column(name="REFSALESPERIOD")
    public String getRefSalesPeriod() {
        return RefSalesPeriod;
    }

    public void setRefSalesPeriod(String refSalesPeriod) {
        RefSalesPeriod = refSalesPeriod;
    }
    @Column(name="REFAREA")
    public String getRefArea() {
        return RefArea;
    }

    public void setRefArea(String refArea) {
        RefArea = refArea;
    }
    @Column(name="REFOSDTYPE")
    public String getRefOSDType() {
        return RefOSDType;
    }

    public void setRefOSDType(String refOSDType) {
        RefOSDType = refOSDType;
    }
    @Column(name="REFITEM")
    public String getRefItem() {
        return RefItem;
    }

    public void setRefItem(String refItem) {
        RefItem = refItem;
    }
    @Column(name="REFDESC")
    public String getRefDesc() {
        return RefDesc;
    }

    public void setRefDesc(String refDesc) {
        RefDesc = refDesc;
    }
    @Column(name="HISTORICSALESQTY")
    public String getHistoricSalesQty() {
        return HistoricSalesQty;
    }

    public void setHistoricSalesQty(String historicSalesQty) {
        HistoricSalesQty = historicSalesQty;
    }
    @Column(name="HISTORICSALESAMT")
    public String getHistoricSalesAMT() {
        return HistoricSalesAMT;
    }

    public void setHistoricSalesAMT(String historicSalesAMT) {
        HistoricSalesAMT = historicSalesAMT;
    }
    @Column(name="HISTORICAVGUNITPRICE")
    public String getHistoricAvgUnitPrice() {
        return HistoricAvgUnitPrice;
    }

    public void setHistoricAvgUnitPrice(String historicAvgUnitPrice) {
        HistoricAvgUnitPrice = historicAvgUnitPrice;
    }
    @Column(name="DISPLAYONDM")
    public String getDisplayonDM() {
        return DisplayonDM;
    }

    public void setDisplayonDM(String displayonDM) {
        DisplayonDM = displayonDM;
    }
    @Column(name="MATCHTHEME")
    public String getMatchtheme() {
        return Matchtheme;
    }

    public void setMatchtheme(String matchtheme) {
        Matchtheme = matchtheme;
    }
    @Column(name="CONSIGNMENT")
    public String getConsignment() {
        return Consignment;
    }

    public void setConsignment(String consignment) {
        Consignment = consignment;
    }
    @Column(name="NEWITEM")
    public String getNewItem() {
        return NewItem;
    }

    public void setNewItem(String newItem) {
        NewItem = newItem;
    }
    @Column(name="OWNBRAND")
    public String getOwnBrand() {
        return OwnBrand;
    }

    public void setOwnBrand(String ownBrand) {
        OwnBrand = ownBrand;
    }
    @Column(name="LIMITEDITEM")
    public String getLimitedItem() {
        return LimitedItem;
    }

    public void setLimitedItem(String limitedItem) {
        LimitedItem = limitedItem;
    }
    @Column(name="EXCLUSIVEITEM")
    public String getExclusiveItem() {
        return ExclusiveItem;
    }

    public void setExclusiveItem(String exclusiveItem) {
        ExclusiveItem = exclusiveItem;
    }
    @Column(name="ONSHELFPROMITEM")
    public String getOnShelfPromItem() {
        return OnShelfPromItem;
    }

    public void setOnShelfPromItem(String onShelfPromItem) {
        OnShelfPromItem = onShelfPromItem;
    }
    @Column(name="CLEARANCEITEM")
    public String getClearanceItem() {
        return ClearanceItem;
    }

    public void setClearanceItem(String clearanceItem) {
        ClearanceItem = clearanceItem;
    }
    @Column(name="FREEGOODS")
    public String getFreeGoods() {
        return FreeGoods;
    }

    public void setFreeGoods(String freeGoods) {
        FreeGoods = freeGoods;
    }
    @Column(name="PROMOCOMPENSATION")
    public String getPromocompensation() {
        return Promocompensation;
    }

    public void setPromocompensation(String promocompensation) {
        Promocompensation = promocompensation;
    }
    @Column(name="COMPENSATIONDETAIL")
    public String getCompensationdetail() {
        return Compensationdetail;
    }

    public void setCompensationdetail(String compensationdetail) {
        Compensationdetail = compensationdetail;
    }
    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Column(name="CREATION_DATE")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name="CREATED_BY")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name="LAST_UPDATED_BY")
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name="LAST_UPDATE_DATE")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name="LAST_UPDATE_LOGIN")
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name="PROM_NUMBER")
    public String getPromNumber() {
        return promNumber;
    }

    public void setPromNumber(String promNumber) {
        this.promNumber = promNumber;
    }
    @Column(name="PROM_PERIOD_FROM")
    public Date getPromPeriodFrom() {
        return promPeriodFrom;
    }

    public void setPromPeriodFrom(Date promPeriodFrom) {
        this.promPeriodFrom = promPeriodFrom;
    }
    @Column(name="PROM_PERIOD_TO")
    public Date getPromPeriodTo() {
        return promPeriodTo;
    }

    public void setPromPeriodTo(Date promPeriodTo) {
        this.promPeriodTo = promPeriodTo;
    }
}
