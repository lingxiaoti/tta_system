package com.sie.wastons.ttadata.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.wastons.sql.SqlTemplateUtil;
import com.sie.wastons.ttadata.model.entities.readyonly.TtaSalesInEntity_HI_RO;
import com.sie.wastons.view.ApiRequest;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by user on 2019/10/28.
 */
@Component
public class TtaSalesInDataServer {

  @Autowired
  BaseViewObject<TtaSalesInEntity_HI_RO> ttaSalesInDAO_HI_RO;


  public Pagination<TtaSalesInEntity_HI_RO> findTtaSalesInInfo(ApiRequest<JSONObject> params) throws Exception {
    JSONObject queryParamJSON = params.getParams();
    Pagination<TtaSalesInEntity_HI_RO> sales = new Pagination();
    SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
    String currentData = null;

    // 销售时间不可为空
    if (queryParamJSON.containsKey("actTranDate") && queryParamJSON.getDate("actTranDate") != null) {
      currentData = format1.format(queryParamJSON.getDate("actTranDate"));
      queryParamJSON.put("actTranDate", currentData);
    } else {
      return sales;
    }
    // 门店编号不可为空
    if (!queryParamJSON.containsKey("store") || StringUtils.isBlank(queryParamJSON.getString("store"))) {
      return sales;
    }
    // 货品编号不可为空
    if (!queryParamJSON.containsKey("item") || StringUtils.isBlank(queryParamJSON.getString("item"))) {
      return sales;
    }
    // 供应商编号不可为空
    if (!queryParamJSON.containsKey("vendorNbr") || StringUtils.isBlank(queryParamJSON.getString("vendorNbr"))) {
      return sales;
    }

    // 判断该时间是否存在于在财务日历
    List<TtaSalesInEntity_HI_RO> tradeCalendar = ttaSalesInDAO_HI_RO.findList("select " +
      "TRADE_YEAR tradeYear,TRADE_MONTH tradeMonth，WEEK_START weekStart,WEEK_END weekEnd " +
      "from tta_trade_calendar " +
      "where weekStart <=? and weekEnd >=?", currentData, currentData);
    if (tradeCalendar == null || tradeCalendar.isEmpty()) {
      return sales;
    }

    // 得出表名
    String dataTable = tradeCalendar.get(0).getTradeYear() + String.format("%02d", tradeCalendar.get(0).getTradeMonth());

    // 判断该表是否存在
    Integer count = ttaSalesInDAO_HI_RO.count("select count(*) from user_tables where table_name =upper('TTA_SALES_IN_" + dataTable + "')");
    if (Integer.valueOf(0).equals(count)) {
      return sales;
    }

    StringBuffer sql = new StringBuffer(TtaSalesInEntity_HI_RO.findTtaSalesInInfo(dataTable));
    sales = SqlTemplateUtil.findSqlPagination(ttaSalesInDAO_HI_RO, JSONObject.toJavaObject(queryParamJSON, TtaSalesInEntity_HI_RO.class), sql, params.getPageIndex(), params.getPageRows());
    return sales;

    //Map<String, Object> queryParamMap = new HashMap<String, java.lang.Object>();


//    if (queryParamJSON.containsKey("creationDate") && queryParamJSON.get("creationDate") != null) {
//      dataTable = dataTableFormat.format(queryParamJSON.getDate("creationDate"));
//      currentData = currentDataFormat.format(queryParamJSON.getDate("creationDate"));
//    } else {
//      dataTable = dataTableFormat.format(new Date());
//      currentData = currentDataFormat.format(new Date());
//    }
//    Integer count = ttaSalesInDAO_HI_RO.count("select count(*) from user_tables where table_name =upper('TTA_SALES_IN_" + dataTable + "')");
//    if (Integer.valueOf(0).equals(count)) {
//      List<TtaSalesInEntity_HI_RO> ttaSalesIn = new ArrayList<TtaSalesInEntity_HI_RO>();
//      sales.setData(ttaSalesIn);
//      return sales;
//    }
//
//    StringBuffer sql = new StringBuffer(TtaSalesInEntity_HI_RO.findTtaSalesInInfo(dataTable, currentData));
//    queryParamJSON.remove("creationDate");
//    // 关联供应商表
//    if (queryParamJSON.containsKey("supplierNumber") && queryParamJSON.get("supplierNumber") != null) {
//      sql.append(" and TO_CHAR(VENDOR_NBR) IN " + queryParamJSON.get("supplierNumber"));
//      queryParamJSON.remove("supplierNumber");
//    }
//    sales = SqlTemplateUtil.findSqlPagination(ttaSalesInDAO_HI_RO, JSONObject.toJavaObject(queryParamJSON, TtaSalesInEntity_HI_RO.class), sql, params.getPageIndex(), params.getPageRows());
//    return sales;
  }

  public List<TtaSalesInEntity_HI_RO> findTradeCalendar(String date) {
    String year = date.substring(0, date.lastIndexOf("-"));
    String month = date.substring(date.lastIndexOf("-") + 1, date.length());
    if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
      List<TtaSalesInEntity_HI_RO> tradeCalendar = ttaSalesInDAO_HI_RO.findList("select WEEK_START weekStart,WEEK_END weekEnd from tta_trade_calendar where TRADE_YEAR =? and TRADE_MONTH =?", year, month);
      if(tradeCalendar.isEmpty()){
        return new ArrayList<TtaSalesInEntity_HI_RO>();
      }
      for (TtaSalesInEntity_HI_RO sales : tradeCalendar) {
        sales.setWeek(sales.getWeekStart() + "-" + sales.getWeekEnd());
      }
      return tradeCalendar;
    }
    return new ArrayList<TtaSalesInEntity_HI_RO>();
  }
}
