package com.sie.watsons.base.productEco.model.entities.readonly;

import com.sie.watsons.base.productEco.model.entities.PlmProductHeadEcoEntity_HI;

import java.util.List;

public class PlmProductParamEcoEntity_HI_RO {

    //修改单头
    private PlmProductHeadEcoEntity_HI_RO headInfo;
    //修改单 图片
    private List<PlmProductPicfileTableEcoEntity_HI_RO> picfileTableEcos;
    //修改单售价
    private List<PlmProductPriceEcoEntity_HI_RO> priceEcos;
    //修改单QA文件
    private List<PlmProductQaFileEcoEntity_HI_RO> qaFileEcos;
    //修改单供应商信息
    private List<PlmProductSupplierInfoEcoEntity_HI_RO> supplierEcos;
    //修改单药品属性
    private PlmProductDrugEcoEntity_HI_RO drugEcos;
    //修改单器械属性
    private PlmProductMedicalinfoEcoEntity_HI_RO medicalinfoEcos;

    public PlmProductMedicalinfoEcoEntity_HI_RO getMedicalinfoEcos() {
        return medicalinfoEcos;
    }

    public void setMedicalinfoEcos(PlmProductMedicalinfoEcoEntity_HI_RO medicalinfoEcos) {
        this.medicalinfoEcos = medicalinfoEcos;
    }

    public PlmProductDrugEcoEntity_HI_RO getDrugEcos() {
        return drugEcos;
    }

    public void setDrugEcos(PlmProductDrugEcoEntity_HI_RO drugEcos) {
        this.drugEcos = drugEcos;
    }

    public List<PlmProductPicfileTableEcoEntity_HI_RO> getPicfileTableEcos() {
        return picfileTableEcos;
    }

    public void setPicfileTableEcos(List<PlmProductPicfileTableEcoEntity_HI_RO> picfileTableEcos) {
        this.picfileTableEcos = picfileTableEcos;
    }

    public List<PlmProductPriceEcoEntity_HI_RO> getPriceEcos() {
        return priceEcos;
    }

    public void setPriceEcos(List<PlmProductPriceEcoEntity_HI_RO> priceEcos) {
        this.priceEcos = priceEcos;
    }

    public List<PlmProductQaFileEcoEntity_HI_RO> getQaFileEcos() {
        return qaFileEcos;
    }

    public void setQaFileEcos(List<PlmProductQaFileEcoEntity_HI_RO> qaFileEcos) {
        this.qaFileEcos = qaFileEcos;
    }

    public List<PlmProductSupplierInfoEcoEntity_HI_RO> getSupplierEcos() {
        return supplierEcos;
    }

    public void setSupplierEcos(List<PlmProductSupplierInfoEcoEntity_HI_RO> supplierEcos) {
        this.supplierEcos = supplierEcos;
    }

    public PlmProductHeadEcoEntity_HI_RO getHeadInfo() {
        return headInfo;
    }

    public void setHeadInfo(PlmProductHeadEcoEntity_HI_RO headInfo) {
        this.headInfo = headInfo;
    }
}
