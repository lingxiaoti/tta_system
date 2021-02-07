package com.sie.watsons.base.report.model.entities.readonly;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hmb
 * @date 2019/11/13 10:53
 */
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
public class TtaFreeGoodsEntityModel extends BaseRowModel implements Serializable {
    @ExcelProperty(value = "主供应商编号", index = 0)
    private String vendorNbr;
    @ExcelProperty(value = "主供应商名称", index = 1)
    private String vendorName;
    @ExcelProperty(value = "主供应商品牌", index = 2)
    private String brand;
    @ExcelProperty(value = "免费货品_条款", index = 3)
    private String freeTerms;
    @ExcelProperty(value = "本年度实际折扣前采购额（含税）", index = 4)
    private BigDecimal netPurchase;
    @ExcelProperty(value = "本年度预估全年折扣前采购额（含税）", index = 5)
    private BigDecimal futerPurchase;
    @ExcelProperty(value = "Group", index = 6)
    private String groupDesc;
    @ExcelProperty(value = "本年度应收免费货金额-合同免费货（不含税）", index = 7)
    private BigDecimal totalContractAmt;
    @ExcelProperty(value = "本年度实收免费货金额-促销免费货（不含税）", index = 8)
    private BigDecimal totalPromotionAmt;
    @ExcelProperty(value = "本年度实收免费货金额-试用装免费货（不含税）", index = 9)
    private BigDecimal totalSackAmt;
    @ExcelProperty(value = "本年度其他方式收取金额-货款调整金额（不含税）", index = 10)
    private BigDecimal borrowAdjAmt;
    @ExcelProperty(value = "本年度其他方式收取金额-ABOI实收金额（不含税）", index = 11)
    private BigDecimal aboiRecevieAmt;
    @ExcelProperty(value = "本年度其他方式收取金额-CA实收金额（不含税）", index = 12)
    private BigDecimal caRecevieAmt;
    @ExcelProperty(value = "差异", index = 13)
    private BigDecimal differAmt;
    @ExcelProperty(value = "采购行动", index = 14)
    private String purchaseAct;
    @ExcelProperty(value = "转交人", index = 15)
    private String changePersonName;

    public TtaFreeGoodsEntityModel() {
    }

    public String getVendorNbr() {
        return vendorNbr;
    }

    public void setVendorNbr(String vendorNbr) {
        this.vendorNbr = vendorNbr;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFreeTerms() {
        return freeTerms;
    }

    public void setFreeTerms(String freeTerms) {
        this.freeTerms = freeTerms;
    }

    public BigDecimal getNetPurchase() {
        return netPurchase;
    }

    public void setNetPurchase(BigDecimal netPurchase) {
        this.netPurchase = netPurchase;
    }

    public BigDecimal getFuterPurchase() {
        return futerPurchase;
    }

    public void setFuterPurchase(BigDecimal futerPurchase) {
        this.futerPurchase = futerPurchase;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public BigDecimal getTotalContractAmt() {
        return totalContractAmt;
    }

    public void setTotalContractAmt(BigDecimal totalContractAmt) {
        this.totalContractAmt = totalContractAmt;
    }

    public BigDecimal getTotalPromotionAmt() {
        return totalPromotionAmt;
    }

    public void setTotalPromotionAmt(BigDecimal totalPromotionAmt) {
        this.totalPromotionAmt = totalPromotionAmt;
    }

    public BigDecimal getTotalSackAmt() {
        return totalSackAmt;
    }

    public void setTotalSackAmt(BigDecimal totalSackAmt) {
        this.totalSackAmt = totalSackAmt;
    }

    public BigDecimal getBorrowAdjAmt() {
        return borrowAdjAmt;
    }

    public void setBorrowAdjAmt(BigDecimal borrowAdjAmt) {
        this.borrowAdjAmt = borrowAdjAmt;
    }

    public BigDecimal getAboiRecevieAmt() {
        return aboiRecevieAmt;
    }

    public void setAboiRecevieAmt(BigDecimal aboiRecevieAmt) {
        this.aboiRecevieAmt = aboiRecevieAmt;
    }

    public BigDecimal getCaRecevieAmt() {
        return caRecevieAmt;
    }

    public void setCaRecevieAmt(BigDecimal caRecevieAmt) {
        this.caRecevieAmt = caRecevieAmt;
    }

    public BigDecimal getDifferAmt() {
        return differAmt;
    }

    public void setDifferAmt(BigDecimal differAmt) {
        this.differAmt = differAmt;
    }

    public String getPurchaseAct() {
        return purchaseAct;
    }

    public void setPurchaseAct(String purchaseAct) {
        this.purchaseAct = purchaseAct;
    }

    public String getChangePersonName() {
        return changePersonName;
    }

    public void setChangePersonName(String changePersonName) {
        this.changePersonName = changePersonName;
    }

    @Override
    public String toString() {
        return "TtaFreeGoodsEntityModel{" +
                "vendorNbr='" + vendorNbr + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", brand='" + brand + '\'' +
                ", freeTerms='" + freeTerms + '\'' +
                ", netPurchase=" + netPurchase +
                ", futerPurchase=" + futerPurchase +
                ", groupDesc='" + groupDesc + '\'' +
                ", totalContractAmt=" + totalContractAmt +
                ", totalPromotionAmt=" + totalPromotionAmt +
                ", totalSackAmt=" + totalSackAmt +
                ", borrowAdjAmt=" + borrowAdjAmt +
                ", aboiRecevieAmt=" + aboiRecevieAmt +
                ", caRecevieAmt=" + caRecevieAmt +
                ", differAmt=" + differAmt +
                ", purchaseAct='" + purchaseAct + '\'' +
                ", changePersonName='" + changePersonName + '\'' +
                '}';
    }
}
