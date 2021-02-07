package com.sie.watsons.base.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * API接口路径配置文件
 */

@Component
@ConfigurationProperties(prefix = "sc", ignoreInvalidFields = true)
@PropertySource(value = {"classpath:com/sie/saaf/app/config/qcs_config.properties"}, ignoreResourceNotFound = true, encoding = "UTF-8")
public class ConfigModel {

    private String addItemUrl;
    private String updateUdaMethodUrl;
    private String updatePriceChangeMethodUrl;
    private String updateCostChangeMethodUrl;
    private String updateItemPropertyReturnsUrl;
    private String udaAttributeSynUrl;
    private String depClasCodeUrl;
    private String siteListingUrl;
    private String commodityPriceAreaUrl;
    private String locDesc;
    //RMS上传服务器
    private String host;
    private String user;
    private String possword;
    private String path;
    private String open;
    //药品上传服务器
    private String host2;
    private String user2;
    private String possword2;
    private String path2;
    private String open2;
    private String bpmUrl;  //bpm地址
    private String updateItem;
    private String updateUDA;
    private String updateSupp;
    private String plmServer;

    public String getPlmServer() {
        return plmServer;
    }

    public void setPlmServer(String plmServer) {
        this.plmServer = plmServer;
    }

    public String getBpmUrl() {
        return bpmUrl;
    }

    public void setBpmUrl(String bpmUrl) {
        this.bpmUrl = bpmUrl;
    }

    public String getHost() {return host;}

    public void setHost(String host) {this.host = host;}

    public String getUser() {return user;}

    public void setUser(String user) {this.user = user;}

    public String getPossword() {return possword;}

    public void setPossword(String possword) {this.possword = possword;}

    public String getPath() {return path;}

    public void setPath(String path) {this.path = path;}

    public String getOpen() {return open;}

    public void setOpen(String open) {this.open = open;}

    public String getHost2() {return host2;}

    public void setHost2(String host2) {this.host2 = host2;}

    public String getUser2() {return user2;}

    public void setUser2(String user2) {this.user2 = user2;}

    public String getPossword2() {return possword2;}

    public void setPossword2(String possword2) {this.possword2 = possword2;}

    public String getPath2() {return path2;}

    public void setPath2(String path2) {this.path2 = path2;}

    public String getOpen2() {return open2;}

    public void setOpen2(String open2) {this.open2 = open2;}

    public String getCommodityPriceAreaUrl() {
        return commodityPriceAreaUrl;
    }

    public void setCommodityPriceAreaUrl(String commodityPriceAreaUrl) {
        this.commodityPriceAreaUrl = commodityPriceAreaUrl;
    }

    public String getSiteListingUrl() {
        return siteListingUrl;
    }

    public void setSiteListingUrl(String siteListingUrl) {
        this.siteListingUrl = siteListingUrl;
    }

    public String getDepClasCodeUrl() {
        return depClasCodeUrl;
    }

    public void setDepClasCodeUrl(String depClasCodeUrl) {
        this.depClasCodeUrl = depClasCodeUrl;
    }

    public String getUdaAttributeSynUrl() {
        return udaAttributeSynUrl;
    }

    public void setUdaAttributeSynUrl(String udaAttributeSynUrl) {
        this.udaAttributeSynUrl = udaAttributeSynUrl;
    }

    public String getUpdateItemPropertyReturnsUrl() {
        return updateItemPropertyReturnsUrl;
    }

    public void setUpdateItemPropertyReturnsUrl(
            String updateItemPropertyReturnsUrl) {
        this.updateItemPropertyReturnsUrl = updateItemPropertyReturnsUrl;
    }

    public String getUpdateCostChangeMethodUrl() {
        return updateCostChangeMethodUrl;
    }

    public void setUpdateCostChangeMethodUrl(String updateCostChangeMethodUrl) {
        this.updateCostChangeMethodUrl = updateCostChangeMethodUrl;
    }

    public String getUpdatePriceChangeMethodUrl() {
        return updatePriceChangeMethodUrl;
    }

    public void setUpdatePriceChangeMethodUrl(String updatePriceChangeMethodUrl) {
        this.updatePriceChangeMethodUrl = updatePriceChangeMethodUrl;
    }

    public String getUpdateUdaMethodUrl() {
        return updateUdaMethodUrl;
    }

    public void setUpdateUdaMethodUrl(String updateUdaMethodUrl) {
        this.updateUdaMethodUrl = updateUdaMethodUrl;
    }

    public String getAddItemUrl() {
        return addItemUrl;
    }

    public void setAddItemUrl(String addItemUrl) {
        this.addItemUrl = addItemUrl;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public String getUpdateItem() {
        return updateItem;
    }

    public void setUpdateItem(String updateItem) {
        this.updateItem = updateItem;
    }

    public String getUpdateUDA() {
        return updateUDA;
    }

    public void setUpdateUDA(String updateUDA) {
        this.updateUDA = updateUDA;
    }

    public String getUpdateSupp() {
        return updateSupp;
    }

    public void setUpdateSupp(String updateSupp) {
        this.updateSupp = updateSupp;
    }
}
