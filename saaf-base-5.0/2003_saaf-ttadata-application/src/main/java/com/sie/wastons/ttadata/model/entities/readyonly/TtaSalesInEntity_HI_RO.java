package com.sie.wastons.ttadata.model.entities.readyonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.wastons.sql.annotation.SqlBinder;
import lombok.Data;

import java.util.Date;

/**
 * TtaSalesInEntity_HI_RO Entity Object
 * Thu Oct 17 14:08:46 GMT+08:00 2019  Auto Generate
 */

@Data
public class TtaSalesInEntity_HI_RO {
  public static String findTtaSalesInInfo(String tableName) {
    String sql = "SELECT\n" +
      "\tSALES.CREATION_DATE creationDate,\n" +
      "\tSALES.ACT_TRAN_DATE as actTranDate,\n" +
      "\tSALES.VENDOR_NBR as vendorNbr,\n" +
      "\tSALES.STORE as STORE,\n" +
      "\tSALES.ITEM as item,\n" +
      "\tSALES.SALES_QTY salesQty,\n" +
      "\tSALES.SALES_AMT salesAmt\n" +
      "FROM\n" +
      "\tTTA_SALES_IN_" + tableName + " SALES where 1=1";
    return sql;
  }

  @SqlBinder(sqlColumn = "STORE",opreation = SqlBinder.OPR.IN)
  private String vsCode;

  @SqlBinder(sqlColumn = "ITEM",opreation = SqlBinder.OPR.IN)
  private String rmsCode;

  @SqlBinder(sqlColumn = "VENDOR_NBR",opreation = SqlBinder.OPR.IN)
  private String supplierNumber;


  // 销售日期
  @SqlBinder(sqlColumn = "ACT_TRAN_DATE")
  private Integer actTranDate;

  // 供应商编号
  @SqlBinder(sqlColumn = "VENDOR_NBR")
  private java.math.BigDecimal vendorNbr;

  // 门店编号
  @SqlBinder(sqlColumn = "STORE")
  private String store;

  // 货品编号
  @SqlBinder(sqlColumn = "ITEM")
  private String item;

  // 销售数量
  private java.math.BigDecimal salesQty;

  // 销售金额(含税)
  private java.math.BigDecimal salesAmt;

  // private Integer actTranDate;

  private java.math.BigDecimal tranDate;
  private java.math.BigDecimal salesExcludeAmt;
  private String purchType;
  private String currency;
  private java.math.BigDecimal cost;
  private java.math.BigDecimal gp;
  private java.math.BigDecimal versionNum;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private java.math.BigDecimal createdBy;
  private java.math.BigDecimal lastUpdatedBy;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date lastUpdateDate;
  private java.math.BigDecimal lastUpdateLogin;
  private Integer operatorUserId;

  private Integer tradeYear;
  private Integer tradeMonth;

  private String week;
  private Integer weekStart;
  private Integer weekEnd;
}
