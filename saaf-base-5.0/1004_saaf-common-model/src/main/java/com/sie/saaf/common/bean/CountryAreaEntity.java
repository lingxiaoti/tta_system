package com.sie.saaf.common.bean;

/**
 * 国家省市区实体类
 */
public class CountryAreaEntity {
    // 国家
    String countryId;
    String countryName;
    String countryCode;
    // 省
    String provinceId;
    String provinceName;
    String provinceCode;
    // 市
    String cityId;
    String cityName;
    String cityCode;
    // 区
    String areaId;
    String areaName;
    String areaCode;

    public CountryAreaEntity() {

    }
    public CountryAreaEntity(String countryId, String countryName, String countryCode) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }
    public CountryAreaEntity(String countryId, String countryName, String countryCode, String provinceId, String provinceName, String provinceCode) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.provinceCode = provinceCode;
    }
    public CountryAreaEntity(String countryId, String countryName, String countryCode, String provinceId, String provinceName, String provinceCode, String cityId, String cityName, String cityCode) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.provinceCode = provinceCode;
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityCode = cityCode;
    }
    public CountryAreaEntity(String countryId, String countryName, String countryCode, String provinceId, String provinceName, String provinceCode, String cityId, String cityName, String cityCode, String areaId, String areaName, String areaCode) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.provinceCode = provinceCode;
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.areaId = areaId;
        this.areaName = areaName;
        this.areaCode = areaCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
