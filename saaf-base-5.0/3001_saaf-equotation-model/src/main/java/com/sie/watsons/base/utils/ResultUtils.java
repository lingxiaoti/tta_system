package com.sie.watsons.base.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


public final class ResultUtils {

    //查询类型,传入json,传入字段List,传出字段List,对应快码List
    public static JSONArray getLookUpValues(JSONArray jsonParams, List<String> incomingParam, List<String> efferentParam, List<String> typeParam) {
        List<JSONObject> LookUpValuesList = new ArrayList<>();
        JSONArray returnJson = new JSONArray();
        LookUpValuesList = getReturnJson(incomingParam, efferentParam, typeParam);
        try {
            for (Object jsonParam : jsonParams) {
                JSONObject jsonObj = JSONObject.parseObject(jsonParam.toString());
                for (int i = 0; i < incomingParam.size(); i++) {
                    jsonObj.put(efferentParam.get(i), LookUpValuesList.get(i).get(jsonObj.getString(incomingParam.get(i))));
                }
                // 查询用户信息
                try {
                    if (!org.springframework.util.ObjectUtils.isEmpty(jsonObj.getInteger("createdBy"))) {
                        jsonObj = getUserInfoForCreatedBy(jsonObj);
                    }
                } catch (Exception e) {
                }
//                try {
//                    if (!org.springframework.util.ObjectUtils.isEmpty(jsonObj.getInteger("lastUpdatedBy"))) {
//                        jsonObj = getUserInfoForLastUpdatedBy(jsonObj);
//                    }
//                } catch (Exception e) {
//                }
                returnJson.add(jsonObj);
            }
            return returnJson;
        } catch (Exception e) {
            return jsonParams;
        }
    }

    public static JSONArray getLookUpValues2(JSONArray jsonParams, List<String> incomingParam, List<String> efferentParam, List<String> typeParam) {
        List<JSONObject> LookUpValuesList = new ArrayList<>();
        JSONArray returnJson = new JSONArray();
        LookUpValuesList = getReturnJson(incomingParam, efferentParam, typeParam);
        try {
            for (Object jsonParam : jsonParams) {
                JSONObject jsonObj = JSONObject.parseObject(jsonParam.toString());
                for (int i = 0; i < incomingParam.size(); i++) {
                    jsonObj.put(efferentParam.get(i), LookUpValuesList.get(i).get(jsonObj.getString(incomingParam.get(i))));
                }
                // 查询用户信息
                try {
                    if (!org.springframework.util.ObjectUtils.isEmpty(jsonObj.getInteger("createdBy"))) {
                        jsonObj = getUserInfoForCreatedBy(jsonObj);
                    }
                } catch (Exception e) {
                }
                returnJson.add(jsonObj);
            }
            return returnJson;
        } catch (Exception e) {
            return jsonParams;
        }
    }

    public static JSONObject getLookUpValues(JSONObject jsonParam, List<String> incomingParam, List<String> efferentParam, List<String> typeParam) {
        List<JSONObject> LookUpValuesList = new ArrayList<>();
        LookUpValuesList = getReturnJson(incomingParam, efferentParam, typeParam);
        JSONObject returnJson = new JSONObject();
        returnJson = jsonParam;
        try {
            for (int i = 0; i < incomingParam.size(); i++) {
                returnJson.put(efferentParam.get(i), LookUpValuesList.get(i).get(returnJson.getString(incomingParam.get(i))));
            }
            // 查询用户信息
            if (!org.springframework.util.ObjectUtils.isEmpty(returnJson.getInteger("createdBy"))) {
                returnJson = getUserInfoForCreatedBy(returnJson);
            }
//            if (!org.springframework.util.ObjectUtils.isEmpty(returnJson.getInteger("lastUpdatedBy"))) {
//                returnJson = getUserInfoForLastUpdatedBy(returnJson);
//            }
            return returnJson;
        } catch (Exception e) {
            return jsonParam;
        }
    }

