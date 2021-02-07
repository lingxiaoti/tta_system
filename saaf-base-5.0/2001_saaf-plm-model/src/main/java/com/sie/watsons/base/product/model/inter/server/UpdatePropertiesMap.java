package com.sie.watsons.base.product.model.inter.server;

import java.util.HashMap;
import java.util.Map;

public class UpdatePropertiesMap {
	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("countUnit", "16|FF");
		map.put("place", "17|FF");
		map.put("originCountry", "18|FF");
		map.put("unit", "27|FF");
		map.put("warehouseGetDay", "35|FF");
		map.put("sxDays", "36|FF");
		map.put("warehousePostDay", "37|FF");
		map.put("productEname", "42");
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
		// 20200401补充map
		map.put("tier", "666|LV");// 测试数据
		map.put("warehouseResource", "64|LV");// 测试数据
		map.put("condition", "51|LV");// 测试数据
		map.put("powerOb", "48|LV");// 测试数据
		map.put("rangOb", "47|LV");// 测试数据
		return map;
	}
}
