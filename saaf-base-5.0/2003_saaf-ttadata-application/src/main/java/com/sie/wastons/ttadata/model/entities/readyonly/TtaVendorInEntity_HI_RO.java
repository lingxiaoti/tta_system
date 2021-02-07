package com.sie.wastons.ttadata.model.entities.readyonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * TtaVendorInEntity_HI_RO Entity Object
 * Thu Sep 26 13:39:21 GMT+08:00 2019  Auto Generate
 */

public class TtaVendorInEntity_HI_RO {

  public static String find_TtaVendorIn_List = "SELECT " +
    "vendor.supplier_code vendorNbr," +
    "vendor.supplier_name vendorName " +
    "FROM tta_supplier vendor where 1=1";

  private String vendorNbr;
  private String vendorName;
  private String status;
  private int versionNum;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date creationDate;
  private int createdBy;
  private int lastUpdatedBy;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date lastUpdateDate;
  private int lastUpdateLogin;
  private Integer operatorUserId;

  public void setVendorNbr(String vendorNbr) {
    this.vendorNbr = vendorNbr;
  }


  public String getVendorNbr() {
    return vendorNbr;
  }

  public void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }


  public String getVendorName() {
    return vendorName;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public String getStatus() {
    return status;
  }

  public void setVersionNum(int versionNum) {
    this.versionNum = versionNum;
  }


  public int getVersionNum() {
    return versionNum;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }


  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }


  public int getCreatedBy() {
    return createdBy;
  }

  public void setLastUpdatedBy(int lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }


  public int getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdateDate(Date lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }


  public Date getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateLogin(int lastUpdateLogin) {
    this.lastUpdateLogin = lastUpdateLogin;
  }


  public int getLastUpdateLogin() {
    return lastUpdateLogin;
  }

  public void setOperatorUserId(Integer operatorUserId) {
    this.operatorUserId = operatorUserId;
  }


  public Integer getOperatorUserId() {
    return operatorUserId;
  }
}