    public static List<JSONObject> getReturnJson(List<String> IncomingParam, List<String> EfferentParam, List<String> typeParam) {
        List<JSONObject> LookUpValuesList = new ArrayList<>();
        for (int i = 0; i < typeParam.size(); i++) {
            JSONObject b = new JSONObject();
            b.put("lookupType", typeParam.get(i));
            JSONObject raesultJSON = getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getLookUpValuesMap", b);
            LookUpValuesList.add(raesultJSON);
        }
        return LookUpValuesList;
    }

    public static JSONObject getActivitiesHistoric(String code) {
        JSONObject b = new JSONObject();
        b.put("code", code);
        JSONObject raesultJSON = getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getActivitiesHistoric", b);
        return raesultJSON;
    }

    public static JSONObject getUserInfoForLastUpdatedBy(JSONObject jsonObj) {
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("userId", jsonObj.getInteger("lastUpdatedBy"));
        JSONObject resultJson = getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
        Assert.notNull(resultJson, "根据最后更新人lastUpdatedBy:" + jsonObj.getInteger("lastUpdatedBy") + "查询数据为空");
        if (jsonObj.getString("userFullName") == null) {
            jsonObj.put("userFullName", resultJson.getString("userFullName"));
        }
        jsonObj.put("lastUpdatedByName", resultJson.getString("userFullName"));
        jsonObj.put("email", resultJson.getString("email"));
        return jsonObj;
    }

    public static JSONObject getUserInfoForCreatedBy(JSONObject jsonObj) {
        JSONObject paramsJson = new JSONObject();
        paramsJson.put("userId", jsonObj.getInteger("createdBy"));
        JSONObject resultJson = getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson);
        Assert.notNull(resultJson, "根据创建人userId:" + jsonObj.getInteger("createdBy") + "查询数据为空");
        jsonObj.put("userName", resultJson.getString("userName"));
        jsonObj.put("createdByName", resultJson.getString("userFullName"));
        jsonObj.put("userFullName", resultJson.getString("userFullName"));
        jsonObj.put("email", resultJson.getString("email"));
        jsonObj.put("phoneNumber", resultJson.getString("phoneNumber"));
        jsonObj.put("projectLeaderName", resultJson.getString("userFullName"));
        jsonObj.put("departmentFullName",resultJson.getString("departmentFullName"));
        jsonObj.put("deptName",resultJson.getString("departmentFullName"));
        return jsonObj;
    }

    public static JSONObject getServiceResult(String requestURL, JSONObject queryParamJSON) {
        Assert.isTrue(StringUtils.isNotBlank(requestURL), "requesturl is required");
        RestTemplate restTemplate = (RestTemplate) SpringBeanUtil.getBean("restTemplate");
        if (restTemplate == null) {
            throw new ExceptionInInitializerError("初始化异常");
        }

        MultiValueMap header = new LinkedMultiValueMap();
        Long timestamp = System.currentTimeMillis();
        header.add("timestamp", timestamp + "");
        header.add("pdasign", SaafToolUtils.buildSign(timestamp));
        JSONObject resultJSON = restSpringCloudPost(requestURL, queryParamJSON, header, restTemplate);
        if (resultJSON.getIntValue("statusCode") == 200) {
            JSONObject data = resultJSON.getJSONObject("data");
            return data;
        }
        return null;
    }


    public static JSONObject restSpringCloudPost(String requestURL, JSONObject postParam, MultiValueMap<String, String> headerParams, RestTemplate restTemplate) {
        JSONObject resultJSONObject = new JSONObject();

        if (!headerParams.containsKey("Accept")) {
            headerParams.add("Accept", "application/json");
        }

        if (!headerParams.containsKey("Accept-Charset")) {
            headerParams.add("Accept-Charset", "utf-8");
        }

        if (!headerParams.containsKey("Content-Type")) {
            headerParams.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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
                        requestBodey.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                if (iterator.hasNext())
                    requestBodey.append("&");
            }
        }

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        HttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        if (postParam != null && StringUtils.isNotBlank(postParam.toJSONString())) {
            try {
                restTemplate.setRequestFactory(clientHttpRequestFactory);
                HttpEntity request = new HttpEntity(requestBodey.toString(), headerParams);
                ResponseEntity responseEntity = restTemplate.postForEntity(requestURL, request, String.class, new Object[0]);
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

}


