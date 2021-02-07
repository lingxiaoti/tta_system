package com.sie.watsons.base.redisUtil;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ResultConstant {
	public static JSONObject PLM_SUP_ORDER_REASON;
	public static JSONObject PLM_SUP_ORDER_STATUS;
//	public static JSONObject PLM_PRODUCT_UPDATETYPE;
	public static JSONObject PLM_PRODUCT_UPDATESESON;
	public static JSONObject PLM_PRODUCT_UPDATEPRICE;
	public static JSONObject PLM_PRODUCT_UPD_STATUS;
    public static JSONObject PLM_SUP_STATUS_ALL;
    public static JSONObject PLM_PRODUCT_TRANS;
	public static JSONObject PLM_PRODUCT_SESION;
	public static JSONObject PLM_PRODUCT_RETURNPRO;
	public static JSONObject PLM_SUP_STATUS_WC;
	public static JSONObject PLM_SUP_EFFECTIVE_WAY;
	public static JSONObject PLM_NOTICE_EMAIL;
	public static JSONObject PLM_SUP_STATUS;
	public static JSONObject PLM_SUP_WAREHOUSE;
	public static JSONObject PLM_PRODUCT_PURCHASE;
	public static JSONObject PLM_PRODUCT_PLACETYPE;
	public static JSONObject PLM_PRODUCT_STATUS;
	public static Map<Integer, JSONObject> userMap = new HashMap<>();
	public static JSONObject PLM_OB_PRODUCT_BILL_STATUS;
	public static JSONObject PLM_DEVE_PRODUCT_STATUS;
	public static JSONObject PLM_PROJECT_PRODUCT_CATEGORY;
	public static JSONObject PLM_QA_DETAIL_STATUS;
	public static JSONObject PLM_PRODUCT_EXEP_BILL_STATUS;
	public static JSONObject PLM_EXCEPTION_SOURCE;
	public static JSONObject PLM_EXCEPTION_CATEGORY;
	public static JSONObject PLM_TREATMENT;
	public static JSONObject PLM_PROJECT_STATUS;
	public static JSONObject PLM_EXCEPTION_OCCURRENCE_STAGE;
	public static JSONObject PLM_SUPPLIER_TYPE;
	public static JSONObject PLM_SUPPLIER_QA_STATUS;
	public static JSONObject PLM_SUP_STOP_REASON;
	public static JSONObject PLM_SUP_STORE_TYPE;
	public static JSONObject PLM_SUP_ORDER_TYPE;
	public static JSONObject PLM_PRODUCT_ORTHERINFO;
	public static JSONObject PLM_PRODUT_ORDERSTATUS;
	// 线上渠道类型
	public static JSONObject PLM_PRODUCT_ONLINETYPE;
	/**
	 * 文件类型
	 */
	public static JSONObject PLM_PRODUCT_HEADQAFILETYE;
	public static JSONObject PLM_BRAND_QA_TYPE;


	public static Map<String, String> map = new HashMap<>();
	static {
//		map.put("tier", "2|FF"); // 测试数据
//		map.put("grossEarnings", "3|LV");// 测试数据
//		map.put("unit", "4|FF");// 测试数据
//		map.put("consultDate", "4|FF");// 测试数据
//		map.put("consultEnddate", "4|FF");// 测试数据

		map.put("countUnit", "16|FF");
		map.put("place", "17|FF");
		map.put("originCountry", "18|FF");
		map.put("standardOfUnit", "27|FF");
		map.put("warehouseGetDay", "35|FF");
		map.put("sxDays", "36|FF");
		map.put("warehousePostDay", "37|FF");
		map.put("productEname", "42|FF");
		map.put("validDays", "2|LV");
		map.put("specialLicence", "3|LV");
		map.put("productType", "4|LV");
		map.put("productResource", "5|LV");
		map.put("productCategeery", "6|LV");
		map.put("pricewarProduct", "7|LV");
		map.put("uniqueCommodities", "8|LV");
		map.put("specialtyProduct", "9|LV");
		map.put("productProperties", "10|LV");
		map.put("buyingLevel", "11|LV");
		map.put("dangerousProduct", "12|LV");
		map.put("posInfo", "13|LV");
		map.put("internationProduct", "15|LV");
		map.put("sesionProduct", "29|LV");
		map.put("productReturn", "41|LV");
		map.put("topProduct", "45|LV");
		map.put("companyDeletion", "53|LV");
		map.put("bluecapProduct", "61|LV");
		map.put("crossborderProduct", "62|LV");
		map.put("vcProduct", "63|LV");
		//20200401补充map
		map.put("tier", "666|LV");// 测试数据
		map.put("warehouseResource", "64|LV");// 测试数据
		map.put("condition", "51|LV");// 测试数据
		map.put("powerOb", "48|LV");// 测试数据
		map.put("rangOb", "47|LV");// 测试数据
		map.put("drugIns", "50|LV");// 测试数据
	}
}
