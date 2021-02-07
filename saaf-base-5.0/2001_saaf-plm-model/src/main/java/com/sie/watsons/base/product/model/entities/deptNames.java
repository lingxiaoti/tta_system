package com.sie.watsons.base.product.model.entities;

import java.util.HashMap;
import java.util.Map;

public class deptNames {
	// none ob
	public String getNoneobDeptByname(String columnname) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("originCountry", "NONOBQA");
		data.put("dangerousProduct", "NONOBQA");
		data.put("place", "NONOBQA"); // 地点信息变更
		data.put("sxDays", "NONOBQA");
		data.put("validDays", "NONOBQA");
		//data.put("productReturn", "OEM");
		//data.put("warehouseGetDay", "SUPPLY");
		//data.put("warehousePostDay", "SUPPLY");
		//data.put("price","OEM");
		if (data.containsKey(columnname)) {
			return data.get(columnname);
		} else {
			return "";
		}
	}
	//SX_DAYS
	// ob
	public String getObDeptByname(String columnname) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("originCountry", "OBQA");
		data.put("dangerousProduct", "OBQA");
		data.put("place", "OBQA,OEM"); // 地点信息变更
		data.put("sxDays", "OBQA");
		data.put("validDays", "OBQA");
		data.put("productReturn", "OEM");
		//data.put("warehouseGetDay", "SUPPLY");
		//data.put("warehousePostDay", "SUPPLY");
		data.put("price","OEM");
		if (data.containsKey(columnname)) {
			return data.get(columnname);
		} else {
			return "";
		}
	}

	// 初始化流程变量
	public Map<String, String> getParams() {
		Map<String, String> pali = new HashMap<String, String>();
		pali.put("OBQA", "0");
		pali.put("OEM", "0");
		pali.put("SUPPLY", "0");
		pali.put("NONOBQA", "0");
		pali.put("BIC", "0");
		pali.put("NEEDGM", "0");
		return pali;
	}

	//需要gm审批字段
	public String getGmColumn(String columnname) {
		//需要gm审批字段
		Map<String, String> data = new HashMap<String, String>();
		data.put("sesionProduct", "sesionProduct");
		data.put("productReturn", "productReturn");
		data.put("price", "price");
		data.put("unitPrice", "unitPrice");
		if (data.containsKey(columnname)) {
			return data.get(columnname);
		} else {
			return "";
		}
	}
}