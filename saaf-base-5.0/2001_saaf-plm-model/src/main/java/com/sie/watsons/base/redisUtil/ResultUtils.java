package com.sie.watsons.base.redisUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;

public final class ResultUtils {

	private static String USER_TYPE = "userType";

	// 查询类型,传入json,传入字段List,传出字段List,对应快码List
	public static JSONArray getLookUpValues(JSONArray jsonParams,
			List<String> incomingParam, List<String> efferentParam,
			List<String> typeParam) {
		List<JSONObject> LookUpValuesList = new ArrayList<>();
		JSONArray returnJson = new JSONArray();
		// LookUpValuesList = getReturnJson(incomingParam, efferentParam,
		// typeParam);
		try {
			for (Object jsonParam : jsonParams) {
				JSONObject jsonObj = JSONObject.parseObject(jsonParam
						.toString());
				for (int i = 0; i < incomingParam.size(); i++) {
					jsonObj.put(efferentParam.get(i), LookUpValuesList.get(i)
							.get(jsonObj.getString(incomingParam.get(i))));
				}
				// 查询用户信息
				if (!org.springframework.util.ObjectUtils.isEmpty(jsonObj
						.getInteger("createdBy"))) {
					jsonObj = getUserInfoForCreatedBy(jsonObj);
				}
				if (!org.springframework.util.ObjectUtils.isEmpty(jsonObj
						.getInteger("lastUpdatedBy"))) {
					jsonObj = getUserInfoForLastUpdatedBy(jsonObj);
				}
				returnJson.add(jsonObj);
			}
			return returnJson;
		} catch (Exception e) {
			return jsonParams;
		}
	}

	public static void getLookUpValue(String typeParam)
			throws ClassNotFoundException, NoSuchFieldException,
			IllegalAccessException {
		List<String> paramList = new ArrayList<>();
		paramList.add(typeParam);
		List<JSONObject> lookUpValuesList = getReturnJson(paramList);
		if (lookUpValuesList != null && lookUpValuesList.size() > 0) {
			JSONObject obj = lookUpValuesList.get(0);
			Class<?> c = Class
					.forName("com.sie.watsons.base.redisUtil.ResultConstant");
			Field field = c.getField(typeParam);
			field.set(null, obj);
		}
	}

	public static void getCreator(Integer userId) {
		JSONObject param = new JSONObject();
		param.put("createdBy", userId);
		param = getUserInfoForCreatedBy(param);
		ResultConstant.userMap.put(userId, param);
	}

	public static JSONObject getLookUpValues(JSONObject jsonParam,
			List<String> incomingParam, List<String> efferentParam,
			List<String> typeParam) {
		// lookUpValuesList = null;
		// List<JSONObject> lookUpValuesList = getReturnJson(incomingParam,
		// efferentParam, typeParam);
		// JSONObject obj = lookUpValuesList.get(0);
		// ResultConstant.PLM_SUP_WAREHOUSE = obj;
		// Map<String, String> map = new HashMap<>();

		JSONObject returnJson;
		returnJson = jsonParam;
		try {
			for (int i = 0; i < incomingParam.size(); i++) {
				// returnJson.put(efferentParam.get(i),
				// lookUpValuesList.get(i).get(returnJson.getString(incomingParam.get(i))));
			}
			// 查询用户信息
			if (!org.springframework.util.ObjectUtils.isEmpty(returnJson
					.getInteger("createdBy"))) {
				returnJson = getUserInfoForCreatedBy(returnJson);
			}
			if (!org.springframework.util.ObjectUtils.isEmpty(returnJson
					.getInteger("lastUpdatedBy"))) {
				returnJson = getUserInfoForLastUpdatedBy(returnJson);
			}
			return returnJson;
		} catch (Exception e) {
			return jsonParam;
		}
	}

	public static List<JSONObject> getReturnJson(List<String> typeParam) {
		List<JSONObject> LookUpValuesList = new ArrayList<>();
		for (int i = 0; i < typeParam.size(); i++) {
			JSONObject b = new JSONObject();
			b.put("lookupType", typeParam.get(i));
			JSONObject raesultJSON = getServiceResult(
					"http://1002-saaf-api-gateway/api/ttadataServer"
							+ "/ttadata/ttaBaseDataService/v2/getLookUpValuesMapPLM",
					b);

			String url = "http://1002-saaf-api-gateway/api/plmServer/supService/findList";
			LookUpValuesList.add(raesultJSON);
		}
		return LookUpValuesList;
	}

