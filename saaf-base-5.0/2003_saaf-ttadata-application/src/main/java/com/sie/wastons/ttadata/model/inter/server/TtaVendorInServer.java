package com.sie.wastons.ttadata.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.wastons.ttadata.model.entities.readyonly.TtaVendorInEntity_HI_RO;
import com.sie.wastons.view.ApiRequest;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2019/10/31.
 */
@Component
public class TtaVendorInServer {

  @Autowired
  BaseViewObject<TtaVendorInEntity_HI_RO> vendorDAO_HI_RO;


  public List<TtaVendorInEntity_HI_RO> findTtaVendorInInfo(ApiRequest<JSONObject> params) throws IllegalAccessException {
    StringBuffer sql = new StringBuffer(TtaVendorInEntity_HI_RO.find_TtaVendorIn_List);
    String supplierMessage = packageSupplierMessage(params.getParams().getString("supplierMessage"));
    sql.append(" and vendor.supplier_code in (" + supplierMessage + ")");
    List<TtaVendorInEntity_HI_RO> list = vendorDAO_HI_RO.findList(sql);
    return list;
  }

  public Pagination<TtaVendorInEntity_HI_RO> findTtaVendorInList(ApiRequest<JSONObject> params) throws IllegalAccessException {
    StringBuffer sql = new StringBuffer(TtaVendorInEntity_HI_RO.find_TtaVendorIn_List);
    JSONObject json = params.getParams();
    Map<String, Object> queryParamMap = new HashMap<String, Object>();
    if (json.containsKey("whether") && StringUtils.isNotBlank(json.getString("whether"))) {
      String supplierMessage = packageSupplierMessage(json.getString("whether"));
      sql.append(" and vendor.supplier_code not in (" + supplierMessage + ")");
    }
    if (json.containsKey("supplierNumber") && StringUtils.isNotBlank(json.getString("supplierNumber"))) {
      String supplierMessage = packageSupplierMessage(json.getString("supplierNumber"));
      sql.append(" and vendor.supplier_code not in (" + supplierMessage + ")");
    }

   /* if (json.containsKey("supplierName") && StringUtils.isNotBlank(json.getString("supplierName"))) {
      sql.append(" and vendor.supplier_name =:supplierName");
      queryParamMap.put("supplierName", json.getString("supplierName"));
    }*/

    // 供应商编号
    if (json.containsKey("vendorNbr") && StringUtils.isNotBlank(json.getString("vendorNbr"))) {
      sql.append(" and vendor.supplier_code =:code");
      queryParamMap.put("code", json.getString("vendorNbr"));
      //SaafToolUtils.parperParam(json, "vendor.supplier_code", "vendorNbr", sql, queryParamMap, "="); 此方法编码强制转数字引起sql异常
    }

    // 供应商名称模糊查询
    if (json.containsKey("vendorName") && StringUtils.isNotBlank(json.getString("vendorName"))) {
      SaafToolUtils.parperParam(json, "vendor.supplier_name", "vendorName", sql, queryParamMap, "like");
    }
    Pagination<TtaVendorInEntity_HI_RO> list = vendorDAO_HI_RO.findPagination(sql, "select count(1) from(" + sql + ")num", queryParamMap, params.getPageIndex(), params.getPageRows());
    return list;
  }

  public List<TtaVendorInEntity_HI_RO> findAutomaticTtaVendorIn() throws IllegalAccessException {
    List<TtaVendorInEntity_HI_RO> list = vendorDAO_HI_RO.findList("SELECT\n" +
      "\tSUBSTR (supplier_code, 0, 7) AS vendorNbr\n" +
      "FROM\n" +
      "\ttta_supplier\n" +
      "WHERE 1 = 1 GROUP BY SUBSTR (supplier_code, 0, 7)\n" +
      "HAVING COUNT (supplier_code) > 1 ");
    return list;
  }

  public List<TtaVendorInEntity_HI_RO> findAutomaticTtaVendorInInfo(String params) throws IllegalAccessException {
    JSONObject json = JSONObject.parseObject(params);
    List<TtaVendorInEntity_HI_RO> list = vendorDAO_HI_RO.findList("SELECT\n" +
      "\t\tvendor.supplier_code vendorNbr,\n" +
      "    vendor.supplier_name vendorName\n" +
      "\tFROM\n" +
      "\t\ttta_supplier vendor\n" +
      "\tWHERE\n" +
      "\t\tsupplier_code LIKE '" + json.get("vendorNbr") + "%'");
    return list;
  }

  public String packageSupplierMessage(String supplier) {
    String[] temp = supplier.split(",");
    StringBuffer supplierMessage = new StringBuffer();
    for (int i = 0; i < temp.length; i++) {
      supplierMessage.append("'" + temp[i] + "',");
    }
    return supplierMessage.substring(0, supplierMessage.length() - 1);
  }
}
