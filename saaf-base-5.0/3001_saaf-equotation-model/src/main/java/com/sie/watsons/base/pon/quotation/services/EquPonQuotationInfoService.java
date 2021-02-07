package com.sie.watsons.base.pon.quotation.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.quotation.model.entities.EquPonQuotationInfoEntity_HI;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationInfoEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.inter.IEquPonQuotationInfo;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.base.utils.restful.RestfulClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/equPonQuotationInfoService")
public class EquPonQuotationInfoService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationInfoService.class);

    @Autowired
    private IEquPonQuotationInfo equPonQuotationInfoServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.equPonQuotationInfoServer;
    }

    @PostMapping("/findQuotationInfoPagination")
    public String findQuotationInfoPagination(@RequestParam String params,
                                              @RequestParam(defaultValue = "1", required = false) Integer pageIndex,
                                              @RequestParam(defaultValue = "10", required = false) Integer pageRows) {
        try {
            JSONObject jsonObject = parseObject(params);
            Integer userId = getUserSessionBean().getUserId();
            jsonObject = equPonQuotationInfoServer.findQuotationInfoPagination(jsonObject, pageIndex, pageRows,userId);

            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("relevantCatetory");
            incomingParam.add("projectCategory");
            incomingParam.add("orderType");
            incomingParam.add("docStatus");
            efferentParam.add("relevantCatetoryMeaning");
            efferentParam.add("projectCategoryMeaning");
            efferentParam.add("orderTypeMeaning");
            efferentParam.add("docStatusMeaning");
            typeParam.add("EQU_PROJECT_CATEGORY");
            typeParam.add("EQU_PROJECT_TYPE");
            typeParam.add("EQU_PON_ORDER_TYPE");
            typeParam.add("EQU_PON_QUOTATION_DOC_STATUS");
            JSONArray data = jsonObject.getJSONArray("data");
            data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
            jsonObject.put("data",data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/findQuotationInfo")
    public String findQuotationInfo(@RequestParam("params") String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            Integer userId = getSessionUserId();
            jsonObject.put("userId", userId);
            EquPonQuotationInfoEntity_HI_RO quotationInfo = equPonQuotationInfoServer.findQuotationInfo(jsonObject);
            // 根据供应商id和立项id查询如果没有数据立项只读页面则显示确定和拒绝按钮，否则隐藏
            if (quotationInfo == null){
                return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
            }
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            incomingParam.add("relevantCatetory");
            incomingParam.add("projectCategory");
            incomingParam.add("orderType");
            incomingParam.add("docStatus");
            efferentParam.add("relevantCatetoryMeaning");
            efferentParam.add("projectCategoryMeaning");
            efferentParam.add("orderTypeMeaning");
            efferentParam.add("docStatusMeaning");
            typeParam.add("EQU_PROJECT_CATEGORY");
            typeParam.add("EQU_PROJECT_TYPE");
            typeParam.add("EQU_PON_ORDER_TYPE");
            typeParam.add("EQU_PON_QUOTATION_DOC_STATUS");
            jsonObject = ResultUtils.getLookUpValues(JSON.parseObject(JSONObject.toJSONString(quotationInfo)),incomingParam,efferentParam,typeParam);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, jsonObject).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/waitQuotationInfoPagination")
    public String waitQuotationInfo(@RequestParam("params") String params,
                                    @RequestParam(defaultValue = "1", required = false) Integer pageIndex,
                                    @RequestParam(defaultValue = "10", required = false) Integer pageRows) {
        try {
            Integer userId = getUserSessionBean().getUserId();
            JSONObject jsonObject = equPonQuotationInfoServer.waitQuotationInfo(params, pageIndex, pageRows, userId);
            JSONArray data = jsonObject.getJSONArray("data");
            List<String> incomingParam = new ArrayList<>();
            List<String> efferentParam = new ArrayList<>();
            List<String> typeParam = new ArrayList<>();
            data = ResultUtils.getLookUpValues(data,incomingParam,efferentParam,typeParam);
            jsonObject.put("data",data);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/confirmParticipation")
    public String confirmParticipation(@RequestParam("params") String params) {
        try {
            Integer userId = getUserSessionBean().getUserId();
            Integer quotationId = equPonQuotationInfoServer.confirmParticipation(params, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, quotationId).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/confirmParticipationForTest")
    public String confirmParticipationForTest(@RequestParam("params") String params) {
        try {
            Integer quotationId = equPonQuotationInfoServer.confirmParticipations(params);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, quotationId).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/rejectParticipation")
    public String rejectParticipation(@RequestParam("params") String params) {
        try {
            Integer userId = getUserSessionBean().getUserId();
            Integer updateHeadId = equPonQuotationInfoServer.rejectParticipation(params, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, updateHeadId).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveQuotationInfo")
    public String saveQuotationInfo(@RequestParam(required = true) String editParams) {
        try {
            JSONObject jsonObject = parseObject(editParams);
            Integer userId = getUserSessionBean().getUserId();
            EquPonQuotationInfoEntity_HI instance = equPonQuotationInfoServer.saveQuotationInfo(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/deleteQuotationInfo")
    public String deleteQuotationInfo(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            return equPonQuotationInfoServer.deleteQuotationInfo(jsonObject, userId);
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/remoteCallByTemplate")
    public void remoteCallByTemplate() {
        RestTemplate restTemplate =new RestTemplate();
        String url = "http://127.0.0.1:2211/baseLoginService/test";

//        String url = "http://127.0.0.1:8089/api/baseServer/baseLoginService/test";
//        String params = "{\"userName\":\"admin\",\"pwd\":\"MTIzNDU2\",\"domain\":\"http://localhost\"}";
//        String param = "{'xing':'chu','ming':'cai'}";
        // exchange请求
        MultiValueMap<String, Object> exchangeMap = new LinkedMultiValueMap<String, Object>();
        JSONObject jsonObject1 = new JSONObject();
        HashMap<String, Object> map = new HashMap<>();
        map.put("xing", "chu");
        map.put("ming", "baodong");
        String s = RestfulClientUtils.restfulPostFormParam(url, map,new HashMap<>());
        System.out.println(s);
        exchangeMap.add("params", jsonObject1.toJSONString());
//        jsonObject1.put("domain", "http://localhost");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(exchangeMap, headers);
//        JSONObject jsonObject = SaafToolUtils.restSpringCloudPost(url, jsonObject1, null, restTemplate);
//        System.out.println(jsonObject.toString());
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if (exchange.getStatusCode() == HttpStatus.OK) {
            String exchangeBody = exchange.getBody();
            System.out.println(exchangeBody);
        }
    }

    /**
     * 生成二次议价单据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "generateAgainQuotationDoc")
    public String generateAgainQuotationDoc(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            String instance = equPonQuotationInfoServer.generateAgainQuotationDoc(jsonObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

//    @PostMapping("/findQuotationDetails")
//    public String findQuotationDetails(@RequestParam String params) {
//        try {
//            JSONObject jsonObject = parseObject(params);
//            jsonObject = equPonQuotationInfoServer.findQuotationDetails(jsonObject);
//            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, jsonObject).toString();
//        } catch (IllegalArgumentException e) {
//            LOGGER.warn(e.getMessage());
//            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
//        }
//    }

    /**
     * 查询单据状态是报价中或已提交的，如果截止时间比当前时间小，则将单据更改为已截止状态
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateQuotationInfoForQuotationStatus")
    public String updateQuotationInfoForQuotationStatus() {

        try {
            equPonQuotationInfoServer.updateQuotationInfoForQuotationStatus();
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 用于监控发起报价邀请的所有立项单据48小时之内是否生成报价
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "monitorQuotationGenerate")
    public String updateMonitorQuotationGenerate() {

        try {
            equPonQuotationInfoServer.updateMonitorQuotationGenerate();
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }
    }
}