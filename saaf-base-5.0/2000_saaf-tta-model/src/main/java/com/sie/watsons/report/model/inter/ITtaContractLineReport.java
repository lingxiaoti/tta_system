package com.sie.watsons.report.model.inter;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface ITtaContractLineReport {
	/**
	 * 查找贸易条款信息
	 * @param queryParamJSON
	 * @return
	 */
	JSONObject findTermsAgrement(JSONObject queryParamJSON) throws Exception;


	/**
	 * 查询店铺或者区域信息
	 */
	List<Map<String, Object>> findShopArea(String flag) throws Exception;


	/**
	 * 通过lookType和lookupCode查询数据字段，返回唯一结果。
	 */
	public String findDicUniqueResult(String lookupType, String lookupCode);

}