	// public static List<JSONObject> getReturnJson(List<String> typeParam) {
	// List<JSONObject> LookUpValuesList = new ArrayList<>();
	// for (int i = 0; i < typeParam.size(); i++) {
	// JSONObject b = new JSONObject();
	// b.put("lookupType", typeParam.get(i));
	// JSONObject raesultJSON = getServiceResult(
	// "http://1002-saaf-api-gateway/api/ttadataServer"
	// + "/ttadata/ttaBaseDataService/v2/getLookUpValuesMapPLM",
	// b);
	//
	// String url =
	// "http://1002-saaf-api-gateway/api/plmServer/supService/findList";
	// LookUpValuesList.add(raesultJSON);
	// }
	// return LookUpValuesList;
	// }

	public static JSONObject getActivitiesHistoric(String code) {
		JSONObject b = new JSONObject();
		b.put("code", code);
		JSONObject raesultJSON = getServiceResult(
				"http://1002-saaf-api-gateway/api/ttadataServer"
						+ "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric",
				b);
		return raesultJSON;
	}

	public static JSONObject getUserInfoForLastUpdatedBy(JSONObject jsonObj) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("userId", jsonObj.getInteger("lastUpdatedBy"));
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/ttadataServer"
						+ "/ttadata/ttaBaseDataService/v2/getUserInfo",
				paramsJson);
		Assert.notNull(resultJson,
				"根据最后更新人lastUpdatedBy:" + jsonObj.getInteger("lastUpdatedBy")
						+ "查询数据为空");
		jsonObj.put("lastUpdatedName", resultJson.getString("userName"));
		jsonObj.put("email", resultJson.getString("email"));
		return jsonObj;
	}

	public static JSONObject getUserInfoForCreatedBy(JSONObject jsonObj) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("userId", jsonObj.getInteger("createdBy"));
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/ttadataServer"
						+ "/ttadata/ttaBaseDataService/v2/getUserInfo",
				paramsJson);
		Assert.notNull(resultJson,
				"根据创建人userId:" + jsonObj.getInteger("createdBy") + "查询数据为空");
		jsonObj.put("userName", resultJson.getString("userName"));
		jsonObj.put("createdByName", resultJson.getString("userFullName"));
		jsonObj.put("userFullName", resultJson.getString("userFullName"));
		jsonObj.put("email", resultJson.getString("email"));
		jsonObj.put("phoneNumber", resultJson.getString("phoneNumber"));
		jsonObj.put("projectLeaderName", resultJson.getString("userFullName"));
		jsonObj.put("userType", resultJson.getString("userType"));
		return jsonObj;
	}

	/**
	 * 根据用户类型获取用户信息
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static JSONObject getUserInfoByUserType(JSONObject jsonObject) {
		if (SaafToolUtils.isNullOrEmpty(jsonObject.getString(USER_TYPE))) {
			throw new IllegalArgumentException("用户类型信息未获得！");
		}
		JSONObject transferParams = new JSONObject();
		transferParams.put(USER_TYPE, jsonObject.getString(USER_TYPE));
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/ttadataServer"
						+ "/ttadata/ttaBaseDataService/v2/getUserInfoByType",
				transferParams);
		return resultJson;
	}

	public static JSONObject getEquotationSupplierInfo(JSONObject jsonObject) {
		JSONObject transferParams = new JSONObject();
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/equotationServer/equPosSupplierInfoService/findSupplierInfo",
				transferParams);
		return resultJson;
	}

	public static JSONObject getTtaSupplierInfo(JSONObject jsonObject) {

		jsonObject.put("params", jsonObject.toString());
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/baseServer/baseRequestUrlService/findPlmSupplier",
				jsonObject);
		return resultJson;
	}

	/**
	 * 查询Equotation供应商信息
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static Map<Object, JSONArray> queryEquotationSupplierInfo(
			JSONObject queryParams) {
		Map<Object, JSONArray> returnMap = new HashMap<>(16);
		returnMap = remoteCallDataEncapsulation(
				queryParams,
				returnMap,
				"supplierName",
				"http://1002-saaf-api-gateway/api/equotationServer/equPosSupplierInfoService/findSupplierInfo");
		return returnMap;
	}

	/**
	 * 远程调用查询功能数据封装
	 * 
	 * @param queryParams
	 * @param returnMap
	 * @param key
	 * @param url
	 * @return
	 */
	public static Map<Object, JSONArray> remoteCallDataEncapsulation(
			JSONObject queryParams, Map<Object, JSONArray> returnMap,
			String key, String url) {
		if (SaafToolUtils.isNullOrEmpty(url)) {
			throw new IllegalArgumentException("未获取调用链接！");
		}
		if (SaafToolUtils.isNullOrEmpty(queryParams.getString("certificate"))) {
			throw new IllegalArgumentException("未获取信用证书！");
		}
		if (SaafToolUtils.isNullOrEmpty(key)) {
			throw new IllegalArgumentException("未获取Map的Key值");
		}

		JSONObject resultJson = SaafToolUtils.preaseServiceResultJSON(
				url,
				new JSONObject()
						.fluentPut("params", queryParams)
						.fluentPut("certificate",
								queryParams.getString("certificate"))
						.fluentPut("pageIndex", 1)
						.fluentPut("pageRows", Integer.MAX_VALUE));
		if (!"S".equals(resultJson.getString("status"))) {
			throw new IllegalArgumentException("查询失败！");
		} else if (resultJson.getJSONArray("data").size() == 0) {

		} else {
			JSONArray dataArray = resultJson.getJSONArray("data");
			for (int j = 0; j < dataArray.size(); j++) {
				JSONObject obj = dataArray.getJSONObject(j);
				if (returnMap.containsKey(obj.get(key))) {
					JSONArray array = returnMap.get(obj.getInteger(key));
					array.add(obj);
					returnMap.put(obj.get(key), array);
				} else {
					JSONArray array = new JSONArray();
					array.add(obj);
					returnMap.put(obj.get(key), array);
				}
			}
		}
		return returnMap;
	}

	public static JSONObject getServiceResult(String requestURL,
			JSONObject queryParamJSON) {
		Assert.isTrue(StringUtils.isNotBlank(requestURL),
				"requesturl is required");
		RestTemplate restTemplate = (RestTemplate) SpringBeanUtil
				.getBean("restTemplate");
		if (restTemplate == null) {
			throw new ExceptionInInitializerError("初始化异常");
		}

		MultiValueMap header = new LinkedMultiValueMap();
		Long timestamp = System.currentTimeMillis();
		header.add("timestamp", timestamp + "");
		header.add("pdasign", SaafToolUtils.buildSign(timestamp));
		JSONObject resultJSON = restSpringCloudPost(requestURL, queryParamJSON,
				header, restTemplate);
		if (resultJSON.getIntValue("statusCode") == 200) {
			if (JSON.parse(resultJSON.getString("data")) instanceof JSONArray) {
				return resultJSON;
			}
			JSONObject data = resultJSON.getJSONObject("data");
			return data;
		}
		return null;
	}

	public static JSONObject restSpringCloudPost(String requestURL,
			JSONObject postParam, MultiValueMap<String, String> headerParams,
			RestTemplate restTemplate) {
		JSONObject resultJSONObject = new JSONObject();

		if (!headerParams.containsKey("Accept")) {
			headerParams.add("Accept", "application/json");
		}

		if (!headerParams.containsKey("Accept-Charset")) {
			headerParams.add("Accept-Charset", "utf-8");
		}

		if (!headerParams.containsKey("Content-Type")) {
			headerParams.add("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
		}

		if (!headerParams.containsKey("Content-Encoding")) {
			headerParams.add("Content-Encoding", "UTF-8");
		}

		StringBuilder requestBodey = new StringBuilder();
		if (postParam != null && postParam.size() > 0) {
			Set<Map.Entry<String, Object>> entrySet = postParam.entrySet();
			Iterator iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry) iterator.next();
				requestBodey.append(entry.getKey()).append("=");
				if (entry.getValue() != null) {
					try {
						requestBodey.append(URLEncoder.encode(entry.getValue()
								.toString(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
					}
				}
				if (iterator.hasNext())
					requestBodey.append("&");
			}
		}

		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		HttpClient httpClient = httpClientBuilder.build();
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient);

		if (postParam != null
				&& StringUtils.isNotBlank(postParam.toJSONString())) {
			try {
				restTemplate.setRequestFactory(clientHttpRequestFactory);
				HttpEntity request = new HttpEntity(requestBodey.toString(),
						headerParams);
				ResponseEntity responseEntity = restTemplate.postForEntity(
						requestURL, request, String.class, new Object[0]);
				HttpStatus strVlaue = responseEntity.getStatusCode();
				if (strVlaue.value() == 200) {
					resultJSONObject.put("statusCode", strVlaue.value());
				}

				String body = (String) responseEntity.getBody();
				resultJSONObject.put("data", body);
			} catch (Exception var13) {
				resultJSONObject.put("msg", var13.getMessage());
			}
		}
		return resultJSONObject;
	}

	/**
	 * 根据用户id获取用户上级信息
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static JSONObject getUserInfoByUserId(JSONObject jsonObject) {
		if (SaafToolUtils.isNullOrEmpty(jsonObject.getString("userId"))) {
			throw new IllegalArgumentException("用户id信息未获得！");
		}
		JSONObject transferParams = new JSONObject();
		transferParams.put("userId", jsonObject.getString("userId"));
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/ttadataServer"
						+ "/ttadata/ttaBaseDataService/v2/getUserParentByuserId",
				transferParams);
		return resultJson;
	}

	// userName
	public static JSONObject getUserInfoForByUserName(JSONObject jsonObj) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("userName", jsonObj.getString("userName"));
		JSONObject resultJson = getServiceResult(
				"http://1002-saaf-api-gateway/api/ttadataServer"
						+ "/ttadata/ttaBaseDataService/v2/getUserInfoByUserName",
				paramsJson);
		System.out.println(resultJson);
		return resultJson;
	}

	public static JSONObject doGet(String url, JSONObject json) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		JSONObject response = null;
		try {
			StringEntity s = new StringEntity(json.toJSONString(), "utf-8");
			s.setContentEncoding("UTF-8");
			// s.setContentType("text/html;charset=utf-8");//发送json数据需要设置contentType
			HttpResponse res = client.execute(get);
			if (res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				org.apache.http.HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity(), "utf-8");// 返回json格式：
				response = JSONObject.parseObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	public static JSONObject doPost(String url, JSONObject json) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			// String a =
			// "{\"data_set\": [{\"request_id\":100011, \"update_type\":\"ADD\",\"uda_type\":\"FF\",\"uda_id\":1,\"uda_value\":\"2\",\"item\":\"10000123\",\"update_datetime\":\"20191202120000\",\"last_update_id\":\"Tom\"},{\"request_id\":100012, \"update_type\":\"ADD\",\"uda_type\":\"FF\",\"uda_id\":9,\"uda_value\":5,\"item\":\"10000123\",\"update_datetime\":\"20191202130000\",\"last_update_id\":\"Jerry\"}]}";

			StringEntity s = new StringEntity(json.toJSONString(), "utf-8");
			// StringEntity s = new StringEntity(a, "utf-8");
			s.setContentEncoding("UTF-8");
			// s.setContentType("text/html;charset=utf-8");//发送json数据需要设置contentType
			post.setEntity(s);

			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				org.apache.http.HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity(), "utf-8");// 返回json格式：
				response = JSONObject.parseObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	public static Object doPost1(String url, JSONObject json) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		Object returnObject = null;
		try {
			// String a =
			// "{\"data_set\": [{\"request_id\":100011, \"update_type\":\"ADD\",\"uda_type\":\"FF\",\"uda_id\":1,\"uda_value\":\"2\",\"item\":\"10000123\",\"update_datetime\":\"20191202120000\",\"last_update_id\":\"Tom\"},{\"request_id\":100012, \"update_type\":\"ADD\",\"uda_type\":\"FF\",\"uda_id\":9,\"uda_value\":5,\"item\":\"10000123\",\"update_datetime\":\"20191202130000\",\"last_update_id\":\"Jerry\"}]}";

			StringEntity s = new StringEntity(json.toJSONString(), "utf-8");
			// StringEntity s = new StringEntity(a, "utf-8");
			s.setContentEncoding("UTF-8");
			// s.setContentType("text/html;charset=utf-8");//发送json数据需要设置contentType
			post.setEntity(s);

			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				org.apache.http.HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity(), "utf-8");// 返回json格式：
				// response = JSONObject.parseObject(result);
				return result;
			} else {
				return "url is fialed to request ! ";
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String args[]) {

	}

}
